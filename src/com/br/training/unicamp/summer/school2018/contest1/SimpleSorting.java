package com.br.training.unicamp.summer.school2018.contest1;

import java.util.*;

/**
 * Created by rene on 22/01/18.
 */
// http://codeforces.com/group/3qadGzUdR4/contest/101704/problem/X
public class SimpleSorting {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();

        Set<Integer> numbersSet = new HashSet<>();

        for (int i = 0; i < number; i++) {
            numbersSet.add(scanner.nextInt());
        }

        Integer[] values = new Integer[numbersSet.size()];

        int index = 0;
        for(int currentNumber : numbersSet) {
            values[index++] = currentNumber;
        }

        if (number % 2 == 0) {
            Arrays.sort(values, Collections.reverseOrder());
        } else {
            Arrays.sort(values);
        }

        for(int currentNumber : values) {
            System.out.println(currentNumber);
        }

    }

}
