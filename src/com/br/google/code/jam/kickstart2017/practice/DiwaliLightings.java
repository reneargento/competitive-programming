package com.br.google.code.jam.kickstart2017.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 11/06/17.
 */
public class DiwaliLightings {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Kickstart 2017/Practice/Round 2/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "diwali_lightings_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "diwali_lightings_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "diwali_lightings_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "diwali_lightings_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "diwali_lightings_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "diwali_lightings_large_output.txt";

    public static void main(String[] args) {

        test();
        //compete();
    }

    private static void test() {
        String pattern1 = "BBRB";
        int start1 = 4;
        int end1 = 8;
        System.out.println(countBlueBulbs(pattern1, start1, end1) + " Expected: 4");

        String pattern2 = "BBRB";
        int start2 = 10;
        int end2 = 12;
        System.out.println(countBlueBulbs(pattern2, start2, end2) + " Expected: 2");

        String pattern3 = "BR";
        int start3 = 1;
        int end3 = 1000000;
        System.out.println(countBlueBulbs(pattern3, start3, end3) + " Expected: 500000");

        String pattern4 = "B";
        int start4 = 1;
        int end4 = 1000000;
        System.out.println(countBlueBulbs(pattern4, start4, end4) + " Expected: 1000000");

        String pattern5 = "R";
        int start5 = 1;
        int end5 = 1;
        System.out.println(countBlueBulbs(pattern5, start5, end5) + " Expected: 0");

        String pattern6 = "B";
        int start6 = 1;
        int end6 = 1;
        System.out.println(countBlueBulbs(pattern6, start6, end6) + " Expected: 1");

        String pattern7 = "BBRB";
        int start7 = 10;
        int end7 = 21;
        System.out.println(countBlueBulbs(pattern7, start7, end7) + " Expected: 9");

        String pattern8 = "BBRBBB";
        int start8 = 1;
        int end8 = 6;
        System.out.println(countBlueBulbs(pattern8, start8, end8) + " Expected: 5");

        String pattern9 = "BBRBBB";
        int start9 = 1;
        int end9 = 7;
        System.out.println(countBlueBulbs(pattern9, start9, end9) + " Expected: 6");
    }

    private static void compete() {
        List<String> diwaliInformation = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int i = 0; i < diwaliInformation.size(); i++) {

            String pattern = diwaliInformation.get(i);
            i++;
            String[] values = diwaliInformation.get(i).split(" ");
            long start = Long.parseLong(values[0]);
            long end = Long.parseLong(values[1]);

            long blueBulbs = countBlueBulbs(pattern, start, end);

            output.add("Case #" + caseIndex + ": " + blueBulbs);
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static long countBlueBulbs(String pattern, long start, long end) {

        long patternLength = pattern.length();
        long blueBulbs = 0;

        long difference = end - start + 1;

        long smallestMultiple = start - 1;
        smallestMultiple = roundDown(smallestMultiple, patternLength);

        long highestMultiple = end;
        highestMultiple = roundUp(highestMultiple, patternLength);

        long newStart = start;
        long newEnd = end;

        for(long i = start - smallestMultiple - 1; i < patternLength && i <= difference; i++) {
            if (pattern.charAt((int)i) == 'B') {
                blueBulbs++;
            }

            newStart++;
        }

        long highBound = highestMultiple - end;

        for(int i = 0; i < patternLength - highBound; i++) {
            if (pattern.charAt(i) == 'B') {
                blueBulbs++;
            }

            newEnd--;
        }

        difference = newEnd - newStart + 1;
        long multiples = difference / patternLength;

        int numberOfBlueBulbsOnPattern = 0;
        for(int i = 0; i < patternLength; i++) {
            if (pattern.charAt(i) == 'B') {
                numberOfBlueBulbsOnPattern++;
            }
        }

        blueBulbs += numberOfBlueBulbsOnPattern * multiples;

        return blueBulbs;
    }

    private static long roundDown(long number, long multiple) {
        if (number < multiple) {
            return 0;
        }

        long temp = number % multiple;

        if (temp < number - 2) {
            return number - temp;
        } else {
            return number + multiple - temp;
        }
    }

    private static long roundUp(long number, long multiple) {
        return (number + (multiple - 1)) / multiple * multiple;
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
