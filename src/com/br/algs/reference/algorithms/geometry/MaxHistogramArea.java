package com.br.algs.reference.algorithms.geometry;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 12/03/18.
 */
// Based on https://www.youtube.com/watch?v=ZmnqCZp9bBs
public class MaxHistogramArea {

    public long computeArea(long[] histogram) {

        if (histogram == null || histogram.length == 0) {
            return 0;
        }

        long maxArea = 0;

        Deque<Integer> indexesStack = new ArrayDeque<>();
        int rightIndex;

        for(rightIndex = 0; rightIndex < histogram.length; ) {
            if (indexesStack.isEmpty() || histogram[indexesStack.peek()] <= histogram[rightIndex]) {
                indexesStack.push(rightIndex);
                rightIndex++;
            } else {
                // indexesStack.peek() > histogram[rightIndex]
                maxArea = popBarAndCheckArea(indexesStack, histogram, rightIndex, maxArea);
            }
        }

        while (!indexesStack.isEmpty()) {
            maxArea = popBarAndCheckArea(indexesStack, histogram, rightIndex, maxArea);
        }

        return maxArea;
    }

    private long popBarAndCheckArea(Deque<Integer> indexesStack, long[] histogram, int rightIndex, long maxArea) {
        long area;
        int currentTopHeightIndex = indexesStack.pop();

        if (indexesStack.isEmpty()) {
            area = histogram[currentTopHeightIndex] * rightIndex;
        } else {
            int previousTopHeightIndex = indexesStack.peek();
            area = histogram[currentTopHeightIndex] * (rightIndex - previousTopHeightIndex - 1);
        }

        if (area > maxArea) {
            maxArea = area;
        }

        return maxArea;
    }

    public static void main(String[] args) {
        MaxHistogramArea maxHistogramArea = new MaxHistogramArea();

        long[] histogram1 = {7, 2, 1, 4, 5, 1, 3, 3};
        System.out.println("Max area 1: " + maxHistogramArea.computeArea(histogram1) + " Expected: 8");

        long[] histogram2 = {4, 1000, 1000, 1000, 1000};
        System.out.println("Max area 2: " + maxHistogramArea.computeArea(histogram2) + " Expected: 4000");

        long[] histogram3 = {4, 3, 2, 1};
        System.out.println("Max area 3: " + maxHistogramArea.computeArea(histogram3) + " Expected: 6");

        long[] histogram4 = {1, 0, 0, 1, 0};
        System.out.println("Max area 4: " + maxHistogramArea.computeArea(histogram4) + " Expected: 1");

        long[] histogram5 = {1, 0, 3, 2, 1, 2, 2, 2};
        System.out.println("Max area 5: " + maxHistogramArea.computeArea(histogram5) + " Expected: 6");
    }

}