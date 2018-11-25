package dictreenary;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class DictreenaryTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Used as the basic empty Dictreenary to test; 
    // the @Before method is run before every @Test
    Dictreenary dt;
    @Before
    public void init () {
        dt = new Dictreenary();
    }
    
    
    // =================================================
    // Unit Tests
    // =================================================
    
    // Initialization Tests
    // -------------------------------------------------
    @Test
    public void testDictreenary() {
        assertTrue(dt.isEmpty());
    }

    // Basic Tests
    // -------------------------------------------------
    @Test
    public void testAddWord() {
        dt.addWord("is");
        dt.addWord("it");
        dt.addWord("as");
        dt.addWord("ass");
        dt.addWord("at");
        dt.addWord("bat");
    }

    @Test
    public void testHasWord() {
        dt.addWord("is");
        dt.addWord("it");
        dt.addWord("as");
        dt.addWord("ass");
        dt.addWord("at");
        dt.addWord("bat");
        dt.addWord("AcT");
        assertTrue(dt.hasWord("is"));
        assertTrue(dt.hasWord("it"));
        assertTrue(dt.hasWord("as"));
        assertTrue(dt.hasWord("ass"));
        assertTrue(dt.hasWord("at"));
        assertTrue(dt.hasWord("act"));
        assertTrue(dt.hasWord("bat"));
        assertFalse(dt.hasWord("ii"));
        assertFalse(dt.hasWord("i"));
        assertFalse(dt.hasWord("zoo"));
    }

    @Test
    public void spellCheck() {
        dt.addWord("is");
        dt.addWord("it");
        dt.addWord("as");
        dt.addWord("at");
        dt.addWord("item");
        dt.addWord("ass");
        dt.addWord("bat");
        dt.addWord("bother");
        dt.addWord("goat");
        dt.addWord("goad");
        assertEquals("is", dt.spellCheck("is"));
        assertEquals("it", dt.spellCheck("it"));
        assertEquals("item", dt.spellCheck("itme"));
        assertEquals("as", dt.spellCheck("as"));
        assertEquals("bat", dt.spellCheck("abt"));
        assertEquals("bother", dt.spellCheck("bohter"));
        assertEquals(null, dt.spellCheck("bad"));
        assertEquals(null, dt.spellCheck("zoo"));
    }
    
    @Test
    public void getSortedWords() {
        dt.addWord("is");
        dt.addWord("it");
        dt.addWord("as");
        dt.addWord("itenerary");
        dt.addWord("ass");
        dt.addWord("at");
        dt.addWord("zoo");
        dt.addWord("bat");
        dt.addWord("bother");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "as", "ass", "at", "bat", "bother", "is", "it", "itenerary", "zoo"
        ));
        assertEquals(solution, dt.getSortedWords());
    }
    
}
