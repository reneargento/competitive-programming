package com.br.russian.code.cup.qualification.b;

/**
 * Created by rene on 16/04/17.
 */

import java.util.Scanner;

/**
 * Least common multiple is a well known term in math, it is the smallest number that is divisible by any of the given numbers.
 * The concept of least common multiple can be generalized to other objects in math, for example to fractions.

 You are given two irreducible fractions.
 Find their least common multiple — the smallest positive irreducible fraction p / q such that the result of the
 division of p / q to any of the given fractions is integer.

 Input format
 Input contains several test cases.
 The first line of input contains integer t — the number of test cases (1 ≤ t ≤ 50 000).

 Each test case is described as a single line that contains four positive integers a, b, c, d,
 that correspond to irreducible fractions a / b and c / d (1 ≤ a, b, c, d ≤ 10^9).
 It is guaranteed that a / b and c / d are both irreducible.

 Output format
 For each test case output one line.
 Print two integers: numerator and denominator of the fraction that is a least common multiple of the given a / b and c / d fractions.

 Examples

 Input data
 2
 9 5 12 5
 1 10 3 100

 Output data
 36 5
 3 10
 */
public class LeastCommonMultiple {

    public static void main(String[] args) {
        //test();
        compete();
    }

    private static void test() {
        System.out.println(getLCM(9, 5, 12, 5) + " Expected: 36 5");
        System.out.println(getLCM(1, 10, 3, 100) + " Expected: 3 10");
    }

    private static void compete() {
        Scanner sc = new Scanner(System.in);
        int numberOfTests = Integer.parseInt(sc.nextLine());

        for(int t=0; t < numberOfTests; t++) {
            int number1 = sc.nextInt();
            int number2 = sc.nextInt();
            int number3 = sc.nextInt();
            int number4 = sc.nextInt();

            String lcm = getLCM(number1, number2, number3, number4);
            System.out.println(lcm);
        }
    }

    private static String getLCM(long number1, long number2, long number3, long number4) {
        long lcm = lcm(number1, number3);
        long gcd = gcd(number2, number4);

        return lcm + " " + gcd;
    }

    private static long lcm(long number1, long number2) {
        return number1 * (number2 / gcd(number1, number2));
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

}
