package com.br.algs.reference.algorithms.strings;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Rene Argento on 25/02/18.
 */
// Monte Carlo version
// Runs in O(N + M)
// Extra space: 1
// Does not require backup in the input text
// Has a probabilistic guarantee of giving the correct output

// Las Vegas version
// Typical running time is O(N + M) -> Has a probabilistic guarantee of running in this time.
// Worst case is O(N * M)
// Extra space: 1
// Requires backup in the input text
// Always gives the correct output
public class RabinKarp {

    private String pattern;        // Only needed in the Las Vegas version
    private long patternHash;
    private int patternLength;
    private long largePrimeNumber; // a large prime, small enough to avoid long overflow
    private int alphabetSize = 256;
    private long rm;               // rm = alphabetSize^(patternLength - 1) % largePrimeNumber
    private boolean isMonteCarloVersion;

    public RabinKarp(String pattern, boolean isMonteCarloVersion) {
        if (pattern == null) {
            throw new IllegalArgumentException("Invalid pattern");
        }

        this.pattern = pattern;
        patternLength = pattern.length();
        this.isMonteCarloVersion = isMonteCarloVersion;

        largePrimeNumber = longRandomPrime();

        rm = 1;
        for (int patternIndex = 1; patternIndex <= patternLength - 1; patternIndex++) {
            rm = (rm * alphabetSize) % largePrimeNumber;  // Compute alphabetSize^(patternLength - 1) % largePrimeNumber
        }                                                 // for use in removing leading digit.

        patternHash = hash(pattern);
    }

    // A random 31-bit prime
    private long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    private boolean check(String text, int textIndex) {
        if (isMonteCarloVersion) {
            return true;
        }

        // Las Vegas version
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            if (pattern.charAt(patternIndex) != text.charAt(textIndex + patternIndex)) {
                return false;
            }
        }

        return true;
    }

    // Horner's method applied to modular hashing
    private long hash(String key) {
        // Compute hash for key[0..patternLength - 1]
        long hash = 0;

        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            hash = (hash * alphabetSize + key.charAt(patternIndex)) % largePrimeNumber;
        }

        return hash;
    }

    // Search for a hash match in the text.
    // Returns the index of the first occurrence of the pattern in the text or textLength if no such match.
    public int search(String text) {
        int textLength = text.length();

        if (textLength < patternLength) {
            return textLength;
        }

        long textHash = hash(text);

        if (patternHash == textHash && check(text, 0)) {
            return 0;  // match
        }

        for (int textIndex = patternLength; textIndex < textLength; textIndex++) {
            // Remove leading character, add trailing character, check for match
            textHash = (textHash + largePrimeNumber - rm * text.charAt(textIndex - patternLength) % largePrimeNumber)
                    % largePrimeNumber;
            textHash = (textHash * alphabetSize + text.charAt(textIndex)) % largePrimeNumber;

            int offset = textIndex - patternLength + 1;

            if (patternHash == textHash && check(text, offset)) {
                return offset;  // match
            }
        }

        return textLength;      // no match
    }

    // Count the occurrences of pattern in the text
    public int count(String text) {
        int count = 0;

        int occurrenceIndex = searchFromIndex(text, 0);

        while (occurrenceIndex != text.length()) {
            count++;

            if (occurrenceIndex + 1 >= text.length()) {
                break;
            }

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

            if (occurrenceIndex + 1 >= text.length()) {
                break;
            }

            occurrenceIndex = searchFromIndex(text, occurrenceIndex + 1);
        }

        return offsets;
    }

    // Searches for the pattern in the text starting at specified index
    private int searchFromIndex(String text, int textStartIndex) {
        String eligibleText = text.substring(textStartIndex);

        int textLength = eligibleText.length();

        if (textLength < patternLength) {
            return textStartIndex + textLength;  // no match
        }

        long textHash = hash(eligibleText);

        if (patternHash == textHash && check(eligibleText, 0)) {
            return textStartIndex;  // match
        }

        for (int textIndex = patternLength; textIndex < textLength; textIndex++) {
            // Remove leading character, add trailing character, check for match
            textHash = (textHash + largePrimeNumber - rm * eligibleText.charAt(textIndex - patternLength) % largePrimeNumber)
                    % largePrimeNumber;
            textHash = (textHash * alphabetSize + eligibleText.charAt(textIndex)) % largePrimeNumber;

            int offset = textIndex - patternLength + 1;

            if (patternHash == textHash && check(eligibleText, offset)) {
                return textStartIndex + offset;  // match
            }
        }

        return textStartIndex + textLength;      // no match
    }

    public static void main(String[] args) {
        String pattern = "AACAA";
        String text = "AABRAACADABRAACAADABRA";

        RabinKarp rabinKarp = new RabinKarp(pattern, true);
        System.out.println("text:    " + text);

        int offset = rabinKarp.search(text);
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.println(pattern);
    }

}
