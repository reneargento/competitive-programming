package algorithms.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by Rene Argento on 16/11/20.
 */
public class IsNextDay {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        boolean isNextDay1 = isNextDay(20, 1, 1998, 10, 1, 1998);
        boolean isNextDay2 = isNextDay(29, 10, 1979, 30, 10, 1979);
        System.out.println("Is next day: " + isNextDay1 + " Expected: false");
        System.out.println("Is next day: " + isNextDay2 + " Expected: true");
    }

    private static boolean isNextDay(int day1, int month1, int year1, int day2, int month2, int year2) {
        LocalDate date1 = getLocalDate(day1, month1, year1);
        LocalDate date2 = getLocalDate(day2, month2, year2);
        return date2.equals(date1.plus(1, ChronoUnit.DAYS));
    }

    private static LocalDate getLocalDate(int day, int month, int year) {
        String formattedDay = getFormattedValue(day);
        String formattedMonth = getFormattedValue(month);
        String date = formattedDay + "-" + formattedMonth + "-" + year;
        return LocalDate.parse(date, dateTimeFormatter);
    }

    private static String getFormattedValue(int value) {
        String formattedValue = String.valueOf(value);
        if (formattedValue.length() == 1) {
            formattedValue = "0" + formattedValue;
        }
        return formattedValue;
    }
}
