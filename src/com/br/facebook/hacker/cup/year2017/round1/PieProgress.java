package com.br.facebook.hacker.cup.year2017.round1;

/**
 * Created by rene on 14/01/17.
 */

//Based on https://www.facebook.com/hackercup/problem/1800890323482794/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Some pies are sweet, full of fruit and jam and sugar.

 Some pies are savory, full of meat and potatoes and spices.

 Some pies are in fact not pies at all but tarts or galettes. This probably won't stop you from eating them.

 Every single day for N days, you're determined to eat a pie for dinner.
 Every morning, you'll take a trip to your local pie shop, and buy 0 or more of their pies.
 Every night, you'll eat one pie that you've bought.
 Pies never go bad, so you don't need to eat a pie on the same day that you bought it.
 You may instead eat one that you purchased on an earlier day.

 On the ith day, the shop has M pies for sale, with the jth of these pies costing Ci,j dollars.
 You can choose to buy any (possibly empty) subset of them.
 However, this shop has measures in place to protect itself against crazy pie fanatics buying out its products too quickly.
 In particular, if you buy p pies on a single day, you must pay an additional tax of p2 dollars.

 Input
 Input begins with an integer T, the number of times you go on a pie-eating spree.
 For each case, there is first a line containing two space-separated integers, N and M.
 Then, N lines follow, each containing M space-separated integers.
 The jth integer on the ith line is Ci,j.

 Output
 For the ith case, print a line containing "Case #i: " followed by the minimum you need to pay to eat a pie every day.

 Constraints
 1 ≤ T ≤ 100
 1 ≤ N, M ≤ 300
 1 ≤ Ci,j ≤ 1,000,000

 Example Input
 5
 3 2
 1 1
 100 100
 10000 10000
 5 1
 1
 2
 3
 4
 5
 5 5
 1 2 3 4 5
 2 3 4 5 1
 3 4 5 1 2
 4 5 1 2 3
 5 1 2 3 4
 5 5
 1 1 1 1 1
 2 2 2 2 2
 3 3 3 3 3
 4 4 4 4 4
 5 5 5 5 5
 10 4
 7 15 12 6
 15 3 19 18
 10 9 10 14
 12 14 8 8
 5 3 5 11
 9 14 19 11
 12 6 20 9
 18 13 12 15
 14 14 10 20
 11 19 12 11

 Example output
 Case #1: 107
 Case #2: 20
 Case #3: 10
 Case #4: 18
 Case #5: 79

 Explanation of Sample
 In the first case, you should buy both pies on the first day, for a total cost of 1 + 1 + 22 = 6.
 On the second day you should buy one pie for 100 + 12 = 101.
 On the third day you can eat one of the spare pies you bought on the first day.

 In the third case, you should buy and eat the cheapest pie every day, for a daily cost of 1 + 12 = 2, and a total cost of 10.

 In the fourth case, one possible solution is to buy two pies on the first day (1 + 1 + 22 = 6),
 two pies on the second day (2 + 2 + 22 = 8), and one pie on the third day (3 + 12 = 4) for a total cost of 18.
 On the fourth and fifth days you can eat your two spare pies from the first and second days.
 */

public class PieProgress {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2017/Round 1/Input - Output/";

    private static final String FILE_INPUT_PATH = PATH + "pie_progress_example_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "pie_progress_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "pie_progress_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "pie_progress_output_submission.txt";

    public static void main(String[] args) {
        int[][] piesCost1 = {{1, 1},
                {100, 100},
                {10000, 10000}};

        int[][] piesCost2 = {{1},
                {2},
                {3},
                {4},
                {5}};

        int[][] piesCost3 = {{1, 2, 3, 4, 5},
                {2, 3, 4, 5, 1},
                {3, 4, 5, 1, 2},
                {4, 5, 1, 2, 3},
                {5, 1, 2, 3, 4}};

        int[][] piesCost4 = {{1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3},
                {4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5}};

        int[][] piesCost5 = {{7, 15, 12, 6},
                {15, 3, 19, 18},
                {10, 9, 10, 14},
                {12, 14, 8, 8},
                {5, 3, 5, 11},
                {9, 14, 19, 11},
                {12, 6, 20, 9},
                {18, 13, 12, 15},
                {14, 14, 10, 20},
                {11, 19, 12, 11}};

        System.out.println(minimumPay(piesCost1) + " Expected: 107");
        System.out.println(minimumPay(piesCost2) + " Expected: 20");
        System.out.println(minimumPay(piesCost3) + " Expected: 10");
        System.out.println(minimumPay(piesCost4) + " Expected: 18");
        System.out.println(minimumPay(piesCost5) + " Expected: 79");

        List<String> output = readFileInputAndReturnOutput(FILE_INPUT_PATH);
        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static long minimumPay(int[][] pieCosts) {

        int numberOfDays = pieCosts.length;
        int numberOfPies = pieCosts[0].length;

        int[] addedTax = new int[numberOfPies];

        //Tax follows the sequence: 1, 4, 9, 16, 25, 36 -> +3, +5, +7, +9, +11
        int addedTaxPerPie = 1;

        //Computes added tax per pie bought
        for(int i = 0; i < addedTax.length; i++) {
            addedTax[i] = addedTaxPerPie;
            addedTaxPerPie += 2;
        }

        int taxIndex = 0;
        //Sort costs to get maximum pie bought later
        for(int i = 0; i < pieCosts.length; i++) {
            Arrays.sort(pieCosts[i]);

            for(int j = 0; j < numberOfPies; j++) {
                pieCosts[i][j] += addedTax[taxIndex];
                taxIndex++;
            }

            taxIndex = 0;
        }

        long totalCost = 0;

        Queue<Integer> piesPriceHeap = new PriorityQueue<>();

        for(int i = 0; i < numberOfDays; i++) {
            //Insert all pies of the day in the heap
            for(int j = 0; j < numberOfPies; j++) {
                piesPriceHeap.offer(pieCosts[i][j]);
            }

            //Pick the one with lowest cost
            totalCost += piesPriceHeap.poll();
        }

        return totalCost;
    }

    private static List<String> readFileInputAndReturnOutput(String filePath) {
        List<String> output = new ArrayList<>();

        Path path = Paths.get(filePath);

        try {
            List<String> lines = Files.readAllLines(path);

            int timesToCompute = Integer.parseInt(lines.get(0));
            int lineIndex = 1;

            for (int t = 1; t <= timesToCompute; t++) {
                String line = lines.get(lineIndex);

                String[] valuesInLine = line.split(" ");
                int days = Integer.parseInt(valuesInLine[0]);
                int prices = Integer.parseInt(valuesInLine[1]);

                int[][] pieCosts = new int[days][prices];

                for(int j = 0; j < days; j++) {
                    lineIndex++;
                    String[] pricesOfTheDay = lines.get(lineIndex).split(" ");

                    for(int k = 0; k < prices; k++) {
                        pieCosts[j][k] = Integer.parseInt(pricesOfTheDay[k]);
                    }
                }

                long minimumPay = minimumPay(pieCosts);
                output.add("Case #" + t + ": " + minimumPay);

                lineIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
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
