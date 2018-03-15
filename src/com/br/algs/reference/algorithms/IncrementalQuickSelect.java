package com.br.algs.reference.algorithms;

import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by rene on 14/11/17.
 */
// Based on the work of Rodrigo Paredes and Gonzalo Navarro, available at
// https://www.researchgate.net/publication/242328466_Optimal_Incremental_Sorting
public class IncrementalQuickSelect {

    public static void main(String[] args) {
        // Example of usage
        Integer[] array = {8, 4, 1, 0, 9};

        Stack<Integer> pivots = new Stack<>();
        pivots.push(array.length);

        int kthIndexToSearch = 0;

        while (kthIndexToSearch < array.length) {
            int value = (int) incrementalQuickSelect(array, kthIndexToSearch, pivots);
            System.out.println(value);
            kthIndexToSearch++;
        }
    }

    private static Comparable incrementalQuickSelect(Comparable[] array, int indexToSearch, Stack<Integer> pivots) {
        int smallestCorrectPivotIndex = pivots.peek();

        // Pre-condition: indexToSearch <= smallestCorrectPivotIndex
        if (indexToSearch > smallestCorrectPivotIndex) {
            throw new IllegalArgumentException("Searching for an element in the wrong range");
        }

        if (indexToSearch == smallestCorrectPivotIndex) {
            pivots.pop();
            return array[indexToSearch];
        }

        int pivotIndex = ThreadLocalRandom.current().nextInt(indexToSearch, smallestCorrectPivotIndex);
        int correctPivotIndexAfterPartition = partition(array, pivotIndex, indexToSearch, smallestCorrectPivotIndex - 1);

        // Invariant: array[0] <= ... <= array[indexToSearch - 1] <= array[indexToSearch, correctPivotIndexAfterPartition - 1]
        // <= array[correctPivotIndexAfterPartition] <= array[correctPivotIndexAfterPartition + 1, pivots.peek() - 1]
        // <= array[pivots.peek(), array.length - 1]
        pivots.push(correctPivotIndexAfterPartition);
        return incrementalQuickSelect(array, indexToSearch, pivots);
    }

    private static int partition(Comparable[] array, int pivotIndex, int low, int high) {
        if (low == high) {
            return low;
        }

        Comparable pivot = array[pivotIndex];

        exchange(array, low, pivotIndex);

        int lowIndex = low;
        int highIndex = high + 1;

        while (true) {
            while (less(array[++lowIndex], pivot)) {
                if (lowIndex == high) {
                    break;
                }
            }

            while (less(pivot, array[--highIndex])) {
                if (highIndex == low) {
                    break;
                }
            }

            if (lowIndex >= highIndex) {
                break;
            }

            exchange(array, lowIndex, highIndex);
        }

        // Place pivot in the correct place
        exchange(array, low, highIndex);
        return highIndex;
    }

    public static void exchange(Comparable[] array, int position1, int position2) {
        Comparable temp = array[position1];
        array[position1] = array[position2];
        array[position2] = temp;
    }

    public static boolean less(Comparable value1, Comparable value2) {
        if (value1.compareTo(value2) < 0) {
            return true;
        } else {
            return false;
        }
    }

}
