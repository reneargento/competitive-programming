package com.br.uri.christmas.contest2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by rene on 16/12/17.
 */
// https://www.urionlinejudge.com.br/judge/en/problems/view/2720
//https://www.urionlinejudge.com.br/judge/en/challenges/view/338/4
public class LargeGifts {

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

    private static class Gift implements Comparable<Gift> {

        int id;
        long volume;

        Gift(int id, int height, int width, int length) {
            this.id = id;
            volume = height * width * length;
        }

        @Override
        public int compareTo(Gift other) {
            if (this.volume > other.volume) {
                return -1;
            } else if (this.volume < other.volume) {
                return 1;
            } else {
                if (this.id < other.id) {
                    return -1;
                } else if (this.id > other.id) {
                    return 1;
                }
            }

            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();

        for(int t = 0; t < tests; t++) {
            int gifts = FastReader.nextInt();
            int giftsToChoose = FastReader.nextInt();

            Gift[] allGifts = new Gift[gifts];

            for(int gift = 0; gift < gifts; gift++) {
                int id = FastReader.nextInt();
                int height = FastReader.nextInt();
                int width = FastReader.nextInt();
                int length = FastReader.nextInt();

                allGifts[gift] = new Gift(id, height, width, length);
            }

            Arrays.sort(allGifts);
            int[] chosenGifts = new int[giftsToChoose];

            for(int k = 0; k < giftsToChoose; k++) {
                chosenGifts[k] = allGifts[k].id;
            }

            Arrays.sort(chosenGifts);

            for(int k = 0; k < giftsToChoose; k++) {
                System.out.print(chosenGifts[k]);

                if (k != giftsToChoose - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

}
