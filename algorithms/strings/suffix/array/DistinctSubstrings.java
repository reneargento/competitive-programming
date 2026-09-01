package algorithms.strings.suffix.array;

import datastructures.strings.SuffixArrayNlgN;

/**
 * Created by Rene Argento on 01/09/26.
 */
// Computes the number of different substrings in a string
// Time complexity: O(N lg N)
public class DistinctSubstrings {

    public static int computeDistinctSubstrings(String string) {
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(string);

        int distinctSubstrings = 1; // initialize with 1 for the empty substring
        distinctSubstrings += string.length() - suffixArray.suffixArray[0] - 1;

        for (int i = 1; i < suffixArray.lcp.length; i++) {
            int suffixLength = string.length() - suffixArray.suffixArray[i] - 1;
            distinctSubstrings += (suffixLength - suffixArray.lcp[i]);
        }
        return distinctSubstrings;
    }

    public static void main() {
        int distinctSubstrings = computeDistinctSubstrings("ABABA#");
        System.out.println("Distinct substrings: " + distinctSubstrings);
        System.out.println("Expected: 10");
    }
}
