package com.br.algs.reference.algorithms.combinations;

/**
 * Created by Rene Argento on 16/02/18.
 */
// Based on https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
public class AllCombinations {

    public static void main(String[] args) {
        int[] values = {1, 2, 3, 4, 4};
        printAllCombinations(values);
    }

    private static void printAllCombinations(int[] values) {
        for(int size = 1; size <= values.length; size++) {
            printCombination(values, values.length, size);
        }
    }

    private static void printCombination(int arr[], int numbersLength, int combinationSize) {
        // A temporary array to store all combinations one by one
        int data[] = new int[combinationSize];

        // Print all combinations using temporary array 'data[]'
        combinationUtil(arr, data, 0, numbersLength - 1, 0, combinationSize);
    }

    private static void combinationUtil(int arr[], int data[], int start, int end, int index,
                                        int combinationSize) {
        // Current combination is ready to be printed, print it
        if (index == combinationSize) {
            for (int j = 0; j < combinationSize; j++) {
                System.out.print(data[j] + " ");
            }

            System.out.println();
            return;
        }

        // replace index with all possible elements. The condition
        // "end - i + 1 >= combinationSize - index" makes sure that including one element
        // at index will make a combination with remaining elements at remaining positions
        for (int i = start; i <= end && end - i + 1 >= combinationSize - index; i++) {
            data[index] = arr[i];
            combinationUtil(arr, data, i + 1, end, index + 1, combinationSize);
        }
    }

}
