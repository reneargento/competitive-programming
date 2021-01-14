package datastructures.fenwick.tree;

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

    // O(lg^2 n)
    public int select(long value) {
        int low = 1;
        int high = fenwickTree.length - 1;

        for (int i = 0; i < 30; i++) { // 2^30 is higher than 10^9, which is usually ok
            int middle = low + (high - low) / 2;

            if (rangeSumQuery(1, middle) < value) {
                low = middle;
            } else {
                high = middle;
            }
        }
        return high;
    }

    int lsOne(int value) {
        return value & (-value);
    }
}
