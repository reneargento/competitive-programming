package com.br.algs.reference.algorithms.graph.network.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rene Argento on 24/10/18.
 */
public class MaxBipartiteMatching {

    public void maxBipartiteMatching(List<String> students, List<String> companies,
                                     Map<String, List<String>> preferences) {
        Map<String, Integer> nameToIdMap = new HashMap<>();
        Map<Integer, String> idToNameMap = new HashMap<>();

        int id = 0;

        for (String student : students) {
            nameToIdMap.put(student, id);
            idToNameMap.put(id, student);
            id++;
        }

        for (String company : companies) {
            nameToIdMap.put(company, id);
            idToNameMap.put(id, company);
            id++;
        }

        FlowNetwork flowNetwork = new FlowNetwork(id + 2);
        int source = id;
        int target = id + 1;

        // Add preferences
        for (String student : preferences.keySet()) {
            List<String> preference = preferences.get(student);
            int studentId = nameToIdMap.get(student);

            for (String company : preference) {
                int companyId = nameToIdMap.get(company);
                flowNetwork.addEdge(new FlowEdge(studentId, companyId, 1));
            }
        }

        // Add edge from source to all students
        for (String student : students) {
            int studentId = nameToIdMap.get(student);
            flowNetwork.addEdge(new FlowEdge(source, studentId, 1));
        }

        // Add edge from all companies to target
        for (String company : companies) {
            int companyId = nameToIdMap.get(company);
            flowNetwork.addEdge(new FlowEdge(companyId, target, 1));
        }

        EdmondsKarp edmondsKarp = new EdmondsKarp(flowNetwork, source, target);
        printMatches(flowNetwork, (int) edmondsKarp.maxFlowValue(), idToNameMap);
    }

    private void printMatches(FlowNetwork flowNetwork, int maxFlowValue, Map<Integer, String> idToNameMap) {
        System.out.println("*** Matches ***");

        int source = flowNetwork.vertices() - 2;
        int target = source + 1;

        for (int vertex = 0; vertex < flowNetwork.vertices(); vertex++) {
            for (FlowEdge edge : flowNetwork.adjacent(vertex)) {
                if (edge.from() == source || edge.to() == target) {
                    continue;
                }

                if (vertex == edge.from() && edge.flow() > 0) {
                    String student = idToNameMap.get(edge.from());
                    String company = idToNameMap.get(edge.to());
                    System.out.printf("%5s - %8s\n", student, company);
                }
            }
        }

        System.out.println("\nNumber of matches: " + maxFlowValue + " Expected: 6");
    }

    public static void main(String[] args) {
        List<String> students = new ArrayList<>();
        students.add("Alice");
        students.add("Bob");
        students.add("Carol");
        students.add("Dave");
        students.add("Eliza");
        students.add("Frank");

        List<String> companies = new ArrayList<>();
        companies.add("Adobe");
        companies.add("Amazon");
        companies.add("Facebook");
        companies.add("Google");
        companies.add("IBM");
        companies.add("Yahoo");

        Map<String, List<String>> preferences = new HashMap<>();
        List<String> alicePreferences = new ArrayList<>();
        alicePreferences.add("Adobe");
        alicePreferences.add("Amazon");
        alicePreferences.add("Facebook");
        preferences.put("Alice", alicePreferences);

        List<String> bobPreferences = new ArrayList<>();
        bobPreferences.add("Adobe");
        bobPreferences.add("Amazon");
        bobPreferences.add("Yahoo");
        preferences.put("Bob", bobPreferences);

        List<String> carolPreferences = new ArrayList<>();
        carolPreferences.add("Facebook");
        carolPreferences.add("Google");
        carolPreferences.add("IBM");
        preferences.put("Carol", carolPreferences);

        List<String> davePreferences = new ArrayList<>();
        davePreferences.add("Adobe");
        davePreferences.add("Amazon");
        preferences.put("Dave", davePreferences);

        List<String> elizaPreferences = new ArrayList<>();
        elizaPreferences.add("Google");
        elizaPreferences.add("IBM");
        elizaPreferences.add("Yahoo");
        preferences.put("Eliza", elizaPreferences);

        List<String> frankPreferences = new ArrayList<>();
        frankPreferences.add("IBM");
        frankPreferences.add("Yahoo");
        preferences.put("Frank", frankPreferences);

        new MaxBipartiteMatching().maxBipartiteMatching(students, companies, preferences);
    }

}
