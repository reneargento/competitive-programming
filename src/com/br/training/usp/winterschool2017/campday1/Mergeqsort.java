package com.br.training.usp.winterschool2017.campday1;

import java.util.Scanner;

/**
 * Created by rene on 10/07/17.
 */
public class Mergeqsort {

    private static long[] dp = new long[1001];
    private static long asterisks = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();

        for(int i = 0; i < 1000; i++) {
            dp[i] = -1;
        }

        if (arraySize > 1000) {
            for(int i = 1; i <= 1000; i++) {
                fillDpArray(i);
            }
        }

        sqrtMergesort(arraySize);

        System.out.println(asterisks);
    }

    private static long sqrtMergesort(int n) {
        if (n == 1) {
            return 0;
        }

        asterisks += n;

        int nLeft = (int) Math.floor(Math.sqrt(n));
        int nRight = n - nLeft;

        long left;
        if (nLeft < dp.length) {
            left = dp[nLeft];

            if (left == -1) {
                left = sqrtMergesort(nLeft);
            } else {
                asterisks += left;
            }
        } else {
            left = sqrtMergesort(nLeft);
        }

        long right;
        if (nRight < dp.length) {
            right = dp[nRight];

            if (right == -1) {
                right = sqrtMergesort(nRight);
            } else {
                asterisks += right;
            }
        } else {
            right = sqrtMergesort(nRight);
        }

        return left + right;
    }

    private static void fillDpArray(int size) {
        sqrtMergesort(size);
        dp[size] = asterisks;
        asterisks = 0;
    }

}
