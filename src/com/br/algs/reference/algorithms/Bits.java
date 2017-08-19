package com.br.algs.reference.algorithms;

/**
 * Created by rene on 15/07/17.
 */
public class Bits {

    /**
     * Clears a range of bits in the specified integer.
     *
     * @param bitmap
     *            Integer argument
     * @param positionToStart
     *            Position (0-31) -> Left to right
     * @param numberOfBitsToClear
     *            Number of bits to clear
     * @return Modified integer
     */
    //Example: positionToStart = 5, numberOfBitsToClear = 2
    //~0                   11111111111111111111111111111111
    // 32 - 2 = 30
    //~0 << 30             11000000000000000000000000000000
    //(~0 << 30) >>> 5     00000110000000000000000000000000
    //~((~0 << 30) >>> 5)  11111001111111111111111111111111
    private static int clearRangeOfBits(int bitmap, int positionToStart, int numberOfBitsToClear) {
        return bitmap & ~((~0 << (32 - numberOfBitsToClear)) >>> positionToStart);
    }

    /**
     * Clears a range of bits in the specified integer.
     *
     * @param bitmap
     *            Integer argument
     * @param positionToStart
     *            Position (0-31) -> Left to right
     * @param numberOfBitsToSet
     *            Number of bits to set
     * @return Modified integer
     */
    private static int setRangeOfBits(int bitmap, int positionToStart, int numberOfBitsToSet) {
        return bitmap | (~0 << (32 - numberOfBitsToSet)) >>> positionToStart;
    }

    private static int clearAllBitsExceptRange(int bitmap, int positionToStart, int positionToEnd) {
        int numberOfBitsInRange = positionToEnd - positionToStart + 1;
        return bitmap & ((~0 << (32 - numberOfBitsInRange)) >>> positionToStart);
    }

    //O(s) where s is the number of set bits
    private static int count1Bits(int value) {
        int count = 0;

        while (value > 0) {
            value = value & (value - 1);
            count++;
        }

        return count;
    }

    //O(lg s) where s is the number of set bits
    private static int count1BitsLgSetBits(int value) {
        value = value - ((value >>> 1) & 0x55555555);
        value = (value & 0x33333333) + ((value >>> 2) & 0x33333333);
        return (((value + (value >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;
    }

}
