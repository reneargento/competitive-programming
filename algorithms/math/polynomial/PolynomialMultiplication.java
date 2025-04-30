package algorithms.math.polynomial;

import java.util.Arrays;

/**
 * Created by Rene Argento on 22/04/25.
 */
// Multiply polynomials described by their coefficients.
// Example: 18 + 4x + 5x^2 + 7x^4 is described as [18, 4, 5, 0, 7]
// O (n * m) where n is the number of coefficients of the first polynomial and m is the number of coefficients of the
// second polynomial.
public class PolynomialMultiplication {

    private static long[] multiplyPolynomials(int[] coefficients1, int[] coefficients2) {
        long[] result = new long[coefficients1.length + coefficients2.length - 1];

        for (int i = 0; i < coefficients1.length; i++) {
            for (int j = 0; j < coefficients2.length; j++) {
                int exponent = i + j;

                int coefficient = coefficients1[i] * coefficients2[j];
                result[exponent] += coefficient;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] coefficients1 = { 1, 1, -1, 1, 1 };
        int[] coefficients2 = { 9, -8, 7, 6, 5 };
        long[] result = multiplyPolynomials(coefficients1, coefficients2);
        System.out.println("Result:   " + Arrays.toString(result));
        System.out.println("Expected: [9, 1, -10, 30, 5, -2, 8, 11, 5]");
    }
}
