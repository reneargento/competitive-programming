package com.br.facebook.hacker.cup.year2017.qualification;

/**
 * Created by rene on 07/01/17.
 */
//Based on https://www.facebook.com/hackercup/problem/169401886867367/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lazy Loading
 *
 * Wilson works for a moving company.
 * His primary duty is to load household items into a moving truck.
 * Wilson has a bag that he uses to move these items.
 * He puts a bunch of items in the bag, moves them to the truck, and then drops the items off.

 Wilson has a bit of a reputation as a lazy worker.
 Julie is Wilson's supervisor, and she's keen to make sure that he doesn't slack off.
 She wants Wilson to carry at least 50 pounds of items in his bag every time he goes to the truck.

 Luckily for Wilson, his bag is opaque.
 When he carries a bagful of items, Julie can tell how many items are in the bag
 (based on the height of the stack in the bag), and she can tell the weight of the top item.
 She can't, however, tell how much the other items in the bag weigh.
 She assumes that every item in the bag weighs at least as much as this top item,
 because surely Wilson, as lazy as he is, would at least not be so dense as to put heavier items on top of lighter ones.
 Alas, Julie is woefully ignorant of the extent of Wilson's lack of dedication to his duty,
 and this assumption is frequently incorrect.

 Today there are N items to be moved, and Wilson, paid by the hour as he is,
 wants to maximize the number of trips he makes to move all of them to the truck.
 What is the maximum number of trips Wilson can make without getting berated by Julie?

 Note that Julie is not aware of what items are to be moved today, and she is not keeping
 track of what Wilson has already moved when she examines each bag of items.
 She simply assumes that each bagful contains a total weight of at least k * w where k is the number of items
 in the bag, and w is the weight of the top item.

 Input
 Input begins with an integer T, the number of days Wilson "works" at his job.
 For each day, there is first a line containing the integer N.
 Then there are N lines, the ith of which contains a single integer, the weight of the ith item, Wi.

 Output
 For the ith day, print a line containing "Case #i: " followed by the maximum number of trips Wilson can take that day.

 Constraints
 1 ≤ T ≤ 500
 1 ≤ N ≤ 100
 1 ≤ Wi ≤ 100
 On every day, it is guaranteed that the total weight of all of the items is at least 50 pounds.

 Example input
 5
 4
 30
 30
 1
 1
 3
 20
 20
 20
 11
 1
 2
 3
 4
 5
 6
 7
 8
 9
 10
 11
 6
 9
 19
 29
 39
 49
 59
 10
 32
 56
 76
 8
 44
 60
 47
 85
 71
 91

 Example output
 Case #1: 2
 Case #2: 1
 Case #3: 2
 Case #4: 3
 Case #5: 8

 Explanation of Sample
 In the first case, Wilson can make two trips by stacking a 30-pound item on top of a 1-pound item,
 making the bag appear to contain 60 pounds.

 In the second case, Wilson needs to put all the items in the bag at once and can only make one trip.

 In the third case, one possible solution is to put the items with odd weight in the bag for the first trip,
 and then the items with even weight in the bag for the second trip, making sure to put the heaviest item on top.
 */

public class LazyLoading {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2017/Qualification/Input - Output/";

    private static final String FILE_INPUT_PATH = PATH + "lazy_loading_example_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "lazy_loading_output.txt";

    //private static final String FILE_INPUT_PATH = PATH + "lazy_loading_input.txt";
    //private static final String FILE_OUTPUT_PATH = PATH + "lazy_loading_output_submission.txt";

    public static void main(String[] args) {

        long[] weights1 = {4, 30, 30, 1, 1};
        long[] weights2 = {20, 20, 20};
        long[] weights3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        long[] weights4 = {9, 19, 29, 39, 49, 59};
        long[] weights5 = {32, 56, 76, 8, 44, 60, 47, 85, 71, 91};

        System.out.println(numberOfTrips(weights1) + " Expected: 2");
        System.out.println(numberOfTrips(weights2) + " Expected: 1");
        System.out.println(numberOfTrips(weights3) + " Expected: 2");
        System.out.println(numberOfTrips(weights4) + " Expected: 3");
        System.out.println(numberOfTrips(weights5) + " Expected: 8");

        long[] input = readFileInput(FILE_INPUT_PATH);
        List<String> trips = new ArrayList<>();

        //long numberOfDays = input[0]; //not needed
        int dayIndex = 1;

        for(int  i = 1; i < input.length; i++) {

            long numberWeights = input[i];
            long[] weights = new long[(int) numberWeights];

            for(int j = 0; j < numberWeights; j++) {
                i++;
                weights[j] = input[i];
            }

            long numberOfTrips = numberOfTrips(weights);
            trips.add("Case #" + dayIndex + ": " + numberOfTrips);

            dayIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, trips);
    }

    //O(n log n)
    private static long numberOfTrips(long[] weights) {

        if (weights == null || weights.length == 0) {
            return 0;
        }

        Arrays.sort(weights);

        long numberOfTrips = 0;
        long currentTripWeight;

        int startIndex = 0;
        int endIndex = weights.length - 1;

        while(startIndex <= endIndex) {
            currentTripWeight = 0;

            //Add the heaviest item
            currentTripWeight += weights[endIndex];

            while(currentTripWeight < 50 && startIndex < endIndex) {
                currentTripWeight += weights[endIndex];
                startIndex++;
            }

            if (currentTripWeight >= 50) {
                numberOfTrips++;
            }

            endIndex--;
        }

        return numberOfTrips;
    }

    private static long[] readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<Long> numbers = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                numbers.add(Long.parseLong(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long[] numberArray = new long[numbers.size()];

        for (int i = 0; i < numbers.size(); i++){
            numberArray[i] = numbers.get(i);
        }

        return numberArray;
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

}
