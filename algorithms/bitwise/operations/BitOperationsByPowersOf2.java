package algorithms.bitwise.operations;

/**
 * Created by Rene Argento on 17/01/21.
 */
public class BitOperationsByPowersOf2 {

    // Multiplies number by 2^powerOf2
    public static long multiply(long number, int powerOf2) {
        return number << powerOf2;
    }

    // Divides number by 2^powerOf2
    public static long divide(long number, int powerOf2) {
        return number >> powerOf2;
    }

    // Computes number % (2^powerOf2)
    // Based on https://stackoverflow.com/questions/11076216/re-implement-modulo-using-bit-shifts
    public static long mod(long number, int powerOf2) {
        return number & (powerOf2 - 1);
    }
}
