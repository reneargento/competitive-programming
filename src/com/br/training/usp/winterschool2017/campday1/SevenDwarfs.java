package com.br.training.usp.winterschool2017.campday1;

import java.util.Scanner;

/**
 * Created by rene on 10/07/17.
 */
public class SevenDwarfs {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int packages = scanner.nextInt();
        long[] pack = new long[packages];

        for(int i = 0; i < packages; i++) {
            pack[i] = scanner.nextLong();
        }

        long[] dp = new long[7];
        long[] aux = new long[7];

        for(int i = 0; i < pack.length; i++) {
            //Copy current dp values
            for(int j = 0; j < dp.length; j++) {
                aux[j] = dp[j];
            }

            for(int j = 0; j < dp.length; j++) {
                long sum = aux[j] + pack[i];
                int mod = (int) sum % 7;

                if (dp[mod] < sum) {
                    dp[mod] = sum;
                }
            }
        }

        System.out.println(dp[0]);
    }

}
