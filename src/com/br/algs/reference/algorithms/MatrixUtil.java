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

    //Fast exponentiation in mod 10^4
    private static long mod = 10000;

    //Used for matrices n x n
    private static long[][] fastMatrixExponentiation(long[][] matrix, int exponent) {
        if(exponent == 0) {
            long[][] identityMatrix = new long[matrix.length][matrix[0].length];

            for(int i = 0; i < matrix.length; i++) {
                identityMatrix[i][i] = 1;
            }

            return identityMatrix;
        }

        if(exponent == 1) {
            return matrix;
        }

        long[][] matrixSquared = matrixMultiplication(matrix, matrix);

        if(exponent % 2 == 0) {
            return fastMatrixExponentiation(matrixSquared, exponent / 2);
        }

        if(exponent % 2 == 1) {
            return (matrixMultiplication(matrix, fastMatrixExponentiation(matrixSquared, exponent / 2)));
        }

        return matrix;
    }

    private static long[][] matrixMultiplication(long[][] matrix1, long[][] matrix2) {
        long[][] result = new long[matrix1.length][matrix2[0].length];

        for(int i = 0; i < matrix1.length; i++) {
            for(int j = 0; j < matrix2[0].length; j++) {
                long currentSum = 0;

                for(int k = 0; k < matrix1.length; k++) {
                    currentSum += matrix1[i][k] * matrix2[k][j];
                }

                result[i][j] = currentSum % mod;
            }
        }

        return result;
    }

}
