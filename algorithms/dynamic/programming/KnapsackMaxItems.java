package algorithms.dynamic.programming;

import java.io.IOException;

/**
 * Created by Rene Argento on 23/12/24.
 */
// Given n items, each with its weight W and a maximum knapsack weight S, compute the maximum number
// of the items that we can carry, if we can either ignore or take a particular item.
// If there is a draw, select the solution with the highest weight.
// Based on https://judge.beecrowd.com/pt/challenges/view/914/4
public class KnapsackMaxItems {

    private static class Result {
        int totalItems;
        long totalWeight;

        public Result(int totalItems, long totalWeight) {
            this.totalItems = totalItems;
            this.totalWeight = totalWeight;
        }
    }

    private static class Bag {
        int items;
        long totalWeight;

        public Bag(int items, long totalWeight) {
            this.items = items;
            this.totalWeight = totalWeight;
        }
    }

    public static void main(String[] args) throws IOException {
        int maxWeight = 60;
        int[] weights = { 12, 34, 16, 27, 6, 14, 13, 8, 22, 17, 25, 21, 18 };
        Result result = knapsack(weights, maxWeight);

        System.out.println("Items: " + result.totalItems);
        System.out.println("Weight: " + result.totalWeight);
        System.out.println("Expected: Items 5, Weight: 60");
    }

    private static Result knapsack(int[] weights, int maxWeight) {
        Bag[][] dp = new Bag[weights.length + 1][maxWeight + 1];

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = new Bag(0, 0);
            }
        }

        for (int item = 1; item < dp.length; item++) {
            for (int weight = 0; weight <= maxWeight; weight++) {
                Bag bagWithoutItem = dp[item - 1][weight];
                Bag bagWithItem = new Bag(0, 0);

                if (weights[item - 1] <= weight) {
                    int newItems = dp[item - 1][weight - weights[item - 1]].items + 1;
                    long newWeight = dp[item - 1][weight - weights[item - 1]].totalWeight + weights[item - 1];
                    bagWithItem = new Bag(newItems, newWeight);
                }

                if (bagWithItem.items > bagWithoutItem.items
                        || (bagWithItem.items == bagWithoutItem.items
                        && bagWithItem.totalWeight > bagWithoutItem.totalWeight)) {
                    dp[item][weight] = bagWithItem;
                } else {
                    dp[item][weight] = bagWithoutItem;
                }
            }
        }

        int totalItems = dp[dp.length - 1][dp[0].length - 1].items;
        long totalWeight = dp[dp.length - 1][dp[0].length - 1].totalWeight;
        return new Result(totalItems, totalWeight);
    }
}
