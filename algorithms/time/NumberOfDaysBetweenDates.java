package algorithms.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by Rene Argento on 16/11/20.
 */
// Computes the number of days between two dates

// DateTimeFormatter patterns
// https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/format/DateTimeFormatter.html

// Reference: https://mkyong.com/java8/java-8-difference-between-two-localdate-or-localdatetime/
public class NumberOfDaysBetweenDates {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        long numberOfDays = numberOfDays(20, 1, 1998, 10, 1, 2009);
        System.out.println(numberOfDays + " days");
        System.out.println("Expected: 4009 days");;
    }

    private static long numberOfDays(int day1, int month1, int year1, int day2, int month2, int year2) {
        LocalDate date1 = getLocalDate(day1, month1, year1);
        LocalDate date2 = getLocalDate(day2, month2, year2);
        return ChronoUnit.DAYS.between(date1, date2.plusDays(1));
    }

    private static LocalDate getLocalDate(int day, int month, int year) {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String date = formattedDay + "-" + formattedMonth + "-" + year;
        return LocalDate.parse(date, dateTimeFormatter);
    }
}
