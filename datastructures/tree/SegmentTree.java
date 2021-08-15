package datastructures.tree;

import java.util.Arrays;

/**
 * @author Ricardo Pacheco
 * Adapted by Rene Argento on 21/12/17.
 */
// Based on https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/SegmentTree.java
public class SegmentTree {
    // The Node class represents a partition range of the array.
    private static class Node {
        long sum;
        long min;

        // Value that will be propagated lazily
        Long pendingValue = null;
        int left;
        int right;

        int size() {
            return right - left + 1;
        }
    }

    private final Node[] heap;
    private final long[] array;

    /**
     * Time-Complexity:  O(n * log(n))
     *
     * @param array the Initialization array
     */
    public SegmentTree(long[] array) {
        this.array = Arrays.copyOf(array, array.length);
        // The max size of this array is about 2 * 2 ^ (log2(n) + 1)
        int size = (int) (2 * Math.pow(2.0, Math.floor((Math.log(array.length) / Math.log(2.0)) + 1)));
        heap = new Node[size];
        build(1, 0, array.length);
    }

    // Initialize the Nodes of the Segment tree
    private void build(int index, int left, int size) {
        heap[index] = new Node();
        heap[index].left = left;
        heap[index].right = left + size - 1;

        if (size == 1) {
            heap[index].sum = array[left];
            heap[index].min = array[left];
        } else {
            // Build children
            build(2 * index, left, size / 2);
            build(2 * index + 1, left + size / 2, size - size / 2);

            heap[index].sum = heap[2 * index].sum + heap[2 * index + 1].sum;
            heap[index].min = Math.min(heap[2 * index].min, heap[2 * index + 1].min);
        }
    }

    public int size() {
        return array.length;
    }

    /**
     * Range Sum Query
     *
     * Time-Complexity: O(log(n))
     *
     * @param  left left index
     * @param  right right index
     * @return sum
     */
    public long rangeSumQuery(int left, int right) {
        return rangeSumQuery(1, left, right);
    }

    private long rangeSumQuery(int index, int left, int right) {
        Node node = heap[index];

        // If you did a range update that contained this node, you can infer the Sum without going down the tree
        if (node.pendingValue != null && contains(node.left, node.right, left, right)) {
            return (right - left + 1) * node.pendingValue;
        }

        if (contains(left, right, node.left, node.right)) {
            return heap[index].sum;
        }

        if (intersects(left, right, node.left, node.right)) {
            propagate(index);
            long leftSum = rangeSumQuery(2 * index, left, right);
            long rightSum = rangeSumQuery(2 * index + 1, left, right);

            return leftSum + rightSum;
        }
        return 0;
    }

    /**
     * Range Min Query
     *
     * Time-Complexity: O(log(n))
     *
     * @param  left left index
     * @param  right right index
     * @return min
     */
    public long rangeMinQuery(int left, int right) {
        return rangeMinQuery(1, left, right);
    }

    private long rangeMinQuery(int index, int left, int right) {
        Node node = heap[index];

        // If you did a range update that contained this node, you can infer the Min value without going down the tree
        if (node.pendingValue != null && contains(node.left, node.right, left, right)) {
            return node.pendingValue;
        }

        if (contains(left, right, node.left, node.right)) {
            return heap[index].min;
        }

        if (intersects(left, right, node.left, node.right)) {
            propagate(index);
            long leftMin = rangeMinQuery(2 * index, left, right);
            long rightMin = rangeMinQuery(2 * index + 1, left, right);

            return Math.min(leftMin, rightMin);
        }
        return Long.MAX_VALUE;
    }

    /**
     * Range Update Operation.
     * With this operation you can update either one position or a range of positions with a given number.
     * The update operations will update the less they can to update the whole range (Lazy Propagation).
     * The values will be propagated lazily from top to bottom of the segment tree.
     * This behavior is really useful for updates on portions of the array
     * <p>
     * Time-Complexity: O(log(n))
     *
     * @param left  left index
     * @param right right index
     * @param value value
     */
    public void update(int left, int right, long value) {
        update(1, left, right, value);
    }

    private void update(int index, int left, int right, long value) {
        // The Node of the heap tree represents a range of the array with bounds: [node.left, node.right]
        Node node = heap[index];

        /**
         * If the updating-range contains the portion of the current Node, we lazily update it.
         * This means that we do NOT update each position of the vector, but update only some temporal
         * values into the Node; such values will be propagated down to the Node's children only when they need to.
         */
        if (contains(left, right, node.left, node.right)) {
            change(node, value);
        }

        if (node.size() == 1) {
            return;
        }

        if (intersects(left, right, node.left, node.right)) {
            /**
             * Before continuing to go down the tree we need to propagate the
             * values that have been temporarily/lazily saved into this Node to its children,
             * so that when we visit them the values are properly updated.
             */
            propagate(index);

            update(2 * index, left, right, value);
            update(2 * index + 1, left, right, value);

            node.sum = heap[2 * index].sum + heap[2 * index + 1].sum;
            node.min = Math.min(heap[2 * index].min, heap[2 * index + 1].min);
        }
    }

    // Propagate temporal values to children
    private void propagate(int index) {
        Node node = heap[index];

        if (node.pendingValue != null) {
            change(heap[2 * index], node.pendingValue);
            change(heap[2 * index + 1], node.pendingValue);
            node.pendingValue = null; // Unset the pending propagation value
        }
    }

    // Save the temporal values that will be propagated lazily
    private void change(Node node, long value) {
        node.pendingValue = value;
        node.sum = node.size() * value;
        node.min = value;
        array[node.left] = value;
    }

    // Check if range1 contains range2
    private boolean contains(int left1, int right1, int left2, int right2) {
        return left2 >= left1 && right2 <= right1;
    }

    // Check inclusive intersection, test if range1[left1, right1] intersects range2[left2, right2]
    private boolean intersects(int left1, int right1, int left2, int right2) {
        return left1 <= left2 && right1 >= left2   //  (.[..)..] or (.[...]..)
                || left1 >= left2 && left1 <= right2; // [.(..]..) or [..(..)..]
    }
}