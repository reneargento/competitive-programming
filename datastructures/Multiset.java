package datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Rene Argento on 05/06/21.
 */
public class Multiset<Key extends Comparable<Key>> {
    private final TreeMap<Key, Integer> multiset;

    Multiset() {
        multiset = new TreeMap<>();
    }

    public void add(Key key) {
        int keyFrequency = multiset.getOrDefault(key, 0);
        multiset.put(key, keyFrequency + 1);
    }

    public void delete(Key key) {
        int keyFrequency = multiset.get(key);
        if (keyFrequency == 1) {
            multiset.remove(key);
        } else {
            multiset.put(key, keyFrequency - 1);
        }
    }

    public void deleteAll(Key key) {
        multiset.remove(key);
    }

    public Key firstKey() {
        return multiset.firstKey();
    }

    public Key lastKey() {
        return multiset.lastKey();
    }

    public Key deleteFirstKey() {
        Key firstKey = firstKey();
        delete(firstKey);
        return firstKey;
    }

    public Key deleteLastKey() {
        Key lastKey = lastKey();
        delete(lastKey);
        return lastKey;
    }

    public List<Key> keysInSortedOrder() {
        List<Key> keys = new ArrayList<>();

        for (Key key : multiset.keySet()) {
            int frequency = multiset.get(key);
            for (int i = 0; i < frequency; i++) {
                keys.add(key);
            }
        }
        return keys;
    }
}
