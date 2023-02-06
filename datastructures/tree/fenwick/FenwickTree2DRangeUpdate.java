package datastructures.tree.fenwick;

/**
 * Created by Rene Argento on 26/12/17.
 */
public class FenwickTree2DRangeUpdate {
    private final int[][] fenwickTree;
    private final int rows;
    private final int columns;

    public FenwickTree2DRangeUpdate(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        fenwickTree = new int[rows + 1][columns + 1];
    }

    public void rangeUpdate(int row1, int column1, int row2, int column2, int value) {
        int minRow = Math.min(row1, row2);
        int maxRow = Math.max(row1, row2);
        int minColumn = Math.min(column1, column2);
        int maxColumn = Math.max(column1, column2);

        // Rectangular region between lower row and left column x upper row and right column
        pointUpdate(maxRow + 1, maxColumn + 1, value);
        pointUpdate(minRow, minColumn, value);
        pointUpdate(minRow, maxColumn + 1, -value);
        pointUpdate(maxRow + 1, minColumn, -value);
    }

    private void pointUpdate(int row, int column, int value) {
        for (int i = row; i <= rows; i += lsOne(i)) {
            for (int j = column; j <= columns; j += lsOne(j)) {
                fenwickTree[i][j] += value;
            }
        }
    }

    public int pointQuery(int row, int column) {
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
        FenwickTree2DRangeUpdate fenwickTree2DRUPQ = new FenwickTree2DRangeUpdate(3, 4);
        fenwickTree2DRUPQ.rangeUpdate(1, 2, 1, 3, 1);
        fenwickTree2DRUPQ.rangeUpdate(2, 2, 2, 3, 2);
        fenwickTree2DRUPQ.rangeUpdate(3, 2, 3, 3, 3);

        System.out.println("Fenwick Tree");
        printFenwickTree(fenwickTree2DRUPQ);

        fenwickTree2DRUPQ.rangeUpdate(1, 1, 1, 4, 5);
        fenwickTree2DRUPQ.rangeUpdate(2, 2, 3, 4, 10);

        System.out.println("\nAfter updates");
        printFenwickTree(fenwickTree2DRUPQ);
    }

    private static void printFenwickTree(FenwickTree2DRangeUpdate fenwickTree2DRUPQ) {
        for (int row = 1; row <= fenwickTree2DRUPQ.rows; row++) {
            for (int column = 1; column <= fenwickTree2DRUPQ.columns; column++) {
                System.out.print(fenwickTree2DRUPQ.pointQuery(row, column));

                if (column != fenwickTree2DRUPQ.columns) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
