package algorithms.bitwise.operations;

/**
 * Created by Rene Argento on 15/07/17.
 */
public class BitOperations {

    private static int getBit(int number, int index) {
        int mask = 1 << index;
        if ((number & mask) != 0) {
            return 1;
        }
        return 0;
    }

    private static int setBit(int number, int index) {
        int mask = 1 << index;
        return number | mask;
    }

    private static int clearBit(int number, int index) {
        int mask = ~(1 << index);
        return number & mask;
    }

    private static int updateBit(int number, int index, boolean isBit1) {
        int value = isBit1 ? 1 : 0;
        int mask = ~(1 << index);
        return (number & mask) | (value << index);
    }

    // Clears all bits from the most significant bit to index.
    // Example: number = 11111001, index = 5
    // Result: 00011001
    private static int clearBitsMSBThroughI(int number, int index) {
        int mask = (1 << index) - 1;
        return number & mask;
    }

    // Clears all bits in the range [index, 0].
    // Example: number = 11111001, index = 5
    // Result: 11000000
    private static int clearBitsIThrough0(int number, int index) {
        int mask = -1 << (index + 1);
        return number & mask;
    }

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
    public static int clearRangeOfBits(int bitmap, int positionToStart, int numberOfBitsToClear) {
        return bitmap & ~((~0 << (32 - numberOfBitsToClear)) >>> positionToStart);
    }

    /**
     * Sets a range of bits in the specified integer.
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

    // Clears last set bit.
    // Example: number = 10010
    // Result: 10000
    private static int clearLastBit1(int number) {
        return number & (number - 1);
    }

    // Sets last unset bit.
    // Example: number = 100101
    // Result: 100111
    private static int setLastBit0(int number) {
        return number | (number + 1);
    }

    // Clears last consecutive set bits.
    // Example: number = 100111
    // Result: 100000
    private static int clearLastConsecutiveSetBits(int number) {
        return number & (number + 1);
    }

    // Sets last consecutive unset bits.
    // Example: number = 1001000
    // Result: 1001111
    private static int setLastConsecutiveUnsetBits(int number) {
        return number | (number - 1);
    }

    // Example: number = 110110
    // Result: 000010
    private static int getSmallestPowerOf2(int number) {
        return number & (-number);
    }

    // Example: number = 110100
    // Result: 2
    private static int getIndexOfFirstOne(int number) {
        return Integer.numberOfTrailingZeros(number);
    }
}
