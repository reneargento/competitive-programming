package algorithms.array;

/**
 * Created by Rene Argento on 07/04/22.
 */
// Computes the sum of a rectangle in a 2D array using 2D prefix sum.
// The values must be static (no updates). For dynamic values (with updates) use a Fenwick Tree.
// Based on https://www.geeksforgeeks.org/prefix-sum-2d-array/
public class RangeSum2D {

    public static void main(String[] args) {
        long[][] matrix = {
                { 1, 2, 3, 4 },
                { 5, 6, 7, 8 },
                { 9, 10, 100, 200 },
        };

        long[][] cumulativeSum = computePrefixSum(matrix);

        long rangeSum1 = computeRangeSum(matrix, 0, 0, 2, 3, cumulativeSum);
        System.out.println("Range sum: " + rangeSum1 + " Expected: 355");

        long rangeSum2 = computeRangeSum(matrix, 1, 1, 2, 2, cumulativeSum);
        System.out.println("Range sum: " + rangeSum2 + " Expected: 123");

        long rangeSum3 = computeRangeSum(matrix, 2, 3, 2, 3, cumulativeSum);
        System.out.println("Range sum: " + rangeSum3 + " Expected: 200");
    }

    private static long computeRangeSum(long[][] matrix, int row1, int column1, int row2, int column2,
                                        long[][] cumulativeSum) {
        if (isInvalidIndex(row1, matrix.length)
                || isInvalidIndex(row2, matrix.length)
                || isInvalidIndex(column1, matrix[row1].length)
                || isInvalidIndex(column2, matrix[row2].length)) {
            return -1;
        }

        int minRow = Math.min(row1, row2);
        int minColumn = Math.min(column1, column2);
        int maxRow = Math.max(row1, row2);
        int maxColumn = Math.max(column1, column2);

        long rangeSum = cumulativeSum[maxRow][maxColumn];
        if (minRow > 0) {
            rangeSum -= cumulativeSum[minRow - 1][maxColumn];
        }
        if (minColumn > 0) {
            rangeSum -= cumulativeSum[maxRow][minColumn - 1];
        }
        if (minRow > 0 && minColumn > 0) {
            rangeSum += cumulativeSum[minRow - 1][minColumn - 1];
        }
        return rangeSum;
    }

    private static boolean isInvalidIndex(int index, int maxValue) {
        return index < 0 && index >= maxValue;
    }

    private static long[][] computePrefixSum(long[][] matrix) {
        long[][] cumulativeSum = new long[matrix.length][matrix[0].length];
        cumulativeSum[0][0] = matrix[0][0];

        for (int row = 1; row < matrix.length; row++) {
            cumulativeSum[row][0] += cumulativeSum[row - 1][0] + matrix[row][0];
        }
        for (int column = 1; column < matrix[0].length; column++) {
            cumulativeSum[0][column] += cumulativeSum[0][column - 1] + matrix[0][column];
        }

        for (int row = 1; row < matrix.length; row++) {
            for (int column = 1; column < matrix[0].length; column++) {
                cumulativeSum[row][column] = cumulativeSum[row - 1][column] + cumulativeSum[row][column - 1] -
                        cumulativeSum[row - 1][column - 1] + matrix[row][column];
            }
        }
        return cumulativeSum;
    }
}
