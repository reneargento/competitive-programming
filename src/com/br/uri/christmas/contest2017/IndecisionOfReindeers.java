package com.br.uri.christmas.contest2017;

import java.util.Scanner;

/**
 * Created by rene on 16/12/17.
 */
//https://www.urionlinejudge.com.br/judge/en/problems/view/2721
//https://www.urionlinejudge.com.br/judge/en/challenges/view/338/5
public class IndecisionOfReindeers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int snowballs = 0;

        for(int i = 0; i < 9; i++) {
            snowballs += scanner.nextInt();
        }

        int reindeerIndex = 0;
        String[] reindeers = {"Dasher", "Dancer", "Prancer", "Vixen", "Comet", "Cupid", "Donner", "Blitzen", "Rudolph"};

        while (snowballs > 0){
            snowballs--;

            if (snowballs == 0) {
                System.out.println(reindeers[reindeerIndex]);
            }
            reindeerIndex++;

            if (reindeerIndex >= 9) {
                reindeerIndex = 0;
            }
        }
    }

}
