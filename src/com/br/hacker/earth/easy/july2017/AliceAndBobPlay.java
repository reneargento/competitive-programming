package com.br.hacker.earth.easy.july2017;

import java.util.Scanner;

/**
 * Created by rene on 01/07/17.
 */
public class AliceAndBobPlay {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        for(int i = 0; i < tests; i++) {
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();

            if (columns % 2 != 0) {
                if (columns == 1 && rows > 1) {
                    System.out.println("Bob");
                } else {
                    if (rows % 2 != 0) {
                        System.out.println("Alice");
                    } else {
                        System.out.println("Bob");
                    }
                }
            } else {
               System.out.println("Bob");
            }
        }
    }

}
