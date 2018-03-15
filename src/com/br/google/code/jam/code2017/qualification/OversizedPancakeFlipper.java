package com.br.google.code.jam.code2017.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by rene on 11/03/17.
 */

/**
 * Problem

 Last year, the Infinite House of Pancakes introduced a new kind of pancake.
 It has a happy face made of chocolate chips on one side (the "happy side"), and nothing on the other side (the "blank side").

 You are the head cook on duty.
 The pancakes are cooked in a single row over a hot surface.
 As part of its infinite efforts to maximize efficiency, the House has recently given you an oversized pancake flipper
 that flips exactly K consecutive pancakes.
 That is, in that range of K pancakes, it changes every happy-side pancake to a blank-side pancake, and vice versa;
 it does not change the left-to-right order of those pancakes.

 You cannot flip fewer than K pancakes at a time with the flipper, even at the ends of the row
 (since there are raised borders on both sides of the cooking surface).
 For example, you can flip the first K pancakes, but not the first K - 1 pancakes.

 Your apprentice cook, who is still learning the job, just used the old-fashioned single-pancake flipper to
 flip some individual pancakes and then ran to the restroom with it, right before the time when customers come to
 visit the kitchen.
 You only have the oversized pancake flipper left, and you need to use it quickly to leave all the cooking pancakes happy
 side up, so that the customers leave feeling happy with their visit.

 Given the current state of the pancakes, calculate the minimum number of uses of the oversized pancake flipper needed
 to leave all pancakes happy side up, or state that there is no way to do it.

 Input

 The first line of the input gives the number of test cases, T.
 T test cases follow.
 Each consists of one line with a string S and an integer K.
 S represents the row of pancakes:
 each of its characters is either + (which represents a pancake that is initially happy side up)
 or - (which represents a pancake that is initially blank side up).

 Output

 For each test case, output one line containing
 Case #x: y, where x is the test case number (starting from 1) and y is either IMPOSSIBLE if
 there is no way to get all the pancakes happy side up, or an integer representing the the
 minimum number of times you will need to use the oversized pancake flipper to do it.

 Limits

 1 ≤ T ≤ 100.
 Every character in S is either + or -.
 2 ≤ K ≤ length of S.

 Small dataset
 2 ≤ length of S ≤ 10.

 Large dataset
 2 ≤ length of S ≤ 1000.

 Sample


 Input

 Output

 3
 ---+-++- 3
 +++++ 4
 -+-+- 4

 Case #1: 3
 Case #2: 0
 Case #3: IMPOSSIBLE

 In Case #1, you can get all the pancakes happy side up by first flipping the leftmost 3 pancakes,
 getting to ++++-++-, then the rightmost 3, getting to ++++---+, and finally the 3 pancakes that remain blank side up.
 There are other ways to do it with 3 flips or more, but none with fewer than 3 flips.

 In Case #2, all of the pancakes are already happy side up, so there is no need to flip any of them.

 In Case #3, there is no way to make the second and third pancakes from the left have the same side up,
 because any flip flips them both. Therefore, there is no way to make all of the pancakes happy side up.
 */
public class OversizedPancakeFlipper {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/2017/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "pancake_flipper_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "pancake_flipper_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "pancake_flipper_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "pancake_flipper_large_output.txt";

    public static void main(String[] args) {

        test();

//        List<String> pancakesRowList = readFileInput(FILE_INPUT_PATH);
//
//        List<String> output = new ArrayList<>();
//
//        int caseIndex = 1;
//        for(String pancakeRow : pancakesRowList) {
//            String numberOfFlips = getNumberOfFlips(pancakeRow);
//            output.add("Case #" + caseIndex + ": " + numberOfFlips);
//
//            caseIndex++;
//        }
//
//        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        String pacakesRow1 = "---+-++- 3";
        String pacakesRow2 = "+++++ 4";
        String pacakesRow3 = "-+-+- 4";

        System.out.println(getNumberOfFlips(pacakesRow1) + " Expected: 3");
        System.out.println(getNumberOfFlips(pacakesRow2) + " Expected: 0");
        System.out.println(getNumberOfFlips(pacakesRow3) + " Expected: IMPOSSIBLE");
    }

    private static String getNumberOfFlips(String pancakeRow) {
        String[] input = pancakeRow.split(" ");
        String pancakes = input[0];
        int pancakeFlipperSize = Integer.parseInt(input[1]);

        int numberOfFlips = 0;

        char[] pancakesCharArray = pancakes.toCharArray();
        for(int i = 0; i < pancakesCharArray.length; i++) {
            if (pancakesCharArray[i] == '-') {
                numberOfFlips++;

                if (i + pancakeFlipperSize - 1 < pancakesCharArray.length) {
                    int pancakesFlipped = 0;

                    while (pancakesFlipped < pancakeFlipperSize) {
                        if (pancakesCharArray[i + pancakesFlipped] == '-') {
                            pancakesCharArray[i + pancakesFlipped] = '+';
                        } else {
                            pancakesCharArray[i + pancakesFlipped] = '-';
                        }

                        pancakesFlipped++;
                    }
                } else {
                    return "IMPOSSIBLE";
                }
            }
        }

        return String.valueOf(numberOfFlips);
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> pancakesRowList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i = 1; i < lines.size(); i++) {
                pancakesRowList.add(lines.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pancakesRowList;
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
