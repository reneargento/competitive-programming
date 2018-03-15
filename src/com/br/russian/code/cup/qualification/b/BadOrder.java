package com.br.russian.code.cup.qualification.b;

/**
 * Created by rene on 16/04/17.
 */

import javafx.util.Pair;

import java.util.*;

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

    private static class Path implements Comparable<Path>{

        int beginning;
        int end;

        int numberOfEdges;

        Path(int beginning, int end) {
            this.beginning = beginning;
            this.end = end;
            numberOfEdges = 1;
        }

        @Override
        public int compareTo(Path that) {
            return this.numberOfEdges - that.numberOfEdges;
        }

        @Override
        public boolean equals(Object that) {
            if (this == that) {
                return true;
            }

            if (!(that instanceof Path)) {
                return false;
            }

            Path otherPath = (Path) that;
            if (this.beginning == otherPath.beginning
                    && this.end == otherPath.end) {
                return true;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return 31 * 17 * beginning + end;
        }
    }

    public static void main(String[] args) {
        test();
        //compete();
    }

    private static void test() {
        int[] array0 = {0, 4, 3, 0};
        int[] array1 = {0, 0};
        int[] array2 = {4, 0, 0, 3};
        int[] array3 = {0, 4, 0, 2, 5};

        updateCubesAndCountNumberOfSwaps(array0);

        System.out.println(updateCubesAndCountNumberOfSwaps(array0) + " Expected: 2");
        for(int i = 0; i < array0.length; i++) {
            System.out.print(array0[i]);

            if (i != array0.length - 1) {
                System.out.print(" ");
            }
        }

        System.out.println("\n" + updateCubesAndCountNumberOfSwaps(array1) + " Expected: 1");
        for(int i = 0; i < array1.length; i++) {
            System.out.print(array1[i]);

            if (i != array1.length - 1) {
                System.out.print(" ");
            }
        }

        System.out.println("\n" + updateCubesAndCountNumberOfSwaps(array2) + " Expected: 3");
        for(int i = 0; i < array2.length; i++) {
            System.out.print(array2[i]);

            if (i != array2.length - 1) {
                System.out.print(" ");
            }
        }

        System.out.println("\n" + updateCubesAndCountNumberOfSwaps(array3) + " Expected: 2");
        for(int i = 0; i < array3.length; i++) {
            System.out.print(array3[i]);

            if (i != array3.length - 1) {
                System.out.print(" ");
            }
        }
    }

    private static void compete() {
        Scanner sc = new Scanner(System.in);
        int numberOfTests = Integer.parseInt(sc.nextLine());

        for(int t = 0; t < numberOfTests; t++) {
            int arraySize = sc.nextInt();

            int[] array = new int[arraySize];
            for(int i = 0; i < arraySize; i++) {
                array[i] = sc.nextInt();
            }

            int numberOfSwaps = updateCubesAndCountNumberOfSwaps(array);
            System.out.println(numberOfSwaps);
            for(int i = 0; i < array.length; i++) {
                System.out.print(array[i]);

                if (i != array.length - 1) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }

    private static int updateCubesAndCountNumberOfSwaps(int[] array) {

        Map<Integer, Path> paths = new HashMap<>();
        PriorityQueue<Path> priorityQueue = new PriorityQueue<>();

        for(int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                if (paths.containsKey(array[i])) {

                    Path path1 = paths.get(array[i]);

                    if (paths.containsKey(i + 1)) {
                        Path path2 = paths.get(i + 1);

                        //Get longer cycle
                        if (path1.numberOfEdges >= path2.numberOfEdges) {
                            path1.end = path2.end;
                            path1.numberOfEdges += path2.numberOfEdges + 1;

                            path2.beginning = path1.beginning;
                            path2.end = path1.end;
                        } else {
                            path2.end = path1.end;
                            path2.numberOfEdges += path1.numberOfEdges + 1;

                            path1.beginning = path2.beginning;
                            path1.end = path2.end;
                        }
                    } else {
                        path1.end = i + 1;
                        path1.numberOfEdges++;
                        paths.put(i + 1, path1);
                    }
                } else {
                    if (paths.containsKey(i + 1)) {
                        Path path = paths.get(i + 1);

                        path.end = array[i];
                        path.numberOfEdges++;
                        paths.put(array[i], path);
                    } else {
                        Path path = new Path(i + 1, array[i]);

                        paths.put(i + 1, path);
                        paths.put(array[i], path);
                    }
                }
            }
        }

        //Get only unique paths, removing cycles
        Set<Path> uniquePaths = new HashSet<>();
        for(Integer pathVertex : paths.keySet()) {
            Path path = paths.get(pathVertex);

            //We don't want cycles
            if (path.beginning == path.end
                    || (array[path.beginning - 1] == path.end && array[path.end - 1] == path.beginning)) {
                continue;
            }

            uniquePaths.add(path);
        }

        //Add unique paths to priority queue, automatically ordering them by path length
        for(Path path : uniquePaths) {
            priorityQueue.add(path);
        }

        while (priorityQueue.size() > 1) {
            Path longestPath = priorityQueue.poll();
            Path nextLongestPath = priorityQueue.poll();

            array[longestPath.end - 1] = array[nextLongestPath.beginning - 1];
            longestPath.end = nextLongestPath.end;
            longestPath.numberOfEdges += nextLongestPath.numberOfEdges + 1;

            priorityQueue.offer(longestPath);
        }

        Set<Integer> existentValues = new HashSet<>();
        Queue<Integer> nonExistentValues = new LinkedList<>();

        for(int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                existentValues.add(array[i]);
            }
        }
        for(int i = 1; i <= array.length; i++) {
            if (!existentValues.contains(i)) {
                nonExistentValues.add(i);
            }
        }

        if (priorityQueue.size() == 0) {
            //All values are 0 or there are only cycles in the original configuration
            //Make an array such as [2, 3, 4, 5, 6, 1] or as close as possible
            for(int i = array.length - 1; i >= 0; i--) {
                if (array[i] == 0) {
                    int nextValue = nonExistentValues.poll();

                    if (nextValue - 1 == i && nonExistentValues.size() > 0) {
                        nonExistentValues.offer(nextValue);
                        nextValue = nonExistentValues.poll();
                    }

                    array[i] = nextValue;
                    break;
                }
            }

            for(int i = 0; i < array.length; i++) {
                if (array[i] == 0 && !nonExistentValues.isEmpty()) {
                    int nextValue = nonExistentValues.poll();

                    if (nextValue - 1 == i && nonExistentValues.size() > 0) {
                        nonExistentValues.offer(nextValue);
                        nextValue = nonExistentValues.poll();
                    }

                    array[i] = nextValue;
                }
            }
        } else {
            //Add values to the end of the longest path
            Path longestPath = priorityQueue.poll();

            while (nonExistentValues.size() > 0){
                int nextValue = nonExistentValues.poll();

                int numberOfNonExistentValuesChecked = 1;
                while(array[nextValue - 1] != 0
                        && numberOfNonExistentValuesChecked <= nonExistentValues.size()) {
                    //Adding this value would close the cycle
                    nonExistentValues.offer(nextValue);
                    nextValue = nonExistentValues.poll();

                    numberOfNonExistentValuesChecked++;
                }

                array[longestPath.end - 1] = nextValue;
                longestPath.end = nextValue;
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
