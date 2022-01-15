package algorithms.recursion.permutations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 16/08/21.
 */
// Based on https://introcs.cs.princeton.edu/java/23recursion/Permutations.java.html
// Generates all the permutations (not in order) of an array of numbers.
// O(n * n!) runtime, where n is the number of elements.
// O(n!) space
public class PermutationsNumbersMethod2 {

    private static List<List<Integer>> generateAllPermutations(Integer[] numbers) {
        List<List<Integer>> permutations = new ArrayList<>();
        generateAllPermutations(permutations, numbers, numbers.length);
        return permutations;
    }

    private static void generateAllPermutations(List<List<Integer>> permutations, Integer[] numbers,
                                                int elementsRemaining) {
        if (elementsRemaining == 1) {
            List<Integer> permutation = new ArrayList<>(Arrays.asList(numbers));
            permutations.add(permutation);
            return;
        }

        for (int i = 0; i < elementsRemaining; i++) {
            swap(numbers, i, elementsRemaining - 1);
            generateAllPermutations(permutations, numbers, elementsRemaining - 1);
            swap(numbers, i, elementsRemaining - 1);
        }
    }

    private static void swap(Integer[] numbersArray, int index1, int index2) {
        int aux = numbersArray[index1];
        numbersArray[index1] = numbersArray[index2];
        numbersArray[index2] = aux;
    }

    public static void main(String[] args) {
        Integer[] numbers = new Integer[4];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        List<List<Integer>> permutations = generateAllPermutations(numbers);
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
