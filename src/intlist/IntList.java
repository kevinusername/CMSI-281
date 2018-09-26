// Kevin Peters
package intlist;

public class IntList {

    private static final int START_SIZE = 8;
    // Fields
    private int[] items;
    private int size;

    // Constructor
    IntList() {
        items = new int[START_SIZE];
        size = 0;
    }

    public int getAt(int index) {
        indexValidityCheck(index);
        return items[index];
    }

    public void append(int toAdd) {
        checkAndGrow();
        items[size] = toAdd;
        size++;
    }

    /**
     * Shifts existing values 1 to the right
     * adds input int at index 0
     *
     * @param toAdd int to place at index 0
     */
    public void prepend(int toAdd) {
        checkAndGrow();
        shiftRight(0);
        items[0] = toAdd;
        size++;
    }

    /**
     * adds an input value at a given index
     * shifts existing values to right of index
     *
     * @param toAdd int to add
     * @param index index to place new value
     */
    public void insertAt(int toAdd, int index) {
        indexValidityCheck(index);
        checkAndGrow();

        shiftRight(index);
        items[index] = toAdd;
        size++;
    }

    /**
     * removes all instances of a given int
     * shifts other values so that there are no empty spaces
     * adjusts size to account for removed ints
     * items.length is NOT adjusted
     *
     * @param toRemove int value to be removed
     */
    public void removeAll(int toRemove) {
        int[] newItems = new int[items.length];
        int newSize = 0;

        /*
        Opted NOT to use removeAt to save computational power of moving all
        array values every time an instance of an int is removed
         */
        for (int i = 0, j = 0; i < size; i++) {
            if (items[i] != toRemove) {
                newItems[j] = items[i];
                j++;
                newSize++;
            }
        }

        items = newItems;
        size = newSize;
    }

    public void removeAt(int index) {
        indexValidityCheck(index);
        shiftLeft(index);
        size--;
    }

    private void indexValidityCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    /*
     * Expands the size of the list whenever it is at
     * capacity
     */
    private void checkAndGrow() {
        // Case: big enough to fit another item, so no
        // need to grow
        if (size < items.length) {
            return;
        }

        // Case: we're at capacity and need to grow
        // Step 1: create new, bigger array; we'll
        // double the size of the old one
        int[] newItems = new int[items.length * 2];

        // Step 2: copy the items from the old array
        for (int i = 0; i < items.length; i++) {
            newItems[i] = items[i];
        }

        // Step 3: update IntList reference
        items = newItems;
    }

    /*
     * Shifts all elements to the right of the given
     * index one left
     */
    private void shiftLeft(int index) {
        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }
    }

    private void shiftRight(int index) {
        for (int i = size; i > index; i--) {
            items[i] = items[i - 1];
        }
    }

}