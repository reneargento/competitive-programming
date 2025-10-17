package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 25/09/25.
 */
// Computes the number of digits of a factorial of a number in a target base
// Example: 5! in base 10 is 120 = 3 digits
// O (100000), can be updated to a smaller maximum number
public class FactorialsCountDigitsBase {

    public static void main(String[] args) {
        double[] digitsCountDp = computeFactorialDigitsDp();

        int digits1 = computeFactorialDigits(digitsCountDp, 23233, 344);
        System.out.println("Digits: " + digits1);
        System.out.println("Expected: 36014");

        int digits2 = computeFactorialDigits(digitsCountDp, 1048575, 799);
        System.out.println("\nDigits: " + digits2);
        System.out.println("Expected: 2018112");
    }

    private static final double EPSILON = .00000001;

    private static double[] computeFactorialDigitsDp() {
        double[] digitsCountDp = new double[1000001];
        for (int i = 1; i < digitsCountDp.length; i++) {
            digitsCountDp[i] = digitsCountDp[i - 1] + Math.log(i);
        }
        return digitsCountDp;
    }

    private static int computeFactorialDigits(double[] digitsCount, int number, int base) {
        if (number > 100000) {
            return (int) ((Math.log(Math.sqrt(2 * Math.PI * number))
                    + number * Math.log(number / Math.E)) / Math.log(base)) + 1;
        }
        return (int) Math.ceil(digitsCount[number] / Math.log(base) + EPSILON);
    }
}
