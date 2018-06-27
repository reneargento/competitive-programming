package com.br.movile.code.challenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class iFoodRestaurants {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int instructions = scanner.nextInt();
        scanner.nextLine(); // Move to next line

        Trie trie = new Trie();

        for(int i = 0; i < instructions; i++) {
            String instruction = scanner.nextLine();

            String restaurantName = instruction.substring(instruction.indexOf(":") + 1);

            if (instruction.charAt(0) == 'a') {
                trie.add(restaurantName);
            } else {
                System.out.println(trie.countKeysWithPrefix(restaurantName));
            }
        }
    }

    private static class Trie {

        private class Node {
            private Map<Character, Node> next = new HashMap<>();
            private boolean isKey;
            private int size;
        }

        private Node root = new Node();

        public void add(String key) {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }

            root = add(root, key, 0);
        }

        private Node add(Node node, String key, int digit) {
            if (node == null) {
                node = new Node();
            }

            node.size++;

            if (digit == key.length()) {
                node.isKey = true;
                return node;
            }

            char nextChar = key.charAt(digit);

            Node nextNode = add(node.next.get(nextChar), key, digit + 1);
            node.next.put(nextChar, nextNode);
            return node;
        }

        public int countKeysWithPrefix(String prefix) {
            Node node = getNode(root, prefix, 0);

            if (node == null) {
                return 0;
            }

            return node.size;
        }

        private Node getNode(Node node, String key, int digit) {
            if (node == null) {
                return null;
            }

            if (digit == key.length()) {
                return node;
            }

            char nextChar = key.charAt(digit);
            return getNode(node.next.get(nextChar), key, digit + 1);
        }
    }

}
