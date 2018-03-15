package com.br.training.usp.winterschool2017.campday2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by rene on 11/07/17.
 */
public class Twice {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String maxValue = "99887766554433221100";

        int tests = scanner.nextInt();

        for(int t = 0; t < tests; t++) {
            String value = scanner.nextLine();

            if (value.length() > 20) {
                System.out.println(maxValue);
                continue;
            }

            Map<Integer, Integer> countMap = new HashMap<>();
            for(int i = 0; i <= 9 ; i++) {
                countMap.put(i, 0);
            }


        }
    }

    private static String fixValue(String value, int index, Map<Integer, Integer> countMap) {
        if (index > value.length() || index < 0) {
            return value;
        }

        int number = Integer.parseInt("" + value.charAt(index));

        while(countMap.get(number) == 2) {
            if (number == 0) {
                number = 9;


            } else {
                number--;
            }
        }


        return null;

    }

}
