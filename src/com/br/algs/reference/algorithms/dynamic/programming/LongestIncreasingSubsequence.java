package com.br.algs.reference.algorithms.dynamic.programming;

/**
 * Created by Rene Argento on 28/12/16.
 */
//Based on http://www.geeksforgeeks.org/dynamic-programming-set-3-longest-increasing-subsequence/

import java.util.Stack;

/**
 * The Longest Increasing Subsequence (LIS) problem is to find the length of the longest subsequence
 * of a given sequence such that all elements of the subsequence are sorted in increasing order.
 * For example, the length of LIS for
 * {10, 22, 9, 33, 21, 50, 41, 60, 80} is 6 and LIS is {10, 22, 33, 50, 60, 80}.
 */
public class LongestIncreasingSubsequence {

    // O(n^2)
    private static int longestIncreasingSubsequence(int[] sequence) {
        //Base cases
        if (sequence == null) {
            return 0;
        }
        if (sequence.length == 1) {
            return 1;
        }

        int[] dp = new int[sequence.length];
        int[] previous = new int[sequence.length];

        for (int i = 0; i < dp.length; i++) {
            dp[i] = 1;
            previous[i] = Integer.MIN_VALUE;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (sequence[j] < sequence[i]
                        && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;

                    previous[i] = j; // Used to reconstruct sequence later
                }
            }
        }

        int longestIncSubsequenceLength = dp[0];
        int elementIndexInSequence = 0; // Used to reconstruct sequence later

        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > longestIncSubsequenceLength) {
                longestIncSubsequenceLength = dp[i];
                elementIndexInSequence = i;
            }
        }

        int currentNumberInSequence = sequence[elementIndexInSequence];
        Stack<Integer> stack = new Stack<>();

        System.out.print("Sequence found: ");
        while (currentNumberInSequence != Integer.MIN_VALUE) {
            stack.push(currentNumberInSequence);

            elementIndexInSequence = previous[elementIndexInSequence];
            if (elementIndexInSequence == Integer.MIN_VALUE) {
                break;
            }
            currentNumberInSequence = sequence[elementIndexInSequence];
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }

        System.out.println();
        return longestIncSubsequenceLength;
    }

    public static void main(String[] args) {
        int[] sequence1 = {3, 10, 2, 1, 20};
        int[] sequence2 = {3, 2};
        int[] sequence3 = {50, 3, 10, 7, 40, 80};

        int longestSubsequence1 = longestIncreasingSubsequence(sequence1);
        System.out.println("Expected sequence: 3, 10, 20\n");
        int longestSubsequence2 = longestIncreasingSubsequence(sequence2);
        System.out.println("Expected sequence: 3\n");
        int longestSubsequence3 = longestIncreasingSubsequence(sequence3);
        System.out.println("Expected sequence: 3, 10, 40, 80\n");

        System.out.println("Longest subsequence 1: " + longestSubsequence1 + " Expected: 3");
        System.out.println("Longest subsequence 2: " + longestSubsequence2 + " Expected: 1");
        System.out.println("Longest subsequence 3: " + longestSubsequence3 + " Expected: 4");
    }
}
