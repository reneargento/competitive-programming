package algorithms.sorting;

/**
 * Created by Rene Argento on 17/01/21.
 */
// Used to sort non-negative numbers in O(t * (n + k)),
// where t is the log of maxValue (on base radix), n is the array length and k is the radix.
public class RadixSort {

    private static void radixSort(long[] sequence, int radix) {
        long[] aux = new long[sequence.length];
        long exponent = 1;
        long maxValue = getMaxValue(sequence);

        while (exponent < maxValue) {
            int[] values = new int[radix];

            for (int i = 0; i < sequence.length; i++) {
                int index = (int) (sequence[i] / exponent % radix);
                values[index]++;
            }

            for (int i = 1; i < values.length; i++) {
                values[i] += values[i - 1];
            }

            for (int i = sequence.length - 1; i >= 0; i--) {
                int index = (int) (sequence[i] / exponent % radix);
                aux[--values[index]] = sequence[i];
            }

            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = aux[i];
            }
            exponent *= radix;
        }
    }

    private static long getMaxValue(long[] sequence) {
        long maxValue = sequence[0];

        for (long value : sequence) {
            maxValue = Math.max(maxValue, value);
        }
        return maxValue;
    }
}
