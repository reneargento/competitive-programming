package com.br.algs.reference.algorithms.strings.dynamic.programming;

/**
 * Created by Rene Argento on 28/12/16.
 */
// Based on http://www.geeksforgeeks.org/dynamic-programming-set-4-longest-common-subsequence/
// And http://www.techiedelight.com/longest-common-subsequence-finding-lcs/

import java.util.*;

/**
 * Given two sequences, find the length of longest subsequence present in both of them.
 * A subsequence is a sequence that appears in the same relative order,
 * but not necessarily contiguous.
 * For example, “abc”, “abg”, “bdf”, “aeg”, ‘”acefg”, .. etc are subsequences
 * of “abcdefg”. So a string of length n has 2^n different possible subsequences.
 *
 * It is a classic computer science problem, the basis of diff
 * (a file comparison program that outputs the differences between two files), and has applications in bioinformatics.

 Examples:
 LCS for input Sequences “ABCDGH” and “AEDFHR” is “ADH” of length 3.
 LCS for input Sequences “AGGTAB” and “GXTXAYB” is “GTAB” of length 4.
 */
public class LongestCommonSubsequence {

    private static int dp[][];

    private static int longestCommonSubsequence(String sequence1, String sequence2) {
        if(sequence1 == null || sequence2 == null) {
            return 0;
        }

        // int[][] dp = new int[sequence1.length() + 1][sequence2.length() + 1];
        dp = new int[sequence1.length() + 1][sequence2.length() + 1];

        for(int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[sequence1.length()][sequence2.length()];
    }

    private static void printSubsequence(int[][] dp, String sequence1, String sequence2) {
        Stack<Character> stack = new Stack<>();

        int i = sequence1.length();
        int j = sequence2.length();

        while (i > 0 && j > 0) {
            if (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) {
                stack.push(sequence1.charAt(i - 1));

                i--;
                j--;
            } else {
                if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }
        }

        System.out.println("Subsequence found: ");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
        System.out.println();
    }

    // Print all LCSs
    private static Set<String> getAllLCS(String sequence1, String sequence2) {
        List<String> allLCSList = getAllLCS(sequence1, sequence2, sequence1.length(), sequence2.length());
        return new HashSet<>(allLCSList);
    }

    private static List<String> getAllLCS(String sequence1, String sequence2, int sequenceIndex1, int sequenceIndex2) {

        // If we have reached the end, return an empty String
        if (sequenceIndex1 == 0 || sequenceIndex2 == 0) {
            String emptyString = "";
            List<String> emptyList = new ArrayList<>();
            emptyList.add(emptyString);

            return emptyList;
        }

        if (sequence1.charAt(sequenceIndex1 - 1) == sequence2.charAt(sequenceIndex2 - 1)) {
            List<String> allLCS = getAllLCS(sequence1, sequence2, sequenceIndex1 - 1, sequenceIndex2 - 1);
            List<String> appendedLCS = new ArrayList<>();

            for(String lcs : allLCS) {
                appendedLCS.add(lcs + sequence1.charAt(sequenceIndex1 - 1));
            }

            return appendedLCS;
        }

        if (dp[sequenceIndex1 - 1][sequenceIndex2] > dp[sequenceIndex1][sequenceIndex2 - 1]) {
            return getAllLCS(sequence1, sequence2, sequenceIndex1 - 1, sequenceIndex2);
        }

        if (dp[sequenceIndex1][sequenceIndex2 - 1] > dp[sequenceIndex1 - 1][sequenceIndex2]) {
            return getAllLCS(sequence1, sequence2, sequenceIndex1, sequenceIndex2 - 1);
        }

        // Else, get and return both merged
        List<String> left = getAllLCS(sequence1, sequence2, sequenceIndex1 - 1, sequenceIndex2);
        List<String> top = getAllLCS(sequence1, sequence2, sequenceIndex1, sequenceIndex2 - 1);

        // Merge lists
        top.addAll(left);

        return top;
    }

    public static void main(String[] args) {
        String sequence1 = "ABCDGH";
        String sequence2 = "AEDFHR";

        int longestSubsequence1 = longestCommonSubsequence(sequence1, sequence2);
        System.out.println("Longest subsequence 1: " + longestSubsequence1 + " Expected: 3\n");

        printSubsequence(dp, sequence1, sequence2);
        System.out.println("Expected sequence: ADH\n");


        String sequence3 = "AGGTAB";
        String sequence4 = "GXTXAYB";

        int longestSubsequence2 = longestCommonSubsequence(sequence3, sequence4);
        System.out.println("Longest subsequence 2: " + longestSubsequence2 + " Expected: 4\n");

        printSubsequence(dp, sequence3, sequence4);
        System.out.println("Expected sequence: GTAB\n");


        // Print all LCS
        String sequence5 = "AGTGATG";
        String sequence6 = "GTTAG";

        longestCommonSubsequence(sequence5, sequence6);
        Set<String> allLongestSubsequences = getAllLCS(sequence5, sequence6);

        System.out.println("All longest subsequences:");
        for(String lcs : allLongestSubsequences) {
            System.out.println(lcs);
        }

        System.out.println("Expected sequences: GTTG and GTAG");
    }
}
