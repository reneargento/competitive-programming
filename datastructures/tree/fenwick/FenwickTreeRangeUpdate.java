package datastructures.tree.fenwick;

/**
 * Created by Rene Argento on 13/01/21.
 */
public class FenwickTreeRangeUpdate {
    private FenwickTreeRangeSum fenwickTree;

    FenwickTreeRangeUpdate(int size) {
        fenwickTree = new FenwickTreeRangeSum(size);
    }

    public void rangeUpdate(int startIndex, int endIndex, int value) {
        fenwickTree.update(startIndex, value); // [startIndex, startIndex + 1, ..., size] + value
        fenwickTree.update(endIndex + 1, -value); // [endIndex + 1, endIndex + 2, ..., size] - value
    }

    public long pointQuery(int index) {
        return fenwickTree.rangeSumQuery(index);
    }
}
