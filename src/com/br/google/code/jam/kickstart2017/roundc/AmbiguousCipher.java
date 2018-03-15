package com.br.google.code.jam.kickstart2017.roundc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 25/06/17.
 */
public class AmbiguousCipher {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Kickstart 2017/Round C/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "cipher_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "cipher_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "cipher_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "cipher_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "cipher_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "cipher_large_output.txt";

    public static void main(String[] args) {
        test();
       // compete();
    }

    private static void test() {
        System.out.println(decipher("OMDU") + " Expected: SOUP");
        System.out.println(decipher("BCB") + " Expected: AMBIGUOUS");
        System.out.println(decipher("AOAAAN") + " Expected: BANANA");
    }

    private static void compete() {
        List<String> input = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int i = 0; i < input.size(); i++) {

            String encryptedString = input.get(i);
            String decryptedString = decipher(encryptedString);

            output.add("Case #" + caseIndex + ": " + decryptedString);
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static String decipher(String encryptedString) {
        if (encryptedString.length() % 2 == 1) {
            return "AMBIGUOUS";
        }

        char[] decryptedString = new char[encryptedString.length()];

        char secondLetter = encryptedString.charAt(0);
        char beforeLastLetter = encryptedString.charAt(encryptedString.length() - 1);

        decryptedString[1] = secondLetter;
        decryptedString[encryptedString.length() - 2] = beforeLastLetter;

        char currentLetter = secondLetter;

        //Left-right pass for odd positions
        for(int i = 3; i < encryptedString.length(); i+= 2) {
            int encryptedLetterValue = encryptedString.charAt(i - 1) - 'A';
            int currentLetterValue = currentLetter - 'A';

            int decryptedLetterValue = (encryptedLetterValue - currentLetterValue + 26) % 26;

            char decryptedLetter = (char) (decryptedLetterValue + 'A');
            decryptedString[i] = decryptedLetter;

            currentLetter = decryptedLetter;
        }

        currentLetter = beforeLastLetter;

        //Right-left pass for even positions
        for(int i = encryptedString.length() - 4; i >= 0; i-= 2) {
            int encryptedLetterValue = encryptedString.charAt(i + 1) - 'A';
            int currentLetterValue = currentLetter - 'A';

            int decryptedLetterValue = (encryptedLetterValue - currentLetterValue + 26) % 26;

            char decryptedLetter = (char) (decryptedLetterValue + 'A');
            decryptedString[i] = decryptedLetter;

            currentLetter = decryptedLetter;
        }

        return new String(decryptedString);
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
