package time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {


    public long getAmountDaysBetweenTwoDates(LocalDate firstDate, LocalDate secondDate) {
        return Duration.between(firstDate.atStartOfDay(), secondDate.atStartOfDay()).toDays();

    }

    public Duration getDurationBetweenTwoDates(LocalDateTime firstDate, LocalDateTime secondDate) {
        return Duration.between(firstDate, secondDate);

    }


    public static void main(String[] args) {

        System.out.println(LocalDate.now().minusDays(5));

        long a = new Time().getAmountDaysBetweenTwoDates(LocalDate.now(), LocalDate.parse("2019-10-30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        System.out.println(a);
        if (a >= 1) {
            System.out.println(a);
        }
    }
}
