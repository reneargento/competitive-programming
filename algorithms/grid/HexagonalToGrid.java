package algorithms.grid;

import java.util.Arrays;

/**
 * Created by Rene Argento on 10/04/25.
 */
// Converts a hexagonal grid into a 2D grid.

// Example with cellsInSides = 3
//   1 2 3
//  4 5 6 7
// 8 9 10 11 12
// 13 14 15 16
//  17 18 19
// into
// 1 2 3 -1 -1
// 4 5 6 7 -1
// 8 9 10 11 12
// -1 13 14 15 16
// -1 -1 17 18 19

// Navigation is then possible on six directions:
// west, north, east, south, northwest, southeast
public class HexagonalToGrid {

    // Navigation available after conversion
    int[] neighborRows =    { 0, -1, 0, 1, -1, 1 };
    int[] neighborColumns = { -1, 0, 1, 0, -1, 1 };

    private static int[][] buildGrid(int cellsInSides) {
        int gridDimension = cellsInSides * 2 - 1;
        int[][] grid = new int[gridDimension][gridDimension];
        for (int[] row : grid) {
            Arrays.fill(row, -1);
        }

        int middleRow = grid.length / 2;
        int numbersInRow = cellsInSides;
        int value = 1;

        for (int row = 0; row < grid.length; row++) {
            int startColumn;
            if (row < middleRow) {
                startColumn = 0;
            } else {
                startColumn = gridDimension - numbersInRow;
            }

            int numbersAdded = 0;
            for (int column = startColumn; column < grid[row].length; column++) {
                numbersAdded++;
                grid[row][column] = value;

                value++;
                if (numbersAdded == numbersInRow) {
                    break;
                }
            }

            if (row < middleRow) {
                numbersInRow++;
            } else {
                numbersInRow--;
            }
        }
        return grid;
    }
}
