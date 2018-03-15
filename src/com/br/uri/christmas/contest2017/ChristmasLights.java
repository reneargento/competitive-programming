package com.br.uri.christmas.contest2017;

import java.util.Scanner;

/**
 * Created by rene on 16/12/17.
 */
//https://www.urionlinejudge.com.br/judge/en/problems/view/2718
//https://www.urionlinejudge.com.br/judge/en/challenges/view/338/2
public class ChristmasLights {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int groups = scanner.nextInt();

        for(int group = 0; group < groups; group++) {
            long bulbs = scanner.nextLong();

            String binaryBulbs = "";

            while (bulbs > 0) {
                long divisionRemainder = bulbs % 2;
                binaryBulbs += divisionRemainder;
                bulbs /= 2;
            }

            int largestConsecutiveBulbs = 0;
            int currentConsecutiveBulbs = 0;

            // The binary representation is inversed, but this is a not problem for this exercise
            for(char bulb : binaryBulbs.toCharArray()) {
                if (bulb == '1') {
                    currentConsecutiveBulbs++;
                } else {
                    currentConsecutiveBulbs = 0;
                }

                if (currentConsecutiveBulbs > largestConsecutiveBulbs) {
                    largestConsecutiveBulbs = currentConsecutiveBulbs;
                }
            }

            System.out.println(largestConsecutiveBulbs);
        }
    }

}
