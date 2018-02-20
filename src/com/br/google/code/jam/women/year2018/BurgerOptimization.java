package com.br.google.code.jam.women.year2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 17/02/18.
 */
public class BurgerOptimization {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Women/2018/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "burgers_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "burgers_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "burgers_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "burgers_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "burgers_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "burgers_large_output.txt";

    public static void main(String[] args) {
        test();
       // compete();
    }

    private static void compete() {
        List<String> input = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int l = 0; l < input.size() - 1; l += 2) {

            int ingredients = Integer.parseInt(input.get(l));

            int[] distances = new int[ingredients];

            String[] values = input.get(l + 1).split(" ");

            for(int i = 0; i < values.length; i++) {
                distances[i] = Integer.parseInt(values[i]);
            }

            output.add("Case #" + caseIndex + ": " + getMinimalError(distances));
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        int[] distances1 = {0, 2, 1, 1, 2};
        int[] distances2 = {0};
        int[] distances3 = {2, 2, 2, 2, 2, 2};
        int[] distances4 = {3, 0, 0, 0, 0, 3};
        int[] distances5 = {3, 3, 0, 3, 1, 3, 3, 3};
        int[] distances6 = {3, 3, 3, 2, 0, 0, 0};
        int[] distances7 = {0, 0};

        System.out.println(getMinimalError(distances1) + " Expected: 2");
        System.out.println(getMinimalError(distances2) + " Expected: 0");
        System.out.println(getMinimalError(distances3) + " Expected: 10");
        System.out.println(getMinimalError(distances4) + " Expected: ?");
        System.out.println(getMinimalError(distances5) + " Expected: ?");
        System.out.println(getMinimalError(distances6) + " Expected: ?");
        System.out.println(getMinimalError(distances7) + " Expected: ?");
    }

    private static long getMinimalError(int[] distances) {

        if (distances.length == 1) {
            return (long) Math.pow(distances[0], 2);
        }

        Arrays.sort(distances);

        int[] newDistances = new int[distances.length];
        int newDistancesLeftIndex = 0;
        int newDistancesRightIndex = distances.length - 1;

        boolean left = true;

        for(int i = 0; i < distances.length; i++) {
            if (left) {
                newDistances[newDistancesLeftIndex] = distances[i];
                newDistancesLeftIndex++;
                left = false;
            } else {
                newDistances[newDistancesRightIndex] = distances[i];
                newDistancesRightIndex--;
                left = true;
            }
        }

        long minimalError = 0;

        int upperIndex;

        if (newDistances.length % 2 == 0) {
            upperIndex = newDistances.length / 2 - 1;
        } else {
            upperIndex = newDistances.length / 2;
        }

        int lastPlace = -1;

        for(int i = 0; i < upperIndex; i++) {
            minimalError += Math.pow(newDistances[i] - i, 2);
            lastPlace = i;
        }

        int k = lastPlace + 1;
        boolean decrement = false;

        for(int i = upperIndex; i < newDistances.length; i++) {
            minimalError += Math.pow(newDistances[i] - k, 2);

            if (!decrement && newDistances.length % 2 == 0) {
                decrement = true;
            } else {
                k--;
            }

        }

        return minimalError;
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
