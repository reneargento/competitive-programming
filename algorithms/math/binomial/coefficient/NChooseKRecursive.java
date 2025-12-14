package algorithms.math.binomial.coefficient;

import java.math.BigInteger;

/**
 * Created by Rene Argento on 02/12/22.
 */
// Given N elements, in how many ways can we choose a subset of size K elements?
// Recursive implementation
// Complexity: O(K)
public class NChooseKRecursive {

    public static void main(String[] args) {
        BigInteger[][] dp = new BigInteger[200][200];

        BigInteger numberOfWays = binomialCoefficient(dp, 133, 71);
        System.out.println("Result:   " + numberOfWays);
        System.out.println("Expected: 555687036928510235891585199545206017600");
    }

    private static BigInteger binomialCoefficient(BigInteger[][] dp, int totalNumbers, int numbersToChoose) {
        if (totalNumbers == numbersToChoose || numbersToChoose == 0) {
            return BigInteger.ONE;
        }
        if (totalNumbers < numbersToChoose) {
            return BigInteger.ZERO;
        }
        if (dp[totalNumbers][numbersToChoose] != null) {
            return dp[totalNumbers][numbersToChoose];
        }

        dp[totalNumbers][numbersToChoose] = binomialCoefficient(dp, totalNumbers - 1, numbersToChoose - 1).add(
                binomialCoefficient(dp, totalNumbers - 1, numbersToChoose));
        return dp[totalNumbers][numbersToChoose];
    }
}
