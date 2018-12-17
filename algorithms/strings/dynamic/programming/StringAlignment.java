package algorithms.strings.dynamic.programming;

/**
 * Created by Rene Argento on 15/12/18.
 */
// Also known as the edit distance or the Levenshtein distance problem between 2 strings.
public class StringAlignment {

    // Needleman-Wunsch’s algorithm
    private static int stringAlignment(String string1, String string2) {
        if(string1 == null || string2 == null) {
            return 0;
        }

        int[][] dp = new int[string1.length() + 1][string2.length() + 1];

        // Base cases
        for (int i = 1; i <= string1.length(); i++) {
            dp[i][0] = i * -1;
        }
        for (int j = 1; j <= string2.length(); j++) {
            dp[0][j] = j * -1;
        }

        for(int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                // Match: 2 points, mismatch: -1 point
                dp[i][j] = dp[i - 1][j - 1] + (string1.charAt(i - 1) == string2.charAt(j - 1) ? 2 : -1);
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] - 1);
                dp[i][j] = Math.max(dp[i][j], dp[i][j - 1] - 1);
            }
        }

        return dp[string1.length()][string2.length()];
    }

    public static void main(String[] args) {
        String string1 = "ACAATCC";
        String string2 = "AGCATGC";
        int alignment = stringAlignment(string1, string2);
        System.out.println("Maximum alignment score: " + alignment + " Expected: 7");
    }
}
