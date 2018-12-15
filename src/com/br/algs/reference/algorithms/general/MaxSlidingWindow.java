package com.br.algs.reference.algorithms.general;

import java.util.LinkedList;

/**
 * Created by Rene Argento on 11/01/17.
 */
// Based on http://www.learn4master.com/interview-questions/leetcode/leetcode-sliding-window-maximum
public class MaxSlidingWindow {

    public static void main(String[] args) {

        int[] array1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int windowSize = 3;

        int[] result = getMaxSlidingWindow(array1, windowSize);

        for(int number : result) {
            System.out.print(number + " ");
        }

        System.out.println();
        System.out.println("Expected: 3, 3, 5, 5, 6, 7");
    }

    // O(n)
    private static int[] getMaxSlidingWindow(int[] array, int windowSize) {

        if (windowSize == 0) {
            return new int[0];
        }

        int arraySize = array.length - windowSize + 1;

        int[] maxWindowArray = new int[arraySize];

        LinkedList<Integer> deque = new LinkedList<>();

        for(int i = 0; i < array.length; i++) {
            // Element in deque is no longer in window
            if (deque.size() > 0 && deque.peekFirst() < i - windowSize + 1) {
                deque.pollFirst();
            }

            // If elements in deque are less or equal to the current element, we don't need them
            while (deque.size() > 0 && array[i] > array[deque.peekLast()]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            if (i - windowSize + 1 >= 0) {
                maxWindowArray[i - windowSize + 1] = array[deque.peekFirst()];
            }
        }

        return maxWindowArray;
    }
}
