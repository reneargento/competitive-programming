package com.br.uri.made.by.women2018;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 10/03/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/346/4
public class PairedPairs {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();

        while (length != 0) {
            int[] setA = new int[length];
            int[] setB = new int[length];

            for (int i = 0; i < length; i++) {
                setA[i] = scanner.nextInt();
            }

            for (int i = 0; i < length; i++) {
                setB[i] = scanner.nextInt();
            }

            Map<Integer, HashSet<Integer>> pairedPairs = new HashMap<>();

            int count = 0;
            boolean foundEdgeCase = false;

            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {

                    int number1 = setA[i];
                    int number2 = setB[j];

                    if (number1 == number2 && number1 == 1) {
                        foundEdgeCase = true;
                    }

                    if (pairedPairs.containsKey(number1)) {
                        HashSet<Integer> pairs = pairedPairs.get(number1);

                        if (pairs.contains(number2)) {
                            continue;
                        } else {
                            pairs.add(number2);
                        }
                    } else {
                        HashSet<Integer> pairs = new HashSet<>();
                        pairs.add(number2);
                        pairedPairs.put(number1, pairs);
                    }

                    if (pairedPairs.containsKey(number2)) {
                        HashSet<Integer> pairs = pairedPairs.get(number2);

                        if (!pairs.contains(number1)) {
                            pairs.add(number1);
                        }
                    } else {
                        HashSet<Integer> pairs = new HashSet<>();
                        pairs.add(number1);
                        pairedPairs.put(number2, pairs);
                    }

                    long gcd = gcd(number1, number2);
                    if (gcd == 1) {
                        count++;
                    }
                }
            }

            int result = count * 2;

            if (foundEdgeCase) {
                result--;
            }

            System.out.println(result);
            length = scanner.nextInt();
        }
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

}
