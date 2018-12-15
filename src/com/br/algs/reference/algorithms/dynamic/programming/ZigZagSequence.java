package com.br.algs.reference.algorithms.dynamic.programming;

/**
 * Created by Rene Argento on 27/12/16.
 */
// Problem: https://community.topcoder.com/stat?c=problem_statement&pm=1259&rd=4493

/**
 *
 Problem Statement

 A sequence of numbers is called a zig-zag sequence if the differences between successive numbers
 strictly alternate between positive and negative.
 The first difference (if one exists) may be either positive or negative.
 A sequence with fewer than two elements is trivially a zig-zag sequence.

 For example, 1,7,4,9,2,5 is a zig-zag sequence because the differences (6,-3,5,-7,3) are
 alternately positive and negative.
 In contrast, 1,4,7,2,5 and 1,7,4,5,5 are not zig-zag sequences, the first because
 its first two differences are positive and the second because its last difference is zero.

 Given a sequence of integers, sequence, return the length of the longest subsequence of
 sequence that is a zig-zag sequence.
 A subsequence is obtained by deleting some number of elements (possibly zero) from the original sequence,
 leaving the remaining elements in their original order.
 */
public class ZigZagSequence {

    // O(n^2)
    public static int longestZigZag(int[] sequence) {

        // Base cases
        if (sequence == null) {
            return 0;
        }
        if (sequence.length == 1){
            return 1;
        }

        int longestZigZag = 1;

        int[] dp = new int[sequence.length];

        // Initial state
        for (int i = 0; i < dp.length; i++) {
            dp[i] = 1;
        }

        boolean[] currentDifferenceIsPositive = new boolean[sequence.length];
        currentDifferenceIsPositive[0] = sequence[1] - sequence[0] > 0;

        for (int i = 1; i < sequence.length; i++) {
            for (int j = 0; j < i; j++) {
                if (i == 1 ||
                        (isZigZag(sequence, j, i, currentDifferenceIsPositive[j])
                                && dp[i] < dp[j] + 1)) {
                    dp[i] = dp[j] + 1;

                    if (dp[i] > longestZigZag) {
                        longestZigZag = dp[i];
                    }

                    if (i != 1) {
                        currentDifferenceIsPositive[i] = !currentDifferenceIsPositive[j];
                    } else {
                        currentDifferenceIsPositive[i] = currentDifferenceIsPositive[j];
                    }
                }
            }
        }

        return longestZigZag;
    }

    private static boolean isZigZag(int[] sequence, int leftIndex, int rightIndex, boolean currentDifferenceIsPositive) {
        if (currentDifferenceIsPositive) {
            return sequence[rightIndex] - sequence[leftIndex] < 0;
        } else {
            return sequence[rightIndex] - sequence[leftIndex] > 0;
        }
    }

    public static void main(String[] args) {
        int[] sequence1 = {1, 7, 4, 9, 2, 5};
        int[] sequence2 = {1, 17, 5, 10, 13, 15, 10, 5, 16, 8};
        int[] sequence3 = {44};
        int[] sequence4 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int[] sequence5 = {70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32};
        int[] sequence6 = { 374, 40, 854, 203, 203, 156, 362, 279, 812, 955,
                600, 947, 978, 46, 100, 953, 670, 862, 568, 188,
                67, 669, 810, 704, 52, 861, 49, 640, 370, 908,
                477, 245, 413, 109, 659, 401, 483, 308, 609, 120,
                249, 22, 176, 279, 23, 22, 617, 462, 459, 244 };

        int longestSubsequence1 = longestZigZag(sequence1);
        int longestSubsequence2 = longestZigZag(sequence2);
        int longestSubsequence3 = longestZigZag(sequence3);
        int longestSubsequence4 = longestZigZag(sequence4);
        int longestSubsequence5 = longestZigZag(sequence5);
        int longestSubsequence6 = longestZigZag(sequence6);

        System.out.println("Longest subsequence length 1: " + longestSubsequence1 + " Expected: 6");
        System.out.println("Longest subsequence length 2: " + longestSubsequence2 + " Expected: 7");
        System.out.println("Longest subsequence length 3: " + longestSubsequence3 + " Expected: 1");
        System.out.println("Longest subsequence length 4: " + longestSubsequence4 + " Expected: 2");
        System.out.println("Longest subsequence length 5: " + longestSubsequence5 + " Expected: 8");
        System.out.println("Longest subsequence length 6: " + longestSubsequence6 + " Expected: 36");
    }
}
