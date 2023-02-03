package algorithms.math;

import java.math.BigInteger;

/**
 * Created by Rene Argento on 02/12/22.
 */
// Given N elements, in how many ways can we choose a subset of size K elements?
// Based on https://stackoverflow.com/questions/2201113/combinatoric-n-choose-r-in-java-math
// and https://en.wikipedia.org/wiki/Binomial_coefficient
public class NChooseK {

    public static void main(String[] args) {
        BigInteger numberOfWays1 = binomialCoefficient(4, 2);
        System.out.println("Result:   " + numberOfWays1);
        System.out.println("Expected: 6");
        System.out.println();

        BigInteger numberOfWays2 = binomialCoefficient(133, 71);
        System.out.println("Result:   " + numberOfWays2);
        System.out.println("Expected: 555687036928510235891585199545206017600");
    }

    private static BigInteger binomialCoefficient(int totalLength, int numbersToChoose) {
        BigInteger result = BigInteger.ONE;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result.multiply(BigInteger.valueOf(totalLength - i))
                    .divide(BigInteger.valueOf(i + 1));
        }
        return result;
    }

    // Can be used for numbers that don't need a BigInteger
    private static long binomialCoefficientLong(int totalLength, int numbersToChoose) {
        long result = 1;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result * (totalLength - i) / (i + 1);
        }
        return result;
    }
}
