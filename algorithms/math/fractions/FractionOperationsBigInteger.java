package algorithms.math.fractions;

import java.math.BigInteger;

/**
 * Created by Rene Argento on 29/04/25.
 */
// Same operations as FractionOperations, but using BigInteger to handle bigger numbers
public class FractionOperationsBigInteger {

    private static class Fraction {
        BigInteger nominator;
        BigInteger denominator;

        public Fraction(BigInteger nominator, BigInteger denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    private static Fraction sumFraction(BigInteger nominator1, BigInteger denominator1, BigInteger nominator2,
                                        BigInteger denominator2) {
        BigInteger lcm = lcm(denominator1, denominator2);
        BigInteger multiplier1 = lcm.divide(denominator1);
        BigInteger multiplier2 = lcm.divide(denominator2);

        BigInteger nominator = (nominator1.multiply(multiplier1)).add(nominator2.multiply(multiplier2));
        return new Fraction(nominator, lcm);
    }

    private static Fraction subtractFraction(BigInteger nominator1, BigInteger denominator1, BigInteger nominator2,
                                             BigInteger denominator2) {
        return sumFraction(nominator1, denominator1, nominator2.negate(), denominator2);
    }

    private static Fraction multiplyFraction(BigInteger nominator1, BigInteger denominator1, BigInteger nominator2,
                                             BigInteger denominator2) {
        return new Fraction(nominator1.multiply(nominator2), denominator1.multiply(denominator2));
    }

    private static Fraction divideFraction(BigInteger nominator1, BigInteger denominator1, BigInteger nominator2,
                                           BigInteger denominator2) {
        return multiplyFraction(nominator1, denominator1, denominator2, nominator2);
    }

    private static BigInteger gcd(BigInteger number1, BigInteger number2) {
        number1 = number1.abs();
        number2 = number2.abs();

        while (number2.compareTo(BigInteger.ZERO) > 0) {
            BigInteger temp = number2;
            number2 = number1.mod(number2);
            number1 = temp;
        }
        return number1;
    }

    private static BigInteger lcm(BigInteger number1, BigInteger number2) {
        return number1.multiply(number2.divide(gcd(number1, number2)));
    }

    private static boolean isSmaller(BigInteger nominator1, BigInteger denominator1, BigInteger nominator2,
                                     BigInteger denominator2) {
        return nominator1.multiply(denominator2).compareTo(nominator2.multiply(denominator1)) < 0;
    }
}
