package com.br.hacker.rank.wallmartlabscodesprint;

import java.util.Scanner;

/**
 * Created by rene on 29/10/16.
 */

/**
 * https://www.hackerrank.com/contests/walmart-codesprint-algo/challenges/popsicle-stick-mountains
 * 4000 -> 342113839
 */
public class PopsicleStickMountains {

    static long mod = (long) Math.pow(10, 9) + 7;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int trips = scanner.nextInt();
        long dp[] = new long[4001];
        dp[0] = 0;
        dp[2] = 1;
        dp[4] = 3;

        long[] mountains = new long[dp.length];
        mountains[2] = 1;
        mountains[4] = 2;

        long[] factorials = new long[dp.length];
        factorials[1] = 1;
        factorials[2] = 2;

        int sticks;

        for(int i = 0; i < trips; i++) {
            sticks = scanner.nextInt();
            System.out.println(countMountains(mountains, factorials, dp, sticks));
        }
    }

    private static long countMountains(long[] mountains, long[] factorials, long[] dp, int sticks) {

        int lastComputedCountIndex = 4;

        if (dp[sticks] == 0) {

            for(int i = sticks-2; i >= 4; i-=2) {
                if (dp[i] != 0) {
                    lastComputedCountIndex = i;

                    break;
                }
            }

            for(int i = lastComputedCountIndex +2; i <= sticks; i +=2) {

                computeFactorial(factorials, i);

                if (i == 14) {
                    int a = 1;
                }

                mountains[i] = (factorials[i] / (factorials[(i / 2) + 1] * (factorials[i / 2]))) % mod;

                dp[i] = (dp[i - 2] + mountains[i]) % mod;
            }
        }

        return dp[sticks];
    }

    private static void computeFactorial(long[] factorials, int n) {

        int lastComputedCountIndex = 2;

        for(int i = n - 1; i >= 2; i--) {
            if (factorials[i] != 0) {
                lastComputedCountIndex = i;
            }
        }

        for(int i = lastComputedCountIndex + 1; i <= n; i++) {
            factorials[i] = (factorials[i-1] * i) % mod;
        }
    }

}
