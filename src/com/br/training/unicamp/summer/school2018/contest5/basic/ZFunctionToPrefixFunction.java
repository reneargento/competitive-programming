package com.br.training.unicamp.summer.school2018.contest5.basic;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by rene on 27/01/18.
 */
// http://codeforces.com/group/3qadGzUdR4/contest/101710/problem/A
public class ZFunctionToPrefixFunction {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int length = scanner.nextInt();
        int[] zFunction = new int[length];

        for(int i = 0; i < zFunction.length; i++) {
            zFunction[i] = scanner.nextInt();
        }

        int[] prefix = zFunctionToPrefix(zFunction);

        StringJoiner stringJoiner = new StringJoiner(" ");

        for (int value : prefix) {
            stringJoiner.add(String.valueOf(value));
        }
        System.out.println(stringJoiner);
    }

    private static int[] zFunctionToPrefix(int[] zFunction) {
        int[] prefixFunction = new int[zFunction.length];

        for(int i = 1; i < zFunction.length; i++) {
            prefixFunction[i + zFunction[i] - 1] = Math.max(prefixFunction[i + zFunction[i] - 1], zFunction[i]);
        }
        for(int i = zFunction.length - 2; i >= 0; i--) {
            prefixFunction[i] = Math.max(prefixFunction[i + 1] - 1, prefixFunction[i]);
        }

        return prefixFunction;
    }

}
