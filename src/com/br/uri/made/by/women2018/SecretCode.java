package com.br.uri.made.by.women2018;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 10/03/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/346/1
public class SecretCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Character> characterMap = new HashMap<>();

        characterMap.put(".", 'a');
        characterMap.put("..", 'b');
        characterMap.put("...", 'c');
        characterMap.put(". .", 'd');
        characterMap.put(".. ..", 'e');
        characterMap.put("... ...", 'f');
        characterMap.put(". . .", 'g');
        characterMap.put(".. .. ..", 'h');
        characterMap.put("... ... ...", 'i');
        characterMap.put(". . . .", 'j');
        characterMap.put(".. .. .. ..", 'k');
        characterMap.put("... ... ... ...", 'l');
        characterMap.put(". . . . .", 'm');
        characterMap.put(".. .. .. .. ..", 'n');
        characterMap.put("... ... ... ... ...", 'o');
        characterMap.put(". . . . . .", 'p');
        characterMap.put(".. .. .. .. .. ..", 'q');
        characterMap.put("... ... ... ... ... ...", 'r');
        characterMap.put(". . . . . . .", 's');
        characterMap.put(".. .. .. .. .. .. ..", 't');
        characterMap.put("... ... ... ... ... ... ...", 'u');
        characterMap.put(". . . . . . . .", 'v');
        characterMap.put(".. .. .. .. .. .. .. ..", 'w');
        characterMap.put("... ... ... ... ... ... ... ...", 'x');
        characterMap.put(". . . . . . . . .", 'y');
        characterMap.put(".. .. .. .. .. .. .. .. ..", 'z');

        while (scanner.hasNext()) {
            int characters = scanner.nextInt();
            scanner.nextLine();

            for(int i = 0; i < characters; i++) {
                String line = scanner.nextLine();
                System.out.println(characterMap.get(line));
            }
        }
    }

}
