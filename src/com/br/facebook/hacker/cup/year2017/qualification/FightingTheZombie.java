package com.br.facebook.hacker.cup.year2017.qualification;

/**
 * Created by rene on 07/01/17.
 */
//Based on https://www.facebook.com/hackercup/problem/326053454264498/
//Based on http://www.geeksforgeeks.org/dice-throw-problem/

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Fighting the Zombie
 *
 * "Okay, Wizard, cast your spell!"

 But which of your many spells to cast?
 In the ever-popular role-playing game Dungeons & Dragons, or D&D,
 you determine a spell's damage by rolling polyhedral dice with 4, 6, 8, 10, 12, or 20 sides.
 Since there's a lot of dice-rolling involved, players use shorthand to denote which dice should be rolled.
 XdY means "roll a Y-sided die X times, and sum the rolls''.
 Sometimes, you must add or subtract a value Z after you finish rolling,
 in which case the notation is XdY+Z or XdY-Z respectively.

 For example, if you roll 2d4+1, you'll end up with a result between 3 and 9 inclusive.
 If you roll 1d6-3, your result will be between -2 and 3 inclusive.

 In D&D, wizards are powerful but flimsy spellcasters.
 As a wizard fighting a zombie, your best strategy is to maximize the chance that you can kill the
 zombie with a single spell before it has a chance to retaliate.
 What spell should you cast?

 Input
 Input begins with an integer T, the number of zombies you'll fight.
 For each zombie, there are two lines.
 The first contains two integers, H and S, the minimum amount of damage it takes to defeat the zombie,
 and the number of spells you have prepared, respectively.
 The second line contains S spell descriptions separated by single spaces.
 A spell description is simply the amount of damage a spell does in the notation described above.

 Output
 For each zombie, print a line containing the probability of defeating the zombie if you select your spell optimally.

 Absolute and relative errors of up to 1e-6 will be ignored.

 Constraints
 1 ≤ T ≤ 1,000
 1 ≤ H ≤ 10,000
 2 ≤ S ≤ 10

 Additionally, the following constraints will hold for each spell:

 1 ≤ X ≤ 20
 Y ∈ {4, 6, 8, 10, 12, 20}
 1 ≤ Z ≤ 10,000, if Z is specified.
 X, Y, and Z will be integers with no leading zeros.

 Example input
 5
 2 2
 2d4 1d8
 10 2
 10d6-10 1d6+1
 8 3
 1d4+4 2d4 3d4-4
 40 3
 10d4 5d8 2d20
 10 4
 1d10 1d10+1 1d10+2 1d10+3

 Example output
 Case #1: 1.000000
 Case #2: 0.998520
 Case #3: 0.250000
 Case #4: 0.002500
 Case #5: 0.400000

 Explanation of Sample
 In the first case, you can guarantee a kill with the first spell, which must always do at least 2 damage.

 In the third case, your first spell is the best.
 If you roll a 4, you'll do the requisite 8 damage.
 The second spell requires rolling a 4 on two dice rather than just one,
 and the third spell requires rolling a 4 on all three dice.
 */
public class FightingTheZombie {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2017/Qualification/Input - Output/";

    private static final String FILE_INPUT_PATH = PATH + "fighting_the_zombie_example_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "fighting_the_zombie_output.txt";

    //private static final String FILE_INPUT_PATH = PATH + "fighting_the_zombie_input.txt";
    //private static final String FILE_OUTPUT_PATH = PATH + "fighting_the_zombie_output_submission.txt";

