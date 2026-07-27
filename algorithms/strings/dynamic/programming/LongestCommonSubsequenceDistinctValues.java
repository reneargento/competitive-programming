package algorithms.strings.dynamic.programming;

import java.util.*;

// Computes the LCS between two sequences when each sequence contains distinct values.
// In this scenario, the LCS problem can be reduced to a LIS problem.
// Time complexity: O(l * log(l)), where l is the minimum of (sequence1 length, sequence2 length)
public class LongestCommonSubsequenceDistinctValues {

    private static List<Integer> computeLCSDistinctValues(int[] sequence1, int[] sequence2) {
        Map<Integer, Integer> valueToIndexMap = computeValueToIndexMap(sequence2);
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < sequence1.length; i++) {
            int value = sequence1[i];
            Integer string2Index = valueToIndexMap.get(value);
            if (string2Index != null) {
                indexes.add(string2Index);
            }
        }

        Integer[] indexesArray = indexes.toArray(new Integer[0]);
        List<Integer> longestIncreasingSubsequence = longestIncreasingSubsequence(indexesArray);
        List<Integer> longestCommonSubsequence = new ArrayList<>();
        for (int index : longestIncreasingSubsequence) {
            longestCommonSubsequence.add(sequence2[index]);
        }
        return longestCommonSubsequence;
    }

    private static Map<Integer, Integer> computeValueToIndexMap(int[] sequence) {
        Map<Integer, Integer> valueToIndexMap = new HashMap<>();
        for (int i = 0; i < sequence.length; i++) {
            int value = sequence[i];
            valueToIndexMap.put(value, i);
        }
        return valueToIndexMap;
    }

    private static List<Integer> longestIncreasingSubsequence(Integer[] array) {
        if (array == null || array.length == 0) {
            return new ArrayList<>();
        }
        int[] endIndexes = new int[array.length];
        int[] previousIndexes = new int[array.length];

        Arrays.fill(previousIndexes, -1);
        int length = 1;

        for (int i = 1; i < array.length; i++) {
            // Case 1 - smallest end element
            if (array[i] <= array[endIndexes[0]]) {
                endIndexes[0] = i;
            } else if (array[i] > array[endIndexes[length - 1]]) {
                // Case 2 - highest end element - extends longest increasing subsequence
                previousIndexes[i] = endIndexes[length - 1];
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(array, endIndexes, 0, length - 1, array[i]);
                previousIndexes[i] = endIndexes[indexToReplace - 1];
                endIndexes[indexToReplace] = i;
            }
        }
        return getSequence(array, endIndexes, previousIndexes, length);
    }

    private static int ceilIndex(Integer[] array, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (array[endIndexes[middle]] >= key) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
    }

    private static List<Integer> getSequence(Integer[] array, int[] endIndexes, int[] previousIndexes, int length) {
        List<Integer> sequence = new ArrayList<>();

        for (int i = endIndexes[length - 1]; i >= 0; i = previousIndexes[i]) {
            sequence.add(array[i]);
        }
        Collections.reverse(sequence);
        return sequence;
    }

    public static void main() {
        int[] sequence1 = { 1, 2, 3, 4, 5, 6, 7 };
        int[] sequence2 = { 2, 1, 4, 5, 3, 7, 6 };

        List<Integer> longestCommonSubsequence = computeLCSDistinctValues(sequence1, sequence2);
        System.out.println("LCS: " + longestCommonSubsequence);
        System.out.println("Expected: [2, 4, 5, 7]");
    }
}
