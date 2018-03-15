package com.br.algs.reference.algorithms.matrix;

/**
 * Created by rene on 16/04/17.
 */
public class MatrixExponentiation {

    //Used for matrices n x n
    public static long[][] fastMatrixExponentiation(long[][] matrix, int exponent, long mod) {
        if (exponent == 0) {
            long[][] identityMatrix = new long[matrix.length][matrix[0].length];

            for(int i = 0; i < matrix.length; i++) {
                identityMatrix[i][i] = 1;
            }

            return identityMatrix;
        }

        if (exponent == 1) {
            return matrix;
        }

        long[][] matrixSquared = matrixMultiplication(matrix, matrix, mod);

        if (exponent % 2 == 0) {
            return fastMatrixExponentiation(matrixSquared, exponent / 2, mod);
        } else {
            return (matrixMultiplication(matrix,
                    fastMatrixExponentiation(matrixSquared, exponent / 2, mod), mod));
        }
    }

    public static long[][] matrixMultiplication(long[][] matrix1, long[][] matrix2, long mod) {
        long[][] result = new long[matrix1.length][matrix2[0].length];

        for(int i = 0; i < matrix1.length; i++) {
            for(int j = 0; j < matrix2[0].length; j++) {
                for(int k = 0; k < matrix1[0].length; k++) {
                    result[i][j] = (result[i][j] + matrix1[i][k] * matrix2[k][j]) % mod;
                }
            }
        }

        return result;
    }

}
