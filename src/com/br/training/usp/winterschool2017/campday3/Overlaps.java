package com.br.training.usp.winterschool2017.campday3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by rene on 12/07/17.
 */
public class Overlaps {

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

    private static class Class implements Comparable<Class>{
        int id;
        int dayNumber;
        int startTime;
        int endTime;

        Class(int id, int dayNumber, int startTime, int endTime) {
            this.id = id;
            this.dayNumber = dayNumber;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public int compareTo(Class that) {
            if (this.dayNumber < that.dayNumber) {
                return -1;
            } else if (this.dayNumber > that.dayNumber) {
                return 1;
            } else {
                if (this.endTime < that.endTime) {
                    return  -1;
                } else if (this.endTime > that.endTime) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        FastReader.init(System.in);

        int classesNumber = FastReader.nextInt();
        int days = FastReader.nextInt();

        String[] output = new String[days + 1];
        Class[] classes = new Class[classesNumber];

        Map<Integer, Class> classesInformation = new HashMap<>();

        for(int i = 0; i < classesNumber; i++) {
            int startTime = FastReader.nextInt();
            int endTime = FastReader.nextInt();
            int dayNumber = FastReader.nextInt();

            Class newClass = new Class(i+1, dayNumber, startTime, endTime);
            classes[i] = newClass;

            classesInformation.put(i + 1, newClass);
        }

        Arrays.sort(classes);

        int currentDay = classes[0].dayNumber;
        boolean canAttend = false;
        int class1 = classes[0].id;
        int class2 = 0;

        int currentClassEndTime = classes[0].endTime;

        for(int i = 0; i < classes.length; i++) {
            if (classes[i].dayNumber != currentDay) {
                if (canAttend) {
                    int earliestClass;
                    int latestClass;

                    Class class1Info = classesInformation.get(class1);
                    Class class2Info = classesInformation.get(class2);

                    if (class1Info.startTime < class2Info.startTime) {
                        earliestClass = class1;
                        latestClass = class2;
                    } else {
                        earliestClass = class2;
                        latestClass = class1;
                    }

                    output[currentDay] = "ANO " + earliestClass + " " + latestClass;
                } else {
                    output[currentDay] = "NIE";
                }

                currentDay = classes[i].dayNumber;
                canAttend = false;
                currentClassEndTime = classes[i].endTime;
                class1 = classes[i].id;
            }

            if (classes[i].startTime > currentClassEndTime) {
                canAttend = true;
                class2 = classes[i].id;
            }
        }

        if (canAttend) {
            int earliestClass;
            int latestClass;

            Class class1Info = classesInformation.get(class1);
            Class class2Info = classesInformation.get(class2);

            if (class1Info.startTime < class2Info.startTime) {
                earliestClass = class1;
                latestClass = class2;
            } else {
                earliestClass = class2;
                latestClass = class1;
            }

            output[currentDay] = "ANO " + earliestClass + " " + latestClass;
        } else {
            output[currentDay] = "NIE";
        }

        for(int i = 1; i <= days; i++) {
            if (output[i] != null) {
                System.out.println(output[i]);
            } else {
                System.out.println("NIE");
            }
        }
    }

}
