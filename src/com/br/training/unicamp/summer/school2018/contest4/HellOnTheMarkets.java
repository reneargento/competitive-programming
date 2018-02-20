package com.br.training.unicamp.summer.school2018.contest4;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 26/01/18.
 */
// TODO
public class HellOnTheMarkets {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int contractsNumber = scanner.nextInt();
        int[] contracts = new int[contractsNumber];
        long sum = 0;
        int[] results = new int[contractsNumber];

        for(int i = 0; i < contracts.length; i++) {
            contracts[i] = scanner.nextInt();
            sum += contracts[i];
        }

        if (sum % 2 != 0) {
            System.out.println("No");
        } else {

            long sum1 = 0;
            long sum2 = 0;

            for(int i = 0; i < contracts.length; i++) {
                if (sum1 <= sum2) {
                    sum1 += contracts[i];
                    results[i] = 1;
                } else {
                    sum2 += contracts[i];
                    results[i] = -1;
                }
            }

            if (sum1 != sum2) {
                System.out.println("No");
            } else {
                StringJoiner stringJoiner = new StringJoiner(" ");

                for(int i = 0; i < results.length; i++) {
                    stringJoiner.add(String.valueOf(results[i]));
                }

                System.out.println(stringJoiner);
            }
        }
    }

}
