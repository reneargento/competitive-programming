package com.br.algs.reference.algorithms.strings.suffix.array;

import com.br.algs.reference.datastructures.strings.SuffixArrayLinearTime;

/**
 * Created by Rene Argento on 16/09/18.
 */
// Builds a circular suffix array in O(N)
// To access the circular suffixes, use
// concatenatedString.substring(circularSuffixArray[i], circularSuffixArray[i] + string.length())
public class CircularSuffixArrayLinearTime {

    public static int[] buildCircularSuffixArray(String string) {
        int length = string.length();
        String concatenatedString = string + string;

        SuffixArrayLinearTime suffixArray = new SuffixArrayLinearTime(concatenatedString);
        int[] circularSuffixArray = new int[length];
        int index = 0;

        for (int i = 0; i < concatenatedString.length(); i++) {
            if (suffixArray.index(i) < length) {
                circularSuffixArray[index++] = suffixArray.index(i);
            }
        }

        return circularSuffixArray;
    }

    public static void main(String[] args) {
        String string = "barcelona";
        int length = string.length();
        int[] circularSuffixArray = CircularSuffixArrayLinearTime.buildCircularSuffixArray(string);

        String concatenatedString = string + string;

        for (int i = 0; i < circularSuffixArray.length; i++) {
            System.out.println(concatenatedString.substring(circularSuffixArray[i], circularSuffixArray[i] + length));
        }
    }

}
