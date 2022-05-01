package algorithms.array;

import java.util.Random;

/**
 * Created by Rene Argento on 15/05/20.
 */
// In-place shuffle in O(n)
public class Shuffle {

    public static void shuffle(int[] array) {
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomIndex = i + random.nextInt(array.length - i);

            int randomElement = array[randomIndex];
            array[randomIndex] = array[i];
            array[i] = randomElement;
        }
    }

}
