package com.br.algs.reference.algorithms.permutations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 02/01/18.
 */
public class ZeroOnePermutations {

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
            for(int i = 0; i <= 1; i++) {
                arrayCopy[position] = i;
                generate01Array(combinations, arrayCopy, position + 1, size);
            }
        }
    }

}
