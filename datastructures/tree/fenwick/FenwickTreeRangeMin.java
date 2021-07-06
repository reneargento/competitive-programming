package datastructures.tree.fenwick;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 28/06/21.
 */
// Based on Based on https://stackoverflow.com/questions/31106459/how-to-adapt-fenwick-tree-to-answer-range-minimum-queries/34602284#34602284
@SuppressWarnings("unchecked")
public class FenwickTreeRangeMin {
    private long[] fenwickTree1;
    private Set<Integer>[] children1;
    private long[] fenwickTree2;
    private Set<Integer>[] children2;
    private final long[] values;

    FenwickTreeRangeMin(int size) {
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
    FenwickTreeRangeMin(long[] values) {
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
                fenwickTree2[i - lsOne(i)] = Math.min(fenwickTree2[i - lsOne(i)], values[i]);
                children2[i - lsOne(i)].add(i);
            }
        }

        for (int i = size; i >= 1; i--) {
            fenwickTree1[i] = values[i];
            if (i + lsOne(i) <= size) {
                fenwickTree1[i + lsOne(i)] = Math.min(fenwickTree1[i + lsOne(i)], values[i]);
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
            if (value > fenwickTree[index]) {
                if (originalValue == fenwickTree[index]) {
                    value = Math.min(value, values[index]);

                    for (int child : children[index]) {
                        value = Math.min(value, fenwickTree[child]);
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

    // Returns the minimum value from startIndex to endIndex
    public long rangeMinQuery(int startIndex, int endIndex) {
        long min = Long.MAX_VALUE;

        // Traverse first fenwick tree while checking second fenwick tree
        int currentIndex = startIndex;
        while (currentIndex + lsOne(currentIndex) <= endIndex) {
            min = Math.min(min, fenwickTree2[currentIndex]);
            currentIndex += lsOne(currentIndex);
        }

        // Traverse second fenwick tree while checking first fenwick tree
        currentIndex = endIndex;
        while (currentIndex - lsOne(currentIndex) >= startIndex) {
            min = Math.min(min, fenwickTree1[currentIndex]);
            currentIndex -= lsOne(currentIndex);
        }

        min = Math.min(min, values[currentIndex]);
        return min;
    }

    private int lsOne(int value) {
        return value & (-value);
    }

    public static void main(String[] args) {
        long[] values = {0, 9, 2, 3, 1, 10, 11, 100};
        FenwickTreeRangeMin fenwickTree = new FenwickTreeRangeMin(values);

        System.out.println("RangeMin (1, 1): " + fenwickTree.rangeMinQuery(1, 1) + " Expected: 9");
        System.out.println("RangeMin (2, 4): " + fenwickTree.rangeMinQuery(2, 4) + " Expected: 1");
        System.out.println("RangeMin (5, 7): " + fenwickTree.rangeMinQuery(5, 7) + " Expected: 10");
        System.out.println("RangeMin (1, 7): " + fenwickTree.rangeMinQuery(1, 7) + " Expected: 1");
        System.out.println("RangeMin (7, 7): " + fenwickTree.rangeMinQuery(7, 7) + " Expected: 100");

        fenwickTree.update(4, 8);
        System.out.println("RangeMin (1, 7): " + fenwickTree.rangeMinQuery(1, 7) + " Expected: 2");
        fenwickTree.update(2, 100);
        System.out.println("RangeMin (1, 7): " + fenwickTree.rangeMinQuery(1, 7) + " Expected: 3");
        fenwickTree.update(5, -1);
        System.out.println("RangeMin (2, 4): " + fenwickTree.rangeMinQuery(2, 4) + " Expected: 3");
        System.out.println("RangeMin (1, 7): " + fenwickTree.rangeMinQuery(1, 7) + " Expected: -1");
    }
}
