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
        char[] normalized_chars = normalizeWord(toAdd).toCharArray();
        if (root == null) {
            root = appendWord(normalized_chars, 0);
            return;
        }
        TTNode current = root;

        for (int i = 0; i < normalized_chars.length; ) {

            int diff = compareChars(normalized_chars[i], current.letter);
            if (diff == 0) {
                i++;
                if (current.mid == null) {
                    current.mid = appendWord(normalizeWord(toAdd.substring(i)).toCharArray(), 0);
                    return;
                }
                current = current.mid;
            } else if (diff > 0) {
                if (current.right == null) {
                    current.right = appendWord(normalizeWord(toAdd.substring(i)).toCharArray(), 0);
                    return;
                }
                current = current.right;
            } else {
                if (current.left == null) {
                    current.left = appendWord(normalizeWord(toAdd.substring(i)).toCharArray(), 0);
                    return;
                }
                current = current.left;
            }
        }
    }

    public boolean hasWord(String query) {
        if (isEmpty()) {return false;}

        char[] normalized_chars = normalizeWord(query).toCharArray();

        TTNode current = root;

        for (int i = 0; i < normalized_chars.length; ) {

            int diff = compareChars(normalized_chars[i], current.letter);
            if (diff == 0) {
                i++;
                if (i == normalized_chars.length) { break; }
                if (current.mid == null) { return false; }
                current = current.mid;
            } else if (diff > 0) {
                if (current.right == null) { return false; }
                current = current.right;
            } else {
                if (current.left == null) { return false; }
                current = current.left;
            }
        }
        return current.wordEnd;
    }

    public String spellCheck(String query) {
        throw new UnsupportedOperationException();
    }

    public ArrayList<String> getSortedWords() {
        throw new UnsupportedOperationException();
    }

    // Helper Methods
    // -----------------------------------------------------------

    private String normalizeWord(String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
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
    private TTNode appendWord(char[] newWords, int index) {
        if (index == newWords.length) { return null; }
        TTNode NewNode = (index == newWords.length - 1) ? new TTNode(newWords[index], true) : new TTNode(newWords[index], false);
        NewNode.mid = appendWord(newWords, index + 1);
        return NewNode;
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
