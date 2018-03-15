package com.br.google.code.jam.women.year2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by rene on 11/03/17.
 */
public class TicketTrouble {

    private static class Ticket {
        int row;
        int column;

        Ticket(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Ticket)) {
                return false;
            }

            Ticket ticket1 = (Ticket) obj;

            return this.row == ticket1.row
                    && this.column == ticket1.column;
        }

        @Override
        public int hashCode() {
            return row * 10 + column;
        }
    }

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Women/2017/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "tickets_trouble_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "tickets_trouble_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "tickets_trouble_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "tickets_trouble_large_output.txt";

    public static void main(String[] args) {

//        Ticket[] tickets1 = new Ticket[2];
//        tickets1[0] = new Ticket(1, 2);
//        tickets1[1] = new Ticket(1, 2);
//
//        Ticket[] tickets2 = new Ticket[3];
//        tickets2[0] = new Ticket(1, 2);
//        tickets2[1] = new Ticket(2, 3);
//        tickets2[2] = new Ticket(3, 2);
//
//        Ticket[] tickets3 = new Ticket[3];
//        tickets3[0] = new Ticket(1, 1);
//        tickets3[1] = new Ticket(2, 2);
//        tickets3[2] = new Ticket(1, 2);
//
//        System.out.println(numberOfTicketsInSameRow(tickets1) + " Expected: 1");
//        System.out.println(numberOfTicketsInSameRow(tickets2) + " Expected: 3");
//        System.out.println(numberOfTicketsInSameRow(tickets3) + " Expected: 2");

        List<Ticket[]> ticketList = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(Ticket[] tickets : ticketList) {
            int numberOfTicketsInTheSameRow = numberOfTicketsInSameRow(tickets);
            output.add("Case #" + caseIndex + ": " + numberOfTicketsInTheSameRow);

            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static List<Ticket[]> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<Ticket[]> ticketList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            int i = 1;
            while (i < lines.size()) {
                String[] firstLine = lines.get(i).split(" ");
                int numberOfFriends = Integer.parseInt(firstLine[0]);

                Ticket[] tickets = new Ticket[numberOfFriends];
                int ticketIndex = 0;

                for(int j = i + 1; j <= i + numberOfFriends; j++) {
                    String[] rowAndColumn = lines.get(j).split(" ");

                    int row = Integer.parseInt(rowAndColumn[0]);
                    int column = Integer.parseInt(rowAndColumn[1]);

                    tickets[ticketIndex] = new Ticket(row, column);
                    ticketIndex++;
                }

                ticketList.add(tickets);
                i += numberOfFriends + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ticketList;
    }

    private static int numberOfTicketsInSameRow(Ticket[] tickets) {

        Map<Integer, Integer> rowFrequency = new HashMap<>();
        Set<Ticket> ticketsComputed = new HashSet<>();

        for(Ticket ticket : tickets) {

            //Not computing the same ticket twice
            if (ticketsComputed.contains(ticket)) {
                continue;
            }

            ticketsComputed.add(ticket);

            //Row
            int row = ticket.row;
            int column = ticket.column;

            int rowFrequency1 = 0;

            if (rowFrequency.containsKey(row)) {
                rowFrequency1 = rowFrequency.get(row);
            }

            rowFrequency1 = rowFrequency1 + 1;
            rowFrequency.put(row, rowFrequency1);

            if (row == column) {
                continue;
            }

            //Column
            int rowFrequency2 = 0;

            if (rowFrequency.containsKey(column)) {
                rowFrequency2 = rowFrequency.get(column);
            }

            rowFrequency2 = rowFrequency2 + 1;
            rowFrequency.put(column, rowFrequency2);
        }

        //Check the highest number possible
        int highestNumberOfFriends = 0;
        for(int frequency : rowFrequency.keySet()) {
            if (rowFrequency.get(frequency) > highestNumberOfFriends) {
                highestNumberOfFriends = rowFrequency.get(frequency);
            }
        }

        return highestNumberOfFriends;
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
