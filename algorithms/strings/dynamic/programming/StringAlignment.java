package algorithms.strings.dynamic.programming;

// Also known as the edit distance or the Levenshtein distance problem between 2 strings.
// Runtime O(s1 * s2), where s1 is the first string length and s2 is the second string length
// Computes both the maximum score and also the alignment itself
public class StringAlignment {

    private static class Result {
        String optimalAlignmentA;
        String optimalAlignmentB;
        int score;

        public Result(String optimalAlignmentA, String optimalAlignmentB, int score) {
            this.optimalAlignmentA = optimalAlignmentA;
            this.optimalAlignmentB = optimalAlignmentB;
            this.score = score;
        }
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    // Needleman-Wunsch’s algorithm
    private static Result stringAlignment(String string1, String string2) {
        if (string1 == null || string2 == null) {
            return new Result(string1, string2, 0);
        }
        Cell[][] previous = new Cell[string1.length() + 1][string2.length() + 1];
        int[][] dp = computeDpTable(string1, string2, previous);
        int alignmentScore = dp[string1.length()][string2.length()];

        StringBuilder optimalAlignmentA = new StringBuilder();
        StringBuilder optimalAlignmentB = new StringBuilder();

        int column = dp[0].length - 1;
        for (int row = dp.length - 1; row > 0; row--) {
            Cell previousCell = previous[row][column];
            if (previousCell.row == row - 1 && previousCell.column == column - 1) {
                boolean isMismatch = dp[row - 1][column - 1] - 1 == dp[row][column];
                if (isMismatch) {
                    optimalAlignmentA.append("]");
                    optimalAlignmentB.append("]");
                }
                optimalAlignmentA.append(string1.charAt(row - 1));
                optimalAlignmentB.append(string2.charAt(column - 1));
                if (isMismatch) {
                    optimalAlignmentA.append("[");
                    optimalAlignmentB.append("[");
                }
                column--;
            } else if (previousCell.row == row - 1) {
                optimalAlignmentA.append(string1.charAt(row - 1));
                optimalAlignmentB.append("_");
            } else {
                optimalAlignmentA.append("_");
                optimalAlignmentB.append(string2.charAt(column - 1));
                column--;
                row++;
            }
        }
        return new Result(optimalAlignmentA.reverse().toString(), optimalAlignmentB.reverse().toString(), alignmentScore);
    }

    private static int[][] computeDpTable(String string1, String string2, Cell[][] previous) {
        int[][] dp = new int[string1.length() + 1][string2.length() + 1];

        // Base cases
        for (int i = 1; i <= string1.length(); i++) {
            dp[i][0] = i * -1;
        }
        for (int j = 1; j <= string2.length(); j++) {
            dp[0][j] = j * -1;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                // Match: 2 points, mismatch: -1 point
                int score1 = dp[i - 1][j - 1] + (string1.charAt(i - 1) == string2.charAt(j - 1) ? 2 : -1);
                int score2 = dp[i - 1][j] - 1;
                int score3 = dp[i][j - 1] - 1;

                if (score1 >= score2 && score1 >= score3) {
                    dp[i][j] = score1;
                    previous[i][j] = new Cell(i - 1, j - 1);
                } else if (score2 >= score1 && score2 >= score3) {
                    dp[i][j] = score2;
                    previous[i][j] = new Cell(i - 1, j);
                } else {
                    dp[i][j] = score3;
                    previous[i][j] = new Cell(i, j - 1);
                }
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        String string1 = "ACAATCC";
        String string2 = "AGCATGC";
        Result optimalAlignment = stringAlignment(string1, string2);
        System.out.println("Maximum alignment score: " + optimalAlignment.score + " Expected: 7");

        System.out.println("\nOptimal aligment:");
        System.out.println("A = " + optimalAlignment.optimalAlignmentA);
        System.out.println("B = " + optimalAlignment.optimalAlignmentB);

        System.out.println("Expected:");
        System.out.println("A = A_CAAT[C]C");
        System.out.println("B = AGC_AT[G]C");
    }
}
