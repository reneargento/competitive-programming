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

 You've discovered it: the ultimate recipe for ratatouille, the famous French dish!
 You know which ingredients to use, and how many grams of each one to use, in order to make one serving of ratatouille.
 But you believe that anyone can cook, and so you want to share the recipe with the world... and make some money in the process!

 You have ordered some ingredient packages that are easy to ship.
 Each package contains some amount of one ingredient; different packages may have different amounts even
 if they contain the same ingredient.
 For convenience, you ordered the same number of packages of each ingredient.

 You would like to use these packages to form as many ratatouille kits as possible to send to customers.
 A kit consists of exactly one package of each ingredient, and a label with the integer number of servings of ratatouille
 that the kit makes. Since you do not want to shortchange customers or waste food, each package must contain
 between 90 and 110 percent (inclusive) of the amount of that ingredient that is actually needed to make the
 number of servings of ratatouille on the kit's label.

 For example, suppose that one serving of ratatouille takes 500 g of tomato and 300 g of onion.
 Suppose that you have a 900 g package of tomato and a 660 g package of onion.
 You could form these into a kit that makes two servings of ratatouille.
 To make two servings, 1000 g of tomato and 600 g of onion are required.
 Since the 900 g of tomato you have is within [90, 110]% of the 1000 g of tomato required, and the 660 g of onion
 you have is within [90, 110]% of the 600 g of onion required, this is acceptable.
 However, you could not say that the kit makes one or three servings of ratatouille,
 nor could you say that it makes 1.999 servings (the number of servings must be an integer).

 Note that there are some sets of packages that could never form a kit.
 Continuing with our recipe above, if you have a 1500 g package of tomato and an 809 g package of onion,
 for example, there is no amount of servings that you can make.
 Three servings would take 1500 g of tomato and 900 g of onion, and the amount of onion is not within
 the [90, 110]% range. No other integer amount of servings works, either.

 You want to share your recipe with as many customers as possible, so you want to produce the maximum number of valid kits.
 (Of course, each package can be used in at most one kit.)
 What is the largest number of kits that you can form?
 Note that you are not required to maximize the total number of servings of ratatouille formed.

 Input

 The first line of the input gives the number of test cases, T.
 T test cases follow.
 Each case consists of the following:

 One line with two integers N: the number of ingredients, and P, the number of packages of each ingredient.
 One line with N integers Ri. The i-th of these represents the number of grams of the i-th ingredient needed to
 make one serving of ratatouille.
 N more lines of P integers each. The j-th value on the i-th of these lines, Qij, represents the quantity, in grams,
 in the j-th package of the i-th ingredient.

 Output

 For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y
 is the maximum number of kits you can produce, as described above.

 Limits

 1 ≤ T ≤ 100.
 1 ≤ Ri ≤ 106, for all i.
 1 ≤ Qij ≤ 106, for all i and j.

 Small dataset

 1 ≤ N ≤ 2.
 1 ≤ P ≤ 8.

 Large dataset

 1 ≤ N ≤ 50.
 1 ≤ P ≤ 50.
 N × P ≤ 1000.

 Sample

 Input

 6
 2 1
 500 300
 900
 660
 2 1
 500 300
 1500
 809
 2 2
 50 100
 450 449
 1100 1101
 2 1
 500 300
 300
 500
 1 8
 10
 11 13 17 11 16 14 12 18
 3 3
 70 80 90
 1260 1500 700
 800 1440 1600
 1700 1620 900

 Output

 Case #1: 1
 Case #2: 0
 Case #3: 1
 Case #4: 0
 Case #5: 3
 Case #6: 3

 Note that the last sample case would not appear in the Small dataset.

 Sample cases #1 and #2 are the ones described in the problem statement.
 In sample case #3, you can form a kit out of the 450 g package of the first ingredient and the 1100 g package of the
 second ingredient, and say that the kit makes 10 servings of ratatouille.
 That number of servings requires 500 g of the first ingredient; you have 450 g, which is 90% of 500 and within the allowed limit.
 It requires 1000 g of the second ingredient; you have 1100 g, which is 110% of 1000 and within the allowed limit.

 Once you form this kit, however, you cannot form the remaining packages into a kit. 449 g of the first ingredient and 1101 g
 of the second ingredient would not be able to form 10 (or any other number of) servings.
 In fact, the (450 g, 1100 g) kit is the only kit that can be formed from these packages.

 In sample case #4, no kits can be formed. Note that the recipe requires particular amounts of particular ingredients
 in the given order; the ingredients are not interchangeable.
 This is fine French cuisine, after all!

 In sample case #5, the recipe has only one ingredient — how elegantly simple!
 A single serving cannot use more than 11 g, and two servings cannot use fewer than 18 g.
 It is possible to form three kits: two with an 11 g package, and one with an 18 g package.

 In sample case #6, you can form three valid kits: (700 g, 800 g, 900 g), which makes 10 servings, and (1500 g, 1600 g, 1700 g)
 and (1260 g, 1440 g, 1620 g), each of which makes 20 servings.
 Note that you could also say that the (1260 g, 1440 g, 1620 g) kit makes 17, 18, or 19 servings,
 but it does not matter how many servings a kit makes as long as the kit is valid.

 */
