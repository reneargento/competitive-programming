package com.br.google.code.jam.code2017.qualification;

/**
 * Created by rene on 07/04/17.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem

 Tatiana likes to keep things tidy. Her toys are sorted from smallest to largest,
 her pencils are sorted from shortest to longest and her computers from oldest to newest.
 One day, when practicing her counting skills, she noticed that some integers, when written in base 10 with no leading zeroes,
 have their digits sorted in non-decreasing order. Some examples of this are 8, 123, 555, and 224488.
 She decided to call these numbers tidy.
 Numbers that do not have this property, like 20, 321, 495 and 999990, are not tidy.

 She just finished counting all positive integers in ascending order from 1 to N.
 What was the last tidy number she counted?

 Input
 The first line of the input gives the number of test cases, T.
 T lines follow. Each line describes a test case with a single integer N, the last number counted by Tatiana.

 Output
 For each test case, output one line containing
 Case #x: y, where x is the test case number (starting from 1) and y is the last tidy number counted by Tatiana.

 Limits
 1 ≤ T ≤ 100.

 Small dataset
 1 ≤ N ≤ 1000.

 Large dataset
 1 ≤ N ≤ 1018.

 Sample

 Input

 4
 132
 1000
 7
 111111111111111110

 Output

 Case #1: 129
 Case #2: 999
 Case #3: 7
 Case #4: 99999999999999999

 Note that the last sample case would not appear in the Small dataset.
 */
public class TidyNumbers {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/2017/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "tidy_numbers_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "tidy_numbers_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "tidy_numbers_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "tidy_numbers_large_output.txt";

    public static void main(String[] args) {

        test();

//        List<String> numbersList = readFileInput(FILE_INPUT_PATH);
//
//        List<String> output = new ArrayList<>();
//
//        int caseIndex = 1;
//        for(String number : numbersList) {
//            String lastTidyNumber = getLastTidyNumber(number);
//            output.add("Case #" + caseIndex + ": " + lastTidyNumber);
//
//            caseIndex++;
//        }
//
//        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        String number1 = "132";
        String number2 = "1000";
        String number3 = "7";
        String number4 = "111111111111111110";

        System.out.println(getLastTidyNumber(number1) + " Expected: 129");
        System.out.println(getLastTidyNumber(number2) + " Expected: 999");
        System.out.println(getLastTidyNumber(number3) + " Expected: 7");
        System.out.println(getLastTidyNumber(number4) + " Expected: 99999999999999999");
    }

    private static String getLastTidyNumber(String currentNumber) {
        char[] numbersCharArray = currentNumber.toCharArray();
        boolean ordered = false;

        while (!ordered) {
            ordered = true;

            for(int i = 0; i < numbersCharArray.length - 1; i++) {
                int number1 = (int) numbersCharArray[i];
                int number2 = (int) numbersCharArray[i + 1];

                if (number1 > number2) {
                    ordered = false;

                    numbersCharArray[i] = (char) (number1 - 1);
                    for(int j = i + 1; j < numbersCharArray.length; j++) {
                        numbersCharArray[j] = '9';
                    }

                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        boolean numberStarted = false;
        for (char number : numbersCharArray) {
            if (!numberStarted && number == '0') {
                continue;
            }

            numberStarted = true;
            result.append(number);
        }

        return result.toString();
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

}
