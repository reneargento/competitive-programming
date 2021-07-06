package datastructures.tree.fenwick;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 28/06/21.
 */
// Based on Based on https://stackoverflow.com/questions/31106459/how-to-adapt-fenwick-tree-to-answer-range-minimum-queries/34602284#34602284
@SuppressWarnings("unchecked")
public class FenwickTreeRangeMax {
    private long[] fenwickTree1;
    private Set<Integer>[] children1;
    private long[] fenwickTree2;
    private Set<Integer>[] children2;
    private final long[] values;

    FenwickTreeRangeMax(int size) {
        fenwickTree1 = new long[size + 1];
        fenwickTree2 = new long[size + 1];
        values = new long[size + 1];
        initChildren();

        for (int i = 1; i <= size; i++) {
            if (i - lsOne(i) > 0) {
                children2[i - lsOne(i)].add(i);
            }
            if (i + lsOne(i) <= size) {
                children1[i + lsOne(i)].add(i);
            }
        }
    }

    // Create fenwick tree with values
    FenwickTreeRangeMax(long[] values) {
        this.values = values;
        initChildren();
        build(values);
    }

    private void initChildren() {
        children1 = new HashSet[values.length + 1];
        children2 = new HashSet[values.length + 1];

        for (int i = 1; i < children1.length; i++) {
            children1[i] = new HashSet<>();
            children2[i] = new HashSet<>();
        }
    }

    private void build(long[] values) {
        int size = values.length - 1; // values[0] should always be 0
        fenwickTree1 = new long[size + 1];
        fenwickTree2 = new long[size + 1];

        for (int i = 1; i <= size; i++) {
            fenwickTree2[i] = values[i];
            if (i - lsOne(i) > 0) {
                fenwickTree2[i - lsOne(i)] = Math.max(fenwickTree2[i - lsOne(i)], values[i]);
                children2[i - lsOne(i)].add(i);
            }
        }

        for (int i = size; i >= 1; i--) {
            fenwickTree1[i] = values[i];
            if (i + lsOne(i) <= size) {
                fenwickTree1[i + lsOne(i)] = Math.max(fenwickTree1[i + lsOne(i)], values[i]);
                children1[i + lsOne(i)].add(i);
            }
        }
    }

    public void update(int index, long value) {
        long originalValue = values[index];
        values[index] = value;
        updateTree(index, value, fenwickTree1, children1, originalValue, true);
        updateTree(index, value, fenwickTree2, children2, originalValue, false);
    }

    private void updateTree(int index, long value, long[] fenwickTree, Set<Integer>[] children, long originalValue,
                            boolean incrementIndex) {
        while (true) {
            if (value < fenwickTree[index]) {
                if (originalValue == fenwickTree[index]) {
                    value = Math.max(value, values[index]);

                    for (int child : children[index]) {
                        value = Math.max(value, fenwickTree[child]);
                    }
                } else {
                    break;
                }
            }

            if (value == fenwickTree[index]) {
                break;
            }

            fenwickTree[index] = value;
            if (incrementIndex) {
                index += lsOne(index);
                if (index >= fenwickTree.length) {
                    break;
                }
            } else {
                index -= lsOne(index);
                if (index == 0) {
                    break;
                }
            }
        }
    }

    // Returns the maximum value from startIndex to endIndex
    public long rangeMaxQuery(int startIndex, int endIndex) {
        long max = Long.MIN_VALUE;

        // Traverse first fenwick tree while checking second fenwick tree
        int currentIndex = startIndex;
        while (currentIndex + lsOne(currentIndex) <= endIndex) {
            max = Math.max(max, fenwickTree2[currentIndex]);
            currentIndex += lsOne(currentIndex);
        }

        // Traverse second fenwick tree while checking first fenwick tree
        currentIndex = endIndex;
        while (currentIndex - lsOne(currentIndex) >= startIndex) {
            max = Math.max(max, fenwickTree1[currentIndex]);
            currentIndex -= lsOne(currentIndex);
        }

        max = Math.max(max, values[currentIndex]);
        return max;
    }

    private int lsOne(int value) {
        return value & (-value);
    }

    public static void main(String[] args) {
        long[] values = {0, 9, 2, 3, 1, 10, 11, 100};
        FenwickTreeRangeMax fenwickTree = new FenwickTreeRangeMax(values);

        System.out.println("RangeMax (1, 1): " + fenwickTree.rangeMaxQuery(1, 1) + " Expected: 9");
        System.out.println("RangeMax (3, 5): " + fenwickTree.rangeMaxQuery(3, 5) + " Expected: 10");
        System.out.println("RangeMax (7, 7): " + fenwickTree.rangeMaxQuery(7, 7) + " Expected: 100");

        fenwickTree.update(5, 15);
        System.out.println("RangeMax (2, 6): " + fenwickTree.rangeMaxQuery(2, 6) + " Expected: 15");
        fenwickTree.update(2, 50);
        System.out.println("RangeMax (2, 6): " + fenwickTree.rangeMaxQuery(2, 6) + " Expected: 50");
        System.out.println("RangeMax (1, 7): " + fenwickTree.rangeMaxQuery(1, 7) + " Expected: 100");
    }
}
