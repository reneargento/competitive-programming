package algorithms.strings;

/**
 * Created by Rene Argento on 14/02/22.
 */
// Returns true if a string contains any repeated adjacent substring.
// Based on https://stackoverflow.com/questions/55841427/how-to-check-if-string-has-repeating-pattern
public class AdjacentRepeatedSubstringExists {

    // .* -> Zero or more characters exist before the repeated substring
    // (.+) -> The substring, consisting of one or more characters
    // (?:\\1)+ -> Reference to the substring, checks if it repeats one or more times
    // .* -> Zero or more characters exist after the repeated substring
    public static boolean hasAdjacentRepeatedSubstring(String string) {
        return string.matches("^.*(.+)(?:\\1)+.*$");
    }

    public static void main(String[] args) {
        boolean hasRepeatedSubstring1 = hasAdjacentRepeatedSubstring("RENE");
        System.out.println("RENE: " + hasRepeatedSubstring1 + " Expected: false");

        boolean hasRepeatedSubstring2 = hasAdjacentRepeatedSubstring("ABCRERE");
        System.out.println("ABCRERE: " + hasRepeatedSubstring2 + " Expected: true");

        boolean hasRepeatedSubstring3 = hasAdjacentRepeatedSubstring("ABCABA");
        System.out.println("ABCABA: " + hasRepeatedSubstring3 + " Expected: false");

        boolean hasRepeatedSubstring4 = hasAdjacentRepeatedSubstring("ABCACAZ");
        System.out.println("ABCACAZ: " + hasRepeatedSubstring4 + " Expected: true");
    }
}
