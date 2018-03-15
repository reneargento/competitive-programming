package com.br.google.code.jam.code2017.qualification;

/**
 * Created by rene on 07/04/17.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Problem

 A certain bathroom has N + 2 stalls in a single row; the stalls on the left and right ends are permanently
 occupied by the bathroom guards. The other N stalls are for users.

 Whenever someone enters the bathroom, they try to choose a stall that is as far from other people as possible.
 To avoid confusion, they follow deterministic rules:
 For each empty stall S, they compute two values LS and RS, each of which is the number of empty stalls
 between S and the closest occupied stall to the left or right, respectively.
 Then they consider the set of stalls with the farthest closest neighbor, that is, those S for which min(LS, RS) is maximal.
 If there is only one such stall, they choose it; otherwise, they choose the one among those where max(LS, RS) is maximal.
 If there are still multiple tied stalls, they choose the leftmost stall among those.

 K people are about to enter the bathroom; each one will choose their stall before the next arrives.
 Nobody will ever leave.

 When the last person chooses their stall S, what will be the values of max(LS, RS) and min(LS, RS) be?

 Solving this problem

 This problem has 2 Small datasets and 1 Large dataset.
 You must solve the first Small dataset before you can attempt the second Small dataset.
 You will be able to retry either of the Small datasets (with a time penalty).
 You will be able to make a single attempt at the Large, as usual, only after solving both Small datasets.

 Input

 The first line of the input gives the number of test cases, T. T lines follow.
 Each line describes a test case with two integers N and K, as described above.

 Output

 For each test case, output one line containing Case #x: y z,
 where x is the test case number (starting from 1), y is max(LS, RS), and z is min(LS, RS) as calculated by
 the last person to enter the bathroom for their chosen stall S.

 Limits

 1 ≤ T ≤ 100.
 1 ≤ K ≤ N.

 Small dataset 1
 1 ≤ N ≤ 1000.

 Small dataset 2
 1 ≤ N ≤ 10^6.

 Large dataset
 1 ≤ N ≤ 10^18.

 Sample

 Input
 5
 4 2
 5 2
 6 2
 1000 1000
 1000 1

 Output
 Case #1: 1 0
 Case #2: 1 0
 Case #3: 1 1
 Case #4: 0 0
 Case #5: 500 499

 In Case #1, the first person occupies the leftmost of the middle two stalls,
 leaving the following configuration (O stands for an occupied stall and . for an empty one): O.O..O.
 Then, the second and last person occupies the stall immediately to the right,
 leaving 1 empty stall on one side and none on the other.

 In Case #2, the first person occupies the middle stall, getting to O..O..O.
 Then, the second and last person occupies the leftmost stall.

 In Case #3, the first person occupies the leftmost of the two middle stalls, leaving O..O...O.
 The second person then occupies the middle of the three consecutive empty stalls.

 In Case #4, every stall is occupied at the end, no matter what the stall choices are.

 In Case #5, the first and only person chooses the leftmost middle stall.
 */

public class BathroomStalls {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/2017/Qualification/Input - Output/";

    //    private static final String FILE_INPUT_PATH = PATH + "bathroom_stall_small1_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "bathroom_stall_small1_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "bathroom_stall_small2_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "bathroom_stall_small2_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "bathroom_stall_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "bathroom_stall_large_output.txt";

    public static void main(String[] args) {

        test();
       // compete();
    }

