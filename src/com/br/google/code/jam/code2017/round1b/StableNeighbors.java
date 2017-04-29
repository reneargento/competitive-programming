package com.br.google.code.jam.code2017.round1b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 22/04/17.
 */

/**
 * You are lucky enough to own N pet unicorns.
 * Each of your unicorns has either one or two of the following kinds of hairs in its mane: red hairs, yellow hairs, and blue hairs.

 The color of a mane depends on exactly which sorts of colored hairs it contains:

 A mane with only one color of hair appears to be that color. For example, a mane with only blue hairs is blue.
 A mane with red and yellow hairs appears orange.
 A mane with yellow and blue hairs appears green.
 A mane with red and blue hairs appears violet.

 You have R, O, Y, G, B, and V unicorns with red, orange, yellow, green, blue, and violet manes, respectively.

 You have just built a circular stable with N stalls, arranged in a ring such that each stall borders two other stalls.
 You would like to put exactly one of your unicorns in each of these stalls.
 However, unicorns need to feel rare and special, so no unicorn can be next to another unicorn that shares at least one of the
 hair colors in its mane.
 For example, a unicorn with an orange mane cannot be next to a unicorn with a violet mane, since both of those manes have red hairs.
 Similarly, a unicorn with a green mane cannot be next to a unicorn with a yellow mane, since both of those have yellow hairs.

 Is it possible to place all of your unicorns? If so, provide any one arrangement.

 Input
 The first line of the input gives the number of test cases, T. T test cases follow.
 Each consists of one line with seven integers: N, R, O, Y, G, B, and V.

 Output
 For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1)
 and y is IMPOSSIBLE if it is not possible to place all the unicorns, or a string of N characters representing
 the placements of unicorns in stalls, starting at a point of your choice and reading clockwise around the circle.
 Use R to represent each unicorn with a red mane, O to represent each unicorn with an orange mane, and so on with Y, G, B, and V.
 This arrangement must obey the rules described in the statement above.

 If multiple arrangements are possible, you may print any of them.

 Limits

 1 ≤ T ≤ 100.
 3 ≤ N ≤ 1000.
 R + O + Y + G + B + V = N.
 0 ≤ Z for each Z in {R, O, Y, G, B, V}.

 Small dataset
 O = G = V = 0. (Each unicorn has only one hair color in its mane.)

 Large dataset
 No restrictions beyond the general limits. (Each unicorn may have either one or two hair colors in its mane.)

 Sample

 Input
 4
 6 2 0 2 0 2 0
 3 1 0 2 0 0 0
 6 2 0 1 1 2 0
 4 0 0 2 0 0 2

 Output
 Case #1: RYBRBY
 Case #2: IMPOSSIBLE
 Case #3: YBRGRB
 Case #4: YVYV

 Note that the last two sample cases would not appear in the Small dataset.

 For sample case #1, there are many possible answers; for example, another is BYBRYR.
 Note that BYRYRB would not be a valid answer; remember that the stalls form a ring, and the first touches the last!

 In sample case #2, there are only three stalls, and each stall is a neighbor of the other two, so the two unicorns
 with yellow manes would have to be neighbors, which is not allowed.

 For sample case #3, note that arranging the unicorns in the same color pattern as the Google logo (BRYBGR) would not be valid,
 since a unicorn with a blue mane would be a neighbor of a unicorn with a green mane, and both of those manes share blue hairs.

 In sample case #4, no two unicorns with yellow manes can be neighbors, and no two unicorns with violet manes can be neighbors.
 */
public class StableNeighbors {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/2017/Round 1B/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "stable_neighbors_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "stable_neighbors_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "stable_neighbors_small_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "stable_neighbors_small_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "stable_neighbors_large_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "stable_neighbors_large_output.txt";

    public static void main(String[] args) {

        test();
        //compete();
    }

    private static void test() {
        int numberOfStalls1 = 6;
        int[] unicorns1 = {2, 0, 2, 0, 2, 0};
        System.out.println(getRingArrangement(numberOfStalls1, unicorns1) + " Expected: RYBRBY");

        int numberOfStalls2 = 3;
        int[] unicorns2 = {1, 0, 2, 0, 0, 0};
        System.out.println(getRingArrangement(numberOfStalls2, unicorns2) + " Expected: IMPOSSIBLE");

        int numberOfStalls3 = 6;
        int[] unicorns3 = {2, 0, 1, 1, 2, 0};
        System.out.println(getRingArrangement(numberOfStalls3, unicorns3) + " Expected: YBRGRB");

        int numberOfStalls4 = 4;
        int[] unicorns4 = {0, 0, 2, 0, 0, 2};
        System.out.println(getRingArrangement(numberOfStalls4, unicorns4) + " Expected: YVYV");

        int numberOfStalls5 = 4;
        int[] unicorns5 = {2, 0, 1, 0, 1, 0};
        System.out.println(getRingArrangement(numberOfStalls5, unicorns5) + " Expected: RYRB");
    }

