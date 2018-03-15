package com.br.google.code.jam.kickstart2017.roundf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rene on 24/09/17.
 */
public class Kicksort {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Kickstart 2017/Round F/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "kicksort_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "kicksort_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "kicksort_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "kicksort_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "kicksort_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "kicksort_large_output.txt";

    public static void main(String[] args) {
       // test();
         compete();
    }

    private static void compete() {
        List<String> input = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int l = 0; l < input.size(); l++) {

            int arraySize = Integer.parseInt(input.get(l));
            l++;

            String[] values = input.get(l).split(" ");

            int[] array = new int[arraySize];
            for(int i = 0; i < values.length; i++) {
                array[i] = Integer.parseInt(values[i]);
            }

            output.add("Case #" + caseIndex + ": " + isWorstCase(array));
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        int[] array1 = {1, 4, 3, 2};
        int[] array2 = {2, 1, 3, 4};
        int[] array3 = {2, 1};
        int[] array4 = {1, 2, 3};
        int[] array5 = {3, 2, 4, 1};
        int[] array6 = {1,2};

        System.out.println(isWorstCase(array1) + " Expected: YES");
        System.out.println(isWorstCase(array2) + " Expected: NO");
        System.out.println(isWorstCase(array3) + " Expected: YES");
        System.out.println(isWorstCase(array4) + " Expected: NO");
        System.out.println(isWorstCase(array5) + " Expected: NO");
        System.out.println(isWorstCase(array6) + " Expected: YES");
    }

    private static String isWorstCase(int[] array) {

        String isWorstCase = "YES";
        String isNotWorstCase = "NO";

        int left;
        int right;
        boolean isNextLeft;
        int currentValue;

        int copyLeft = 0;
        int copyRight = array.length - 1;

        int[] copyArray = new int[array.length];
        System.arraycopy(array, 0, copyArray, 0, array.length);

        Arrays.sort(copyArray);

        if (array.length % 2 != 0) {
            currentValue = array[array.length / 2];
            isNextLeft = true;

            left = array.length / 2;
        } else {
            currentValue = array[array.length / 2 - 1];
            isNextLeft = false;

            left = (array.length / 2) - 1;
        }

        right = left;

        while (left != 0 && right != array.length - 1) {

            if ((copyLeft >= 0 && currentValue != copyArray[copyLeft])
                    && (copyRight < copyArray.length && currentValue != copyArray[copyRight])) {
                return isNotWorstCase;
            } else {
                if (copyLeft >= 0 && currentValue == copyArray[copyLeft]) {
                    copyLeft++;
                } else if (copyRight < copyArray.length && currentValue == copyArray[copyRight]) {
                    copyRight--;
                }
            }

            int nextValue;

            if (isNextLeft) {
                left = left - 1;

                if (left < 0) {
                    return isWorstCase;
                }

                nextValue = array[left];
            } else {
                right = right + 1;

                if (right > array.length - 1) {
                    return isWorstCase;
                }

                nextValue = array[right];
            }

            isNextLeft = !isNextLeft;
            currentValue = nextValue;
        }

        return isWorstCase;
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
