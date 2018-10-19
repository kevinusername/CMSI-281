package linked_forneymonegerie;

import java.util.NoSuchElementException;

@SuppressWarnings({"WeakerAccess", "SpellCheckingInspection"}) // This is just an artifact from my IDE
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

        ForneymonType y1Type = y1.head;

        while (true) {
            ForneymonType y2Location = y2.findType(y1Type.type);

            if (y2Location != null) { // If the type is in y2
                // If there are equal or more of this type in y2, remove the type from diffBoi
                if (y2Location.count >= y1Type.count) {
                    diffBoi.releaseType(y2Location.type);
                } else {
                    // Otherwise, release the amount of this type in y2 from diffBoi
                    for (int i = 0; i < y2Location.count; i++) {
                        diffBoi.release(y2Location.type);
                    }
                }
            }

            // If there are more types to check in y1, move to next type, otherwise stop checking
            if (y1Type.next != null) { y1Type = y1Type.next; } else { break; }
        }

        return diffBoi;
    }

    public static boolean sameCollection(LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        if (y1.size != y2.size) { return false; } // Quick little cheat to save computation if possible
        return (diffMon(y2, y1).empty() && diffMon(y1, y2).empty());
    }

    // Methods
    // -----------------------------------------------------------
    public boolean empty() {
        return 0 == size;
    }

    public int size() {
        return size;
    }

    public int typeSize() {
        return typeSize;
    }

    public boolean collect(String toAdd) {
        ForneymonType location = findType(toAdd);

        if (empty() || location == null) { // Case: type not already in collection
            append(toAdd, 1);
            return true;
        }

        // Case: type is already in collection
        location.count++;
        size++;
        modCount++;
        return false;
    }

    public boolean release(String toRemove) {
        ForneymonType location = findType(toRemove);

        if (location == null) { return false; }

        if (location.count == 1) { //case: last of its type
            releaseType(toRemove);
            return true;
        }
        // case: multiple of its type
        location.count--;
        size--;
        modCount++;
        return true;
    }

    public void releaseType(String toNuke) {
        ForneymonType location = findType(toNuke);
        if (location == null) { return; } // Case: toNuke is NOT in the collection

        size -= location.count;
        typeSize--;
        modCount++;

        if (empty()) { // Case: toNuke was the only type in collection
            head = null;
            tail = null;
            return;
        }
        // Shorter var names for the sake of readability
        ForneymonType prev = location.prev;
        ForneymonType next = location.next;

        if (prev == null) { // Case: toNuke is head
            head = next;
            head.prev = null;
            return;
        }
        if (next == null) { // Case: toNuke is tail
            prev.next = null;
            tail = prev;
            return;
        }
        // Case: toNuke type is in collection, but is not the head or tail
        prev.next = next;
        next.prev = prev;
    }

    public int countType(String toCount) {
        ForneymonType location = findType(toCount);
        if (null == location) { return 0; }
        return location.count;
    }

    public boolean contains(String toCheck) {
        ForneymonType location = findType(toCheck);
        return location != null;
    }

    public String rarestType() {
        if (empty()) { return null; }

        ForneymonType current = head;
        ForneymonType rarest = current;

        while (true) {
            if (current.next == null) { break; }
            current = current.next;
            if (current.count <= rarest.count) { rarest = current; }
        }
        return rarest.type;
    }

    public LinkedForneymonegerie clone() {
        LinkedForneymonegerie cloneyBoi = new LinkedForneymonegerie();
        cloneyBoi.size = size;
        cloneyBoi.typeSize = typeSize;
        cloneyBoi.modCount = modCount;
        // TODO: Find way to not do this twice?

        if (empty()) { return cloneyBoi; }

        cloneyBoi.head = new ForneymonType(head.type, head.count);
        cloneyBoi.tail = cloneyBoi.head;

        Iterator ogIter = this.getIterator();
        ogIter.nextType();

        for (int i = 1; i < typeSize; i++) {
            cloneyBoi.append(ogIter.getType(), ogIter.getCount());
            ogIter.nextType();
        }

        // Reset these values since they have been altered in the cloning process
        cloneyBoi.size = size;
        cloneyBoi.typeSize = typeSize;
        cloneyBoi.modCount = modCount;

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
        other.typeSize = temp.typeSize;
        other.modCount = temp.modCount + 1;
    }

    public LinkedForneymonegerie.Iterator getIterator() {
        return new Iterator(this);
    }

    // Private helper methods
    // -----------------------------------------------------------

    private ForneymonType findType(String toFind) {

        ForneymonType current = head;
        if (current == null) { return null; }
        while (true) {
            if (current.type.equals(toFind)) {
                return current;
            }
            if (current.next == null) { break; }
            current = current.next;
        }

        return null;
    }

    private void append(String type, int count) {

        if (empty()) {
            head = new ForneymonType(type, count);
            tail = head;
        } else {
            ForneymonType oldTail = tail;
            tail.next = new ForneymonType(type, count);
            tail = oldTail.next;
            tail.prev = oldTail;
        }

        modCount++;
        typeSize++;
        size += count;
    }

    // Inner Classes
    // -----------------------------------------------------------

    public class Iterator implements LinkedForneymonegerieIteratorInterface {
        LinkedForneymonegerie owner;
        ForneymonType current;
        int itModCount;
        int typePosition;

        Iterator(LinkedForneymonegerie y) {
//            if (y.head == null) {
//                throw new IllegalArgumentException("Collection is empty. Nothing to iterate through");
//            }
            owner = y;
            current = y.head;
            itModCount = y.modCount;
            typePosition = 1; // custom field to track what # of a type iterator is currently on
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

            ForneymonType location = findType(toReplaceWith);
            if (location == null) {
                current.type = toReplaceWith;
                itModCount++;
                owner.modCount++;
                return;
            }

            location.count += current.count;
            releaseType(getType());
            itModCount++;
            // TODO: this now points to nothing???
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