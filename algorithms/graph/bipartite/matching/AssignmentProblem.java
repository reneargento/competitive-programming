package algorithms.graph.bipartite.matching;

import algorithms.graph.shortest.path.Dijkstra;
import datastructures.DirectedEdge;
import datastructures.EdgeWeightedDigraph;

import java.util.Random;

/**
 * Created by Rene Argento on 04/11/18.
 */
// Solves an n-by-n assignment problem in N^3 log N time using the successive shortest path algorithm.
// The assignment problem is to find a minimum weight matching in an edge-weighted complete bipartite graph.
// O(N^3 log N)
// Reference: https://algs4.cs.princeton.edu/65reductions/AssignmentProblem.java.html
public class AssignmentProblem {

    private static final double FLOATING_POINT_EPSILON = 1E-14;
    private static final int UNMATCHED = -1;

    private int n;              // number of rows and columns
    private double[][] weight;  // the n-by-n cost matrix
    private double minWeight;   // minimum value of any weight
    private double[] px;        // px[i] = dual variable for row i
    private double[] py;        // py[j] = dual variable for column j
    private int[] xy;           // xy[i] = j means i-j is a match
    private int[] yx;           // yx[j] = i means i-j is a match

    /**
     * Determines an optimal solution to the assignment problem.
     *
     * @param  weight the <em>n</em>-by-<em>n</em> matrix of weights
     * @throws IllegalArgumentException unless all weights are nonnegative
     * @throws IllegalArgumentException if {@code weight} is {@code null}
     */
    public AssignmentProblem(double[][] weight) {
        if (weight == null) {
            throw new IllegalArgumentException("Weight matrix is null");
        }

        n = weight.length;
        this.weight = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (weight[i][j] < 0) {
                    throw new IllegalArgumentException("Weights cannot be negative");
                }
                if (weight[i][j] < minWeight) {
                    minWeight = weight[i][j];
                }
                this.weight[i][j] = weight[i][j];
            }
        }

        // Dual variables
        px = new double[n];
        py = new double[n];

        // Initial matching is empty
        xy = new int[n];
        yx = new int[n];

        for (int i = 0; i < n; i++) {
            xy[i] = UNMATCHED;
        }

        for (int j = 0; j < n; j++) {
            yx[j] = UNMATCHED;
        }

        // Add n edges to matching
        for (int k = 0; k < n; k++) {
            augment();
        }
    }

    // Find shortest augmenting path and update
    private void augment() {
        // Build residual graph
        EdgeWeightedDigraph residualGraph = new EdgeWeightedDigraph(2 * n + 2);

        int source = 2 * n;
        int target = 2 * n + 1;

        for (int i = 0; i < n; i++) {
            if (xy[i] == UNMATCHED) {
                residualGraph.addEdge(new DirectedEdge(source, i, 0.0));
            }
        }

        for (int j = 0; j < n; j++) {
            if (yx[j] == UNMATCHED) {
                residualGraph.addEdge(new DirectedEdge(n + j, target, py[j]));
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (xy[i] == j) {
                    residualGraph.addEdge(new DirectedEdge(n + j, i, 0.0));
                } else {
                    residualGraph.addEdge(new DirectedEdge(i, n + j, reducedCost(i, j)));
                }
            }
        }

        // Compute shortest path from source to every other vertex
        Dijkstra shortestPaths = new Dijkstra(residualGraph, source);

        // Augment along alternating path
        for (DirectedEdge edge : shortestPaths.pathTo(target)) {
            int vertex1 = edge.from();
            int vertex2 = edge.to() - n;

            if (vertex1 < n) {
                xy[vertex1] = vertex2;
                yx[vertex2] = vertex1;
            }
        }

        // Update dual variables
        for (int i = 0; i < n; i++) {
            px[i] += shortestPaths.distTo(i);
        }

        for (int j = 0; j < n; j++) {
            py[j] += shortestPaths.distTo(n + j);
        }
    }

    // Reduced cost of i-j
    // (subtracting off minWeight reweights all weights to be non-negative)
    private double reducedCost(int i, int j) {
        double reducedCost = (weight[i][j] - minWeight) + px[i] - py[j];

        // To avoid issues with floating-point precision
        double magnitude = Math.abs(weight[i][j]) + Math.abs(px[i]) + Math.abs(py[j]);
        if (Math.abs(reducedCost) <= FLOATING_POINT_EPSILON * magnitude) {
            return 0.0;
        }

        return reducedCost;
    }

    // Returns the dual optimal value for the specified row.
    public double dualRow(int rowIndex) {
        return px[rowIndex];
    }

    // Returns the dual optimal value for the specified column.
    public double dualCol(int columnIndex) {
        return py[columnIndex];
    }

    // Returns the column associated with the specified row in the optimal solution.
    public int solution(int rowIndex) {
        return xy[rowIndex];
    }

    // Returns the total weight of the optimal solution
    public double weight() {
        double total = 0.0;

        for (int i = 0; i < n; i++) {
            if (xy[i] != UNMATCHED) {
                total += weight[i][xy[i]];
            }
        }

        return total;
    }

    public static void main(String[] args) {
        Random random = new Random();

        // Create random n-by-n matrix
        int n = 10;
        double[][] weight = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                weight[i][j] = random.nextInt(900) + 100;  // 3 digits
            }
        }

        // Solve the assignment problem
        AssignmentProblem assignment = new AssignmentProblem(weight);

        System.out.printf("Weight = %.0f\n", assignment.weight());
        System.out.println();

        // Print n-by-n matrix and optimal solution
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                if (column == assignment.solution(row)) {
                    System.out.printf("*%.0f ", weight[row][column]);
                } else {
                    System.out.printf(" %.0f ", weight[row][column]);
                }
            }
            System.out.println();
        }
    }
}
