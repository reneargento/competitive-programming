package datastructures.tree.fenwick;

/**
 * Created by Rene Argento on 13/01/21.
 */
public class FenwickTreeRangeSumRangeUpdate {
    private FenwickTreeRangeSum fenwickTreeRangeSum;
    private FenwickTreeRangeUpdate fenwickTreeRangeUpdate;

    FenwickTreeRangeSumRangeUpdate(int size) {
        fenwickTreeRangeSum = new FenwickTreeRangeSum(size);
        fenwickTreeRangeUpdate = new FenwickTreeRangeUpdate(size);
    }

    public void rangeUpdate(int startIndex, int endIndex, int value) {
        fenwickTreeRangeUpdate.rangeUpdate(startIndex, endIndex, value); // [startIndex, startIndex + 1, ..., endIndex] + value
        fenwickTreeRangeSum.update(startIndex, value * (startIndex - 1));
        fenwickTreeRangeSum.update(endIndex + 1, -value * endIndex);
    }

    public long rangeSumQuery(int index) {
        return fenwickTreeRangeUpdate.pointQuery(index) * index - // Initial calculation
                fenwickTreeRangeSum.rangeSumQuery(index); // Cancellation factor
    }

    public long rangeSumQuery(int startIndex, int endIndex) {
        return rangeSumQuery(endIndex) - rangeSumQuery(startIndex - 1);
    }
}
