package com.br.algs.reference.algorithms.strings;

/**
 * Created by rene on 27/01/18.
 */
public class PrefixFunction {

    private static int[] getKMPPrefix(String string) {
        char[] characters = string.toCharArray();
        int[] prefix = new int[string.length()];

        int j = 0;

        for (int i = 1; i < characters.length; i++) {
            while (j > 0 && characters[j] != characters[i])
                j = prefix[j - 1];

            if (characters[j] == characters[i]) {
                j++;
            }

            prefix[i] = j;
        }

        return prefix;
    }

}
