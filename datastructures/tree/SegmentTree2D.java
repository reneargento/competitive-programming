package datastructures.tree;

/**
 * Created by Rene Argento on 02/08/21.
 */
// Adapted and enhanced the original version on https://www.geeksforgeeks.org/two-dimensional-segment-tree-sub-matrix-sum/
public class SegmentTree2D {
    private final Node[][] baseSegmentTree;
    private final Node[][] segmentTree2D;
    private final int rowsSize;
    private final int columnsSize;

    private class Node {
        long sum;
        int minValue;
        int maxValue;

        public Node(long sum, int minValue, int maxValue) {
            this.sum = sum;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }
    }

    public SegmentTree2D(int[][] matrix) {
        rowsSize = matrix.length;
        columnsSize = matrix[0].length;

        baseSegmentTree = new Node[rowsSize * 4][columnsSize * 4];
        segmentTree2D = new Node[rowsSize * 4][columnsSize * 4];

        int low = 1;

        // Create the base segment trees for each row
        for (int rowIndex = 1; rowIndex <= matrix.length; rowIndex++) {
            buildBase(low, columnsSize, rowIndex, 1, matrix);
        }
        // Build the 2D segment tree based on the base segment trees
        build(low, rowsSize, 1);
    }

    /*
     * Constructs the base segment trees based on a matrix.
     */
    private void buildBase(int low, int high, int rowIndex, int columnIndex, int[][] matrix) {
        if (high == low) {
            int value = matrix[rowIndex - 1][low - 1];
            baseSegmentTree[rowIndex][columnIndex] = new Node(value, value, value);
        } else {
            int middle = (low + high) / 2;
            buildBase(low, middle, rowIndex,2 * columnIndex, matrix);
            buildBase(middle + 1, high, rowIndex, 2 * columnIndex + 1, matrix);

            Node leftNode = baseSegmentTree[rowIndex][2 * columnIndex];
            Node rightNode = baseSegmentTree[rowIndex][2 * columnIndex + 1];
            baseSegmentTree[rowIndex][columnIndex] = mergeNodeFromChildren(leftNode, rightNode);
        }
    }

    /*
     * Constructs the Segment Tree 2D based on the base segment trees.
     */
    private void build(int low, int high, int rowIndex) {
        if (high == low) {
            for (int columnIndex = 1; columnIndex < 4 * columnsSize; columnIndex++) {
                segmentTree2D[rowIndex][columnIndex] = copyNode(segmentTree2D[rowIndex][columnIndex],
                        baseSegmentTree[low][columnIndex]);
            }
        } else {
            int middle = (low + high) / 2;
            build(low, middle, 2 * rowIndex);
            build(middle + 1, high, 2 * rowIndex + 1);

            for (int columnIndex = 1; columnIndex < 4 * columnsSize; columnIndex++) {
                Node leftNode = segmentTree2D[2 * rowIndex][columnIndex];
                Node rightNode = segmentTree2D[2 * rowIndex + 1][columnIndex];
                segmentTree2D[rowIndex][columnIndex] = mergeNodeFromChildren(leftNode, rightNode);
            }
        }
    }

    /*
     * Returns the range sum, min, max in the rectangle (row1, column1) to (row2, column2)
     * O(lg r * lg c), where r is the number of rows and c is the number of columns
     */
    public Node rangeQuery(int row1,int column1, int row2, int column2) {
        int minRow = Math.min(row1, row2);
        int maxRow = Math.max(row1, row2);
        int minColumn = Math.min(column1, column2);
        int maxColumn = Math.max(column1, column2);
        return rangeQuery(1, 1, rowsSize, minRow, minColumn, maxRow, maxColumn);
    }

