package com.br.training.usp.winterschool2017.campday3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by rene on 12/07/17.
 */
public class SkippingTheQueue {

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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }

    private static class Person implements Comparable<Person>{
        long cost;
        int linePosition;

        Person(long cost, int linePosition) {
            this.cost = cost;
            this.linePosition = linePosition;
        }

        @Override
        public int compareTo(Person that) {
            if (this.cost < that.cost) {
                return -1;
            } else if (this.cost > that.cost) {
                return 1;
            } else {
                if (this.linePosition < that.linePosition) {
                    return -1;
                } else if (this.linePosition > that.linePosition) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int people = FastReader.nextInt();
        Person[] line = new Person[people];

        for(int i = 0; i < line.length; i++) {
            long cost = FastReader.nextInt();
            Person person = new Person(cost, line.length - i);
            line[i] = person;
        }

        Arrays.sort(line);

        long totalCost = 0;
        int currentPosition = people + 1;

        for(int i = 0; i < line.length; i++) {
            if (currentPosition == 1) {
                break;
            }

            if (line[i].linePosition < currentPosition) {
                int positionsToAdvance = currentPosition - line[i].linePosition;
                totalCost += line[i].cost * positionsToAdvance;

                currentPosition = line[i].linePosition;
            }
        }

        System.out.println(totalCost);
    }

}
