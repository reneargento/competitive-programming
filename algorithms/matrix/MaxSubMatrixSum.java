package algorithms.matrix;

/**
 * Created by Rene Argento on 11/12/18.
 */
// Finds maximum sum in a k x k sub-matrix
// Based on https://www.techiedelight.com/find-maximum-sum-submatrix-in-given-matrix/
public class MaxSubMatrixSum {

    private static class Cell {
        private int row;
        private int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static Cell findMaxSumSubMatrix(long matrix[][], int k) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        // Pre-process the input matrix such that sum[i][j] stores sum of elements in matrix from (0, 0) to (i, j)
        long[][] sum = new long[rows][columns];
        sum[0][0] = matrix[0][0];

        // Pre-process first row
        for (int j = 1; j < columns; j++) {
            sum[0][j] = matrix[0][j] + sum[0][j - 1];
        }

        // Pre-process first column
        for (int i = 1; i < rows; i++) {
            sum[i][0] = matrix[i][0] + sum[i - 1][0];
        }

        // Pre-process the rest of the matrix
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                sum[i][j] = matrix[i][j] + sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1];
            }
        }

        long total, max = Integer.MIN_VALUE;
        Cell result = null;

        // Find maximum sum sub-matrix

        // Start from cell (k - 1, k - 1) and consider each submatrix of size k x k
        for (int i = k - 1; i < rows; i++) {
            for (int j = k - 1; j < columns; j++) {
                // Note (i, j) is bottom right corner coordinates of square sub-matrix of size k

                total = sum[i][j];

                if (i - k >= 0) {
                    total = total - sum[i - k][j];
                }

                if (j - k >= 0) {
                    total = total - sum[i][j - k];
                }

                if (i - k >= 0 && j - k >= 0) {
                    total = total + sum[i - k][j - k];
                }

                if (total > max) {
                    max = total;
                    result = new Cell(i, j);
                }
            }
        }

        // Returns coordinates of bottom right corner of sub-matrix
        return result;
    }

    public static void main(String[] args) {
        // 5 x 5 matrix
        long[][] matrix =
                {
                        { 3, -4, 6, -5, 1 },
                        { 1, -2, 8, -4, -2 },
                        { 3, -8, 9, 3, 1 },
                        { -7, 3, 4, 2, 7 },
                        { -3, 7, -5, 7, -6 }
                };

        // Sub-matrix size
        int k = 3;

        // cell contains bottom right corner coordinates of sub-matrix
        Cell cell = findMaxSumSubMatrix(matrix, k);

        System.out.println("Max submatrix top row: " + (cell.row - k + 1));
        System.out.println("Max submatrix top column: " + (cell.column - k + 1));

        // Print maximum sum sub-matrix
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                int row = i + cell.row - k + 1;
                int column = j + cell.column - k + 1;
                System.out.printf("%3d", matrix[row][column]);
            }
            System.out.println();
        }
    }

}
