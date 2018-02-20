package com.br.algs.reference.datastructures;

import java.util.*;

/**
 * Created by Rene Argento on 19/02/18.
 */
public class Trie {

    private class Node {
        private Map<Character, Node> next = new HashMap<>();
        private boolean isKey;
        private int size;
    }

    private Node root = new Node();
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        Node node = getNode(root, key, 0);
        return node != null && node.isKey;
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

    public void add(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (contains(key)) {
            return;
        }

        root = add(root, key, 0);
        size++;
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

    public void delete(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (!contains(key)) {
            return;
        }

        root = delete(root, key, 0);
        size--;
    }

    private Node delete(Node node, String key, int digit) {

        node.size--;

        if (digit == key.length()) {
            node.isKey = false;
        } else {
            char nextChar = key.charAt(digit);
            Node childNode = delete(node.next.get(nextChar), key, digit + 1);

            if (childNode != null) {
                node.next.put(nextChar, childNode);
            } else {
                node.next.remove(nextChar);
            }
        }

        if (node.isKey || node.next.size() > 0) {
            return node;
        }

        return null;
    }

    public int countKeysWithPrefix(String prefix) {
        Node node = getNode(root, prefix, 0);

        if (node == null) {
            return 0;
        }

        return node.size;
    }

    // O(n lg n) due to sort - the hash map in the nodes saves memory, but does not necessarily store subsets in order.
    // So a sort is required to return the keys in order.
    public Iterable<String> keys() {
        List<String> keys = new ArrayList<>();
        collectKeys(root, new StringBuilder(), keys);

        Collections.sort(keys);

        return keys;
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }

        List<String> keysWithPrefix = new ArrayList<>();
        Node nodeWithPrefix = getNode(root, prefix, 0);
        collectKeys(nodeWithPrefix, new StringBuilder(prefix), keysWithPrefix);

        Collections.sort(keysWithPrefix);

        return keysWithPrefix;
    }

    private void collectKeys(Node node, StringBuilder prefix, List<String> keys) {
        if (node == null) {
            return;
        }

        if (node.isKey) {
            keys.add(prefix.toString());
        }

        for (Character character : node.next.keySet()) {
            prefix.append(character);
            collectKeys(node.next.get(character), prefix, keys);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public Iterable<String> keysThatMatch(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }

        List<String> keysThatMatch = new ArrayList<>();
        collectKeys(root, new StringBuilder(), pattern, keysThatMatch);

        Collections.sort(keysThatMatch);

        return keysThatMatch;
    }

    private void collectKeys(Node node, StringBuilder prefix, String pattern, List<String> keys) {
        if (node == null) {
            return;
        }

        int digit = prefix.length();
        if (digit == pattern.length() && node.isKey) {
            keys.add(prefix.toString());
        }

        if (digit == pattern.length()) {
            return;
        }

        char nextCharInPattern = pattern.charAt(digit);

        for (Character character : node.next.keySet()) {
            if (nextCharInPattern == '.' || nextCharInPattern == character) {
                prefix.append(character);
                collectKeys(node.next.get(character), prefix, pattern, keys);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }

    public String longestPrefixOf(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }

        int length = search(root, query, 0, 0);
        return query.substring(0, length);
    }

    private int search(Node node, String query, int digit, int length) {
        if (node == null) {
            return length;
        }

        if (node.isKey) {
            length = digit;
        }

        if (digit == query.length()) {
            return length;
        }

        char nextChar = query.charAt(digit);
        return search(node.next.get(nextChar), query, digit + 1, length);
    }

    @Override
    public String toString() {
        StringJoiner keys = new StringJoiner(", ");

        for(String key : keys()) {
            keys.add(key);
        }

        return "{ " + keys.toString() + " }";
    }

}
