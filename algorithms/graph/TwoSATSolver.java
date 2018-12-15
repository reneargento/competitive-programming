package algorithms.graph;

import java.util.*;

/**
 * Created by rene on 26/10/17.
 */
@SuppressWarnings("unchecked")
public class TwoSATSolver {

    private class StronglyConnectedComponents {

        private int[] sccId;
        private int sccCount;
        private int[] componentSizes;
        private int vertices;

        public StronglyConnectedComponents(List<Integer>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];
            sccId = new int[adjacent.length];
            componentSizes = new int[adjacent.length];
            vertices = adjacent.length;

            List<Integer>[] inverseEdges = invertGraphEdges(adjacent);
            int[] topologicalOrder = topologicalSort(inverseEdges);

            for(int vertex : topologicalOrder) {
                if (!visited[vertex]) {
                    depthFirstSearch(vertex, adjacent, null, visited, false);
                    sccCount++;
                }
            }
        }

        public boolean stronglyConnected(int vertex1, int vertex2) {
            return sccId[vertex1] == sccId[vertex2];
        }

        public int sccId(int vertex) {
            return sccId[vertex];
        }

        public int count() {
            return sccCount;
        }

        private int[] topologicalSort(List<Integer>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];
            Stack<Integer> finishTimes = new Stack<>();

            //If the vertices are 0-index based, start i with value 0
            for(int i = 0; i < visited.length; i++) {
                if (!visited[i]) {
                    depthFirstSearch(i, adjacent, finishTimes, visited, true);
                }
            }

            int[] topologicalSort = new int[finishTimes.size()];
            int arrayIndex = 0;

            while (!finishTimes.isEmpty()) {
                topologicalSort[arrayIndex++] = finishTimes.pop();
            }

