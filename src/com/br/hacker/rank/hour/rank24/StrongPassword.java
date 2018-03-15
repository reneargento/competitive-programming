package com.br.hacker.rank.hour.rank24;

/**
 * Created by rene on 02/11/17.
 */
// https://www.hackerrank.com/contests/hourrank-24/challenges/strong-password
public class StrongPassword {

    public static void main(String[] args) {
        System.out.println(minimumNumber(3, "Ab1"));
        System.out.println(minimumNumber(11, "#HackerRank"));
    }

    private static int minimumNumber(int length, String password) {
        // Return the minimum number of characters to make the password strong
        boolean hasUpperCaseLetter = false;
        boolean hasLowerCaseLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;

        for(int c = 0; c < password.length(); c++) {
            char currentChar = password.charAt(c);

            if (Character.isUpperCase(currentChar)) {
                hasUpperCaseLetter = true;
            }

            if (Character.isLowerCase(currentChar)) {
                hasLowerCaseLetter = true;
            }

            if (Character.isDigit(currentChar)) {
                hasDigit = true;
            }

            // Special characters: //!@#$%^&*()-+
            if (currentChar == '!'
                    || currentChar == '@'
                    || currentChar == '#'
                    || currentChar == '$'
                    || currentChar == '%'
                    || currentChar == '^'
                    || currentChar == '&'
                    || currentChar == '*'
                    || currentChar == '('
                    || currentChar == ')'
                    || currentChar == '-'
                    || currentChar == '+') {
                hasSpecialCharacter = true;
            }
        }

        int charsToAdd = 0;

        if (!hasUpperCaseLetter) {
            charsToAdd++;
        }
        if (!hasLowerCaseLetter) {
            charsToAdd++;
        }
        if (!hasDigit) {
            charsToAdd++;
        }
        if (!hasSpecialCharacter) {
            charsToAdd++;
        }

        int totalChars = password.length() + charsToAdd;

        if (totalChars < 6) {
            charsToAdd += 6 - totalChars;
        }

        return charsToAdd;
    }

}
