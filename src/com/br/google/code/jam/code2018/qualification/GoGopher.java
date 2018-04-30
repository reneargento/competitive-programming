package com.br.google.code.jam.code2018.qualification;

import java.util.*;

/**
 * Created by Rene Argento on 06/04/18.
 */
// https://codejam.withgoogle.com/2018/challenges/00000000000000cb/dashboard/0000000000007a30
public class GoGopher {

    private static class Cell {
        int x;
        int y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return 1000 * x + y;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Cell)) {
                return false;
            }

            Cell otherCell = (Cell) other;
            return this.x == otherCell.x && this.y == otherCell.y;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int area = scanner.nextInt();

            List<Cell> options = new ArrayList<>();
            int maxColumn;

            if (area == 20) {
                maxColumn = 13;
            } else {
                maxColumn = 58;
            }

            for (int row = 6; row <= 7; row++) {
                for (int column = 11; column <= maxColumn; column++) {
                    options.add(new Cell(row, column));
                }
            }

            Set<Cell> selectedCells = new HashSet<>();
            Random random = new Random();

            while (true) {
                int randomIndex = random.nextInt(options.size());

                Cell currentCell = options.get(randomIndex);
                System.out.println(currentCell.x + " " + currentCell.y);

                int selectedCellX = scanner.nextInt();
                int selectedCellY = scanner.nextInt();

                if ((selectedCellX == -1 || selectedCellY == -1)
                        || (selectedCellX == 0 || selectedCellY == 0)) {
                    break;
                }

                selectedCells.add(new Cell(selectedCellX, selectedCellY));
                updateOptions(options, selectedCells);
            }
        }

    }

    private static void updateOptions(List<Cell> options, Set<Cell> selectedCells) {
        Set<Cell> cellsToRemove = new HashSet<>();

        for(Cell cell : options) {
            Cell cell1 = new Cell(cell.x - 1, cell.y - 1);
            Cell cell2 = new Cell(cell.x - 1, cell.y);
            Cell cell3 = new Cell(cell.x - 1, cell.y + 1);
            Cell cell4 = new Cell(cell.x, cell.y - 1);
            Cell cell5 = new Cell(cell.x, cell.y + 1);
            Cell cell6 = new Cell(cell.x + 1, cell.y - 1);
            Cell cell7 = new Cell(cell.x + 1, cell.y);
            Cell cell8 = new Cell(cell.x + 1, cell.y + 1);

            if (selectedCells.contains(cell1)
                    && selectedCells.contains(cell2)
                    && selectedCells.contains(cell3)
                    && selectedCells.contains(cell4)
                    && selectedCells.contains(cell5)
                    && selectedCells.contains(cell6)
                    && selectedCells.contains(cell7)
                    && selectedCells.contains(cell8)
                    && selectedCells.contains(cell)) {
                cellsToRemove.add(cell);
            }
        }

        options.removeAll(cellsToRemove);
    }

}
