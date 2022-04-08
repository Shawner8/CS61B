/** A simple implementation of deque via Array. */
public class ArrayDeque<T> {

    private T[] array;
    private int size;
    private int frontPointer;

    /** Creates an empty array deque. */
    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        frontPointer = 0;
    }

    /** Resize the Array to the given capacity. */
    private void resize(int capacity) {
        T[] tmp = (T[]) new Object[capacity];
        System.arraycopy(array, frontPointer % array.length,
                tmp, 0, -frontPointer);
        System.arraycopy(array, 0,
                tmp, -frontPointer, frontPointer + array.length);
        array = tmp;
        frontPointer = 0;
    }

    /** Adds an item to the front of the deque. */
    public void addFirst(T item) {
        if (size == array.length - 1) {
            resize(array.length * 2);
        }
        frontPointer -= 1;
        array[frontPointer % array.length] = item;
        size += 1;
    }

    /** Adds an item to the back of the deque. */
    public void addLast(T item) {
        if (size == array.length - 1) {
            resize(array.length * 2);
        }
        array[(size + frontPointer) % array.length] = item;
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
        if (!isEmpty()) {
            for (int i = 0; i < size; i += 1) {
                System.out.print(array[(i + frontPointer) % array.length] + " ");
            }
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null. */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            T item = array[frontPointer % array.length];
            array[frontPointer % array.length] = null;
            size -= 1;
            frontPointer += 1;
            if (array.length >= 16 && size / array.length < 0.25) {
                resize(array.length / 2);
            }
            return item;
        }
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null. */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            T item = array[(size - 1 + frontPointer) % array.length];
            array[(size - 1 + frontPointer) % array.length] = null;
            size -= 1;
            if (array.length >= 16 && size / array.length < 0.25) {
                resize(array.length / 2);
            }
            return item;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null. */
    public T get(int index) {
        int revisedIndex = (index + frontPointer) % array.length;
        if (revisedIndex < 0 || revisedIndex >= size){
            return null;
        } else {
            return array[revisedIndex];
        }
    }
}
