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
            if (current.next != null) {
                return true;
            }
            return typePosition < current.count;
        }

        public boolean hasPrev() {
            throw new UnsupportedOperationException();
        }

        public boolean isValid() {
            return itModCount == owner.modCount;
        }

        public String getType() {
            throw new UnsupportedOperationException();
        }

        public void next() {
            throw new UnsupportedOperationException();
        }

        public void prev() {
            throw new UnsupportedOperationException();
        }

        public void replaceAll(String toReplaceWith) {
            throw new UnsupportedOperationException();
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