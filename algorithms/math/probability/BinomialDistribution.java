package algorithms.math.probability;

/**
 * Created by Rene Argento on 19/12/20.
 */
// Reads in n, k, and p as arguments and prints out
// (n choose k) * p^k * (1-p)^n-k.
// Given N events, what is the probability of k outcomes, given that the probability of 1 outcome is p.
// Based on https://algs4.cs.princeton.edu/11model/Binomial.java.html
// More information on: https://math.stackexchange.com/questions/151810/probability-of-3-heads-in-10-coin-flips
public class BinomialDistribution {

    public static void main(String[] args) {
        double probabilityOf3CoinsIn10Flips = binomial(10, 3, 0.5);
        System.out.println("Probability: " + probabilityOf3CoinsIn10Flips);
        System.out.println("Expected: 0.1171875");
    }

    public static double binomial(int n, int k, double p) {
        double[][] binomial = new double[n + 1][k + 1];

        // Base cases
        for (int i = 0; i <= n; i++) {
            binomial[i][0] = Math.pow(1.0 - p, i);
        }
        binomial[0][0] = 1.0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                binomial[i][j] = p * binomial[i - 1][j - 1] + (1.0 - p) * binomial[i - 1][j];
            }
        }
        return binomial[n][k];
    }
}
