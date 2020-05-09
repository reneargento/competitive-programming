package algorithms.dynamic.programming.longest.increasing.subsequence;

import java.util.*;

/**
 * Created by Rene Argento on 28/12/16.
 */

// Computes the longest increasing subsequence in O(N lg N)
// Based on https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
// and https://www.geeksforgeeks.org/construction-of-longest-monotonically-increasing-subsequence-n-log-n/
public class LongestIncreasingSubsequence {

    public static String longestIncreasingSubsequence(int[] array) {
        if (array == null || array.length == 0) {
            return "";
        }

        int[] endIndexes = new int[array.length];
        int[] previousIndices = new int[array.length];

        Arrays.fill(previousIndices, -1);
        int length = 1;

        for (int i = 1; i < array.length; i++) {
            // Case 1 - smallest end element
            if (array[i] <= array[endIndexes[0]]) {
                endIndexes[0] = i;
            } else if (array[i] > array[endIndexes[length - 1]]) {
                // Case 2 - highest end element - extends longest increasing subsequence
                previousIndices[i] = endIndexes[length - 1];
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(array, endIndexes, -1, length - 1, array[i]);
                previousIndices[i] = endIndexes[indexToReplace - 1];
                endIndexes[indexToReplace] = i;
            }
        }

        List<Integer> sequence = getSequence(array, endIndexes, previousIndices, length);
        return getSequenceDescription(sequence);
    }

    private static int ceilIndex(int[] array, int[] endIndexes, int low, int high, int key) {
        while (high - low > 1) {
            int middle = low + (high - low) / 2;

            if (array[endIndexes[middle]] >= key) {
                high = middle;
            } else {
                low = middle;
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

    private static String getSequenceDescription(List<Integer> sequence) {
        StringJoiner sequenceDescription = new StringJoiner(" ");
        for (Integer value : sequence) {
            sequenceDescription.add(value.toString());
        }
        return sequenceDescription.toString();
    }

    public static void main (String[] args) {
        int[] array1 = {2, 5, 3, 7, 11, 8, 10, 13, 6};
        System.out.println("Longest Increasing Subsequence: " + longestIncreasingSubsequence(array1));
        System.out.println("Expected: 2 3 7 8 10 13");

        int[] array2 = {6, 8, 1, 3, 9, 4, 5, 2, 7, -1, 11};
        System.out.println("\nLongest Increasing Subsequence: " + longestIncreasingSubsequence(array2));
        System.out.println("Expected: 1 3 4 5 7 11");

        int[] array3 = {144, 2, 2, 106, 184, 21, 42, 68, 128, 189, 72, 108, 158, 98, 36, 8, 153, 48, 103, 133, 133, 48,
                90, 154, 167, 146, 168, 129, 100, 46, 188, 197, 49, 190, 103, 33, 163, 97, 53, 92, 86, 125, 152, 196,
                175, 188, 157, 129, 36, 60, 14, 121, 60, 104, 28, 27, 50, 148, 156};
        System.out.println("\nLongest Increasing Subsequence: " + longestIncreasingSubsequence(array3));
        System.out.println("Expected: 2 21 42 68 72 98 103 133 154 167 168 188 190 196");
    }

}