package algorithms.matrix;

/**
 * Created by Rene Argento on 19/01/18.
 */
// Based on https://www.hackerearth.com/practice/notes/matrix-exponentiation-1/
public class FibonacciLgN {

    private static final long MOD = 1000000007;

    public static long fibonacciNumber(int number) {
        if (number == 0 || number == 1) {
            return number;
        }

        // Fib1 Fib2
        long[][] initialMatrix = {
                {0, 1}
        };

        // Generates Fib2 Fib3
        long[][] matrixToMultiply = {
                {0, 1},
                {1, 1}
        };

        long[][] exponentialMatrix =
                MatrixExponentiation.fastMatrixExponentiation(matrixToMultiply, number - 1, MOD);
        long[][] result = MatrixExponentiation.matrixMultiplication(initialMatrix, exponentialMatrix, MOD);
        return result[0][1] % MOD;
    }

    public static void main(String[] args) {
        System.out.println(FibonacciLgN.fibonacciNumber(10) + " Expected: 55");
        System.out.println(FibonacciLgN.fibonacciNumber(30) + " Expected: 832040");
    }

}
