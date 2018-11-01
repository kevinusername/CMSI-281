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

    /**
     * @return a *new* LinkedForneymonegerie object consisting of all Forneymon (NOTE: not ForneymonTypes) from y1 that
     * * do NOT appear in y2
     */
    public static LinkedForneymonegerie diffMon(LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        LinkedForneymonegerie diffBoi = y1.clone();

        ForneymonType y1Type = y1.head;

        while (true) {
            ForneymonType y2Type = y2.findType(y1Type.type);

            if (y2Type != null) { // Case: type is in y2 (else, no action required)
                // If there are equal or more of this type in y2, remove the type from diffBoi
                if (y2Type.count >= y1Type.count) {
                    diffBoi.releaseType(y2Type.type);
                } else { // Otherwise, release the amount of this type in y2 from diffBoi
                    // Saves computational strain of repeatedly calling release()
                    diffBoi.findType(y1Type.type).count -= y2Type.count;
                    diffBoi.size -= y2Type.count;
                    diffBoi.modCount++;
                }
            }
            // If there are more types to check in y1, move to next type, otherwise stop checking
            if (y1Type.next != null) { y1Type = y1Type.next; } else { break; }
        }
        return diffBoi;
    }

    /**
     * @return boolean of if two Forneymonegeries contain exact same collections
     */
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

    /**
     * Adds 1 Forneymon of type toAdd to collection
     *
     * @param toAdd type to add to collection
     * @return if type was not already in collection
     */
    public boolean collect(String toAdd) {
        ForneymonType location = findType(toAdd);

        if (location == null) { // Case: type not already in collection
            append(toAdd, 1);
            return true;
        }
        // Case: type is already in collection
        location.count++;
        size++;
        modCount++;
        return false;
    }

    /**
     * Releases 1 Forneymon of type toRemove. If there is only 1 of this type, removes the whole type
     *
     * @param toRemove type to release 1 of
     * @return if at least 1 Forneymon was released
     */
    public boolean release(String toRemove) {
        ForneymonType location = findType(toRemove);

        if (location == null) { return false; } // Case: type not in colelction

        if (location.count == 1) { // Case: last of its type
            releaseType(toRemove);
            return true;
        }
        // Case: multiple of its type
        location.count--;
        size--;
        modCount++;
        return true;
    }

    /**
     * Removes all Forneymon of a given type from the collection
     *
     * @param toNuke type to remove from collection
     */
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
        // Shorter var names for the sake of readability/sanity
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

    /**
     * @return ForneymonType with lowest count that was collected most recently
     */
    public String rarestType() {
        if (empty()) { return null; }

        ForneymonType current = head;
        ForneymonType rarest = current;

        while (true) {
            if (null == current.next) { break; }
            current = current.next;
            if (current.count <= rarest.count) { rarest = current; }
        }
        return rarest.type;
    }

    @Override
    public LinkedForneymonegerie clone() {
        LinkedForneymonegerie cloneyBoi = new LinkedForneymonegerie();

        ForneymonType ogType = head; // reference for current poisition in original collection

        for (int i = 0; i < typeSize; i++) { // Copy over all ForneymonTypes to clone's collection
            cloneyBoi.append(ogType.type, ogType.count);
            ogType = ogType.next;
        }

        // Set these values to equal the originals
        cloneyBoi.size = size;
        cloneyBoi.typeSize = typeSize;
        cloneyBoi.modCount = modCount;

        return cloneyBoi;
    }

    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------

    /**
     * Swaps all fields with another Forneymonegerie
     *
     * @param other Forneymonegerie to swap values with
     */
    public void trade(LinkedForneymonegerie other) {
        ForneymonType tempHead = head, tempTail = tail;
        int tempSize = size, tempTypeSize = typeSize, tempModCount = modCount;

        head = other.head;
        tail = other.tail;
        size = other.size;
        typeSize = other.typeSize;
        modCount = other.modCount + 1;

        other.head = tempHead;
        other.tail = tempTail;
        other.size = tempSize;
        other.typeSize = tempTypeSize;
        other.modCount = tempModCount + 1;
    }

    /**
     * @return Iterator object for this Forneymonegerie. Starts at head
     */
    public LinkedForneymonegerie.Iterator getIterator() { return new Iterator(this); }

    // Private helper methods
    // -----------------------------------------------------------

    /**
     * @param toFind name of type to check for
     * @return reference to the Formoneymon type object in this Forneymonegerie
     */
    private ForneymonType findType(String toFind) {

        ForneymonType current = head;
        if (null == current) { return null; }

        while (true) {
            if (current.type.equals(toFind)) { return current; }
            if (null == current.next) { break; }
            current = current.next;
        }

        return null; // Case: type not in collection
    }

    /**
     * Adds a ForneymonType of specified type and count to end of collection. Does not account for already existing
     * types, collect() should be used in that instance.
     * <p>
     * Primarily useful in minimizing computation neccessary in the cloning process.
     *
     * @param type  name of ForneymonType to add to end of collection
     * @param count amount of the type that will be appended
     */
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
        int typePosition; // Track what # of a type iterator is currently on

        Iterator(LinkedForneymonegerie y) {
            owner = y;
            current = y.head;
            itModCount = y.modCount;
            typePosition = 1; // started index at 1 *gasp*, this is so it matches with the actual count value of type
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

        /**
         * Replaces all Formeymon of a given type with an input type
         * <p>
         * Iterator will end pointing at ForneymonType toReplaceWith
         *
         * @param toReplaceWith type that will replace current.type
         */
        public void replaceAll(String toReplaceWith) {
            if (!isValid()) { throw new IllegalStateException("Invalid iterator"); }
            int oldModCount = modCount; // for keeping iterator in sync

            if (toReplaceWith.equals(current.type)) { return; } // Case: trying to replace current with itself

            ForneymonType existing = findType(toReplaceWith);
            if (existing == null) { // Case: toReplaceWith type is not in collection
                append(toReplaceWith, current.count);
                releaseType(current.type);
                current = tail;
            } else { // Case: toReplaceWith type is already in collection
                existing.count += current.count;
                releaseType(current.type);
                current = existing;
            }
            // Since the above actions alter mod count, restore owner's original value and increment both counts
            // this allows the iterator to stay in sync by counting this method as only 1 "mod" action for each
            modCount = oldModCount + 1;
            itModCount++;
        }

        /**
         * This is strictly for testing purposes, and is thus package private.
         * <p>
         * Useful when testing that Iterator and its owner are in sync
         * <p>
         * Could be removed if it breaks the rules
         *
         * @return current.count
         */
        int getCount() {
            return current.count;
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