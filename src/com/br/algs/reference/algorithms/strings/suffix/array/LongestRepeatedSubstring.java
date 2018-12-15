package com.br.algs.reference.algorithms.strings.suffix.array;

import com.br.algs.reference.datastructures.strings.SuffixArrayLinearTime;

import java.util.*;

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

    // Longest common substring
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

    // Finds the longest substring that is repeated k or more times.
    // Runtime complexity: O(N * k)
    public String longestRepeatedSubstring(String string, int k) {
        if (string == null) {
            throw new IllegalArgumentException("String cannot be null");
        }

        if (string.length() == 0) {
            return "";
        }

        if (k == 0) {
            return string;
        }

        int longestSubstringSuffixIndex = 0;
        int maxLength = 0;

        SuffixArrayLinearTime suffixArray = new SuffixArrayLinearTime(string);
        int[] lcp = new KasaiAlgorithm().buildLCPArray(suffixArray.getSuffixes(), string);

        for (int i = 0; i < string.length(); i++) {
            int currentIndex = i;
            int currentMinLength = lcp[i];

            while (currentIndex <= i + k - 1 && currentIndex < string.length()) {
                if (lcp[currentIndex] < currentMinLength) {
                    currentMinLength = lcp[currentIndex];
                }

                currentIndex++;
            }

            if (currentIndex == i + k && currentMinLength > maxLength) {
                maxLength = currentMinLength;
                longestSubstringSuffixIndex = i;
            }
        }

        return suffixArray.select(longestSubstringSuffixIndex).substring(0, maxLength);
    }

    // Finds all repeated substrings of size equal or higher to minimumLength.
    // Runtime complexity of O(N^2)
    public List<String> getAllRepeatedSubstringsOfMinimumLength(String string, int minimumLength) {
        if (string == null) {
            throw new IllegalArgumentException("String cannot be null");
        }

        if (string.length() == 0) {
            return new ArrayList<>();
        }

        SuffixArrayLinearTime suffixArray = new SuffixArrayLinearTime(string);
        int[] lcp = new KasaiAlgorithm().buildLCPArray(suffixArray.getSuffixes(), string);

        Set<String> substrings = new HashSet<>();

        for (int i = 0; i < lcp.length; i++) {
            // Small optimization to avoid processing the same substrings
            if (i > 0 && lcp[i] == lcp[i - 1]) {
                continue;
            }

            if (lcp[i] >= minimumLength) {
                substrings.add(suffixArray.select(i).substring(0, lcp[i]));
            }
        }

        List<String> substringsList = new ArrayList<>(substrings);
        Collections.sort(substringsList);
        return substringsList;
    }

}
