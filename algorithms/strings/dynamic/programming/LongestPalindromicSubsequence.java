package algorithms.strings.dynamic.programming;

import java.util.Stack;

/**
 * Created by Rene Argento on 16/12/18.
 */
public class LongestPalindromicSubsequence {

    public static int longestPalindromicSubsequenceLength(String string) {

        int length = string.length();
        int[][] dp = new int[length][length];

        // Base case - strings of length 1 are palindromes of length 1
        for (int i = 0; i < dp.length; i++) {
            dp[i][i] = 1;
        }

        for (int substringLength = 2; substringLength <= length; substringLength++) {
            for (int left = 0; left < length - substringLength + 1; left++) {
                int right = left + substringLength - 1;

                if (string.charAt(left) == string.charAt(right) && substringLength == 2) {
                    dp[left][right] = 2;
                } else if (string.charAt(left) == string.charAt(right)) {
                    dp[left][right] = dp[left + 1][right - 1] + 2;
                } else {
                    dp[left][right] = Math.max(dp[left][right - 1], dp[left + 1][right]);
                }
            }
        }

        return dp[0][length - 1];
    }

    public static String longestPalindromicSubsequence(String string) {
        String reverse = new StringBuilder(string).reverse().toString();
        return longestCommonSubsequence(string, reverse);
    }

    private static String longestCommonSubsequence(String string1, String string2) {
        int[][] dp = new int[string1.length() + 1][string2.length() + 1];

        for(int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        Stack<Character> stack = new Stack<>();

        int i = string1.length();
        int j = string2.length();

        while (i > 0 && j > 0) {
            if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                stack.push(string1.charAt(i - 1));

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

        StringBuilder longestCommonSubsequence = new StringBuilder();
        while (!stack.isEmpty()) {
            longestCommonSubsequence.append(stack.pop());
        }
        return longestCommonSubsequence.toString();
    }

    public static void main(String[] args) {
        String string1 = "ADAM";
        int lpsLength1 = longestPalindromicSubsequenceLength(string1);
        String lps1 = longestPalindromicSubsequence(string1);
        System.out.println("Longest palindromic subsequence length: " + lpsLength1 + " Expected: 3");
        System.out.println("Longest palindromic subsequence: " + lps1 + " Expected: ADA");

        String string2 = "GEEKSFORGEEKS";
        int lpsLength2 = longestPalindromicSubsequenceLength(string2);
        String lps2 = longestPalindromicSubsequence(string2);
        System.out.println("\nLongest palindromic subsequence length: " + lpsLength2 + " Expected: 5");
        System.out.println("Longest palindromic subsequence: " + lps2 + " Expected: EEGEE");

        String string3 = "BBABCBCAB";
        int lpsLength3 = longestPalindromicSubsequenceLength(string3);
        String lps3 = longestPalindromicSubsequence(string3);
        System.out.println("\nLongest palindromic subsequence length: " + lpsLength3 + " Expected: 7");
        System.out.println("Longest palindromic subsequence: " + lps3 + " Expected: BACBCAB");
    }
}
