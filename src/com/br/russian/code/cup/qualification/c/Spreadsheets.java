package com.br.russian.code.cup.qualification.c;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by rene on 29/04/17.
 */
public class Spreadsheets {

    public static void main(String[] args) {
        test();
        //compete();
    }

    private static void test() {
        System.out.println(getColumnValue(1) + " Expected: A");
        System.out.println(getColumnValue(26) + " Expected: Z");
        System.out.println(getColumnValue(27) + " Expected: AA");
        System.out.println(getColumnValue(52) + " Expected: AZ");
        System.out.println(getColumnValue(10) + " Expected: J");
        System.out.println(getColumnValue(100) + " Expected: CV");
        System.out.println(getColumnValue(1000) + " Expected: ALL");
        System.out.println(getColumnValue(100000000) + " Expected: HJUNYV");
    }

    private static void compete() {
        Scanner sc = new Scanner(System.in);
        int numberOfTests = Integer.parseInt(sc.nextLine());

        for(int i = 0; i < numberOfTests; i++) {
            int columnId = sc.nextInt();

            System.out.println(getColumnValue(columnId));
        }
    }

    private static String getColumnValue(int columnId) {
        StringBuilder result = new StringBuilder();

        Map<Integer, String> columnsMap = new HashMap<>();
        columnsMap.put(1, "A");
        columnsMap.put(2, "B");
        columnsMap.put(3, "C");
        columnsMap.put(4, "D");
        columnsMap.put(5, "E");
        columnsMap.put(6, "F");
        columnsMap.put(7, "G");
        columnsMap.put(8, "H");
        columnsMap.put(9, "I");
        columnsMap.put(10, "J");
        columnsMap.put(11, "K");
        columnsMap.put(12, "L");
        columnsMap.put(13, "M");
        columnsMap.put(14, "N");
        columnsMap.put(15, "O");
        columnsMap.put(16, "P");
        columnsMap.put(17, "Q");
        columnsMap.put(18, "R");
        columnsMap.put(19, "S");
        columnsMap.put(20, "T");
        columnsMap.put(21, "U");
        columnsMap.put(22, "V");
        columnsMap.put(23, "W");
        columnsMap.put(24, "X");
        columnsMap.put(25, "Y");
        columnsMap.put(26, "Z");

        boolean isLastChar = columnId <= 26;

        while (columnId % 26 >= 0) {
            int letterKey;

            if (columnId % 26 == 0) {
                letterKey = 26;
                columnId = columnId / 26 - 1;
            } else {
                letterKey = columnId % 26;
                columnId /= 26;
            }

            result.append(columnsMap.get(letterKey));

            if (columnId <= 26) {
                if (isLastChar) {
                    break;
                } else {
                    isLastChar = true;
                }
            }
        }

        return result.reverse().toString();
    }

}
