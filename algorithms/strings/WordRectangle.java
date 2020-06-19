package algorithms.strings;

import java.util.*;

/**
 * Created by Rene Argento on 19/06/20.
 */
// Finds the rectangle of letters of largest area (height x length) such that every row forms a word (reading left
// to right) and every column forms a word (reading top to bottom).
// O(ml^3 * n^ml) runtime, where n is the number of words and ml is the maximum length of a word among all words
// O(n^ml * ml^2) space
public class WordRectangle {

    private static class Trie {

        private class Node {
            char value;
            Map<Character, Node> children;
            boolean isWord;

            public Node(char value) {
                this.value = value;
                children = new HashMap<>();
            }
        }

        private Node root;

        public Trie() {
            root = new Node('*');
        }

        // O(l) runtime, where l is the length of the word
        public boolean containsSubstring(String word) {
            Node currentNode = root;

            for (int i = 0; i < word.length(); i++) {
                char character = word.charAt(i);
                currentNode = currentNode.children.get(character);

                if (currentNode == null) {
                    return false;
                }
            }
            return true;
        }

        // O(l) runtime, where l is the length of the word
        public void insert(String word) {
            insert(root, word, 0);
        }

        private void insert(Node node, String word, int index) {
            if (index == word.length()) {
                return;
            }

            char nextChar = word.charAt(index);
            Node nextNode = node.children.get(nextChar);

            if (nextNode == null) {
                nextNode = new Node(nextChar);
            }
            if (index == word.length() - 1) {
                nextNode.isWord = true;
            }

            insert(nextNode, word, index + 1);
            node.children.put(nextChar, nextNode);
        }
    }

    public static class WordGroup {
        private Set<String> wordSet;
        private List<String> wordList;

        public WordGroup() {
            wordSet = new HashSet<>();
            wordList = new ArrayList<>();
        }

        public void addWord(String word) {
            wordSet.add(word);
            wordList.add(word);
        }

        public boolean contains(String word) {
            return wordSet.contains(word);
        }

        public List<String> getWords() {
            return wordList;
        }
    }

    public static class Rectangle {
        private int height;
        private int length;
        private char[][] characters;

        public Rectangle(int length) {
            this.length = length;
        }

        public Rectangle(char[][] characters) {
            height = characters.length;
            length = characters[0].length;
            this.characters = characters;
        }

        public String getColumn(int columnIndex) {
            if (columnIndex >= characters[0].length) {
                return null;
            }

            StringBuilder column = new StringBuilder();
            for (char[] row : characters) {
                column.append(row[columnIndex]);
            }
            return column.toString();
        }

        public Rectangle append(String word) {
            if (word.length() != length) {
                return null;
            }

            char[][] newRectangle = new char[height + 1][length];

            for (int row = 0; row < height; row++) {
                System.arraycopy(characters[row], 0, newRectangle[row], 0, length);
            }
            word.getChars(0, length, newRectangle[height], 0);

            return new Rectangle(newRectangle);
        }

        public boolean isComplete(WordGroup wordGroup) {
            for (int columnIndex = 0; columnIndex < length; columnIndex++) {
                String column = getColumn(columnIndex);

                if (!wordGroup.contains(column)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isPartialValid(Trie trie) {
            if (height == 0) {
                return true;
            }

            for (int columnIndex = 0; columnIndex < length; columnIndex++) {
                String column = getColumn(columnIndex);

                if (!trie.containsSubstring(column)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            StringBuilder rectangle = new StringBuilder();

            for (int row = 0; row < height; row++) {
                for (int column = 0; column < length; column++) {
                    rectangle.append(characters[row][column]);
                }
                if (row != height - 1) {
                    rectangle.append("\n");
                }
            }
            return rectangle.toString();
        }
    }

    // O(ml^3 * n^ml) runtime, where n is the number of words in the list and ml is the maximum length of a word in the
    // list
    // O(n^ml * ml^2) space
    public static Rectangle getWordRectangle(List<String> words) {
        WordGroup[] wordGroups = createWordGroups(words);
        int maxWordLength = wordGroups.length;
        int maxRectangleArea = maxWordLength * maxWordLength;

        Trie[] tries = new Trie[maxWordLength];

        // Try all pairs (length, height) for the rectangle dimensions.
        // The first rectangle found will be the max valid rectangle.
        for (int area = maxRectangleArea; area > 0; area--) {
            for (int length = 1; length <= maxWordLength; length++) {
                if (area % length == 0) {
                    int height = area / length;

                    if (height <= maxWordLength) {
                        Rectangle rectangle = makeRectangle(length, height, wordGroups, tries);
                        if (rectangle != null) {
                            return rectangle;
                        }
                    }
                }
            }
        }
        return null;
    }

    // Group words by length
    private static WordGroup[] createWordGroups(List<String> words) {
        int maxWordLength = 0;
        for (String word : words) {
            maxWordLength = Math.max(maxWordLength, word.length());
        }

        WordGroup[] wordGroups = new WordGroup[maxWordLength];

        for (String word : words) {
            int wordLength = word.length() - 1;
            if (wordGroups[wordLength] == null) {
                wordGroups[wordLength] = new WordGroup();
            }
            wordGroups[wordLength].addWord(word);
        }
        return wordGroups;
    }

    private static Rectangle makeRectangle(int length, int height, WordGroup[] wordGroups, Trie[] tries) {
        WordGroup wordGroupByLength = wordGroups[length - 1];
        WordGroup wordGroupByHeight = wordGroups[height - 1];
        if (wordGroupByLength == null || wordGroupByHeight == null) {
            return null;
        }

        if (tries[height - 1] == null) {
            Trie trie = new Trie();
            for (String word : wordGroupByHeight.getWords()) {
                trie.insert(word);
            }
            tries[height - 1] = trie;
        }

        Rectangle initialRectangle = new Rectangle(length);
        return makePartialRectangle(initialRectangle, height, wordGroups, tries);
    }

    // O(n^ml) runtime, where n is the number of words in the word group list and ml is the maximum length of a word in
    // the word group list
    private static Rectangle makePartialRectangle(Rectangle rectangle, int finalHeight, WordGroup[] wordGroups,
                                                  Trie[] tries) {
        if (rectangle.height == finalHeight) {
            WordGroup wordGroupByHeight = wordGroups[finalHeight - 1];
            if (rectangle.isComplete(wordGroupByHeight)) {
                return rectangle;
            }
            return null;
        }

        // Check if all columns are valid prefixes
        if (!rectangle.isPartialValid(tries[finalHeight - 1])) {
            return null;
        }

        // Go through each word of the right length, append it to the partial rectangle and attempt to build a rectangle
        // recursively.
        WordGroup wordGroupByLength = wordGroups[rectangle.length - 1];

        for (String word : wordGroupByLength.getWords()) {
            Rectangle rectangleWithAppendedWord = rectangle.append(word);
            Rectangle wordRectangle = makePartialRectangle(rectangleWithAppendedWord, finalHeight, wordGroups, tries);

            if (wordRectangle != null) {
                return wordRectangle;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String[] words = {"RENE", "ABCDE", "TEST", "ABC", "ABST", "12345", "1234", "ATR", "BEE", "SSN",
                "TTE", "ALGORITHMS", "STRING"};
        List<String> wordList = Arrays.asList(words);

        Rectangle wordRectangle = getWordRectangle(wordList);
        System.out.println("Word rectangle:\n" + wordRectangle);
        System.out.println("\nExpected:\nATR\nBEE\nSSN\nTTE");
    }

}
