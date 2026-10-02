package algorithms.strings;

import java.util.Arrays;

/**
 * Created by Rene Argento on 20/09/26.
 */
// Counts the number of palindromic substrings in the given string.
// Time complexity: O(N^2)
public class CountPalindromicSubstrings {

    private static int countPalindromicSubstrings(String string) {
        int palindromicCount = 0;
        int[][] dp = new int[string.length()][string.length()];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        for (int i = 0; i < string.length(); i++) {
            for (int j = i; j < string.length(); j++) {
                if (isPalindrome(string, dp, i, j) > 0) {
                    palindromicCount++;
                }
            }
        }
        return palindromicCount;
    }

    private static int isPalindrome(String string, int[][] dp, int start, int end) {
        if (start == end) {
            return 1;
        }
        if (start + 1 == end) {
            return string.charAt(start) == string.charAt(end) ? 1 : 0;
        }
        if (dp[start][end] != -1) {
            return dp[start][end];
        }

        int palindromicCount = 0;
        if (string.charAt(start) == string.charAt(end)) {
            palindromicCount = isPalindrome(string, dp, start + 1, end - 1);
        }
        dp[start][end] = palindromicCount;
        return palindromicCount;
    }

    public static void main() {
        String string1 = "abacaba";
        int palindromicCount1 = countPalindromicSubstrings(string1);
        System.out.println("Palindromic substrings: " + palindromicCount1);
        System.out.println("Expected: 12");

        String string2 = "aabbaa";
        int palindromicCount2 = countPalindromicSubstrings(string2);
        System.out.println("\nPalindromic substrings: " + palindromicCount2);
        System.out.println("Expected: 11");
    }
}
