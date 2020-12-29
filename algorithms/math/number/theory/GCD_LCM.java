package algorithms.math.number.theory;

/**
 * Created by rene on 16/04/17.
 */
public class GCD_LCM {

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static long lcm(long number1, long number2) {
        return number1 * (number2 / gcd(number1, number2));
    }

    private static String reduceFraction(long dividend, long divisor) {
        long gcd = gcd(dividend, divisor);
        long newDividend = dividend / gcd;
        long newDivisor = divisor / gcd;

        return newDividend + "/" + newDivisor;
    }

}
