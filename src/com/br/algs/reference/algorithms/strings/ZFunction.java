package com.br.algs.reference.algorithms.strings;

/**
 * Created by rene on 27/01/18.
 */
public class ZFunction {

    private static int[] getZFunction(String string) {
        char[] characters = string.toCharArray();
        int[] zFunction = new int[string.length()];

        int left = 0;
        int right = 0;

        for(int i = 1; i < characters.length; i++) {
            if (i > right) {
                right = i;
                left = i;

                while (right < characters.length
                        && characters[right - left] == characters[right]) {
                    right++;
                }

                zFunction[i] = right - left;
                right--;
            } else {
                int k = i - left;
                if (zFunction[k] < right - i + 1) {
                    zFunction[i] = zFunction[k];
                }
                else {
                    left = i;
                    while (right < characters.length
                            && characters[right - left] == characters[right]) {
                        right++;
                    }
                    zFunction[i] = right - left;
                    right--;
                }
            }
        }

        return zFunction;
    }

}
