package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 21/01/22.
 */
// Converts a double number into a fraction.
// Example: 2.6 -> 13 / 5
public class DecimalToFraction {

    private static class Fraction {
        long nominator;
        long denominator;

        public Fraction(long nominator, long denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    // Method 1 - faster but does not work for repeating decimals (for example, for 0.3333..., use method 2)
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

    // Method 2 - gets the smallest possible fraction using binary search
    // Based on https://stackoverflow.com/questions/5124743/algorithm-for-simplifying-decimal-to-fractions
    private static Fraction decimalToFraction(double decimal) {
        double error = 0.000001;
        int numberFloor = (int) Math.floor(decimal);
        decimal -= numberFloor;

        if (decimal < error) {
            return new Fraction(numberFloor, 1);
        } else if (1 - error < decimal) {
            return new Fraction(numberFloor + 1, 1);
        }

        // The lower fraction is 0/1
        long lowerNominator = 0;
        long lowerDenominator = 1;
        // The upper fraction is 1/1
        long upperNominator = 1;
        long upperDenominator = 1;

        while (true) {
            // The middle fraction is (lowerNominator + upperNominator) / (lowerDenominator + upperDenominator)
            long middleNominator = lowerNominator + upperNominator;
            long middleDenominator = lowerDenominator + upperDenominator;
            if (middleDenominator * (decimal + error) < middleNominator) {
                // If decimal + error < middle
                // middle is our new upper
                upperNominator = middleNominator;
                upperDenominator = middleDenominator;
            } else if (middleNominator < middleDenominator * (decimal - error)) {
                // Else If middle < decimal - error
                // middle is our new lower
                lowerNominator = middleNominator;
                lowerDenominator = middleDenominator;
            } else {
                // Else middle is our best fraction
                return new Fraction(numberFloor * middleDenominator + middleNominator, middleDenominator);
            }
        }
    }
}
