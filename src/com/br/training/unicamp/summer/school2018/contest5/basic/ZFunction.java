package com.br.training.unicamp.summer.school2018.contest5.basic;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by rene on 27/01/18.
 */
// http://codeforces.com/group/3qadGzUdR4/contest/101710/problem/D
public class ZFunction {

    private static int[] getZFunction(String string) {
        char[] characters = string.toCharArray();
        int[] zFunction = new int[string.length()];

        int left = 0;
        int right = 0;

        for(int i = 1; i < characters.length; i++) {
            if (i > right) {
                right = i;
                left = i;

                while (right < characters.length
                        && characters[right - left] == characters[right]) {
                    right++;
                }

                zFunction[i] = right - left;
                right--;
            } else {
                int k = i - left;
                if (zFunction[k] < right - i + 1) {
                    zFunction[i] = zFunction[k];
                }
                else {
                    left = i;
                    while (right < characters.length
                            && characters[right - left] == characters[right]) {
                        right++;
                    }
                    zFunction[i] = right - left;
                    right--;
                }
            }
        }

        return zFunction;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String string = scanner.next();
        int[] zFunction = getZFunction(string);

        StringJoiner stringJoiner = new StringJoiner(" ");

        for (int value : zFunction) {
            stringJoiner.add(String.valueOf(value));
        }
        System.out.println(stringJoiner);
    }

}
