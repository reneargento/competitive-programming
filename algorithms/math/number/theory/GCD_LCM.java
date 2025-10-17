package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 16/04/17.
 */
public class GCD_LCM {

    private static long gcd(long number1, long number2) {
        number1 = Math.abs(number1);
        number2 = Math.abs(number2);

        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    public static long lcm(long number1, long number2) {
        return number1 * number2 / gcd(number1, number2);
    }

    private static String reduceFraction(long numerator, long denominator) {
        long gcd = gcd(numerator, denominator);
        long newNumerator = numerator / gcd;
        long newDenominator = denominator / gcd;

        return newNumerator + "/" + newDenominator;
    }
}
