package com.br.algs.reference.algorithms.permutations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 25/06/17.
 */
public class StringPermutations {

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
                stringPermutation(prefix + string.charAt(i), string.substring(0, i) +
                        string.substring(i + 1, length), combinations);
            }
        }
    }

}
