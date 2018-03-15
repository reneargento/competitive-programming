package com.br.codefights.marathon.april2017;

/**
 * Created by rene on 29/04/17.
 */

import java.util.HashSet;
import java.util.Set;

/**
 * The prime xor operation, a # b, takes two numbers and returns the product of the prime numbers that
 * divide either a or b, but not both a and b.

 Given two numbers a and b, return the result of operation a # b.

 Example

 For a = 18 and b = 10, the output should be
 primeXor(a, b) = 15.

 a = 2 * 3 * 3 and b = 2 * 5: Both factorizations contain the prime number 2, so 2 is not a candidate.
 3 appears only in the factorization of a and 5 appears only in the factorization of b, so a # b = 3 * 5 = 15.
 Note that each prime number is counted once in a # b, even though 3 appears twice in the factorization of a.

 Input/Output

 [time limit] 3000ms (java)
 [input] integer a

 Guaranteed constraints:
 2 ≤ a ≤ 107.

 [input] integer b

 Guaranteed constraints:
 2 ≤ b ≤ 107.

 [output] integer64

 The result of operation a # b.
 */
public class PrimeXor {

    public static void main(String[] args) {
        System.out.println(primeXor(18, 10) + " Expected: 15");
        System.out.println(primeXor(18, 110) + " Expected: 165");
        System.out.println(primeXor(13, 2) + " Expected: 26");
        System.out.println(primeXor(2, 44) + " Expected: 11");
        System.out.println(primeXor(24, 78) + " Expected: 13");
        System.out.println(primeXor(60, 171) + " Expected: 190");
        System.out.println(primeXor(805, 840) + " Expected: 138");
        System.out.println(primeXor(9812, 10812) + " Expected: 6630459");
    }

    private static long primeXor(int a, int b) {

        Set<Integer> primesA = new HashSet<>();
        Set<Integer> primesB = new HashSet<>();
        long product = 1;

        for (int i = 2; i <= a; i++) {
            while (a % i == 0) {
                primesA.add(i);
                a /= i;
            }
        }

        for (int i = 2; i <= b; i++) {
            while (b % i == 0) {
                primesB.add(i);
                b /= i;
            }
        }

        for(Integer value : primesA) {
            if (!primesB.contains(value)) {
                product *= value;
            }
        }

        for(Integer value : primesB) {
            if (!primesA.contains(value)) {
                product *= value;
            }
        }

        return product;
    }

    private static long primeXorOptimized(int a, int b) {
        long res = 1;

        for (int i = 2; i * i <= Math.max(a,b); i++) {
            if ((a % i == 0) ^ (b % i == 0)) {
                res *= i;
            }

            while (a % i == 0) {
                a /= i;
            }
            while (b % i == 0) {
                b /= i;
            }
        }
        if (a > 1 && a != b) res = res * a;
        if (b > 1 && a != b) res = res * b;
        return res;
    }

}
