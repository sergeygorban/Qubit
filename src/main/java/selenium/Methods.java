package selenium;

import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Methods {

    public LocalDateTime parseTime(WebElement element) {

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy LLL d h:mm:ss a")
                .toFormatter(Locale.US);


        // For Plate Groups - Current day
        try {

            String time = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy LLL d", Locale.US)) + " " +
                    element.getText();
            return LocalDateTime.parse(time, formatter);

        // For Plate Groups - Not current day
        } catch (DateTimeParseException e) {

            try {
                return LocalDateTime.parse(LocalDate.now().getYear() + " " + element.getText(), formatter);

        // For Analytics reports
            } catch (DateTimeParseException e1) {

                DateTimeFormatter formatter1 = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("EEE LLL d H:mm:ss yyyy")
                        .toFormatter(Locale.US);

                return LocalDateTime.parse(element.getText(), formatter1);
            }
        }
    }


    public static void main(String[] args) {

        String a = "Tue Jul 23 23:30:34 2019";

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy LLL d h:mm:ss a")
                .toFormatter(Locale.US);

        try {

            String time = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy LLL d", Locale.US)) + " " +
                    a;
            System.out.println(LocalDateTime.parse(time, formatter));

            // Not current day
        } catch (DateTimeParseException e) {

            try {
                System.out.println(LocalDateTime.parse(LocalDate.now().getYear() + " " + a, formatter));

            } catch (DateTimeParseException e1) {

                DateTimeFormatter formatter1 = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("EEE LLL d H:mm:ss yyyy")
                        .toFormatter(Locale.US);

                System.out.println();

            }
        }
    }
}
