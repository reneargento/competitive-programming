package algorithms.dynamic.programming;

import java.util.*;

/**
 * Created by Rene Argento on 18/05/20.
 */
// Gets the longest word in the list that is composed by 2 or more other words in the list
// Runtime: O(n * l^2), where n is the number of words in the list and l is the longest length of a word in the list
public class LongestComposedWord {

    public static String longestWord(List<String> words) {
        Map<String, Boolean> wordExistenceMap = new HashMap<>();
        for (String word : words) {
            wordExistenceMap.put(word, true);
        }

        words.sort(new LengthComparator());

        for (String word : words) {
            if (canBuildWord(word, wordExistenceMap, true)) {
                return word;
            }
        }
        return "";
    }

    private static boolean canBuildWord(String word, Map<String, Boolean> wordExistenceMap, boolean isOriginalWord) {
        if (wordExistenceMap.containsKey(word) && !isOriginalWord) {
            return wordExistenceMap.get(word);
        }

        for (int i = 1; i < word.length(); i++) {
            String part1 = word.substring(0, i);
            String part2 = word.substring(i);

            if (wordExistenceMap.getOrDefault(part1, false)
                    && canBuildWord(part2, wordExistenceMap, false)) {
                return true;
            }
        }
        wordExistenceMap.put(word, false);
        return false;
    }

    private static class LengthComparator implements Comparator<String> {
        @Override
        public int compare(String string1, String string2) {
            return string2.length() - string1.length();
        }
    }

    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("cat");
        words.add("banana");
        words.add("dog");
        words.add("nana");
        words.add("walk");
        words.add("walker");
        words.add("dogwalker");
        words.add("dogwalkerNotComposed");

        String longestWord = longestWord(words);
        System.out.println("Longest word: " + longestWord);
        System.out.println("Expected: dogwalker");
    }

}
