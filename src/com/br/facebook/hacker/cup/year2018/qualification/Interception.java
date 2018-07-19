package com.br.facebook.hacker.cup.year2018.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Interception {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2018/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "interception_example_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "interception_example_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "interception_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "interception_output.txt";

    public static void main(String[] args) {
        //test();
        compete();
    }

    private static void test() {
        System.out.println(checkPolynomial(1) + " Expected: false");
        System.out.println(checkPolynomial(4) + " Expected: true");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            int degree = Integer.parseInt(lines.get(i));

            // We can ignore the coefficients
            i += (degree + 1);
            String result;

            if (!checkPolynomial(degree)) {
                result = "Case #" + caseId + ": " + "1\n0.0";
            } else {
                result = "Case #" + caseId + ": " + "0";
            }

            output.add(result);
            caseId++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static boolean checkPolynomial(int polynomialSize) {
        return polynomialSize % 2 == 0;
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
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
