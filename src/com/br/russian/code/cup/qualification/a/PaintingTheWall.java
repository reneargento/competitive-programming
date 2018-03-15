package com.br.russian.code.cup.qualification.a;

import java.util.*;

/**
 * Created by rene on 02/04/17.
 */

/**
 * Little girl Masha is looking at the wall in her room.
 * The wall is tiled with square tiles, but some of the tiles are replaced with lamps.
 * So it is possible to consider the wall to be a rectangle of n × m, some cells contain tiles, other cells contain lamps.

 Masha has paints of k different colors.
 Consider continuous segments of tiles, having an edge of the wall or a lamp at each of its ends.
 Masha wants to paint all tiles in such way, that any such segment has all tiles painted in different colors.
 Masha will not paint lamps. She doesn't have to use all the colors.

 Help Masha to paint the wall.

 Input format
 Input contains several test cases. The first line contains the number of test cases t.

 Each test case is described by several lines.
 The first line contains three integers:
 n, m, k (1 ≤ n, m ≤ 100, 1 ≤ k ≤ max(n, m)) — the size of the wall and the number of paints Masha has.

 The following n lines contain m integers aij each:

 aij = 0 means that the position (i, j) contains the lamp;
 aij = 1 means that the position (i, j) contains the tile.
 The total number of tiles and lamps in all test cases of one input doesn't exceed 10^5.

 Output format
 For each test case first print the answer:

 NO, if there is no way to paint the wall.
 YES, if there is at least one way to paint the wall.
 In this case the following n lines must contain m integers bij each — the color of the tile at position (i, j), or 0,
 if there is a lamp at this position.
 If there are several ways to paint the wall, you can output any one.


 Input data
 2
 4 3 2
 0 1 0
 1 0 1
 1 0 1
 0 1 0
 3 4 2
 0 1 0 1
 1 0 1 1
 1 1 1 0

 Output data
 YES
 0 2 0
 2 0 2
 1 0 1
 0 1 0
 NO
 */
public class PaintingTheWall {

    private class MatrixCell {
        int row, column;

        MatrixCell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static int maxRow;
    private static int maxColumn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfTests = Integer.parseInt(sc.nextLine());
        PaintingTheWall paintingTheWall = new PaintingTheWall();

        for(int i = 0; i < numberOfTests; i++) {
            int wallLines = sc.nextInt();
            int wallColumns = sc.nextInt();
            int numberOfColors = sc.nextInt();

            int[][] wall = new int[wallLines][wallColumns];

            for(int j = 0; j < wallLines; j++) {
                for(int k = 0; k < wallColumns; k++) {
                    wall[j][k] = sc.nextInt();
                }
            }

            int[][] painting = paintingTheWall.paintTheWallWithoutLamps(wall, numberOfColors + 1);
            paintingTheWall.addLamps(wall, painting);
            boolean canPaint = paintingTheWall.checkIfItIsPossibleToPaint(wall, painting, numberOfColors);

            if (!canPaint) {
                System.out.println("NO");
            } else {
                System.out.println("YES");

                for(int j = 0; j < painting.length; j++) {
                    for(int k = 0; k < painting[0].length; k++) {
                        System.out.print(painting[j][k]);
                        if (k != wallColumns - 1) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    private int[][] paintTheWallWithoutLamps(int[][] wall, int numberOfColors) {
        int[][] painting = new int[wall.length][wall[0].length];

        int currentColor = 1;

        //Paint the first row
        for(int i = 0; i < painting[0].length; i++) {
            painting[0][i] = currentColor % numberOfColors;
            currentColor++;

            if (currentColor % numberOfColors == 0) {
                currentColor++;
            }
        }

        //Paint the rest
        for(int column = 0; column < painting[0].length; column++) {
            currentColor = painting[0][column];

            for(int row = 1; row < painting.length; row++) {
                currentColor++;

                if (currentColor % numberOfColors == 0) {
                    currentColor++;
                }

                painting[row][column] = currentColor % numberOfColors;
            }
        }

        return painting;
    }

    private void addLamps(int[][] wall, int[][] painting) {
        for(int i = 0; i < wall.length; i++) {
            for(int j = 0; j < wall[0].length; j++) {
                if (wall[i][j] == 0) {
                    painting[i][j] = 0;
                }
            }
        }
    }

    private boolean checkIfItIsPossibleToPaint(int[][] wall, int[][] painting, int numberOfColors) {
        maxRow = wall.length - 1;
        maxColumn = wall[0].length - 1;

        boolean[][] visited = new boolean[wall.length][wall[0].length];

        //Vertical search
        for(int i = 0; i < wall.length; i++) {
            for(int j = 0; j < wall[0].length; j++) {

                if (wall[i][j] != 0 && !visited[i][j]) {
                    boolean canPaint = bfs(wall, painting, visited, i, j, numberOfColors, true);

                    if (!canPaint) {
                        return false;
                    }
                }
            }
        }

        //Horizontal search
        //Reset visited matrix
        visited = new boolean[wall.length][wall[0].length];

        for(int i = 0; i < wall.length; i++) {
            for(int j = 0; j < wall[0].length; j++) {

                if (wall[i][j] != 0 && !visited[i][j]) {
                    boolean canPaint = bfs(wall, painting, visited, i, j, numberOfColors, false);

                    if (!canPaint) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isTheCellSafe(int wall[][], int row, int column) {
        return row >= 0 && row <= maxRow
                && column >= 0 && column <= maxColumn
                && wall[row][column] != 0;
    }

    private boolean bfs(int[][] matrix, int[][] painting, boolean[][] visited, int row, int column, int numberOfColors,
                        boolean vertical) {

        //Used to generate combinations of all neighbors: [0,-1], [-1,0] [1,0] [0,1]
//        int[] neighborRowCombination = {0, -1, 1, 0};
//        int[] neighborColumnCombination = {-1, 0, 0, 1};
        int[] neighborRowCombination;
        int[] neighborColumnCombination;

        if (vertical) {
            neighborRowCombination = new int[]{-1, 1};
            neighborColumnCombination = new int[]{0, 0};
        } else {
            neighborRowCombination = new int[]{0, 0};
            neighborColumnCombination = new int[]{-1, 1};
        }

        Set<Integer> colorsUsed = new HashSet<>();

        Queue<MatrixCell> queue = new LinkedList<>();
        queue.add(new MatrixCell(row, column));

        while(queue.size() > 0) {

            MatrixCell matrixCell = queue.remove();
            int currentRow = matrixCell.row;
            int currentColumn = matrixCell.column;

            //Check to see if it is possible to paint
            if (matrix[currentRow][currentColumn] != 0) {
                for(int colors = 1; colors <= numberOfColors; colors++) {
                    if (colorsUsed.contains(colors) && colors == painting[currentRow][currentColumn]) {
                        return false;
                    }
                }
            }

            visited[currentRow][currentColumn] = true;
            colorsUsed.add(painting[currentRow][currentColumn]);

            //Check all neighbors
            for(int i = 0; i < 2; i++) {
                int neighborRow = currentRow + neighborRowCombination[i];
                int neighborColumn = currentColumn + neighborColumnCombination[i];

                if (isTheCellSafe(matrix, neighborRow, neighborColumn) && !visited[neighborRow][neighborColumn]) {
                    queue.add(new MatrixCell(neighborRow, neighborColumn));
                }
            }
        }
        return true;
    }
}
