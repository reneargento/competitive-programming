package datastructures.tree.fenwick;

/**
 * Created by Rene Argento on 13/01/21.
 */
public class FenwickTreeRangeSum {
    private long[] fenwickTree;

    // Create empty fenwick tree
    FenwickTreeRangeSum(int size) {
        fenwickTree = new long[size + 1];
    }

    // Create fenwick tree with values
    FenwickTreeRangeSum(long[] values) {
        build(values);
    }

    private void build(long[] values) {
        int size = values.length - 1; // values[0] should always be 0
        fenwickTree = new long[size + 1];
        for (int i = 1; i <= size; i++) {
            fenwickTree[i] += values[i];

            if (i + lsOne(i) <= size) { // i has parent
                fenwickTree[i + lsOne(i)] += fenwickTree[i]; // Add to that parent
            }
        }
    }

    // Returns sum from 1 to index
    public long rangeSumQuery(int index) {
        long sum = 0;
        while (index > 0) {
            sum += fenwickTree[index];
            index -= lsOne(index);
        }
        return sum;
    }

    public long rangeSumQuery(int startIndex, int endIndex) {
        return rangeSumQuery(endIndex) - rangeSumQuery(startIndex - 1);
    }

    // Updates the value of element on index by value (can be positive/increment or negative/decrement)
    public void update(int index, long value) {
        while (index < fenwickTree.length) {
            fenwickTree[index] += value;
            index += lsOne(index);
        }
    }

    // O(log n)
    int select(long rank) {
        int p = 1;
        while (p * 2 < fenwickTree.length) {
            p *= 2;
        }

        int index = 0;
        while (p > 0) {
            if (rank > fenwickTree[index + p]) {
                rank -= fenwickTree[index + p];
                index += p;
            }
            p /= 2;
        }
        return index + 1;
    }

    // O(lg^2 n)
    public int select2(long rank) {
        int low = 1;
        int high = fenwickTree.length - 1;

        for (int i = 0; i < 30; i++) { // 2^30 is higher than 10^9, which is usually ok
            int middle = low + (high - low) / 2;

            if (rangeSumQuery(1, middle) < rank) {
                low = middle;
            } else {
                high = middle;
            }
        }
        return high;
    }

    private int lsOne(int value) {
        return value & (-value);
    }
}
