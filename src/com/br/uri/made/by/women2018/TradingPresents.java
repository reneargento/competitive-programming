package com.br.uri.made.by.women2018;

import java.util.Scanner;

/**
 * Created by Rene Argento on 10/03/18.
 */
public class TradingPresents {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for(int t = 0; t < tests; t++) {
            int gifts = scanner.nextInt();

            int[] roseGifts = new int[gifts];
            int[] hugoGifts = new int[gifts];

            int sumRose = 0;
            int sumHugo = 0;

            for(int i = 0; i < gifts; i++) {
                roseGifts[i] = scanner.nextInt();
                sumRose += roseGifts[i];
            }

            for(int i = 0; i < gifts; i++) {
                hugoGifts[i] = scanner.nextInt();
                sumHugo += hugoGifts[i];
            }

            int difference = Math.abs(sumRose - sumHugo);

            if (difference == 0) {
                System.out.println(0);
                continue;
            }

            int result;

            if (sumRose > sumHugo) {
                result = getMinimumDifference(roseGifts, hugoGifts, difference);
            } else {
                result = getMinimumDifference(hugoGifts, roseGifts, difference);
            }

            System.out.println(result);
        }
    }

    private static int getMinimumDifference(int[] gifts1, int[] gifts2, int difference) {
        return 0;
    }

}
