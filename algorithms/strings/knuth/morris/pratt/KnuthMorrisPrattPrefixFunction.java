package algorithms.strings.knuth.morris.pratt;

/**
 * Created by rene on 27/01/18.
 */
public class KnuthMorrisPrattPrefixFunction {

    private static int[] getKMPPrefix(String pattern) {
        int[] prefix = new int[pattern.length()];

        int j = 0;

        for (int i = 1; i < pattern.length(); i++) {
            while (j > 0 && pattern.charAt(j) != pattern.charAt(i)) {
                j = prefix[j - 1];
            }

            if (pattern.charAt(j) == pattern.charAt(i)) {
                j++;
            }

            prefix[i] = j;
        }

        return prefix;
    }

}
