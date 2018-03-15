package com.br.codeforces.educational.round19;

/**
 * Created by rene on 15/04/17.
 */

import java.util.Scanner;

/**
 * You are given sequence a1, a2, ..., an of integer numbers of length n.
 * Your task is to find such subsequence that its sum is odd and maximum among all such subsequences.
 * It's guaranteed that given sequence contains subsequence with odd sum.

 Subsequence is a sequence that can be derived from another sequence by deleting some elements without changing the order
 of the remaining elements.

 You should write a program which finds sum of the best subsequence.

 Input
 The first line contains integer number n (1 ≤ n ≤ 105).

 The second line contains n integer numbers a1, a2, ..., an ( - 104 ≤ ai ≤ 104).
 The sequence contains at least one subsequence with odd sum.

 Output
 Print sum of resulting subseqeuence.

 Examples

 input
 4
 -2 2 -3 1
 output
 3

 input
 3
 2 -5 -3
 output
 -1

 Note
 In the first example sum of the second and the fourth elements is 3.
 */
//http://codeforces.com/contest/797/problem/B
public class OddSum {

    public static void main(String[] args) {
        test();
        //compete();
    }

    private static void compete() {
        Scanner scanner = new Scanner(System.in);

        int arrayLength = scanner.nextInt();
        int[] numbers = new int[arrayLength];

        for(int i = 0; i < arrayLength; i++) {
            numbers[i] = scanner.nextInt();
        }

        long oddSum = oddSum(numbers);
        System.out.println(oddSum);
    }

    private static void test() {
        int[] numbers1 = {-2, 2, -3, 1};
        long oddSum1 = oddSum(numbers1);

        int[] numbers2 = {2, -5, -3};
        long oddSum2 = oddSum(numbers2);

        int[] numbers3 = {};
        long oddSum3 = oddSum(numbers3);

        int[] numbers4 = {4, 4, 4};
        long oddSum4 = oddSum(numbers4);

        int[] numbers5 = {3, 5, 5, -1, 3};
        long oddSum5 = oddSum(numbers5);

        int[] numbers6 = {3, 5, 5, -3, 1};
        long oddSum6 = oddSum(numbers6);

        System.out.println(oddSum1 + " Expected: 3");
        System.out.println(oddSum2 + " Expected: -1");
        System.out.println(oddSum3 + " Expected: -1");
        System.out.println(oddSum4 + " Expected: -1");
        System.out.println(oddSum5 + " Expected: 15");
        System.out.println(oddSum6 + " Expected: 13");
    }

    private static long oddSum(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return -1;
        }

        long positivesSum = 0;
        long maxNegative = Integer.MIN_VALUE;
        long minOddPositive = Integer.MAX_VALUE;

        for(int i = 0; i < numbers.length; i++) {
            if (numbers[i] >= 0) {
                positivesSum += numbers[i];

                if (numbers[i] % 2 == 1 && numbers[i] < minOddPositive) {
                    minOddPositive = numbers[i];
                }
            } else if (numbers[i] % 2 == -1 && numbers[i] > maxNegative) {
                maxNegative = numbers[i];
            }
        }

        if (positivesSum % 2 == 1) {
            return positivesSum;
        } else {
            if (maxNegative != Integer.MIN_VALUE && minOddPositive != Integer.MAX_VALUE) {
                long addition = Math.max(maxNegative, -minOddPositive);
                return positivesSum + addition;
            } else if (maxNegative != Integer.MIN_VALUE) {
                return positivesSum + maxNegative;
            } else if (minOddPositive != Integer.MAX_VALUE){
                return positivesSum - minOddPositive;
            }
        }

        return -1;
    }

}
