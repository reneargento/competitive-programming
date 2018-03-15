package com.br.google.code.jam.kickstart2017.roundg;

/**
 * Created by rene.argento on 18/12/17.
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 22/10/17.
 */
public class HugeNumbers {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Kickstart 2017/Round G/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "huge_numbers_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "huge_numbers_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "huge_numbers_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "huge_numbers_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "huge_numbers_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "huge_numbers_large_output.txt";

    public static void main(String[] args) {
        //test();
        compete();
    }

    private static void compete() {
        List<String> input = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int l = 0; l < input.size(); l++) {

            String[] values = input.get(l).split(" ");

            int a = Integer.parseInt(values[0]);
            int n = Integer.parseInt(values[1]);
            int p = Integer.parseInt(values[2]);

            output.add("Case #" + caseIndex + ": " + hugeNumber(a, n, p));
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        int a1 = 2;
        int n1 = 1;
        int p1 = 2;

        int a2 = 3;
        int n2 = 3;
        int p2 = 2;

        int a3 = 1;
        int n3 = 10;
        int p3 = 4;

        System.out.println(hugeNumber(a1, n1, p1) + " Expected: 0");
        System.out.println(hugeNumber(a2, n2, p2) + " Expected: 1");
        System.out.println(hugeNumber(a3, n3, p3) + " Expected: 1");
    }

    //Fast exponentiation mod
    private static long mod;

    private static long hugeNumber(int a, int n, int p) {
        mod = p;
        return computeAToNModP(a, n, p);
    }

    private static long computeAToNModP(int a, int n, int p) {
        long answer = a % p;

        for(int i = 2; i <= n; i++) {
            answer = fastExponentiation(answer, i) % p;
        }

        return answer;
    }

    private static long fastExponentiation(long base, long exponent) {
        if (exponent == 0) {
            return 1;
        }
        if (exponent == 1) {
            return base;
        }

        long baseSquared = (base * base) % mod;

        if (exponent % 2 == 0) {
            return fastExponentiation(baseSquared, exponent / 2);
        }

        if (exponent % 2 == 1) {
            return (base * fastExponentiation(baseSquared, exponent / 2)) % mod;
        }

        return -1;
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> valuesList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i = 1; i < lines.size(); i++) {
                valuesList.add(lines.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return valuesList;
    }

    private static void writeDataOnFile(String file, List<String> data){
        for(String line : data) {
            writeFileOutput(file, line + "\n");
        }
    }

    private static void writeFileOutput(String file, String data){
        byte[] dataBytes = data.getBytes();

        try {
            Files.write(Paths.get(file), dataBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}