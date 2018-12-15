package algorithms.strings.suffix.array;

/**
 * Created by Rene Argento on 17/09/18.
 */
// Finds the smallest cyclic rotation of a string in O(N)
// Example: rene -> ener
public class SmallestCyclicRotation {

    // Zhou Yuan's minimum expression algorithm
    public String smallestCyclicRotationMethod1(String string) {
        String concatenatedString = string + string;

        int originalStringLength = string.length();
        int length = concatenatedString.length();
        int i = 0;
        int j = 1;
        int k = 0;

        while (i + k < length && j + k < length) {
            if (concatenatedString.charAt(i + k) == concatenatedString.charAt(j + k)) {
                k++;
            } else if (concatenatedString.charAt(i + k) > concatenatedString.charAt(j + k)) {
                i = i + k + 1;
                if (i <= j) {
                    i = j + 1;
                }
                k = 0;
            } else if (concatenatedString.charAt(i + k) < concatenatedString.charAt(j + k)) {
                j = j + k + 1;
                if (j <= i) {
                    j = i + 1;
                }
                k = 0;
            }
        }

        int smallestCyclicRotationIndex = Math.min(i, j);
        return concatenatedString.substring(smallestCyclicRotationIndex, smallestCyclicRotationIndex + originalStringLength);
    }

    public String smallestCyclicRotationMethod2(String string) {
        int[] circularSuffixArray = CircularSuffixArrayLinearTime.buildCircularSuffixArray(string);
        String concatenatedString = string + string;
        return concatenatedString.substring(circularSuffixArray[0], circularSuffixArray[0] + string.length());
    }

}
