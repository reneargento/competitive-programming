package algorithms.recursion.permutations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rene Argento on 26/09/19.
 */
// This algorithm has a much better runtime than Permutations when generating permutations from a string with
// many duplicate characters.
// The best case runtime can get to n^2 when we have a string with all duplicate characters such as "aaaaaaaaaaaaa".
// The worst case runtime is still the same.
public class PermutationsWithManyDuplicates {

    // O(n^2 * n!) runtime, where n is the string size
    // O(n!) space
    public static List<String> permutations(String string) {
        List<String> permutations = new ArrayList<>();
        Map<Character, Integer> frequencyMap = buildFrequencyMap(string);
        permutations("", string.length(), frequencyMap, permutations);
        return permutations;
    }

    private static Map<Character, Integer> buildFrequencyMap(String string) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char character : string.toCharArray()) {
            int frequency = frequencyMap.getOrDefault(character, 0);
            frequency++;
            frequencyMap.put(character, frequency);
        }

        return frequencyMap;
    }

    public static void permutations(String prefix, int remaining, Map<Character, Integer> frequencyMap,
                                    List<String> permutations) {
        // Base case
        if (remaining == 0) {
            permutations.add(prefix);
            return;
        }

        for (Character character : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(character);
            if (frequency > 0) {
                frequencyMap.put(character, frequency - 1);
                permutations(prefix + character, remaining - 1, frequencyMap, permutations);
                frequencyMap.put(character, frequency);
            }
        }
    }
}
