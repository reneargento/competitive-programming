package algorithms.math.binomial.coefficient;

import java.math.BigInteger;

/**
 * Created by Rene Argento on 05/12/25.
 */
// Given N elements, in how many ways can we choose a subset of size K elements or more?
// For example, given 10 balls, in how many ways we can choose 6 or more?
public class NChooseKorMore {

    public static void main(String[] args) {
        long binomialCoefficientKorMore = cumulativeBinomialCoefficientKOrMore(10, 6);
        System.out.println("Ways: " + binomialCoefficientKorMore);
        System.out.println("Expected: 386");
    }

    // Compute C(n, k) + C(n, k+1) + C(n, k+2), ... + C(n, n)
    private static long cumulativeBinomialCoefficientKOrMore(int totalNumbers, int numbersToChoose) {
        if (numbersToChoose <= 0) {
            return 1L << totalNumbers;  // 2^n
        }
        if (numbersToChoose > totalNumbers) {
            return 0;
        }

        long sum = 0;
        long binomialCoefficient = 1;
        for (int i = 0; i < totalNumbers; i++) {
            binomialCoefficient = binomialCoefficient * (totalNumbers - i) / (i + 1);

            if (i >= numbersToChoose - 1) {
                sum += binomialCoefficient;
            }
        }
        return sum;
    }

    // Alternative using BigInteger and storing values in an array.
    // With the array, loop over indexes higher than or equals to numbersToChoose
    private static BigInteger[] computeBinomialCoefficients(int totalNumbers) {
        BigInteger[] binomialCoefficients = new BigInteger[totalNumbers];
        binomialCoefficients[0] = BigInteger.ONE;

        for (int i = 1; i < binomialCoefficients.length; i++) {
            binomialCoefficients[i] = binomialCoefficients[i - 1].multiply(BigInteger.valueOf(totalNumbers - (i - 1)))
                    .divide(BigInteger.valueOf(i));
        }
        return binomialCoefficients;
    }
}
