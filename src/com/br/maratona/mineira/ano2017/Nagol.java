package com.br.maratona.mineira.ano2017;

import java.util.Scanner;

/**
 * Created by rene on 27/05/17.
 */

/**
 * Nagol, a former superhero who everyone thinks is dead, lives in a quiet inner city of Minas.
 * After retiring from the hectic life of a hero, he now works as a tile designer.
 * We will imagine a wall of tiles as a grid of L * C, L lines identified from 0 to L-1 and C columns identified from 0 to C-1.
 * Nagol has its own style of design, it uses its hands to "scratch" each of the tiles and turn the final wall into a great work of art.

 The order he uses to do this is always the same, starts from the first row and scribbles all the C columns from left to right,
 then goes to the second row and scribbles all C columns in the same way, it repeats until Finish the L lines.
 An important detail is that he never makes two straight strokes with the same hand, alternating starting always with the right hand.
 Here is an example of a final wall where L = 2 and C = 3: (Picture)

 Your task is, given the size of the wall (L and C) and the position of a specific tile (X and Y),
 tell which hand Nagol will use to scratch it.

 Input
 Each row of the input has four integers L (0 < L, C < 105), X (0 ≤ X < L), Y (0 ≤ Y < C), all previously described.

 Output
 Display a single line with the message "Direita" (right, in Portuguese) if it has scratched the tile with your right hand or
 "Esquerda" (left, in Portuguese), otherwise.
 */

//https://www.urionlinejudge.com.br/judge/en/challenges/view/266/12
public class Nagol {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        int rowToSearch = scanner.nextInt();
        int columnToSearch = scanner.nextInt();

        int totalTilesScratched = rowToSearch * columns + columnToSearch;

        if (totalTilesScratched % 2 != 0) {
            System.out.println("Esquerda");
        } else {
            System.out.println("Direita");
        }
    }

}
