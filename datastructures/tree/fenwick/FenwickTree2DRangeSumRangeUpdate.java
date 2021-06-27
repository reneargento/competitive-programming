package datastructures.tree.fenwick;

/**
 * Created by Rene Argento on 04/01/18.
 */
// Adapted from https://arxiv.org/pdf/1311.6093.pdf and https://www.codechef.com/viewsolution/6391361
public class FenwickTree2DRangeSumRangeUpdate {
    private final FenwickTree fenwickTree2DXY;
    private final FenwickTree fenwickTree2DX;
    private final FenwickTree fenwickTree2DY;
    private final FenwickTree fenwickTree2DI;
    private final int rows;
    private final int columns;

    public FenwickTree2DRangeSumRangeUpdate(int rows, int columns) {
        fenwickTree2DXY = new FenwickTree(rows, columns);
        fenwickTree2DX = new FenwickTree(rows, columns);
        fenwickTree2DY = new FenwickTree(rows, columns);
        fenwickTree2DI = new FenwickTree(rows, columns);
        this.rows = rows;
        this.columns = columns;
    }

    public void rangeUpdate(int row1, int column1, int row2, int column2, int value) {
        int minRow = Math.min(row1, row2);
        int maxRow = Math.max(row1, row2);
        int minColumn = Math.min(column1, column2);
        int maxColumn = Math.max(column1, column2);

        fenwickTree2DXY.pointUpdate(minRow, minColumn, value);
        fenwickTree2DXY.pointUpdate(minRow, maxColumn + 1, -value);
        fenwickTree2DXY.pointUpdate(maxRow + 1, minColumn, -value);
        fenwickTree2DXY.pointUpdate(maxRow + 1, maxColumn + 1, value);

        fenwickTree2DX.pointUpdate(minRow, minColumn, value * (1 - minColumn));
        fenwickTree2DX.pointUpdate(minRow, maxColumn + 1, value * maxColumn);
        fenwickTree2DX.pointUpdate(maxRow + 1, minColumn, value * (minColumn - 1));
        fenwickTree2DX.pointUpdate(maxRow + 1, maxColumn + 1, -value * maxColumn);

        fenwickTree2DY.pointUpdate(minRow, minColumn, value * (1 - minRow));
        fenwickTree2DY.pointUpdate(minRow, maxColumn + 1, value * (minRow - 1));
        fenwickTree2DY.pointUpdate(maxRow + 1, minColumn, value * maxRow);
        fenwickTree2DY.pointUpdate(maxRow + 1, maxColumn + 1, -value * maxRow);

        fenwickTree2DI.pointUpdate(minRow, minColumn, value * (minRow - 1) * (minColumn - 1));
        fenwickTree2DI.pointUpdate(minRow, maxColumn + 1, -value * (minRow - 1) * maxColumn);
        fenwickTree2DI.pointUpdate(maxRow + 1, minColumn, -value * maxRow * (minColumn - 1));
        fenwickTree2DI.pointUpdate(maxRow + 1, maxColumn + 1, value * maxRow * maxColumn);
    }

    public long rangeSum(int row1, int column1, int row2, int column2) {
        int minRow = Math.min(row1, row2);
        int maxRow = Math.max(row1, row2);
        int minColumn = Math.min(column1, column2);
        int maxColumn = Math.max(column1, column2);

        long value1 = fenwickTree2DXY.pointQuery(maxRow, maxColumn) * maxRow * maxColumn
                + fenwickTree2DX.pointQuery(maxRow, maxColumn) * maxRow
                + fenwickTree2DY.pointQuery(maxRow, maxColumn) * maxColumn
                + fenwickTree2DI.pointQuery(maxRow, maxColumn);

        long value2 = fenwickTree2DXY.pointQuery(maxRow, minColumn - 1) * maxRow * (minColumn - 1)
                + fenwickTree2DX.pointQuery(maxRow, minColumn - 1) * maxRow
                + fenwickTree2DY.pointQuery(maxRow, minColumn - 1) * (minColumn - 1)
                + fenwickTree2DI.pointQuery(maxRow, minColumn - 1);

        long value3 = fenwickTree2DXY.pointQuery(minRow - 1, maxColumn) * (minRow - 1) * maxColumn
                + fenwickTree2DX.pointQuery(minRow - 1, maxColumn) * (minRow - 1)
                + fenwickTree2DY.pointQuery(minRow - 1, maxColumn) * maxColumn
                + fenwickTree2DI.pointQuery(minRow - 1, maxColumn);

        long value4 = fenwickTree2DXY.pointQuery(minRow - 1, minColumn - 1) * (minRow - 1) * (minColumn - 1)
                + fenwickTree2DX.pointQuery(minRow - 1, minColumn - 1) * (minRow - 1)
                + fenwickTree2DY.pointQuery(minRow - 1, minColumn - 1) * (minColumn - 1)
                + fenwickTree2DI.pointQuery(minRow - 1, minColumn - 1);

        // Rectangular region between lower row and left column x upper row and right column
        return value1 - value2 - value3 + value4;
    }

    public static class FenwickTree {
        private final long[][] fenwickTree;
        private final int rows;
        private final int columns;

        public FenwickTree(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;

            fenwickTree = new long[rows + 1][columns + 1];
        }

        private void pointUpdate(int row, int column, int value) {
            for (int i = row; i <= rows; i += lsOne(i)) {
                for (int j = column; j <= columns; j += lsOne(j)) {
                    fenwickTree[i][j] += value;
                }
            }
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
    }

    // Tests
    public static void main(String[] args) {
        FenwickTree2DRangeSumRangeUpdate fenwickTree2D = new FenwickTree2DRangeSumRangeUpdate(3, 4);
        fenwickTree2D.rangeUpdate(1, 2, 1, 3, 1);
        fenwickTree2D.rangeUpdate(2, 2, 2, 3, 2);
        fenwickTree2D.rangeUpdate(3, 2, 3, 3, 3);

        System.out.println("Fenwick Tree");
        printFenwickTree(fenwickTree2D);

        fenwickTree2D.rangeUpdate(1, 1, 1, 4, 5);
        fenwickTree2D.rangeUpdate(2, 2, 3, 4, 10);

        System.out.println("\nAfter updates");
        printFenwickTree(fenwickTree2D);
    }

    private static void printFenwickTree(FenwickTree2DRangeSumRangeUpdate fenwickTree2DRURQ) {
        for (int row = 1; row <= fenwickTree2DRURQ.rows; row++) {
            for (int column = 1; column <= fenwickTree2DRURQ.columns; column++) {
                System.out.print(fenwickTree2DRURQ.rangeSum(row, column, row, column));

                if (column != fenwickTree2DRURQ.columns) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
