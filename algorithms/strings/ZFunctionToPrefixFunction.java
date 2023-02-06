package algorithms.strings;

/**
 * Created by Rene Argento on 27/01/18.
 */
// Based on https://codereview.stackexchange.com/questions/87349/prefix-and-z-functions-in-c-string-algorithms
public class ZFunctionToPrefixFunction {

    private static int[] zFunctionToPrefix(int[] zFunction) {
        int[] prefixFunction = new int[zFunction.length];

        for(int i = 1; i < zFunction.length; i++) {
            prefixFunction[i + zFunction[i] - 1] = Math.max(prefixFunction[i + zFunction[i] - 1], zFunction[i]);
        }
        for(int i = zFunction.length - 2; i >= 0; i--) {
            prefixFunction[i] = Math.max(prefixFunction[i + 1] - 1, prefixFunction[i]);
        }

        return prefixFunction;
    }
}
