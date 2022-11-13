package algorithms.matrix;

/**
 * Created by Rene Argento on 27/09/22.
 */
// Based on https://math.stackexchange.com/questions/732679/how-to-rotate-a-matrix-by-45-degrees
public class Rotate45Degrees {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static Cell rotateCell(int row, int column, int rowsNumber) {
        int rotatedRow = row + column - 1;
        int rotatedColumn = column - row + rowsNumber;
        return new Cell(rotatedRow, rotatedColumn);
    }
}
