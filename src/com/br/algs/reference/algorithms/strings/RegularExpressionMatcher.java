package com.br.algs.reference.algorithms.strings;

import com.br.algs.reference.datastructures.Digraph;

import java.util.*;

/**
 * Created by Rene Argento on 30/03/18.
 */
public class RegularExpressionMatcher {

    private class RangeComplement {
        private char leftCharacter;
        private char rightCharacter;

        RangeComplement(char leftCharacter, char rightCharacter) {
            this.leftCharacter = leftCharacter;;
            this.rightCharacter = rightCharacter;
        }
    }

    private char[] regularExpression;  // Match transitions
    private Digraph digraph;           // Epsilon transitions
    private int numberOfStates;

    private Map<Integer, Integer> setsMatchMap;
    private Map<Integer, Set<Character>> setsComplementMap;
    private Map<Integer, List<RangeComplement>> setsComplementRangesMap;

    public RegularExpressionMatcher(String regularExpressionString) {
        // Create the nondeterministic finite automaton for the given regular expression
        Deque<Integer> operators = new ArrayDeque<>();
        regularExpression = regularExpressionString.toCharArray();
        numberOfStates = regularExpression.length;

        setsMatchMap = new HashMap<>();
        setsComplementMap = new HashMap<>();
        setsComplementRangesMap = new HashMap<>();

        digraph = new Digraph(numberOfStates + 1);

        for (int i = 0; i < numberOfStates; i++) {
            int leftOperator = i;

            if (regularExpression[i] == '(' || regularExpression[i] == '|' || regularExpression[i] == '[') {
                operators.push(i);
            } else if (regularExpression[i] == ')') {
                leftOperator = handleRightParenthesis(operators, i);
            } else if (regularExpression[i] == ']') {
                leftOperator = operators.pop();
                handleSets(leftOperator, i);
            }

            if (i < numberOfStates - 1) {
                if (regularExpression[i + 1] == '*') {
                    digraph.addEdge(leftOperator, i + 1);
                    digraph.addEdge(i + 1, leftOperator);
                } else if (regularExpression[i + 1] == '+') {
                    digraph.addEdge(i + 1, leftOperator);
                }
            }

            if (regularExpression[i] == '(' || regularExpression[i] == '*' || regularExpression[i] == ')'
                    || regularExpression[i] == '+' || regularExpression[i] == '[' || regularExpression[i] == ']') {
                digraph.addEdge(i, i + 1);
            }
        }
    }

    private int handleRightParenthesis(Deque<Integer> operators, int index) {
        Set<Integer> orOperatorIndexes = new HashSet<>();

        while (regularExpression[operators.peek()] == '|') {
            int or = operators.pop();
            orOperatorIndexes.add(or);
        }

        int leftOperator = operators.pop();

        for (int orOperatorIndex : orOperatorIndexes) {
            digraph.addEdge(orOperatorIndex, index);
            digraph.addEdge(leftOperator, orOperatorIndex + 1);
        }

        return leftOperator;
    }

    private void handleSets(int leftSquareBracket, int index) {
        boolean isComplementSet = false;
        Set<Character> charactersToComplement = null;
        List<RangeComplement> rangesToComplement = null;

        // Handle complement set descriptors
        // If it is a complement operator, put all characters in a set to optimize the recognition later
        if (regularExpression[leftSquareBracket + 1] == '^') {
            isComplementSet = true;
            leftSquareBracket++; // No need to check this character in the next loop
            charactersToComplement = new HashSet<>();
            rangesToComplement = new ArrayList<>();

            for (int indexInsideBrackets = leftSquareBracket + 1; indexInsideBrackets < index; indexInsideBrackets++) {
                if (regularExpression[indexInsideBrackets + 1] == '-') {
                    char leftCharacter = regularExpression[indexInsideBrackets];
                    char rightCharacter = regularExpression[indexInsideBrackets + 2];

                    rangesToComplement.add(new RangeComplement(leftCharacter, rightCharacter));
                    indexInsideBrackets += 2;
                } else {
                    charactersToComplement.add(regularExpression[indexInsideBrackets]);
                }
            }
        }

        // Handle all set-of-character descriptors
        for (int indexInsideBrackets = leftSquareBracket + 1; indexInsideBrackets < index; indexInsideBrackets++) {
            digraph.addEdge(leftSquareBracket, indexInsideBrackets);

            // If a match occurs while checking the characters in this set, the DFA will go to
            // the right square bracket state.
            setsMatchMap.put(indexInsideBrackets, index);

            if (isComplementSet) {
                setsComplementMap.put(indexInsideBrackets, charactersToComplement);
                if (rangesToComplement.size() > 0) {
                    setsComplementRangesMap.put(indexInsideBrackets, rangesToComplement);
                }
            }

            // If it as a range, there is no need to process the next 2 characters
            if (regularExpression[indexInsideBrackets + 1] == '-') {
                indexInsideBrackets += 2;
            }
        }
    }

