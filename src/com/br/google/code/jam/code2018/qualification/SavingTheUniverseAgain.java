package com.br.google.code.jam.code2018.qualification;

import java.util.*;

/**
 * Created by Rene Argento on 06/04/18.
 */
// https://codejam.withgoogle.com/2018/challenges/00000000000000cb/dashboard
public class SavingTheUniverseAgain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int damagePossible = scanner.nextInt();
            String command = scanner.next();

            int power = 1;
            int damageTaken = 0;
            boolean needsToHack = false;

            System.out.print("Case #" + t + ": ");

            for (int i = 0; i < command.length(); i++) {
                if (command.charAt(i) == 'S') {
                    damageTaken += power;
                } else {
                    power *= 2;
                }

                if (damageTaken > damagePossible) {
                    needsToHack = true;
                }
            }

            if (!needsToHack) {
                System.out.println(0);
            } else {
                int updates = 0;
                boolean possible = false;
                char[] commands = command.toCharArray();

                for (int i = commands.length - 1; i >= 0; i--) {

                    if (commands[i] == 'C') {
                        for (int j = i + 1; j < commands.length; j++) {
                            if (commands[j] == 'S') {
                                updates++;
                                commands[j - 1] = 'S';
                                commands[j] = 'C';

                                damageTaken -= power / 2;

                                if (damageTaken <= damagePossible) {
                                    possible = true;
                                    break;
                                }
                            } else {
                                break;
                            }
                        }

                        power /= 2;

                        if (possible) {
                            break;
                        }
                    }
                }

                if (possible) {
                    System.out.println(updates);
                } else {
                    System.out.println("IMPOSSIBLE");
                }
            }
        }

    }

}
