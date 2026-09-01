package algorithms.strings.suffix.array;

import datastructures.strings.SuffixArrayNlgN;

/**
 * Created by Rene Argento on 01/09/26.
 */
// Computes the suffixes of a string inside another string
// Time complexity: O(M lg N), where m is the length of the pattern string and n is the length of the string
public class StringMatching {

    private static int[] stringMatching(SuffixArrayNlgN suffixArray, String string, String patternString) {
        int stringLength = string.length();
        char[] stringChars = string.toCharArray();
        char[] pattern = patternString.toCharArray();
        int low = 0;
        int high = stringLength - 1;
        int middle;
        while (low < high) {                                 // find lower bound
            middle = (low + high) / 2;
            int compareResult = stringCompare(stringChars, suffixArray.suffixArray[middle], pattern, 0);  // try to find pattern in suffix 'middle'
            if (compareResult >= 0) {
                high = middle;          // prune upper half
            } else {
                low = middle + 1;       // prune lower half including middle
            }
        }
        if (stringCompare(stringChars, suffixArray.suffixArray[low], pattern,0) != 0) {
            return new int[]{ -1, -1 };         // if not found
        }

        int[] result = new int[]{ low, 0 } ;
        low = 0;
        high = stringLength - 1;
        while (low < high) {                 // if lower bound is found, find upper bound
            middle = (low + high) / 2;
            int compareResult = stringCompare(stringChars, suffixArray.suffixArray[middle], pattern,0);
            if (compareResult > 0) {
                high = middle;          // prune upper half
            } else {
                low = middle + 1;       // prune lower half including middle
            }
        }
        if (stringCompare(stringChars, suffixArray.suffixArray[high], pattern,0) != 0) {
            high--;                      // special case
        }
        result[1] = high;
        return result;
    } // return lower/upper bound as the first/second item of the pair, respectively

    private static int stringCompare(char[] string1, int index1, char[] string2, int index2) {
        for (int i = 0; index1 + i < string1.length && index2 + i < string2.length; i++){
            if (string1[index1 + i] != string2[index2 + i]) {
                return string1[index1 + i] - string2[index2 + i];
            }
        }
        return 0;
    }

    public static void main() {
        String string = "RENETESTNE$";
        String pattern = "NE";
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(string);
        int[] positions = stringMatching(suffixArray, string, pattern);

        if (positions[0] != -1 && positions[1] != -1) {
            System.out.printf("%s is found at SA [%d .. %d] of %s\n", pattern, positions[0], positions[1], string);
            System.out.println("Suffixes:");
            for (int i = positions[0]; i <= positions[1]; i++) {
                System.out.printf("  %s\n",
                        string.substring(suffixArray.suffixArray[i]));
            }
        } else {
            System.out.printf("%s is not found in %s\n", pattern, string);
        }

        System.out.println("Expected: NE$, NETESTNE$");
    }
}
