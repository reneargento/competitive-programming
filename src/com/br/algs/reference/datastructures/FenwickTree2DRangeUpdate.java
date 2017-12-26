package com.br.algs.reference.datastructures;

/**
 * Created by rene on 26/12/17.
 */
public class FenwickTree2DRangeUpdate {
    private int[][] fenwickTree;
    private int rows;
    private int columns;

    public FenwickTree2DRangeUpdate(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        fenwickTree = new int[rows + 1][columns + 1];
    }

    public void pointUpdate(int row, int column, int value) {
        for (int i = row; i <= rows; i += i & (-i)) {
            for (int j = column; j <= columns; j += j & (-j)) {
                fenwickTree[i][j] += value;
            }
        }
    }

    public void rangeUpdate(int row1, int column1, int row2, int column2, int value) {
        int minRow = Math.min(row1, row2);
        int maxRow = Math.max(row1, row2);
        int minColumn = Math.min(column1, column2);
        int maxColumn = Math.max(column1, column2);

        //Rectangular region between lower row and left column x upper row and right column
        pointUpdate(maxRow + 1, maxColumn + 1, value);
        pointUpdate(minRow, minColumn, value);
        pointUpdate(minRow, maxColumn + 1, -value);
        pointUpdate(maxRow + 1, minColumn, -value);
    }

    private int pointQuery(int row, int column) {
        int sum = 0;
        for (int i = row; i > 0; i -= i & (-i)) {
            for (int j = column; j > 0; j -= j & (-j)) {
                sum += fenwickTree[i][j];
            }
        }
        return sum;
    }
}
