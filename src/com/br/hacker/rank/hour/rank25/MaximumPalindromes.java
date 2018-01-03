package com.br.hacker.rank.hour.rank25;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by Rene Argento on 02/01/18.
 */
//TODO
// https://www.hackerrank.com/contests/hourrank-25/challenges/maximum-palindromes/problem
public class MaximumPalindromes {

    private static int MOD = 1000000007;
    private static String string;

    private static void initialize(String string) {
        // This function is called once before all queries.
        MaximumPalindromes.string = string;
    }

    private static int answerQuery(int left, int right) {
        // Return the answer for this query modulo 1000000007.
        return longestPalindromeSubsequence(string.substring(left - 1, right));
    }

    // Based on https://www.geeksforgeeks.org/dynamic-programming-set-12-longest-palindromic-subsequence/
    // Returns the length of the longest palindromic subsequence in sequence
    private static int longestPalindromeSubsequence(String sequence) {
        int sequenceSize = sequence.length();
        int i, j, cl;
        int dp[][] = new int[sequenceSize][sequenceSize];  // Create a table to store results of subproblems

        // Strings of length 1 are palindrome of length 1
        for (i = 0; i < sequenceSize; i++) {
            dp[i][i] = 1;
        }

        // Build the table. Note that the lower diagonal values of table are
        // useless and not filled in the process. The values are filled in a
        // manner similar to Matrix Chain Multiplication DP solution (See
        // https://www.geeksforgeeks.org/archives/15553). cl is length of
        // substring
        for (cl = 2; cl <= sequenceSize; cl++) {
            for (i = 0; i < sequenceSize - cl + 1; i++) {
                j = i + cl - 1;

                if (sequence.charAt(i) == sequence.charAt(j) && cl == 2)
                    dp[i][j] = 2;
                else if (sequence.charAt(i) == sequence.charAt(j))
                    dp[i][j] = dp[i+1][j-1] + 2;
                else
                    dp[i][j] = Math.max(dp[i][j-1], dp[i+1][j]);

                dp[i][j] = dp[i][j];
            }
        }

        return dp[0][sequenceSize-1];
    }

    // Based on https://www.geeksforgeeks.org/find-number-distinct-palindromic-sub-strings-given-string/
    // Function to print all distinct palindrome sub-strings of s
    private static void allDistinctPalindromeSubstrings(String s) {
        //map<string, int> m;
        TreeMap<String , Integer> map = new TreeMap<>();
        int n = s.length();

        // table for storing results (2 rows for odd-
        // and even-length palindromes
        int[][] R = new int[2][n+1];

        // Find all sub-string palindromes from the
        // given input string insert 'guards' to
        // iterate easily over s
        s = "@" + s + "#";

        for (int j = 0; j <= 1; j++)
        {
            int rp = 0;   // length of 'palindrome radius'
            R[j][0] = 0;

            int i = 1;
            while (i <= n)
            {
                //  Attempt to expand palindrome centered
                // at i
                while (s.charAt(i - rp - 1) == s.charAt(i +
                        j + rp))
                    rp++;  // Incrementing the length of
                // palindromic radius as and
                // when we find vaid palindrome

                // Assigning the found palindromic length
                // to odd/even length array
                R[j][i] = rp;
                int k = 1;
                while ((R[j][i - k] != rp - k) && (k < rp))
                {
                    R[j][i + k] = Math.min(R[j][i - k],
                            rp - k);
                    k++;
                }
                rp = Math.max(rp - k,0);
                i += k;
            }
        }

        // remove 'guards'
        s = s.substring(1, s.length()-1);

        // Put all obtained palindromes in a hash map to
        // find only distinct palindromess
        map.put(s.substring(0,1), 1);

        for (int i = 1; i < n; i++)
        {
            for (int j = 0; j <= 1; j++)
                for (int rp = R[j][i]; rp > 0; rp--)
                    map.put(s.substring(i - rp - 1,  i - rp - 1
                            + 2 * rp + j), 1);

            map.put(s.substring(i, i + 1), 1);
        }

        // printing all distinct palindromes from
        // hash map
        System.out.println("Below are " + (map.size()) + " palindrome sub-strings");

        int count = 0;
        int longestLength = 0;

        for (Map.Entry<String, Integer> ii : map.entrySet()) {
            System.out.println(ii.getKey());
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String string = in.next();
        initialize(string);

        int queries = in.nextInt();

        for(int query = 0; query < queries; query++){
            int left = in.nextInt();
            int right = in.nextInt();

            int result = answerQuery(left, right);
            System.out.println(result);
        }
        in.close();
    }

}
