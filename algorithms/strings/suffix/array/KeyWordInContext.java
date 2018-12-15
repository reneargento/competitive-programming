package algorithms.strings.suffix.array;

import datastructures.strings.SuffixArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 15/09/18.
 */
// Returns all query strings in the text and their surrounding context characters.
public class KeyWordInContext {

    public List<String> keyWordInContext(String text, String query, int context) {
        List<String> keywordsInContext = new ArrayList<>();
        int length = text.length();

        SuffixArray suffixArray = new SuffixArray(text);

        for (int i = suffixArray.rank(query); i < length; i++) {
            // Check if sorted suffix i is a match
            int from1 = suffixArray.index(i);
            int to1 = Math.min(length, from1 + query.length());
            if (!query.equals(text.substring(from1, to1))) {
                break;
            }

            // Get context surrounding sorted suffix i
            int from2 = Math.max(0, suffixArray.index(i) - context);
            int to2 = Math.min(length, suffixArray.index(i) + query.length() + context);
            keywordsInContext.add(text.substring(from2, to2));
        }

        return keywordsInContext;
    }

}
