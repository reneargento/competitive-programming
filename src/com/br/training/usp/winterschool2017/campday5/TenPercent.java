package com.br.training.usp.winterschool2017.campday5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by rene on 14/07/17.
 */
public class TenPercent {

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

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int listLength = FastReader.nextInt();
        long[] values = new long[listLength];

        for(int i=0; i < listLength; i++) {
            values[i] = FastReader.nextInt();
        }

        int querieCount = FastReader.nextInt();
        long[] queries = new long[querieCount];

        for(int i=0; i < queries.length; i++) {
            queries[i] = FastReader.nextInt();
        }

        Arrays.sort(values);

        for(int q=0; q < queries.length; q++) {
            long target = queries[q];
            double leftTarget = target * 0.9;
            double rightTarget = target * 1.1;

            int leftBoundary = binarySearch(values, leftTarget, 0, values.length - 1, true);
            int rightBoundary = binarySearch(values, rightTarget, 0, values.length - 1, false);

            long count;

            if(leftBoundary == rightBoundary) {
                if(leftTarget <= values[leftBoundary] && values[leftBoundary] <= rightTarget) {
                    count = 1;
                } else {
                    count = 0;
                }
            }  else {
                boolean isLeftInRange = leftTarget <= values[leftBoundary] && values[leftBoundary] <= rightTarget;
                boolean isRightInRange = leftTarget <= values[rightBoundary] && values[rightBoundary] <= rightTarget;

                if(!isLeftInRange && !isRightInRange) {
                    count = 0;
                } else if(isLeftInRange && isRightInRange) {
                    count = rightBoundary - leftBoundary + 1;
                } else {
                    count = rightBoundary - leftBoundary;
                }
            }

            System.out.println(count);
        }
    }

    private static int binarySearch(long[] array, double target, int low, int high, boolean leftSide) {
        if(low < 0 || high >= array.length || low > high) {
            int result;

            if(leftSide) {
                result = low;
                if(result < 0) {
                    result = 0;
                } else if(result >= array.length) {
                    result = array.length - 1;
                }
            } else {
                result = high;
                if(result < 0) {
                    result = 0;
                } else if(result >= array.length) {
                    result = array.length - 1;
                }
            }

            return result;
        }

        int middle = low + (high - low) / 2;

        if(array[middle] < target) {
            low = middle + 1;
            return binarySearch(array, target, low, high, leftSide);
        } else if(array[middle] > target) {
            high = middle - 1;
            return binarySearch(array, target, low, high, leftSide);
        } else {
            int newResult;

            if(leftSide) {
                newResult = binarySearch(array, target, low, middle - 1, true);
                if(newResult != -1 && newResult < middle) {
                    return newResult;
                }
            } else {
                newResult = binarySearch(array, target, middle + 1, high, false);
                if(newResult > middle) {
                    return newResult;
                }
            }
        }

        return middle;
    }

}
