package com.br.algs.reference.algorithms.graph.shortest.path.parallel.jobs;

import com.br.algs.reference.algorithms.graph.shortest.path.BellmanFord;
import com.br.algs.reference.datastructures.DirectedEdge;
import com.br.algs.reference.datastructures.EdgeWeightedDigraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rene Argento on 02/01/18.
 */
public class ParallelJobSchedulingWithDeadlines {

    private class ParallelJobSchedulingWithDeadlinesProblem {

        private double[] jobDurations;
        private Map<Integer, List<Integer>> jobPrecedences;

        // Relative deadlines in the format
        // JOB_ID1 MAXIMUM_TIME_ELAPSED_AFTER_JOBID2_STARTED_IN_WHICH_JOBID1_MUST_START JOB_ID2
        // Example: 2 12.0 4
        // Job 2 must start at most 12 seconds after job 4's start time
        private String[] relativeDeadlines;

        ParallelJobSchedulingWithDeadlinesProblem(double[] jobDurations,
                                                  HashMap<Integer, List<Integer>> jobPrecedences,
                                                  String[] relativeDeadlines) {
            this.jobDurations = jobDurations;
            this.jobPrecedences = jobPrecedences;
            this.relativeDeadlines = relativeDeadlines;
        }

        public double[] getJobDurations() {
            return jobDurations;
        }

        public Map<Integer, List<Integer>> getJobPrecedences() {
            return jobPrecedences;
        }

        public String[] getRelativeDeadlines() {
            return relativeDeadlines;
        }
    }

    // Considering that the problem is feasible
    private void solveProblemIfFeasible(ParallelJobSchedulingWithDeadlinesProblem problem) {
        if (problem == null) {
            return;
        }

        double[] jobDurations = problem.getJobDurations();
        Map<Integer, List<Integer>> jobPrecedences = problem.getJobPrecedences();
        String[] relativeDeadlines = problem.getRelativeDeadlines();

        int numberOfJobs = jobDurations.length;

        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(2 * numberOfJobs + 2);

        int source = 2 * numberOfJobs;
        int target = 2 * numberOfJobs + 1;

        // Add direct successor relations
        for(int job = 0; job < numberOfJobs; job++) {
            edgeWeightedDigraph.addEdge(new DirectedEdge(job, job + numberOfJobs, jobDurations[job]));
            edgeWeightedDigraph.addEdge(new DirectedEdge(source, job, 0));
            edgeWeightedDigraph.addEdge(new DirectedEdge(job + numberOfJobs, target, 0));

            for(int successor : jobPrecedences.get(job)) {
                edgeWeightedDigraph.addEdge(new DirectedEdge(job + numberOfJobs, successor, 0));
            }
        }

        // Add relative deadline relations
        for(String relativeDeadline : relativeDeadlines) {
            String[] deadlineInformation = relativeDeadline.split(" +");

            int dependentJob = Integer.parseInt(deadlineInformation[0]);
            double duration = Double.parseDouble(deadlineInformation[1]);
            int job = Integer.parseInt(deadlineInformation[2]);

            edgeWeightedDigraph.addEdge(new DirectedEdge(dependentJob, job, -duration));
        }

        // Solve problem
        // 1- Negate all edge weights
        EdgeWeightedDigraph edgeWeightedDigraphNegatedEdges = new EdgeWeightedDigraph(edgeWeightedDigraph.vertices());

        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            for(DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                edgeWeightedDigraphNegatedEdges.addEdge(new DirectedEdge(edge.from(), edge.to(), -edge.weight()));
            }
        }

        // 2- Get longest paths using Bellman-Ford
        BellmanFord bellmanFord = new BellmanFord(edgeWeightedDigraphNegatedEdges, source);
        printJobSchedules(bellmanFord, numberOfJobs, target);
    }

    private void printJobSchedules(BellmanFord bellmanFord, int numberOfJobs, int target) {
        System.out.println("Start times:");

        for(int job = 0; job < numberOfJobs; job++) {
            double distance = bellmanFord.distTo(job);

            if (distance != 0) {
                distance = distance * -1;
            }

            System.out.printf("%4d: %6.2f\n", job, distance);
        }

        double targetDistance = bellmanFord.distTo(target);

        if (targetDistance != 0) {
            targetDistance = targetDistance * -1;
        }

        System.out.printf("Finish time: %5.2f\n", targetDistance);
    }

}
