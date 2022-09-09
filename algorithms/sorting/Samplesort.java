package algorithms.sorting;

import java.util.Random;

/**
 * Created by Rene Argento on 26/08/22.
 */
// Based on Python's (2.23) version of Samplesort
// The large number of samples makes a quadratic-time case almost impossible, and asymptotically drives the
// average-case number of compares from quicksort's 2 N ln N (or 12/7 N ln N for the median-of-3 variant) down
// to N lg N.
// To avoid using extra memory, the pivots are stored in the array and shuffled around as partitioning proceeds.
public class Samplesort {

    private static final int INSERTION_SORT_CUTOFF = 2;
    private static Random random;

    private static <T extends Comparable<T>> void sampleSort(T[] array) {
        random = new Random();
        sampleSort(array, array.length);
    }

    private static <T extends Comparable<T>> void sampleSort(T[] array, int size) {
        if (size <= INSERTION_SORT_CUTOFF) {
            insertionSort(array, 0, size);
            return;
        }

        // numberOfPivots ~= lg(n / ln(n))
        double sizeByLogSize = size / Math.log(size);
        int k = (int) Math.round(Math.log(sizeByLogSize) / Math.log(2));
        int numberOfPivots = (int) Math.pow(2, k) - 1;

        // Pick random samples
        for (int i = 0; i < numberOfPivots; i++) {
            int randomIndex = random.nextInt(size);
            exchange(array, i, randomIndex);
        }

        // Recursively sort the sample
        sampleSort(array, numberOfPivots);

        // | 0 1 ... numberOfPivots-1 | numberOfPivots ... n-1 |
        // | <-   sorted sample  ->   | <-    unknown    ->    |
        //                            | lo                  hi |
        sampleSort(array, numberOfPivots, size - 1, numberOfPivots);
    }

    private static <T extends Comparable<T>> void sampleSort(T[] array, int low, int high,
                                                             int numberOfPivots) {
        if (low > high) {
            return;
        }
        if (numberOfPivots == 0) {
            insertionSort(array, low, high);
            return;
        }

        // Move pivots in-place to always have them on both partitions.
        if (numberOfPivots > 0) {
            // The pivots are to the left of low. Move half to the right end.
            int halfPivots = numberOfPivots / 2;
            for (int i = 0; i < halfPivots; i++) {
                low--;
                exchange(array, low, high);
                high--;
            }
        } else {
            // The pivots are to the right of high. Move half to the left end.
            numberOfPivots = -numberOfPivots;
            int halfPivots = (numberOfPivots + 1) / 2;
            for (int i = 0; i < halfPivots; i++) {
                high++;
                exchange(array, low, high);
                low++;
            }
        }

        low--;
        int partition = partition(array, low, high);

        int halfPivots;
        int halfPivotsOnLeftPartition;
        if (numberOfPivots % 2 == 0) {
            halfPivots = numberOfPivots / 2;
            halfPivotsOnLeftPartition = halfPivots - 1;
        } else {
            halfPivots = numberOfPivots / 2;
            halfPivotsOnLeftPartition = halfPivots;
        }
        sampleSort(array, low, partition - 1, halfPivotsOnLeftPartition);
        sampleSort(array, partition + 1, high, -halfPivots);
    }

    private static <T extends Comparable<T>> int partition(T[] array, int low, int high) {
        T pivot = array[low];

        int i = low;
        int j = high + 1;

        while (true) {
            while (less(array[++i], pivot)) {
                if (i == high) {
                    break;
                }
            }

            while (less(pivot, array[--j])) {
                if (j == low) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }
            exchange(array, i, j);
        }

        // Place pivot in the right place
        exchange(array, low, j);
        return j;
    }

    public static <T extends Comparable<T>> boolean less(T value1, T value2) {
        return value1.compareTo(value2) < 0;
    }

    private static boolean more(Comparable value1, Comparable value2) {
        return value1.compareTo(value2) > 0;
    }

    private static <T extends Comparable<T>> void exchange(T[] array, int position1, int position2) {
        T temp = array[position1];
        array[position1] = array[position2];
        array[position2] = temp;
    }

    private static <T extends Comparable<T>> void insertionSort(T[] array, int low, int high) {
        for (int i = low; i <= high; i++) {
            for (int j = i; j > 0 && more(array[j - 1], array[j]); j--) {
                T temp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = temp;
            }
        }
    }
}
