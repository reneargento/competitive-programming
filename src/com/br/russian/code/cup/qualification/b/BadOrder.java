package com.br.russian.code.cup.qualification.b;

/**
 * Created by rene on 16/04/17.
 */

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Little Dima has a nice pattern at the floor of his room, it consists of n dots arranged in a row.
 * Strange coincidence, but Dima also has n toy cubes that he likes to play, they have weights of 1, 2, ..., n grams, respectively.
 * Dima has finished playing with his cubes and put them on a floor at the dots, one cube at each dot.
 * Now he is going to reorder them in such way that their weight increased from left to right.
 * However, he took a break before doing so, meanwhile bad boy Vadim entered the room.

 Vadim knows that Dima will use the following way to rearrange the cubes.
 Each time he will look for the cube with the smallest weight that is at wrong dot yet, and swap it with the one that occupies its dot.

 Vadim is very vicious, so he wants to force Dima to make the maximum number of swaps.
 He took some cubes out of the Dima's row and now he is planning to put them back.
 He wants to put them back in such way, that there still was exactly one cube at each dot, cubes that were not
 taken before stayed at their places, and Dima needed maximum possible number of swaps to sort the cubes using his method.

 How should Vadim put the cubes back to the row?


 Input format
 Input data contains several test cases.
 The first line of input contains the number of test cases t.

 Each test is described in the following way.

 The first line of the description contains an integer n — the number of cubes (1 ≤ n ≤ 10^5).
 The following line contains n integers ai (0 ≤ ai ≤ n)

 If ai is equal to 0, that means that the cube from the i-th dot was taken away by Vadim, this dot is now empty.
 In the other case ai is the weight of the cube at the i-th dot.

 It is guaranteed that all remaining cubes have different weights, Vadim must return exactly the cubes that are
 currently missing from the line.

 The sum of n in all test cases of one input data doesn't exceed 105.

 Output format
 Output two lines for each test case.

 The first line must contain the maximum number of swaps that Vadim can force Dima to make.
 The second line must contain n integers — weights of cubes in order they will be arranged after Vadim puts
 cubes he has taken away back.
 Note, that cubes that were not taken away must remain at their current positions.

 If there are several possible arrangements that force Dima to make maximum number of swaps, output any of them.

 Examples

 Input data
 3
 2
 0 0
 4
 4 0 0 3
 5
 0 4 0 2 5

 Output data
 1
 2 1
 3
 4 1 2 3
 2
 3 4 1 2 5
 */
public class BadOrder {

    public static void main(String[] args) {
        test();
        //compete();
    }

    private static void test() {
        int[] array1 = {0, 0};
        int[] array2 = {4, 0, 0, 3};
        int[] array3 = {0, 4, 0, 2, 5};

        System.out.println(updateCubesAndCountNumberOfSwaps(array1) + " Expected: 1");
        for(int i=0; i < array1.length; i++) {
            System.out.print(array1[i]);

            if(i != array1.length - 1) {
                System.out.print(" ");
            }
        }

        System.out.println("\n" + updateCubesAndCountNumberOfSwaps(array2) + " Expected: 3");
        for(int i=0; i < array2.length; i++) {
            System.out.print(array2[i]);

            if(i != array2.length - 1) {
                System.out.print(" ");
            }
        }

        System.out.println("\n" + updateCubesAndCountNumberOfSwaps(array3) + " Expected: 2");
        for(int i=0; i < array3.length; i++) {
            System.out.print(array3[i]);

            if(i != array3.length - 1) {
                System.out.print(" ");
            }
        }
    }

    private static void compete() {
        Scanner sc = new Scanner(System.in);
        int numberOfTests = Integer.parseInt(sc.nextLine());

        for(int t=0; t < numberOfTests; t++) {
            int arraySize = sc.nextInt();

            int[] array = new int[arraySize];
            for(int i=0; i < arraySize; i++) {
                array[i] = sc.nextInt();
            }

            int numberOfSwaps = updateCubesAndCountNumberOfSwaps(array);
            System.out.println(numberOfSwaps);
            for(int i=0; i < array.length; i++) {
                System.out.print(array[i]);

                if(i != array.length - 1) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }

    private static int updateCubesAndCountNumberOfSwaps(int[] array) {
        int[] existentValues = new int[array.length];
        List<Integer> nonExistentValues = new ArrayList<>();

        for(int i=0; i < array.length; i++) {
            if(array[i] != 0) {
                existentValues[array[i] - 1] = 1;
            }
        }

        for(int i=0; i < existentValues.length; i++) {
            if(existentValues[i] == 0) {
                nonExistentValues.add(i + 1);
            }
        }

        for(int i=0; i < array.length; i++) {
            if(array[i] == 0) {
                int lastValue = nonExistentValues.get(nonExistentValues.size() - 1);

                if(i != lastValue - 1) {
                    nonExistentValues.remove(nonExistentValues.size() - 1);
                } else if(nonExistentValues.size() > 1) {
                    lastValue = nonExistentValues.get(nonExistentValues.size() - 2);
                    nonExistentValues.remove(nonExistentValues.size() - 2);
                }

                array[i] = lastValue;
            }
        }

        return computeNumberOfSwaps(array);
    }

    //http://www.geeksforgeeks.org/minimum-number-swaps-required-sort-array/
    private static int computeNumberOfSwaps(int[] array) {
        int numberOfSwaps = 0;

        List<Pair<Integer, Integer>> arrayPositions = new ArrayList <Pair <Integer, Integer>>();

        for (int i = 0; i < array.length; i++) {
            arrayPositions.add(new Pair<>(array[i], i));
        }

        // Sort the array by array element values to get right position of every element as the elements of second array.
        arrayPositions.sort(new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> pair1, Pair<Integer, Integer> pair2) {
                if (pair1.getKey() < pair2.getKey()) {
                    return -1;
                } else if (pair1.getKey().equals(pair2.getKey())) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        boolean[] visited = new boolean[array.length];

        for (int i = 0; i < array.length; i++) {
            // Already swapped and corrected or already present at correct pos
            if (visited[i] || arrayPositions.get(i).getValue() == i) {
                continue;
            }

            // Find out the number of node in this cycle and add in numberOfSwaps
            int cycleSize = 0;
            int j = i;
            while (!visited[j]) {
                visited[j] = true;

                // Move to next node
                j = arrayPositions.get(j).getValue();
                cycleSize++;
            }

            // Update answer by adding current cycle.
            numberOfSwaps += (cycleSize - 1);
        }

        return numberOfSwaps;
    }

}
