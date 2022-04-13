/** A simple implementation of deque via Array. */
public class ArrayDeque<T> implements Deque<T> {

    private T[] array;
    private int size;
    private int frontPointer;

    /** Creates an empty array deque. */
    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        frontPointer = 0; //invariant: vary between 0 and array.length - 1
    }

    /** Move forward the frontPointer. */
    private void moveForward() {
        frontPointer = (frontPointer + 1) % array.length;
    }

    /** Move backward the frontPointer. */
    private void moveBackward() {
        frontPointer = (frontPointer - 1 + array.length) % array.length;
    }

    /** Reset the frontPointer. */
    private void reset() {
        frontPointer = 0;
    }

    /** Revise index. */
    private int revise(int index) {
        return (frontPointer + index) % array.length;
    }

    /** Resize the Array to the given capacity. */
    private void resize(int capacity) {
        /* iterative implementation without using the function System.arraycopy()
        T[] tmp = (T[]) new Object[capacity];
        for (int i = 0; i < size; i += 1) {
            tmp[i] = array[revise(i)];
        }
        array = tmp;
        frontPointer = 0;
         */
        T[] tmp = (T[]) new Object[capacity];
        if (frontPointer + size <= array.length) {
            System.arraycopy(array, frontPointer, tmp, 0, size);
        } else {
            System.arraycopy(array, frontPointer,
                    tmp, 0, array.length - frontPointer);
            System.arraycopy(array, 0,
                    tmp, array.length - frontPointer, size - (array.length - frontPointer));
        }
        array = tmp;
        reset();
    }

    /** Adds an item to the front of the deque. */
    @Override
    public void addFirst(T item) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        moveBackward();
        array[revise(0)] = item;
        size += 1;
    }

    /** Adds an item to the back of the deque. */
    @Override
    public void addLast(T item) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        array[revise(size)] = item;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line. */
    @Override
    public void printDeque() {
        if (!isEmpty()) {
            for (int i = 0; i < size; i += 1) {
                System.out.print(array[revise(i)] + " ");
            }
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            T item = array[revise(0)];
            array[revise(0)] = null;
            size -= 1;
            moveForward();
            if (array.length >= 16 && size < 0.25 * array.length) {
                resize(array.length / 2);
            }
            return item;
        }
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null. */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            T item = array[revise(size - 1)];
            array[revise(size - 1)] = null;
            size -= 1;
            if (array.length >= 16 && size < 0.25 * array.length) {
                resize(array.length / 2);
            }
            return item;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null. */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            return array[revise(index)];
        }
    }
}
