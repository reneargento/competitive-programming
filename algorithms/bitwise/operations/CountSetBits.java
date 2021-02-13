package algorithms.bitwise.operations;

/**
 * Created by Rene Argento on 17/01/21.
 */
public class CountSetBits {

    // O(s) where s is the number of set bits
    public static int count1Bits(int value) {
        int count = 0;

        while (value > 0) {
            value = value & (value - 1);
            count++;
        }

        return count;
    }

    // O(lg s) where s is the number of set bits
    public static int count1BitsLgSetBits(int value) {
        value = value - ((value >>> 1) & 0x55555555);
        value = (value & 0x33333333) + ((value >>> 2) & 0x33333333);
        return (((value + (value >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;
    }
}
