package selenium;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

public class AllureReport {

    // Adding step in the different place
    @Step("{0}")
    public static void step(final String message){
    }

    // Adding Request to Allure report
    @Attachment(value = "Request", type = "text/plain")
    public static String addingRequestToReport(String message) {
        return message;
    }

    // Adding Response to Allure report
    @Attachment(value = "Response", type = "text/plain")
    public static String addingResponseToReport(String message) {
        return message;
    }

}
