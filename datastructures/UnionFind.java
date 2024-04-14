package datastructures;

/**
 * Created by Rene Argento on 03/06/17.
 */
public class UnionFind {
    private final int[] leaders;
    private final int[] ranks;
    private final int[] sizes;
    private int components;

    public UnionFind(int size) {
        leaders = new int[size];
        ranks = new int[size];
        sizes = new int[size];
        components = size;

        for(int i = 0; i < size; i++) {
            leaders[i]  = i;
            sizes[i] = 1;
        }
    }

    public int count() {
        return components;
    }

    public boolean connected(int site1, int site2) {
        return find(site1) == find(site2);
    }

    // O(α(n)), where α is the inverse Ackermann function.
    public int find(int site) {
        if (site == leaders[site]) {
            return site;
        }
        return leaders[site] = find(leaders[site]);
    }

    // O(α(n)), where α is the inverse Ackermann function.
    public void union(int site1, int site2) {
        int leader1 = find(site1);
        int leader2 = find(site2);

        if (leader1 == leader2) {
            return;
        }

        if (ranks[leader1] < ranks[leader2]) {
            leaders[leader1] = leader2;
            sizes[leader2] += sizes[leader1];
        } else if (ranks[leader2] < ranks[leader1]) {
            leaders[leader2] = leader1;
            sizes[leader1] += sizes[leader2];
        } else {
            leaders[leader1] = leader2;
            sizes[leader2] += sizes[leader1];
            ranks[leader2]++;
        }
        components--;
    }

    public int size(int set) {
        int leader = find(set);
        return sizes[leader];
    }
}
