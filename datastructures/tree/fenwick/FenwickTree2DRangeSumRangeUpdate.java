package datastructures.tree.fenwick;

/**
 * Created by Rene Argento on 04/01/18.
 */
// Adapted from https://arxiv.org/pdf/1311.6093.pdf
// and https://www.codechef.com/viewsolution/6391361
public class FenwickTree2DRangeSumRangeUpdate {

    private FenwickTree fenwickTree2DXY;
    private FenwickTree fenwickTree2DX;
    private FenwickTree fenwickTree2DY;
    private FenwickTree fenwickTree2DI;

    public FenwickTree2DRangeSumRangeUpdate(int rows, int columns) {
        fenwickTree2DXY = new FenwickTree(rows, columns);
        fenwickTree2DX = new FenwickTree(rows, columns);
        fenwickTree2DY = new FenwickTree(rows, columns);
        fenwickTree2DI = new FenwickTree(rows, columns);
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

        //Rectangular region between lower row and left column x upper row and right column
        return value1 - value2 - value3 + value4;
    }

    public static class FenwickTree {
        private int[][] fenwickTree;
        private int rows;
        private int columns;

        public FenwickTree(int rows, int columns) {
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

    // Tests
    public static void main(String[] args) {
        FenwickTree2DRangeSumRangeUpdate fenwickTree2D = new FenwickTree2DRangeSumRangeUpdate(3, 3);
        fenwickTree2D.rangeUpdate(2, 2, 1, 1, 1);
        // fenwickTree2D.rangeUpdate(1, 1, 1, 2, 2);

        for(int i = 1; i <= 3; i++) {
            for(int j = 1; j <= 3; j++) {
                System.out.println("row " + i + " column " + j + ": " + fenwickTree2D.rangeSum(i, j, i, j));
            }
        }
    }

}
