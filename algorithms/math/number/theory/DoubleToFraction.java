package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 21/01/22.
 */
// Converts a double number into a fraction.
// Example: 2.6 -> 13 / 5
public class DoubleToFraction {

    private static class Fraction {
        long a;
        long b;

        public Fraction(long a, long b) {
            this.a = a;
            this.b = b;
        }
    }

    private static Fraction getFractionFromDouble(String number) {
        String[] numbers = number.split("\\.");
        String numbersConcatenated = numbers[0] + numbers[1];
        long dividend = Long.parseLong(numbersConcatenated);
        long divisor = (long) Math.pow(10, numbers[1].length());

        long gcd = gcd(dividend, divisor);
        return new Fraction(dividend / gcd, divisor / gcd);
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }
}