    private static void compete() {
        List<String> stallsAndUsersList = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(String stallsAndUsers : stallsAndUsersList) {
            long[] spaces = getStallSpacesBig(stallsAndUsers);
            output.add("Case #" + caseIndex + ": " + spaces[0] + " " + spaces[1]);

            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        String stallsAndUsers1 = "4 2";
        String stallsAndUsers2 = "5 2";
        String stallsAndUsers3 = "6 2";
        String stallsAndUsers4 = "1000 1000";
        String stallsAndUsers5 = "1000 1";
        String stallsAndUsers6 = "1000000000000000000 1000000000000000000";//N = 10^18 and K = 10^18

        long[] stallSpaces1 = getStallSpacesBig(stallsAndUsers1);
        long[] stallSpaces2 = getStallSpacesBig(stallsAndUsers2);
        long[] stallSpaces3 = getStallSpacesBig(stallsAndUsers3);
        long[] stallSpaces4 = getStallSpacesBig(stallsAndUsers4);
        long[] stallSpaces5 = getStallSpacesBig(stallsAndUsers5);
        long[] stallSpaces6 = getStallSpacesBig(stallsAndUsers6);

        System.out.println(stallSpaces1[0] + " " + stallSpaces1[1] + " Expected: 1 0");
        System.out.println(stallSpaces2[0] + " " + stallSpaces2[1] + " Expected: 1 0");
        System.out.println(stallSpaces3[0] + " " + stallSpaces3[1] + " Expected: 1 1");
        System.out.println(stallSpaces4[0] + " " + stallSpaces4[1] + " Expected: 0 0");
        System.out.println(stallSpaces5[0] + " " + stallSpaces5[1] + " Expected: 500 499");
        System.out.println(stallSpaces6[0] + " " + stallSpaces6[1] + " Expected: 0 0");
    }

    private static long[] getStallSpacesSmall(String row) {
        String[] input = row.split(" ");
        long numberOfStalls = Long.parseLong(input[0]);
        long users = Long.parseLong(input[1]);

        long leftSpaces;
        long rightSpaces;

        long maxSpace = 0;
        long minSpace = 0;

        PriorityQueueResize priorityQueueResize = new BathroomStalls().new PriorityQueueResize(Orientation.MAX);
        priorityQueueResize.insert(numberOfStalls);

        for(int i = 0; i < users; i++) {

            long maxSpaces = priorityQueueResize.deleteTop();
            maxSpaces--;

            leftSpaces = maxSpaces / 2;
            rightSpaces = maxSpaces - leftSpaces;

            if (i == users - 1) {
                maxSpace = Math.max(leftSpaces, rightSpaces);
                minSpace = Math.min(leftSpaces, rightSpaces);
            }

            priorityQueueResize.insert(leftSpaces);
            priorityQueueResize.insert(rightSpaces);
        }

        return new long[]{maxSpace, minSpace};
    }

    private static long[] getStallSpacesBig(String row) {
        String[] input = row.split(" ");
        long numberOfStalls = Long.parseLong(input[0]);
        long users = Long.parseLong(input[1]);

        long leftSpaces = 0;
        long rightSpaces = 0;

        long maxValue = numberOfStalls;

        Set<Long> set = new HashSet<>();
        set.add(maxValue);

        PriorityQueueResize priorityQueue = new BathroomStalls().new PriorityQueueResize(Orientation.MAX);
        priorityQueue.insert(numberOfStalls);

        Map<Long, Long> countMap = new HashMap<>();
        countMap.put(maxValue, 1L);

        long totalValues = 0;

        while (totalValues < users) {
            maxValue = priorityQueue.deleteTop();

            leftSpaces = (maxValue - 1) / 2;
            rightSpaces = maxValue - 1 - leftSpaces;

            long maxValueCount = 0;
            if (countMap.containsKey(maxValue)) {
                maxValueCount = countMap.get(maxValue);
            }

            totalValues += maxValueCount;

            if (totalValues >= users){
                break;
            }

            set.remove(maxValue);
            if (!set.contains(leftSpaces)) {
                set.add(leftSpaces);
                priorityQueue.insert(leftSpaces);
            }
            if (!set.contains(rightSpaces)) {
                set.add(rightSpaces);
                priorityQueue.insert(rightSpaces);
            }

            long leftSpacesCount = 0;
            if (countMap.containsKey(leftSpaces)) {
                leftSpacesCount = countMap.get(leftSpaces);
            }
            leftSpacesCount += maxValueCount;
            countMap.put(leftSpaces, leftSpacesCount);

            long rightSpacesCount = 0;
            if (countMap.containsKey(rightSpaces)) {
                rightSpacesCount = countMap.get(rightSpaces);
            }
            rightSpacesCount += maxValueCount;
            countMap.put(rightSpaces, rightSpacesCount);
        }

        return new long[]{rightSpaces, leftSpaces};
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> numbersList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i = 1; i < lines.size(); i++) {
                numbersList.add(lines.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numbersList;
    }

    private static void writeDataOnFile(String file, List<String> data){
        for(String line : data) {
            writeFileOutput(file, line + "\n");
        }
    }

    private static void writeFileOutput(String file, String data){
        byte[] dataBytes = data.getBytes();

        try {
            Files.write(Paths.get(file), dataBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private enum Orientation {
        MAX, MIN;
    }

    private class PriorityQueueResize {

        private long[] priorityQueue;
        private int size = 0; // in priorityQueue[1..n] with pq[0] unused
        private Orientation orientation;
        ArrayUtil arrayUtil = new ArrayUtil();

        PriorityQueueResize(Orientation orientation) {
            priorityQueue = new long[2];
            this.orientation = orientation;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public long peek() {
            return priorityQueue[1];
        }

        public void insert(long key) {

            if (size == priorityQueue.length - 1) {
                resize(priorityQueue.length * 2);
            }

            size++;
            priorityQueue[size] = key;

            swim(size);
        }

        public long deleteTop() {

            if (size == 0) {
                throw new RuntimeException("Priority queue underflow");
            }

            size--;

            long top = priorityQueue[1];

            arrayUtil.exchange(priorityQueue, 1, size + 1);

            sink(1);

            if (size == priorityQueue.length / 4) {
                resize(priorityQueue.length / 2);
            }

            return top;
        }

        private void swim(int index) {
            while(index / 2 >= 1) {
                if ((orientation == Orientation.MAX && arrayUtil.less(priorityQueue[index / 2], priorityQueue[index]))
                        || (orientation == Orientation.MIN && arrayUtil.more(priorityQueue[index / 2], priorityQueue[index]))) {
                    arrayUtil.exchange(priorityQueue, index / 2, index);
                } else {
                    break;
                }

                index = index / 2;
            }
        }

        private void sink(int index) {
            while (index * 2 <= size) {
                int selectedChildIndex = index * 2;

                if (index * 2 + 1 <= size &&
                        (
                                (orientation == Orientation.MAX && arrayUtil.less(priorityQueue[index * 2], priorityQueue[index * 2 + 1]))
                                        || (orientation == Orientation.MIN && arrayUtil.more(priorityQueue[index * 2], priorityQueue[index * 2 + 1]))
                        )
                        ){
                    selectedChildIndex = index * 2 + 1;
                }

                if ((orientation == Orientation.MAX && arrayUtil.more(priorityQueue[selectedChildIndex], priorityQueue[index]))
                        || (orientation == Orientation.MIN && arrayUtil.less(priorityQueue[selectedChildIndex], priorityQueue[index]))) {
                    arrayUtil.exchange(priorityQueue, index, selectedChildIndex);
                } else {
                    break;
                }

                index = selectedChildIndex;
            }
        }

        private void resize(int newSize) {
            long[] newPriorityQueue = new long[newSize];
            System.arraycopy(priorityQueue, 1, newPriorityQueue, 1, size);
            priorityQueue = newPriorityQueue;
        }

    }

    private class ArrayUtil {

        boolean less(long value1, long value2) {
            if (value1 < value2) {
                return true;
            } else {
                return false;
            }
        }

        boolean more(long value1, long value2) {
            if (value1 > value2) {
                return true;
            } else {
                return false;
            }
        }

        void exchange(long[] array, int position1, int position2) {
            long temp = array[position1];
            array[position1] = array[position2];
            array[position2] = temp;
        }
    }
}
