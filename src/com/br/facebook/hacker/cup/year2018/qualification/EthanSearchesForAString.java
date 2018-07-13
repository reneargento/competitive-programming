package com.br.facebook.hacker.cup.year2018.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 07/07/18.
 */
public class EthanSearchesForAString {

    public static class KnuthMorrisPratt {

        private String pattern;
        private int[] next; // prefix

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
    }

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2018/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "ethan_searches_for_a_string_example_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "ethan_search_example_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "ethan_searches_for_a_string_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "ethan_searches_for_a_string_output.txt";

    public static void main(String[] args) {
        //test();
        compete();
    }

    private static void test() {
        String string1 = getStringInput("ABACUS");
        System.out.println("Result 1: " + string1 + " Expected: ABABACUS");

        String string2 = getStringInput("FACEBOOK");
        System.out.println("Result 2: " + string2 + " Expected: Impossible");

        String string3 = getStringInput("XYZXZYX");
        System.out.println("Result 3: " + string3 + " Expected: XYZXYZXZYX");

        String string4 = getStringInput("FBFBF");
        System.out.println("Result 4: " + string4 + " Expected: Impossible");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String string = lines.get(i);
            String stringToBreakEthan = getStringInput(string);

            output.add("Case #" + caseId + ": " + stringToBreakEthan);
            caseId++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static String getStringInput(String string) {
        KnuthMorrisPratt knuthMorrisPratt = new KnuthMorrisPratt(string);
        int[] nextIndexes = knuthMorrisPratt.next;

        boolean isPossible = false;
        int indexToBreak = -1;
        int nextIndex = -1;

        for (int i = 0; i < nextIndexes.length; i++) {
            if (nextIndexes[i] > 0) {
                isPossible = true;
                indexToBreak = i;
                nextIndex = nextIndexes[i];
                break;
            }
        }

        if (!isPossible) {
            return "Impossible";
        }

        String prefixString = string.substring(0, indexToBreak);
        String suffixString = string.substring(nextIndex);
        return prefixString + suffixString;
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private static void writeDataOnFile(String file, List<String> data){
        for(String line : data) {
            writeFileOutput(file, line + "\n");
        }
    }

    private static void writeFileOutput(String file, String data){
        byte[] dataBytes = data.getBytes();

        try {
            Files.write(Paths.get(file), dataBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
