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

        for(int i=0; i < arrayLength; i++) {
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

        System.out.println(oddSum1 + " Expected: 3");
        System.out.println(oddSum2 + " Expected: -1");
        System.out.println(oddSum3 + " Expected: -1");
    }

    private static long oddSum(int[] numbers) {
        if(numbers == null || numbers.length == 0) {
            return -1;
        }

        long oddSum = -1;
        long[] maxOddSum = new long[numbers.length];

        for(int i=0; i < numbers.length; i++) {
            maxOddSum[i] = numbers[i];
        }

        for(int i=1; i < numbers.length; i++) {
            for(int j=0; j < i; j++) {
                if(numbers[i] > numbers[j]
                        && maxOddSum[i] < maxOddSum[j] + numbers[i]){
                    maxOddSum[i] = maxOddSum[j] + numbers[i];
                }
            }
        }

        for(int i=0; i < maxOddSum.length; i++) {
            if(maxOddSum[i] % 2 == 1 && maxOddSum[i] > oddSum) {
                oddSum = maxOddSum[i];
            }
        }

//        long[] sums = new long[numbers.length];
//        sums[0] = numbers[0] > 0 ? numbers[0] : 0;
//
//        for(int i=1; i < numbers.length; i++) {
//            if(numbers[i] >= 0) {
//                sums[i] = sums[i - 1] + numbers[i];
//            } else {
//                sums[i] = sums[i - 1];
//            }
//
//            if(sums[i] % 2 == 1 && sums[i] > oddSum) {
//                oddSum = sums[i];
//            }
//        }

        return oddSum;
    }

}
