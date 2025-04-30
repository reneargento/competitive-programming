package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 23/03/25.
 */
public class LogAndPower {

    private static double logBaseX(double number, int base) {
        return Math.log(number) / Math.log(base);
    }

    // Computes log(N^X), useful to compute the log of very big numbers
    private static double logNPowerX(int n, int x) {
        return x * Math.log(n);
    }

    // Computes the number of digits (before the fraction) of N^X
    private static int computeNumberOfDigitsOfNPowerX(double n, double x) {
        return (int) (Math.floor(1 + x * Math.log10(n)));
    }

    private static double nthRootOfX(double n, double x) {
        return Math.pow(n, 1 / x);
    }

    // Computes the number of digits of N!
    private static int computeDigitsInFactorial(int number) {
        double totalDigits = 0;
        for (int i = 1; i <= number; i++) {
            totalDigits += Math.log10(i);
        }
        return 1 + (int) totalDigits;
    }
}