public class Ratatouille {

    private static class Range implements Comparable<Range>{
        int lowerBound;
        int higherBound;

        Range(int lowerBound, int higherBound) {
            this.lowerBound = lowerBound;
            this.higherBound = higherBound;
        }

        @Override
        public int compareTo(Range that) {
            if (this.lowerBound < that.lowerBound) {
                return -1;
            } else if (this.lowerBound > that.lowerBound) {
                return 1;
            } else {
                return this.higherBound - that.higherBound;
            }
        }
    }

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/2017/Round 1A/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "ratatouille_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "ratatouille_sample_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "ratatouille_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "ratatouille_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "ratatouille_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "ratatouille_large_output.txt";

    public static void main(String[] args) {

        test();
        //compete();
    }

    private static void compete() {
        List<String> recipeInformation = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int i = 0; i < recipeInformation.size(); i++) {

            String[] values = recipeInformation.get(i).split(" ");
            int numberOfIngredients = Integer.parseInt(values[0]);
            int numberOfPackages = Integer.parseInt(values[1]);

            i++;
            String[] ingredientsValues = recipeInformation.get(i).split(" ");
            int[] ingredients = new int[numberOfIngredients];
            for(int j = 0; j < ingredients.length; j++) {
                ingredients[j] = Integer.parseInt(ingredientsValues[j]);
            }

            List<Integer[]> packages = new ArrayList<>();
            for(int j = 0; j < numberOfIngredients; j++) {
                i++;
                String[] packageValues = recipeInformation.get(i).split(" ");
                Integer[] currentPackage = new Integer[numberOfPackages];

                for(int k = 0; k < numberOfPackages; k++) {
                    currentPackage[k] = Integer.parseInt(packageValues[k]);
                }

                packages.add(currentPackage);
            }

            int numberOfKits = getNumberOfKits(ingredients, packages);

            output.add("Case #" + caseIndex + ": " + numberOfKits);
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        int[] ingredients1 = {500, 300};
        Integer[] gramsNeeded10 = {900};
        Integer[] gramsNeeded11 = {600};

        List<Integer[]> grams1 = new ArrayList<>();
        grams1.add(gramsNeeded10);
        grams1.add(gramsNeeded11);
        int numberOfKits1 = getNumberOfKits(ingredients1, grams1);

        int[] ingredients2 = {500, 300};
        Integer[] gramsNeeded20 = {1500};
        Integer[] gramsNeeded21 = {809};

        List<Integer[]> grams2 = new ArrayList<>();
        grams2.add(gramsNeeded20);
        grams2.add(gramsNeeded21);
        int numberOfKits2 = getNumberOfKits(ingredients2, grams2);

        int[] ingredients3 = {50, 100};
        Integer[] gramsNeeded30 = {450, 449};
        Integer[] gramsNeeded31 = {1100, 1101};

        List<Integer[]> grams3 = new ArrayList<>();
        grams3.add(gramsNeeded30);
        grams3.add(gramsNeeded31);
        int numberOfKits3 = getNumberOfKits(ingredients3, grams3);

        int[] ingredients4 = {500, 300};
        Integer[] gramsNeeded40 = {300};
        Integer[] gramsNeeded41 = {500};

        List<Integer[]> grams4 = new ArrayList<>();
        grams4.add(gramsNeeded40);
        grams4.add(gramsNeeded41);
        int numberOfKits4 = getNumberOfKits(ingredients4, grams4);

        int[] ingredients5 = {10};
        Integer[] gramsNeeded50 = {11, 13, 17, 11, 16, 14, 12, 18};

        List<Integer[]> grams5 = new ArrayList<>();
        grams5.add(gramsNeeded50);
        int numberOfKits5 = getNumberOfKits(ingredients5, grams5);

        int[] ingredients6 = {70, 80, 90};
        Integer[] gramsNeeded60 = {1260, 1500, 700};
        Integer[] gramsNeeded61 = {800, 1440, 1600};
        Integer[] gramsNeeded62 = {1700, 1620, 900};

        List<Integer[]> grams6 = new ArrayList<>();
        grams6.add(gramsNeeded60);
        grams6.add(gramsNeeded61);
        grams6.add(gramsNeeded62);
        int numberOfKits6 = getNumberOfKits(ingredients6, grams6);

        System.out.println(numberOfKits1 + " Expected: 1");
        System.out.println(numberOfKits2 + " Expected: 0");
        System.out.println(numberOfKits3 + " Expected: 1");
        System.out.println(numberOfKits4 + " Expected: 0");
        System.out.println(numberOfKits5 + " Expected: 3");
        System.out.println(numberOfKits6 + " Expected: 3");
    }

