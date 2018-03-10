package com.br.algs.reference.algorithms.strings;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by rene on 16/12/17.
 */
// Runs in O(N * M), but the typical running time is N / M
// Extra space: R
// Requires backup in the input text
public class BoyerMoore {

    private int[] right;
    private String pattern;

    public BoyerMoore(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Invalid pattern");
        }

        this.pattern = pattern;
        int alphabetSize = 256;

        right = new int[alphabetSize];

        for (int currentChar = 0; currentChar < alphabetSize; currentChar++) {
            right[currentChar] = -1; // -1 for chars not in pattern
        }

        for (int patternIndex = 0; patternIndex < pattern.length(); patternIndex++)  {
            right[pattern.charAt(patternIndex)] = patternIndex; // rightmost position for chars in pattern
        }
    }

    // Search for pattern in the text.
    // Returns the index of the first occurrence of the pattern string in the text string or textLength if no such match.
    public int search(String text) {
        int textLength = text.length();
        int patternLength = pattern.length();

        int skip;

        for (int textIndex = 0; textIndex <= textLength - patternLength; textIndex += skip) {
            // Does the pattern match the text at position textIndex?
            skip = 0;

            for (int patternIndex = patternLength - 1; patternIndex >= 0; patternIndex--) {
                if (pattern.charAt(patternIndex) != text.charAt(textIndex + patternIndex)) {
                    skip = Math.max(1, patternIndex - right[text.charAt(textIndex + patternIndex)]);
                    break;
                }
            }
            if (skip == 0) {
                return textIndex; // found
            }
        }

        return textLength;        // not found
    }

    // Count the occurrences of pattern in the text
    public int count(String text) {
        int count = 0;

        int occurrenceIndex = searchFromIndex(text, 0);

        while (occurrenceIndex != text.length()) {
            count++;
            occurrenceIndex = searchFromIndex(text, occurrenceIndex + 1);
        }

        return count;
    }

    // Finds all the occurrences of pattern in the text
    public Iterable<Integer> findAll(String text) {
        Queue<Integer> offsets = new LinkedList<>();

        int occurrenceIndex = searchFromIndex(text, 0);

        while (occurrenceIndex != text.length()) {
            offsets.offer(occurrenceIndex);
            occurrenceIndex = searchFromIndex(text, occurrenceIndex + 1);
        }

        return offsets;
    }

    // Searches for the pattern in the text starting at specified index.
    private int searchFromIndex(String text, int textStartIndex) {
        int textLength = text.length();
        int patternLength = pattern.length();

        int skip;

        for (int textIndex = textStartIndex; textIndex <= textLength - patternLength; textIndex += skip) {
            // Does the pattern match the text at position textIndex?
            skip = 0;

            for (int patternIndex = patternLength - 1; patternIndex >= 0; patternIndex--) {
                if (pattern.charAt(patternIndex) != text.charAt(textIndex + patternIndex)) {
                    skip = Math.max(1, patternIndex - right[text.charAt(textIndex + patternIndex)]);
                    break;
                }
            }
            if (skip == 0) {
                return textIndex; // found
            }
        }

        return textLength;        // not found
    }

    public static void main(String[] args) {
        String pattern = "AACAA";
        String text = "AABRAACADABRAACAADABRA";

        BoyerMoore boyerMoore = new BoyerMoore(pattern);
        System.out.println("text:    " + text);

        int offset = boyerMoore.search(text);
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.println(pattern);
    }

}
