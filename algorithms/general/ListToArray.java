package algorithms.general;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 09/05/25.
 */
// Based on https://stackoverflow.com/questions/9572795/convert-list-to-array-in-java
public class ListToArray {

    private static Integer[] listToArray(List<Integer> list) {
        return list.toArray(new Integer[0]);
    }

    private static List<Integer> arrayToList(Integer[] array) {
        return Arrays.asList(array);
    }
}
