package com.br.training.unicamp.summer.school2018.contest1;

import java.util.*;

/**
 * Created by rene on 22/01/18.
 */
// TODO http://codeforces.com/group/3qadGzUdR4/contest/101704/problem/P
public class Permutations {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
        String numbers = "";

        if (number == 1) {
            System.out.println(1);
            return;
        }

        for(int i = 1; i <= number; i++) {
            numbers += i;
        }

        List<String> permutations = new ArrayList<>();
        stringPermutation(numbers, permutations);

      //  Collections.sort(permutations);

        for (String permutation : permutations) {
            print(permutation);
        }

//        long factorial = factorial(number);
//        int indexToSwap1 = 0;
//        int indexToSwap2 = 1;

//        Set<String> permutations = new HashSet<>();
//
//        while (permutations.size() < factorial) {
//            permutations.add(numbers.toString());
//
//            char charAtIndex1 = numbers.charAt(indexToSwap1);
//            char charAtIndex2 = numbers.charAt(indexToSwap2);
//
//            numbers.replace(indexToSwap2, indexToSwap2 + 1, String.valueOf(charAtIndex1));
//            numbers.replace(indexToSwap1, indexToSwap1 + 1, String.valueOf(charAtIndex2));
//
//            indexToSwap1++;
//            indexToSwap2++;
//
//            if (indexToSwap1 == numbers.length()) {
//                indexToSwap1 = 0;
//            }
//
//            if (indexToSwap2 == numbers.length()) {
//                indexToSwap2 = 0;
//            }
//        }
//
//        for(String permutation : permutations) {
//            print(permutation);
//        }
    }

    private static void stringPermutation(String string, List<String> combinations) {
        stringPermutation("", string, combinations);
    }

    private static void stringPermutation(String prefix, String string, List<String> combinations) {
        int length = string.length();

        if (length == 0)  {
            combinations.add(prefix);
        } else {
            for (int i = 0; i < length; i++) {
                stringPermutation(prefix + string.charAt(i), string.substring(0, i) +
                        string.substring(i + 1, length), combinations);
            }
        }
    }

    private static long factorial(int number) {
        long factorial = 1;

        while (number >= 1) {
            factorial *= number;
            number--;
        }

        return factorial;
    }

    private static void print(String numbers) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        for(int c = 0; c < numbers.length(); c++) {
            stringJoiner.add(String.valueOf(numbers.charAt(c)));
        }
        System.out.println(stringJoiner.toString());
    }

}
