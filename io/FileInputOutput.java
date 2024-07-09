package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/04/17.
 */
public class FileInputOutput {

    // Reads all lines
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

    // Reads one line at a time (or integer)
    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(String file) throws IOException {
            reader = new BufferedReader(new FileReader(file));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private static void writeDataOnFile(String file, List<String> data) {
        for (String line : data) {
            writeFileOutput(file, line + "\n");
        }
    }

    private static void writeFileOutput(String file, String data) {
        byte[] dataBytes = data.getBytes();

        try {
            Files.write(Paths.get(file), dataBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
