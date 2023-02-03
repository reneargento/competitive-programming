package algorithms.dynamic.programming.coin.change;

import java.util.Arrays;

/**
 * Created by Rene Argento on 01/12/22.
 */
// Given a list of coins and a target amount, what is the minimal number of coins that can be used to sum to that
// target amount?
public class CoinChangeMinimalCoins {

    // O(coinTypes * targetExchange) runtime complexity and O(targetExchange) space
    private static long[] calculateMinimumCoinsToMakeChange(int[] coins, int targetExchange) {
        long[] dp = new long[targetExchange + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int coinValue : coins) {
            for (int currentSum = coinValue; currentSum < dp.length; currentSum++) {
                if (dp[currentSum - coinValue] != Integer.MAX_VALUE) {
                    long coinsNeeded = dp[currentSum];
                    long coinsNeededAlternate = dp[currentSum - coinValue] + 1;
                    dp[currentSum] = Math.min(coinsNeeded, coinsNeededAlternate);
                }
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        int[] coins1 = { 25, 10, 5 };
        int targetExchange1 = 30;
        long[] dp1 = calculateMinimumCoinsToMakeChange(coins1, targetExchange1);
        System.out.println("Coins: " + dp1[targetExchange1] + " Expected: 2");

        int targetExchange2 = 1;
        System.out.print("Coins: ");
        System.out.print((dp1[targetExchange2] != Integer.MAX_VALUE) ? dp1[targetExchange2] : "Not possible");
        System.out.println(" Expected: Not possible");

        int[] coins2 = { 9, 6, 5, 1 };
        int targetExchange3 = 11;
        long[] dp2 = calculateMinimumCoinsToMakeChange(coins2, 32);
        System.out.println("Coins: " + dp2[targetExchange3] + " Expected: 2");

        int targetExchange4 = 32;
        System.out.println("Coins: " + dp2[targetExchange4] + " Expected: 4");
    }
}
