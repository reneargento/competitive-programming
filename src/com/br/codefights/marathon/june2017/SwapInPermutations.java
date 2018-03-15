package com.br.codefights.marathon.june2017;

/**
 * Created by rene on 24/06/17.
 */

/**
 * The permutation p of numbers 1, 2, ..., n is called parity-preserving if all the
 * numbers on even positions (p[0], p[2], ...) are even and all the numbers on odd positions (p[1], p[3], ...) are odd.
 * Given a permutation p, determine whether it's possible to swap no more than one pair of its elements to obtain a
 * parity-preserving permutation.

 Example

 For p = [2, 1, 6, 5, 4, 3], the output should be
 swapInPermutation(p) = true.

 The permutation is already parity-preserving.

 For p = [2, 4, 6, 5, 1, 3], the output should be
 swapInPermutation(p) = true.

 We can swap the numbers 4 and 1 to obtain a parity-preserving permutation.

 For p = [3, 4, 6, 5, 1, 2], the output should be
 swapInPermutation(p) = false.

 More than one pair of elements would have to be swapped to obtain a parity-preserving permutation.

 Input/Output

 [time limit] 3000ms (java)

 [input] array.integer p
 An array of integers that represents a permutation of numbers 1, 2, ..., n.

 Guaranteed constraints:
 1 ≤ p.length ≤ 105.

 [output] boolean
 Return true if it's possible to swap no more than one pair of elements to obtain a parity-preserving permutation,
 otherwise return false.
 */
public class SwapInPermutations {

    public static void main(String[] args) {
        int[] array1 = {2, 1, 6, 5, 4, 3};
        System.out.println(swapInPermutation(array1) + " Expected: true");

        int[] array2 = {2, 4, 6, 5, 1, 3};
        System.out.println(swapInPermutation(array2) + " Expected: true");

        int[] array3 = {3, 4, 6, 5, 1, 2};
        System.out.println(swapInPermutation(array3) + " Expected: false");

        int[] array4 = {1};
        System.out.println(swapInPermutation(array4) + " Expected: false");

        int[] array5 = {1, 2};
        System.out.println(swapInPermutation(array5) + " Expected: true");

        int[] array6 = {1, 2, 3};
        System.out.println(swapInPermutation(array6) + " Expected: false");

    }

    private static boolean swapInPermutation(int[] array) {

        int oddWrong = 0;
        int evenWrong = 0;

        for(int i = 0; i < array.length; i++) {
            if (i % 2 == 0 && array[i] % 2 != 0) {
                evenWrong++;
            } else if (i % 2 != 0 && array[i] % 2 == 0) {
                oddWrong++;
            }
        }

        if (evenWrong == 0 && oddWrong == 0) {
            return true;
        }
        if (evenWrong == 1 && oddWrong == 1) {
            return true;
        }

        return false;
    }

}