    private static void compete() {
        List<String> unicornInformation = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int i=0; i < unicornInformation.size(); i++) {

            String[] values = unicornInformation.get(i).split(" ");
            int numberOfStalls = Integer.parseInt(values[0]);
            int[] unicorns = new int[6];

            for(int j=0; j < 6; j++) {
                unicorns[j] = Integer.parseInt(values[j + 1]);
            }

            String ringArrangement = getRingArrangementSmall(numberOfStalls, unicorns);

            output.add("Case #" + caseIndex + ": " + ringArrangement);
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }




    private static String getRingArrangement(int numberOfStalls, int[] unicorns) {

        StringBuilder ring = new StringBuilder();

        int numberOfRedHairs = unicorns[0];
        int numberOfOrangeHairs = unicorns[1];
        int numberOfYellowHairs = unicorns[2];
        int numberOfGreenHairs = unicorns[3];
        int numberOfBlueHairs = unicorns[4];
        int numberOfVioletHairs = unicorns[5];

        if(numberOfRedHairs > 0) {
            ring.append("R");
            numberOfRedHairs--;
        } else if(numberOfYellowHairs > 0) {
            ring.append("Y");
            numberOfYellowHairs--;
        } else if(numberOfBlueHairs > 0) {
            ring.append("B");
            numberOfBlueHairs--;
        } else if(numberOfVioletHairs > 0) {
            ring.append("V");
            numberOfVioletHairs--;
        } else if(numberOfOrangeHairs > 0) {
            ring.append("O");
            numberOfOrangeHairs--;
        } else if(numberOfGreenHairs > 0) {
            ring.append("G");
            numberOfGreenHairs--;
        }

        for(int i=1; i < numberOfStalls; i++) {
            boolean unicornAdded = false;

            if(ring.charAt(i - 1) == 'R') {
                if(numberOfGreenHairs > 0) {
                    ring.append("G");
                    numberOfGreenHairs--;
                    unicornAdded = true;
                } else if(numberOfYellowHairs > 0 && numberOfYellowHairs >= numberOfBlueHairs) {
                    ring.append("Y");
                    numberOfYellowHairs--;
                    unicornAdded = true;
                } else if(numberOfBlueHairs > 0) {
                    ring.append("B");
                    numberOfBlueHairs--;
                    unicornAdded = true;
                }
            } else if(ring.charAt(i - 1) == 'Y') {
                if(numberOfVioletHairs > 0) {
                    ring.append("V");
                    numberOfVioletHairs--;
                    unicornAdded = true;
                } else if(numberOfBlueHairs > 0 && numberOfBlueHairs > numberOfRedHairs) {
                    ring.append("B");
                    numberOfBlueHairs--;
                    unicornAdded = true;
                } else if(numberOfRedHairs > 0) {
                    ring.append("R");
                    numberOfRedHairs--;
                    unicornAdded = true;
                }
            } else if(ring.charAt(i - 1) == 'B') {
                if(numberOfOrangeHairs > 0) {
                    ring.append("O");
                    numberOfOrangeHairs--;
                    unicornAdded = true;
                } else if(numberOfRedHairs > 0 && numberOfRedHairs > numberOfYellowHairs
                        && numberOfRedHairs > numberOfOrangeHairs) {
                    ring.append("R");
                    numberOfRedHairs--;
                    unicornAdded = true;
                } else if(numberOfYellowHairs > 0 && numberOfYellowHairs > numberOfOrangeHairs) {
                    ring.append("Y");
                    numberOfYellowHairs--;
                    unicornAdded = true;
                }
            } else if(ring.charAt(i - 1) == 'O') {
                if(numberOfBlueHairs > 0) {
                    ring.append("B");
                    numberOfBlueHairs--;
                    unicornAdded = true;
                }
            } else if(ring.charAt(i - 1) == 'G') {
                if(numberOfRedHairs > 0) {
                    ring.append("R");
                    numberOfRedHairs--;
                    unicornAdded = true;
                }
            } else if(ring.charAt(i - 1) == 'V') {
                if(numberOfYellowHairs > 0) {
                    ring.append("Y");
                    numberOfYellowHairs--;
                    unicornAdded = true;
                }
            }

            if(!unicornAdded) {
                return "IMPOSSIBLE";
            }
        }

        boolean impossible = false;

        if(ring.charAt(0) == 'R') {
            if(ring.charAt(ring.length() - 1) == 'R'
                    || ring.charAt(ring.length() - 1) == 'O'
                    || ring.charAt(ring.length() - 1) == 'V') {
                impossible = true;
            }
        } else if(ring.charAt(0) == 'B') {
            if(ring.charAt(ring.length() - 1) == 'B'
                    || ring.charAt(ring.length() - 1) == 'G'
                    || ring.charAt(ring.length() - 1) == 'V') {
                impossible = true;
            }
        } else if(ring.charAt(0) == 'Y') {
            if(ring.charAt(ring.length() - 1) == 'Y'
                    || ring.charAt(ring.length() - 1) == 'O'
                    || ring.charAt(ring.length() - 1) == 'G') {
                impossible = true;
            }
        } else if(ring.charAt(0) == 'O') {
            if(ring.charAt(ring.length() - 1) == 'O'
                    || ring.charAt(ring.length() - 1) == 'R'
                    || ring.charAt(ring.length() - 1) == 'Y'
                    || ring.charAt(ring.length() - 1) == 'V'
                    || ring.charAt(ring.length() - 1) == 'G') {
                impossible = true;
            }
        } else if(ring.charAt(0) == 'G') {
            if(ring.charAt(ring.length() - 1) == 'G'
                    || ring.charAt(ring.length() - 1) == 'B'
                    || ring.charAt(ring.length() - 1) == 'Y'
                    || ring.charAt(ring.length() - 1) == 'O'
                    || ring.charAt(ring.length() - 1) == 'V') {
                impossible = true;
            }
        } else if(ring.charAt(0) == 'V') {
            if(ring.charAt(ring.length() - 1) == 'V'
                    || ring.charAt(ring.length() - 1) == 'R'
                    || ring.charAt(ring.length() - 1) == 'B'
                    || ring.charAt(ring.length() - 1) == 'O'
                    || ring.charAt(ring.length() - 1) == 'G') {
                impossible = true;
            }
        }

        if(impossible) {
            return "IMPOSSIBLE";
        }

        return ring.toString();
    }

