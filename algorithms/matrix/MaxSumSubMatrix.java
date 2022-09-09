package algorithms.matrix;

/**
 * Created by Rene Argento on 14/06/20.
 */
// Finds the max sum sub-matrix in O(r^2 * c) runtime, where r is the number of rows in the matrix
// and c is the number of columns in the matrix
// O(c) space
public class MaxSumSubMatrix {

    public static class SubMatrix {
        private int startRow;
        private int endRow;
        private int startColumn;
        private int endColumn;
        private int sum;

        public SubMatrix(int startRow, int endRow, int startColumn, int endColumn, int sum) {
            this.startRow = startRow;
            this.endRow = endRow;
            this.startColumn = startColumn;
            this.endColumn = endColumn;
            this.sum = sum;
        }

        @Override
        public String toString() {
            return "(" + startRow + ", " + startColumn + ") "
                    + "(" + startRow + ", " + endColumn + ") "
                    + "(" + endRow + ", " + startColumn + ") "
                    + "(" + endRow + ", " + endColumn + ") "
                    + "sum: " + sum;
        }
    }

    private static class Subarray {
        private int index1;
        private int index2;
        private int sum;

        public Subarray(int index1, int index2, int sum) {
            this.index1 = index1;
            this.index2 = index2;
            this.sum = sum;
        }

        public int getSum() {
            return sum;
        }
    }

    // O(r^2 * c) runtime, where r is the number of rows in the matrix and c is the number of columns in the matrix
    // O(c) space
    public static SubMatrix getMaxSubMatrix(int[][] matrix) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;
        SubMatrix maxSubMatrix = null;

        for (int startRow = 0; startRow < rowCount; startRow++) {
            int[] cumulativeColumnSum = new int[columnCount];

            for (int endRow = startRow; endRow < rowCount; endRow++) {
                for (int column = 0; column < columnCount; column++) {
                    cumulativeColumnSum[column] += matrix[endRow][column];
                }

                Subarray currentMaxSubarray = getMaxSumSubarray(cumulativeColumnSum);
                if (maxSubMatrix == null || maxSubMatrix.sum < currentMaxSubarray.getSum()) {
                    int startColumn = currentMaxSubarray.index1;
                    int endColumn = currentMaxSubarray.index2;
                    int sum = currentMaxSubarray.getSum();
                    maxSubMatrix = new SubMatrix(startRow, endRow, startColumn, endColumn, sum);
                }
            }
        }
        return maxSubMatrix;
    }

    // Kadane's algorithm
    // O(n) runtime, where n is the length of the array
    private static Subarray getMaxSumSubarray(int[] array) {
        Subarray maxSumSubarray = null;
        int currentSum = 0;
        int startIndex = 0;

        for (int i = 0; i < array.length; i++) {
            currentSum += array[i];

            if (maxSumSubarray == null || currentSum > maxSumSubarray.getSum()) {
                maxSumSubarray = new Subarray(startIndex, i, currentSum);
            }

            if (currentSum < 0) {
                startIndex = i + 1;
                currentSum = 0;
            }
        }
        return maxSumSubarray;
    }

    public static void main(String[] args) {
        int[][] matrix1 = {
                {2, -1, 2, -4},
                {-5, -1, 2, -2},
                {-2, 5, 2, -1},
                {1, -1, 2, 7}
        };
        SubMatrix subMatrix1 = getMaxSubMatrix(matrix1);
        System.out.println("Max sub-matrix: " + subMatrix1);
        System.out.println("Expected: (2, 1) (2, 3) (3, 1) (3, 3) sum: 14");

        int[][] matrix2 = {
                {2, -1, 8, -4},
                {-5, -1, 2, -1},
                {-2, -3, 2, -1},
                {1, -1, 2, 7}
        };
        SubMatrix subMatrix2 = getMaxSubMatrix(matrix2);
        System.out.println("\nMax sub-matrix: " + subMatrix2);
        System.out.println("Expected: (0, 2) (0, 3) (3, 2) (3, 3) sum: 15");
    }
}
