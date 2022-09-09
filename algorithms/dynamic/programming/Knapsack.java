package algorithms.dynamic.programming;

import java.util.Arrays;

/**
 * Created by Rene Argento on 09/09/22.
 */
// Given n items, each with its value V and weight W and a maximum knapsack weight S, compute the maximum value
// of the items that we can carry, if we can either ignore or take a particular item.
// Based on https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
public class Knapsack {

    public static void main(String[] args) {
        int[] values1 = { 100, 70, 50, 10 };
        int[] weights1 = { 10, 4, 6, 12 };
        int maxWeight1 = 12;
        long maximumValue1 = knapsack(values1, weights1, maxWeight1);
        System.out.println("Max value 1: " + maximumValue1 + " Expected: 120");

        int[] values2 = { 60, 100, 120 };
        int[] weights2 = { 10, 20, 30 };
        int maxWeight2 = 50;
        long maximumValue2 = knapsack(values2, weights2, maxWeight2);
        System.out.println("Max value 2: " + maximumValue2 + " Expected: 220");
    }

    // O(n * S) runtime complexity and O(S) space
    private static long knapsack(int[] values, int[] weights, int maxWeight) {
        long[] dp = new long[maxWeight + 1];

        for (int item = 1; item <= values.length; item++) {
            for (int weight = maxWeight; weight >= 0; weight--) {
                if (weight - weights[item - 1] >= 0) {
                    long valueWithoutItem = dp[weight];
                    long valueWithItem = dp[weight - weights[item - 1]] + values[item - 1];
                    dp[weight] = Math.max(valueWithoutItem, valueWithItem);
                }
            }
        }
        return dp[maxWeight];
    }

    // O(n * S) runtime complexity and O(n * S) space
    private static long knapsack2(int[] values, int[] weights, int maxWeight) {
        long[][] dp = new long[values.length][maxWeight + 1];
        for (long[] rows : dp) {
            Arrays.fill(rows, -1);
        }
        return knapsack2(values, weights, dp, 0, maxWeight);
    }

    private static long knapsack2(int[] values, int[] weights, long[][] dp, int itemIndex, int remainingWeight) {
        if (itemIndex == values.length || remainingWeight == 0) {
            return 0;
        }
        if (dp[itemIndex][remainingWeight] != -1) {
            return dp[itemIndex][remainingWeight];
        }

        int weightWithItem = remainingWeight - weights[itemIndex];
        if (weightWithItem < 0) {
            dp[itemIndex][remainingWeight] = knapsack2(values, weights, dp, itemIndex + 1, remainingWeight);
            return dp[itemIndex][remainingWeight];
        }

        long totalValueWithItem = values[itemIndex] +
                knapsack2(values, weights, dp, itemIndex + 1, weightWithItem);
        long totalValueWithoutItem = knapsack2(values, weights, dp, itemIndex + 1, remainingWeight);
        dp[itemIndex][remainingWeight] = Math.max(totalValueWithItem, totalValueWithoutItem);
        return dp[itemIndex][remainingWeight];
    }
}
