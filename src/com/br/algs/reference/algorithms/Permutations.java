package com.br.algs.reference.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 25/06/17.
 */
public class Permutations {

    //String
    private static void stringPermutation(String string, List<String> combinations) {
        stringPermutation("", string, combinations);
    }

    private static void stringPermutation(String prefix, String string, List<String> combinations) {
        int length = string.length();

        if (length == 0)  {
            combinations.add(prefix);
            //System.out.println(prefix);
        } else {
            for (int i = 0; i < length; i++) {
                stringPermutation(prefix + string.charAt(i), string.substring(0, i) + string.substring(i + 1, length), combinations);
            }
        }
    }

    //Integers 0 and 1
    private static List<int[]> generateAll01Combinations(int size) {
        List<int[]> combinations = new ArrayList<>();
        int[] array = new int[size];

        generate01Array(combinations, array, 0, size);
        return combinations;
    }

    private static void generate01Array(List<int[]> combinations, int[] array, int position, int size) {
        int[] arrayCopy = new int[array.length];
        System.arraycopy(array, 0, arrayCopy, 0, array.length);

        if(position == size) {
            combinations.add(arrayCopy);
        } else {
            for(int i=0; i <= 1; i++) {
                arrayCopy[position] = i;
                generate01Array(combinations, arrayCopy, position + 1, size);
            }
        }
    }

}
