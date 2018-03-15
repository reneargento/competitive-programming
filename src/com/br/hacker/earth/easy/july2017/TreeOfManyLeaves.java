package com.br.hacker.earth.easy.july2017;

import java.util.Scanner;

/**
 * Created by rene on 01/07/17.
 */
public class TreeOfManyLeaves {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        for(int i = 0; i < tests; i++) {
            int leaves = scanner.nextInt();

            int result = 0;

            for(int j = 0; j < leaves; j++) {
                result = result ^ scanner.nextInt();
            }

            System.out.println(result);
        }
    }
}
