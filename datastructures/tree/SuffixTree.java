package datastructures.tree;

import java.util.*;

/**
 * Created by Rene Argento on 01/10/26.
 */
public class SuffixTree {

    private static final int FIRST_TERMINAL = 65536;

    private static class Node {
        int start;
        int[] end;
        Node suffixLink;

        Map<Integer, Node> children = new HashMap<>();

        Node(int start, int[] end) {
            this.start = start;
            this.end = end;
        }

        int edgeLength() {
            return end[0] - start + 1;
        }
    }

    /*
     * The complete generalized text.
     * Java chars:
     *     0 .. 65535
     *
     * Unique terminal symbols:
     *     65536, 65537, ...
     */
    private final List<Integer> text = new ArrayList<>();

    private final Node root;

    /*
     * Each added string gets its own end object.
     *
     * All leaves created for that string point to this object.
     * When the string's terminal is reached, the object is no
     * longer modified, so those leaves remain frozen.
     */
    private int[] currentLeafEnd;

    /*
     * Ukkonen active point.
     */
    private Node activeNode;
    private int activeEdge;
    private int activeLength;

    private int remainingSuffixCount;

    /*
     * Each string receives a unique terminal.
     */
    private int nextTerminal = FIRST_TERMINAL;

    public SuffixTree() {
        root = new Node(-1, new int[]{ -1 });
        root.suffixLink = root;
        activeNode = root;
    }

    /**
     * Adds a new string to the generalized suffix tree.
     * A unique terminal is automatically appended to the string.
     */
    public void add(String string) {
        /*
         * All leaves created for this string share this end.
         *
         * It is intentionally a different object from the end used by every other string.
         */
        currentLeafEnd = new int[]{ -1 };

        /*
         * Add the actual characters.
         */
        for (int i = 0; i < string.length(); i++) {
            extend(string.charAt(i));
        }

        /*
         * Add a unique terminal.
         *
         * Because the terminal is unique, it forces all suffixes
         * of this string to become explicit and freezes its leaves.
         */
        extend(nextTerminal++);

        /*
         * The leaves belonging to this string now point to a
         * completed/frozen end object.
         */
        currentLeafEnd = null;
    }

    /**
     * One Ukkonen extension.
     */
    private void extend(int symbol) {
        text.add(symbol);
        int position = text.size() - 1;

        /*
         * Only leaves belonging to the current string use this end object.
         */
        currentLeafEnd[0] = position;
        remainingSuffixCount++;

        Node lastCreatedInternalNode = null;

        while (remainingSuffixCount > 0) {
            /*
             * If activeLength == 0, activeEdge is the current position.
             */
            if (activeLength == 0) {
                activeEdge = position;
            }

            int activeEdgeSymbol = text.get(activeEdge);
            Node next = activeNode.children.get(activeEdgeSymbol);

            if (next == null) {
                /*
                 * Rule 2:
                 * Create a new leaf.
                 */
                Node leaf = new Node(position, currentLeafEnd);
                activeNode.children.put(activeEdgeSymbol, leaf);

                /*
                 * Previous internal node gets its suffix link.
                 */
                if (lastCreatedInternalNode != null) {
                    lastCreatedInternalNode.suffixLink = activeNode;
                    lastCreatedInternalNode = null;
                }
            } else {
                /*
                 * Walk down the complete edge if possible.
                 */
                if (walkDown(next)) {
                    continue;
                }

                /*
                 * We are somewhere inside this edge.
                 * Check whether the next symbol already matches.
                 */
                int edgeSymbol = text.get(next.start + activeLength);

                if (edgeSymbol == symbol) {
                    /*
                     * Rule 3.
                     */
                    activeLength++;

                    if (lastCreatedInternalNode != null) {
                        lastCreatedInternalNode.suffixLink = activeNode;
                    }

                    /*
                     * No more suffixes need to be explicitly inserted for this phase.
                     */
                    break;
                }

                /*
                 * Rule 2:
                 * Split the existing edge.
                 */
                int[] splitEnd = { next.start + activeLength - 1 };

                Node split = new Node(next.start, splitEnd);
                activeNode.children.put(activeEdgeSymbol, split);

                /*
                 * New leaf for the current suffix.
                 */
                Node leaf = new Node(position, currentLeafEnd);
                split.children.put(symbol, leaf);

                /*
                 * Existing child now begins after the split.
                 */
                next.start += activeLength;

                split.children.put(text.get(next.start), next);

                /*
                 * Connect the previous internal node.
                 */
                if (lastCreatedInternalNode != null) {
                    lastCreatedInternalNode.suffixLink = split;
                }
                lastCreatedInternalNode = split;
            }
            remainingSuffixCount--;

            /*
             * Move the active point.
             */
            if (activeNode == root && activeLength > 0) {
                activeLength--;
                activeEdge = position - remainingSuffixCount + 1;
            } else if (activeNode != root) {
                activeNode = activeNode.suffixLink;
            }
        }
    }

