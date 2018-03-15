package com.br.codefights.marathon.may2017;

/**
 * Created by rene on 27/05/17.
 */

/**
 * The PreSuffix of two numbers a and b is a number that is the prefix of one of the given numbers and the suffix of the other number.
 * Both numbers are in decimal notation.

 Given two integers, a and b, return their maximum PreSuffix, or -1 if they don't have any PreSuffixes.

 Example

 For a = 123456 and b = 456123, the output should be
 maxPreSuffix(a, b) = 456.

 In this case, a and b have two PreSuffixes, 123 and 456. 456 is the maximum PreSuffix.

 For a = 123 and b = 456, the output should be
 maxPreSuffix(a, b) = -1.

 These two numbers don't have any PreSuffixes.

 Input/Output

 [time limit] 3000ms (java)
 [input] integer a

 Guaranteed constraints:
 1 ≤ a ≤ 109.

 [input] integer b

 Guaranteed constraints:
 1 ≤ b ≤ 109.

 [output] integer
 The maximum PreSuffix of a and b, or -1 if a PreSuffix doesn't exist.
 */
public class MaxPreSuffix {

    public static void main(String[] args) {
        System.out.println(maxPreSuffix(123456, 456123) + " Expected: " + 456);
        System.out.println(maxPreSuffix(123, 456) + " Expected: " + -1);
        System.out.println(maxPreSuffix(60012893, 28936001) + " Expected: " + 6001);
        System.out.println(maxPreSuffix(12321, 12321) + " Expected: " + 12321);
        System.out.println(maxPreSuffix(1, 111) + " Expected: " + 1);
        System.out.println(maxPreSuffix(7929218, 2111792) + " Expected: " + 792);
        System.out.println(maxPreSuffix(210129888, 189092101) + " Expected: " + 2101);
        System.out.println(maxPreSuffix(100010000, 1000000000) + " Expected: " + 10000);
    }

    private static int maxPreSuffix(int a, int b) {

        String temp;
        String result = "-1";

        String s1 = String.valueOf(a);
        String s2 = String.valueOf(b);

        int firstPreSuffix = 0;
        int secondPreSuffix = 0;

        for(int i = 0; i < s1.length(); i++) {
            temp = s1.substring(0, i + 1);

            if (s2.length() - 1 - i < 0) {
                break;
            }

            if (s2.substring(s2.length() - 1 - i, s2.length()).equals(temp)) {
                result = temp;
            }
        }

        firstPreSuffix = Integer.parseInt(result);

        for(int i = 0; i < s2.length(); i++) {
            temp = s2.substring(0, i + 1);

            if (s1.length() - 1 - i < 0) {
                break;
            }

            if (s1.substring(s1.length() - 1 - i, s1.length()).equals(temp)) {
                result = temp;
            }
        }

        secondPreSuffix = Integer.parseInt(result);

        return Math.max(firstPreSuffix, secondPreSuffix);
    }

}
