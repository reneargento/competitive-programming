package com.br.google.code.jam.kickstart2017.roundf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 24/09/17.
 */
//TODO
public class EatCake {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Kickstart 2017/Round F/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "eatcake_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "eatcake_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "eatcake_small_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "eatcake_small_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "eatcake_large_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "eatcake_large_output.txt";

    public static void main(String[] args) {
         //test();
        compete();
    }

    private static void compete() {
        List<String> input = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int l = 0; l < input.size(); l++) {

            int area = Integer.parseInt(input.get(l));

            output.add("Case #" + caseIndex + ": " + countCakes(area));
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        System.out.println(countCakes(3) + " Expected: 3");
        System.out.println(countCakes(4) + " Expected: 1");
        System.out.println(countCakes(5) + " Expected: 2");
        System.out.println(countCakes(20) + " Expected: 2");
    }

    private static int countCakes(int area) {

        int cakes = area;

        int temp = area;
        boolean foundSqrt;

        while (temp > 1) {
            double sqrt = Math.sqrt(temp);
            foundSqrt = false;

            if (sqrt == (int) sqrt) {
                area -= temp;
                cakes -= temp;
                cakes++;

                temp = area;
                foundSqrt = true;
            }

            if (!foundSqrt) {
                temp--;
            }
        }

        return cakes;
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
