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
//TODO
public class CEOSearch {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Women/2018/Input - Output/";

    private static final String FILE_INPUT_PATH = PATH + "ceo_search_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "ceo_search_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "ceo_search_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "ceo_search_small_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "ceo_search_large_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "ceo_search_large_output.txt";

    public static void main(String[] args) {
        test();
        // compete();
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

                int experience = Integer.parseInt(values[0]);
                BigInteger employeeNumber = new BigInteger(values[1]);

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
        employees1[0] = new Employee(BigInteger.valueOf(2), 0);
        employees1[1] = new Employee(BigInteger.valueOf(2), 2);
        employees1[2] = new Employee(BigInteger.valueOf(1), 3);

        Employee[] employees2 = new Employee[1];
        employees2[0] = new Employee(BigInteger.valueOf(5), 0);

        Employee[] employees3 = new Employee[3];
        employees3[0] = new Employee(BigInteger.valueOf(4), 0);
        employees3[1] = new Employee(BigInteger.valueOf(3), 1);
        employees3[2] = new Employee(BigInteger.valueOf(1), 2);

        System.out.println(getCEOExperience(employees1) + " Expected: 4");
        System.out.println(getCEOExperience(employees2) + " Expected: 5");
        System.out.println(getCEOExperience(employees3) + " Expected: ?");
    }

    public static class Employee implements Comparable<Employee> {
        BigInteger number;
        int experience;
        BigInteger managing;

        Employee(BigInteger number, int experience) {
            this.number = number;
            this.experience = experience;
            managing = BigInteger.valueOf(experience);
        }

        @Override
        public int compareTo(Employee other) {
            return this.experience - other.experience;
        }
    }

    private static BigInteger getCEOExperience(Employee[] employees) {
        Arrays.sort(employees);
        PriorityQueue<Employee> heap = new PriorityQueue<>(new Comparator<Employee>() {
            @Override
            public int compare(Employee employee1, Employee employee2) {
                return employee2.experience - employee1.experience;
            }
        });
        heap.offer(employees[0]);

        int maxExperience = 0;

        for(int i = 1; i < employees.length; i++) {
            Employee currentEmployee = employees[i];
            Employee employeeInHeap = heap.peek();

            if (currentEmployee.experience > maxExperience) {
                maxExperience = currentEmployee.experience;
            }

            int compare = currentEmployee.managing.compareTo(employeeInHeap.number);

            if (compare == 0) {
                heap.poll();
            } else if (compare > 0) {
                heap.poll();

                BigInteger managersLeft = currentEmployee.managing.subtract(employeeInHeap.number);

                while (!heap.isEmpty() && managersLeft.compareTo(BigInteger.ZERO) > 0) {
                    Employee nextEmployeeInHeap = heap.peek();

                    BigInteger managing = managersLeft.subtract(nextEmployeeInHeap.number);

                    if (managing.compareTo(BigInteger.ZERO) < 0) {
                        nextEmployeeInHeap.number = nextEmployeeInHeap.number.subtract(managersLeft);
                        managersLeft = BigInteger.ZERO;
                    } else {
                        heap.poll();
                        managersLeft = managing;
                    }
                }
            } else {
                employeeInHeap.number = employeeInHeap.number.subtract(currentEmployee.number);
            }

            heap.offer(currentEmployee);
        }

        BigInteger employeesNeedingManagers = BigInteger.ZERO;

        while (!heap.isEmpty()) {
            Employee employee = heap.poll();
            employeesNeedingManagers = employeesNeedingManagers.add(employee.number);
        }

        BigInteger ceoLevel;
        long maxExperienceNeeded = maxExperience + 1;

        if (employeesNeedingManagers.compareTo(BigInteger.valueOf(maxExperienceNeeded)) > 0) {
            ceoLevel = employeesNeedingManagers;
        } else {
            ceoLevel = BigInteger.valueOf(maxExperienceNeeded);
        }

        return ceoLevel;
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