    public boolean recognizes(String text) {
        Set<Integer> allPossibleStates = new HashSet<>();
        DirectedDFS directedDFS = new DirectedDFS(digraph, 0);

        for (int vertex = 0; vertex < digraph.vertices(); vertex++) {
            if (directedDFS.marked(vertex)) {
                allPossibleStates.add(vertex);
            }
        }

        for (int i = 0; i < text.length(); i++) {
            // Compute possible NFA states for text[i + 1]
            Set<Integer> states = new HashSet<>();

            for (int vertex : allPossibleStates) {
                if (vertex < numberOfStates) {
                    if (setsMatchMap.containsKey(vertex)) {
                        recognizeSet(text, i, vertex, states);
                    } else if (regularExpression[vertex] == text.charAt(i) || regularExpression[vertex] == '.') {
                        states.add(vertex + 1);
                    }
                }
            }

            allPossibleStates = new HashSet<>();
            directedDFS = new DirectedDFS(digraph, states);

            for (int vertex = 0; vertex < digraph.vertices(); vertex++) {
                if (directedDFS.marked(vertex)) {
                    allPossibleStates.add(vertex);
                }
            }

            // Optimization if no states are reachable
            if (allPossibleStates.size() == 0) {
                return false;
            }
        }

        for (int vertex : allPossibleStates) {
            if (vertex == numberOfStates) {
                return true;
            }
        }

        return false;
    }

    private void recognizeSet(String text, int index, int vertex, Set<Integer> states) {
        int indexOfRightSquareBracket = setsMatchMap.get(vertex);

        // Is it a range?
        if (regularExpression[vertex + 1] == '-') { // No need to worry about out of bounds indexes
            char leftRangeIndex = regularExpression[vertex];
            char rightRangeIndex = regularExpression[vertex + 2];

            if (leftRangeIndex <= text.charAt(index) && text.charAt(index) <= rightRangeIndex) {
                if (!isCharPartOfComplementSet(text, index, vertex)) {
                    states.add(indexOfRightSquareBracket);
                }
            } else if (setsComplementMap.containsKey(vertex) && !isCharPartOfComplementSet(text, index, vertex)) {
                states.add(indexOfRightSquareBracket);
            }
        } else if (regularExpression[vertex] == text.charAt(index) || regularExpression[vertex] == '.') {
            if (!isCharPartOfComplementSet(text, index, vertex)) {
                states.add(indexOfRightSquareBracket);
            }
        } else if (setsComplementMap.containsKey(vertex) && !isCharPartOfComplementSet(text, index, vertex)) {
            states.add(indexOfRightSquareBracket);
        }
    }

    private boolean isCharPartOfComplementSet(String text, int index, int vertex) {
        // Check complements map
        if (setsComplementMap.containsKey(vertex)
                && setsComplementMap.get(vertex).contains(text.charAt(index))) {
            return true;
        }

        // Check complement ranges map
        if (setsComplementRangesMap.containsKey(vertex)) {
            for (RangeComplement rangeComplement : setsComplementRangesMap.get(vertex)) {
                if (rangeComplement.leftCharacter <= text.charAt(index)
                        && text.charAt(index) <= rangeComplement.rightCharacter) {
                    return true;
                }
            }
        }

        return false;
    }

    private class DirectedDFS {

        private boolean[] visited;

        private DirectedDFS(Digraph digraph, int source) {
            visited = new boolean[digraph.vertices()];
            dfs(digraph, source);
        }

        private DirectedDFS(Digraph digraph, Iterable<Integer> sources) {
            visited = new boolean[digraph.vertices()];

            for(int source : sources) {
                if (!visited[source]) {
                    dfs(digraph, source);
                }
            }
        }

