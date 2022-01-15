package algorithms.recursion.permutations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 16/08/21.
 */
// Based on https://github.com/stevenhalim/cpbook-code/blob/master/ch3/cs/UVa11742.java
// Generates all the permutations of an array of numbers.
// O(n * n!) runtime, where n is the number of elements.
// O(n!) space
public class PermutationsNumbersMethod1 {

    private static List<List<Integer>> generateAllPermutations(int numbers) {
        Integer[] numbersArray = new Integer[numbers];
        List<List<Integer>> permutations = new ArrayList<>();
        generateAllPermutations(numbersArray, 0, 0, permutations);
        return permutations;
    }

    private static void generateAllPermutations(Integer[] numbers, int index, int mask,
                                                List<List<Integer>> permutations) {
        int length = numbers.length;
        if (mask == (1 << length) - 1) {
            List<Integer> permutation = new ArrayList<>(Arrays.asList(numbers));
            permutations.add(permutation);
            return;
        }

        for (int i = 0; i < numbers.length; i++) {
            if ((mask & (1 << i)) == 0) {
                // Still available
                numbers[index] = i;
                int nextMask = mask | (1 << i);
                generateAllPermutations(numbers, index + 1, nextMask, permutations);
            }
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> permutations = generateAllPermutations(4);
        System.out.println("Number of permutations: " + permutations.size() + " Expected: 24\n");

        System.out.println("Permutations");
        for (List<Integer> permutation : permutations) {
            for (int value : permutation) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
