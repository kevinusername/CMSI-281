package forneymonegerie;

public class Forneymonegerie implements ForneymonegerieInterface {

    private static final int START_SIZE = 16;
    // Fields
    // ----------------------------------------------------------
    private ForneymonType[] collection;
    private int size;
    private int typeSize;


    // Constructor
    // ----------------------------------------------------------
    Forneymonegerie() {
        collection = new ForneymonType[START_SIZE];
        size = 0;
        typeSize = 0;
    }

    // Static methods
    // ----------------------------------------------------------

    /**
     * Returns a *new* Forneymonegerie object consisting of all Forneymon from y1 that do NOT appear in y2.
     *
     * @param y1 Forneymonegerie in which all Forneymon MUST be
     * @param y2 Forneymonegerie in which all Forneymon must NOT be
     * @return Forneymonegerie object consisting of all Forneymon from y1 that do NOT appear in y2.
     */
    public static Forneymonegerie diffMon(Forneymonegerie y1, Forneymonegerie y2) {
        Forneymonegerie fusion = y1.clone();

        for (int i = 0; i < fusion.typeSize; i++) {
            int y2Index = y2.typeIndex(fusion.collection[i].type);
            if (y2Index == -1) {
                continue;
            }
            int difference = fusion.collection[i].count - y2.collection[y2Index].count;
            if (difference <= 0) {
                fusion.releaseType(fusion.collection[i].type);
                i--;
                continue;
            }
            fusion.collection[i].count -= difference;
        }

        return fusion;
    }

    public static boolean sameCollection(Forneymonegerie y1, Forneymonegerie y2) {
        // TODO: can be done in one line...

        // Don't bother if size fields don't match
        if (y1.size != y2.size || y1.typeSize != y2.typeSize) {
            return false;
        }

        // Since size fields match, make sure all types, and their respective sizes, match
        for (int i = 0; i < y1.typeSize; i++) {
            int y2Location = y2.typeIndex(y1.collection[i].type);

            if (y2Location == -1) {  // If this type from y1 is NOT in y2, not equal
                return false;
            }

            // If the amount of each type is not the same, not equal
            if (y1.collection[i].count != y2.collection[y2Location].count) {
                return false;
            }
        }
        return true;
    }

    // Methods
    // ----------------------------------------------------------
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

        int index = typeIndex(toAdd);

        if (index != -1) { // If type is already in collection, just increment sizes
            collection[index].count++;
            size++;
            return false;
        }

        // Add new type and increment sizes
        checkAndGrow();
        collection[typeSize] = new ForneymonType(toAdd, 1);
        size++;
        typeSize++;
        return true;
    }

    public boolean release(String toRemove) {
        int index = typeIndex(toRemove);
        if (index != -1) { // If the type is in the collection

            if (collection[index].count == 1) { // If last of a type, remove the type and exit
                releaseType(toRemove);
                return true;
            }

            collection[index].count -= 1;
            size--;
            return true;
        }

        return false; // Type isn't in the collection, nothing removed
    }

    public void releaseType(String toNuke) {
        int index = typeIndex(toNuke);
        if (index != -1) {
            size -= collection[index].count;
            shiftLeft(index);
            typeSize--;
        }
    }


    public int countType(String toCount) {
        int index = typeIndex(toCount);
        if (index != -1) {
            return collection[index].count;
        }

        return 0;
    }

    public boolean contains(String toCheck) {
        return typeIndex(toCheck) != -1;
    }

    public String nth(int n) {
        for (int i = 0; n >= 0 && i < typeSize; i++) {
            if (collection[i].count > n) {
                return collection[i].type;
            }
            n -= collection[i].count;
        }
        throw new IllegalArgumentException();
    }

    public String rarestType() {
        ForneymonType rarest = collection[0]; // Default to first collected type

        for (int i = 1; i < typeSize; i++) {
            if (collection[i].count <= rarest.count) { // If a type is found to be rarer, save its type instead
                rarest = collection[i];
            }
        }
        return rarest.type;
    }

    @Override
    public Forneymonegerie clone() {
        Forneymonegerie clonegerie = new Forneymonegerie();
        clonegerie.collection = new ForneymonType[collection.length];

        for (int i = 0; i < typeSize; i++) {
            clonegerie.collection[i] = new ForneymonType(collection[i].type, collection[i].count);
        }
        clonegerie.size = size;
        clonegerie.typeSize = typeSize;

        return clonegerie;
    }

    public void trade(Forneymonegerie other) {
        Forneymonegerie temp = clone();

        // Inherit fields from other
        collection = other.collection;
        size = other.size;
        typeSize = other.typeSize;

        // Inherit fields from clone of original
        other.collection = temp.collection;
        other.size = temp.size;
        other.typeSize = typeSize;
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder("[ ");
        for (int i = 0; i < typeSize; i++) {
            returnString.append("\"" + collection[i].type + "\"" + ": " + collection[i].count);
            if (i != typeSize - 1) {
                returnString.append(", ");
            }
        }
        returnString.append(" ]");
        return returnString.toString();
    }


    // Private helper methods
    // ----------------------------------------------------------

    private void shiftLeft(int index) {
        for (int i = index; i < size - 1; i++) {
            collection[i] = collection[i + 1];
        }
    }

    /**
     * Finds index of a given type in the collection
     *
     * @param toFind type to search for index of
     * @return index in array, -1 if NOT present
     */
    private int typeIndex(String toFind) {
        for (int i = 0; i < typeSize; i++) {
            if (collection[i].type.equals(toFind)) {
                return i;
            }
        }
        return -1;
    }

    private void checkAndGrow() {
        // Case: big enough to fit another item, so no
        // need to grow
        if (typeSize < collection.length) {
            return;
        }

        // Case: we're at capacity and need to grow
        // Step 1: create new, bigger array; we'll
        ForneymonType[] newItems = new ForneymonType[collection.length + 8];

        // Step 2: copy the items from the old array
        for (int i = 0; i < collection.length; i++) {
            newItems[i] = collection[i];
        }

        // Step 3: update IntList reference
        collection = newItems;
    }

    // Private Classes
    // ----------------------------------------------------------
    private class ForneymonType {
        String type;
        int count;

        ForneymonType(String t, int c) {
            type = t;
            count = c;
        }
    }

}
