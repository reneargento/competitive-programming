package algorithms.dynamic.programming.coin.change;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 29/12/16.
 */
// Based on https://www.hackerrank.com/challenges/coin-change
// How many different ways can you make change for an amount, given a list of coins?
public class CoinChangeCountWays {

    public static void main(String[] args) {
        int[] coins1 = { 1, 2, 3 };
        int targetExchange1 = 4;
        long[] dp1 = calculateCoinChangePossibilities(coins1, targetExchange1);
        long coinChangePossibilities1 = dp1[targetExchange1];
        System.out.println("Coin change possibilities 1: " + coinChangePossibilities1 + " Expected: 4");

        int[] coins2 = { 2, 5, 3, 6 };
        int targetExchange2 = 10;
        long[] dp2 = calculateCoinChangePossibilities(coins2, targetExchange2);
        long coinChangePossibilities2 = dp2[targetExchange2];
        System.out.println("Coin change possibilities 2: " + coinChangePossibilities2 + " Expected: 5");

        int[] coins3 = { 4, 5, 8 };
        int targetExchange3 = 14;
        long[] dp3 = calculateCoinChangePossibilities(coins3, targetExchange3);
        long coinChangePossibilities3 = dp3[targetExchange3];
        System.out.println("Coin change possibilities 3: " + coinChangePossibilities3 + " Expected: 1");
        List<Integer> coinsUsed = computeCoinsUsed(coins3, targetExchange3, dp3);
        System.out.print("Coins used:");
        for (int coin : coinsUsed) {
            System.out.print(" " +coin);
        }
        System.out.println();
        System.out.println("Coins expected: 4 5 5");
    }

    // Based on https://www.geeksforgeeks.org/coin-change-dp-7/
    // Using O(coinTypes * targetExchange) runtime complexity and O(targetExchange) space
    private static long[] calculateCoinChangePossibilities(int[] coins, int targetExchange) {
        long[] dp = new long[targetExchange + 1];

        // Base case - for 0 exchange, there is 1 solution (no coins)
        dp[0] = 1;

        for (int coinValue : coins) {
            for (int currentSum = coinValue; currentSum <= targetExchange; currentSum++) {
                dp[currentSum] += dp[currentSum - coinValue];
            }
        }
        return dp;
    }

    private static List<Integer> computeCoinsUsed(int[] coins, int targetExchange, long[] dp) {
        List<Integer> coinsUsed = new ArrayList<>();
        for (int coinValue : coins) {
            while (targetExchange >= coinValue && dp[targetExchange - coinValue] == 1) {
                coinsUsed.add(coinValue);
                targetExchange -= coinValue;
            }
        }
        return coinsUsed;
    }

    // O(coinTypes * targetExchange) runtime complexity and O(coinTypes * targetExchange) space - Top-down approach
    private static long[][] calculateCoinChangePossibilities2(int[] coins, int targetExchange) {
        // dp[i][c] = Number of ways to sum to cost with items >= i
        long[][] dp = new long[coins.length][targetExchange + 1];
        for (int item = 0; item < coins.length; item++) {
            Arrays.fill(dp[item], -1);
        }
        calculateCoinChangePossibilities2(coins, dp, 0, targetExchange);
        return dp;
    }

    private static long calculateCoinChangePossibilities2(int[] coins, long[][] dp, int index, int currentSum) {
        if (currentSum == 0) {
            return 1;
        }
        if (index == coins.length || currentSum < 0) {
            return 0;
        }
        if (dp[index][currentSum] != -1) {
            return dp[index][currentSum];
        }
        // Move on or use item
        dp[index][currentSum] = calculateCoinChangePossibilities2(coins, dp, index + 1, currentSum) +
                calculateCoinChangePossibilities2(coins, dp, index, currentSum - coins[index]);
        return dp[index][currentSum];
    }

    private static void computeCoinsUsed2(int[] coins, long[][] dp, List<Integer> indexes, int index, int currentSum) {
        if (currentSum == 0) {
            return;
        }
        if (dp[index + 1][currentSum] != 1) {
            indexes.add(coins[index]);
            computeCoinsUsed2(coins, dp, indexes, index, currentSum - coins[index]);
        } else {
            computeCoinsUsed2(coins, dp, indexes, index + 1, currentSum);
        }
    }

    // O(coinTypes * targetExchange) runtime complexity and O(coinTypes * targetExchange) space - Bottom-up approach
    private static long calculateCoinChangePossibilities3(int[] coins, int targetExchange) {
        if (coins == null) {
            return 1;
        }
        long[][] dp = new long[coins.length][targetExchange + 1];

        // Base cases
        // Check if targetExchange is divisible by the first coin: if it is, set the value to 1 (possible solution)
        for (int currentSum = 0; currentSum < dp[0].length; currentSum++) {
            if (currentSum % coins[0] == 0) {
                dp[0][currentSum] = 1;
            }
        }

        // 0 targetExchange = 1 possibility (using no coins)
        for(int coinType = 0; coinType < dp.length; coinType++) {
            dp[coinType][0] = 1;
        }

        for (int coinType = 1; coinType < dp.length; coinType++) {
            for (int currentSum = 1; currentSum < dp[0].length; currentSum++) {
                if (currentSum >= coins[coinType]) {
                    dp[coinType][currentSum] = dp[coinType - 1][currentSum] + dp[coinType][currentSum - coins[coinType]];
                } else {
                    dp[coinType][currentSum] = dp[coinType - 1][currentSum];
                }
            }
        }
        return dp[coins.length - 1][targetExchange];
    }
}
