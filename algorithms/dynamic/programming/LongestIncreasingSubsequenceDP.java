package algorithms.dynamic.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rene Argento on 04/05/20.
 */
// Computes the longest increasing subsequence in O(N^2)
public class LongestIncreasingSubsequenceDP {

    public static List<Integer> longestIncreasingSubsequence(int[] array) {
        if (array == null || array.length == 0) {
            return new ArrayList<>();
        }

        int[] lis = new int[array.length];
        int[] previous = new int[array.length];
        Arrays.fill(previous, -1);

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] > array[i]
                        && lis[i] + 1 > lis[j]) {
                    lis[j] = lis[i] + 1;
                    previous[j] = i;
                }
            }
        }
        return getSequence(array, lis, previous);
    }

    private static List<Integer> getSequence(int[] array, int[] lis, int[] previous) {
        int elementIndex = 0;
        int sequenceLength = 0;

        for (int i = 0; i < lis.length; i++) {
            if (lis[i] > sequenceLength) {
                sequenceLength = lis[i];
                elementIndex = i;
            }
        }

        LinkedList<Integer> sequence = new LinkedList<>();

        while (elementIndex != -1) {
            sequence.addFirst(array[elementIndex]);
            elementIndex = previous[elementIndex];
        }

        return sequence;
    }

    public static void main(String[] args) {
        int array[] = { 10, 22, 9, 33, 21, 50, 41, 60 };
        List<Integer> longestIncreasingSubsequence = longestIncreasingSubsequence(array);

        System.out.print("LIS: ");
        for (Integer value : longestIncreasingSubsequence) {
            System.out.print(value + " ");
        }
        System.out.println("\nExpected: 10 22 33 50 60");
    }

}
