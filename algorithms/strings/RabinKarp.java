package algorithms.strings;

import java.util.ArrayList;
import java.util.List;

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
    private final String pattern;        // Only needed in the Las Vegas version
    private final long patternHash;
    private final int patternLength;
    private final int alphabetSize = 256;
    private long rm;               // rm = alphabetSize^(patternLength - 1) % largePrimeNumber
    private final boolean isMonteCarloVersion;
    private static final long PRIME_NUMBER = 1_000_000_007L;

    public RabinKarp(String pattern, boolean isMonteCarloVersion) {
        this.pattern = pattern;
        patternLength = pattern.length();
        this.isMonteCarloVersion = isMonteCarloVersion;

        rm = 1;
        for (int patternIndex = 1; patternIndex <= patternLength - 1; patternIndex++) {
            rm = (rm * alphabetSize) % PRIME_NUMBER;  // Compute alphabetSize^(patternLength - 1) % PRIME_NUMBER
        }                                             // for use in removing leading digit.
        patternHash = hash(pattern);
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
            hash = (hash * alphabetSize + key.charAt(patternIndex)) % PRIME_NUMBER;
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
            textHash = (textHash + PRIME_NUMBER - rm * text.charAt(textIndex - patternLength) % PRIME_NUMBER)
                    % PRIME_NUMBER;
            textHash = (textHash * alphabetSize + text.charAt(textIndex)) % PRIME_NUMBER;

            int offset = textIndex - patternLength + 1;
            if (patternHash == textHash && check(text, offset)) {
                return offset;  // match
            }
        }
        return textLength;      // no match
    }

    // Finds all the occurrences of pattern in the text
    public List<Integer> searchAll(String text) {
        List<Integer> offsets = new ArrayList<>();

        int textLength = text.length();
        if (textLength < patternLength) {
            return offsets;
        }

        long textHash = hash(text.substring(0, patternLength));
        if (patternHash == textHash && check(text, 0)) {
            offsets.add(0);  // match
        }

        for (int textIndex = patternLength; textIndex < textLength; textIndex++) {
            // Remove leading character, add trailing character, check for match
            textHash = (textHash + PRIME_NUMBER - rm * text.charAt(textIndex - patternLength) % PRIME_NUMBER)
                    % PRIME_NUMBER;
            textHash = (textHash * alphabetSize + text.charAt(textIndex)) % PRIME_NUMBER;

            int offset = textIndex - patternLength + 1;
            if (patternHash == textHash && check(text, offset)) {
                offsets.add(offset);  // match
            }
        }
        return offsets;
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
