package google.sheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsRequestInitializer;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import constants.Const;
import singleton.Singleton;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GoogleSheets {

    private Credential credential = Singleton.INSTANCE.getCredential();
    private Sheets.Spreadsheets spreadsheet;

    // Values for MajorDimension - https://developers.google.com/sheets/api/reference/rest/v4/Dimension
    // Values for ValueRenderOption - https://developers.google.com/sheets/api/reference/rest/v4/ValueRenderOption
    // Values for DateTimeRenderOption - https://developers.google.com/sheets/api/reference/rest/v4/DateTimeRenderOption
    public List<List<Object>> getRowsValues(String spreadsheetID, String range) {
        try {
            return getSpreadsheet().values().get(spreadsheetID, range)
                    .setMajorDimension("ROWS")
                    .setValueRenderOption("UNFORMATTED_VALUE")
                    .setDateTimeRenderOption("FORMATTED_STRING")
                    .execute()
                    .getValues();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<List<Object>> getColumnsValues(String spreadsheetID, String range) {
        try {
            return getSpreadsheet().values().get(spreadsheetID, range)
                    .setMajorDimension("COLUMNS")
                    .setValueRenderOption("UNFORMATTED_VALUE")
                    .setDateTimeRenderOption("FORMATTED_STRING")
                    .execute()
                    .getValues();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Values for ValueInputOption - https://developers.google.com/sheets/api/reference/rest/v4/ValueInputOption
    public void updateValues(String spreadsheetID, String range, List<List<Object>> values) {

        try {
            getSpreadsheet().values().update(spreadsheetID, range, new ValueRange().setValues(values))
                    .setValueInputOption("RAW")
                    .execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateValueForCell(String spreadsheetID, String rangeForCell, String valueForCell) {

        try {
            getSpreadsheet().values().update(spreadsheetID, rangeForCell,
                    new ValueRange().setValues(Arrays.asList(Arrays.asList(valueForCell))))
                    .setValueInputOption("RAW")
                    .execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    // For the first range from the list "ranges" there corresponds the first list of
    // values from the array "valueForRanges", etc.

    @SafeVarargs
    public final void updateValuesForListRanges(String spreadsheetID, List<String> ranges,
                                                List<List<Object>>... valueForRanges) {

        List<ValueRange> valuesRanges = Stream.iterate(0, n -> n + 1)
                .limit(ranges.size())
                .map(n -> new ValueRange().setRange(ranges.get(n)).setValues(valueForRanges[n]))
                .collect(Collectors.toList());

        try {
            getSpreadsheet().values().batchUpdate(spreadsheetID,
                    new BatchUpdateValuesRequest().setData(valuesRanges).setValueInputOption("RAW"))
                    .execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Sheets.Spreadsheets createSpreadsheetUsingServiceAccount() {

        try {
            return new Sheets.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential)
                    .setApplicationName(" ")
                    .build()
                    .spreadsheets();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Sheets.Spreadsheets createSpreadsheetUsingApiKey() {

        try {
            return new Sheets.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(), new GoogleCredential().createScoped(Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY)))
                    .setGoogleClientRequestInitializer(
                            new SheetsRequestInitializer(Const.API_KEY.getValue()))
                    .setApplicationName("Jmeter")
                    .build()
                    .spreadsheets();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Sheets.Spreadsheets getSpreadsheet() {

        if (spreadsheet == null) {
            this.spreadsheet = createSpreadsheetUsingServiceAccount();
        }
        return spreadsheet;
    }

    public GoogleSheets setCredential(Credential credential) {
        this.credential = credential;
        return this;
    }
}
