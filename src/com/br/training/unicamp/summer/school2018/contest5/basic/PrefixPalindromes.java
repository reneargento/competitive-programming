package com.br.training.unicamp.summer.school2018.contest5.basic;

import java.util.Scanner;

/**
 * Created by rene on 27/01/18.
 */
public class PrefixPalindromes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.next();
        char[] characters = string.toCharArray();

        int right = string.length();
        int prefixLength = 0;

        boolean found = false;

        while (!found) {
            int left = 0;
            right--;
            prefixLength = right;

            while (right >= left) {
                if (characters[left] != characters[right]) {
                    break;
                }

                if (left >= right) {
                    found = true;
                    break;
                }

                left++;
                right--;
            }

            if (left >= right) {
                break;
            }
        }

        System.out.println(prefixLength + 1);
    }

}
