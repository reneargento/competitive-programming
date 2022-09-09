package algorithms.dynamic.programming;

/**
 * Created by Rene Argento on 29/12/16.
 */
// Based on https://www.hackerrank.com/challenges/coin-change
// How many different ways can you make change for an amount, given a list of coins?
public class CoinChangeProblem {

    public static void main(String[] args) {
        int[] coins1 = {1, 2, 3};
        int targetExchange1 = 4;
        long coinChangePossibilities1 = calculateCoinChangePossibilities(coins1, targetExchange1);
        System.out.println("Coin change possibilities 1: " + coinChangePossibilities1 + " Expected: 4");

        int[] coins2 = {2, 5, 3, 6};
        int targetExchange2 = 10;
        long coinChangePossibilities2 = calculateCoinChangePossibilities(coins2, targetExchange2);
        System.out.println("Coin change possibilities 2: " + coinChangePossibilities2 + " Expected: 5");
    }

    // Based on https://www.geeksforgeeks.org/coin-change-dp-7/
    // Using O(n * m) runtime complexity and O(n) space
    private static long calculateCoinChangePossibilities(int[] coins, int targetExchange) {
        long[] dp = new long[targetExchange + 1];

        // Base case - for 0 exchange, there is 1 solution (no coins)
        dp[0] = 1;

        for (int i = 0; i < coins.length; i++) {
            for(int j = coins[i]; j <= targetExchange; j++) {
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[targetExchange];
    }

    // O(n * m) runtime complexity and O(n m) space
    private static long calculateCoinChangePossibilities2(int[] coins, int targetExchange) {
        if (coins == null) {
            return 1;
        }
        long[][] dp = new long[coins.length][targetExchange + 1];

        // Base cases
        // Check if targetExchange is divisible by the first coin: if it is, set the value to 1 (possible solution)
        for (int j = 0; j < dp[0].length; j++) {
            if (j % coins[0] == 0) {
                dp[0][j] = 1;
            }
        }

        // 0 targetExchange = 1 possibility (using no coins)
        for(int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1 ; j < dp[0].length; j++) {
                if (j >= coins[i]) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[coins.length - 1][targetExchange];
    }
}
