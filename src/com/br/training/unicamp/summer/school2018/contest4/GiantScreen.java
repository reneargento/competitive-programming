package com.br.training.unicamp.summer.school2018.contest4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 26/01/18.
 */
public class GiantScreen {

    public static void main(String[] args) throws IOException {
        String inputFile = "giant.in";
        String outputFile = "giant.out";
        List<String> input = readFileInput(inputFile);

        String[] targetValues = input.get(0).split(" ");

        int horizontalResolutionTarget = Integer.parseInt(targetValues[0]);
        int verticalResolutionTarget = Integer.parseInt(targetValues[1]);
        int horizontalSizeTarget = Integer.parseInt(targetValues[2]);
        int verticalSizeTarget = Integer.parseInt(targetValues[3]);

        int screenTypes = Integer.parseInt(input.get(1));
        long lowestPrice = Long.MAX_VALUE;

        for (int type = 0; type < screenTypes; type++) {
            String[] values = input.get(2 + type).split(" ");

            int horizontalResolution = Integer.parseInt(values[0]);
            int verticalResolution = Integer.parseInt(values[1]);
            int horizontalSize = Integer.parseInt(values[2]);
            int verticalSize = Integer.parseInt(values[3]);

            int price = Integer.parseInt(values[4]);

            long currentHorizontalResolution = 0;
            long currentVerticalResolution = verticalResolution;
            long currentHorizontalSize = 0;
            long currentVerticalSize = verticalSize;

            // Horizontal
            long currentPrice;
            int horizontalScreensUsed = 0;
            int verticalScreensUsed = 1;

            while (currentHorizontalResolution < horizontalResolutionTarget
                    || currentHorizontalSize < horizontalSizeTarget) {
                horizontalScreensUsed++;

                currentHorizontalResolution += horizontalResolution;
                currentHorizontalSize += horizontalSize;
            }

            while (currentVerticalResolution < verticalResolutionTarget
                    || currentVerticalSize < verticalSizeTarget) {
                verticalScreensUsed++;

                currentVerticalSize += verticalSize;
                currentVerticalResolution += verticalResolution;
            }

            currentPrice = Math.max(horizontalScreensUsed, 1) * Math.max(verticalScreensUsed, 1) * price;
            if (currentPrice < lowestPrice) {
                lowestPrice = currentPrice;
            }

            // Vertical
            horizontalScreensUsed = 1;
            verticalScreensUsed = 0;

            currentHorizontalResolution = horizontalResolution;
            currentVerticalResolution = 0;
            currentHorizontalSize = horizontalSize;
            currentVerticalSize = 0;

            while (currentVerticalResolution < horizontalResolutionTarget
                    || currentVerticalSize < horizontalSizeTarget) {
                verticalScreensUsed++;

                currentVerticalResolution += verticalResolution;
                currentVerticalSize += verticalSize;
            }

            while (currentHorizontalResolution < verticalResolutionTarget
                    || currentHorizontalSize < verticalSizeTarget) {
                horizontalScreensUsed++;

                currentHorizontalResolution += horizontalResolution;
                currentHorizontalSize += horizontalSize;
            }

            currentPrice = Math.max(horizontalScreensUsed, 1) * Math.max(verticalScreensUsed, 1) * price;
            if (currentPrice < lowestPrice) {
                lowestPrice = currentPrice;
            }
        }

        List<String> output = new ArrayList<>();
        output.add(String.valueOf(lowestPrice));
        writeDataOnFile(outputFile, output);
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
