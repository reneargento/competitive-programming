package com.br.training.unicamp.summer.school2018.contest1;

import java.util.*;

/**
 * Created by Rene Argento on 22/01/18.
 */
// Based on https://stackoverflow.com/questions/3172179/valid-permutation-of-parenthesis
// http://codeforces.com/group/3qadGzUdR4/contest/101704/problem/Z
public class Parenthesis {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
        brackets(number, 0, "");
    }

    public static void brackets(int openStock, int closeStock, String parentheses) {
        if (openStock == 0 && closeStock == 0) {
            System.out.println(parentheses);
        }

        if (openStock > 0) {
            brackets(openStock - 1, closeStock + 1, parentheses + "(");
        }

        if (closeStock > 0) {
            brackets(openStock, closeStock - 1, parentheses + ")");
        }
    }

}
