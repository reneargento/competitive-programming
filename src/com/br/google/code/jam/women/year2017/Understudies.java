package com.br.google.code.jam.women.year2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene on 11/03/17.
 */
public class Understudies {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Women/2017/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "understudies_small_input_sample.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "understudies_small_output_sample.txt";
//    private static final String FILE_INPUT_PATH = PATH + "understudies_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "understudies_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "understudies_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "understudies_large_output.txt";

    public static void main(String[] args) {

        List<double[]> probabilities = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(double[] probability : probabilities) {
            String probabilityOfSuccess = probabilityOfTheShowSuccess(probability);
            output.add("Case #" + caseIndex + ": " + probabilityOfSuccess);

            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static List<double[]> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<double[]> probabilities = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            int i = 1;
            while (i < lines.size()) {
                String[] firstLine = lines.get(i).split(" ");
                int numberOfRoles = Integer.parseInt(firstLine[0]);

                double[] probability = new double[numberOfRoles * 2];

                String[] probabilitiesLine = lines.get(i + 1).split(" ");
                for(int j = 0; j < probabilitiesLine.length; j++) {
                    probability[j] = Double.parseDouble(probabilitiesLine[j]);
                }

                probabilities.add(probability);
                i += 2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return probabilities;
    }

    private static String probabilityOfTheShowSuccess(double[] probabilities) {

        double result = 1;

        Arrays.sort(probabilities);

        int leftIndex = 0;
        int rightIndex = probabilities.length - 1;

        for(int i = 0; i < probabilities.length / 2; i++) {

            double probabilityOfCurrentRole = 1 - probabilities[leftIndex] * probabilities[rightIndex];
            result *= probabilityOfCurrentRole;

            leftIndex++;
            rightIndex--;
        }

        return String.format("%.6f", result);
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
