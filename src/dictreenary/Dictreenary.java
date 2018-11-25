package dictreenary;

import java.util.ArrayList;

public class Dictreenary implements DictreenaryInterface {

    // Fields
    // -----------------------------------------------------------
    TTNode root;
    
    
    // Constructor
    // -----------------------------------------------------------
    Dictreenary () {}
    
    
    // Methods
    // -----------------------------------------------------------
    
    public boolean isEmpty () {
        throw new UnsupportedOperationException();
    }
    
    public void addWord (String toAdd) {
        throw new UnsupportedOperationException();
    }
    
    public boolean hasWord (String query) {
        throw new UnsupportedOperationException();
    }
    
    public String spellCheck (String query) {
        throw new UnsupportedOperationException();
    }
    
    public ArrayList<String> getSortedWords () {
        throw new UnsupportedOperationException();
    }
    
    
    // Helper Methods
    // -----------------------------------------------------------
    
    private String normalizeWord (String s) {
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
    private int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }
    
    // [!] Add your own helper methods here!
    
    
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
        
        TTNode (char c, boolean w) {
            letter  = c;
            wordEnd = w;
        }
        
    }
    
}
