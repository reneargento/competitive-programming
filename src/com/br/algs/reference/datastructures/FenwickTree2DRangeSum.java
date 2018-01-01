package com.br.algs.reference.datastructures;

/**
 * Created by rene on 23/09/17.
 */
public class FenwickTree2DRangeSum {

    private int[][] fenwickTree;
    private int rows;
    private int columns;

    public FenwickTree2DRangeSum(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        fenwickTree = new int[rows + 1][columns + 1];
    }

    public void pointUpdate(int row, int column, int value) {
        // Add 1 for 0-based index operations
        for (int i = row + 1; i <= rows; i += i & (-i)) {
            for (int j = column + 1; j <= columns; j += j & (-j)) {
                fenwickTree[i][j] += value;
            }
        }
    }

    // O(log(m) * log(n))
    public int rangeSum(int row1, int column1, int row2, int column2) {
        int minRow = Math.min(row1, row2);
        int maxRow = Math.max(row1, row2);
        int minColumn = Math.min(column1, column2);
        int maxColumn = Math.max(column1, column2);

        //Rectangular region between lower row and left column x upper row and right column
        return pointQuery(maxRow + 1, maxColumn + 1) + pointQuery(minRow, minColumn) -
                pointQuery(minRow, maxColumn + 1 ) - pointQuery(maxRow + 1, minColumn);
    }

    public int pointQuery(int row, int column) {
        int sum = 0;
        for (int i = row; i > 0; i -= i & (-i)) {
            for (int j = column; j > 0; j -= j & (-j)) {
                sum += fenwickTree[i][j];
            }
        }
        return sum;
    }
}