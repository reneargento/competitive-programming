package com.br.uri.christmas.contest2017;

import java.util.Scanner;

/**
 * Created by rene on 16/12/17.
 */
//https://www.urionlinejudge.com.br/judge/en/problems/view/2722
//https://www.urionlinejudge.com.br/judge/en/challenges/view/338/6
public class EvergreenTrick {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        scanner.nextLine();

        for(int t = 0; t < tests; t++) {
            String line1 = scanner.nextLine();
            String line2 = scanner.nextLine();

            StringBuilder result = new StringBuilder();
            int line1Index = 0;
            int line2Index = 0;

            for(int i = 0; i < line1.length() / 2; i++) {
                result.append(line1.charAt(line1Index++));

                if (line1Index < line1.length()) {
                    result.append(line1.charAt(line1Index++));
                }

                result.append(line2.charAt(line2Index++));

                if (line2Index < line2.length()) {
                    result.append(line2.charAt(line2Index++));
                }
            }

            System.out.println(result.toString());
        }
    }

}
