package algorithms.math.fractions;

import algorithms.math.number.theory.GCD_LCM;

/**
 * Created by Rene Argento on 25/04/25.
 */
// The code assumes that only nominators can be negative, not denominators.
public class FractionOperations {

    public static class Fraction {
        long nominator;
        long denominator;

        public Fraction(long nominator, long denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    public static Fraction sumFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        long lcm = GCD_LCM.lcm(denominator1, denominator2);
        long multiplier1 = lcm / denominator1;
        long multiplier2 = lcm / denominator2;

        long nominator = (nominator1 * multiplier1) + (nominator2 * multiplier2);
        return new Fraction(nominator, lcm);
    }

    private static Fraction subtractFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        return sumFraction(nominator1, denominator1, -nominator2, denominator2);
    }

    private static Fraction multiplyFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        return new Fraction(nominator1 * nominator2, denominator1 * denominator2);
    }

    private static Fraction divideFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        return multiplyFraction(nominator1, denominator1, denominator2, nominator2);
    }

    private static boolean isSmaller(long nominator1, long denominator1, long nominator2, long denominator2) {
        return nominator1 * denominator2 < nominator2 * denominator1;
    }
}
