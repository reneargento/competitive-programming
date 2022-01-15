package algorithms.recursion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 26/09/19.
 */
// Get all subsets of a set
public class PowerSet {

    // O(n * 2^n) runtime
    // O(n * 2^n) space
    public static Set<Set<Integer>> powerSet(List<Integer> items) {
        Set<Set<Integer>> subsets = new HashSet<>();
        int numberOfSubsets = 1 << items.size(); // There are 2^n subsets

        for (int subsetId = 0; subsetId < numberOfSubsets; subsetId++) {
            Set<Integer> subset = new HashSet<>();
            int bit = 0;

            for (int id = subsetId; id > 0; id >>= 1) {
                if ((id & 1) == 1) {
                    subset.add(items.get(bit));
                }
                bit++;
            }

            subsets.add(subset);
        }
        return subsets;
    }
}
