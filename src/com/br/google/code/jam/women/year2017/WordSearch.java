package com.br.google.code.jam.women.year2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 11/03/17.
 */
public class WordSearch {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Women/2017/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "word_search_input_sample.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "word_search_output_sample.txt";
    private static final String FILE_INPUT_PATH = PATH + "word_search_small_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "word_search_small_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "word_search_large_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "word_search_large_output.txt";

    public static void main(String[] args) {

        List<int[]> input = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int[] values : input) {
            String[][] grid = computeGrid(values);
            output.add("Case #" + caseIndex + ":");

            String concat = "";
            for(int i = 0; i < grid.length; i++) {
                for(int j =0; j < grid[0].length; j++) {
                    concat += grid[i][j];
                }

                if (i != grid.length - 1) {
                    concat += "\n";
                }
            }
            output.add(concat);

            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static List<int[]> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<int[]> input = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i = 1; i < lines.size(); i++) {
                String[] values = lines.get(i).split(" ");
                int maxSize = Integer.parseInt(values[0]);
                int occurrences = Integer.parseInt(values[1]);

                int[] valuesArray = new int[2];
                valuesArray[0] = maxSize;
                valuesArray[1] = occurrences;

                input.add(valuesArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    private static String[][] computeGrid(int[] input) {

        String[][] grid = new String[50][50];

        for(int i = 0; i < 50; i++) {
            for(int j = 0; j < 50; j++) {
                grid[i][j] = "O";
            }
        }

        int maxSize = input[0];
        int occurrences = input[1];

        if (occurrences == 0) {
            return grid;
        }

        int wordsWritten = 0;

        for(int i = 0; i < 50; i += 2) {
            grid[i][0] = "I";
            grid[i][1] = "/";
            grid[i][2] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][3] = "I";
            grid[i][4] = "/";
            grid[i][5] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][6] = "I";
            grid[i][7] = "/";
            grid[i][8] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][9] = "I";
            grid[i][10] = "/";
            grid[i][11] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][12] = "I";
            grid[i][13] = "/";
            grid[i][14] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][15] = "I";
            grid[i][16] = "/";
            grid[i][17] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][18] = "I";
            grid[i][19] = "/";
            grid[i][20] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][21] = "I";
            grid[i][22] = "/";
            grid[i][23] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][24] = "I";
            grid[i][25] = "/";
            grid[i][26] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][27] = "I";
            grid[i][28] = "/";
            grid[i][29] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][30] = "I";
            grid[i][31] = "/";
            grid[i][32] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][33] = "I";
            grid[i][34] = "/";
            grid[i][35] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][36] = "I";
            grid[i][37] = "/";
            grid[i][38] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][39] = "I";
            grid[i][40] = "/";
            grid[i][41] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][42] = "I";
            grid[i][43] = "/";
            grid[i][44] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }

            grid[i][45] = "I";
            grid[i][46] = "/";
            grid[i][47] = "O";

            wordsWritten++;
            if (wordsWritten == occurrences) {
                break;
            }
        }

        return grid;
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