            return topologicalSort;
        }

        // Fast, but recursive
        private void depthFirstSearch(int sourceVertex, List<Integer>[] adjacent, Stack<Integer> finishTimes,
                                      boolean[] visited, boolean getFinishTimes) {
            visited[sourceVertex] = true;

            if (!getFinishTimes) {
                sccId[sourceVertex] = sccCount;
                componentSizes[sccCount]++;
            }

            if (adjacent[sourceVertex] != null) {
                for(int neighbor : adjacent[sourceVertex]) {
                    if (!visited[neighbor]) {
                        depthFirstSearch(neighbor, adjacent, finishTimes, visited, getFinishTimes);
                    }
                }
            }

            if (getFinishTimes) {
                finishTimes.push(sourceVertex);
            }
        }

        private List<Integer>[] invertGraphEdges(List<Integer>[] adjacent) {
            List<Integer>[] inverseEdges = new ArrayList[adjacent.length];

            for(int i = 0; i < inverseEdges.length; i++) {
                inverseEdges[i] = new ArrayList<>();
            }

            //If the vertices are 0-index based, start i with value 0
            for(int i = 0; i < adjacent.length; i++) {
                List<Integer> neighbors = adjacent[i];

                if (neighbors != null) {
                    for(int neighbor : adjacent[i]) {
                        inverseEdges[neighbor].add(i);
                    }
                }
            }

            return inverseEdges;
        }

        private List<Integer>[] getSCCsInReverseTopologicalOrder() {
            List<Integer>[] stronglyConnectedComponents = (List<Integer>[]) new ArrayList[sccCount];

            for(int scc = 0; scc < stronglyConnectedComponents.length; scc++) {
                stronglyConnectedComponents[scc] = new ArrayList<>();
            }

            for(int vertex = 0; vertex < vertices; vertex++) {
                int stronglyConnectedComponentId = sccId(vertex);
                stronglyConnectedComponents[stronglyConnectedComponentId].add(vertex);
            }

            return stronglyConnectedComponents;
        }

        public List<Integer>[] getSCCsInTopologicalOrder() {
            List<Integer>[] sccsInTopologicalOrder = getSCCsInReverseTopologicalOrder();

            List<Integer>[] sccsInReverseTopologicalOrder = (List<Integer>[]) new ArrayList[sccCount];
            int currentSCCInReverseOrderIndex = 0;

            for(int scc = sccsInTopologicalOrder.length - 1; scc >= 0 ; scc--) {
                sccsInReverseTopologicalOrder[currentSCCInReverseOrderIndex++] = sccsInTopologicalOrder[scc];
            }

            return sccsInReverseTopologicalOrder;
        }
    }

    public Map<Character, Boolean> solve2SAT(String formula) {

        // First pass to find the number of variables in the formula
        Set<Character> variables = new HashSet<>();
        char[] charsInFormula = formula.toCharArray();
        for(int i = 0; i < charsInFormula.length; i++) {
            if (charsInFormula[i] != '('
                    && charsInFormula[i] != ')'
                    && charsInFormula[i] != 'V'
                    && charsInFormula[i] != '^'
                    && charsInFormula[i] != ' '
                    && charsInFormula[i] != '!') {
                variables.add(charsInFormula[i]);
            }
        }

        List<Integer>[] adjacent = (List<Integer>[]) new ArrayList[variables.size() * 2];

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            adjacent[vertex] = new ArrayList<>();
        }

        String[] values = formula.split(" ");

        Map<String, Integer> variableToIdMap = new HashMap<>();
        Map<Integer, String> idToVariableMap = new HashMap<>();

        // Second pass to get vertices
        for(int i = 0; i < values.length; i += 2) {
            boolean isVariable1Negation;
            boolean isVariable2Negation;

            // Read variables
            String variable1;
            String variable2;

            String variable1Negation;
            String variable2Negation;

            if (values[i].charAt(1) == '!') {
                variable1 = values[i].substring(2, 3);
                isVariable1Negation = true;
            } else {
                variable1 = String.valueOf(values[i].charAt(1));
                isVariable1Negation = false;
            }
            variable1Negation = "!" + variable1;

            i += 2;

            if (values[i].charAt(0) == '!') {
                variable2 = values[i].substring(1, 2);
                isVariable2Negation = true;
            } else {
                variable2 = String.valueOf(values[i].charAt(0));
                isVariable2Negation = false;
            }
            variable2Negation = "!" + variable2;

            // Add variables to mappings if they do not exist yet
            if (!variableToIdMap.containsKey(variable1)) {
                addVariableToMappings(variable1, variableToIdMap, idToVariableMap);
                addVariableToMappings(variable1Negation, variableToIdMap, idToVariableMap);
            }
            if (!variableToIdMap.containsKey(variable2)) {
                addVariableToMappings(variable2, variableToIdMap, idToVariableMap);
                addVariableToMappings(variable2Negation, variableToIdMap, idToVariableMap);
            }

            // Add edges to implication digraph
            // Map (A V B) to (A -> !B) and (B -> !A)
            // based on http://www.geeksforgeeks.org/2-satisfiability-2-sat-problem/
            int variable1Id = variableToIdMap.get(variable1);
            int variable1NegationId = variableToIdMap.get(variable1Negation);
            int variable2Id = variableToIdMap.get(variable2);
            int variable2NegationId = variableToIdMap.get(variable2Negation);

            if (!isVariable1Negation) {
                if (!isVariable2Negation) {
                    adjacent[variable1Id].add(variable2NegationId);
                    adjacent[variable2Id].add(variable1NegationId);
                } else {
                    adjacent[variable1Id].add(variable2Id);
                    adjacent[variable2NegationId].add(variable1NegationId);
                }
            } else {
                if (!isVariable2Negation) {
                    adjacent[variable1NegationId].add(variable2NegationId);
                    adjacent[variable2Id].add(variable1Id);
                } else {
                    adjacent[variable1NegationId].add(variable2Id);
                    adjacent[variable2NegationId].add(variable1Id);
                }
            }
        }

        // Compute strongly connected components
        StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(adjacent);

        // Check if formula is satisfiable
        if (!isFormulaSatisfiable(adjacent, stronglyConnectedComponents)) {
            return null;
        }

        // Solve 2-SAT by assigning variables to true using the strongly connected components topological order
        List<Integer>[] sccsInTopologicalOrder = stronglyConnectedComponents.getSCCsInTopologicalOrder();

        Map<Character, Boolean> solution = new HashMap<>();

        for(List<Integer> scc : sccsInTopologicalOrder) {
            for(int vertexId : scc) {
                String vertexVariable = idToVariableMap.get(vertexId);

                char variable;

                boolean isNegation = vertexVariable.charAt(0) == '!';
                if (!isNegation) {
                    variable = vertexVariable.charAt(0);
                } else {
                    variable = vertexVariable.charAt(1);
                }

                if (!solution.containsKey(variable)) {
                    if (!isNegation) {
                        solution.put(variable, true);
                    } else {
                        solution.put(variable, false);
                    }
                }
            }
        }

        return solution;
    }

    private void addVariableToMappings(String variable, Map<String, Integer> variableToIdMap,
                                       Map<Integer, String> idToVariableMap) {
        int variableId = variableToIdMap.size();

        variableToIdMap.put(variable, variableId);
        idToVariableMap.put(variableId, variable);
    }

    private boolean isFormulaSatisfiable(List<Integer>[] adjacent, StronglyConnectedComponents stronglyConnectedComponents) {
        for(int vertex = 0; vertex < adjacent.length; vertex += 2) {
            if (stronglyConnectedComponents.stronglyConnected(vertex, vertex + 1)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        TwoSATSolver twoSATSolver = new TwoSATSolver();

        String formula1 = "(A V B) ^ (C V !B)";
        System.out.println("Formula 1: " + formula1);
        Map<Character, Boolean> solution1 = twoSATSolver.solve2SAT(formula1);

        if (solution1 == null) {
            System.out.print("The formula is not satisfiable");
        } else {
            for(char variable : solution1.keySet()) {
                System.out.print(variable + ": " + solution1.get(variable) + " ");
            }
        }
        System.out.println("\nExpected: \nA: true B: true C: true");
        System.out.println("OR A: false B: true C: true");
        System.out.println("OR A: false B: true C: false");
        System.out.println("OR A: true B: false C: false");

        String formula2 = "(X V Y) ^ (!X V Y) ^ (X V !Y) ^ (!X V !Y)";
        System.out.println("\nFormula 2: " + formula2);
        Map<Character, Boolean> solution2 = twoSATSolver.solve2SAT(formula2);

        if (solution2 == null) {
            System.out.print("The formula is not satisfiable");
        } else {
            for(char variable : solution2.keySet()) {
                System.out.print(variable + ": " + solution2.get(variable) + " ");
            }
        }
        System.out.println("\nExpected: \nThe formula is not satisfiable");

        String formula3 = "(A V B) ^ (B V C) ^ (C V D) ^ (!A V !C) ^ (!B V !D)";
        System.out.println("\nFormula 3: " + formula3);
        Map<Character, Boolean> solution3 = twoSATSolver.solve2SAT(formula3);

        if (solution3 == null) {
            System.out.print("The formula is not satisfiable");
        } else {
            for(char variable : solution3.keySet()) {
                System.out.print(variable + ": " + solution3.get(variable) + " ");
            }
        }
        System.out.println("\nExpected: \nA: false B: true C: true D: false");

        String formula4 = "(A V B) ^ (!A V B) ^ (B V !A) ^ (!A V !B)";
        System.out.println("\nFormula 4: " + formula4);
        Map<Character, Boolean> solution4 = twoSATSolver.solve2SAT(formula4);

        if (solution4 == null) {
            System.out.print("The formula is not satisfiable");
        } else {
            for(char variable : solution4.keySet()) {
                System.out.print(variable + ": " + solution4.get(variable) + " ");
            }
        }
        System.out.println("\nExpected: \nA: false B: true");

        String formula5 = "(A V D) ^ (!B V E) ^ (C V G) ^ (B V !E) ^ (!H V !B) ^ (C V !A) ^ (D V !C) ^ (E V !D) ^ " +
                "(!C V !G) ^ (F V G) ^ (A V G) ^ (!G V !A)";
        System.out.println("\nFormula 5: " + formula5);
        Map<Character, Boolean> solution5 = twoSATSolver.solve2SAT(formula5);

        if (solution5 == null) {
            System.out.print("The formula is not satisfiable");
        } else {
            for(char variable : solution5.keySet()) {
                System.out.print(variable + ": " + solution5.get(variable) + " ");
            }
        }
        System.out.println("\nExpected: \nA: true B: true C: true D: true E: true F: true G: false H: false");
        System.out.println("OR A: false B: true C: false D: true E: true F: false G: true H: false");
        System.out.println("OR A: false B: true C: false D: true E: true F: true G: true H: false");
    }

}
