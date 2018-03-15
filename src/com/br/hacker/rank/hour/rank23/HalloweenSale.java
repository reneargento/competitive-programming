package com.br.hacker.rank.hour.rank23;

import java.util.Scanner;

/**
 * Created by rene on 09/09/17.
 */
public class HalloweenSale {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int initialPrice = scanner.nextInt();
        int moneyToSubtract = scanner.nextInt();
        int threshold = scanner.nextInt();
        int dollarsAvailable = scanner.nextInt();
        int currentPrice = initialPrice;

        int totalGamesBought = 0;

        while (dollarsAvailable > 0) {
            int moneyToPay = Math.max(currentPrice, threshold);

            if (dollarsAvailable - moneyToPay >= 0) {
                totalGamesBought++;
            }

            dollarsAvailable -= moneyToPay;

            currentPrice -= moneyToSubtract;
        }

        System.out.println(totalGamesBought);
    }

}
