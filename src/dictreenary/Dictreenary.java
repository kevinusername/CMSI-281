package dictreenary;

import java.util.ArrayList;

public class Dictreenary implements DictreenaryInterface {

    // Fields
    // -----------------------------------------------------------
    TTNode root;


    // Constructor
    // -----------------------------------------------------------
    Dictreenary() {}

    // Methods
    // -----------------------------------------------------------

    public boolean isEmpty() {
        return root == null;
    }

    public void addWord(String toAdd) {
        toAdd = normalizeWord(toAdd);
        if (root == null) {
            root = addNew(toAdd);
        } else { appendWord(toAdd, root); }
    }

    public boolean hasWord(String query) {
        if (isEmpty()) {return false;}

        char[] normalized_chars = normalizeWord(query).toCharArray();
        TTNode current = root;

        for (int i = 0; i < normalized_chars.length; ) {
            if (current == null) { return false; }

            int diff = compareChars(normalized_chars[i], current.letter);

            if (diff == 0) {
                i++;
                if (i == normalized_chars.length) { break; }
                current = current.mid;
            } else if (diff > 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return current.wordEnd;
    }

    public String spellCheck(String query) {
        if (hasWord(query)) { return query; }

        char[] normalized_chars = normalizeWord(query).toCharArray();

        for (int i = 0; i < normalized_chars.length - 1; i++) {
            String correction = swapAdjacent(normalized_chars, i);
            if (hasWord(correction)) { return correction; }
        }
        return null;
    }

    public ArrayList<String> getSortedWords() {
        ArrayList<String> sortedList = new ArrayList<>();
        inOrderCollector(sortedList, root, "");
        return sortedList;
    }

    // Helper Methods
    // -----------------------------------------------------------

    private String normalizeWord(String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) { throw new IllegalArgumentException(); }
        return s.trim().toLowerCase();
    }

    /*
     * Returns:
     *   int less than 0 if c1 is alphabetically less than c2
     *   0 if c1 is equal to c2
     *   int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars(char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }

    // [!] Add your own helper methods here!
    private boolean appendWord(String toAdd, TTNode current) {
        if (current == null) { return false; }
        int diff = compareChars(toAdd.charAt(0), current.letter);
        if (diff == 0) {
            if (!appendWord(toAdd.substring(1), current.mid)) {
                current.mid = addNew(toAdd.substring(1));
            }
        } else if (diff > 0) {
            if (!appendWord(toAdd, current.right)) {
                current.right = addNew(toAdd);
            }
        } else {
            if (!appendWord(toAdd, current.left)) {
                current.left = addNew(toAdd);
            }
        }
        return true;
    }

    private TTNode addNew(String toAdd) {
        if (toAdd.length() == 0) { return null; }
        TTNode current = new TTNode(toAdd.charAt(0), toAdd.length() == 1);
        current.mid = addNew(toAdd.substring(1));
        return current;
    }

    private String swapAdjacent(char[] word, int index) {
        char[] clone = word.clone();
        char temp = clone[index];
        clone[index] = clone[index + 1];
        clone[index + 1] = temp;
        return String.valueOf(clone);
    }

    private void inOrderCollector(ArrayList<String> sortedList, TTNode current, String workingWord) {
        if (current == null) { return; }
        inOrderCollector(sortedList, current.left, workingWord);
        if (current.wordEnd) { sortedList.add(workingWord + current.letter); }
        inOrderCollector(sortedList, current.mid, workingWord + current.letter);
        inOrderCollector(sortedList, current.right, workingWord);
    }

    // TTNode Internal Storage
    // -----------------------------------------------------------

    /*
     * Internal storage of Dictreenary words
     * as represented using a Ternary Tree with TTNodes
     */
    private class TTNode {

        boolean wordEnd;
        char letter;
        TTNode left, mid, right;

        TTNode(char c, boolean w) {
            letter = c;
            wordEnd = w;
        }
    }
}
