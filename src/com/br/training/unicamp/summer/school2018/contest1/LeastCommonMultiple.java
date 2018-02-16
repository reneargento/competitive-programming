package com.br.training.unicamp.summer.school2018.contest1;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by rene on 22/01/18.
 */
//http://codeforces.com/group/3qadGzUdR4/contest/101704/problem/O
public class LeastCommonMultiple {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();

        BigInteger lcm = BigInteger.ONE;

        for(int i = 1; i <= number; i++) {
            lcm = lcm(lcm, BigInteger.valueOf(i));
        }

        System.out.println(lcm);
    }

    private static BigInteger gcd(BigInteger number1, BigInteger number2) {
        while (number2.compareTo(BigInteger.ZERO) > 0) {
            BigInteger temp = number2;
            number2 = number1.mod(number2);
            number1 = temp;
        }
        return number1;
    }

    private static BigInteger lcm(BigInteger number1, BigInteger number2) {
        return number1.multiply(number2.divide(gcd(number1, number2)));
    }

}
