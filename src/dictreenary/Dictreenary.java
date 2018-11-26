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
        char[] normalized_chars = normalizeWord(query).toCharArray();
        TTNode current = root;

        for (int i = 0; i < normalized_chars.length; ) {
            if (current == null) { return false; }

            int diff = compareChars(normalized_chars[i], current.letter);

            // Move toward node where letter is expected be; increment index if match
            if (diff == 0) {
                i++;
                if (i == normalized_chars.length) { break; } // Matching word found
                current = current.mid;
            } else if (diff > 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return current.wordEnd; // Return if found word is recognized as a word by dictionary
    }

    public String spellCheck(String query) {
        if (hasWord(query)) { return query; }

        char[] normalized_chars = normalizeWord(query).toCharArray();

        // Go through each possible swap of adjacent chars and check if valid word can be found
        for (int i = 0; i < normalized_chars.length - 1; i++) {
            String correction = swapAdjacent(normalized_chars, i);
            if (hasWord(correction)) { return correction; }
        }
        return null;
    }

    /**
     * Uses a private, recursive in-order-traversal to find all words
     *
     * @return ArrayList of words in alphabetical order (according to spec)
     */
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

    /**
     * Recursively look through the Ternary Tree to find where a word should be placed. If a longer word containing the
     * new word is already known, mark the shorter word's last letter as a wordEnd.
     *
     * @param toAdd   Word to add to tree
     * @param current currently examined node
     * @return false if null node found where word can be placed, else true
     */
    private boolean appendWord(String toAdd, TTNode current) {
        if (current == null) { return false; } // Signal to place a new word in empty node
        if (toAdd.length() == 0) { return true; } // Case: nothing left to add. Avoids calling addNew()

        int diff = compareChars(toAdd.charAt(0), current.letter);

        // Navigate letters according to relative order in dictionary
        if (diff == 0) {
            if (!appendWord(toAdd.substring(1), current.mid)) {
                current.mid = addNew(toAdd.substring(1));
            } else if (toAdd.length() == 1) { // Case: a longer word containing this word is already known
                current.wordEnd = true;
            }
        } else if (diff > 0) {
            if (!appendWord(toAdd, current.right)) { current.right = addNew(toAdd); }
        } else {
            if (!appendWord(toAdd, current.left)) { current.left = addNew(toAdd); }
        }

        return true;
    }

    /**
     * Called once a null node is found where a word should be placed. Creates a chain of mid children from the
     * proceeding letters
     *
     * @param toAdd word, or substring of a word, to add to a null node on the tree
     * @return A node with word's letters as a chain of mid children.
     */
    private TTNode addNew(String toAdd) {
        if (toAdd.length() == 0) { return null; }
        TTNode current = new TTNode(toAdd.charAt(0), toAdd.length() == 1);
        current.mid = addNew(toAdd.substring(1));
        return current;
    }

    /**
     * @param word  char[] of a word's letters
     * @param index index of char to swap with its right adjacent letter
     * @return the word with letters at i and i+1 swapped
     */
    private String swapAdjacent(char[] word, int index) {
        char[] clone = word.clone();
        char temp = clone[index];
        clone[index] = clone[index + 1];
        clone[index + 1] = temp;
        return String.valueOf(clone);
    }

    /**
     * Use and in-order-traversal to append found words to ArrayList in order
     *
     * @param sortedList  originally empty list to append found words to
     * @param current     the node that is being examined
     * @param workingWord String of previous nodes' letters visited along this path
     */
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
