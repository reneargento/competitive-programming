package algorithms.bitwise.operations;

/**
 * Created by Rene Argento on 07/03/21.
 */
public class GrayCodes {

    private static long getGrayCode(long position) {
        return position ^ (position >> 1);
    }

    // Based on https://stackoverflow.com/questions/9617782/whats-the-reverse-function-of-x-xor-x-2
    private static long getGrayCodePosition(long grayCode) {
        long position = 0;
        while (grayCode > 0) {
            position ^= grayCode;
            grayCode >>= 1;
        }
        return position;
    }
}
