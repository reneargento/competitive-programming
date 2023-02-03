package algorithms.strings.knuth.morris.pratt;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rene Argento on 10/03/18.
 */
// Runs in O(N + M)
// Extra space: N + M
// Does not require backup in the input text

// Based on https://algs4.cs.princeton.edu/53substring/KMPplus.java.html
public class KnuthMorrisPratt {

    private final String pattern;
    private final int[] next; // prefix

    public KnuthMorrisPratt(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern");
        }

        // Build NFA from pattern
        this.pattern = pattern;
        int patternLength = pattern.length();

        next = new int[patternLength];

        int j = -1;

        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            // Compute next[patternIndex]

            if (patternIndex == 0) {
                next[patternIndex] = -1;
            } else if (pattern.charAt(patternIndex) != pattern.charAt(j)) {
                next[patternIndex] = j;
            } else {
                next[patternIndex] = next[j];
            }

            while (j >= 0 && pattern.charAt(patternIndex) != pattern.charAt(j)) {
                j = next[j];
            }
            j++;
        }
    }

    // Search for pattern in text.
    // Returns the index of the first occurrence of the pattern in the text or textLength if no such match.
    public int search(String text) {
        int textIndex;
        int patternIndex;
        int textLength = text.length();
        int patternLength = pattern.length();

        for (textIndex = 0, patternIndex = 0; textIndex < textLength && patternIndex < patternLength; textIndex++) {
            while (patternIndex >= 0 && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                patternIndex = next[patternIndex];
            }
            patternIndex++;
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
            while (patternIndex >= 0 && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                patternIndex = next[patternIndex];
            }
            patternIndex++;
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
