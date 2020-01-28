package time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {



    public long getAmountDaysBetweenTwoDates(LocalDate firstDate, LocalDate secondDate) {
        return Duration.between(firstDate.atStartOfDay(), secondDate.atStartOfDay()).toDays();
    }

    public static int getAmountDaysForSqlServer(LocalDate date) {
        return (int) Duration.between(LocalDate.parse("1900-01-01").atStartOfDay(), date.atStartOfDay()).toDays();
    }

    public Duration getDurationBetweenTwoDates(LocalDateTime firstDate, LocalDateTime secondDate) {
        return Duration.between(firstDate, secondDate);
    }

}
