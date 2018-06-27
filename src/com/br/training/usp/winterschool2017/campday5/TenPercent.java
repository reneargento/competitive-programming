package com.br.training.usp.winterschool2017.campday5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
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

    private static class Query {
        long value;
        int originalIndex;
        int valuesInRange;

        Query(long value, int originalIndex) {
            this.value = value;
            this.originalIndex = originalIndex;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int listLength = FastReader.nextInt();
        long[] values = new long[listLength];

        for(int i = 0; i < listLength; i++) {
            values[i] = FastReader.nextInt();
        }

        int queriesCount = FastReader.nextInt();
        Query[] queries = new Query[queriesCount];

        for(int i = 0; i < queries.length; i++) {
            long value = FastReader.nextInt();
            queries[i] = new Query(value, i);
        }

        Arrays.sort(queries, new Comparator<Query>() {
            @Override
            public int compare(Query query1, Query query2) {
                if (query1.value < query2.value) {
                    return -1;
                } else if (query1.value > query2.value) {
                    return 1;
                }

                return 0;
            }
        });

        int[] valuesInRange = new int[queries.length];

        for(int v = 0; v < values.length; v++) {
            long target = values[v];
            double leftTarget = target * 0.9;
            double rightTarget = target * 1.1;

            int leftBoundary = binarySearch(queries, leftTarget, 0, queries.length - 1, true);
            int rightBoundary = binarySearch(queries, rightTarget, 0, queries.length - 1, false);

            boolean isLeftInRange = leftTarget <= queries[leftBoundary].value && queries[leftBoundary].value <= rightTarget;
            boolean isRightInRange = leftTarget <= queries[rightBoundary].value && queries[rightBoundary].value <= rightTarget;

            if (leftBoundary == rightBoundary) {
                if (isLeftInRange) {
                    valuesInRange[leftBoundary]++;

                    if (leftBoundary + 1 < valuesInRange.length) {
                        valuesInRange[leftBoundary + 1]--;
                    }
                }
            }  else {
                if (!isLeftInRange && !isRightInRange) {
                    //Nothing to do in this case
                } else if (isLeftInRange && isRightInRange) {
                    valuesInRange[leftBoundary]++;
                    if (rightBoundary + 1 < valuesInRange.length) {
                        valuesInRange[rightBoundary + 1]--;
                    }
                } else {
                    if (isLeftInRange) {
                        valuesInRange[leftBoundary]++;
                        if (rightBoundary < valuesInRange.length) {
                            valuesInRange[rightBoundary]--;
                        }
                    } else if (isRightInRange) {
                        valuesInRange[leftBoundary + 1]++;
                        if (rightBoundary + 1 < valuesInRange.length) {
                            valuesInRange[rightBoundary + 1]--;
                        }
                    }
                }
            }
        }

        int currentCount = 0;
        for(int i = 0; i < valuesInRange.length; i++) {
            currentCount += valuesInRange[i];
            queries[i].valuesInRange = currentCount;
        }

        Arrays.sort(queries, new Comparator<Query>() {
            @Override
            public int compare(Query query1, Query query2) {
                if (query1.originalIndex < query2.originalIndex) {
                    return -1;
                } else if (query1.originalIndex > query2.originalIndex) {
                    return 1;
                }

                return 0;
            }
        });


        for(int i = 0; i < queries.length; i++) {
            System.out.println(queries[i].valuesInRange);
        }
    }

    private static int binarySearch(Query[] array, double target, int low, int high, boolean leftSide) {
        if (low < 0 || high >= array.length || low > high) {
            int result;

            if (leftSide) {
                result = low;
            } else {
                result = high;
            }

            if (result < 0) {
                result = 0;
            } else if (result >= array.length) {
                result = array.length - 1;
            }

            return result;
        }

        int middle = low + (high - low) / 2;

        if (array[middle].value < target) {
            low = middle + 1;
            return binarySearch(array, target, low, high, leftSide);
        } else if (array[middle].value > target) {
            high = middle - 1;
            return binarySearch(array, target, low, high, leftSide);
        } else {
            int newResult;

            if (leftSide) {
                newResult = binarySearch(array, target, low, middle - 1, true);
                if (newResult < middle) {
                    return newResult;
                }
            } else {
                newResult = binarySearch(array, target, middle + 1, high, false);
                if (newResult > middle) {
                    return newResult;
                }
            }
        }

        return middle;
    }

}
