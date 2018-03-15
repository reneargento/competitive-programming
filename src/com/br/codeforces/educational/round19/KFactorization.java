package com.br.codeforces.educational.round19;

import java.util.ArrayList;
import java.util.List;
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

 input
 100000 2
 output
 2 50000

 input
 100000 20
 output
 -1

 */
//http://codeforces.com/contest/797/problem/A
public class KFactorization {

    public static void main(String[] args) {
        //test();
        compete();
    }

    private static void test() {
        int[] factors1 = kFactorization(5, 1);
        int[] factors2 = kFactorization(5, 2);
        int[] factors3 = kFactorization(1024, 5);
        int[] factors4 = kFactorization(100000, 2);
        int[] factors5 = kFactorization(100000, 20);

        if (factors1[0] == -1) {
            System.out.println(-1);
        } else {
            for(int i = 0; i < factors1.length; i++) {
                System.out.print(factors1[i]);

                if (i != factors1.length - 1) {
                    System.out.print(" ");
                }
            }
        }

        System.out.println("\nExpected: 5");

        if (factors2[0] == -1) {
            System.out.println(-1);
        } else {
            for(int i = 0; i < factors2.length; i++) {
                System.out.print(factors2[i]);

                if (i != factors2.length - 1) {
                    System.out.print(" ");
                }
            }
        }

        System.out.println("Expected: -1");

        if (factors3[0] == -1) {
            System.out.println(-1);
        } else {
            for(int i = 0; i < factors3.length; i++) {
                System.out.print(factors3[i]);

                if (i != factors3.length - 1) {
                    System.out.print(" ");
                }
            }
        }

        System.out.println("\nExpected: 2 64 2 2 2");

        if (factors4[0] == -1) {
            System.out.println(-1);
        } else {
            for(int i = 0; i < factors4.length; i++) {
                System.out.print(factors4[i]);

                if (i != factors4.length - 1) {
                    System.out.print(" ");
                }
            }
        }

        System.out.println("\nExpected: 2 50000");

        if (factors5[0] == -1) {
            System.out.println(-1);
        } else {
            for(int i = 0; i < factors5.length; i++) {
                System.out.print(factors5[i]);

                if (i != factors5.length - 1) {
                    System.out.print(" ");
                }
            }
        }

        System.out.println("Expected: -1");
    }

    private static void compete() {
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
        int numbersRequired = scanner.nextInt();;

        int[] output = kFactorization(number, numbersRequired);
        if (output[0] == -1) {
            System.out.println(-1);
        } else {
            for(int i = 0; i < output.length; i++) {
                System.out.print(output[i]);

                if (i != output.length - 1) {
                    System.out.print(" ");
                }
            }
        }
    }

    private static int[] kFactorization(int number, int numbersRequired) {
        int[] output = new int[numbersRequired];

        List<Integer> factors = new ArrayList<>();

        //Get all factors
        for(int i = 2; i <= number && number != 1; i++) {

            while(number % i == 0) {
                factors.add(i);
                number /= i;
            }
        }

        while (factors.size() > numbersRequired && factors.size() >= 2) {
            int resultingFactor = factors.get(0) * factors.get(1);

            factors.remove(0);
            factors.remove(0);

            factors.add(resultingFactor);
        }

        if (factors.size() == numbersRequired) {
            for(int i = 0; i < factors.size(); i++) {
                output[i] = factors.get(i);
            }
        } else {
            output[0] = -1;
        }

        return output;
    }

}
