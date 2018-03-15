package com.br.russian.code.cup.qualification.b;

/**
 * Created by rene on 16/04/17.
 */

import java.util.Scanner;

/**
 * Opening ceremony of the new campus of N State University will be visited by nm very important persons.
 * The ceremony will take place in a hall that has the form of a rectangle, seats in the hall are
 * arranged in n rows, m seats in each row.
 * Rows are numbered from 1 to n, seats in each row are numbered from 1 to m, the j-th seat of the i-th row is denoted as (i, j).

 The organizers of the ceremony have numbered the guests from 1 to nm in accordance with their importance —
 the greater, the more important. The most important guest, the mayor of the city, gets the number nm.
 The mayor is planning to take seat (1, 1). Now the other guests must be assigned seats.
 The guests must be arranged according to their importance, there must be no situation that a guest with greater number
 is seating further from the mayor than a guest with smaller number.
 The distance between two seats (r1, s1) and (r2, s2) is measured as |r1 - r2| + |s1 - s2|.

 Help the organizers to assign guests to seats.

 Input format

 Input contains several test cases. The first line contains the number of test cases t (1 ≤ t ≤ 400).
 Each test case is specified with a line that contains two integers: n and m (1 ≤ n, m ≤ 20).

 Output format

 For each test case output the hall plan after the seats are assigned to guests.
 Output n lines, each line must contain m integers, the j-th integer of the i-th line must be equal to the importance
 of the guest that will be assigned the seat (i, j).

 If there are several valid ways to assign seats to guests, output any of them.

 Examples

 Input data
 2
 2 3
 3 2

 Output data
 6 4 2
 5 3 1
 6 4
 5 2
 3 1
 */
public class VeryImportantPersons {

    public static void main(String[] args) {
        test();
       // compete();
    }

    private static void test() {
        int[][] hallPlan1 = getHallPlan(2, 3);
        int[][] hallPlan2 = getHallPlan(3, 2);

        for(int i = 0; i < hallPlan1.length; i++) {
            for(int j = 0; j < hallPlan1[0].length; j++) {
                System.out.print(hallPlan1[i][j]);
                if (j < hallPlan1[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        System.out.println("Expected:" +
                "\n6 4 2 \n" +
                "5 3 1");

        for(int i = 0; i < hallPlan2.length; i++) {
            for(int j = 0; j < hallPlan2[0].length; j++) {
                System.out.print(hallPlan2[i][j]);
                if (j < hallPlan2[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        System.out.println("Expected:" +
                "\n6 4 \n" +
                "5 2 \n" +
                "3 1");
    }

    private static void compete() {
        Scanner sc = new Scanner(System.in);
        int numberOfTests = Integer.parseInt(sc.nextLine());

        for(int t = 0; t < numberOfTests; t++) {
            int rows = sc.nextInt();
            int seats = sc.nextInt();

            int[][] hallPlan = getHallPlan(rows, seats);

            for(int i = 0; i < hallPlan.length; i++) {
                for(int j = 0; j < hallPlan[0].length; j++) {
                    System.out.print(hallPlan[i][j]);
                    if (j < hallPlan[0].length - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
    }

    private static int[][] getHallPlan(int rows, int seats) {
        int[][] hallPlan = new int[rows][seats];

        int[] importance = new int[rows * seats];
        int importanceIndex = 0;

        for(int i = rows * seats; i >= 1; i--) {
            importance[importanceIndex++] = i;
        }

        importanceIndex = 0;

        //Top down diagonals
        for(int column = 0; column < hallPlan[0].length; column++){
            for(int i = 0, j = column; i < rows && j >= 0; i++, j--){
                hallPlan[i][j] = importance[importanceIndex++];
            }
        }

        //Bottom up diagonals
        for(int row = 1; row < rows; row++){
            for(int i = row, j = hallPlan[0].length -1; i < rows && j>=0; i++,j--){
                hallPlan[i][j] = importance[importanceIndex++];
            }
        }

        return hallPlan;
    }
}
