package algorithms.recursion.permutations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 25/06/17.
 */
// Generates all the permutations (in order) of a given string.
// O(n^2 * n!) runtime, where n is the string size
// O(n!) space
public class PermutationsString {

    public static List<String> permutations(String string) {
        List<String> permutations = new ArrayList<>();
        permutations("", string, permutations);
        return permutations;
    }

    private static void permutations(String prefix, String remainder, List<String> permutations) {
        // Base case
        if (remainder.length() == 0) {
            permutations.add(prefix);
            return;
        }

        for (int i = 0; i < remainder.length(); i++) {
            String start = remainder.substring(0, i);
            String end = remainder.substring(i + 1);
            char removedChar = remainder.charAt(i);

            permutations(prefix + removedChar, start + end, permutations);
        }
    }

    public static void main(String[] args) {
        List<String> permutations = permutations("abc");

        for (String permutation : permutations) {
            System.out.println(permutation);
        }
    }

}
