package com.br.hacker.rank.on.one.one.hack52;

import java.util.Scanner;

/**
 * Created by rene on 03/12/17.
 */
//TODO
public class ConstructTheArray {

    private static final double MOD = Math.pow(10, 9) - 7;

    static long countArray(int n, int k, int x) {
        // Return the number of ways to fill in the array.

        if (n == 3) {
            if (x == 1) {
                return k - 1;
            } else {
                return k - 2;
            }
        }

        long ways = 0;

        ways += (k - 1);
        ways %= MOD;
        ways += (k - 2) * (k - 2);
        ways %= MOD;

        for(int i = 2; i < n - 3; i++) {
            ways += (k - 1) * k;
            ways %= MOD;
            ways += (k - 2) * k * (k - 1) / 2;
            ways %= MOD;
        }

        ways += (k - 2) * (k - 2);
        ways %= MOD;

        return ways;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int x = in.nextInt();
        long answer = countArray(n, k, x);
        System.out.println(answer);
        in.close();
    }

}
