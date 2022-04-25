/** A simple implementation of Deque. */
public class LinkedListDeque<T> {

    /* The basic element to construct a LinkedListDeque. */
    private static class TNode<T> {
        private T item;
        private TNode<T> prev;
        private TNode<T> next;
    }

    private TNode<T> sentinel;
    private int size;

    /** Creates an empty linked list deque. */
    public LinkedListDeque() {
        sentinel = new TNode<>();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

//    /** Creates a deep copy of other. */
//    public LinkedListDeque(LinkedListDeque other) {
//        sentinel = new TNode<>();
//        sentinel.prev = sentinel;
//        sentinel.next = sentinel;
//        size = 0;
//
//        TNode<T> node = other.sentinel;
//        while (node.next != other.sentinel) {
//            node = node.next;
//            addLast(node.item);
//        }
//    }

    /** Adds an item to the front of the deque. */
    public void addFirst(T item) {
        TNode<T> node = new TNode<>();
        node.item = item;
        node.prev = sentinel;
        node.next = sentinel.next;
        sentinel.next.prev = node;
        sentinel.next = node;
        size += 1;
    }

    /** Adds an item to the back of the deque. */
    public void addLast(T item) {
        TNode<T> node = new TNode<>();
        node.item = item;
        node.prev = sentinel.prev;
        node.next = sentinel;
        sentinel.prev.next = node;
        sentinel.prev = node;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return (size == 0);
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line. */
    public void printDeque() {
        if (isEmpty()) {
            System.out.println();
        } else {
            TNode<T> p = sentinel;
            while (p.next != sentinel) {
                p = p.next;
                System.out.print(p.item + " ");
            }
            System.out.println(p.item);
        }
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null. */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            T item = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return item;
        }
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null. */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            T item = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return item;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null. */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            TNode<T> p = sentinel.next;
            int i = 0;
            while (i < index) {
                p = p.next;
                i += 1;
            }
            return p.item;
        }
    }

    /* A helper function for getRecursive. */
    private T helper(TNode<T> node, int index) {
        if (index == 0) {
            return node.item;
        } else {
            return helper(node.next, index - 1);
        }
    }

    /** Recursive version of get. */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            return helper(sentinel.next, index);
        }
    }
}
