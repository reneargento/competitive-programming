package algorithms.math.number.theory;

/**
 * Created by rene on 11/06/17.
 */
public class Multiples {

    // Rounds down a number to a multiple
    // Example: 13 and 4 becomes 12
    private static long roundDown(long number, long multiple) {
        if (number < multiple) {
            return 0;
        }

        long temp = number % multiple;

        if (temp < number - 2) {
            return number - temp;
        } else {
            return number + multiple - temp;
        }
    }

    // Rounds up a number to a multiple
    // Example: 13 and 4 becomes 16
    private static long roundUp(long number, long multiple) {
        return (number + (multiple - 1)) / multiple * multiple;
    }

}
