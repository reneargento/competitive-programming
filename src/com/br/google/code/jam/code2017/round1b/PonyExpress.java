package com.br.google.code.jam.code2017.round1b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 22/04/17.
 */

/**
 * It's the year 1860, and the Pony Express is the fastest mail delivery system joining the East and West coasts of the United States.
 This system serves N different cities.
 In each city, there is one horse (as in the expression "one-horse town"); each horse travels at a certain constant speed
 and has a maximum total distance it can travel before it becomes too tired to continue.

 The Pony Express rider starts off on the starting city's horse.
 Every time the rider reaches a city, they may continue to use their current horse or switch to that city's horse;
 switching is instantaneous. Horses never get a chance to rest, so whenever part of a horse's maximum total distance is "used up",
 it is used up forever! When the rider reaches the destination city, the mail is delivered.

 The routes between cities were established via complicated negotiations between company owners, lawmakers, union delegates,
 and cousin Pete. That means that the distances between cities do not necessarily follow common sense: for instance,
 they do not necessarily comply with the triangle inequality, and the distance from city A to city B might be different from
 the distance from city B to city A!

 You are a time traveling entrepreneur, and you have brought a fast computer from the future.
 A single computer is not enough for you to set up an e-mail service and make the Pony Express obsolete,
 but you can use it to make optimal routing plans for the Pony Express.
 Given all data about routes between cities and the horses in each city, and a list of pairs of starting and ending cities,
 can you quickly calculate the minimum time necessary for each delivery?
 (You should treat all of these deliveries as independent; using cities/horses on one route does not make
 them unavailable on other routes.)

 Input

 The first line of the input gives the number of test cases, T. T test cases follow.
 Each test case is described as follows:

 One line with two integers: N, the number of cities with horses, and Q, the number of pairs of stops we are interested in.
 Cities are numbered from 1 to N.

 N lines, each containing two integers Ei, the maximum total distance, in kilometers,
 the horse in the i-th city can go and Si, the constant speed, in kilometers per hour, at which the horse travels.

 N lines, each containing N integers.
 The j-th integer on the i-th of these lines, Dij, is -1 if there is no direct route from the i-th to the j-th city,
 and the length of that route in kilometers otherwise.

 Q lines containing two integers Uk and Vk, the starting and destination point, respectively,
 of the k-th pair of cities we want to investigate.

 Output

 For each test case, output one line containing Case #x: y1 y2 ... yQ, where x is the test case number (starting from 1)
 and yk is the minimum time, in hours, to deliver a letter from city Uk to city Vk.

 Each yk will be considered correct if it is within an absolute or relative error of 10-6 of the correct answer.
 See the FAQ for an explanation of what that means, and what formats of real numbers we accept.

 Limits

 1 ≤ T ≤ 100.
 2 ≤ N ≤ 100.
 1 ≤ Ei ≤ 10^9, for all i.
 1 ≤ Si ≤ 1000, for all i.
 -1 ≤ Dij ≤ 10^9, for all i, j.
 Dii = -1, for all i. (There are no direct routes from a city to itself.)
 Dij ≠ 0, for all i, j.
 Uk ≠ Vk, for all k.
 It is guaranteed that the delivery from Uk to Vk can be accomplished with the given horses, for all k.
 Ul ≠ Um and/or Vl ≠ Vm, for all different l, m. (No ordered pair of cities to investigate is repeated within a test case.)

 Small dataset
 Dij = -1, for all i, j where i + 1 ≠ j. (The cities are in a single line; each route goes from one city to the next city in line.)
 Q = 1.
 U1 = 1.
 V1 = N. (The only delivery to calculate is between the first and last cities in the line).

 Large dataset
 1 ≤ Q ≤ 100.
 1 ≤ Uk ≤ N, for all k.
 1 ≤ Vk ≤ N, for all k.

 Sample

 Input
 3
 3 1
 2 3
 2 4
 4 4
 -1 1 -1
 -1 -1 1
 -1 -1 -1
 1 3
 4 1
 13 10
 1 1000
 10 8
 5 5
 -1 1 -1 -1
 -1 -1 1 -1
 -1 -1 -1 10
 -1 -1 -1 -1
 1 4
 4 3
 30 60
 10 1000
 12 5
 20 1
 -1 10 -1 31
 10 -1 10 -1
 -1 -1 -1 10
 15 6 -1 -1
 2 4
 3 1
 3 2

 Output
 Case #1: 0.583333333
 Case #2: 1.2
 Case #3: 0.51 8.01 8.0

 Note that the last sample case would not appear in the Small dataset.

 In Case #1 there are two options: use the horse in city 1 for the entire trip, or change horses in city 2.
 Both horses have enough endurance, so both options are viable.
 Since the horse in city 2 is faster, it is better to change, for a total time of 1/3 + 1/4.

 In Case #2 there are two intermediate cities in which you can change horses.
 If you change horses in city 2, however, your new horse, while blazingly fast, will not have enough endurance,
 so you will be forced to change again in city 3.
 If you keep your horse, you will have the option to change horses (or not) in city 3.
 So, the three options, with their total times, are:

 Change horses in both city 2 and 3 (1/10 + 1/1000 + 10/8 = 1.351).
 Change horses just in city 3 (2/10 + 10/8 = 1.45).
 Never change horses (12/10 = 1.2).

 In Case #3, there are lots of alternatives for each delivery.
 The optimal one for the first delivery (city 2 to city 4) is to go to city 1 in time 10/1000, change horses,
 and then go to cities 2, 3 and 4, in that order, using the horse from city 1, which takes time (10 + 10 + 10) / 60.

 For the second delivery (city 3 to city 1) you have no choice but to first go to city 4 which takes time 10/5.
 Your relatively fast horse does not have enough endurance to get anywhere else, so you need to grab the horse in city 4.
 You could use it to get directly to city 1 in time 15, but that would be slower than riding it to city 2 in time 6 and
 then using the blazingly fast horse in city 2 to get to city 1 in just 10/1000 extra time.

 In the third delivery (city 3 to city 2) of Case #3 it is optimal to use the first two steps of the previous one,
 for a total time of 10/5 + 6 = 8.

 */
