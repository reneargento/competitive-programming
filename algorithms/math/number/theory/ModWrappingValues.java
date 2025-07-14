package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 20/03/21.
 */
// Returns the result of a mod operation on an index when wrapping the maximum or minimum value.
// Based on https://stackoverflow.com/questions/1082917/mod-of-negative-number-is-melting-my-brain
public class ModWrappingValues {

    private static int wrapIndex(int index, int maxValue) {
        int modResult = index % maxValue;
        return modResult < 0 ? maxValue + modResult : modResult;
    }

    // One-liner but uses mod operation twice
    private static int wrapIndex2(int index, int maxValue) {
        return ((index % maxValue) + maxValue) % maxValue;
    }
}
