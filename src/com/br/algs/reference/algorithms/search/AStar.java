package com.br.algs.reference.algorithms.search;

import java.util.*;

/**
 * Created by rene on 18/04/17.
 */
public class AStar {

    private class Node implements Comparable<Node>{
        private String state;
        private int fScore;
        private int gScore;
        private Node parent;

        Node(String state) {
            this.state = state;
        }

        @Override
        public int compareTo(Node that) {
            return this.fScore - that.fScore;
        }

        @Override
        public boolean equals(Object that) {
            if(this == that) {
                return true;
            }

            if(!(that instanceof Node)) {
                return false;
            }

            return this.state.equals(((Node) that).state);
        }

        @Override
        public int hashCode() {
            return 31 * 17 + state.hashCode();
        }
    }

    private static final String END_STATE = "END_STATE";
    private static long statesEvaluated;
    private static long numberOfMoves;

    private List<String> solvePuzzle(int[][] grid) {

        long totalStatesEvaluated = 1;
        String initialState = getState(grid);

        Node sourceNode = new Node(initialState);
        sourceNode.gScore = 0;
        sourceNode.fScore = getHeuristicValue(initialState);

        // The set of currently discovered nodes that are not evaluated yet.
        // Initially, only the start node is known.
        PriorityQueue<Node> openSet = new PriorityQueue<>(10);
        openSet.add(sourceNode);

        // The set of nodes already evaluated.
        Set<Node> closedSet = new HashSet<>();

        Node current = openSet.poll();

        while (current != null && !current.state.equals(END_STATE)) {

            totalStatesEvaluated++;

            closedSet.add(current);

            for(Node neighbor : getNeighbors(current)) {

                if(closedSet.contains(neighbor)) {
                    continue;
                }

                neighbor.parent = current;

                // The distance (number of moves) from start to the neighbor
                neighbor.gScore = current.gScore + 1;
                // cost = number of moves + heuristic
                neighbor.fScore = neighbor.gScore + getHeuristicValue(neighbor.state);

                openSet.add(neighbor);
            }

            current = openSet.poll();
        }

        if(current == null) {
            return null;
        }

        statesEvaluated = totalStatesEvaluated;
        numberOfMoves = current.gScore;
        return getSolution(current);
    }

    private String getState(int[][] grid) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i < grid.length; i++) {
            for(int j=0; j < grid[0].length; j++) {
                stringBuilder.append(grid[i][j]);
            }
        }

        return stringBuilder.toString();
    }

    //Return the node neighbors, according to the problem statement
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        return neighbors;
    }

    //Compute value to be added using an heuristic such as manhattan distance, sum of square distances, etc
    private int getHeuristicValue(String state) {
        return getManhattanDistance(state);
    }

    private int getManhattanDistance(String state) {
        int manhattanDistance = 0;

        int[][] grid = new int[3][3];
        int stateStringIndex = 0;

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                grid[i][j] = state.charAt(stateStringIndex++) - '0';
            }
        }

        //A number correct position in the grid is given by:
        //If number % 3 != 0
        //  correct position row = number / 3
        //  correct position column = number % 3 - 1
        //else
        //  correct position row = number / 3 - 1
        //  correct position column = 2
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 0) {
                    manhattanDistance += Math.abs(2 - i) + Math.abs(2 - j);;
                    continue;
                }

                int correctRow;
                int correctColumn;

                if(grid[i][j] % 3 != 0) {
                    correctRow = grid[i][j] / 3;
                    correctColumn = grid[i][j] % 3 - 1;
                } else {
                    correctRow = grid[i][j] / 3 - 1;
                    correctColumn = 2;
                }

                int distance = Math.abs(correctRow - i) + Math.abs(correctColumn - j);
                manhattanDistance += distance;
            }
        }

        return manhattanDistance;
    }

    private List<String> getSolution(Node node) {
        List<String> solution = new ArrayList<>();

        while (node != null) {
            solution.add(node.state);
            node = node.parent;
        }

        List<String> orderedSolution = new ArrayList<>();

        for(int i = solution.size() - 1; i >= 0; i--) {
            orderedSolution.add(solution.get(i));
        }

        return orderedSolution;
    }

    private void printSolution(List<String> states) {
        for(String state : states) {
            int index = 0;

            for(int i=0; i < 3; i++) {
                for(int j=0; j < 3; j++) {
                    System.out.print(state.charAt(index++));
                }
                System.out.println();
            }

            System.out.println();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
