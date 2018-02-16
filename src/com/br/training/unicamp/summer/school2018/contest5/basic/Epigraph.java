package com.br.training.unicamp.summer.school2018.contest5.basic;

import java.util.*;

// http://codeforces.com/group/3qadGzUdR4/contest/101710/problem/Q
// TODO
public class Epigraph {

    private static long countAllUniqueSubstrings(String string) {
        Set<String> uniqueSubstrings = new HashSet<>();

        for(int i = 0; i < string.length(); i++) {
            for(int j = 1; j <= string.length() - i; j++) {
                uniqueSubstrings.add(string.substring(i, i + j));
            }
        }

        long count = 0;
        for(String substring : uniqueSubstrings) {
            count += substring.length();
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String string = scanner.next();
        System.out.println(countAllUniqueSubstrings(string));
    }

}
