package algorithms.strings.knuth.morris.pratt;

import java.util.ArrayList;
import java.util.List;

// Alternative implementation to KMP algorithm
// Runs in O(N + M)
// Extra space: N + M
public class KnuthMorrisPratt2 {

    public static void main() {
        String pattern = "AACAA";
        String text = "AABRAACADABRAACAADABRA";

        int[] resetTable = preProcess(pattern.toCharArray());
        List<Integer> indexes = findAll(pattern.toCharArray(), text.toCharArray(), resetTable);

        System.out.println("text:    " + text);
        int offset = indexes.getFirst();
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.println(pattern);
    }

    private static int[] preProcess(char[] pattern) {
        int[] resetTable = new int[pattern.length + 1];
        int i = 0;
        int j = -1;
        resetTable[0] = -1;

        while (i < pattern.length) {
            while ((j >= 0) && (pattern[i] != pattern[j])) {
                j = resetTable[j];
            }
            i++;
            j++;
            resetTable[i] = j;
        }
        return resetTable;
    }

    private static List<Integer> findAll(char[] pattern, char[] text, int[] resetTable) {
        List<Integer> indexes = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < text.length) {
            while ((j >= 0) && (text[i] != pattern[j])) {
                j = resetTable[j];
            }
            i++;
            j++;
            if (j == pattern.length) {
                indexes.add(i - j);
                j = resetTable[j];
            }
        }
        return indexes;
    }
}
