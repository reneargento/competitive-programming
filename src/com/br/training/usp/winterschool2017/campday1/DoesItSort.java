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

        for(int i=0; i < functionCalls; i++) {
            String function = FastReader.next();
            int param1 = FastReader.nextInt();
            int param2 = FastReader.nextInt();

            String command = function + " " + param1 + " " + param2;

            commands[i] = command;
        }

        int nonSortableBitmap = generateAll01Combinations(commands);

        boolean isSorted = nonSortableBitmap == -1;

        if(!isSorted) {
            System.out.println("incorrect");

            for(int i=0; i < 20; i++) {
                if((nonSortableBitmap & (1 << i)) == 0) {
                    System.out.print(0);
                } else {
                    System.out.print(1);
                }

                if(i < 19) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        } else {
            System.out.println("correct");
        }
    }

//    public static void main(String[] args) throws IOException {
//
//        FastReader.init(System.in);
//        int functionCalls = FastReader.nextInt();
//
//        String[] commands = new String[functionCalls];
//
//        for(int i=0; i < functionCalls; i++) {
//            String function = FastReader.next();
//            int param1 = FastReader.nextInt();
//            int param2 = FastReader.nextInt();
//
//            String command = function + " " + param1 + " " + param2;
//
//            commands[i] = command;
//        }
//
//        List<int[]> combinations = generateAll01Combinations(commands);
//
//        boolean isSorted = true;
//
//        for(int i=0; i < combinations.size(); i++) {
//            int[] array = combinations.get(i);
//
//            int[] arrayCopy = new int[20];
//            System.arraycopy(array, 0, arrayCopy, 0, 20);
//
//            for(int j=0; j < commands.length; j++) {
//                String[] command = commands[j].split(" ");
//                String function = command[0];
//                int param1 = Integer.parseInt(command[1]);
//                int param2 = Integer.parseInt(command[2]);
//
//                if(function.equals("sort")) {
//                    int numberOf1s = 0;
//
//                    for(int k=param1; k < param2; k++) {
//                        if(array[k] == 1) {
//                            numberOf1s++;
//                        }
//                    }
//
//                    for(int k=param1; k <= param2 - numberOf1s - 1; k++) {
//                        array[k] = 0;
//                    }
//
//                    for(int k=param2 - numberOf1s; k < param2; k++) {
//                        array[k] = 1;
//                    }
//                    //Arrays.sort(array, param1, param2);
//                } else {
//                    if(array[param1] > array[param2]) {
//                        int aux = array[param1];
//                        array[param1] = array[param2];
//                        array[param2] = aux;
//                    }
//                }
//            }
//
//            for(int j=0; j < array.length - 1; j++) {
//                if(array[j] > array[j + 1]) {
//                    isSorted = false;
//                    break;
//                }
//            }
//
//            if(!isSorted) {
//                System.out.println("incorrect");
//
//                for(int j=0; j < 20; j++) {
//                    System.out.print(arrayCopy[j]);
//
//                    if(i != arrayCopy.length - 1) {
//                        System.out.print(" ");
//                    }
//                }
//                System.out.println();
//                break;
//            }
//        }
//
//        if(isSorted) {
//            System.out.println("correct");
//        }
//    }

    private static boolean testSorting(int bitmap, String[] commands) {
        for(int j=0; j < commands.length; j++) {
            String[] command = commands[j].split(" ");
            String function = command[0];
            int param1 = Integer.parseInt(command[1]);
            int param2 = Integer.parseInt(command[2]);

            if(function.equals("sort")) {
                int numberOf1s = 0;

                for(int k=param1; k < param2; k++) {
                    if((bitmap & (1 << k)) == 1) {
                        numberOf1s++;
                    }
                }

                for(int k=param1; k <= param2 - numberOf1s - 1; k++) {
                    bitmap = bitmap & ~(1 << k);
                }

                for(int k=param2 - numberOf1s; k < param2; k++) {
                    bitmap = bitmap | (1 << k);
                }
                //Arrays.sort(array, param1, param2);
            } else {
                if((bitmap & (1 << param1)) != 0 && (bitmap & (1 << param2)) == 0) {
                    bitmap = bitmap & ~(1 << param1);
                    bitmap = bitmap | (1 << param2);
                }
            }
        }

        if(bitmap == 64512) {
            int stop = 1;
        }

        for(int j=0; j < 19; j++) {
            if((bitmap & (1 << j)) != 0 && (bitmap & (1 << (j + 1))) == 0) {
                return false;
            }
        }

        return true;
    }

    private static int generateAll01Combinations(String[] commands) {
        List<Integer> combinations = new ArrayList<>();

        int initialBitmap = 0;
        int result = generateArray(combinations, initialBitmap, 0, commands);
        return result;
    }

    private static int generateArray(List<Integer> combinations, int bitmap, int position, String[] commands) {
//        boolean[] arrayCopy = new boolean[array.length];
//        System.arraycopy(array, 0, arrayCopy, 0, array.length);
      //  int copyValue = value;

        if(position == 2) {
//            boolean[] arrayCopy2 = new boolean[20];
//            System.arraycopy(arrayCopy, 0, arrayCopy2, 0, 20);
            combinations.add(bitmap);

            boolean isSorted = testSorting(bitmap, commands);
            if(!isSorted) {
                return bitmap;
            } else {
                return -1;
            }
        } else {
            for(int i=0; i <= 1; i++) {
                if(i == 0) {
                    bitmap = bitmap & ~(1 << position);
                } else {
                    bitmap = bitmap | (1 << position);
                }

                int result = generateArray(combinations, bitmap, position + 1, commands);
                if(result != -1) {
                    return result;
                }
            }
        }

        return -1;
    }


//    private static List<int[]> generateAll01Combinations(String[] commands) {
//        List<int[]> combinations = new ArrayList<>();
//        int[] array = new int[20];
//
//        generateArray(combinations, array, 0, commands);
//        return combinations;
//    }
//
//    private static void generateArray(List<int[]> combinations, int[] array, int position, String[] commands) {
//        int[] arrayCopy = new int[array.length];
//        System.arraycopy(array, 0, arrayCopy, 0, array.length);
//
//        if(position == 20) {
//            combinations.add(arrayCopy);
//        } else {
//            for(int i=0; i <= 1; i++) {
//                arrayCopy[position] = i;
//                arrayCopy[position] = position;
//                generateArray(combinations, arrayCopy, position + 1, commands);
//            }
//        }
//    }

}
