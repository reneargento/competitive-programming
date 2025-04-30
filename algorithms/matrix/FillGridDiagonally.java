package algorithms.matrix;

/**
 * Created by Rene Argento on 15/04/25.
 */
// Fills all cells of a 2D grid diagonally
public class FillGridDiagonally {

    private static int[][] computeGrid(int rows, int columns) {
        int[][] grid = new int[rows][columns];
        int row = 0;
        int column = 0;
        int maxRow = 0;
        int startColumn = 0;
        int secondHalfBoardRowStart = 1;
        int value = 1;

        boolean secondHalfBoardReached = grid[0].length == 1;
        while (row < grid.length
                && column < grid[0].length) {
            grid[row][column] = value;

            if (row == maxRow) {
                if (!secondHalfBoardReached) {
                    row = 0;
                } else {
                    row = secondHalfBoardRowStart;
                    secondHalfBoardRowStart++;
                }
                if (row != grid.length - 1) {
                    maxRow = Math.min(maxRow + 1, grid.length - 1);
                }
            } else {
                row++;
            }

            if (column == 0
                    || (secondHalfBoardReached && row == secondHalfBoardRowStart - 1)
                    || grid.length == 1) {
                if (startColumn < grid[0].length - 1) {
                    startColumn++;
                    if (startColumn == grid[0].length - 1) {
                        secondHalfBoardReached = true;
                    }
                }
                column = startColumn;
            } else {
                column--;
            }
            value++;
        }
        return grid;
    }

    public static void main(String[] args) {
        int[][] grid = computeGrid(5, 5);

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                System.out.print(grid[row][column] + " ");
            }
            System.out.println();
        }
    }
}