    public static void main(String[] args) {
        String[] spells1 = {"2d4", "1d8"};
        System.out.println(chanceOfKillingTheZombie(2, spells1) + " Expected: 1.000000");

        String[] spells2 = {"10d6-10", "1d6+1"};
        System.out.println(chanceOfKillingTheZombie(10, spells2) + " Expected: 0.998520");

        String[] spells3 = {"1d4+4", "2d4", "3d4-4"};
        System.out.println(chanceOfKillingTheZombie(8, spells3) + " Expected: 0.250000");

        String[] spells4 = {"10d4", "5d8", "2d20"};
        System.out.println(chanceOfKillingTheZombie(40, spells4) + " Expected: 0.002500");

        String[] spells5 = {"1d10", "1d10+1", "1d10+2", "1d10+3"};
        System.out.println(chanceOfKillingTheZombie(10, spells5) + " Expected: 0.400000");
//
//        for(int i = 0; i < 1000; i++) {
//            String[] spellsHeavyInput = {"20d20", "20d20", "20d20", "20d20", "20d20", "20d20", "20d20", "20d20", "20d20", "20d20"};
//            System.out.println(chanceOfKillingTheZombie(300, spellsHeavyInput) + " Expected: Enough memory");
//        }

        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int zombiesFought = 0;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size() - 1; i++) {
            String[] healthAndSpells = lines.get(i).split(" ");
            int health = Integer.parseInt(healthAndSpells[0]);

            i++;
            String[] spells = lines.get(i).split(" ");
            String probability = chanceOfKillingTheZombie(health, spells);

            zombiesFought++;
            output.add("Case #" + zombiesFought + ": " + probability);
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static String chanceOfKillingTheZombie(int health, String[] spells) {

        double chanceOfKillingTheZombie = 0;

        for(String currentSpell : spells) {
            int timesToRoll;
            int sides;
            int zValue = 0;

            int indexOfDChar = currentSpell.indexOf('d');
            int indexOfPlusChar = currentSpell.indexOf('+');
            int indexOfMinusChar = currentSpell.indexOf('-');

            timesToRoll = Integer.parseInt(currentSpell.substring(0, indexOfDChar));

            if (indexOfPlusChar != -1) {
                sides = Integer.parseInt(currentSpell.substring(indexOfDChar+1, indexOfPlusChar));
                zValue = Integer.parseInt(currentSpell.substring(indexOfPlusChar+1));
            } else if (indexOfMinusChar != -1){
                sides = Integer.parseInt(currentSpell.substring(indexOfDChar+1, indexOfMinusChar));
                zValue = Integer.parseInt(currentSpell.substring(indexOfMinusChar+1)) * -1;
            } else {
                sides = Integer.parseInt(currentSpell.substring(indexOfDChar+1));
            }

            double probability = getSpellProbability(health, timesToRoll, sides, zValue);
            if (probability > chanceOfKillingTheZombie) {
                chanceOfKillingTheZombie = probability;
            }

            if (chanceOfKillingTheZombie == 1) {
                break;
            }
        }

        return String.format("%f", chanceOfKillingTheZombie);
    }

    private static double getSpellProbability(int health, int dices, int sides, int zValue) {

        //Considering a maximum of 20 dices and 20 sides
        if (zValue < 0 && health > 400) {
            return 0;
        }

        if (zValue > health) {
            return 1;
        }

        if (dices >= health && zValue >= 0) {
            return 1;
        }

        int maxValuePossible = dices * sides;

        BigInteger[][] dp = new BigInteger[dices + 1][maxValuePossible + 1];

        BigDecimal totalValues = BigDecimal.valueOf(Math.pow(sides, dices));

        //Base case - for just 1 dice
        for(int i = 1; i <= sides; i++) {
            dp[1][i] = BigInteger.valueOf(1);
        }

        int hitRequired = zValue > 0 ? health - zValue : health - zValue;

        if (hitRequired > maxValuePossible) {
            return 0;
        }

        //To optimize for big values:
        // Check if it is better to compute the chances of killing or not killing the zombie
        boolean computeChancesOfKillingZombie = maxValuePossible - hitRequired < hitRequired - 1;

        if (computeChancesOfKillingZombie) {

            for(int i = 2; i <= dices; i++) {
                for(int j = 1; j <= maxValuePossible; j++) {
                    for(int k = 1; k <= sides && k < j; k++) {

                        if (dp[i][j] == null) {
                            dp[i][j] = BigInteger.valueOf(0);
                        }

                        if (dp[i-1][j - k] == null) {
                            dp[i-1][j - k] = BigInteger.valueOf(0);
                        }

                        dp[i][j] = dp[i][j].add(dp[i - 1][j - k]);
                    }
                }
            }

            BigInteger numberOfValuesThatWouldKillTheZombie = BigInteger.valueOf(0);

            for(int i = hitRequired; i <= maxValuePossible; i++) {
                if (dp[dices][i] != null) {
                    numberOfValuesThatWouldKillTheZombie = numberOfValuesThatWouldKillTheZombie.add(dp[dices][i]);
                }
            }

            BigDecimal killZombieValues = new BigDecimal(numberOfValuesThatWouldKillTheZombie);
            return killZombieValues.divide(totalValues, 6, RoundingMode.HALF_UP).doubleValue();
        } else {

            for(int i = 2; i <= dices; i++) {
                for(int j = 1; j <= hitRequired + sides; j++) {
                    for(int k = 1; k <= sides && k < j; k++) {

                        if (dp[i][j] == null) {
                            dp[i][j] = BigInteger.valueOf(0);
                        }

                        if (dp[i-1][j - k] == null) {
                            dp[i-1][j - k] = BigInteger.valueOf(0);
                        }
                        dp[i][j] = dp[i][j].add(dp[i - 1][j - k]);
                    }
                }
            }

            BigInteger numberOfValuesThatWouldNotKillTheZombie = BigInteger.valueOf(0);

            for(int i = 1; i < hitRequired; i++) {
                if (dp[dices][i] != null) {
                    numberOfValuesThatWouldNotKillTheZombie = numberOfValuesThatWouldNotKillTheZombie.add(dp[dices][i]);
                }
            }

            BigDecimal doNotKillZombieValues = new BigDecimal(numberOfValuesThatWouldNotKillTheZombie);
            return 1 - doNotKillZombieValues.divide(totalValues, 6, RoundingMode.HALF_DOWN).doubleValue();
        }
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
