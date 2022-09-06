package hw3.hash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int bucketNum;
        HashMap<Integer, Integer> times = new HashMap<Integer, Integer>();
        HashSet<Integer> keyset = new HashSet<Integer>();
        for (Oomage o : oomages) {
            bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            keyset.add(bucketNum);
            if (times.containsKey(bucketNum)) {
                times.put(bucketNum, times.get(bucketNum) + 1);
            } else {
                times.put(bucketNum, 1);
            }
        }
        int sum = 0;
        for (Integer key : keyset) {
            sum += times.get(key);
        }
        for (Integer key : keyset) {
            try {
                assertTrue((double) times.get(key) > 0.02 * sum);
                assertTrue((double) times.get(key) < 0.4 * sum);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
