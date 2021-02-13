package algorithms.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by Rene Argento on 19/11/20.
 */
// Computes the number of hours, minutes and seconds between times in the format HH:mm:ss
public class NumberOfHourMinuteSecondBetweenTimes {

    // Change to hh:mm:ss aa for AM/PM format
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static void main(String[] args) {
        int day = 18;
        int month = 4;
        int year = 1989;
        int startHour = 20;
        int startMinute = 0;
        int startSecond = 5;
        int endHour = 3;
        int endMinute = 35;
        int endSecond = 12;

        LocalDateTime startTime = getLocalDateTime(day, month, year, startHour, startMinute, startSecond);
        if (endHour < startHour
                || (endHour == startHour && endMinute <= startMinute)) {
            day++;
        }
        LocalDateTime endTime = getLocalDateTime(day, month, year, endHour, endMinute, endSecond);

        long secondsDifference = ChronoUnit.SECONDS.between(startTime, endTime);
        long hours = secondsDifference / 3600;
        long minutes = (secondsDifference % 3600) / 60;
        long seconds = (secondsDifference % 3600) % 60;
        System.out.printf("%02d:%02d:%02d\n", hours, minutes, seconds);
        System.out.println("Expected: 07:35:07");
    }

    private static LocalDateTime getLocalDateTime(int day, int month, int year, int hour, int minute, int second) {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String formattedHour = String.format("%02d", hour);
        String formattedMinute = String.format("%02d", minute);
        String formattedSecond = String.format("%02d", second);

        String dateTime = String.format("%s-%s-%s %s:%s:%s", formattedDay, formattedMonth, year, formattedHour,
                formattedMinute, formattedSecond);
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }
}
