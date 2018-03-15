package com.br.codefights.marathon.june2017;

/**
 * Created by rene on 24/06/17.
 */

import java.util.Arrays;

/**
 * John loves arrays. He calls an array a dense if there exist some numbers x and y such that a contains
 * only consecutive numbers x, x + 1, x + 2, ..., y, in any order.
 * His parents know that he loves arrays and gave him one for his birthday.
 * Now John wants to check whether this array a is dense or not.

 Example

 For a = [3, 4, 2, 1], the output should be
 isArrayDense(a) = true.
 This array contains the consecutive numbers 1, 2, 3, and 4.

 For a = [5, 3, 1, 2], the output should be
 isArrayDense(a) = false.

 Input/Output
 [time limit] 3000ms (java)

 [input] array.integer a

 Guaranteed constraints:
 1 ≤ a.length ≤ 1000,
 -1000 ≤ a[i] ≤ 1000.

 [output] boolean
 Return true if a is dense, otherwise return false.
 */
public class ArrayDense {

    public static void main(String[] args) {
        int[] array1 = {3, 4, 2, 1};
        System.out.println(isArrayDense(array1) + " Expected: true");

        int[] array2 = {5, 3, 1, 2};
        System.out.println(isArrayDense(array2) + " Expected: false");

        int[] array3 = {1000};
        System.out.println(isArrayDense(array3) + " Expected: true");

        int[] array4 = {0};
        System.out.println(isArrayDense(array4) + " Expected: true");

        int[] array5 = {1000, 999};
        System.out.println(isArrayDense(array5) + " Expected: true");

        int[] array6 = {5, 4, 1, 4};
        System.out.println(isArrayDense(array6) + " Expected: false");

        int[] array7 = {1, 2, 2};
        System.out.println(isArrayDense(array7) + " Expected: false");

    }

    private static boolean isArrayDense(int[] array) {
        Arrays.sort(array);

        for(int i = 1; i < array.length; i++) {
            if (array[i] != array[i - 1] + 1) {
                return false;
            }
        }

        return true;
    }

}
