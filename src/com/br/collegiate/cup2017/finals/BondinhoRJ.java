package com.br.collegiate.cup2017.finals;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by rene on 17/06/17.
 */
public class BondinhoRJ {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int people = scanner.nextInt();
        int[] previous = new int[people + 1];

        for(int i = 1; i < previous.length; i++) {
            previous[i] = scanner.nextInt();
        }

        int suggestions = scanner.nextInt();
        int capacity = scanner.nextInt();

        for(int i = 0; i < suggestions; i++) {

            Set<Integer> consideredPeople = new HashSet<>();

            int[] bond = new int[capacity];
            for(int j = 0; j < capacity; j++) {
                bond[j] = scanner.nextInt();

                consideredPeople.add(bond[j]);
            }

            boolean fair = true;

            for(int j = 0; j < previous.length; j++) {
                if (consideredPeople.contains(j + 1)) {
                    if (!consideredPeople.contains(previous[j + 1])) {
                        System.out.println("no");
                        fair = false;
                        break;
                    }
                }
            }

            if (fair) {
                System.out.println("yes");
            }
        }
    }

}
