package com.br.codefights.marathon.april2017;

/**
 * Created by rene on 29/04/17.
 */

/**
 * A teacher told his students to line up and split into n groups by counting in the following way:
 * 1, 2, 3, ..., n - 1, n, n - 1, ..., 3, 2, 1, 2, 3, ..., and so on.
 * But some of the students were being noisy and weren't listening as their neighbors said their number,
 * so it's possible that they may have counted incorrectly.

 Given the array students, which represents how the students counted themselves, return the number of students
 who were placed in the wrong groups (compared to the correct counting order).

 Example

 For n = 4 and students = [1, 2, 3, 4, 3, 2, 1, 2, 3], the output should be
 groupingWithMistakes(n, students) = 0.

 None of the students made a mistake, so the order is absolutely correct.

 For n = 4 and students = [1, 2, 2, 3, 4, 3, 2, 1, 2], the output should be
 groupingWithMistakes(n, students) = 7.

 The third student made a mistake, after which the other students started counting incorrectly.
 So all the students after the second one were put into the wrong groups.

 Input/Output

 [time limit] 3000ms (java)
 [input] integer n

 The number of groups into which the students should be split.

 Guaranteed constraints:
 2 ≤ n ≤ 100.

 [input] array.integer students

 How the students counted themselves.

 Guaranteed constraints:
 n ≤ students.length ≤ 800,
 1 ≤ students[i] ≤ n.

 [output] integer

 The number of students who were put into the wrong groups as a result of counting errors.
 */
public class GroupingWithMistakes {

    public static void main(String[] args) {
        int n1 = 4;
        int[] students1 = {1, 2, 3, 4, 3, 2, 1, 2, 3};
        System.out.println(groupingWithMistakes(n1, students1) + " Expected: 0");

        int n2 = 4;
        int[] students2 = {1, 2, 2, 3, 4, 3, 2, 1, 2};
        System.out.println(groupingWithMistakes(n2, students2) + " Expected: 7");

        int n3 = 3;
        int[] students3 = {1, 2, 3, 2, 1, 2, 1, 2, 1, 2, 3, 2, 3, 3, 3};
        System.out.println(groupingWithMistakes(n3, students3) + " Expected: 3");

        int n4 = 10;
        int[] students4 = {1, 10, 3, 9, 7, 6, 6, 8, 9, 10, 9, 8, 1, 6, 5, 5, 1, 2, 1, 2};
        System.out.println(groupingWithMistakes(n4, students4) + " Expected: 7");

        int n5 = 5;
        int[] students5 = {1, 2, 3, 3, 5, 4, 4, 4, 1, 2, 1, 3, 5, 4, 2, 4, 1, 4, 1, 4};
        System.out.println(groupingWithMistakes(n5, students5) + " Expected: 9");

        int n6 = 2;
        int[] students6 = {1, 1};
        System.out.println(groupingWithMistakes(n6, students6) + " Expected: 1");
    }

    private static int groupingWithMistakes(int n, int[] students) {
        int[] correctPositions = new int[students.length];

        int position = 0;
        boolean increasing = true;

        for(int i = 0; i < students.length; i++) {
            if (increasing) {
                position = position + 1;

                if (position == n) {
                    increasing = false;
                }
            } else {
                position = position - 1;

                if (position == 1) {
                    increasing = true;
                }
            }

            correctPositions[i] = position;
        }

        int incorrectPositions = 0;

        for(int i = 0; i < students.length; i++) {
            if (students[i] != correctPositions[i]) {
                incorrectPositions++;
            }
        }

        return incorrectPositions;
    }

    private static int groupingWithMistakesOptimized(int n, int[] students) {

        boolean up = true;
        int ans = 0;
        int cur = 0;

        for(int i = 0; i < students.length; i++){
            if (up && cur==n){
                cur = n - 1;
                up = false;
            }else if (up) {
                cur++;
            }else if (!up && cur==1){
                cur = 2;
                up = true;
            }else if (!up){
                cur--;
            }
            if (cur != students[i])
                ans++;
        }
        return ans;
    }

}
