package com.br.hacker.rank.on.one.one.hack52;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by rene on 03/12/17.
 */
public class NumberGroups {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        long answer = sumOfGroup(k);

        System.out.println(answer);
        in.close();
    }

    static long sumOfGroup(int k) {
        // Return the sum of the elements of the k'th group.

        BigInteger kValue = BigInteger.valueOf(k);
        BigInteger kValueMinus1 = BigInteger.valueOf(k - 1);
        BigInteger leftRange = kValue.multiply(kValueMinus1);

        BigInteger currentNumber = leftRange.add(BigInteger.valueOf(1));
        BigInteger sum = BigInteger.ZERO;

        for(int number = 0; number < k; number++) {
            sum = sum.add(currentNumber);
            currentNumber = currentNumber.add(BigInteger.valueOf(2));
        }

        return sum.longValue();
    }

}
