package forneymonegerie;

import java.util.Arrays;

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
     * An absolute nightmare
     *
     * @param y1
     * @param y2
     * @return a *new* Forneymonegerie object consisting of all Forneymon from y1 that do NOT appear in y2.
     */
    public static Forneymonegerie diffMon(Forneymonegerie y1, Forneymonegerie y2) {
        Forneymonegerie fusion = new Forneymonegerie();

        for (int i = 0; i < y1.typeSize; i++) {
            int y2Location = y2.typeIndex(y1.collection[i].type);

            if (y2Location == -1) { // when type in y1 not present in y2
                // Collect all of that type from y1
                fusion.collect(y1.collection[i].type);
                fusion.size += y1.collection[i].count - 1;
                fusion.collection[fusion.typeSize - 1].count += y1.collection[i].count - 1;
                continue; // Move onto next type
            }

            int difference = y1.collection[i].count - y2.collection[y2Location].count;
            if (difference > 0) {
                // Collect all the Forneymon of that type that are in y1 but NOT y2
                fusion.collect(y1.collection[i].type);
                fusion.size += difference - 1;
                fusion.collection[fusion.typeSize - 1].count += difference - 1;
            }
        }

        return fusion;
    }

    public static boolean sameCollection(Forneymonegerie y1, Forneymonegerie y2) {
        if (y1.size != y2.size || y1.typeSize != y2.typeSize) {
            return false;
        }

        for (int i = 0; i < y1.typeSize; i++) {
            if (y1.collection[i].count != y2.collection[y2.typeIndex(y1.collection[i].type)].count) {
                System.out.println("hello there");
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
        int index = typeIndex(toRemove);
        if (index != -1) {
            collection[index].count -= 1;
            size--;
            return true;
        }

        return false;
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
        ForneymonType rarest = collection[0];

        for (int i = 1; i < typeSize; i++) {
            if (collection[i].count <= rarest.count) {
                rarest = collection[i];
            }
        }
        return rarest.type;
    }

    @Override
    public Forneymonegerie clone() {
        Forneymonegerie clonegerie = new Forneymonegerie();
        clonegerie.collection = collection;
        clonegerie.size = size;
        clonegerie.typeSize = typeSize;

        return clonegerie;
    }

    public void trade(Forneymonegerie other) {
        Forneymonegerie temp = clone();
        collection = other.collection;
        size = other.size;
        typeSize = other.typeSize;

        other.collection = temp.collection;
        other.size = temp.size;
        other.typeSize = typeSize;
    }


    // Private helper methods
    // ----------------------------------------------------------

    private void shiftLeft(int index) {
        for (int i = index; i < size - 1; i++) {
            collection[i] = collection[i + 1];
        }
    }

    private int typeIndex(String toFind) {
        for (int i = 0; i < typeSize; i++) {
            if (collection[i].type.equals(toFind)) {
                return i;
            }
        }
        return -1;
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