    /**
     * Walk down an entire edge when activeLength covers it.
     */
    private boolean walkDown(Node next) {
        if (activeLength >= next.edgeLength()) {
            activeEdge = next.start + next.edgeLength();
            activeLength -= next.edgeLength();
            activeNode = next;
            return true;
        }
        return false;
    }

    /**
     * Returns true if pattern occurs completely inside one of
     * the strings added to this generalized suffix tree.
     * A match is not allowed to cross a terminal.
     */
    public boolean contains(String pattern) {
        if (pattern.isEmpty()) {
            return true;
        }

        Node current = root;
        int patternIndex = 0;

        while (patternIndex < pattern.length()) {
            int symbol = pattern.charAt(patternIndex);
            Node next = current.children.get(symbol);

            if (next == null) {
                return false;
            }

            int edgeIndex = next.start;
            int edgeEnd = next.end[0];

            while (edgeIndex <= edgeEnd
                    && patternIndex < pattern.length()) {
                int textSymbol = text.get(edgeIndex);

                /*
                 * A terminal marks the end of an input string.
                 * Therefore a pattern cannot continue through it.
                 */
                if (textSymbol >= FIRST_TERMINAL) {
                    return false;
                }
                if (textSymbol != pattern.charAt(patternIndex)) {
                    return false;
                }
                edgeIndex++;
                patternIndex++;
            }

            /*
             * The entire pattern has been matched, possibly in the middle of an edge.
             */
            if (patternIndex == pattern.length()) {
                return true;
            }

            /*
             * Continue with the next edge.
             */
            current = next;
        }
        return true;
    }

    /**
     * Number of strings added to the generalized suffix tree.
     */
    public int size() {
        return nextTerminal - FIRST_TERMINAL;
    }

    /**
     * Prints the tree.
     * Terminals are displayed as <T0>, <T1>, ...
     */
    public void print() {
        print(root, "");
    }

    private void print(Node node, String indent) {
        for (Node child : node.children.values()) {
            StringBuilder edge = new StringBuilder();

            for (int i = child.start; i <= child.end[0]; i++) {
                int symbol = text.get(i);
                if (symbol >= FIRST_TERMINAL) {
                    edge.append("<T").append(symbol - FIRST_TERMINAL).append(">");
                } else {
                    edge.append((char) symbol);
                }
            }
            System.out.println(indent + edge);
            print(child, indent + "  ");
        }
    }

    public static void main(String[] args) {
        SuffixTree suffixTree = new SuffixTree();
        suffixTree.add("banana");
        suffixTree.add("apple");
        suffixTree.add("orange");

        System.out.println("Contains: " + suffixTree.contains("banana"));
        System.out.println("Expected: true");

        System.out.println("Contains: " + suffixTree.contains("apple"));
        System.out.println("Expected: true");

        System.out.println("Contains: " + suffixTree.contains("orange"));
        System.out.println("Expected: true");

        System.out.println("Contains: " + suffixTree.contains("ana"));
        System.out.println("Expected: true");

        System.out.println("Contains: " + suffixTree.contains("nana"));
        System.out.println("Expected: true");

        System.out.println("Contains: " + suffixTree.contains("rang"));
        System.out.println("Expected: true");

        System.out.println("Contains: " + suffixTree.contains("app"));
        System.out.println("Expected: true");

        System.out.println("Contains: " + suffixTree.contains("nap"));
        System.out.println("Expected: false");

        System.out.println("Contains: " + suffixTree.contains("pear"));
        System.out.println("Expected: false");

        suffixTree.add("abracadabra");

        System.out.println("Contains: " + suffixTree.contains("abra"));
        System.out.println("Expected: true");

        System.out.println("Contains: " + suffixTree.contains("cad"));
        System.out.println("Expected: true");

        System.out.println("Contains: " + suffixTree.contains("pear"));
        System.out.println("Expected: false");
    }
}
