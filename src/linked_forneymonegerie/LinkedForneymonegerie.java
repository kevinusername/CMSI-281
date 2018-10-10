package linked_forneymonegerie;

import java.util.NoSuchElementException;

public class LinkedForneymonegerie implements LinkedForneymonegerieInterface {

    // Fields
    // -----------------------------------------------------------
    private ForneymonType head;
    private int size, typeSize, modCount;


    // Constructor
    // -----------------------------------------------------------
    LinkedForneymonegerie() {
        head = null;
        size = 0;
        typeSize = 0;
        modCount = 0;
    }

    public static LinkedForneymonegerie diffMon(LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        throw new UnsupportedOperationException();
    }

    public static boolean sameCollection(LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        throw new UnsupportedOperationException();
    }

    // Methods
    // -----------------------------------------------------------
    public boolean empty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int typeSize() {
        return typeSize;
    }

    public boolean collect(String toAdd) {
        if (head == null) {
            head = new ForneymonType(toAdd, 1);
            return true;
        }
    }

    public boolean release(String toRemove) {
        throw new UnsupportedOperationException();
    }

    public void releaseType(String toNuke) {
        throw new UnsupportedOperationException();
    }

    public int countType(String toCount) {
        throw new UnsupportedOperationException();
    }

    public boolean contains(String toCheck) {
        throw new UnsupportedOperationException();
    }

    public String rarestType() {
        throw new UnsupportedOperationException();
    }

    public LinkedForneymonegerie clone() {
        throw new UnsupportedOperationException();
    }

    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------

    public void trade(LinkedForneymonegerie other) {
        throw new UnsupportedOperationException();
    }

    public LinkedForneymonegerie.Iterator getIterator() {
        throw new UnsupportedOperationException();
    }

    // Private helper methods
    // -----------------------------------------------------------

    // TODO: Your helper methods here!

    // Inner Classes
    // -----------------------------------------------------------

    public class Iterator implements LinkedForneymonegerieIteratorInterface {
        LinkedForneymonegerie owner;
        ForneymonType current;
        int itModCount;
        int typePosition;

        Iterator(LinkedForneymonegerie y) {
            if (y.head == null) {
                throw new IllegalArgumentException("Collection is empty. Nothing to iterate through!");
            }
            owner = y;
            current = y.head;
            itModCount = y.modCount;
            typePosition = 1;
        }

        public boolean hasNext() {
            if (!isValid()) { return false; }

            if (current.next != null) { return true; }
            return typePosition < current.count;
        }

        public boolean hasPrev() {
            if (!isValid()) { return false; }

            if (current.prev != null) { return true; }
            return typePosition > current.count;
        }

        public boolean isValid() {
            return itModCount == owner.modCount;
        }

        public String getType() {
            if (!isValid()) { return null; }
            return current.type;
        }

        public void next() {
            if (!isValid()) { throw new IllegalStateException("Invalid iterator!"); }

            // If this is the last of this type, move on to the next type if it exists
            if (typePosition == current.count) {
                if (!hasNext()) { throw new NoSuchElementException("There is no next element"); }
                current = current.next;
            }

            typePosition++; // Move to next Forneymon of this type
        }

        public void prev() {
            if (!isValid()) { throw new IllegalStateException("Invalid iterator!"); }

            // If this is not the first of its type, move back 1 in this type
            if (typePosition > 1) { typePosition--; }

            if (!hasPrev()) { throw new NoSuchElementException("There is no previous element"); }

            current = current.prev;
        }

        public void replaceAll(String toReplaceWith) {
            if (!isValid()) { throw new IllegalStateException("Invalid iterator!"); }

            // TODO: Implement checking if toReplaceWith type already exists once there is a contains() method

            current.type = toReplaceWith;
        }
    }

    private class ForneymonType {
        ForneymonType next, prev;
        String type;
        int count;

        ForneymonType(String t, int c) {
            type = t;
            count = c;
        }
    }
}