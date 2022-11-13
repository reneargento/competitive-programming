package datastructures;

/**
 * Created by Rene Argento on 30/09/22.
 */
// Data structure that answers RMQ (range minimum queries) in O(1). It builds in O(N lg N).
// Used for static arrays.
// Based on https://www.geeksforgeeks.org/sparse-table/
public class SparseTable {
    private final int[][] sparseTable;

    SparseTable(int[] values) {
        sparseTable = buildSparseTable(values);
    }

    // O(N lg N)
    private int[][] buildSparseTable(int[] values) {
        int columns = log2(values.length) + 1;
        int[][] sparseTable = new int[values.length][columns];

        for (int row = 0; row < sparseTable.length; row++) {
            sparseTable[row][0] = values[row];
        }

        for (int column = 1; column < sparseTable[0].length; column++) {
            // Compute minimum value for all intervals of size 2^column
            for (int row = 0; row + ((1 << column) - 1) < sparseTable.length; row++) {
                int value1 = sparseTable[row][column - 1];
                int value2 = sparseTable[row + (1 << (column - 1))][column - 1];
                sparseTable[row][column] = Math.min(value1, value2);
            }
        }
        return sparseTable;
    }

    // O(1)
    public int rangeMinQuery(int start, int end) {
        int rangeLog = log2(end - start + 1);
        // Compute the minimum value of the first 2^rangeLog elements and the last 2^rangeLog elements in range.
        int value1 = sparseTable[start][rangeLog];
        int value2 = sparseTable[end - (1 << rangeLog) + 1][rangeLog];
        return Math.min(value1, value2);
    }

    private int log2(int value) {
        return (int) (Math.log(value) / Math.log(2));
    }

    // Used for testing
    public static void main(String[] args) {
        int[] values = { 10, 3, 5, 1, 22, 2 };
        SparseTable sparseTable = new SparseTable(values);

        int minimum1 = sparseTable.rangeMinQuery(0, 5);
        System.out.println("Minimum 1: " + minimum1 + " Expected: 1");

        int minimum2 = sparseTable.rangeMinQuery(0, 2);
        System.out.println("Minimum 2: " + minimum2 + " Expected: 3");

        int minimum3 = sparseTable.rangeMinQuery(4, 4);
        System.out.println("Minimum 3: " + minimum3 + " Expected: 22");

        int minimum4 = sparseTable.rangeMinQuery(4, 5);
        System.out.println("Minimum 4: " + minimum4 + " Expected: 2");

        int minimum5 = sparseTable.rangeMinQuery(2, 4);
        System.out.println("Minimum 5: " + minimum5 + " Expected: 1");
    }
}
