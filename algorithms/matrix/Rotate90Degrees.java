package algorithms.matrix;

/**
 * Created by Rene Argento on 12/02/21.
 */
public class Rotate90Degrees {

    public static void main(String[] args) {
        char[][] matrix = {
                {'1', '2', '3'},
                {'4', '5', '6'},
                {'7', '8', '9'}
        };
        char[][] rotated = rotate90Degrees(matrix);

        System.out.println("Rotated matrix");
        for (int row = 0; row < rotated.length; row++) {
            for (int column = 0; column < rotated[0].length; column++) {
                System.out.print(rotated[row][column]);

                if (column != rotated[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static char[][] rotate90Degrees(char[][] matrix) {
        char[][] rotated = new char[matrix.length][matrix[0].length];

        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[0].length; column++) {
                int rotatedColumn = matrix[0].length - 1 - row;
                rotated[column][rotatedColumn] = matrix[row][column];
            }
        }
        return rotated;
    }
}
