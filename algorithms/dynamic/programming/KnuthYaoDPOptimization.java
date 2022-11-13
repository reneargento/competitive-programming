package algorithms.dynamic.programming;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Rene Argento on 06/10/22.
 */
// Used to reduce the time complexity of DP solutions primarily from O(N^3) to O(N^2).

// Can be used when the cost function satisfies the following conditions, for (a ≤ b ≤ c ≤ d):
// 1- It is a Monotone on the lattice of intervals (MLI) [cost(b, c) ≤ cost(a, d)]
// 2- It satisfies the quadrangle inequality (QI) [cost(a, c) + cost(b, d) ≤ cost(b, c) + cost(a, d)]
// Based on https://www.geeksforgeeks.org/knuths-optimization-in-dynamic-programming/

// This class uses the Knuth-Yao optimization while solving the problem of cutting a stick with the minimum cost
// possible, where the cost of a cut is the length of the stick (UVa 10003 - Cutting Sticks)
public class KnuthYaoDPOptimization {

    private static final int MAX_VALUE = 1000000000;

    public static void main(String[] args) throws IOException {
        int[] cuts1 = { 25, 50, 75 };
        int length1 = 100;
        int minimumCost1 = computeMinimumCost(cuts1, length1);
        System.out.println("Minimum cost: " + minimumCost1 + " Expected: 200");

        int[] cuts2 = { 4, 5, 7, 8 };
        int length2 = 10;
        int minimumCost2 = computeMinimumCost(cuts2, length2);
        System.out.println("Minimum cost: " + minimumCost2 + " Expected: 22");
    }

    private static int computeMinimumCost(int[] cuts, int stickLength) {
        int[] newCuts = new int[cuts.length + 2];
        newCuts[0] = 0;
        newCuts[newCuts.length - 1] = stickLength;
        System.arraycopy(cuts, 0, newCuts, 1, newCuts.length - 2);

        int[][] dp = new int[newCuts.length][newCuts.length];
        // Knuth-Yao DP optimization
        int[][] opt = new int[newCuts.length][newCuts.length];

        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumCost(newCuts, dp, opt, 0, newCuts.length - 1);
    }

    private static int computeMinimumCost(int[] cuts, int[][] dp, int[][] opt, int left, int right) {
        if (left + 1 >= right) {
            opt[left][right] = left;
            return 0;
        }

        if (dp[left][right] != -1) {
            return dp[left][right];
        }
        dp[left][right] = MAX_VALUE;

        computeMinimumCost(cuts, dp, opt, left, right - 1);
        computeMinimumCost(cuts, dp, opt, left + 1, right);

        int minimumCost = Integer.MAX_VALUE;
        for (int i = opt[left][right - 1]; i <= opt[left + 1][right]; i++) {
            int leftCost = computeMinimumCost(cuts, dp, opt, left, i);
            int rightCost = computeMinimumCost(cuts, dp, opt, i, right);
            int cost = leftCost + rightCost + (cuts[right] - cuts[left]);
            if (cost < minimumCost) {
                minimumCost = cost;
                opt[left][right] = i;
            }
        }
        dp[left][right] = minimumCost;
        return dp[left][right];
    }
}
