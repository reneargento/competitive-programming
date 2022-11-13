package algorithms.dynamic.programming.longest.increasing.subsequence;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rene Argento on 01/10/22.
 */
// Counts the minimum number of increasing subsequences in the array.
// Dilworth's theorem: the minimal decomposition into ascending subsequences is equal to the size of the
// maximal non-increasing subsequence.
public class CountNumberOfIncreasingSubsequences {

    // O(N lg N)
    private static int countMinimumIncreasingSubsequences(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int[] endIndexes = new int[array.length];
        int[] previousIndices = new int[array.length];

        Arrays.fill(previousIndices, -1);
        int length = 1;

        for (int i = 1; i < array.length; i++) {
            // Case 1 - highest end element
            if (array[i] > array[endIndexes[0]]) {
                endIndexes[0] = i;
            } else if (array[i] <= array[endIndexes[length - 1]]) {
                // Case 2 - smallest end element - extends longest non-increasing subsequence
                previousIndices[i] = endIndexes[length - 1];
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = previousElementIndex(array, endIndexes, 0, length - 1, array[i]);
                previousIndices[i] = endIndexes[indexToReplace - 1];
                endIndexes[indexToReplace] = i;
            }
        }

        List<Integer> sequence = getSequence(array, endIndexes, previousIndices, length);
        return sequence.size();
    }

    // Search is reversed because the array is sorted in a non-increasing order
    private static int previousElementIndex(int[] array, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (array[endIndexes[middle]] < key) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
    }

    private static List<Integer> getSequence(int[] array, int[] endIndexes, int[] previousIndices, int length) {
        LinkedList<Integer> sequence = new LinkedList<>();

        for (int i = endIndexes[length - 1]; i >= 0; i = previousIndices[i]) {
            sequence.addFirst(array[i]);
        }
        return sequence;
    }

    public static void main(String[] args) {
        int[] sequence1 = { 5, 1, 3, 7, 4, 9, 6, 8, 2 };
        int minimumIncreasingSequences1 = countMinimumIncreasingSubsequences(sequence1);
        System.out.println("Minimum Increasing Sequences 1: " + minimumIncreasingSequences1 + " Expected: 3");

        int[] sequence2 = { 1, 2, 3, 4 };
        int minimumIncreasingSequences2 = countMinimumIncreasingSubsequences(sequence2);
        System.out.println("Minimum Increasing Sequences 2: " + minimumIncreasingSequences2 + " Expected: 1");

        int[] sequence3 = { 5, 4, 3, 2, 1 };
        int minimumIncreasingSequences3 = countMinimumIncreasingSubsequences(sequence3);
        System.out.println("Minimum Increasing Sequences 3: " + minimumIncreasingSequences3 + " Expected: 5");

        int[] sequence4 = { 7, 7, 7, 7 };
        int minimumIncreasingSequences4 = countMinimumIncreasingSubsequences(sequence4);
        System.out.println("Minimum Increasing Sequences 4: " + minimumIncreasingSequences4 + " Expected: 4");
    }
}
