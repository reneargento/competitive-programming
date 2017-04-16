package com.br.algs.reference.algorithms;

/**
 * Created by rene on 16/04/17.
 */
public class MatrixUtil {

    private static int[][] traverseDiagonally(int rows, int columns) {
        int[][] matrix = new int[rows][columns];

        int count = 0;

        //Top down diagonals
        for(int column = 0; column < matrix[0].length; column++){
            for(int i=0, j = column; i < rows && j >= 0; i++, j--){
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

}
