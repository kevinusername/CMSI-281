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
        LinkedForneymonegerie diffBoi = y1.clone();

        Iterator y1Iter = y1.getIterator();

        while (true) {
            Iterator y2Location = y2.findType(y1Iter.getType());

            if (y2Location != null) { // If the type is in y2
                // If there are equal or more of this type in y2, remove the type from diffBoi
                if (y2Location.getCount() >= y1Iter.getCount()) {
                    diffBoi.releaseType(y2Location.getType());
                }
                // Otherwise, release the amount of this type in y2 from diffBoi
                else {
                    for (int i = 0; i < y2Location.getCount(); i++) {
                        diffBoi.release(y2Location.getType());
                    }
                }
            }

            // If there are more types to check in y1, move to next type, otherwise stop checking
            if (y1Iter.hasNextType()) { y1Iter.nextType(); } else { break; }
        }

        return diffBoi;
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

        if (empty()) {
            head = null;
            tail = null;
            return;
        }

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
        LinkedForneymonegerie cloneyBoi = new LinkedForneymonegerie();
        cloneyBoi.size = size;
        cloneyBoi.typeSize = typeSize;
        cloneyBoi.modCount = modCount;

        if (empty()) {
            return cloneyBoi;
        }

        cloneyBoi.head = new ForneymonType(head.type, head.count);
        cloneyBoi.tail = cloneyBoi.head;

        Iterator ogIter = new Iterator(this);
        ogIter.nextType();

        for (int i = 1; i < typeSize; i++) {
            cloneyBoi.append(ogIter.getType(), ogIter.getCount());
            ogIter.nextType();
        }

        return cloneyBoi;
    }

    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------

    public void trade(LinkedForneymonegerie other) {
        LinkedForneymonegerie temp = clone();

        head = other.head;
        tail = other.tail;
        size = other.size;
        typeSize = other.typeSize;
        modCount = other.modCount + 1;

        other.head = temp.head;
        other.tail = temp.tail;
        other.size = temp.size;
        other.typeSize = other.size;
        other.modCount = temp.modCount + 1;
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

    private void append(String type, int count) {
        ForneymonType oldTail = tail;
        tail.next = new ForneymonType(type, count);
        tail = oldTail.next;
        tail.prev = oldTail;
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
                throw new IllegalArgumentException("Collection is empty. Nothing to iterate through");
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
            return typePosition > 1;
        }

        public boolean isValid() {
            return itModCount == owner.modCount;
        }

        public String getType() {
            if (!isValid()) { return null; }
            return current.type;
        }

        public void next() {
            if (!isValid()) { throw new IllegalStateException("Invalid iterator"); }

            // If this is the last of this type, move on to the next type if it exists
            if (typePosition == current.count) {
                if (!hasNext()) { throw new NoSuchElementException("There is no next element"); }
                current = current.next;
                typePosition = 1;
                return;
            }

            typePosition++; // Move to next Forneymon of this type
        }

        public void prev() {
            if (!isValid()) { throw new IllegalStateException("Invalid iterator"); }

            // If this is not the first of its type, move back 1 in this type
            if (typePosition > 1) {
                typePosition--;
                return;
            }

            if (!hasPrev()) { throw new NoSuchElementException("There is no previous element"); }

            current = current.prev;
            typePosition = current.count;
        }

        public void replaceAll(String toReplaceWith) {
            if (!isValid()) { throw new IllegalStateException("Invalid iterator"); }

            // TODO: Implement checking if toReplaceWith type already exists once there is a contains() method

            current.type = toReplaceWith;
            itModCount++;
            owner.modCount++;
        }

        // Custom private helper methods:
        private void nextType() {
            if (!isValid()) { throw new IllegalStateException("Invalid iterator"); }
            if (current.next != null) {
                current = current.next;
                typePosition = 1;
            }
        }

        private int getCount() {
            return current.count;
        }

        private boolean hasNextType() {
            return (current.next != null);
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