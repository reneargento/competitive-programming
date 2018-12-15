package algorithms.strings.dynamic.programming;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 10/01/17.
 */
// Based on https://www.quora.com/What-are-the-top-10-most-popular-dynamic-programming-problems-among-interviewers
public class LongestCommonSubstring {

    // O(n^2)
    private static List<String> longestCommonSubstringLength(String string1, String string2) {
        int maxLength = 0;
        List<String> result = null;

        if (string1 == null || string2 == null) {
            return new ArrayList<>();
        }

        int[][] dp = new int[string1.length()][string2.length()];

        for (int i = 1; i < string1.length(); i++) {
            for (int j = 1; j < string2.length(); j++) {

                if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;

                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];

                        result = new ArrayList<>();
                        String substring = string1.substring(i - maxLength, i);
                        result.add(substring);
                    } else if (dp[i][j] == maxLength && maxLength > 0) {
                        String substring = string1.substring(i - maxLength, i);
                        result.add(substring);
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String string1 = "abcde12423fg";
        String string2 = "9808nasjde12asn23";

        System.out.println("Longest common substring:");
        List<String> longestCommonSubstring = longestCommonSubstringLength(string1, string2);
        for (String substring : longestCommonSubstring) {
            System.out.print(substring);
        }

        System.out.println();
        System.out.println("Expected: de12\n");
        int length = longestCommonSubstringLength(string1, string2).get(0).length();
        System.out.println("Longest common substring length: " + length + " Expected: 4");
    }
}