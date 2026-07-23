package algorithms.strings.dynamic.programming;

import java.util.*;

// Computes the LCS between two strings when each string contains distinct characters.
// In this scenario, the LCS problem can be reduced to a LIS problem.
// Time complexity: O(s * log(s)), where s is the minimum of (sequence1 length, sequence2 length)
public class LongestCommonSubsequenceDistinctChars {

    private static String computeLCSDistinctChars(String string1, String string2) {
        Map<Character, Integer> characterToIndexMap = computeCharacterToIndexMap(string2);
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < string1.length(); i++) {
            char character = string1.charAt(i);
            Integer string2Index = characterToIndexMap.get(character);
            if (string2Index != null) {
                indexes.add(string2Index);
            }
        }

        Integer[] indexesArray = indexes.toArray(new Integer[0]);
        List<Integer> longestIncreasingSubsequence = longestIncreasingSubsequence(indexesArray);
        StringBuilder longestCommonSubsequence = new StringBuilder();
        for (int index : longestIncreasingSubsequence) {
            longestCommonSubsequence.append(string2.charAt(index));
        }
        return longestCommonSubsequence.toString();
    }

    private static Map<Character, Integer> computeCharacterToIndexMap(String string) {
        Map<Character, Integer> characterToIndexMap = new HashMap<>();
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            characterToIndexMap.put(character, i);
        }
        return characterToIndexMap;
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
        String string1 = "ABCDEFG";
        String string2 = "ADKJFRZ@.CB";

        String longestCommonSubsequence = computeLCSDistinctChars(string1, string2);
        System.out.println("LCS: " + longestCommonSubsequence);
        System.out.println("Expected: ADF");
    }
}
