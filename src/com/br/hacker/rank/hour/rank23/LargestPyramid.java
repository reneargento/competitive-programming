package com.br.hacker.rank.hour.rank23;

import java.util.Scanner;

/**
 * Created by rene on 09/09/17.
 */
public class LargestPyramid {

    private static int cellsAvailable;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int queries = in.nextInt();

        for(int q = 0; q < queries; q++){
            int rows = in.nextInt();
            int columns = in.nextInt();
            cellsAvailable = in.nextInt();

            int[][] heights = new int[rows][columns];

            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                    heights[i][j] = in.nextInt();
                }
            }

            int maxPyramidSize = 0;

            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                    if (heights[i][j] == 0) {
                        int pyramidSize = getPyramidSize(heights, i, j, 0, 0, cellsAvailable);
                        if (pyramidSize > maxPyramidSize) {
                            maxPyramidSize = pyramidSize;
                        }
                    }
                }
            }

            System.out.println(maxPyramidSize);
        }

        in.close();
    }

    private static int getPyramidSize(int[][] matrix, int row, int column, int value, int currentPyramidSize,
                                      int currentCellsAvailable) {
        int pyramidSize = currentPyramidSize + 1;

        int newValue = matrix[row][column] + 1;

        if (currentCellsAvailable > 0) {
            currentCellsAvailable--;
        } else {
            return currentPyramidSize;
        }

        if (isValidCell(matrix, row - 1, column)) {
            if (matrix[row - 1][column] <= value) {
                int cellsToUse = newValue - matrix[row - 1][column] + 1;
                if (cellsToUse <= currentCellsAvailable) {
                    currentCellsAvailable -= cellsToUse;

                    matrix[row - 1][column] += cellsToUse;
                } else {
                    return currentPyramidSize;
                }
            } else {
                return currentPyramidSize;
            }
        }

        if (isValidCell(matrix, row + 1, column)) {
            if (matrix[row + 1][column] <= value) {
                int cellsToUse = newValue - matrix[row + 1][column] + 1;
                if (cellsToUse <= currentCellsAvailable) {
                    currentCellsAvailable -= cellsToUse;

                    matrix[row + 1][column] += cellsToUse;
                } else {
                    return currentPyramidSize;
                }
            } else {
                return currentPyramidSize;
            }
        }

        if (isValidCell(matrix, row, column - 1)) {
            if (matrix[row][column - 1] <= value) {
                int cellsToUse = newValue - matrix[row][column - 1] + 1;
                if (cellsToUse <= currentCellsAvailable) {
                    currentCellsAvailable -= cellsToUse;

                    matrix[row][column - 1] += cellsToUse;
                } else {
                    return currentPyramidSize;
                }
            } else {
                return currentPyramidSize;
            }
        }

        if (isValidCell(matrix, row, column + 1)) {
            if (matrix[row][column + 1] <= value) {
                int cellsToUse = newValue - matrix[row][column + 1] + 1;
                if (cellsToUse <= currentCellsAvailable) {
                    currentCellsAvailable -= cellsToUse;

                    matrix[row][column + 1] += cellsToUse;
                } else {
                    return currentPyramidSize;
                }
            } else {
                return currentPyramidSize;
            }
        }

        if (isValidCell(matrix, row + 1, column + 1)) {
            if (matrix[row + 1][column + 1] <= value) {
                int cellsToUse = newValue - matrix[row + 1][column + 1] + 1;
                if (cellsToUse <= currentCellsAvailable) {
                    currentCellsAvailable -= cellsToUse;

                    matrix[row + 1][column + 1] += cellsToUse;
                } else {
                    return currentPyramidSize;
                }
            } else {
                return currentPyramidSize;
            }
        }

        if (isValidCell(matrix, row + 1, column - 1)) {
            if (matrix[row + 1][column - 1] <= value) {
                int cellsToUse = newValue - matrix[row + 1][column - 1] + 1;
                if (cellsToUse <= currentCellsAvailable) {
                    currentCellsAvailable -= cellsToUse;

                    matrix[row + 1][column - 1] += cellsToUse;
                } else {
                    return currentPyramidSize;
                }
            }else {
                return currentPyramidSize;
            }
        }

        if (isValidCell(matrix, row - 1, column + 1)) {
            if (matrix[row - 1][column + 1] <= value) {
                int cellsToUse = newValue - matrix[row - 1][column + 1] + 1;
                if (cellsToUse <= currentCellsAvailable) {
                    currentCellsAvailable -= cellsToUse;

                    matrix[row - 1][column + 1] += cellsToUse;
                } else {
                    return currentPyramidSize;
                }
            } else {
                return currentPyramidSize;
            }
        }

        if (isValidCell(matrix, row - 1, column - 1)) {
            if (matrix[row - 1][column - 1] <= value) {
                int cellsToUse = newValue - matrix[row - 1][column - 1] + 1;
                if (cellsToUse <= currentCellsAvailable) {
                    currentCellsAvailable -= cellsToUse;

                    matrix[row - 1][column - 1] += cellsToUse;
                } else {
                    return currentPyramidSize;
                }
            } else {
                return currentPyramidSize;
            }
        }

        cellsAvailable = currentCellsAvailable;
        matrix[row][column] = newValue;



        return pyramidSize;
    }

    private static boolean isValidCell(int[][] matrix, int row, int column) {
        return row >= 0 && row < matrix.length && column >= 0 && column < matrix[0].length;
    }

}
