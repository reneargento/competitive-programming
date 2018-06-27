package com.br.google.code.jam.women.year2018;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 17/02/18.
 */
public class CEOSearch {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Women/2018/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "ceo_search_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "ceo_search_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "ceo_search_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "ceo_search_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "ceo_search_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "ceo_search_large_output.txt";

    public static void main(String[] args) {
        // test();
        compete();
    }

    private static void compete() {
        List<String> input = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int l = 0; l < input.size();) {

            int experiences = Integer.parseInt(input.get(l));
            Employee[] employees = new Employee[experiences];

            for(int i = 1; i <= experiences; i++) {
                String[] values = input.get(l + i).split(" ");

                BigInteger employeeNumber = new BigInteger(values[0]);
                BigInteger experience = new BigInteger(values[1]);

                employees[i - 1] = new Employee(employeeNumber, experience);
            }

            output.add("Case #" + caseIndex + ": " + getCEOExperience(employees));
            caseIndex++;

            l += experiences + 1;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        Employee[] employees1 = new Employee[3];
        employees1[0] = new Employee(BigInteger.valueOf(2), BigInteger.valueOf(0));
        employees1[1] = new Employee(BigInteger.valueOf(2), BigInteger.valueOf(2));
        employees1[2] = new Employee(BigInteger.valueOf(1), BigInteger.valueOf(3));

        Employee[] employees2 = new Employee[1];
        employees2[0] = new Employee(BigInteger.valueOf(5), BigInteger.valueOf(0));

        Employee[] employees3 = new Employee[3];
        employees3[0] = new Employee(BigInteger.valueOf(4), BigInteger.valueOf(0));
        employees3[1] = new Employee(BigInteger.valueOf(3), BigInteger.valueOf(1));
        employees3[2] = new Employee(BigInteger.valueOf(1), BigInteger.valueOf(2));

        Employee[] employees4 = new Employee[2];
        employees4[0] = new Employee(BigInteger.valueOf(6), BigInteger.valueOf(1));
        employees4[1] = new Employee(BigInteger.valueOf(1), BigInteger.valueOf(2));

        System.out.println(getCEOExperience(employees1) + " Expected: 4");
        System.out.println(getCEOExperience(employees2) + " Expected: 5");
        System.out.println(getCEOExperience(employees3) + " Expected: 3");
        System.out.println(getCEOExperience(employees4) + " Expected: 5");
    }

    public static class Employee implements Comparable<Employee> {
        BigInteger number;
        BigInteger experience;

        Employee(BigInteger number, BigInteger experience) {
            this.number = number;
            this.experience = experience;
        }

        @Override
        public int compareTo(Employee other) {
            if (this.experience.compareTo(other.experience) > 0) {
                return -1;
            } else if (this.experience.compareTo(other.experience) < 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private static BigInteger getCEOExperience(Employee[] employees) {

        BigInteger slotsToManage = BigInteger.ZERO;

        for (int i = 1; i < employees.length; i++) {
            BigInteger slots = employees[i].experience.multiply(employees[i].number);
            BigInteger difference = slots.subtract(employees[i - 1].number);
            int compare = difference.compareTo(BigInteger.ZERO);

            if (compare == 0) {
                continue;
            } else if (compare > 0) {
                if (difference.compareTo(slotsToManage) >= 0) {
                    slotsToManage = BigInteger.ZERO;
                } else {
                    slotsToManage = slotsToManage.subtract(difference);
                }
            } else {
                BigInteger differenceNegated = difference.negate();
                slotsToManage = slotsToManage.add(differenceNegated);
            }
        }

        slotsToManage = slotsToManage.add(employees[employees.length - 1].number);

        BigInteger maxExperienePlusOne = employees[employees.length - 1].experience;
        maxExperienePlusOne = maxExperienePlusOne.add(BigInteger.ONE);

        if (slotsToManage.compareTo(maxExperienePlusOne) >= 0) {
            return slotsToManage;
        } else {
            return maxExperienePlusOne;
        }
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