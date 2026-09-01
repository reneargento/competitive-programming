package algorithms.strings.suffix.array;

import datastructures.strings.SuffixArrayNlgN;

/**
 * Created by Rene Argento on 01/09/26.
 */
// Computes the longest common substring between two strings
// Time complexity: O(N lg N)
public class LongestCommonSubstring {

    private static String longestCommonSubstring(String string1, String string2) {
        int maxLcp = -1;
        int bestIndex = 0;

        String concatenatedString = string1 + string2 + "#";   // append pattern and '#'
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(concatenatedString);
        int stringLength = concatenatedString.length();
        int string2Length = string2.length();

        for (int i = 1; i < stringLength; i++) {
            if (suffixArray.lcp[i] > maxLcp
                    && owner(suffixArray.suffixArray[i], stringLength, string2Length)
                        != owner(suffixArray.suffixArray[i - 1], stringLength, string2Length)) {  // different owner
                maxLcp = suffixArray.lcp[i];
                bestIndex = i;
            }
        }
        return concatenatedString.substring(suffixArray.suffixArray[bestIndex], suffixArray.suffixArray[bestIndex] + maxLcp);
    }

    private static int owner(int index, int concatenatedStringLength, int string2Length) {
        return (index < concatenatedStringLength - string2Length - 1) ? 1 : 2;
    }

    public static void main() {
        String lcs = longestCommonSubstring("GATAGACA$", "CATA");
        System.out.println("LCS: " + lcs);
        System.out.println("Expected: ATA");
    }
}
