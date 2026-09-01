package algorithms.strings.suffix.array;

import datastructures.strings.SuffixArrayNlgN;

/**
 * Created by Rene Argento on 01/09/26.
 */
// Computes the longest repeating substring in a string
// Time complexity: O(N lg N)
public class LongestRepeatedSubstringNLgN {

    public static String longestRepeatingSubstring(String string) {
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(string);
        int maxLcp = 0;
        int bestIndex = 0;

        for (int i = 1; i < string.length(); i++)
            if (suffixArray.lcp[i] > maxLcp) {
                maxLcp = suffixArray.lcp[i];
                bestIndex = i;
            }
        return string.substring(suffixArray.suffixArray[bestIndex], suffixArray.suffixArray[bestIndex] + maxLcp);
    }

    public static void main() {
        String lrs = longestRepeatingSubstring("ABABA#");
        System.out.printf("LRS: %s,  length: %d\n", lrs, lrs.length());
        System.out.println("Expected: LRS: ABA, length = 3");
    }
}