    private static int getNumberOfKits(int[] ingredients, List<Integer[]> grams) {

        if (ingredients.length == 0) {
            return 0;
        }

        Map<Integer, List<Range>> servingsCount = new HashMap<>();

        for(int i = 0; i < grams.size(); i++) {

            Integer[] grams1 = grams.get(i);
            for(int j = 0; j < grams1.length; j++) {

                // Lower bound: 0.9 * R * M < Q
                // Upper bound: 1.1 * R * M > Q
                //Simplifying
                // Lower bound:  0.9 * (Q/R) < M
                // Upper bound: 1.1 * (Q/R) > M
                double lowerBound = (10.0/11.0) * grams1[j] / ingredients[i];
                double upperBound = (10.0/9.0) * grams1[j] / ingredients[i];

                int lowerBoundInt = (int) Math.ceil(lowerBound);
                int upperBoundInt = (int) Math.floor(upperBound);

                Range newRange = new Range(lowerBoundInt, upperBoundInt);
                List<Range> servingsList;

                if ( servingsCount.containsKey(i)) {
                    servingsList = servingsCount.get(i);
                } else {
                    servingsList = new ArrayList<>();
                }
                servingsList.add(newRange);

                servingsCount.put(i, servingsList);
            }
        }

        int kits = 0;

        //Sort all ranges
        for(int key : servingsCount.keySet()) {
            List<Range> servingsCountList = servingsCount.get(key);
            Collections.sort(servingsCountList);
        }

        if (servingsCount.size() == 1) {
            List<Range> servingsCountList = servingsCount.get(0);
            for(Range range : servingsCountList) {
                if (range.lowerBound <= range.higherBound) {
                    kits++;
                }
            }
        } else {
            for(int pack = 0; pack < grams.get(0).length; pack++) {
                for(int key : servingsCount.keySet()) {
                    List<Range> servingsCountList = servingsCount.get(key);

                    if (servingsCountList.isEmpty()) {
                        break;
                    }
                    Range servingsCount1 = servingsCountList.get(0);

                    boolean canMakeKit = false;

                    for(int key2 : servingsCount.keySet()) {
                        if (key != key2) {
                            canMakeKit = false;

                            List<Range> servingsCountList2 = servingsCount.get(key2);

                            for(Range servingsCount2 : servingsCountList2) {
                                if (isInRange(servingsCount1, servingsCount2)) {
                                    canMakeKit = true;
                                    break;
                                }
                            }

                            if (!canMakeKit) {
                                break;
                            }
                        }
                    }

                    if (canMakeKit) {
                        kits++;

                        for(int key2 : servingsCount.keySet()) {
                            List<Range> servingsCountList2 = servingsCount.get(key2);
                            if (servingsCountList2 != null && servingsCountList2.size() > 0) {
                                servingsCountList2.remove(0);
                            }
                        }
                    }
                }
            }
        }

        return kits;
    }

    private static boolean isInRange(Range range1, Range range2) {
        if (range1.higherBound < range2.lowerBound) {
            return false;
        }
        if (range1.lowerBound > range2.higherBound) {
            return false;
        }
        if (range2.higherBound < range1.lowerBound) {
            return false;
        }
        if (range2.lowerBound > range1.higherBound) {
            return false;
        }

        return true;
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
