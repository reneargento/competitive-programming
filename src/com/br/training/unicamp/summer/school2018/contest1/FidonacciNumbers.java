package com.br.training.unicamp.summer.school2018.contest1;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by rene on 22/01/18.
 */
// http://codeforces.com/group/3qadGzUdR4/contest/101704/problem/Y
public class FidonacciNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();

        if (number == 0) {
            System.out.println(0);
        } else if (number == 1 || number == 2) {
            System.out.println(1);
        } else {
            BigInteger[] values = new BigInteger[number + 1];
            values[0] = BigInteger.ZERO;
            values[1] = BigInteger.ONE;
            values[2] = BigInteger.ONE;

            BigInteger number1 = BigInteger.ZERO;
            BigInteger number2 = BigInteger.ONE;
            BigInteger number3 = BigInteger.ONE;

            BigInteger next = BigInteger.ZERO;

            for(int i = 3; i <= number; i++) {
                next = number3.add(number1);

                number1 = number2;
                number2 = number3;
                number3 = next;
            }

            System.out.println(next);
        }
    }

}
