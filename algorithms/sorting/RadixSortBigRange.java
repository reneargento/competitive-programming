package algorithms.sorting;

/**
 * Created by Rene Argento on 17/01/21.
 */
// Used to sort non-negative numbers that are in a big range, such as [0 ... 10^9].
// Runs in O(t * (n + k)),
// where t is the log of sequenceData.maxValue (on base radix), n is the array length and k is the radix.
// The default radix in this case is 2^15.
// Notice the use of bit shifts instead of division and mod operations to optimize runtime.
public class RadixSortBigRange {

    private static class Sequence {
        long[] values;
        long maxValue;

        public Sequence(long[] values, long maxValue) {
            this.values = values;
            this.maxValue = maxValue;
        }
    }

    // Aux[] array is just an array with the same length as sequence[] and no values.
    private static long[] radixSort(Sequence sequenceData, long[] aux, int length) {
        long[] sequence = sequenceData.values;
        int powerOf2 = 15;
        int radix = (2 << powerOf2); // Power of 2 nearest to 10^5
        long exponent = 1;
        boolean isSortedArraySwapped = false;

        while ((2L << (exponent - 1)) < sequenceData.maxValue) {
            int[] values = new int[radix];

            for (int i = 0; i < length; i++) {
                int dividedValue;

                if (!isSortedArraySwapped) {
                    dividedValue = (int) (sequence[i] >> (exponent - 1)); // Same as sequence[i] / 2^(exponent - 1)
                } else {
                    dividedValue = (int) (aux[i] >> (exponent - 1));
                }
                int index = dividedValue & (radix - 1); // Same as dividedValue % radix
                values[index]++;
            }

            for (int i = 1; i < values.length; i++) {
                values[i] += values[i - 1];
            }

            for (int i = length - 1; i >= 0; i--) {
                if (!isSortedArraySwapped) {
                    int dividedValue = (int) (sequence[i] >> (exponent - 1));
                    int index = dividedValue & (radix - 1);
                    aux[--values[index]] = sequence[i];
                } else {
                    int dividedValue = (int) (aux[i] >> (exponent - 1));
                    int index = dividedValue & (radix - 1);
                    sequence[--values[index]] = aux[i];
                }
            }

            isSortedArraySwapped = !isSortedArraySwapped;
            exponent += powerOf2;
        }

        if (isSortedArraySwapped) {
            return aux;
        }
        return sequence;
    }
}
