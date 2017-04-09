package com.br.templates;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 07/04/17.
 */
public class InputOutput {

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> numbersList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i=1; i < lines.size(); i++) {
                numbersList.add(lines.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numbersList;
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
