package com.br.uri.made.by.women2018;

import java.util.Scanner;

/**
 * Created by Rene Argento on 10/03/18.
 */
public class GraceHopperGrandmaOfCobol {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String[] words = scanner.nextLine().split("-");

            if ((words[0].charAt(0) == ('c') || words[0].charAt(words[0].length() - 1) == ('c')
                    || words[0].charAt(0) == ('C') || words[0].charAt(words[0].length() - 1) == ('C'))
                    && (words[1].charAt(0) == ('o') || words[1].charAt(words[1].length() - 1) == ('o')
                    || words[1].charAt(0) == ('O') || words[1].charAt(words[1].length() - 1) == ('O'))
                    && (words[2].charAt(0) == ('b') || words[2].charAt(words[2].length() - 1) == ('b')
                    || words[2].charAt(0) == ('B') || words[2].charAt(words[2].length() - 1) == ('B'))
                    && (words[3].charAt(0) == ('o') || words[3].charAt(words[3].length() - 1) == ('o')
                    || words[3].charAt(0) == ('O') || words[3].charAt(words[3].length() - 1) == ('O'))
                    && (words[4].charAt(0) == ('l') || words[4].charAt(words[4].length() - 1) == ('l')
                    || words[4].charAt(0) == ('L') || words[4].charAt(words[4].length() - 1) == ('L'))) {
                System.out.println("GRACE HOPPER");
            } else {
                System.out.println("BUG");
            }
        }
    }

}
