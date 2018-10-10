package linked_forneymonegerie;

import java.util.NoSuchElementException;

public class LinkedForneymonegerie implements LinkedForneymonegerieInterface {

    // Fields
    // -----------------------------------------------------------
    private ForneymonType head, tail;
    private int size, typeSize, modCount;


    // Constructor
    // -----------------------------------------------------------
    LinkedForneymonegerie() {
        head = null;
        tail = null;
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
        if (empty()) {
            head = new ForneymonType(toAdd, 1);
            tail = head;

            size++;
            typeSize++;
            modCount++;
            return true;
        }

        Iterator iter = findType(toAdd);
        if (iter == null) {
            tail.next = new ForneymonType(toAdd, 1);
            tail.next.prev = tail;
            tail = tail.next;

            size++;
            typeSize++;
            modCount++;
            return true;
        }

        iter.current.count++;
        size++;
        modCount++;
        return false;
    }

    public boolean release(String toRemove) {
        Iterator iter = findType(toRemove);
        if (iter == null) { return false; }

        if (iter.current.count == 1) {
            releaseType(toRemove);
            return true;
        }
        iter.current.count--;
        size--;
        modCount++;
        return true;
    }

    public void releaseType(String toNuke) {
        Iterator iter = findType(toNuke);
        if (iter == null) {
            return;
        }

        size -= iter.current.count;
        typeSize--;
        modCount++;

        // Adjust adjacent objects references, thus removing this type
        ForneymonType prev = iter.current.prev;
        ForneymonType next = iter.current.next;

        if (prev == null) {
            head = next;
            head.prev = null;
            return;
        }
        if (next == null) {
            prev.next = null;
            tail = prev;
            return;
        }

        prev.next = next;
        next.prev = prev;
    }

    public int countType(String toCount) {
        Iterator iter = findType(toCount);
        if (iter == null) {
            return 0;
        }
        return iter.current.count;
    }

    public boolean contains(String toCheck) {
        Iterator iter = findType(toCheck);
        return iter != null;
    }

    public String rarestType() {
        Iterator iter = getIterator();
        ForneymonType rarest = iter.current;
        iter.nextType();

        while (true) {
            if (iter.current.count <= rarest.count) {
                rarest = iter.current;
            }
            if (iter.current.next == null) {
                break;
            }
            iter.nextType();
        }
        return rarest.type;
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
        return new Iterator(this);
    }

    // Private helper methods
    // -----------------------------------------------------------

    private Iterator findType(String toFind) {
        Iterator iter = getIterator();

        while (true) {
            if (iter.getType().equals(toFind)) {
                return iter;
            }

            if (iter.current.next == null) {
                break;
            }

            iter.nextType();
        }

        return null;
    }

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
            itModCount++;
            owner.modCount++;
        }

        // Custom private helper methods:
        private void nextType() {
            if (!isValid()) { throw new IllegalStateException("Invalid iterator!"); }
            if (current.next != null) {
                current = current.next;
                typePosition = 1;
            }
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