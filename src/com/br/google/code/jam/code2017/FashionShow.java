package com.br.google.code.jam.code2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 08/04/17.
 */
public class FashionShow {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/2017/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "fashion_show_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "fashion_show_sample_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "fashion_show_small_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "fashion_show_small_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "fashion_show_large_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "fashion_show_large_output.txt";

    public static void main(String[] args) {

        test();
        //compete();
    }

    private static void compete() {
        List<String> input = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int i=0; i < input.size(); i++) {
            String[] matrixSizeAndModels = input.get(i).split(" ");

            int matrixSize = Integer.parseInt(matrixSizeAndModels[0]);
            int numberOfModelsPreSet = Integer.parseInt(matrixSizeAndModels[1]);

            String[] models = new String[numberOfModelsPreSet];

            for(int j=0; j < numberOfModelsPreSet; j++) {
                models[j] = input.get(++i);
            }

            String[] caseOutput = getStylishConfiguration(matrixSize, models);
            String[] stylePointsAndModelsUpdated = caseOutput[0].split(" ");
            int stylePoints = Integer.parseInt(stylePointsAndModelsUpdated[0]);
            int modelsUpdated = Integer.parseInt(stylePointsAndModelsUpdated[1]);

            output.add("Case #" + caseIndex + ": " + stylePoints + " " + modelsUpdated);
            for(int model=1; model <= modelsUpdated; model++) {
                output.add(caseOutput[model]);
            }

            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        String[] modelConfiguration1 = new String[0];

        String[] modelConfiguration2 = new String[1];
        modelConfiguration2[0] = "o 1 1";

        String[] modelConfiguration3 = new String[4];
        modelConfiguration3[0] = "+ 2 3";
        modelConfiguration3[1] = "+ 2 1";
        modelConfiguration3[2] = "x 3 1";
        modelConfiguration3[3] = "+ 2 2";

        String[] modelConfiguration4 = new String[1];
        modelConfiguration4[0] = "x 1 1";

        String[] output1 = getStylishConfiguration(2, modelConfiguration1);
        String[] output2 = getStylishConfiguration(1, modelConfiguration2);
        //Big test case
        String[] output3 = getStylishConfiguration(3, modelConfiguration3);
        //Small test case
        String[] output4 = getStylishConfiguration(3, modelConfiguration4);

        System.out.println("Output 1:");
        for(int i=0; i < output1.length; i++) {
            System.out.println(output1[i]);
        }
        System.out.println();
        System.out.println("Expected:");
        System.out.println("4 3\n" +
                "o 2 2\n" +
                "+ 2 1\n" +
                "x 1 1");

        System.out.println();
        System.out.println("Output 2:");
        for(int i=0; i < output2.length; i++) {
            System.out.println(output2[i]);
        }
        System.out.println();
        System.out.println("Expected:");
        System.out.println("2 0");

        System.out.println();
        System.out.println("Output 3:");
        for(int i=0; i < output3.length; i++) {
            System.out.println(output3[i]);
        }
        System.out.println();
        System.out.println("Expected:");
        System.out.println("6 2\n" +
                "o 2 3\n" +
                "x 1 2");

        System.out.println();
        System.out.println("Output 4:");
        for(int i=0; i < output4.length; i++) {
            System.out.println(output4[i]);
        }
        System.out.println();
        System.out.println("Expected:");
        System.out.println("6 4\n" +
                "o 1 3\n" +
                "+ 1 2\n" +
                "x 2 2\n" +
                "+ 3 2");
    }

    private static String[] getStylishConfiguration(int matrixSize, String[] models) {

        String[][] modelsMatrix = new String[matrixSize][matrixSize];
        int numberOfModelsUpdated = 0;
        int totalPoints = 0;
        List<String> modelsUpdated = new ArrayList<>();

        for(int i=0; i < modelsMatrix.length; i++) {
            for(int j=0; j < modelsMatrix[0].length; j++) {
                modelsMatrix[i][j] = ".";
            }
        }

        for(int i=0; i < models.length; i++) {
            String[] modelConfiguration = models[i].split(" ");
            String modelStyle = modelConfiguration[0];
            int row = Integer.parseInt(modelConfiguration[1]) - 1;
            int column = Integer.parseInt(modelConfiguration[2]) - 1;

            modelsMatrix[row][column] = modelStyle;
            if(modelStyle.equals("+") || modelStyle.equals("x")){
                totalPoints++;
            } else if(modelStyle.equals("o")) {
                totalPoints += 2;
            }
        }

        int superTrendyStyleColumnIndex = -1;
        for(int column=0; column < modelsMatrix[0].length; column++) {
            if(modelsMatrix[0][column].equals("o")) {
                superTrendyStyleColumnIndex = column;
                break;
            }
        }

        //Add o style
        if(superTrendyStyleColumnIndex == -1) {
            numberOfModelsUpdated++;
            superTrendyStyleColumnIndex = matrixSize - 1;

            if(modelsMatrix[0][matrixSize - 1].equals(".")) {
                totalPoints += 2;
            } else {
                totalPoints += 1;
            }

            modelsMatrix[0][matrixSize - 1] = "o";
            modelsUpdated.add("o " + 1 + " " + matrixSize); //1-based index
        }

        for(int column=0; column < modelsMatrix[0].length; column++) {
            if (modelsMatrix[0][column].equals(".")){
                modelsMatrix[0][column] = "+";

                totalPoints++;
                numberOfModelsUpdated++;
                modelsUpdated.add("+ " + 1 + " " + (column + 1));
            }
        }

        //Add models on the diagonal of the model o
        boolean goToTheLeftDiagonal = superTrendyStyleColumnIndex >= matrixSize / 2;
        for(int i=1; i < matrixSize; i++) {
            int columnIndex = goToTheLeftDiagonal ? superTrendyStyleColumnIndex - i : superTrendyStyleColumnIndex + i;
            if(columnIndex < 0 || columnIndex >= matrixSize) {
                break;
            }

            String modelUp = modelsMatrix[0][columnIndex];

            if(modelUp.equals("+")) {
                numberOfModelsUpdated++;
                totalPoints++;
                modelsUpdated.add("x " + (i+1) + " " + (columnIndex + 1));
                modelsMatrix[i][columnIndex] = "x";
            }
        }

        //Add models + on the center of the final rows
        int middleColumn = matrixSize / 2;
        int secondHalfRows = matrixSize / 2 + 1;

        for(int row=secondHalfRows; row < matrixSize; row++) {
            modelsMatrix[row][middleColumn] = "+";
            numberOfModelsUpdated++;
            totalPoints++;
            modelsUpdated.add("+ " + (row + 1) + " " + (middleColumn + 1));
        }

        String[] output = new String[modelsUpdated.size() + 1];
        output[0] = totalPoints + " " + numberOfModelsUpdated;
        for(int i=1; i <= numberOfModelsUpdated; i++) {
            output[i] = modelsUpdated.get(i - 1);
        }
        return output;
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> numbersList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i=1; i < lines.size(); i++) {
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
