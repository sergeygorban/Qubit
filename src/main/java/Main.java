import com.google.api.services.sheets.v4.model.ValueRange;
import google.sheets.GoogleSheets;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {


        String spreadsheetId = "1q-sL5vDJ1NbThKyO54tlZxkCg2WzpxuSaJTcVeBpPiY";
        String range = "Sheet1";

        /*List<List<Object>> sheet = new GoogleSheets().getRowsValues(spreadsheetId, range);
        System.out.println(sheet);


        new GoogleSheets().updateValues(spreadsheetId, "Sheet1!D2:E5", Arrays.asList(Arrays.asList("!!!!!!!")));


        new GoogleSheets().updateValueForCell(spreadsheetId, "Sheet1!D2:E5", "11111111111");

        List<List<Object>> listForFirstRange = new ArrayList<>();
        listForFirstRange.add(Arrays.asList("1", "2"));
        listForFirstRange.add(Arrays.asList("3", "4"));

        List<List<Object>> listForSecondRange = new ArrayList<>();
        listForSecondRange.add(Arrays.asList("3"));

        new GoogleSheets().updateValuesForListRanges(spreadsheetId, Arrays.asList("Sheet1!D2:E3", "Sheet1!G2:G2"),
                listForFirstRange, listForSecondRange);*/

        System.out.println(new GoogleSheets()
                .createSpreadsheetUsingApiKey().values().get(spreadsheetId, range).execute().getValues());

        new GoogleSheets().createSpreadsheetUsingApiKey()
                .values()
                .update(spreadsheetId, "Sheet1!D2:E2",new ValueRange().setValues(Arrays.asList(Arrays.asList("1","2"))))

                .setValueInputOption("RAW")
                .execute();


    }
}
