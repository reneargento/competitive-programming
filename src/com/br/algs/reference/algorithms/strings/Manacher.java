package com.br.algs.reference.algorithms.strings;

/**
 * Created by Rene Argento on 11/03/18.
 */
// Finds the longest palindromic substring in the text in linear time.
// Based on https://algs4.cs.princeton.edu/53substring/Manacher.java.html
// and https://articles.leetcode.com/longest-palindromic-substring-part-ii/
public class Manacher {

    private int[] lengths; // lengths[i] = length of the longest palindromic substring of transformedString, centered at i
    private String string; // original string
    private char[] transformedString;

    public Manacher(String string) {
        this.string = string;
        preProcess();
        lengths = new int[transformedString.length];

        int center = 0;
        int right = 0;

        for (int index = 1; index < transformedString.length - 1; index++) {
            int mirrorIndex = 2 * center - index; // Same as mirrorIndex = center - (index - center)

            if (right > index) {
                lengths[index] = Math.min(lengths[mirrorIndex], right - index);
            }

            // Attempt to expand palindrome centered at index
            while (transformedString[index - (1 + lengths[index])] == transformedString[index + (1 + lengths[index])]) {
                lengths[index]++;
            }

            // If palindrome centered at index expands past right, adjust center based on expanded palindrome.
            if (index + lengths[index] > right) {
                center = index;
                right = index + lengths[index];
            }
        }
    }

    // Transform string into transformedString.
    // For example, if string = "abba", then transformedString = "$#a#b#b#a#@"
    // The # are interleaved to handle even/odd-length palindromes uniformly.
    // $ and @ are prepended and appended to each end to avoid bounds checking.
    private void preProcess() {
        transformedString = new char[string.length() * 2 + 3];
        transformedString[0] = '$';
        transformedString[string.length() * 2 + 2] = '@';

        for (int index = 0; index < string.length(); index++) {
            transformedString[index * 2 + 1] = '#';
            transformedString[index * 2 + 2] = string.charAt(index);
        }

        transformedString[string.length() * 2 + 1] = '#';
    }

    public String longestPalindromicSubstring() {
        int maxLength = 0;
        int center = 0;

        for (int index = 1; index < lengths.length - 1; index++) {
            if (lengths[index] > maxLength) {
                maxLength = lengths[index];
                center = index;
            }
        }

        return string.substring((center - 1 - maxLength) / 2, (center - 1 + maxLength) / 2);
    }

    // Longest palindromic substring centered at index / 2
    public String longestPalindromicSubstring(int index) {
        int length = lengths[index + 2];
        int center = index + 2;

        return string.substring((center - 1 - length) / 2, (center - 1 + length) / 2);
    }

    public static void main(String[] args) {

        String string1 = "abcreeerbbb";
        Manacher manacher1 = new Manacher(string1);
        System.out.println("Longest palindromic substring 1: " + manacher1.longestPalindromicSubstring() +
                " Expected: reeer");

        String string2 = "abccbaabcreeerbbb";
        Manacher manacher2 = new Manacher(string2);
        System.out.println("Longest palindromic substring 2: " + manacher2.longestPalindromicSubstring() +
                " Expected: abccba");

        String string3 = "abcabcacba";
        Manacher manacher3 = new Manacher(string3);
        System.out.println("Longest palindromic substring 3: " + manacher3.longestPalindromicSubstring() +
                " Expected: abcacba");

        String string4 = "baab";
        Manacher manacher4 = new Manacher(string4);
        System.out.println("Longest palindromic substring 4: " + manacher4.longestPalindromicSubstring() +
                " Expected: baab");
    }

}
