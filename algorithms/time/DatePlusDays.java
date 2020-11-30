package algorithms.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Rene Argento on 17/11/20.
 */
// Computes the next date after X days.
// Reads and outputs dates in the format: 1984-October-12
public class DatePlusDays {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd");

    public static void main(String[] args) {
        String futureDate = getFutureDate("1984-October-12", 318);
        System.out.println("Date: " + futureDate + "\nExpected: 1985-August-26");
    }

    private static String getFutureDate(String dateString, int days) {
        LocalDate date = LocalDate.parse(dateString, dateTimeFormatter);
        LocalDate futureDate = date.plusDays(days);
        return futureDate.format(dateTimeFormatter);
    }
}
