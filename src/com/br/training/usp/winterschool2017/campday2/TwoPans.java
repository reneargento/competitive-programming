package com.br.training.usp.winterschool2017.campday2;

import java.util.Scanner;

/**
 * Created by rene on 11/07/17.
 */
public class TwoPans {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pancakes = scanner.nextInt();

        //Base case
        if (pancakes == 1) {
            System.out.println(2);
            System.out.println("1A 0X");
            System.out.println("0X 1B");

            return;
        }

        int time = pancakes;

        System.out.println(time);

        if (pancakes % 2 == 0) {
            int currentPancake = 1;
            for(int i = 1; i <= pancakes; i++) {
                if (i % 2 == 1) {
                    System.out.println(currentPancake + "A " + (currentPancake + 1) + "A");
                } else {
                    System.out.println((currentPancake - 1) + "B " + currentPancake + "B");
                }
                currentPancake++;
            }
        } else {
            System.out.println("1A 3A");
            System.out.println("1B 2A");
            System.out.println("2B 3B");

            int currentPancake = 4;
            for(int i = 4; i <= pancakes; i++) {
                if (i % 2 == 0) {
                    System.out.println(currentPancake + "A " + (currentPancake + 1) + "A");
                } else {
                    System.out.println((currentPancake - 1) + "B " + currentPancake + "B");
                }
                currentPancake++;
            }
        }
    }

}
