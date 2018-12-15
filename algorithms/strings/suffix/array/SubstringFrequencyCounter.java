package algorithms.strings.suffix.array;

import datastructures.strings.SuffixArrayLinearTime;

/**
 * Created by Rene Argento on 20/09/18.
 */
// Counts the frequency of substrings in O(k lg N), where k is the substring size and N is the text size
public class SubstringFrequencyCounter {

    private String string;
    private SuffixArrayLinearTime suffixArray;

    SubstringFrequencyCounter(String string) {
        this.string = string;
        suffixArray = new SuffixArrayLinearTime(string);
    }

    public int countFrequency(String substring) {
        int firstOccurrenceIndex = suffixArray.rank(substring);

        if (compareSuffixAndSubstring(firstOccurrenceIndex, substring) != 0) {
            // Substring is not in the text
            return 0;
        }

        int lastOccurrenceIndex = getLastOccurrenceIndex(firstOccurrenceIndex, substring);
        return lastOccurrenceIndex - firstOccurrenceIndex + 1;
    }

    private int getLastOccurrenceIndex(int firstOccurrenceIndex, String substring) {
        int lastOccurrenceIndex = firstOccurrenceIndex;

        int low = firstOccurrenceIndex;
        int high = suffixArray.length() - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            int compare = compareSuffixAndSubstring(middle, substring);

            if (compare > 0) {
                high = middle - 1;
            } else if (compare < 0) {
                low = middle + 1;
            } else {
                lastOccurrenceIndex = middle;
                low = middle + 1;
            }
        }

        return lastOccurrenceIndex;
    }

    // Runtime complexity: O(min(N, k))
    private int compareSuffixAndSubstring(int suffixIndex, String substring) {
        int substringCharIndex = 0;
        int suffixCharIndex = 0;
        int suffixLength = string.length() - suffixIndex;

        while (suffixCharIndex < suffixLength && substringCharIndex < substring.length()) {
            char currentSuffixChar = suffixArray.getSuffixCharAt(suffixIndex, suffixCharIndex);

            if (currentSuffixChar < substring.charAt(substringCharIndex)) {
                return -1;
            } else if (currentSuffixChar > substring.charAt(substringCharIndex)) {
                return 1;
            }

            suffixCharIndex++;
            substringCharIndex++;
        }

        // If the k first characters of the suffix match, we consider it a match
        if (substringCharIndex == substring.length()) {
            return 0;
        }

        return suffixLength - substring.length();
    }

}
