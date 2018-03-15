package com.br.uri.christmas.contest2017;

import java.util.Scanner;

/**
 * Created by rene on 16/12/17.
 */
//https://www.urionlinejudge.com.br/judge/en/problems/view/2723
//https://www.urionlinejudge.com.br/judge/en/challenges/view/338/7
//TODO
public class BalancingGifts {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for(int t = 0; t < tests; t++) {
            int gifts = scanner.nextInt();

            int leftSideWeight = 0;
            int rightSideWeight = 0;

            boolean possible = true;

            for(int gift = 0; gift < gifts; gift++) {
                int weight = scanner.nextInt();

                if (leftSideWeight + weight - rightSideWeight <= 5) {
                    leftSideWeight += weight;
                } else if (rightSideWeight + weight - leftSideWeight <= 5) {
                    rightSideWeight += weight;
                } else {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                System.out.println("Feliz Natal!");
            } else {
                System.out.println("Ho Ho Ho!");
            }
        }
    }

}
