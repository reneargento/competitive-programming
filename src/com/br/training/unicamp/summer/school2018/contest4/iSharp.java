package com.br.training.unicamp.summer.school2018.contest4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 26/01/18.
 */
public class iSharp {

    public static void main(String[] args) throws IOException {
        String inputFile = "isharp.in";
        String outputFile = "isharp.out";
        List<String> input = readFileInput(inputFile);

        String[] values = input.get(0).split(" ");
        String prefix = values[0];
        StringBuilder result = new StringBuilder();

        for(int i = 1; i < values.length; i++) {

            int variableNameStartIndex = 0;
            char current = values[i].charAt(variableNameStartIndex);
            StringBuilder variableName = new StringBuilder();

            while (current != '*'
                    && current != '&'
                    && current != '['
                    && current != ']'
                    && current != ','
                    && current != ';') {
                variableName.append(current);
                variableNameStartIndex++;

                current = values[i].charAt(variableNameStartIndex);
            }

            result.append(prefix);
            int rightIndex = values[i].length() - 2;

            while (rightIndex >= variableNameStartIndex) {
                char currentChar = values[i].charAt(rightIndex);

                if (currentChar == ']') {
                    result.append("[]");
                    rightIndex--;
                } else {
                    result.append(currentChar);
                }

                rightIndex--;
            }

            result.append(" ").append(variableName).append(";").append("\n");
        }

        List<String> output = new ArrayList<>();
        output.add(String.valueOf(result.toString()));
        writeDataOnFile(outputFile, output);
    }

    private static List<String> readFileInput(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
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
