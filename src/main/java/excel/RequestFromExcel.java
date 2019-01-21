package excel;

import creating_object.ValueForJson;
import file.Files;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * Created by Admin on 23.03.2018.
 */
public class RequestFromExcel {

    private static final String NO_PARAM = "no param";
    private static final String FILE_PATH = "filePath";
    private static final String PARAM = "P:";
    private static int CELL_FOR_REQ;
    private static int CELL_FOR_RES;
    private static int START_REQ;
    private static int END_REQ;
    private static boolean NEW_FILE;
    private static boolean SEND_EMAIL;

    public static void sendRequest(Api api) {

        // Reading file
        Path file = Files.getIncomingDataForApi(api);

        // Closing a file, if one is already opened
        String windowTitle = "Microsoft Excel - " + file.getFileName().toString();
        Files.closingApplicationWindow(windowTitle);


        Workbook workbook = Files.readingFileExcel(file);
        Sheet sheet = workbook.getSheet(api.dataSheet);

        if (sheet == null) {
            throw new RuntimeException("Sheet with parameters not found");
        }

        //Search for the initial row with parameters
        Row parameters = StreamSupport.stream(sheet.spliterator(), false)
                .filter(row -> StreamSupport.stream(row.spliterator(), false)
                        .anyMatch(cell -> cell.toString().trim().startsWith("P:")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Missing rows with parameters or parameters do not match " +
                        "the following format: \"P: Parameter name\""));

        // Checking the filling of all cells in the row
        StreamSupport.stream(parameters.spliterator(), false)
                .filter(cell -> cell.toString().trim().equals(""))
                .findFirst()
                .ifPresent(cell -> new RuntimeException("The parameter row must not contain empty cells"));


        // Validation of user parameters
        if (START_REQ != 0) {

            if (START_REQ > sheet.getLastRowNum()) throw new RuntimeException("The initial row for the request is greater than the number of rows in the sheet");
            if (START_REQ < parameters.getRowNum()) throw new RuntimeException("The initial row for the request is less than the number of row with parameters");
            if (START_REQ == parameters.getRowNum()) throw new RuntimeException("The initial row for the request should not be equal to a string with parameters");

        }

        if (END_REQ != 0) {
            if (END_REQ < START_REQ) throw new RuntimeException("END_REQ should not be less than START_REQ");
            if (END_REQ > sheet.getLastRowNum()) END_REQ = sheet.getLastRowNum();
        } else {
            END_REQ = sheet.getLastRowNum();
        }

        if (CELL_FOR_REQ == 0) {

            //Writing a request to a cell after the last parameter's cell
            CELL_FOR_REQ = StreamSupport.stream(parameters.spliterator(), false)
                    .filter(cell -> cell.toString().trim().startsWith(PARAM))
                    .max(Comparator.comparing(Cell::getColumnIndex))
                    .get().getColumnIndex() + 1;
        }

        if (CELL_FOR_RES == 0) {
            CELL_FOR_RES = CELL_FOR_REQ + 1;
        }

        // Setting cell styles
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);



        // Forming and sending a request
        StreamSupport.stream(sheet.spliterator(), false)
                .filter(row -> {
                    boolean is = false;
                    if (START_REQ == 0) is = row.getRowNum() > parameters.getRowNum();
                    if (START_REQ != 0 && END_REQ == 0) is = row.getRowNum() >= START_REQ;
                    if (START_REQ != 0 && END_REQ != 0) is = row.getRowNum() >= START_REQ && row.getRowNum() <= END_REQ;
                    return is;

                })
                .peek(row -> {

                    // Creating cells if cells do not have values
                    Stream.iterate(0, n -> n + 1)
                            .limit(parameters.getLastCellNum())
                            .forEachOrdered(n -> {
                                if (row.getCell(n) == null) {
                                    row.createCell(n);
                                    row.getCell(n).setCellStyle(cellStyle);
                                }
                            });
                })
                .forEachOrdered(row -> {
                    // Forming the body of the request
                    LinkedHashMap<String, Object> map = StreamSupport.stream(row.spliterator(), false)
                            .limit(parameters.getLastCellNum())
                            .filter(val -> !val.toString().equals(NO_PARAM)
                                    && parameters.getCell(val.getColumnIndex()).toString().trim().startsWith(PARAM)
                                    && !parameters.getCell(val.getColumnIndex()).toString().trim().substring(2).trim()
                                    .equals(FILE_PATH))
                            .collect(Collectors.toMap(
                                    a -> parameters.getCell(a.getColumnIndex()).toString().trim().substring(2).trim(),
                                    new ValueForJson(),
                                    (a, b) -> {
                                        throw new IllegalStateException(String.format("Duplicate key %s", a));
                                    },
                                    LinkedHashMap::new));

                    // Sending a request
                    List<String> proc;

                    if (api.method.contains("MULT")) {

                        String path = StreamSupport.stream(row.spliterator(), false)
                                .limit(parameters.getLastCellNum())
                                .filter(val -> !val.toString().equals(NO_PARAM) &&
                                        parameters.getCell(val.getColumnIndex()).toString().trim().startsWith(PARAM) &&
                                        parameters.getCell(val.getColumnIndex()).toString().trim().substring(2).trim()
                                                .equals(FILE_PATH))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("The Excel file does not have a " +
                                        "value for the file path"))
                                .toString();

                        proc = Requests.sendRequestNew(api,map,path);
                    } else {
                        proc = Requests.sendRequestNew(api,map,null);
                    }

                    row.getCell(CELL_FOR_REQ).setCellValue(proc.get(0));
                    row.getCell(CELL_FOR_RES).setCellValue(proc.get(1));

                });



        if (NEW_FILE) {

            file = Files.createNewFileExcel(api, workbook);
            Files.openFile(file);

        } else {

            Files.openFile(Files.writingFileExcel(file, workbook));
        }

        if (SEND_EMAIL) {

            new SendEmail().sendEmail(file);
        }
    }

    public static void setCellForReq(int cellForReq) {
        CELL_FOR_REQ = cellForReq - 1;
    }

    public static void setCellForRes(int cellForRes) {
        CELL_FOR_RES = cellForRes - 1;
    }

    public static void setStartReq(int startReq) {
        START_REQ = startReq - 1;
    }

    public static void setEndReq(int endReq) {
        END_REQ = endReq -1;
    }

    public static void setNewFile(boolean newFile) {
        NEW_FILE = newFile;
    }

    public static void setSendEmail(boolean sendEmail) {
        SEND_EMAIL = sendEmail;
    }

}
