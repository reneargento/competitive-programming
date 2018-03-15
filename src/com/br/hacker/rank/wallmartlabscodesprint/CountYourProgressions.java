package com.br.hacker.rank.wallmartlabscodesprint;

/**
 * Created by rene on 29/10/16.
 */

import java.util.Scanner;

/**
 * John has a sequence of numbers with him. He is interested in finding out the number of subsequences of his
 * sequence that are arithmetic progressions.

 A subsequence is a sequence that can be derived from another sequence by deleting some elements without
 changing the order of the remaining elements. For example, the sequence {4,6,9} is a subsequence of {4,5,6,7,8,9}
 obtained after removal of elements 5,7,8. The relation of one sequence being the subsequence of another is a preorder.

 Arithmetic Progression: A sequence is an arithmetic progression if the difference between consecutive elements is constant.
 If the initial term of arithmetic progression is b[1] and the common difference is D then: b[n] = b[1] + (n-1)*D

 Note that empty sequence or a single element sequence are arithmetic progressions.

 Input Format

 The first line of the input is an integer , total number of elements in the sequence. Each of the next ith lines
 contains a single integer representing an element of the sequence.

 Output Format

 Let A be the number of subsequences that are arithmetic progressions. Print the value of A modulo 1000000009 (10^9 + 9).

 https://www.hackerrank.com/contests/walmart-codesprint-algo/challenges/count-your-progressions

 */
public class CountYourProgressions {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int length = scanner.nextInt();
        int[] array = new int[length];

        for(int i = 0; i < length; i++) {
            array[i] = scanner.nextInt();
        }

        int result = countSubsequences(array);

        System.out.println(result);
    }

    private static int countSubsequences(int[] array) {

        //Empty subsequence + 1 element subsequences
        int count = 1 + array.length;
        int difference;
        int elementsInSequenceSoFar;

        for(int i = 0; i < array.length; i++) {
            for(int j = i + 1; j < array.length; j++) {
                difference = Math.abs(array[j] - array[i]);
                count++;
                elementsInSequenceSoFar = 2;

                for(int k = j + 1; k < array.length; k++){
                    if (Math.abs(array[k] - array[i]) == difference * elementsInSequenceSoFar) {
                        count++;
                        elementsInSequenceSoFar++;
                    }
                }
            }
        }

        return count;

    }

}
