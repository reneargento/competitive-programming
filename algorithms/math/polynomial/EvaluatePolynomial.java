package algorithms.math.polynomial;

/**
 * Created by Rene Argento on 18/04/25.
 */
// Computes the value of a polynomial of the form c0 + c1 * x + c2 * x^2 + ... + cn * x^n
// given its coefficients and x value.
public class EvaluatePolynomial {

    private static double evaluatePolynomial(double[] coefficients, double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }
}
