package com.br.hacker.rank.rookie.rank4;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/02/18.
 */
// https://www.hackerrank.com/contests/rookierank-4/challenges/height-and-total-height-of-a-bst/problem
public class HeightAndTotalHeightBST {

    static int[] heightOfBST(int[] array) {

        BinarySearchTree<Integer, Integer> binarySearchTree = new BinarySearchTree<>();

        for (int i = 0; i < array.length; i++) {
            binarySearchTree.put(array[i], 0);
        }

        int rootHeight = binarySearchTree.height(binarySearchTree.root) - 1;
        int totalHeight = binarySearchTree.totalHeight();

        return new int[]{rootHeight, totalHeight};
    }

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int aSize = Integer.parseInt(scan.nextLine().trim());

        int[] array = new int[aSize];

        String[] aItems = scan.nextLine().split(" ");

        for (int aItr = 0; aItr < aSize; aItr++) {
            int aItem = Integer.parseInt(aItems[aItr].trim());
            array[aItr] = aItem;

        }

        int[] result = heightOfBST(array);

        for (int resultItr = 0; resultItr < result.length; resultItr++) {
            System.out.print(String.valueOf(result[resultItr]));

            if (resultItr != result.length - 1) {
                System.out.print("\n");
            }
        }

        System.out.println();
        System.out.close();
    }

    private static class BinarySearchTree<Key extends Comparable<Key>, Value> {

        private class Node {
            private Key key;
            private Value value;

            private Node left;
            private Node right;

            private int size; //# of nodes in subtree rooted here

            public Node(Key key, Value value, int size) {
                this.key = key;
                this.value = value;
                this.size = size;
            }
        }

        private Node root;

        public int size() {
            return size(root);
        }

        private int size(Node node) {
            if (node == null) {
                return 0;
            }

            return node.size;
        }

        public boolean isEmpty() {
            return size(root) == 0;
        }

        public Value get(Key key) {
            if (key == null) {
                return null;
            }

            return get(root, key);
        }

        private Value get(Node node, Key key) {
            if (node == null) {
                return null;
            }

            int compare = key.compareTo(node.key);
            if (compare < 0) {
                return get(node.left, key);
            } else if (compare > 0) {
                return get(node.right, key);
            } else {
                return node.value;
            }
        }

        public boolean contains(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Argument to contains() cannot be null");
            }
            return get(key) != null;
        }

        public void put(Key key, Value value) {
            if (key == null) {
                return;
            }

            if (contains(key)) {
                return;
            }

            root = put(root, key, value);
        }

        private Node put(Node node, Key key, Value value) {
            if (node == null) {
                return new Node(key, value, 1);
            }

            int compare = key.compareTo(node.key);

            if (compare < 0) {
                node.left = put(node.left, key, value);
            } else if (compare > 0) {
                node.right = put(node.right, key, value);
            } else {
                node.value = value;
            }

            node.size = size(node.left) + 1 + size(node.right);
            return node;
        }

        public int height(Node node) {
            if (node == null) {
                return 0;
            }

            return 1 + Math.max(height(node.left), height(node.right));
        }

        public int totalHeight() {
            if (isEmpty()) {
                return -1;
            }

            return totalHeight(root);
        }

        public int totalHeight(Node node) {
            if (node == null) {
                return 0;
            }

            return height(node) - 1 + totalHeight(node.left) + totalHeight(node.right);
        }
    }

}
