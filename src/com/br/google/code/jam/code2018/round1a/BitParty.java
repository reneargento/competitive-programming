package com.br.google.code.jam.code2018.round1a;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 13/04/18.
 */
public class BitParty {

    private static class Cashier implements Comparable<Cashier> {
        long scanTime;
        long extraTime;
        long maxBits;

        Cashier(long maxBits, long scanTime, long extraTime) {
            this.maxBits = maxBits;
            this.scanTime = scanTime;
            this.extraTime = extraTime;
        }

        @Override
        public int compareTo(Cashier other) {
            return (int) ((scanTime + extraTime) - (other.scanTime + other.extraTime));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int test = 0; test < tests; test++) {
            int robots = scanner.nextInt();
            int bits = scanner.nextInt();
            int cashiers = scanner.nextInt();

            Cashier[] cashiersInformation = new Cashier[cashiers];

            for (int cashier = 0; cashier < cashiers; cashier++) {
                long maxBits = scanner.nextInt();
                long scanTime = scanner.nextInt();
                long extraTime = scanner.nextInt();

                cashiersInformation[cashier] = new Cashier(maxBits, scanTime, extraTime);
            }

            Arrays.sort(cashiersInformation);

            long totalTime = 0;

            for (int cashier = 0; cashier < cashiers; cashier++) {
                long currentTime = 0;

                long bitsToUse = Math.min(bits, cashiersInformation[cashier].maxBits);

                bits -= bitsToUse;
                currentTime += bitsToUse * cashiersInformation[cashier].scanTime;
                currentTime += cashiersInformation[cashier].extraTime;

                totalTime = Math.max(totalTime, currentTime);

                if (bits == 0) {
                    break;
                }
            }

            System.out.println(totalTime);
        }

    }

}
