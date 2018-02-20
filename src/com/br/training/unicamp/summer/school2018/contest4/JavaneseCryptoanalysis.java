package com.br.training.unicamp.summer.school2018.contest4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 26/01/18.
 */
// TODO
public class JavaneseCryptoanalysis {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = scanner.nextLine();
        char[] allCharacters = text.toCharArray();
        char[] auxCharacters = new char[allCharacters.length];

        int charAIndex = 65;
        int charZIndex = 90;
        boolean possible = false;

        char[] vowels = {'A', 'E', 'I', 'O', 'U'};
        char[] consonants = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
            'S', 'T', 'V', 'X', 'W', 'Y', 'Z'};

        for (int index1 = 0; index1 < consonants.length; index1++ ) {
            Map<Integer, Integer> encoding = new HashMap<>();

            for (int index2 = 0; index2 < vowels.length; index2++ ) {

                for (int index3 = 0; index3 < 26; index3++) {
                    int encryptedIndex = charAIndex + index2 + index3;

                    if (encryptedIndex > charZIndex) {
                        encryptedIndex = charAIndex + (encryptedIndex - charZIndex) - 1;
                    }

                    int originalIndex = charAIndex + index1;

                    if (originalIndex > charZIndex) {
                        originalIndex = charAIndex + (originalIndex - charZIndex) - 1;
                    }

                    encoding.put(encryptedIndex, originalIndex);
                }

                for (int character = 0; character < allCharacters.length; character++) {
                    if (allCharacters[character] == ' ') {
                        auxCharacters[character] = ' ';
                        continue;
                    }

                    int encryptedIndex = (int) allCharacters[character];
                    auxCharacters[character] = (char) (int) encoding.get(encryptedIndex);
                }

                if (isValidText(auxCharacters)) {
                    possible = true;
                    break;
                }
            }

            if (possible) {
                break;
            }
        }

        if (!possible) {
            System.out.println("impossible");
        } else {
            for(int i = 0; i < auxCharacters.length; i++) {
                System.out.print(auxCharacters[i]);
            }
            System.out.println();
        }
    }

    private static boolean isValidText(char[] allCharacters) {
        boolean isLastDigitVowel = false;
        boolean isLastDigitConsonant = false;

        for(int i = 0; i < allCharacters.length; i++) {
            if (allCharacters[i] == ' ') {
                isLastDigitVowel = false;
                isLastDigitConsonant = false;
                continue;
            }

            if (isVowel(allCharacters[i])) {
                if (isLastDigitVowel) {
                    return false;
                }
                isLastDigitVowel = true;
                isLastDigitConsonant = false;
            } else {
                if (isLastDigitConsonant) {
                    return false;
                }
                isLastDigitConsonant = true;
                isLastDigitVowel = false;
            }
        }

        return true;
    }

    private static boolean isVowel(char character) {
        if (character == 'A'
                || character == 'E'
                || character == 'I'
                || character == 'O'
                || character == 'U') {
            return true;
        }

        return false;
    }

    private static List<String> readFileInput(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
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
