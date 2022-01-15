package io;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 28/11/20.
 */
// This algorithm gets all words in the line.
// Words can be separated by one or more spaces.
public class WordsFromLine {

    public static void main(String[] args) {
        String line = "  this  is an input   with  irregular   space   lengths  ";
        List<String> words = getWords(line);

        StringJoiner wordList = new StringJoiner(" ");
        for (String word : words) {
            wordList.add(word);
        }
        System.out.println("Words: " + wordList);
        System.out.println("Expected: this is an input with irregular space lengths");
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
    }
}
