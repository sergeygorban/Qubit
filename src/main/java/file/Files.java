package file;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import excel.Api;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by Admin on 17.04.2018.
 */
public class Files {

    public List<Path> getAllFilesFromDir(String directory) {

        try {
            return java.nio.file.Files.list(Paths.get(directory)).collect(Collectors.toList());
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteAllFilesInDir(String directory) {

        getAllFilesFromDir(directory).stream()
                .filter(Objects::nonNull)
                .forEach(file -> {
                    try {
                        java.nio.file.Files.delete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    public Path getIncomingDataForApi(Api api) {

        try {
            return java.nio.file.Files.list(Paths.get(api.STORAGE))
                    .filter(file -> file.getFileName().toString().startsWith(api.dataFile))
                    .findFirst().get();
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> void openFile(T pathToFile) {

        try {
            Runtime.getRuntime().exec("cmd /c start " + pathToFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // Termination of the process
    public void closeExcel() {

        try {
            Runtime.getRuntime().exec("cmd /c taskkill /f /im excel.exe");
            Thread.sleep(1000);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Workbook readingFileExcel(Path pathToFile) {

        try(InputStream inputStream = java.nio.file.Files.newInputStream(pathToFile)) {

            return new XSSFWorkbook(inputStream);

        } catch (Exception e) {
            throw new  RuntimeException(e.getMessage());
        }
    }

    public String writingFileExcel(String pathToFile, Workbook workbook) {

        try(FileOutputStream outputStream = new FileOutputStream(pathToFile)) {

            workbook.write(outputStream);

            return pathToFile;

        } catch (Exception e) {
            throw new  RuntimeException(e.getMessage());
        }
    }

    public Path writingFileExcel(Path pathToFile, Workbook workbook) {

        try(OutputStream outputStream = new FileOutputStream(pathToFile.toFile())) {

            workbook.write(outputStream);

            return pathToFile;

        } catch (Exception e) {
            throw new  RuntimeException(e.getMessage());
        }
    }

    public Path createNewFileExcel(Api api, Workbook workbook) {

        String pathToFile = api.STORAGE + "\\" +  api.dataFile + "_"
                +  LocalDateTime.now().format(DateTimeFormatter.ofPattern("y-MM-dd_HH-mm-ss")) + ".xlsx";

        try(FileOutputStream outputStream = new FileOutputStream(pathToFile)) {
            workbook.write(outputStream);

            return Paths.get(pathToFile);

        } catch (Exception e) {
            throw new  RuntimeException(e.getMessage());
        }
    }

    // Closing the application window
    public  void closingApplicationWindow (String windowTitle) {

        try {

            // https://msdn.microsoft.com/ru-ru/library/windows/desktop/ms633499(v=vs.85).aspx
            WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, windowTitle);

            if (hwnd != null) {

                User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null);
                Thread.sleep(1000);
            }

            if (User32.INSTANCE.FindWindow(null, windowTitle) != null) {

                User32.INSTANCE.SetForegroundWindow(hwnd);

                WinUser.INPUT input = new WinUser.INPUT();
                input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
                input.input.setType("ki");
                input.input.ki.wScan = new WinDef.WORD( 0 );
                input.input.ki.time = new WinDef.DWORD( 0 );
                input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR( 0 );

                // https://msdn.microsoft.com/en-us/library/windows/desktop/dd375731(v=vs.85).aspx
                input.input.ki.wVk = new WinDef.WORD(0x0D);

                // https://msdn.microsoft.com/en-us/library/windows/desktop/ms646310(v=vs.85).aspx
                User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
                Thread.sleep(1000);

            }

            if (User32.INSTANCE.FindWindow(null, windowTitle) != null)
                throw new RuntimeException("Could not close application");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}

