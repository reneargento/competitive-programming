package com.br.algs.reference.algorithms.matrix;

/**
 * Created by Rene Argento on 19/01/18.
 */
public class DiagonalTraversal {

    /**
     * 9 8 6
     * 7 5 3
     * 4 2 1
     */

    private static int[][] traverseDiagonally1(int rows, int columns) {
        int[][] matrix = new int[rows][columns];

        int count = 0;

        //Top down diagonals
        for(int column = 0; column < matrix[0].length; column++){
            for(int i = 0, j = column; i < rows && j >= 0; i++, j--){
                matrix[i][j] = count++;
            }
        }

        //Bottom up diagonals
        for(int row = 1; row < rows; row++){
            for(int i = row, j = matrix[0].length -1; i < rows && j>=0; i++,j--){
                matrix[i][j] = count++;;
            }
        }

        return matrix;
    }

    /**
     * 9 7 4
     * 8 5 2
     * 6 3 1
     */

    private static int[][] traverseDiagonally2(int rows, int columns) {
        int[][] matrix = new int[rows][columns];

        int number = rows * columns;

        for(int i = 0; i < rows * columns; i++) {
            int row = i;
            int column = 0;

            do {
                if(row >= 0 && row < rows && column >= 0 && column < columns) {
                    matrix[row][column] = number;
                    number--;
                }

                row--;
                column++;
            } while (row >= 0);

        }

        return matrix;
    }

}
