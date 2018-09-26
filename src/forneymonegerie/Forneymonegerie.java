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
    public static Forneymonegerie diffMon(Forneymonegerie y1, Forneymonegerie y2) {
        throw new UnsupportedOperationException();
    }

    public static boolean sameCollection(Forneymonegerie y1, Forneymonegerie y2) {
        throw new UnsupportedOperationException();
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
        for (int i = 0; i < typeSize; i++) {
            if (collection[i].type.equals(toAdd)) {
                collection[i].count++;
                size++;
                return false;
            }
        }
        collection[typeSize] = new ForneymonType(toAdd, 1);
        size++;
        typeSize++;
        return true;
    }

    public boolean release(String toRemove) {
        for (int i = 0; i < typeSize; i++) {
            if (collection[i].type.equals(toRemove)) {
                collection[i].count -= 1;
                size--;
                return true;
            }
        }
        return false;
    }

    public void releaseType(String toNuke) {
        for (int i = 0; i < typeSize; i++) {
            if (collection[i].type.equals(toNuke)) {
                size -= collection[i].count;
                shiftLeft(i);
                typeSize--;
            }
        }
    }

    private void shiftLeft(int index) {
        for (int i = index; i < size - 1; i++) {
            collection[i] = collection[i + 1];
        }
    }

    public int countType(String toCount) {
        for (int i = 0; i < typeSize; i++) {
            if (collection[i].type.equals(toCount)) {
                return collection[i].count;
            }
        }
        return 0;
    }

    public boolean contains(String toCheck) {
        for (int i = 0; i < typeSize; i++) {
            if (collection[i].type.equals(toCheck)) {
                return true;
            }
        }
        return false;
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
        ForneymonType rarest = collection[0];

        for (int i = 1; i < typeSize; i++) {
            if (collection[i].count <= rarest.count) {
                rarest = collection[i];
            }
        }
        return rarest.type;
    }

    public Forneymonegerie clone() {
        throw new UnsupportedOperationException();
    }

    public void trade(Forneymonegerie other) {
        throw new UnsupportedOperationException();
    }


    // Private helper methods
    // ----------------------------------------------------------

    // TODO: Add yours here!

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
