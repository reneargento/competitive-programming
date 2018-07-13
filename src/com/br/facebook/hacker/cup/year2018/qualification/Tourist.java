package com.br.facebook.hacker.cup.year2018.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 07/07/18.
 */
public class Tourist {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2018/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "tourist_example_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "tourist_example_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "tourist_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "tourist_output.txt";

    private static class Attraction implements Comparable<Attraction> {
        int id;
        String attraction;

        Attraction(int id, String attraction) {
            this.id = id;
            this.attraction = attraction;
        }

        @Override
        public int compareTo(Attraction other) {
            return this.id - other.id;
        }
    }

    public static void main(String[] args) {
        compete();
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] information = lines.get(i).split(" ");
            int attractionsNumber = Integer.parseInt(information[0]);
            int numberToVisit = Integer.parseInt(information[1]);
            long visitId = Long.parseLong(information[2]);
            String[] attractions = new String[attractionsNumber];

            for (int j = 0; j < attractions.length; j++) {
                i++;
                attractions[j] = lines.get(i);
            }

            String listOfAttractions = getAttractions(attractions, numberToVisit, visitId);

            output.add("Case #" + caseId + ": " + listOfAttractions);
            caseId++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static String getAttractions(String[] attractions, int numberToVisit, long visitId) {
        Attraction[] attractionsToVisit = new Attraction[numberToVisit];
        int attractionsToVisitId = 0;

        int startAttraction = (int) ((numberToVisit * (visitId - 1)) % attractions.length);

        for (int i = startAttraction; i < attractions.length && attractionsToVisitId < attractionsToVisit.length; i++) {
            attractionsToVisit[attractionsToVisitId++] = new Attraction(i, attractions[i]);
        }

        int attractionId = 0;
        while (attractionsToVisitId < attractionsToVisit.length) {
            attractionsToVisit[attractionsToVisitId++] = new Attraction(attractionId, attractions[attractionId++]);
        }

        Arrays.sort(attractionsToVisit);
        StringBuilder listOfAttractions = new StringBuilder();

        for (int i = 0; i < attractionsToVisit.length; i++) {
            listOfAttractions.append(attractionsToVisit[i].attraction);

            if (i != attractionsToVisit.length - 1) {
                listOfAttractions.append(" ");
            }
        }

        return listOfAttractions.toString();
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
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
