package com.br.uri.christmas.contest2017;

import java.util.Scanner;

/**
 * Created by rene on 16/12/17.
 */
//https://www.urionlinejudge.com.br/judge/en/problems/view/2719
//https://www.urionlinejudge.com.br/judge/en/challenges/view/338/3
public class HowManyTrips {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for(int t = 0; t < tests; t++) {
            int gifts = scanner.nextInt();
            int totalWeight = scanner.nextInt();

            int currentWeight = 0;
            int trips = 0;

            for(int gift = 0; gift < gifts; gift++) {
                int giftWeight = scanner.nextInt();

                if (currentWeight + giftWeight > totalWeight) {
                    trips++;
                    currentWeight = giftWeight;
                } else {
                    currentWeight += giftWeight;
                }
            }

            if (currentWeight != 0) {
                trips++;
            }

            System.out.println(trips);
        }

    }

}
