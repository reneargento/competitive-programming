package com.br.algs.reference.datastructures;

/**
 * Created by rene on 23/09/17.
 */
public class SegmentTree {
    private SegmentTree lc = null, rc = null;
    private long sum, lazy;
    private int start, end;

    // [startIndex, endIndex]
    SegmentTree (int startIndex, int endIndex) {
        this.start = startIndex;
        this.end = endIndex;
        if (startIndex != endIndex) {
            int mid = (startIndex + endIndex) >> 1;
            lc = new SegmentTree (startIndex, mid);
            rc = new SegmentTree (mid + 1, endIndex);
        }
    }

    public long range () {
        return end - start + 1;
    }

    public void update (int left, int right, int value) {
        if (left > end || start > right) return;
        if (left == start && right == end) {
            sum += range () * value;
            lazy += value;
            return;
        }

        push();

        int mid = (start + end) >> 1;
        if (mid >= right) lc.update (left, right, value);
        else if (mid < left) rc.update (left, right, value);
        else {
            lc.update (left, mid, value);
            rc.update (mid + 1, right, value);
        }
        sum = lc.sum + rc.sum;
    }

    public long query (int left, int right) {
        if (left > end || start > right) return 0;
        if (left == start && right == end) {
            return sum;
        }

        push ();

        int mid = (start + end) >> 1;
        if (mid >= right) return lc.query (left, right);
        else if (mid < left) return rc.query (left, right);
        else return lc.query (left, mid) + rc.query (mid + 1, right);
    }

    private void push () {
        if (lc != null) {
            lc.sum += lc.range () * lazy;
            lc.lazy += lazy;
            rc.sum += rc.range () * lazy;
            rc.lazy += lazy;
            sum = lc.sum + rc.sum;
        }
        lazy = 0;
    }
}
