package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 *
 * @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;
    private int NEW_SIZE = 16;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /**
     * Computes the hash function of the given key. Consists of
     * computing the hashcode, followed by modding by the number of buckets.
     * To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return this.buckets[hash(key)].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (get(key) != null) {
            this.buckets[hash(key)].remove(key);
            size -= 1;
        }
        this.buckets[hash(key)].put(key, value);
        size += 1;
        if (loadFactor() > MAX_LF) {
            NEW_SIZE *= 2;
            MyHashMap<K, V> newmap = new MyHashMap<K, V>();
            newmap.buckets = new ArrayMap[NEW_SIZE];
            newmap.clear();
            for (int i = 0; i < this.buckets.length; i += 1) {
                Iterator<K> iterator = this.buckets[i].iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    newmap.put(key, get(key));
                }
            }
            this.buckets = newmap.buckets;
            this.size = newmap.size;
        }
        //直接插入构建一个新对象就好了
//            NEW_SIZE *= 2;
//            newbuckets = new ArrayMap[NEW_SIZE];
//            for (int i = 0; i < this.buckets.length; i += 1) {
//                Iterator<K> newiterator = this.buckets[i].iterator();
//                while (newiterator.hasNext()) {
//                    key = newiterator.next();
//                    if (key == null) {
//                        break;
//                    }
//                    System.out.println(key);
//                    if (get(key) != null) {
//                        this.newbuckets[Math.floorMod(key.hashCode(), newbuckets.length)].remove(key);
//                    }
//                }
//            }
//            buckets = newbuckets;
//        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V ans = this.buckets[hash(key)].remove(key);
        if (ans != null) {
            size -= 1;
        }
        return ans;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (get(key) == value) {
            return remove(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
