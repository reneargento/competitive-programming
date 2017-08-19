package com.br.algs.reference.algorithms;

/**
 * Created by rene on 16/04/17.
 */
public class MathUtil {

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static long lcm(long number1, long number2) {
        return (number1 * number2) / gcd(number1, number2);
    }

    private static String reduceFraction(long dividend, long divisor) {

        long gcd = gcd(dividend, divisor);
        long newDividend = dividend / gcd;
        long newDivisor = divisor / gcd;

        return newDividend + "/" + newDivisor;
    }

    //Fast exponentiation in mod 10^9 + 7
    private static long mod = 1000000007;

    private static long fastExponentiation(long base, int exponent) {
        if(exponent == 0) {
            return 1;
        }

        if(exponent == 1) {
            return base;
        }

        if(exponent % 2 == 0) {
            return fastExponentiation((base * base) % mod, exponent / 2);
        }

        if(exponent % 2 == 1) {
            return (base * fastExponentiation((base * base) % mod, exponent / 2)) % mod;
        }

        return -1;
    }

}
