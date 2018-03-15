package com.br.uri.made.by.women2018;

import java.util.*;

/**
 * Created by Rene Argento on 10/03/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/346/5
@SuppressWarnings("unchecked")
public class TripProgramming {

    public static class EdgeWeightedDigraph {

        private final int vertices;
        private int edges;
        private List<DirectedEdge>[] adjacent;

        public EdgeWeightedDigraph(int vertices) {
            this.vertices = vertices;
            edges = 0;
            adjacent = (List<DirectedEdge>[]) new ArrayList[vertices];

            for(int vertex = 0; vertex < vertices; vertex++) {
                adjacent[vertex] = new ArrayList<>();
            }
        }

        public int vertices() {
            return vertices;
        }

        public int edgesCount() {
            return edges;
        }

        public int outdegree(int vertex) {
            return adjacent[vertex].size();
        }

        public void addEdge(DirectedEdge edge) {
            adjacent[edge.from()].add(edge);
            edges++;
        }

        public Iterable<DirectedEdge> adjacent(int vertex) {
            return adjacent[vertex];
        }

        public Iterable<DirectedEdge> edges() {
            List<DirectedEdge> bag = new ArrayList<>();

            for(int vertex = 0; vertex < vertices; vertex++) {
                bag.addAll(adjacent[vertex]);
            }

            return bag;
        }

        public EdgeWeightedDigraph reverse() {
            EdgeWeightedDigraph reverse = new EdgeWeightedDigraph(vertices);

            for(int vertex = 0; vertex < vertices; vertex++) {
                for(DirectedEdge edge : adjacent(vertex)) {
                    int neighbor = edge.to();
                    reverse.addEdge(new DirectedEdge(neighbor, vertex, edge.weight()));
                }
            }

            return reverse;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();

            for(int vertex = 0; vertex < vertices(); vertex++) {
                stringBuilder.append(vertex).append(": ");

                for(DirectedEdge neighbor : adjacent(vertex)) {
                    stringBuilder.append(neighbor).append(" ");
                }
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();
        }

    }

    public static class DirectedEdge {

        private final int vertex1;
        private final int vertex2;
        private final double weight;

        public DirectedEdge(int vertex1, int vertex2, double weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public double weight() {
            return weight;
        }

        public int from() {
            return vertex1;
        }

        public int to() {
            return vertex2;
        }

        public String toString() {
            return String.format("%d->%d %.2f", vertex1, vertex2, weight);
        }

    }

    public static class IndexMinPriorityQueue<Key extends Comparable<Key>> {

        private Key[] keys;
        private int[] pq; // Holds the indices of the keys
        private int[] qp; // Inverse of pq -> qp[i] gives the position of i in pq[] (the index j such that pq[j] is i).
        // qp[pq[i]] = pq[qp[i]] = i
        private int size = 0;

        @SuppressWarnings("unchecked")
        public IndexMinPriorityQueue(int size) {
            keys = (Key[]) new Comparable[size + 1];
            pq = new int[size + 1];
            qp = new int[size + 1];

            for(int index = 0; index < qp.length; index++) {
                qp[index] = -1;
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public boolean contains(int index) {
            return qp[index] != -1;
        }

        //Return key associated with index
        public Key keyOf(int index) {
            if (!contains(index)) {
                throw new NoSuchElementException("Index is not in the priority queue");
            }

            return keys[index];
        }

        public void insert(int index, Key key) {
            if (contains(index)) {
                throw new IllegalArgumentException("Index is already in the priority queue");
            }

            if (size != keys.length - 1) {
                size++;

                keys[index] = key;
                pq[size] = index;
                qp[index] = size;

                swim(size);
            }
        }

        // Remove a minimal key and return its index
        public int deleteMin() {
            if (size == 0) {
                throw new NoSuchElementException("Priority queue underflow");
            }

            int minElementIndex = pq[1];
            exchange(1, size);
            size--;
            sink(1);

            keys[pq[size + 1]] = null;
            qp[pq[size + 1]] = -1;

            return minElementIndex;
        }

        public void delete(int i) {
            if (!contains(i)) {
                throw new NoSuchElementException("Index is not in the priority queue");
            }

            int index = qp[i];

            exchange(index, size);
            size--;

            swim(index);
            sink(index);

            keys[i] = null; // Same thing as keys[pq[size + 1]] = null
            qp[i] = -1;     // Same thing as qp[pq[size + 1]] = -1;
        }

        // Change the key associated with index to key argument
        public void changeKey(int index, Key key) {
            if (!contains(index)) {
                throw new NoSuchElementException("Index is not in the priority queue");
            }

            keys[index] = key;

            swim(qp[index]);
            sink(qp[index]);
        }

        public void decreaseKey(int index, Key key) {
            if (!contains(index)) {
                throw new NoSuchElementException("Index is not in the priority queue");
            }
            if (key.compareTo(keys[index]) >= 0) {
                throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
            }

            keys[index] = key;
            swim(qp[index]);
        }

        public void increaseKey(int index, Key key) {
            if (!contains(index)) {
                throw new NoSuchElementException("Index is not in the priority queue");
            }
            if (key.compareTo(keys[index]) <= 0) {
                throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");
            }

            keys[index] = key;
            sink(qp[index]);
        }

        public Key minKey() {
            if (size == 0) {
                throw new NoSuchElementException("Priority queue underflow");
            }

            return keys[pq[1]];
        }

        public int minIndex() {
            if (size == 0) {
                throw new NoSuchElementException("Priority queue underflow");
            }

            return pq[1];
        }

        private void swim(int index) {
            while(index / 2 >= 1 && more(index / 2, index)) {
                exchange(index / 2, index);
                index = index / 2;
            }
        }

        private void sink(int index) {
            while (index * 2 <= size) {
                int selectedChildIndex = index * 2;

                if (index * 2 + 1 <= size && more(index * 2, index * 2 + 1)){
                    selectedChildIndex = index * 2 + 1;
                }

                if (less(selectedChildIndex, index)) {
                    exchange(index, selectedChildIndex);
                } else {
                    break;
                }

                index = selectedChildIndex;
            }
        }

        private boolean less(int keyIndex1, int keyIndex2) {
            return keys[pq[keyIndex1]].compareTo(keys[pq[keyIndex2]]) < 0;
        }

        private boolean more(int keyIndex1, int keyIndex2) {
            return keys[pq[keyIndex1]].compareTo(keys[pq[keyIndex2]]) > 0;
        }

        private void exchange(int keyIndex1, int keyIndex2) {
            int temp = pq[keyIndex1];
            pq[keyIndex1] = pq[keyIndex2];
            pq[keyIndex2] = temp;

            qp[pq[keyIndex1]] = keyIndex1;
            qp[pq[keyIndex2]] = keyIndex2;
        }

    }

    public static class Dijkstra {

        private DirectedEdge[] edgeTo;  // last edge on path to vertex
        private double[] distTo;        // length of path to vertex
        private IndexMinPriorityQueue<Double> priorityQueue;

        public Dijkstra(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
            distTo = new double[edgeWeightedDigraph.vertices()];
            priorityQueue = new IndexMinPriorityQueue<>(edgeWeightedDigraph.vertices());

            for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                distTo[vertex] = Double.POSITIVE_INFINITY;
            }
            distTo[source] = 0;
            priorityQueue.insert(source, 0.0);

            while (!priorityQueue.isEmpty()) {
                relax(edgeWeightedDigraph, priorityQueue.deleteMin());
                // In a source-sink problem, break the loop if the sink was relaxed
            }
        }

        private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
            for(DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.to();

                if (distTo[neighbor] > distTo[vertex] + edge.weight()) {
                    distTo[neighbor] = distTo[vertex] + edge.weight();
                    edgeTo[neighbor] = edge;

                    if (priorityQueue.contains(neighbor)) {
                        priorityQueue.decreaseKey(neighbor, distTo[neighbor]);
                    } else {
                        priorityQueue.insert(neighbor, distTo[neighbor]);
                    }
                }
            }
        }

        public double distTo(int vertex) {
            return distTo[vertex];
        }

        public DirectedEdge edgeTo(int vertex) {
            return edgeTo[vertex];
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] < Double.POSITIVE_INFINITY;
        }

        public Iterable<DirectedEdge> pathTo(int vertex) {
            if (!hasPathTo(vertex)) {
                return null;
            }

            Deque<DirectedEdge> path = new ArrayDeque<>();
            for(DirectedEdge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.from()]) {
                path.push(edge);
            }

            return path;
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int cities = scanner.nextInt();
        int roads = scanner.nextInt();

        while (cities != 0 || roads != 0) {
            EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(cities + 1);

            for (int i = 0; i < roads; i++) {
                int city1 = scanner.nextInt();
                int city2 = scanner.nextInt();
                int minutes = scanner.nextInt();

                edgeWeightedDigraph.addEdge(new DirectedEdge(city1, city2, minutes));
                edgeWeightedDigraph.addEdge(new DirectedEdge(city2, city1, minutes));
            }

            int target = scanner.nextInt();
            int source = 1;

            Dijkstra dijkstra = new Dijkstra(edgeWeightedDigraph, target);

            long totalTime = (long) dijkstra.distTo[1];

            StringBuilder path = new StringBuilder();
            boolean isFirstCity = true;

            for (DirectedEdge edge : dijkstra.pathTo(source)) {
                if (isFirstCity) {
                    path.append(edge.from());
                    isFirstCity = false;

                    if (edge.to() != source) {
                        path.append(" ");
                    }
                }

                if (edge.from() != edge.to()) {
                    path.append(edge.to());
                }

                if (edge.to() != source) {
                    path.append(" ");
                }
            }

            if (path.length() == 0) {
                path.append(target);
            }

            if (totalTime >= 120) {
                long timeLate = totalTime - 120;
                System.out.println("It will be " + timeLate + " minutes late. Travel time - " + totalTime
                        + " - best way - " + path);
            } else {
                System.out.println("Will not be late. Travel time - " + totalTime
                        + " - best way - " + path);
            }

            cities = scanner.nextInt();
            roads = scanner.nextInt();
        }
    }

}
