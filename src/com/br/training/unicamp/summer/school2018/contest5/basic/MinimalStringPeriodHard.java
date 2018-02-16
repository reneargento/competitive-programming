package com.br.training.unicamp.summer.school2018.contest5.basic;

import java.util.Scanner;

/**
 * Created by rene on 27/01/18.
 */
// http://codeforces.com/group/3qadGzUdR4/contest/101710/problem/J
public class MinimalStringPeriodHard {

    private static void computeLPSArray(String str, int M, int lps[]) {
        int len = 0;

        int i;

        lps[0] = 0;
        i = 1;

        while (i < M) {
            if (str.charAt(i) == str.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    // Returns true if string is repetition of one of its substrings else return false.
    private static boolean isRepeat(String string) {
        int n = string.length();
        int lps[] = new int[n];

        computeLPSArray(string, n, lps);

        int len = lps[n - 1];

        if (len > 0) {
            System.out.println(string.substring(0, n - len));
        } else {
            System.out.println(string);
        }

        return (len > 0 && n % (n - len) == 0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String string = scanner.next();
            isRepeat(string);
        }
    }

}
