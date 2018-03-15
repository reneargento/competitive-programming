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
 *
 Annie is a bus driver with a high-stress job.
 She tried to unwind by going on a Caribbean cruise, but that also turned out to be stressful,
 so she has recently taken up horseback riding.

 Today, Annie is riding her horse to the east along a long and narrow one-way road that runs west to east.
 She is currently at kilometer 0 of the road, and her destination is at kilometer D;
 kilometers along the road are numbered from west to east.

 There are N other horses traveling east on the same road; all of them will go on traveling forever,
 and all of them are currently between Annie's horse and her destination.
 The i-th of these horses is initially at kilometer Ki and is traveling at its maximum speed of Si kilometers per hour.

 Horses are very polite, and a horse H1 will not pass (move ahead of) another horse H2 that started off ahead of H1.
 (Two or more horses can share the same position for any amount of time; you may consider the horses to be single points.)
 Horses (other than Annie's) travel at their maximum speeds, except that whenever a horse H1 catches up to another
 slower horse H2, H1 reduces its speed to match the speed of H2.

 Annie's horse, on the other hand, does not have a maximum speed and can travel at any speed that Annie chooses,
 as long as it does not pass another horse.
 To ensure a smooth ride for her and her horse, Annie wants to choose a single constant "cruise control" speed for her horse
 for the entire trip, from her current position to the destination, such that her horse will not pass any other horses.
 What is the maximum such speed that she can choose?

 Input
 The first line of the input gives the number of test cases, T; T test cases follow.
 Each test case begins with two integers D and N:
 the destination position of all of the horses (in kilometers) and the number of other horses on the road.
 Then, N lines follow.
 The i-th of those lines has two integers Ki and Si: the initial position (in kilometers) and maximum
 speed (in kilometers per hour) of the i-th of the other horses on the road.

 Output
 For each test case, output one line containing Case #x: y, where x is the
 test case number (starting from 1) and y is the maximum constant speed (in kilometers per hour) that Annie
 can use without colliding with other horses.
 y will be considered correct if it is within an absolute or relative error of 10-6 of the correct answer.
 See the FAQ for an explanation of what that means, and what formats of real numbers we accept.

 Limits
 1 ≤ T ≤ 100.
 0 < Ki < D ≤ 109, for all i.
 Ki ≠ Kj, for all i ≠ j. (No two horses start in the same position.)
 1 ≤ Si ≤ 10000.

 Small dataset
 1 ≤ N ≤ 2.

 Large dataset
 1 ≤ N ≤ 1000.

 Sample

 Input
 3
 2525 1
 2400 5
 300 2
 120 60
 60 90
 100 2
 80 100
 70 10

 Output
 Case #1: 101.000000
 Case #2: 100.000000
 Case #3: 33.333333

 In sample case #1, there is one other (very slow!) horse on the road; it will reach Annie's destination after 25 hours.
 Anything faster than 101 kilometers per hour would cause Annie to pass the horse before reaching the destination.

 In sample case #2, there are two other horses on the road.
 The faster horse will catch up to the slower horse at kilometer 240 after 2 hours.
 Both horses will then go at the slower horse's speed for 1 more hour, until the horses reach Annie's destination at kilometer 300.
 The maximum speed that Annie can choose without passing another horse is 100 kilometers per hour.
 */
public class CruiseControl {

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
        int destination1 = 2525;
        Integer[] horses10 = {2400, 5};
        List<Integer[]> horsesInformation1 = new ArrayList<>();
        horsesInformation1.add(horses10);

        System.out.printf("%.6f Expected: 101.000000\n", getSpeed(destination1, horsesInformation1));

        int destination2 = 300;
        Integer[] horses20 = {120, 60};
        Integer[] horses21 = {60, 90};
        List<Integer[]> horsesInformation2 = new ArrayList<>();
        horsesInformation2.add(horses20);
        horsesInformation2.add(horses21);

        System.out.printf("%.6f Expected: 100.000000\n", getSpeed(destination2, horsesInformation2));

        int destination3 = 100;
        Integer[] horses30 = {80, 100};
        Integer[] horses31 = {70, 10};
        List<Integer[]> horsesInformation3 = new ArrayList<>();
        horsesInformation3.add(horses30);
        horsesInformation3.add(horses31);

        System.out.printf("%.6f Expected: 33.333333\n", getSpeed(destination3, horsesInformation3));
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

            double speed = getSpeed(destination, horsesInformation);

            output.add("Case #" + caseIndex + ": " + String.format("%.6f", speed));
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static double getSpeed(int destination, List<Integer[]> horses) {

        double maxHoursRequired = 0;

        for(int i = 0; i < horses.size(); i++) {
            Integer[] horseInformation = horses.get(i);
            double location = horseInformation[0];
            double speed = horseInformation[1];

            double hoursRequired = (destination - location) / speed;
            if (maxHoursRequired < hoursRequired) {
                maxHoursRequired = hoursRequired;
            }
        }

        return destination / maxHoursRequired;
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
