package datastructures.tree.fenwick;

/**
 * Created by Rene Argento on 23/09/17.
 */
public class FenwickTree2DRangeSum {
    private final long[][] fenwickTree;
    private final int rows;
    private final int columns;

    public FenwickTree2DRangeSum(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        fenwickTree = new long[rows + 1][columns + 1];
    }

    // Based on https://codeforces.com/blog/entry/81796
    public FenwickTree2DRangeSum(long[][] values) {
        int rowsSize = values.length - 1;
        int columnsSize = values[0].length - 1;
        rows = rowsSize;
        columns = columnsSize;
        fenwickTree = new long[rowsSize + 1][columnsSize + 1];

        for (int i = 1; i <= rowsSize; i++) {
            for (int j = 1; j <= columnsSize; j++) {
                fenwickTree[i][j] = values[i][j];
            }
        }

        for (int i = 1; i <= rowsSize; i++) {
            for (int j = 1; j <= columnsSize; j++) {
                if (i + lsOne(i) <= rowsSize) {
                    fenwickTree[i + lsOne(i)][j] += fenwickTree[i][j];
                }
            }
        }

        for (int i = 1; i <= rowsSize; i++) {
            for (int j = 1; j <= columnsSize; j++) {
                if (j + lsOne(j) <= columnsSize) {
                    fenwickTree[i][j + lsOne(j)] += fenwickTree[i][j];
                }
            }
        }
    }

    public void pointUpdate(int row, int column, int value) {
        for (int i = row; i <= rows; i += lsOne(i)) {
            for (int j = column; j <= columns; j += lsOne(j)) {
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

        // Rectangular region between lower row and left column x upper row and right column
        return pointQuery(maxRow, maxColumn) + pointQuery(minRow - 1, minColumn - 1) -
                pointQuery(minRow - 1, maxColumn) - pointQuery(maxRow, minColumn - 1);
    }

    private int pointQuery(int row, int column) {
        int sum = 0;
        for (int i = row; i > 0; i -= lsOne(i)) {
            for (int j = column; j > 0; j -= lsOne(j)) {
                sum += fenwickTree[i][j];
            }
        }
        return sum;
    }

    private int lsOne(int value) {
        return value & (-value);
    }

    public static void main(String[] args) {
        long[][] values = {
                {0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0},
                {0, 0, 2, 2, 0},
                {0, 0, 3, 3, 0}
        };

        FenwickTree2DRangeSum fenwickTree2DPURQ = new FenwickTree2DRangeSum(values);
        System.out.println("Fenwick Tree");
        printFenwickTree(fenwickTree2DPURQ);
        System.out.println("Range query: " + fenwickTree2DPURQ.rangeSum(1, 2, 3, 4)
                + " expected: 12");

        fenwickTree2DPURQ.pointUpdate(1, 2, 3);
        fenwickTree2DPURQ.pointUpdate(2, 1, 1);
        fenwickTree2DPURQ.pointUpdate(3, 4, 5);
        System.out.println("\nAfter updates");
        printFenwickTree(fenwickTree2DPURQ);
        System.out.println("Range query: " + fenwickTree2DPURQ.rangeSum(1, 2, 3, 4)
                + " expected: 20");
    }

    private static void printFenwickTree(FenwickTree2DRangeSum fenwickTree2DPURQ) {
        for (int row = 1; row <= fenwickTree2DPURQ.rows; row++) {
            for (int column = 1; column <= fenwickTree2DPURQ.columns; column++) {
                System.out.print(fenwickTree2DPURQ.rangeSum(row, column, row, column));

                if (column != fenwickTree2DPURQ.columns) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}