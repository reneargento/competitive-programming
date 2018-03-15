package com.br.uri.christmas.contest2017;

import java.util.Scanner;

/**
 * Created by rene on 16/12/17.
 */
//https://www.urionlinejudge.com.br/judge/en/problems/view/2717
//https://www.urionlinejudge.com.br/judge/en/challenges/view/338/1
public class ElfTime {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int time = scanner.nextInt();

        int toyTime1 = scanner.nextInt();
        int toyTime2 = scanner.nextInt();

        if (toyTime1 + toyTime2 <= time) {
            System.out.println("Farei hoje!");
        } else {
            System.out.println("Deixa para amanha!");
        }
    }

}
