package sentinal;

public class PhraseHash implements PhraseHashInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------

    private final static int BUCKET_START = 1000;
    private final static double LOAD_MAX = 0.7;
    private int size, longest;
    private String[] buckets;


    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------

    PhraseHash() {
        buckets = new String[BUCKET_START];
        size = 0;
    }


    // -----------------------------------------------------------
    // Public Methods
    // -----------------------------------------------------------

    public int size() { return size; }

    public boolean isEmpty() {
        return size == 0;
    }

    public void put(String s) {
        checkAndGrow();

        int index = hash(s);

        if (buckets[index] == null) {
            buckets[index] = s;
        } else if (!buckets[index].equals(s)) {
            insertNextFree(s, index);
        }

        int length = s.split(" ").length;
        if (length > longest) { longest = length; }

        size++;
    }


    public String get(String s) {
        int index = hash(s);
        if (buckets[index] == null) {
            return null;
        } else if (buckets[index].equals(s)) {
            return buckets[index];
        } else {
            return searchNextBuckets(s, index);
        }
    }


    public int longestLength() { return longest; }


    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------

    /*
     * This is apparently an effective way to get only positive values from hashCode().
     * Stack Overflow post explaining it:
     * https://stackoverflow.com/questions/4412179/best-way-to-make-javas-modulus-behave-like-it-should-with-negative-numbers/4412200#4412200
     */
    private int hash(String s) { return (s.hashCode() % buckets.length + buckets.length) % buckets.length; }

    private void checkAndGrow() {
        if (size / buckets.length < LOAD_MAX) { return; }

        String[] oldBuckets = buckets;
        buckets = new String[buckets.length * 2];
        size = 0;

        for (String s : oldBuckets) {
            if (s != null) { put(s); }
        }
    }

    private void insertNextFree(String s, int index) {
        int newIndex = index + 1;
        while (newIndex != index) {
            if (buckets[newIndex] == null) {
                buckets[newIndex] = s;
                return;
            }
            newIndex++;
        }
    }

    private String searchNextBuckets(String s, int index) {
        int newIndex = index + 1;
        while (newIndex != index) {
            if (buckets[newIndex].equals(s)) {
                return buckets[newIndex];
            }
            newIndex++;
        }
        return null;
    }
}
