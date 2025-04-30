package algorithms.math.fractions;

import java.io.IOException;

/**
 * Created by Rene Argento on 30/04/25.
 */
// Converts a double number into a fraction.
// Example: 0.142857142857 -> 1 / 7
public class DecimalToFraction {

    private static class Fraction {
        long nominator;
        long denominator;

        public Fraction(long nominator, long denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    public static void main(String[] args) throws IOException {
        Fraction fraction1 = computeFraction("0.142857", 6);
        System.out.println("Fraction 1: " + fraction1.nominator + "/" + fraction1.denominator);
        System.out.println("Expected:   1/7");

        Fraction fraction2 = computeFraction("1.6", 1);
        System.out.println("\nFraction 2: " + fraction2.nominator + "/" + fraction2.denominator);
        System.out.println("Expected:   5/3");

        Fraction fraction3 = computeFraction("123.456", 2);
        System.out.println("\nFraction 3: " + fraction3.nominator + "/" + fraction3.denominator);
        System.out.println("Expected:   61111/495");
    }

    // j = length of repeating digits pattern after the decimal point
    // k = length of non-repeating digits after the decimal point
    // Example: 25.321565656...
    // k = 3
    // j = 2
    private static Fraction computeFraction(String numberString, int j) {
        long nominator;
        long denominator;
        int decimalPointIndex = numberString.indexOf('.');
        int k = numberString.length() - (decimalPointIndex + 1) - j;
        double decimal = Double.parseDouble(numberString);

        int kJSum = k + j;
        long tenToK = (long) Math.pow(10, k);
        long tenToJ = (long) Math.pow(10, j);
        long tenToKJSum = (long) Math.pow(10, kJSum);
        long tenToKJSumTimesDecExp = Math.round(tenToKJSum * decimal);

        if (j == 0) {
            nominator = tenToKJSumTimesDecExp;
            denominator = tenToKJSum;
        } else {
            nominator = (tenToKJSumTimesDecExp - tenToKJSumTimesDecExp / tenToJ);
            denominator = (tenToKJSum - tenToK);
        }
        return reduceFraction(nominator, denominator);
    }

    private static Fraction reduceFraction(long nominator, long denominator) {
        long gcd = gcd(nominator, denominator);
        return new Fraction(nominator / gcd, denominator / gcd);
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