        private void dfs(Digraph digraph, int source) {
            visited[source] = true;

            for(int neighbor : digraph.adjacent(source)) {
                if (!visited[neighbor]) {
                    dfs(digraph, neighbor);
                }
            }
        }

        private boolean marked(int vertex) {
            return visited[vertex];
        }
    }

    public static void main(String[] args) {

        String pattern1 = "RENE[^ABC]";
        RegularExpressionMatcher regularExpressionMatcher1 = new RegularExpressionMatcher(pattern1);
        String text1 = "RENED";
        boolean matches1 = regularExpressionMatcher1.recognizes(text1);
        System.out.println("Text 1 check: " + matches1 + " Expected: true");

        String text2 = "RENEA";
        boolean matches2 = regularExpressionMatcher1.recognizes(text2);
        System.out.println("Text 2 check: " + matches2 + " Expected: false");

        String text3 = "RENEC";
        boolean matches3 = regularExpressionMatcher1.recognizes(text3);
        System.out.println("Text 3 check: " + matches3 + " Expected: false");

        String pattern2 = "A[^A-Z0]+Z";
        RegularExpressionMatcher regularExpressionMatcher2 = new RegularExpressionMatcher(pattern2);
        String text4 = "AbZ";
        boolean matches4 = regularExpressionMatcher2.recognizes(text4);
        System.out.println("Text 4 check: " + matches4 + " Expected: true");

        String text5 = "AAZ";
        boolean matches5 = regularExpressionMatcher2.recognizes(text5);
        System.out.println("Text 5 check: " + matches5 + " Expected: false");

        String text6 = "A0Z";
        boolean matches6 = regularExpressionMatcher2.recognizes(text6);
        System.out.println("Text 6 check: " + matches6 + " Expected: false");

        String text7 = "AabcdeZ";
        boolean matches7 = regularExpressionMatcher2.recognizes(text7);
        System.out.println("Text 7 check: " + matches7 + " Expected: true");

        String pattern3 = "A[^A-Z0]+ZZ";
        RegularExpressionMatcher regularExpressionMatcher3 = new RegularExpressionMatcher(pattern3);
        String text8 = "AbcZZ";
        boolean matches8 = regularExpressionMatcher3.recognizes(text8);
        System.out.println("Text 8 check: " + matches8 + " Expected: true");

        String pattern4 = "A[^A-Z0]*ZZ";
        RegularExpressionMatcher regularExpressionMatcher4 = new RegularExpressionMatcher(pattern4);
        String text9 = "AZZ";
        boolean matches9 = regularExpressionMatcher4.recognizes(text9);
        System.out.println("Text 9 check: " + matches9 + " Expected: true");

        String text10 = "AJZZ";
        boolean matches10 = regularExpressionMatcher4.recognizes(text10);
        System.out.println("Text 10 check: " + matches10 + " Expected: false");

        String text11 = "Abcdef123ZZ";
        boolean matches11 = regularExpressionMatcher4.recognizes(text11);
        System.out.println("Text 11 check: " + matches11 + " Expected: true");

        String pattern5 = "A([^A-Z0]|[^a-f])+[^a-f]Z";
        RegularExpressionMatcher regularExpressionMatcher5 = new RegularExpressionMatcher(pattern5);
        String text12 = "ABgZ";
        boolean matches12 = regularExpressionMatcher5.recognizes(text12);
        System.out.println("Text 12 check: " + matches12 + " Expected: true");

        String text13 = "ABCDEFGagZ";
        boolean matches13 = regularExpressionMatcher5.recognizes(text13);
        System.out.println("Text 13 check: " + matches13 + " Expected: true");

        String text14 = "ABZ";
        boolean matches14 = regularExpressionMatcher5.recognizes(text14);
        System.out.println("Text 14 check: " + matches14 + " Expected: false");

        String text15 = "AAZafgZ";
        boolean matches15 = regularExpressionMatcher5.recognizes(text15);
        System.out.println("Text 15 check: " + matches15 + " Expected: true");

        String text16 = "AAZaffZ";
        boolean matches16 = regularExpressionMatcher5.recognizes(text16);
        System.out.println("Text 16 check: " + matches16 + " Expected: false");
    }

}
