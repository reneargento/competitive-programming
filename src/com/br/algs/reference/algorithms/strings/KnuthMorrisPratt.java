package com.br.algs.reference.algorithms.strings;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rene Argento on 25/02/18.
 */
// Runs in O(N + M)
// Extra space: R * M
// Does not require backup in the input text
public class KnuthMorrisPratt {

    private String pattern;
    private int[][] dfa;  // deterministic-finite-automaton

    public KnuthMorrisPratt(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern");
        }

        // Build DFA from pattern
        this.pattern = pattern;

        int patternLength = pattern.length();
        int alphabetSize = 256;

        dfa = new int[alphabetSize][patternLength];
        dfa[pattern.charAt(0)][0] = 1;

        int restartState = 0;

        for (int patternIndex = 1; patternIndex < patternLength; patternIndex++) {
            // Compute dfa[][patternIndex]
            for (int currentChar = 0; currentChar < alphabetSize; currentChar++) {
                dfa[currentChar][patternIndex] = dfa[currentChar][restartState]; // Copy mismatch cases
            }
            dfa[pattern.charAt(patternIndex)][patternIndex] = patternIndex + 1;  // Set match case
            restartState = dfa[pattern.charAt(patternIndex)][restartState];      // Update restart state
        }
    }

    // Search for pattern in text.
    // Returns the index of the first occurrence of the pattern string in the text string or textLength if no such match.
    public int search(String text) {
        int textIndex;
        int patternIndex;
        int textLength = text.length();
        int patternLength = pattern.length();

        for (textIndex = 0, patternIndex = 0; textIndex < textLength && patternIndex < patternLength; textIndex++) {
            patternIndex = dfa[text.charAt(textIndex)][patternIndex];
        }
        if (patternIndex == patternLength) {
            return textIndex - patternLength; // found
        } else {
            return textLength;                // not found
        }
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
        int textIndex;
        int patternIndex;
        int textLength = text.length();
        int patternLength = pattern.length();

        for (textIndex = textStartIndex, patternIndex = 0; textIndex < textLength && patternIndex < patternLength;
             textIndex++) {
            patternIndex = dfa[text.charAt(textIndex)][patternIndex];
        }
        if (patternIndex == patternLength) {
            return textIndex - patternLength; // found
        } else {
            return textLength;                // not found
        }
    }

    public static void main(String[] args) {
        String pattern = "AACAA";
        String text = "AABRAACADABRAACAADABRA";

        KnuthMorrisPratt knuthMorrisPratt = new KnuthMorrisPratt(pattern);
        System.out.println("text:    " + text);

        int offset = knuthMorrisPratt.search(text);
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.println(pattern);
    }

}
