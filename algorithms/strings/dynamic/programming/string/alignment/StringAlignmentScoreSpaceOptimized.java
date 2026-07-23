package algorithms.strings.dynamic.programming.string.alignment;

// Time complexity: O(n * m), where n is the length of string1 and m is the length of string2
// Space complexity: O(min(n, m)), using the smallest string as string2
public class StringAlignmentScoreSpaceOptimized {

    private static int stringAlignmentSpaceOptimized(String string1, String string2) {
        if (string1 == null || string2 == null) {
            return 0;
        }

        if (string1.length() < string2.length()) {
            String aux = string1;
            string1 = string2;
            string2 = aux;
        }
        int[][] dp = new int[2][string2.length() + 1];

        // Base cases
        for (int j = 1; j < dp[0].length; j++) {
            dp[0][j] = j * -1;
        }

        for (int i = 1; i <= string1.length(); i++) {
            dp[1][0] = i * -1;
            for (int j = 1; j < dp[0].length; j++) {
                // Match: 2 points, mismatch: -1 point
                dp[1][j] = dp[0][j - 1] + (string1.charAt(i - 1) == string2.charAt(j - 1) ? 2 : -1);
                dp[1][j] = Math.max(dp[1][j], dp[0][j] - 1);
                dp[1][j] = Math.max(dp[1][j], dp[1][j - 1] - 1);
            }

            int[] aux = dp[0];
            dp[0] = dp[1];
            dp[1] = aux;
        }
        return dp[0][string2.length()];
    }

    public static void main(String[] args) {
        String string1 = "ACAATCC";
        String string2 = "AGCATGC";
        int alignmentScore = stringAlignmentSpaceOptimized(string1, string2);
        System.out.println("Maximum alignment score: " + alignmentScore);
        System.out.println("Expected: 7");
    }
}
