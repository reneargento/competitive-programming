package algorithms.dynamic.programming.coin.change;

/**
 * Created by Rene Argento on 01/12/22.
 */
// How many different ways can you make change for an amount, given a list of coins and a limit on the
// number of each coin?
public class CoinChangeWithRestrictions {

    private static long[] countUnrestrictedWays(int[] values, int maxPossibleValue) {
        long[] dp = new long[maxPossibleValue + 1];
        dp[0] = 1;

        for (int value : values) {
            for (int totalValue = value; totalValue < dp.length; totalValue++) {
                dp[totalValue] += dp[totalValue - value];
            }
        }
        return dp;
    }

    // Inclusion-exclusion principle
    // ways = Ways(A) + Ways(B) + Ways(C) + Ways(D) - Ways(AB) - Ways(AC) ... + Ways(ABC) + Ways(ABD) ... - Ways(ABCD) ...
    private static long countWays(int[] values, int[] quantities, int targetValue, long[] dp) {
        long ways = 0;
        int bitmapsNumber = (1 << values.length);

        // Check all permutations in bitmap
        for (int bitmap = 0; bitmap < bitmapsNumber; bitmap++) {
            int currentValue = targetValue;
            int multiplier = 1;

            for (int valueId = 0; valueId < values.length; valueId++) {
                if ((bitmap & (1 << valueId)) > 0) {
                    multiplier = -multiplier;
                    // Subtract values achievable with more coins than allowed
                    currentValue -= (quantities[valueId] + 1) * values[valueId];
                }
            }
            if (currentValue >= 0) {
                ways += multiplier * dp[currentValue];
            }
        }
        return ways;
    }

    public static void main(String[] args) {
        int[] values1 = { 1, 2, 5, 10 };
        long[] dp1 = countUnrestrictedWays(values1, 100000);

        int[] quantities1 = { 3, 2, 3, 1 };
        int targetValue1 = 10;
        long ways1 = countWays(values1, quantities1, targetValue1, dp1);
        System.out.println("Ways: " + ways1 + " Expected: 4");

        int[] quantities2 = { 1000, 2, 2, 2 };
        int targetValue2 = 900;
        long ways2 = countWays(values1, quantities2, targetValue2, dp1);
        System.out.println("Ways: " + ways2 + " Expected: 27");

        int[] values2 = { 10, 20, 30, 40 };
        long[] dp2 = countUnrestrictedWays(values2, 100000);

        int[] quantities3 = { 100, 100, 100, 100 };
        int targetValue3 = 101;
        long ways3 = countWays(values2, quantities3, targetValue3, dp2);
        System.out.println("Ways: " + ways3 + " Expected: 0");
    }
}
