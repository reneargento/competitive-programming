package com.br.uri.christmas.contest2017;

import java.util.Scanner;

/**
 * Created by rene on 16/12/17.
 */
//https://www.urionlinejudge.com.br/judge/en/problems/view/2724
//https://www.urionlinejudge.com.br/judge/en/challenges/view/338/8
public class HelpPatatatitu {

    public static class BoyerMoore {
        private final int R;     // the radix
        private int[] right;     // the bad-character skip array

        private String pat;      // or as a string

        /**
         * Preprocesses the pattern string.
         *
         * @param pat the pattern string
         */
        public BoyerMoore(String pat) {
            this.R = 256;
            this.pat = pat;

            // position of rightmost occurrence of c in the pattern
            right = new int[R];
            for (int c = 0; c < R; c++)
                right[c] = -1;
            for (int j = 0; j < pat.length(); j++)
                right[pat.charAt(j)] = j;
        }

        /**
         * Returns the index of the first occurrrence of the pattern string
         * in the text string.
         *
         * @param  txt the text string
         * @return the index of the first occurrence of the pattern string
         *         in the text string; n if no such match
         */
        public int search(String txt) {
            int m = pat.length();
            int n = txt.length();
            int skip;
            for (int i = 0; i <= n - m; i += skip) {
                skip = 0;
                for (int j = m-1; j >= 0; j--) {
                    if (pat.charAt(j) != txt.charAt(i+j)) {
                        skip = Math.max(1, j - right[txt.charAt(i+j)]);
                        break;
                    }
                }
                if (skip == 0) return i;    // found
            }
            return n;                       // not found
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for(int t = 0; t < tests; t++) {
            int dangerousElementsNumber = scanner.nextInt();
            String[] dangerousElements = new String[dangerousElementsNumber];

            for(int danger = 0; danger < dangerousElementsNumber; danger++) {
                dangerousElements[danger] = scanner.next();
            }

            int experiments = scanner.nextInt();

            for(int e = 0; e < experiments; e++) {
                String experiment = scanner.next();

                boolean proceed = true;

                for(int danger = 0; danger < dangerousElementsNumber; danger++) {
                    BoyerMoore boyerMoore = new BoyerMoore(dangerousElements[danger]);
                    int offset = boyerMoore.search(experiment);

                    if (offset != experiment.length()) {
                        int indexToCheckIfItIsAnotherElement = offset + dangerousElements[danger].length();

                        if (indexToCheckIfItIsAnotherElement == experiment.length()
                            || (indexToCheckIfItIsAnotherElement < experiment.length()
                                && Character.isUpperCase(experiment.charAt(indexToCheckIfItIsAnotherElement)))) {
                            proceed = false;
                            break;
                        }
                    }
                }

                if (proceed) {
                    System.out.println("Prossiga");
                } else {
                    System.out.println("Abortar");
                }
            }

            if (t != tests - 1) {
                System.out.println();
            }
        }

    }

}
