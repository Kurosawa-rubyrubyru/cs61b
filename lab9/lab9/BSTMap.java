package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (key.compareTo(p.key) > 0) {
            return getHelper(key, p.right);
        } else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return p.value;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private void putHelper(K key, V value, Node p) {
        if (key.compareTo(p.key) > 0) {
            if (p.right != null) {
                putHelper(key, value, p.right);
            } else {
                p.right = new Node(key, value);
                size += 1;
            }
        } else if (key.compareTo(p.key) < 0) {
            if (p.left != null) {
                putHelper(key, value, p.left);
            } else {
                p.left = new Node(key, value);
                size += 1;
            }
        } else {
            p.value = value;
        }
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (size == 0) {
            Node n = new Node(key, value);
            root = n;
            size += 1;
        } else {
            putHelper(key, value, root);
        }
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

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (root == null) {
            return null;
        }
        Node goal = root;
        Node parent = null;
        while (goal != null) {
            if (key.compareTo(goal.key) > 0) {
                parent = goal;
                goal = goal.right;
            } else if (key.compareTo(goal.key) < 0) {
                parent = goal;
                goal = goal.left;
            } else {
                return removeHelper(parent, goal);
            }
        }
        return null;
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/

    private V removeHelper(Node parent, Node goal) {
        if (goal.left == null) {
            if (goal == root) {
                root = goal.right;
            } else if (goal == parent.left) {
                parent.left = goal.right;
            } else if (goal == parent.right) {
                parent.right = goal.right;
            }
        } else if (goal.right == null) {
            if (goal == root) {
                root = goal.left;
            } else if (goal == parent.left) {
                parent.left = goal.left;
            } else if (goal == parent.right) {
                parent.right = goal.left;
            }
        } else {
            Node target = goal.right;
            Node targetParent = goal;
            while (target.left != null) {
                targetParent = target;
                target = target.left;
            }
            goal.value = target.value;
            goal.key = target.key;
            if (target == targetParent.left) {
                targetParent.left = target.right;
            } else {
                targetParent.right = target.right;
            }
        }
        return goal.value;
    }

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
