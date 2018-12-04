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
        longest = 0;
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
        } else if (get(s) == null) {
            insertNextFree(s, index);
        } else { return; }

        checkLongest(s);
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
     * This is apparently an effective way to get only positive values without conditionals.
     * Stack Overflow post explaining it:
     * https://stackoverflow.com/questions/4412179/best-way-to-make-javas-modulus-behave-like-it-should-with-negative-numbers/4412200#4412200
     */
    private int hash(String s) {
        char[] phraseChars = s.toCharArray();
        int hash = 1;
        for (char c : phraseChars) { hash *= (int) c; }
        return (hash % buckets.length + buckets.length) % buckets.length;
    }

    private void checkAndGrow() {
        if ((double) size / buckets.length < LOAD_MAX) { return; }

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
            if (newIndex == buckets.length) { newIndex = 0; }

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
            if (newIndex == buckets.length) { newIndex = 0; }

            // If phrase was in table, would be found before hitting empty cell
            if (buckets[newIndex] == null) { return null; }

            if (buckets[newIndex].equals(s)) { return buckets[newIndex]; }

            newIndex++;
        }
        return null;
    }

    private void checkLongest(String s) {
        int length = s.split(" ").length;
        if (length > longest) { longest = length; }
    }
}
