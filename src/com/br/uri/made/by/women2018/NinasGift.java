package com.br.uri.made.by.women2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/03/18.
 */
public class NinasGift {

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

        for (int t = 0; t < tests; t++) {
            int length = FastReader.nextInt();

            int[] permutation = new int[length];
            int[] array = new int[length];

            for (int i = 0; i < permutation.length; i++) {
                int number = FastReader.nextInt();
                permutation[i] = number;
                array[i] = number;
            }



        }

    }

    private static boolean isCycleFound(int[] currentArray, int[] permutation) {

        boolean isCycleFound = true;

        for (int i = 0; i < currentArray.length; i++) {
            if (currentArray[i] != permutation[i]) {
                isCycleFound = false;
                break;
            }
        }

        return isCycleFound;
    }

}
