package com.br.google.code.jam.code2017.round1c;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by rene on 30/04/17.
 */
public class AmpleSyrup {

    private static class Pancake implements Comparable<Pancake>{

        private double radius;
        private double topArea;
        private double sideArea;

        private Pancake(double radius, double height) {
            this.radius = radius;
            topArea = Math.PI * Math.pow(radius, 2);
            sideArea = 2 * Math.PI * radius * height;
        }

        @Override
        public int compareTo(Pancake that) {
            if (this.topArea > that.topArea) {
                return -1;
            } else if (this.topArea < that.topArea) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/2017/Round 1C/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "ample_syrup_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "ample_syrup_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "ample_syrup_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "ample_syrup_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "ample_syrup_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "ample_syrup_large_output.txt";

    public static void main(String[] args) {

        //test();
        compete();
    }

    private static void test() {
        Pancake pancake10 = new Pancake(100, 20);
        Pancake pancake11 = new Pancake(200, 10);
        Pancake[] pancakes1 = {pancake10, pancake11};
        int stackSize1 = 1;
        System.out.println(String.format("%.9f", computeArea(pancakes1, stackSize1)) + " Expected: 138230.076757951");

        Pancake pancake20 = new Pancake(100, 20);
        Pancake pancake21 = new Pancake(200, 10);
        Pancake[] pancakes2 = {pancake20, pancake21};
        int stackSize2 = 2;
        System.out.println(String.format("%.9f", computeArea(pancakes2, stackSize2)) + " Expected: 150796.447372310");

        Pancake pancake30 = new Pancake(100, 10);
        Pancake pancake31 = new Pancake(100, 10);
        Pancake pancake32 = new Pancake(100, 10);
        Pancake[] pancakes3 = {pancake30, pancake31, pancake32};
        int stackSize3 = 2;
        System.out.println(String.format("%.9f", computeArea(pancakes3, stackSize3)) + " Expected: 43982.297150257");

        Pancake pancake40 = new Pancake(9, 3);
        Pancake pancake41 = new Pancake(7, 1);
        Pancake pancake42 = new Pancake(10, 1);
        Pancake pancake43 = new Pancake(8, 4);
        Pancake[] pancakes4 = {pancake40, pancake41, pancake42, pancake43};
        int stackSize4 = 2;
        System.out.println(String.format("%.9f", computeArea(pancakes4, stackSize4)) + " Expected: 625.176938064");

        Pancake pancake50 = new Pancake(44574, 101556);
        Pancake pancake51 = new Pancake(27094, 167076);
        Pancake[] pancakes5 = {pancake50, pancake51};
        int stackSize5 = 2;
        System.out.println(String.format("%.9f", computeArea(pancakes5, stackSize5)) + " Expected: > 0");
    }

    private static void compete() {
        List<String> pancakesInformation = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int i = 0; i < pancakesInformation.size(); i++) {

            String[] values = pancakesInformation.get(i).split(" ");
            int numberOfPancakes = Integer.parseInt(values[0]);
            int stackSize = Integer.parseInt(values[1]);
            Pancake[] pancakes = new Pancake[numberOfPancakes];

            for(int j = 0; j < pancakes.length; j++) {
                i++;
                String[] pancakeInfo = pancakesInformation.get(i).split(" ");
                double radius = Double.parseDouble(pancakeInfo[0]);
                double height = Double.parseDouble(pancakeInfo[1]);

                pancakes[j] = new Pancake(radius, height);
            }

            String areaExposed = String.format("%.9f", computeArea(pancakes, stackSize));

            output.add("Case #" + caseIndex + ": " + areaExposed);
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static double computeArea (Pancake[] pancakes, int stackSize) {

        Pancake[] pancakesSortedBySideArea = new Pancake[pancakes.length];
        System.arraycopy(pancakes, 0, pancakesSortedBySideArea, 0, pancakes.length);

        Arrays.sort(pancakesSortedBySideArea, new Comparator<Pancake>() {
            @Override
            public int compare(Pancake pancake1, Pancake pancake2) {
                if (pancake1.sideArea > pancake2.sideArea) {
                    return -1;
                } else if (pancake1.sideArea < pancake2.sideArea) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        Arrays.sort(pancakes);

        Pancake[] selectedPancakes = new Pancake[stackSize];
        double highestExposedAreaFound = 0;

        for(int i = 0; i < pancakes.length; i++) {
            int selectedPancakesIndex = selectedPancakes.length - 1;

            selectedPancakes[selectedPancakesIndex--] = pancakes[i];
            int j = 0;

            while (selectedPancakesIndex >= 0 && j < pancakesSortedBySideArea.length) {
                if (pancakesSortedBySideArea[j] == pancakes[i]
                        || pancakesSortedBySideArea[j].radius > selectedPancakes[selectedPancakes.length - 1].radius) {
                    j++;
                    continue;
                }

                selectedPancakes[selectedPancakesIndex--] = pancakesSortedBySideArea[j++];
            }

            if (selectedPancakesIndex >= 0) {
                continue;
            }

            double currentExposedArea = computeTotalExposedArea(selectedPancakes, stackSize);
            if (currentExposedArea > highestExposedAreaFound) {
                highestExposedAreaFound = currentExposedArea;
            }
        }

        return highestExposedAreaFound;
    }

    private static double computeTotalExposedArea(Pancake[] selectedPancakes, int stackSize) {
        double exposedArea = 0;

        for(int i = stackSize - 1; i >= 0; i--) {
            if (i == stackSize - 1) {
                exposedArea += selectedPancakes[i].topArea + selectedPancakes[i].sideArea;
            } else {
                exposedArea += selectedPancakes[i].sideArea;
            }
        }

        return exposedArea;
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
