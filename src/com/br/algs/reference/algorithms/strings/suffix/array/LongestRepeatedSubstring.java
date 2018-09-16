package com.br.algs.reference.algorithms.strings.suffix.array;

/**
 * Created by Rene Argento on 15/09/18.
 */
public class LongestRepeatedSubstring {

    public String longestRepeatedSubstring(String text) {
        SuffixArrayLinearTime suffixArray = new SuffixArrayLinearTime(text);
        int[] lcp = new KasaiAlgorithm().buildLCPArray(suffixArray.getSuffixes(), text);

        int highestLCPLength = 0;
        int targetSuffixIndex = 0;

        for (int i = 0; i < text.length() - 1; i++) {
            if (lcp[i] > highestLCPLength) {
                highestLCPLength = lcp[i];
                targetSuffixIndex = i;
            }
        }

        return text.substring(suffixArray.index(targetSuffixIndex), suffixArray.index(targetSuffixIndex) + highestLCPLength);
    }

    public String longestRepeatedSubstringIn2Sources(String text1, String text2) {
        char inexistentCharacter = getInexistentCharacter(text1, text2);
        String mergedTexts = text1 + inexistentCharacter + text2;

        int text1Length = text1.length();

        SuffixArrayLinearTime suffixArray = new SuffixArrayLinearTime(mergedTexts);
        int[] suffixes = suffixArray.getSuffixes();
        int[] lcpArray = new KasaiAlgorithm().buildLCPArray(suffixes, mergedTexts);

        int highestLCPLength = 0;
        int targetSuffixIndex = 0;

        for(int i = 0; i < mergedTexts.length() - 1; i++) {
            // Both suffixes are from text 1
            if (suffixArray.index(i) < text1Length && suffixArray.index(i + 1) < text1Length) {
                continue;
            }

            // Both suffixes are from text 2
            if (suffixArray.index(i) > text1Length && suffixArray.index(i + 1) > text1Length) {
                continue;
            }

            // Suffixes are from different texts
            int longestCommonPrefixLength = lcpArray[i];
            if (longestCommonPrefixLength > highestLCPLength) {
                highestLCPLength = longestCommonPrefixLength;
                targetSuffixIndex = i;
            }
        }

        return mergedTexts.substring(suffixArray.index(targetSuffixIndex),
                suffixArray.index(targetSuffixIndex) + highestLCPLength);
    }

    private char getInexistentCharacter(String string1, String string2) {
        char[] charactersInString1 = new char[256];
        char[] charactersInString2 = new char[256];

        for (int i = 0; i < string1.length(); i++) {
            charactersInString1[string1.charAt(i)]++;
        }

        for (int i = 0; i < string2.length(); i++) {
            charactersInString2[string2.charAt(i)]++;
        }

        for (int i = 0; i < charactersInString1.length; i++) {
            if (charactersInString1[i] == 0 && charactersInString2[i] == 0) {
                return (char) i;
            }
        }

        throw new IllegalStateException("All characters appear in both texts");
    }

}
