package datastructures;

import java.util.Objects;

/**
 * Created by Rene Argento on 28/10/20.
 */
public class Cell {
    int row;
    int column;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Cell cell = (Cell) other;
        return row == cell.row && column == cell.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}