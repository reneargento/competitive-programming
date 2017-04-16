package com.br.codeforces.educational.round19;

import java.util.Scanner;

/**
 * Created by rene on 15/04/17.
 */

/**
 * Given a positive integer n, find k integers (not necessary distinct) such that all these integers are strictly greater than 1,
 * and their product is equal to n.

 Input
 The first line contains two integers n and k (2 ≤ n ≤ 100000, 1 ≤ k ≤ 20).

 Output
 If it's impossible to find the representation of n as a product of k numbers, print -1.

 Otherwise, print k integers in any order. Their product must be equal to n. If there are multiple answers, print any of them.

 Examples

 input
 5 1
 output
 5

 input
 5 2
 output
 -1

 input
 1024 5
 output
 2 64 2 2 2

 */
//http://codeforces.com/contest/797/problem/A
public class KFactorization {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
        int numbersRequired = scanner.nextInt();;

        int[] output = kFactorization(number, numbersRequired);
        if(output[0] == -1) {
            System.out.println(-1);
        } else {
            for(int i=0; i < output.length; i++) {
                System.out.print(output[i]);

                if(i != output.length - 1) {
                    System.out.print(" ");
                }
            }
        }

    }

    private static int[] kFactorization(int number, int numbersRequired) {
        int[] output = new int[numbersRequired];

        return output;
    }

}
