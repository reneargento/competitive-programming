package com.br.google.code.jam.code2017.round1a;

/**
 * Created by rene on 14/04/17.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Problem

 You are catering a party for some children, and you are serving them a cake in the shape of a grid with R rows and C columns.
 Your assistant has started to decorate the cake by writing every child's initial in icing on exactly one cell of the cake.
 Each cell contains at most one initial, and since no two children share the same initial, no initial appears more than once on the cake.

 Each child wants a single rectangular (grid-aligned) piece of cake that has their initial and no other child's initial(s).
 Can you find a way to assign every blank cell of the cake to one child, such that this goal is accomplished?
 It is guaranteed that this is always possible.
 There is no need to split the cake evenly among the children, and one or more of them may even get a 1-by-1 piece;
 this will be a valuable life lesson about unfairness.

 Input
 The first line of the input gives the number of test cases, T.
 T test cases follow.
 Each begins with one line with two integers R and C.
 Then, there are R more lines of C characters each, representing the cake.
 Each character is either an uppercase English letter (which means that your assistant has already added that letter to that cell)
 or ? (which means that that cell is blank).

 Output
 For each test case, output one line containing Case #x: and nothing else.
 Then output R more lines of C characters each.
 Your output grid must be identical to the input grid, but with every ? replaced with
 an uppercase English letter, representing that that cell appears in the slice for the child who has that initial.
 You may not add letters that did not originally appear in the input.
 In your grid, for each letter, the region formed by all the cells containing that letter must be a single grid-aligned rectangle.

 If there are multiple possible answers, you may output any of them.

 Limits
 1 ≤ T ≤ 100.
 There is at least one letter in the input grid.
 No letter appears in more than one cell in the input grid.
 It is guaranteed that at least one answer exists for each test case.

 Small dataset
 1 ≤ R ≤ 12.
 1 ≤ C ≤ 12.
 R × C ≤ 12.

 Large dataset
 1 ≤ R ≤ 25.
 1 ≤ C ≤ 25.

 Sample

 Input

 3
 3 3
 G??
 ?C?
 ??J
 3 4
 CODE
 ????
 ?JAM
 2 2
 CA
 KE

 Output

 Case #1:
 GGJ
 CCJ
 CCJ
 Case #2:
 CODE
 COAE
 JJAM
 Case #3:
 CA
 KE

 The sample output displays one set of answers to the sample cases.
 Other answers may be possible.
 */
public class AlphabetCake {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/2017/Round 1A/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "alphabet_cake_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "alphabet_cake_sample_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "alphabet_cake_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "alphabet_cake_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "alphabet_cake_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "alphabet_cake_large_output.txt";

    public static void main(String[] args) {

        test();
         //compete();
    }

    private static void compete() {
        List<String> cakeInformation = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int i = 0; i < cakeInformation.size(); i++) {

            String[] values = cakeInformation.get(i).split(" ");
            int rows = Integer.parseInt(values[0]);
            int columns = Integer.parseInt(values[1]);

            String[][] cake = new String[rows][columns];

            for(int k = 0; k < rows; k++) {
                i++;
                for(int j = 0; j < columns; j++) {
                    String nextRow = cakeInformation.get(i);
                    cake[k][j] = String.valueOf(nextRow.charAt(j));
                }
            }

            buildCake(cake);
            output.add("Case #" + caseIndex + ":");// + spaces[0] + " " + spaces[1]);
            String rowValue = "";

            for(int row = 0; row < cake.length; row++) {
                for(int column = 0; column < cake[0].length; column++) {
                    rowValue += cake[row][column];
                }
                if (row != cake.length - 1) {
                    rowValue += "\n";
                }
            }

            output.add(rowValue);
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        String[][] cake1 = {{"G", "?", "?"},
                {"?", "C", "?"},
                {"?", "?", "J"}};

        String[][] cake2 = {{"C", "O", "D", "E"},
                {"?", "?", "?", "?"},
                {"?", "J", "A", "M"}};

        String[][] cake3 = {{"C", "A"},
                {"K", "E"}};

        buildCake(cake1);
        buildCake(cake2);
        buildCake(cake3);

        for(int i = 0; i < cake1.length; i++) {
            for(int j = 0; j < cake1[0].length; j++) {
                System.out.print(cake1[i][j]);
            }
            System.out.println();
        }

        System.out.println();
        for(int i = 0; i < cake2.length; i++) {
            for(int j = 0; j < cake2[0].length; j++) {
                System.out.print(cake2[i][j]);
            }
            System.out.println();
        }

        System.out.println();
        for(int i = 0; i < cake3.length; i++) {
            for(int j = 0; j < cake3[0].length; j++) {
                System.out.print(cake3[i][j]);
            }
            System.out.println();
        }
    }

    private static void buildCake(String[][] cake) {
        String currentChar;

        for(int i = 0; i < cake.length; i++) {
            currentChar = "?";

            //Get current char
            int j = 0;
            while(j < cake[0].length) {
                if (!cake[i][j].equals("?")) {
                    currentChar = cake[i][j];
                    break;
                }
                j++;
            }

            for(int k = 0; k < j; k++) {
                cake[i][k] = currentChar;
            }

            for(int k = j + 1; k < cake[0].length; k++) {
                if (cake[i][k].equals("?")) {
                    cake[i][k] = currentChar;
                } else {
                    currentChar = cake[i][k];
                }
            }
        }

        for(int j = 0; j < cake[0].length; j++) {
            currentChar = "?";

            //Get current char
            int i = 0;
            while (i < cake.length) {
                if (!cake[i][j].equals("?")) {
                    currentChar = cake[i][j];
                    break;
                }
                i++;
            }

            for (int k = 0; k < i; k++) {
                cake[k][j] = currentChar;
            }

            for (int k = i + 1; k < cake.length; k++) {
                if (cake[k][j].equals("?")) {
                    cake[k][j] = currentChar;
                } else {
                    currentChar = cake[k][j];
                }
            }
        }

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