public class PonyExpress {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/2017/Round 1B/Input - Output/";

    //    private static final String FILE_INPUT_PATH = PATH + "cruise_control_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "cruise_control_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "cruise_control_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "cruise_control_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "cruise_control_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "cruise_control_large_output.txt";

    public static void main(String[] args) {

        test();
        //compete();
    }

    private static void test() {
        double[][] horses1 = {{2, 3},
                {2, 4},
                {4, 4}};

        int[][] distances1 = {{-1, 1, -1},
                {-1, -1, 1},
                {-1, -1, -1}};
        int source1 = 1;
        int destination1 = 3;

        System.out.println(getTimeToTravelSmall(source1, destination1, horses1, distances1) + " Expected: 0.583333333");

        double[][] horses2 = {{13, 10},
                {1, 1000},
                {10, 8},
                {5, 5}};

        int[][] distances2 = {{-1, 1, -1, -1},
                {-1, -1, 1, -1},
                {-1, -1, -1, 10},
                {-1, -1, -1, -1}};
        int source2 = 1;
        int destination2 = 4;

        System.out.println(getTimeToTravelSmall(source2, destination2, horses2, distances2) + " Expected: 1.2");

//        double[][] horses3 = {{30, 60},
//                {10, 1000},
//                {12, 5},
//                {20, 1}};
//
//        int[][] distances3 = {{-1, 10, -1, 31},
//                {10, -1, 10, -1},
//                {-1, -1, -1, 10},
//                {15, 6, -1, -1}};
//
//        int[][] sourceDestinations3 = {{2, 4},
//                {3, 1},
//                {3, 2}};
    }

    private static void compete() {
        List<String> cruiseInformation = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int i = 0; i < cruiseInformation.size(); i++) {

            String[] values = cruiseInformation.get(i).split(" ");
            int destination = Integer.parseInt(values[0]);
            int numberOfHorses = Integer.parseInt(values[1]);

            List<Integer[]> horsesInformation = new ArrayList<>();

            for(int j = 0; j < numberOfHorses; j++) {
                i++;

                Integer[] horseInfo = new Integer[2];
                String[] horseValues = cruiseInformation.get(i).split(" ");
                horseInfo[0] = Integer.parseInt(horseValues[0]);
                horseInfo[1] = Integer.parseInt(horseValues[1]);
                horsesInformation.add(horseInfo);
            }

//            double speed = getTimeToTravel(destination, horsesInformation);
//
//            output.add("Case #" + caseIndex + ": " + String.format("%.6f", speed));
//            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static double getTimeToTravelSmall(int source, int destination, double[][] horses, int[][] distances) {

        double time = 0;
        double totalDistance = 0;

        int travelledHorseDistance = 0;
        double horseEndurance = horses[0][1];

        for(int i = source - 1; i < destination - 1; i++) {
            totalDistance += distances[i][i+1];

            if (travelledHorseDistance + distances[i][i+1] < horseEndurance) {
                travelledHorseDistance += distances[i][i+1];
            }

        }

        if (horses[0][0] >= totalDistance) {
            time = totalDistance / horses[0][1];
        }

        return time;
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> valuesList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i = 1; i < lines.size(); i++) {
                valuesList.add(lines.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return valuesList;
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
