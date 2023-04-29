package algorithms.graph;

import java.io.IOException;
import java.util.*;

/**
 * Created by Rene Argento on 19/03/23.
 */
// Given two graphs, are they isomorphic?
// By isomorphic it means that they have the same connected components, with the same formats.
// Connected components are equal if they have the same format, irrespective of translations, rotations or reflections.
// O(V^2) runtime
// Based on UVa-10707 2D-Nim problem
public class GraphIsomorphism {

    private static class Vertex implements Comparable<Vertex> {
        int row;
        int column;

        public Vertex(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int compareTo(Vertex other) {
            if (row != other.row) {
                return Integer.compare(row, other.row);
            }
            return Integer.compare(column, other.column);
        }


        @Override
        public boolean equals(Object object) {
            Vertex otherVertex = (Vertex) object;
            return row == otherVertex.row && column == otherVertex.column;
        }
    }

    private static class ComponentFormat {
        List<Vertex> vertices;

        public ComponentFormat() {
            vertices = new ArrayList<>();
        }

        public ComponentFormat(List<Vertex> vertices) {
            this.vertices = new ArrayList<>();
            for (Vertex vertex : vertices) {
                this.vertices.add(new Vertex(vertex.row, vertex.column));
            }
        }

        void adjustValues() {
            Collections.sort(vertices);
            adjustOffsets();
        }

        private void adjustOffsets() {
            if (vertices.get(0).row < 0) {
                int offset = vertices.get(0).row * -1;
                for (int i = 0; i < vertices.size(); i++) {
                    vertices.get(i).row += offset;
                }
            }

            List<Integer> columns = new ArrayList<>();
            for (Vertex vertex : vertices) {
                columns.add(vertex.column);
            }
            Collections.sort(columns);

            if (columns.get(0) < 0) {
                int offset = columns.get(0) * -1;
                for (int i = 0; i < vertices.size(); i++) {
                    vertices.get(i).column += offset;
                }
            }
        }

        boolean isTheSame(ComponentFormat otherComponent) {
            if (vertices.size() != otherComponent.vertices.size()) {
                return false;
            }

            List<Vertex> reverseVertices = new ArrayList<>();
            for (Vertex vertex : vertices) {
                reverseVertices.add(new Vertex(vertex.column, vertex.row));
            }
            Collections.sort(reverseVertices);

            // Compare all 8 format representations of the component
            return isTheSameFormat(vertices, otherComponent)
                    || isTheSameFormat(reverseVertices, otherComponent);
        }

        private boolean isTheSameFormat(List<Vertex> vertices, ComponentFormat otherComponent) {
            ComponentFormat componentFormat1 = new ComponentFormat(vertices);
            if (isTheSame(componentFormat1, otherComponent)) {
                return true;
            }

            ComponentFormat componentFormat2 = new ComponentFormat(vertices);
            componentFormat2.reverseValues(true);
            if (isTheSame(componentFormat2, otherComponent)) {
                return true;
            }

            ComponentFormat componentFormat3 = new ComponentFormat(vertices);
            componentFormat3.reverseValues(false);
            if (isTheSame(componentFormat3, otherComponent)) {
                return true;
            }

            ComponentFormat componentFormat4 = new ComponentFormat(vertices);
            componentFormat4.reverseValues(true);
            componentFormat4.reverseValues(false);
            return isTheSame(componentFormat4, otherComponent);
        }

        private void reverseValues(boolean reverseRows) {
            if (!reverseRows) {
                Collections.sort(vertices, new Comparator<Vertex>() {
                    @Override
                    public int compare(Vertex vertex1, Vertex vertex2) {
                        return Integer.compare(vertex1.column, vertex2.column);
                    }
                });
            }

            List<Integer> reversedValues = new ArrayList<>();
            int currentReversedValue = 0;

            for (int i = vertices.size() - 1; i >= 0; i--) {
                reversedValues.add(currentReversedValue);

                if (i > 0) {
                    if (reverseRows) {
                        if (vertices.get(i).row != vertices.get(i - 1).row) {
                            currentReversedValue++;
                        }
                    } else {
                        if (vertices.get(i).column != vertices.get(i - 1).column) {
                            currentReversedValue++;
                        }
                    }
                }
            }

            int reverseValueIndex = 0;
            for (int i = vertices.size() - 1; i >= 0; i--) {
                if (reverseRows) {
                    vertices.get(i).row = reversedValues.get(reverseValueIndex);
                } else {
                    vertices.get(i).column = reversedValues.get(reverseValueIndex);
                }
                reverseValueIndex++;
            }
            Collections.sort(vertices);
        }

        private boolean isTheSame(ComponentFormat componentFormat1, ComponentFormat componentFormat2) {
            for (int i = 0; i < componentFormat1.vertices.size(); i++) {
                if (!componentFormat1.vertices.get(i).equals(componentFormat2.vertices.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        // . . . . X . . .
        // . . . X . . . .
        // . . . . . X . .
        // . X X . . X . .
        // X X X . . X . X
        boolean[][] board1 = {
                { false, false, false, false, true, false, false, false },
                { false, false, false, true, false, false, false, false },
                { false, false, false, false, false, true, false, false },
                { false, true, true, false, false, true, false, false },
                { true, true, true, false, false, true, false, true }
        };

        // X X . . . . . X
        // X X . X . . . .
        // X . . . . X X X
        // . X . . . . . .
        // . . . . . . . .
        boolean[][] board2 = {
                { true, true, false, false, false, false, false, true },
                { true, true, false, true, false, false, false, false },
                { true, false, false, false, false, true, true, true },
                { false, true, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false }
        };

        boolean isIsomorphic = isIsomorphic(board1, board2);
        System.out.println("Is isomorphic? " + (isIsomorphic ? "YES" : "NO"));
        System.out.println("Expected: YES");
    }

    private static boolean isIsomorphic(boolean[][] board1, boolean[][] board2) {
        List<ComponentFormat> components1 = computeComponents(board1);
        List<ComponentFormat> components2 = computeComponents(board2);

        if (components1.size() != components2.size()) {
            return false;
        }

        Set<Integer> componentIDsMatched = new HashSet<>();
        for (int componentID1 = 0; componentID1 < components1.size(); componentID1++) {
            boolean matched = false;

            for (int componentID2 = 0; componentID2 < components2.size(); componentID2++) {
                if (componentIDsMatched.contains(componentID2)) {
                    continue;
                }

                if (components1.get(componentID1).isTheSame(components2.get(componentID2))) {
                    matched = true;
                    componentIDsMatched.add(componentID2);
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }
        return true;
    }

    private static List<ComponentFormat> computeComponents(boolean[][] board) {
        List<ComponentFormat> components = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column]) {
                    ComponentFormat componentFormat = new ComponentFormat();
                    floodFill(board, neighborRows, neighborColumns, componentFormat, row, column, row, column);
                    componentFormat.adjustValues();
                    components.add(componentFormat);
                }
            }
        }
        return components;
    }

    private static void floodFill(boolean[][] board, int[] neighborRows, int[] neighborColumns,
                                  ComponentFormat componentFormat, int originRow, int originColumn, int row,
                                  int column) {
        int rowFormatValue = row - originRow;
        int columnFormatValue = column - originColumn;
        componentFormat.vertices.add(new Vertex(rowFormatValue, columnFormatValue));

        board[row][column] = false;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(board, neighborRow, neighborColumn) && board[neighborRow][neighborColumn]) {
                floodFill(board, neighborRows, neighborColumns, componentFormat, originRow, originColumn, neighborRow,
                        neighborColumn);
            }
        }
    }

    private static boolean isValid(boolean[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
    }
}
