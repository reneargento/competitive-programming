package com.br.training.unicamp.summer.school2018.contest5.basic;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by rene on 27/01/18.
 */
// http://codeforces.com/group/3qadGzUdR4/contest/101710/problem/C
public class PrefixFunction {

    private static int[] getKMPPrefix(String string) {
        char[] characters = string.toCharArray();
        int[] prefix = new int[string.length()];

        int j = 0;

        for (int i = 1; i < characters.length; i++) {
            while (j > 0 && characters[j] != characters[i])
                j = prefix[j - 1];

            if (characters[j] == characters[i]) {
                j++;
            }

            prefix[i] = j;
        }

        return prefix;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String string = scanner.next();
        int[] prefix = getKMPPrefix(string);

        StringJoiner stringJoiner = new StringJoiner(" ");

        for (int value : prefix) {
            stringJoiner.add(String.valueOf(value));
        }
        System.out.println(stringJoiner);
    }

}
