package com.br.codefights.marathon.june2017;

import java.util.*;

/**
 * Created by rene on 24/06/17.
 */

/**
 * Number a is an anagram of number b if a's digits can be rearranged to obtain b.
 * For example, the number 239 is an anagram of 923.
 * However, the number 2390 isn't an anagram of 923, because 0923 isn't the same number as 923.

 Given two numbers l and r, calculate the number of pairs of different numbers a and b such that l ≤ a < b ≤ r and a is an anagram of b.

 Example

 For l = 10 and r = 44, the output should be
 numberOfAnagramPairs(l, r) = 6.

 All possible pairs are listed below:

 a = 12, b = 21;
 a = 13, b = 31;
 a = 14, b = 41;
 a = 23, b = 32;
 a = 24, b = 42;
 a = 34, b = 43.
 For l = 1 and r = 10, the output should be
 numberOfAnagramPairs(l, r) = 0.

 There are no possible pairs. Even a = 1, b = 10 isn't a correct pair because 01 isn't equal to 1.

 Input/Output
 [time limit] 3000ms (java)

 [input] integer l
 Guaranteed constraints:
 1 ≤ l ≤ 105.

 [input] integer r
 Guaranteed constraints:
 l ≤ r ≤ 105.

 [output] integer
 The number of pairs (a, b) such that l ≤ a < b ≤ r and a is an anagram of b.
 */
public class Anagrams {

    public static void main(String[] args) {
        System.out.println(numberOfAnagramPairs(10, 44) + " Expected: 6");
        System.out.println(numberOfAnagramPairs(1, 10) + " Expected: 0");
        System.out.println(numberOfAnagramPairs(1, 1) + " Expected: 0");
        System.out.println(numberOfAnagramPairs(12, 43) + " Expected: 6");
        System.out.println(numberOfAnagramPairs(9508, 9793) + " Expected: 223");
        System.out.println(numberOfAnagramPairs(9405, 99835) + " Expected: 2892725");
    }

    private static int numberOfAnagramPairs(int left, int right) {
        Map<String, Integer> dp = new HashMap<>();

        for(int i = left; i <= right; i++) {
            String key = getAnagram(i);
            if (!dp.containsKey(key)) {
                dp.put(key, 0);
            }
            dp.put(key, dp.get(key) + 1);
        }

        int anagramPairs = 0;
        for(String key: dp.keySet()) {
            int frequency = dp.get(key);
            anagramPairs += frequency * (frequency - 1) / 2;
        }

        return anagramPairs;
    }

    private static String getAnagram(int number) {
        char[] chars = Integer.toString(number).toCharArray();
        Arrays.sort(chars);
        return new StringBuilder(new String(chars)).reverse().toString();
    }

}
