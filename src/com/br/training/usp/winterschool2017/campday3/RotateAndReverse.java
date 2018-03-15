package com.br.training.usp.winterschool2017.campday3;

import java.util.Scanner;

public class RotateAndReverse {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StringBuilder stringBuilder = new StringBuilder();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            if (line.equals("\n")) {
                stringBuilder.append("\n");
                continue;
            }

            char[] chars = line.toCharArray();
            String current = "";
            String specialChars = "";

            for(int i = 0; i < chars.length; i++) {

                if (Character.isLetter(chars[i])) {
                    if (!specialChars.equals("")) {
                        stringBuilder.append(specialChars);
                        specialChars = "";
                    }

                    current += chars[i];
                } else {
                    if (!current.equals("")) {
                        String newString = computeString(current.toCharArray());
                        stringBuilder.append(newString);
                        current = "";
                    }

                    specialChars += chars[i];
                }
            }

            if (!current.equals("")) {
                String newString = computeString(current.toCharArray());
                stringBuilder.append(newString);
            } else if (!specialChars.equals("")) {
                stringBuilder.append(specialChars);
            }

            stringBuilder.append("\n");
        }

        System.out.print(stringBuilder.toString());
    }

    private static String computeString(char[] chars) {

        if (chars[0] == 'O') {
            int stop = 1;
        }

        char[] newChars = new char[chars.length];

        if (chars.length % 2 == 1) {
            for(int i = 0; i < chars.length; i++) {
                if (!Character.isLetter(chars[i])) {
                    continue;
                }

                if (Character.isUpperCase(chars[i])) {
                    newChars[i] = Character.toUpperCase(chars[chars.length - 1 - i]);
                } else {
                    newChars[i] = Character.toLowerCase(chars[chars.length - 1 - i]);
                }
            }
        } else {
            int halfIndex = chars.length / 2;

            int newCharsIndex = 0;
            for(int i = halfIndex; i < chars.length; i++) {
                if (Character.isUpperCase(chars[i - halfIndex])) {
                    newChars[newCharsIndex++] = Character.toUpperCase(chars[i]);
                } else {
                    newChars[newCharsIndex++] = Character.toLowerCase(chars[i]);
                }
            }

            for(int i = 0; i < halfIndex; i++) {
                if (Character.isUpperCase(chars[halfIndex + i])) {
                    newChars[newCharsIndex++] = Character.toUpperCase(chars[i]);
                } else {
                    newChars[newCharsIndex++] = Character.toLowerCase(chars[i]);
                }
            }
        }

        return new String(newChars);
    }

}
