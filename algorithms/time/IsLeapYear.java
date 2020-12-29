package algorithms.time;

/**
 * Created by Rene Argento on 19/12/20.
 */
public class IsLeapYear {

    private static boolean isLeapYear(int year) {
        return (year % 400 == 0 ||
                (year % 100 != 0 && year % 4 == 0));
    }
}
