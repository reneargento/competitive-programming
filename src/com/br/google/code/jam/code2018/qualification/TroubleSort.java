package com.br.google.code.jam.code2018.qualification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/04/18.
 */
// https://codejam.withgoogle.com/2018/challenges/00000000000000cb/dashboard/00000000000079cb
public class TroubleSort {

    public static class FastReader {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        //Used to check EOF
        //If getLine() == null, it is a EOF
        //Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int numbers = FastReader.nextInt();

            int evenArraySize;

            if (numbers % 2 == 0) {
                evenArraySize = numbers / 2;
            } else {
                evenArraySize = numbers / 2 + 1;
            }

            int[] arrayEven = new int[evenArraySize];
            int[] arrayOdd = new int[numbers / 2];

            int oddIndex = 0;
            int evenIndex = 0;

            for (int i = 0; i < numbers; i++) {
                int number = FastReader.nextInt();

                if (i % 2 == 0) {
                    arrayEven[evenIndex++] = number;
                } else {
                    arrayOdd[oddIndex++] = number;
                }
            }

            Arrays.sort(arrayOdd);
            Arrays.sort(arrayEven);

            boolean sorted = true;
            int incorrectIndex = -1;

            for (int i = 0; i < arrayOdd.length; i++) {
                if (arrayEven[i] > arrayOdd[i]) {
                    sorted = false;
                    incorrectIndex = i * 2;
                    break;
                }

                if (i + 1 < arrayEven.length) {
                    if (arrayOdd[i] > arrayEven[i + 1]) {
                        sorted = false;
                        incorrectIndex = i * 2 + 1;
                        break;
                    }
                }
            }

            System.out.print("Case #" + t + ": ");

            if (sorted) {
                System.out.println("OK");
            } else {
                System.out.println(incorrectIndex);
            }
        }
    }

}
