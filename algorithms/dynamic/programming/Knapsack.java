package algorithms.dynamic.programming;

import java.util.*;

/**
 * Created by Rene Argento on 09/09/22.
 */
// Given n items, each with its value V and weight W and a maximum knapsack weight S, compute the maximum value
// of the items that we can carry, if we can either ignore or take a particular item.
// Based on https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
// and https://www.geeksforgeeks.org/printing-items-01-knapsack/
public class Knapsack {

    public static void main(String[] args) {
        int[] values1 = { 100, 70, 50, 10 };
        int[] weights1 = { 10, 4, 6, 12 };
        int maxWeight1 = 12;
        long maximumValue1 = knapsack3(values1, weights1, maxWeight1);
        System.out.println("Max value 1: " + maximumValue1 + " Expected: 120");

        List<Integer> selectedItems1 = knapsack2(values1, weights1, maxWeight1);
        System.out.print("Items:");
        for (Integer item : selectedItems1) {
            System.out.print(" " + item);
        }
        System.out.println(" Expected: 70 50");

        int[] values2 = { 60, 100, 120 };
        int[] weights2 = { 10, 20, 30 };
        int maxWeight2 = 50;
        long maximumValue2 = knapsack3(values2, weights2, maxWeight2);
        System.out.println("\nMax value 2: " + maximumValue2 + " Expected: 220");

        List<Integer> selectedItems2 = knapsack2(values2, weights2, maxWeight2);
        System.out.print("Items:");
        for (Integer item : selectedItems2) {
            System.out.print(" " + item);
        }
        System.out.println(" Expected: 100 120");
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

    // O(n * S) runtime complexity and O(n * S) space - Bottom-up approach
    // Also reconstructs the solution
    private static List<Integer> knapsack2(int[] values, int[] weights, int maxWeight) {
        long[][] dp = new long[values.length + 1][maxWeight + 1];

        for (int item = 1; item < dp.length; item++) {
            for (int weightRemaining = 1; weightRemaining <= maxWeight; weightRemaining++) {
                long valueWithoutItem = dp[item - 1][weightRemaining];
                long valueWithItem = 0;
                if (weightRemaining >= weights[item - 1]) {
                    valueWithItem = dp[item - 1][weightRemaining - weights[item - 1]] + values[item - 1];
                }
                dp[item][weightRemaining] = Math.max(valueWithoutItem, valueWithItem);
            }
        }

        // Knapsack solution = dp[values.length][maxWeight];
        List<Integer> selectedItems = new ArrayList<>();
        int weight = maxWeight;
        for (int item = dp.length - 1; item > 0; item--) {
            if (dp[item][weight] != dp[item - 1][weight]) {
                selectedItems.add(values[item - 1]);
                weight -= weights[item - 1];
            }
        }
        Collections.reverse(selectedItems);
        return selectedItems;
    }

    // O(n * S) runtime complexity and O(n * S) space - Top-down approach
    // Also reconstructs the solution
    private static long knapsack3(int[] values, int[] weights, int maxWeight) {
        long[][] dp = new long[values.length][maxWeight + 1];
        for (long[] rows : dp) {
            Arrays.fill(rows, -1);
        }

        long maxValue = knapsack3(values, weights, dp, 0, maxWeight);
        // Optional reconstruction
        List<Integer> items = reconstructItems2(values, weights, maxWeight, dp);
        return maxValue;
    }

    private static long knapsack3(int[] values, int[] weights, long[][] dp, int itemIndex, int remainingWeight) {
        if (itemIndex == dp.length || remainingWeight == 0) {
            return 0;
        }
        if (dp[itemIndex][remainingWeight] != -1) {
            return dp[itemIndex][remainingWeight];
        }

        long totalValueWithoutItem = knapsack3(values, weights, dp, itemIndex + 1, remainingWeight);

        long totalValueWithItem = 0;
        int weightWithItem = remainingWeight - weights[itemIndex];
        if (weightWithItem >= 0) {
            totalValueWithItem = values[itemIndex] + knapsack3(values, weights, dp, itemIndex + 1, weightWithItem);
        }

        dp[itemIndex][remainingWeight] = Math.max(totalValueWithItem, totalValueWithoutItem);
        return dp[itemIndex][remainingWeight];
    }

    private static List<Integer> reconstructItems2(int[] values, int[] weights, int maxWeight, long[][] dp) {
        List<Integer> items = new ArrayList<>();
        long currentValue = dp[0][maxWeight];
        reconstructItems2(values, weights, dp, items, 0, maxWeight, currentValue);
        return items;
    }

    private static void reconstructItems2(int[] values, int[] weights, long[][] dp, List<Integer> items, int itemIndex,
                                          int remainingWeight, long currentValue) {
        if (itemIndex == values.length || currentValue == 0) {
            return;
        }
        if (itemIndex == values.length - 1) {
            items.add(values[itemIndex]);
            return;
        }

        if (dp[itemIndex][remainingWeight] != dp[itemIndex + 1][remainingWeight]) {
            items.add(values[itemIndex]);
            reconstructItems2(values, weights, dp, items, itemIndex + 1,
                    remainingWeight - weights[itemIndex], currentValue - values[itemIndex]);
        } else {
            reconstructItems2(values, weights, dp, items, itemIndex + 1, remainingWeight, currentValue);
        }
    }
}