    private static String getRingArrangementSmall(int numberOfStalls, int[] unicorns) {

        StringBuilder ring = new StringBuilder();

        int numberOfRedHairs = unicorns[0];
        int numberOfYellowHairs = unicorns[2];
        int numberOfBlueHairs = unicorns[4];

        if(numberOfRedHairs > 0) {
            ring.append("R");
            numberOfRedHairs--;
        } else if(numberOfYellowHairs > 0) {
            ring.append("Y");
            numberOfYellowHairs--;
        } else if(numberOfBlueHairs > 0) {
            ring.append("B");
            numberOfBlueHairs--;
        }

        for(int i=1; i < numberOfStalls; i++) {
            boolean unicornAdded = false;

            if(ring.charAt(i - 1) == 'R') {
                if(numberOfYellowHairs > 0 && numberOfYellowHairs >= numberOfBlueHairs) {
                    ring.append("Y");
                    numberOfYellowHairs--;
                    unicornAdded = true;
                } else if(numberOfBlueHairs > 0) {
                    ring.append("B");
                    numberOfBlueHairs--;
                    unicornAdded = true;
                }
            } else if(ring.charAt(i - 1) == 'Y') {
                if(numberOfBlueHairs > 0 && numberOfBlueHairs > numberOfRedHairs) {
                    ring.append("B");
                    numberOfBlueHairs--;
                    unicornAdded = true;
                } else if(numberOfRedHairs > 0) {
                    ring.append("R");
                    numberOfRedHairs--;
                    unicornAdded = true;
                }
            } else if(ring.charAt(i - 1) == 'B') {
                if(numberOfRedHairs > 0 && numberOfRedHairs > numberOfYellowHairs) {
                    ring.append("R");
                    numberOfRedHairs--;
                    unicornAdded = true;
                } else if(numberOfYellowHairs > 0) {
                    ring.append("Y");
                    numberOfYellowHairs--;
                    unicornAdded = true;
                }
            }
            if(!unicornAdded) {
                return "IMPOSSIBLE";
            }
        }

        if(ring.charAt(0) == ring.charAt(ring.length() - 1)) {
            return "IMPOSSIBLE";
        }

        return ring.toString();
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> valuesList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i=1; i < lines.size(); i++) {
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
