package com.br.hacker.rank.hour.rank22;

import java.util.Scanner;

/**
 * Created by rene on 02/07/17.
 */
public class ParityGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int length = scanner.nextInt();
        int[] array = new int[length];

        int sum = 0;
        boolean hasOddNumber = false;

        for(int i = 0; i < length; i++) {
            array[i] = scanner.nextInt();
            sum += array[i];

            if ((array[i] & 1) == 1) {
                hasOddNumber = true;
            }
        }

        if ((sum & 1) == 0) {
            System.out.println("0");
        } else {
            if (array.length == 1) {
                System.out.println("-1");
            } else {
                if (hasOddNumber) {
                    System.out.println("1");
                } else {
                    System.out.println("2");
                }
            }
        }
    }

}
