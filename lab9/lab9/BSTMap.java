package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Shawn
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

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else {
            int cmp = key.compareTo(p.key);
            if (cmp == 0) {
                return p.value;
            } else if (cmp < 0) {
                return getHelper(key, p.left);
            } else {
                return getHelper(key, p.right);
            }
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        } else {
            int cmp = key.compareTo(p.key);
            if (cmp == 0) {
                p.value = value;
            } else if (cmp < 0) {
                p.left = putHelper(key, value, p.left);
            } else {
                p.right = putHelper(key, value, p.right);
            }
            return p;
        }
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /** Adds each keys saved in the subtree rooted in P into keySet recursively. */
    private void keySetHelper(Set<K> keySet, Node p) {
        if (p != null) {
            keySet.add(p.key);
            keySetHelper(keySet, p.left);
            keySetHelper(keySet, p.right);
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        keySetHelper(keySet, root);
        return keySet;
    }

    private int childCounter(Node p) {
        int childNum = 0;
        if (p.left != null) {
            childNum += 1;
        }
        if (p.right != null) {
            childNum += 1;
        }
        return childNum;
    }

    private Node singleChild(Node p) {
        if (p.left != null) {
            return p.left;
        } else {
            return p.right;
        }
    }

    private Node maxNode(Node p) {
        if (p.right == null) {
            return p;
        } else {
            return maxNode(p.right);
        }
    }

    private Node minNode(Node p) {
        if (p.left == null) {
            return p;
        } else {
            return minNode(p.left);
        }
    }

    private Node predecessor(Node p) {
        return maxNode(p.left);
    }

    private Node successor(Node p) {
        return minNode(p.right);
    }

    /** Removes KEY from the subtree rooted in PRESENT with parent node PARENT
     *  if present returns VALUE removed,
     *  null on failed removal.
     *  This remove operation was implemented by using "Hibbard deletion".
     */
    private V removeHelper(K key, Node present, Node parent, boolean left) {
        if (present == null) {
            return null;
        } else {
            int cmp = key.compareTo(present.key);
            if (cmp == 0) {
                V value = present.value;
                int childNum = childCounter(present);
                if (childNum == 0) {
                    if (parent == null) {
                        root = null;
                    } else {
                        if (left) {
                            parent.left = null;
                        } else {
                            parent.right = null;
                        }
                    }
                } else if (childNum == 1) {
                    if (parent == null) {
                        root = singleChild(present);
                    } else {
                        if (left) {
                            parent.left = singleChild(present);
                        } else {
                            parent.right = singleChild(present);
                        }
                    }
                } else {
                    Node predecessor = predecessor(present);
                    removeHelper(predecessor.key, present.left, present, true);
                    size += 1;
                    present.key = predecessor.key;
                    present.value = predecessor.value;
                }
                size -= 1;
                return value;
            } else if (cmp < 0) {
                return removeHelper(key, present.left, present, true);
            } else {
                return removeHelper(key, present.right, present, false);
            }
        }
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        return removeHelper(key, root, null, true);
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            return remove(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
