package time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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

    public Duration getDurationBetweenTwoDates(LocalDate firstDate, LocalDate secondDate) {
        return Duration.between(firstDate, secondDate);
    }

    public static LocalDate getFirstDayOfCurrentMonth() {
        return LocalDate.now().withDayOfMonth(1);
    }

    public static LocalDate getLastDayOfCurrentMonth() {
        return LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
    }


    public static void main(String[] args) {

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeFormat.ISO_4.getFormat())));
        System.out.println(Time.getFirstDayOfCurrentMonth());
        System.out.println(Time.getLastDayOfCurrentMonth());

    }

}
