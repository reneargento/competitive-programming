package com.br.training.unicamp.summer.school2018.contest1;

import java.util.Scanner;

/**
 * Created by rene on 22/01/18.
 */
// http://codeforces.com/group/3qadGzUdR4/contest/101704/problem/J
public class JosephProblem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfPeople = scanner.nextInt();
        int skipNumber = scanner.nextInt();

        int survivor = josephusProblem(numberOfPeople, skipNumber);
        System.out.println(survivor);
    }

    private static int josephusProblem(int numberOfPeople, int skipNumber) {
        if (numberOfPeople == 1) {
            return 1;
        } else {
            /* The position returned by josephus(numberOfPeople - 1, skipNumber)
            is adjusted because the recursive call
            josephus(numberOfPeople - 1, skipNumber) considers the original
            position skipNumber % numberOfPeople + 1 as position 1 */
            return (josephusProblem(numberOfPeople - 1, skipNumber) + skipNumber - 1) % numberOfPeople + 1;
        }
    }

}
