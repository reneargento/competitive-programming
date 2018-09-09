package com.br.facebook.hacker.cup.year2018.round1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 21/07/18.
 */
public class EthanTraversesATree {

    private static class Node {
        private Node left;
        private Node right;
        private int value;

        Node(int value) {
            this.value = value;
        }
    }

    private static class UnionFind {

        private int[] leaders;
        private int[] ranks;

        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                ranks[i] = 0;
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        //O(inverse Ackermann function)
        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }

            return leaders[site] = find(leaders[site]);
        }

        //O(inverse Ackermann function)
        public void union(int site1, int site2) {

            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
            } else {
                leaders[leader1] = leader2;
                ranks[leader2]++;
            }

            components--;
        }

    }

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2018/Round 1/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "ethan_traverses_a_tree_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "ethan_traverses_a_tree_sample_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "ethan_traverses_a_tree_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "ethan_traverses_a_tree_output.txt";

    public static void main(String[] args) {
        //test();
        compete();
    }

    private static void test() {
        // Test 1
        Node node11 = new Node(1);
        Node node12 = new Node(2);
        node11.right = node12;
        String labels1 = getLabels(node11, 2, 1 );
        System.out.println(labels1 + " Expected: 1 1");

        // Test 2
        String labels2 = getLabels(node11, 2, 2);
        System.out.println(labels2 + " Expected: Impossible");

        // Test 3
        Node node31 = new Node(1);
        Node node32 = new Node(2);
        Node node33 = new Node(3);
        node31.right = node33;
        node33.left = node32;
        String labels3 = getLabels(node31, 3, 2);
        System.out.println(labels3 + " Expected: 1 1 2");

        // Test 4
        Node node41 = new Node(1);
        Node node42 = new Node(2);
        Node node43 = new Node(3);
        Node node44 = new Node(4);
        Node node45 = new Node(5);
        Node node46 = new Node(6);
        Node node47 = new Node(7);
        Node node48 = new Node(8);
        Node node49 = new Node(9);
        node41.left = node42;
        node41.right = node43;
        node42.left = node44;
        node43.right = node45;
        node44.left = node46;
        node45.right = node47;
        node46.left = node48;
        node47.right = node49;
        String labels4 = getLabels(node41, 9, 4);
        System.out.println(labels4 + " Expected: 1 2 3 4 4 2 3 1 1");

        // Test 5
        Node node51 = new Node(1);
        Node node52 = new Node(2);
        Node node53 = new Node(3);
        Node node54 = new Node(4);
        Node node55 = new Node(5);
        Node node56 = new Node(6);
        Node node57 = new Node(7);
        Node node58 = new Node(8);
        Node node59 = new Node(9);
        Node node510 = new Node(10);
        Node node511 = new Node(11);
        Node node512 = new Node(12);
        Node node513 = new Node(13);
        Node node514 = new Node(14);
        Node node515 = new Node(15);
        node51.left = node58;
        node53.right = node59;
        node55.left = node515;
        node55.right = node56;
        node57.left = node54;
        node58.left = node52;
        node58.right = node513;
        node510.left = node514;
        node510.right = node512;
        node511.left = node55;
        node512.left = node53;
        node513.left = node510;
        node513.right = node511;
        node515.right = node57;
        String labels5 = getLabels(node51, 15, 4);
        System.out.println(labels5 + " Expected: 1 1 2 3 4 1 2 3 1 3 1 3 2 3 1");

        // Test 6
        Node node61 = new Node(1);
        Node node62 = new Node(2);
        Node node63 = new Node(3);
        Node node64 = new Node(4);
        Node node65 = new Node(5);
        Node node66 = new Node(6);
        Node node67 = new Node(7);
        Node node68 = new Node(8);
        Node node69 = new Node(9);
        Node node610 = new Node(10);
        Node node611 = new Node(11);
        Node node612 = new Node(12);
        Node node613 = new Node(13);
        Node node614 = new Node(14);
        Node node615 = new Node(15);
        node61.left = node68;
        node61.right = node611;
        node63.right = node69;
        node65.left = node615;
        node65.right = node66;
        node67.left = node64;
        node68.left = node62;
        node68.right = node613;
        node610.left = node614;
        node610.right = node612;
        node611.left = node65;
        node612.left = node63;
        node613.left = node610;
        node615.right = node67;
        String labels6 = getLabels(node61, 15, 6);
        System.out.println(labels6 + " Expected: Impossible");

        // Test 7
        Node node71 = new Node(1);
        Node node72 = new Node(2);
        Node node73 = new Node(3);
        Node node74 = new Node(4);
        Node node75 = new Node(5);
        Node node76 = new Node(6);
        Node node77 = new Node(7);
        node71.left = node72;
        node71.right = node73;
        node72.left = node74;
        node72.right = node75;
        node73.left = node76;
        node73.right = node77;
        String labels7 = getLabels(node71, 7, 2 );
        System.out.println(labels7 + " Expected: Impossible");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size();) {
            String[] line = lines.get(i).split(" ");
            i++;
            int totalLabels = Integer.parseInt(line[0]);
            int maxValue = Integer.parseInt(line[1]);
            Node[] nodes = new Node[totalLabels + 1];

            for (int l = 1; l <= totalLabels; l++) {
                nodes[l] = new Node(l);
            }

            for (int l = 1; l <= totalLabels; l++, i++) {
                String[] children = lines.get(i).split(" ");
                int leftChild = Integer.parseInt(children[0]);
                int rightChild = Integer.parseInt(children[1]);

                if (leftChild != 0) {
                    nodes[l].left = nodes[leftChild];
                }
                if (rightChild != 0) {
                    nodes[l].right = nodes[rightChild];
                }
            }

            String finalLabels = getLabels(nodes[1], totalLabels, maxValue);

            output.add("Case #" + caseId + ": " + finalLabels);
            caseId++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static int preOrderIndex;
    private static int postOrderIndex;
    private static final String IMPOSSIBLE = "Impossible";

    private static String getLabels(Node root, int totalLabels, int maxValue) {
        int[] indexesPreOrder = new int[totalLabels];
        int[] indexesPostOrder = new int[totalLabels];
        int[] finalLabels = new int[totalLabels + 1];
        boolean allLabelsUsed = false;
        preOrderIndex = 0;
        postOrderIndex = 0;

        preOrderTraversal(root, indexesPreOrder);
        postOrderTraversal(root, indexesPostOrder);

        UnionFind unionFind = new UnionFind(totalLabels + 1);

        for (int i = 0; i < indexesPreOrder.length; i++) {
            int index1 = indexesPreOrder[i];
            int index2 = indexesPostOrder[i];

            if (index1 != index2 && !unionFind.connected(index1, index2)) {
                unionFind.union(index1, index2);
            }
        }

        int currentLabel = 1;
        for (int i = 1; i < unionFind.leaders.length; i++) {
            if (finalLabels[unionFind.find(i)] == 0) {
                finalLabels[unionFind.find(i)] = currentLabel;
                finalLabels[i] = currentLabel;

                if (currentLabel < maxValue) {
                    currentLabel++;
                } else {
                    allLabelsUsed = true;
                }
            } else {
                finalLabels[i] = finalLabels[unionFind.leaders[i]];
            }
        }

        if (!allLabelsUsed) {
            return IMPOSSIBLE;
        }

        StringJoiner labels = new StringJoiner(" ");
        for (int i = 1; i < finalLabels.length; i++) {
            labels.add(String.valueOf(finalLabels[i]));
        }

        return labels.toString();
    }

    private static void preOrderTraversal(Node node, int[] indexes) {
        if (node == null) {
            return;
        }

        indexes[preOrderIndex++] = node.value;

        if (node.left != null) {
            preOrderTraversal(node.left, indexes);
        }

        if (node.right != null) {
            preOrderTraversal(node.right, indexes);
        }
    }

    private static void postOrderTraversal(Node node, int[] indexes) {
        if (node == null) {
            return;
        }

        if (node.left != null) {
            postOrderTraversal(node.left, indexes);
        }

        if (node.right != null) {
            postOrderTraversal(node.right, indexes);
        }

        indexes[postOrderIndex++] = node.value;
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
