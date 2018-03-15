package com.br.training.usp.winterschool2017.campday1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by rene on 10/07/17.
 */
public class DoesItSort {

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
        int functionCalls = FastReader.nextInt();

        String[] commands = new String[functionCalls];
        int[] params1 = new int[functionCalls];
        int[] params2 = new int[functionCalls];

        for(int i = 0; i < functionCalls; i++) {
            String function = FastReader.next();
            int param1 = FastReader.nextInt();
            int param2 = FastReader.nextInt();

            commands[i] = function;
            params1[i] = param1;
            params2[i] = param2;
        }

        int nonSortableBitmap = generateAll01Combinations(commands, params1, params2);

        boolean isSorted = nonSortableBitmap == -1;

        if (!isSorted) {
            System.out.println("incorrect");

            for(int i = 19; i >= 0; i--) {
                if ((nonSortableBitmap & (1 << i)) == 0) {
                    System.out.print(0);
                } else {
                    System.out.print(1);
                }

                if (i > 0) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        } else {
            System.out.println("correct");
        }
    }

    private static boolean testSorting(int bitmap, String[] commands, int[] params1,  int[] params2) {
        for(int j = 0; j < commands.length; j++) {
            String function = commands[j];
            int originalParam1 = params1[j];
            int originalParam2 = params2[j];

            //Params are given left-to-right, so let's shift them
            int param1 = 19 - originalParam1;
            int param2 = 19 - originalParam2;

            if (function.equals("sort")) {
                int bitmapFraction = clearAllBitsExceptRange(bitmap, originalParam1 + 12,
                        originalParam2 + 12 - 1);
                int numberOf1s = count1Bits(bitmapFraction);

                int numberOfBitsInRange = originalParam2 - originalParam1;
                int numberOfBitsToClear = numberOfBitsInRange - numberOf1s;

                if (numberOf1s != 0 && numberOfBitsToClear != 0) {
                    bitmap = clearRangeOfBits(bitmap, originalParam1 + 12, numberOfBitsToClear);
                    bitmap = setRangeOfBits(bitmap, originalParam1 + 12 + numberOfBitsToClear, numberOf1s);
                }
            } else {
                if ((bitmap & (1 << param1)) != 0 && (bitmap & (1 << param2)) == 0) {
                    bitmap = bitmap & ~(1 << param1);
                    bitmap = bitmap | (1 << param2);
                }
            }
        }

        for(int j = 19; j > 0; j--) {
            if ((bitmap & (1 << j)) != 0 && (bitmap & (1 << (j - 1))) == 0) {
                return false;
            }
        }

        return true;
    }

    private static int generateAll01Combinations(String[] commands,  int[] params1,  int[] params2) {
        int all1Bits = (int) Math.pow(2, 20) - 1;

        int[] bitmaps = new int[all1Bits + 1];

        for(int bitmap = 0; bitmap < bitmaps.length; bitmap++) {
            bitmaps[bitmap] = bitmap;
        }
        //Improve worst-case by randomizing the bitmap tests
        shuffle(bitmaps);

        for(int i = 0; i < bitmaps.length; i++) {
            boolean isSorted = testSorting(bitmaps[i], commands, params1, params2);
            if (!isSorted) {
                return bitmaps[i];
            }
        }

        return -1;
    }

    private static int clearRangeOfBits(int bitmap, int positionToStart, int numberOfBitsToClear) {
        return bitmap & ~((~0 << (32 - numberOfBitsToClear)) >>> positionToStart);
    }

    private static int setRangeOfBits(int bitmap, int positionToStart, int numberOfBitsToSet) {
        return bitmap | (~0 << (32 - numberOfBitsToSet)) >>> positionToStart;
    }

    private static int clearAllBitsExceptRange(int bitmap, int positionToStart, int positionToEnd) {
        int numberOfBitsInRange = positionToEnd - positionToStart + 1;
        return bitmap & ((~0 << (32 - numberOfBitsInRange)) >>> positionToStart);
    }

    private static int count1Bits(int value) {
        int count = 0;

        while (value > 0) {
            value = value & (value - 1);
            count++;
        }

        return count;
    }

    //Fisher-Yates shuffle
    private static void shuffle(int[] array) {
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomValue = i + random.nextInt(array.length - i);

            int randomElement = array[randomValue];
            array[randomValue] = array[i];
            array[i] = randomElement;
        }
    }

}
