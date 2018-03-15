package com.br.collegiate.cup2017.finals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by rene on 17/06/17.
 */
public class Sailing {

    private static class FastReader {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        /** Call this method to initialize reader for InputStream */
        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        /** Get next word */
        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws IOException{
        FastReader.init(System.in);

        int boats = FastReader.nextInt();
        int tourists = FastReader.nextInt();

        int[] colors = new int[boats + 1];

        for(int i = 1; i < colors.length; i++) {
            colors[i] = FastReader.nextInt();
        }

        for(int i = 0; i < tourists; i++) {
            int left = FastReader.nextInt();
            int right = FastReader.nextInt();

            int lastColor = colors[left];
            int cost = colors[left];
            int boatsUsed = 0;

            int maxCost = cost;

            for(int j = left + 1; j <= right; j++) {
                if (colors[j] != lastColor) {
                    if (cost > maxCost) {
                        maxCost = cost;
                        cost = 0;
                        boatsUsed = -1;
                    }
                }

                boatsUsed++;

                cost += Math.max(0, colors[j] - boatsUsed);

                lastColor = colors[j];
            }

            System.out.println(maxCost);
        }

    }

}
