package com.br.algs.reference.algorithms.strings;

/**
 * Created by Rene Argento on 03/03/18.
 */
// A tandem repeat of a base string t in a string s is a substring of s having at least two
// consecutive copies of t (nonoverlaping).
// This algorithm returns the index of the beginning of the longest tandem repeat of t in s.
// Runs in O(N + M)
public class TandemRepeatSearch {

    private String pattern;
    private int[][] dfa;  // deterministic-finite-automaton
    private int baseStringLength;
    private int tandemRepeat;

    public TandemRepeatSearch(String baseString, String text) {
        if (baseString == null || baseString.length() == 0) {
            throw new IllegalArgumentException("Invalid base string");
        }
        if (text == null) {
            throw new IllegalArgumentException("Invalid text");
        }

        // Create the Knuth-Morris-Pratt DFA for k concatenated copies of baseString,
        // where k = textLength / baseStringLength
        StringBuilder pattern = new StringBuilder();

        int maxNumberOfRepeats = text.length() / baseString.length();

        for (int repeat = 0; repeat < maxNumberOfRepeats; repeat++) {
            pattern.append(baseString);
        }

        this.pattern = pattern.toString();
        int patternLength = pattern.length();
        int alphabetSize = 256;
        baseStringLength = baseString.length();
        tandemRepeat = -1;

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

        computeTandemRepeat(text);
    }

    private void computeTandemRepeat(String text) {
        int textIndex;
        int patternIndex;

        // A tandem repeat is composed of at least 2 consecutive occurrences of the base string.
        // If 1 occurrence were enough, we would initialize maxPatternIndexMatched with 0.
        int maxPatternIndexMatched = baseStringLength;

        for (textIndex = 0, patternIndex = 0; textIndex < text.length() && patternIndex < pattern.length(); textIndex++) {
            patternIndex = dfa[text.charAt(textIndex)][patternIndex];

            if (patternIndex % baseStringLength == 0 && patternIndex > maxPatternIndexMatched) {
                tandemRepeat = textIndex - patternIndex + 1;
                maxPatternIndexMatched = patternIndex;
            }
        }
    }

    public int findTandemRepeat() {
        return tandemRepeat;
    }

    public static void main(String[] args) {
        String baseString1 = "abcab";
        String text1 = "abcabcababcababcababcab";

        TandemRepeatSearch knuthMorrisPrattTandemRepeat1 = new TandemRepeatSearch(baseString1, text1);
        int tandemRepeat1 = knuthMorrisPrattTandemRepeat1.findTandemRepeat();
        System.out.println("Tandem repeat 1: " + tandemRepeat1 + " Expected: 3");


        String baseString2 = "rene";
        String text2 = "renereneabrenerenereneab";

        TandemRepeatSearch knuthMorrisPrattTandemRepeat2 = new TandemRepeatSearch(baseString2, text2);
        int tandemRepeat2 = knuthMorrisPrattTandemRepeat2.findTandemRepeat();
        System.out.println("Tandem repeat 2: " + tandemRepeat2 + " Expected: 10");


        String baseString3 = "abcab";
        String text3 = "abcababcababcababcabreabcab";

        TandemRepeatSearch knuthMorrisPrattTandemRepeat3 = new TandemRepeatSearch(baseString3, text3);
        int tandemRepeat3 = knuthMorrisPrattTandemRepeat3.findTandemRepeat();
        System.out.println("Tandem repeat 3: " + tandemRepeat3 + " Expected: 0");


        String baseString4 = "rene";
        String text4 = "abcabcabrenereneababcab";

        TandemRepeatSearch knuthMorrisPrattTandemRepeat4 = new TandemRepeatSearch(baseString4, text4);
        int tandemRepeat4 = knuthMorrisPrattTandemRepeat4.findTandemRepeat();
        System.out.println("Tandem repeat 4: " + tandemRepeat4 + " Expected: 8");


        String baseString5 = "rene";
        String text5 = "abcabcababcababcababcab";

        TandemRepeatSearch knuthMorrisPrattTandemRepeat5 = new TandemRepeatSearch(baseString5, text5);
        int tandemRepeat5 = knuthMorrisPrattTandemRepeat5.findTandemRepeat();
        System.out.println("Tandem repeat 5: " + tandemRepeat5 + " Expected: -1");


        // A tandem repeat requires at least 2 consecutive occurrences of baseString in the text,
        // so the two following tests should return -1.

        String baseString6 = "a";
        String text6 = "abcde";

        TandemRepeatSearch knuthMorrisPrattTandemRepeat6 = new TandemRepeatSearch(baseString6, text6);
        int tandemRepeat6 = knuthMorrisPrattTandemRepeat6.findTandemRepeat();
        System.out.println("Tandem repeat 6: " + tandemRepeat6 + " Expected: -1");

        String baseString7 = "a";
        String text7 = "abada";

        TandemRepeatSearch knuthMorrisPrattTandemRepeat7 = new TandemRepeatSearch(baseString7, text7);
        int tandemRepeat7 = knuthMorrisPrattTandemRepeat7.findTandemRepeat();
        System.out.println("Tandem repeat 7: " + tandemRepeat7 + " Expected: -1");
    }

}
