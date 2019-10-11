package time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Time {


    public long getAmountDaysBetweenTwoDates(LocalDate firstDate, LocalDate secondDate) {
        return Duration.between(firstDate.atStartOfDay(), secondDate.atStartOfDay()).toDays();

    }


    public static void main(String[] args) {

        long a = new Time().getAmountDaysBetweenTwoDates(LocalDate.now(), LocalDate.parse("2019-10-17",
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        if (a > 3 && a <= 5) {
            System.out.println(a);
        }
    }
}
