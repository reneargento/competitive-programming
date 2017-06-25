package com.br.algs.reference.algorithms;

import java.util.List;

/**
 * Created by rene on 25/06/17.
 */
public class StringPermutations {

    private static void permutation(String string, List<String> combinations) {
        permutation("", string, combinations);
    }

    private static void permutation(String prefix, String string, List<String> combinations) {
        int length = string.length();

        if (length == 0)  {
            combinations.add(prefix);
            //System.out.println(prefix);
        } else {
            for (int i = 0; i < length; i++) {
                permutation(prefix + string.charAt(i), string.substring(0, i) + string.substring(i + 1, length), combinations);
            }
        }
    }

}
