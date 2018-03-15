package com.br.facebook.hacker.cup.year2017.qualification;

/**
 * Created by rene on 06/01/17.
 */
//Based on https://www.facebook.com/hackercup/problem/1254819954559001/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Some progress bars fill you with anticipation.
 * Some are finished before you know it and make you wonder why there was a progress bar at all.

 Some progress bars progress at a pleasant, steady rate.
 Some are chaotic, lurching forward and then pausing for long periods.
 Some seem to slow down as they go, never quite reaching 100%.

 Some progress bars are in fact not bars at all, but circles.

 On your screen is a progress pie, a sort of progress bar that shows its progress as a sector of a circle.
 Envision your screen as a square on the plane with its bottom-left corner at (0, 0),
 and its upper-right corner at (100, 100).
 Every point on the screen is either white or black.
 Initially, the progress is 0%, and all points on the screen are white.
 When the progress percentage, P, is greater than 0%, a sector of angle (P% * 360) degrees is colored black,
 anchored by the line segment from the center of the square to the center of the top side, and proceeding clockwise.

 While you wait for the progress pie to fill in, you find yourself thinking about
 whether certain points would be white or black at different amounts of progress.

 Input
 Input begins with an integer T, the number of points you're curious about.
 For each point, there is a line containing three space-separated integers, P,
 the amount of progress as a percentage, and X and Y, the coordinates of the point.

 Output
 For the ith point, print a line containing "Case #i: " followed by the color of the point, either "black" or "white".

 Constraints
 1 ≤ T ≤ 1,000
 0 ≤ P, X, Y ≤ 100

 Whenever a point (X, Y) is queried, it's guaranteed that all points within a distance
 of 10-6 of (X, Y) are the same color as (X, Y).

 Example input
 5
 0 55 55
 12 55 55
 13 55 55
 99 99 99
 87 20 40

 Example output
 Case #1: white
 Case #2: white
 Case #3: black
 Case #4: white
 Case #5: black

 Explanation of Sample
 In the first case all of the points are white, so the point at (55, 55) is of course white.

 In the second case, (55, 55) is close to the filled-in sector of the circle, but it's still white.

 In the third case, the filled-in sector of the circle now covers (55, 55), coloring it black.
 */
public class ProgressPie {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2017/Qualification/Input - Output/";

    private static final String FILE_INPUT_PATH = PATH + "progress_pie_example_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "progress_pie_output.txt";

    //private static final String FILE_INPUT_PATH = PATH + "progress_pie_input.txt";
    //private static final String FILE_OUTPUT_PATH = PATH + "progress_pie_output_submission.txt";

    private final static String WHITE = "white";
    private final static String BLACK = "black";

    public static void main(String[] args) {
        System.out.println(isWhite(0, 55, 55) + " Expected: white");
        System.out.println(isWhite(12, 55, 55) + " Expected: white");
        System.out.println(isWhite(13, 55, 55) + " Expected: black");
        System.out.println(isWhite(99, 99, 99) + " Expected: white");
        System.out.println(isWhite(87, 20, 40) + " Expected: black");

        int[][] input = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseCheck = 1;

        for(int i = 0; i < input.length; i++) {

            int percent = input[i][0];
            int pointX = input[i][1];
            int pointY = input[i][2];

            String whiteOrBlack = isWhite(percent, pointX, pointY);
            output.add("Case #" + caseCheck + ": " + whiteOrBlack);

            caseCheck++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static String isWhite(int percent, int pointX, int pointY) {

        if (percent == 0) {
            return WHITE;
        }

        //First, check if it would be inside the full circle
        int radius = 50;
        int centerX = 50;
        int centerY = 50;

        boolean isInsideCircle = Math.pow(pointX - centerX, 2) + Math.pow(pointY - centerY, 2) <= radius * radius;
        if (!isInsideCircle) {
            return WHITE;
        }

        //1% of 360 is 3.6
        double startingAngle = 0;
        double endingAngle = 3.6 * percent;

        double pointAngle = getAngle(centerX, centerY, pointX, pointY);

        if (startingAngle <= pointAngle && pointAngle <= endingAngle) {
            return BLACK;
        } else {
            return WHITE;
        }

    }

    private static double getAngle(double centerX, double centerY, double pointX, double pointY) {
        double angle = Math.toDegrees(Math.atan2(pointX - centerX, pointY - centerY));

        //Atan2 returns a value between 0 and 180 or -1 and -180, we need to map it to 0 and 360
        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    private static int[][] readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<int[]> numbers = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);

                String[] valuesInLine = line.split(" ");
                int[] values = new int[3];

                for(int j = 0; j < 3; j++) {
                    values[j] = Integer.parseInt(valuesInLine[j]);
                }
                numbers.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[][] numberArray = new int[numbers.size()][3];

        for (int i = 0; i < numbers.size(); i++){
            numberArray[i] = numbers.get(i);
        }

        return numberArray;
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
