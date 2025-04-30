package algorithms.grid;

/**
 * Created by Rene Argento on 15/04/25.
 */
// Fills a hexagonal grid starting from the center and spiraling outwards following the path:
// northwest -> north -> northeast -> southeast -> south -> southwest
public class FillHexagonalGrid {

    private static class MoveResult {
        int newRow;
        int newColumn;
        int newValue;

        public MoveResult(int newRow, int newColumn, int newValue) {
            this.newRow = newRow;
            this.newColumn = newColumn;
            this.newValue = newValue;
        }
    }

    private static int[][] computeGrid() {
        int[][] grid = new int[12][12];
        int row = grid.length / 2;
        int column = grid.length / 2;
        int value = 1;
        int iterations = 1;

        // northwest -> north -> northeast -> southeast -> south -> southwest
        int[] neighborRows = { 0, -1, -1, 0, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, 1, 0, -1 };
        final int SOUTH_INDEX = 4;
        final int SOUTHWEST_INDEX = 5;

        MoveResult moveResult = move(grid, row, column, value, iterations, 0, 0);

        while (moveResult.newValue < 30) {
            for (int i = 0; i < neighborRows.length; i++) {
                if (i == SOUTH_INDEX) {
                    iterations++;
                }
                int iterationsToUse = i != SOUTHWEST_INDEX ? iterations : iterations - 1;

                moveResult = move(grid, moveResult.newRow, moveResult.newColumn, moveResult.newValue,
                        iterationsToUse, neighborRows[i], neighborColumns[i]);
            }
        }
        return grid;
    }

    private static MoveResult move(int[][] grid, int row, int column, int value, int iterations, int rowDelta,
                                   int columnDelta) {
        for (int i = 0; i < iterations; i++) {
            row += rowDelta;
            column += columnDelta;
            if (!isValid(grid, row, column)) {
                break;
            }
            grid[row][column] = value;
            value++;
        }
        return new MoveResult(row, column, value);
    }

    private static boolean isValid(int[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    public static void main(String[] args) {
        int[][] grid = computeGrid();

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                System.out.print(grid[row][column] + " ");
            }
            System.out.println();
        }
    }
}