    private Node rangeQuery(int rowIndex, int start, int end, int row1, int column1, int row2, int column2) {
        if (row2 < start || end < row1) {
            return new Node(0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        if (row1 <= start && end <= row2) {
            return rangeQuery(1, 1, columnsSize, column1, column2, rowIndex);
        }

        int middle = (start + end) / 2;
        Node leftNode = rangeQuery(2 * rowIndex, start, middle, row1, column1, row2, column2);
        Node rightNode = rangeQuery(2 * rowIndex + 1, middle + 1, end, row1, column1, row2, column2);
        return mergeNodeFromChildren(leftNode, rightNode);
    }

    private Node rangeQuery(int columnIndex, int start, int end, int row1, int row2, int rowIndex) {
        if (row2 < start || end < row1) {
            return new Node(0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        if (row1 <= start && end <= row2) {
            return segmentTree2D[rowIndex][columnIndex];
        }
        int middle = (start + end) / 2;
        Node leftNode = rangeQuery(2 * columnIndex, start, middle, row1, row2, rowIndex);
        Node rightNode = rangeQuery(2 * columnIndex + 1, middle + 1, end, row1, row2, rowIndex);
        return mergeNodeFromChildren(leftNode, rightNode);
    }

    /*
     * Updates coordinate (row, column) to value
     * O(lg r * lg c), where r is the number of rows and c is the number of columns
     */
    public void update(int row, int column, int value) {
        updateColumn(1, 1, columnsSize, row, column, value);
    }

    private void updateColumn(int column, int low, int high, int rowIndex, int columnIndex, int value) {
        if (columnIndex < low || columnIndex > high) {
            return;
        }

        if (low == high){
            Node node = baseSegmentTree[rowIndex][column];
            node.sum = value;
            node.minValue = value;
            node.maxValue = value;
            updateRow(1,1, rowsSize, rowIndex, column);
            return;
        }

        int middle = (low + high) / 2;
        updateColumn(2 * column, low, middle, rowIndex, columnIndex, value);
        updateColumn(2 * column + 1, middle + 1, high, rowIndex, columnIndex, value);

        Node leftNode = baseSegmentTree[rowIndex][2 * column];
        Node rightNode = baseSegmentTree[rowIndex][2 * column + 1];
        baseSegmentTree[rowIndex][column] = mergeNodeFromChildren(leftNode, rightNode);
        updateRow(1, 1, rowsSize, rowIndex, column);
    }

    private void updateRow(int row, int low, int high, int rowIndex, int columnIndex) {
        if (rowIndex < low || rowIndex > high) {
            return;
        }

        if (low == high) {
            segmentTree2D[row][columnIndex] = copyNode(segmentTree2D[row][columnIndex],
                    baseSegmentTree[rowIndex][columnIndex]);
            return;
        }

        int middle = (low + high) / 2;
        updateRow(2 * row, low, middle, rowIndex, columnIndex);
        updateRow(2 * row + 1, middle + 1, high, rowIndex, columnIndex);

        Node leftNode = segmentTree2D[2 * row][columnIndex];
        Node rightNode = segmentTree2D[2 * row + 1][columnIndex];
        segmentTree2D[row][columnIndex] = mergeNodeFromChildren(leftNode, rightNode);
    }

    private Node mergeNodeFromChildren(Node leftNode, Node rightNode) {
        if (leftNode == null || rightNode == null) {
            return new Node(0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        long sum = leftNode.sum + rightNode.sum;
        int min = Math.min(leftNode.minValue, rightNode.minValue);
        int max = Math.max(leftNode.maxValue, rightNode.maxValue);
        return new Node(sum, min, max);
    }

    private Node copyNode(Node originalNode, Node nodeToCopy) {
        if (nodeToCopy == null) {
            return null;
        }
        if (originalNode == null) {
            return new Node(nodeToCopy.sum, nodeToCopy.minValue, nodeToCopy.maxValue);
        }
        originalNode.sum = nodeToCopy.sum;
        originalNode.minValue = nodeToCopy.minValue;
        originalNode.maxValue = nodeToCopy.maxValue;
        return originalNode;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                { 1, 2, 3, 4 },
                { 5, 6, 7, 8 },
                { 1, 7, 5, 9 },
                { 3, 0, 6, 2 },
        };
        SegmentTree2D segmentTree2D = new SegmentTree2D(matrix);

        System.out.println("The sum of the submatrix (2, 2), (3,3) is "
                + segmentTree2D.rangeQuery(2, 2, 3, 3).sum + " Expected: 25");
        System.out.println("The min of the submatrix (1, 2), (4,4) is "
                + segmentTree2D.rangeQuery(1, 2, 4, 4).minValue + " Expected: 0");
        System.out.println("The max of the submatrix (1, 2), (4,4) is "
                + segmentTree2D.rangeQuery(1, 2, 4, 4).maxValue + " Expected: 9");

        segmentTree2D.update(3, 2, 100);

        System.out.println("The sum of the submatrix (2, 2), (3,3) is "
                + segmentTree2D.rangeQuery(2, 2, 3, 3).sum + " Expected: 118");
        System.out.println("The max of the submatrix (1, 2), (4,4) is "
                + segmentTree2D.rangeQuery(1, 2, 4, 4).maxValue + " Expected: 100");
    }
}
