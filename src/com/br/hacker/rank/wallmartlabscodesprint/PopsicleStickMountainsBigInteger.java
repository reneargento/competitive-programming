package com.br.hacker.rank.wallmartlabscodesprint;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by rene on 29/10/16.
 */

/**
 * https://www.hackerrank.com/contests/walmart-codesprint-algo/challenges/popsicle-stick-mountains
 */
public class PopsicleStickMountainsBigInteger {

    static long mod = (long) Math.pow(10, 9) + 7;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int trips = scanner.nextInt();
        BigInteger dp[] = new BigInteger[4001];
        dp[0] = BigInteger.valueOf(0);
        dp[2] = BigInteger.valueOf(1);
        dp[4] = BigInteger.valueOf(3);

        BigInteger[] mountains = new BigInteger[dp.length];
        mountains[2] = BigInteger.valueOf(1);
        mountains[4] = BigInteger.valueOf(2);

        BigInteger[] factorials = new BigInteger[dp.length];
        factorials[1] = BigInteger.valueOf(1);
        factorials[2] = BigInteger.valueOf(2);

        int sticks;

        for(int i = 0; i < trips; i++) {
            sticks = scanner.nextInt();
            System.out.println(countMountains(mountains, factorials, dp, sticks).longValue() % mod);
        }
    }

    private static BigInteger countMountains(BigInteger[] mountains, BigInteger[] factorials, BigInteger[] dp, int sticks) {

        int lastComputedCountIndex = 4;

        if (dp[sticks] == null || dp[sticks].longValue() == 0) {

            for(int i = sticks-2; i>= 4; i-=2) {
                if (dp[sticks] != null && dp[i].longValue() != 0) {
                    lastComputedCountIndex = i;

                    break;
                }
            }

            for(int i = lastComputedCountIndex +2; i <= sticks; i +=2) {

                computeFactorial(factorials, i);

                mountains[i] = factorials[i].divide(factorials[(i / 2) + 1].multiply(factorials[i / 2]));

                dp[i] = dp[i - 2].add(mountains[i]).mod(BigInteger.valueOf(mod));
            }
        }

        return dp[sticks];
    }

    private static void computeFactorial(BigInteger[] factorials, int n) {

        int lastComputedCountIndex = 2;

        for(int i = n-1; i >= 2; i--) {
            if (factorials[i] != null && factorials[i].longValue() != 0) {
                lastComputedCountIndex = i;
            }
        }

        for(int i = lastComputedCountIndex + 1; i <= n; i++) {
            factorials[i] = factorials[i-1].multiply(BigInteger.valueOf(i));
        }
    }

}
