package algorithms.math.polynomial;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 17/04/25.
 */
// Given a polynomial, its coefficients and N - 2 roots (where N is the number of roots),
// compute the remaining 2 roots using Ruffini's rule and a quadratic equation.
public class PolynomialRoots {

    private static double[] computeRoots(List<Double> coefficients, double[] roots) {
        for (double root : roots) {
            List<Double> nextCoefficients = new ArrayList<>();
            double sumValue = 0;

            for (int i = 0; i < coefficients.size() - 1; i++) {
                double coefficient = coefficients.get(i);
                double nextCoefficient = coefficient + sumValue;
                sumValue = nextCoefficient * root;
                nextCoefficients.add(nextCoefficient);
            }
            coefficients = nextCoefficients;
        }

        double a = coefficients.get(0);
        double b = coefficients.get(1);
        double c = coefficients.get(2);
        double delta = Math.sqrt(Math.pow(b, 2) - 4 * a * c);
        double root1 = (-b + delta) / (2 * a);
        double root2 = (-b - delta) / (2 * a);

        return new double[] { root1, root2 };
    }

    public static void main(String[] args) throws IOException {
        List<Double> coefficients1 = new ArrayList<>();
        coefficients1.add(2.0);
        coefficients1.add(-15.0);
        coefficients1.add(36.0);
        coefficients1.add(-27.0);
        double[] roots1 = new double[] { 3 };

        double[] result1 = computeRoots(coefficients1, roots1);
        System.out.println(Arrays.toString(result1));
        System.out.println("Expected: [3.0, 1.5]");

        List<Double> coefficients2 = new ArrayList<>();
        coefficients2.add(1.0);
        coefficients2.add(-3.0);
        coefficients2.add(-5.0);
        coefficients2.add(15.0);
        coefficients2.add(4.0);
        coefficients2.add(-12.0);
        coefficients2.add(0.0);
        double[] roots2 = new double[] { 1, -2, 0, 2 };

        double[] result2 = computeRoots(coefficients2, roots2);
        System.out.println("\n" + Arrays.toString(result2));
        System.out.println("Expected: [3.0, -1.0]");

        List<Double> coefficients3 = new ArrayList<>();
        coefficients3.add(1.0);
        coefficients3.add(2.3);
        coefficients3.add(1.0);
        coefficients3.add(-0.3);
        double[] roots3 = new double[] { -1.5 };

        double[] result3 = computeRoots(coefficients3, roots3);
        System.out.printf("\n[%.1f, %.1f]\n", result3[0], result3[1]);
        System.out.println("Expected: [0.2, -1.0]");
    }
}
