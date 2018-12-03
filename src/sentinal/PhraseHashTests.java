package sentinal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class PhraseHashTests {

    // =================================================
    // Test Configuration
    // =================================================

    // Used as the basic empty PhraseHash to test;
    // the @Before method is run before every @Test
    PhraseHash p;
    @Before
    public void init () {
        p = new PhraseHash();
    }


    // =================================================
    // Unit Tests
    // =================================================

    @Test
    public void testPut() {
        p.put("bad");
        p.put("awful");
        p.put("terrible");
        p.put("horrific");
    }

    @Test
    public void testGet() {
        p.put("bad");
        p.put("awful");
        p.put("terrible");
        p.put("horrific");
        p.put("worst job");
        assertEquals("bad", p.get("bad"));
        assertEquals("awful", p.get("awful"));
        assertEquals("terrible", p.get("terrible"));
        assertEquals("horrific", p.get("horrific"));
        assertEquals("worst job", p.get("worst job"));
        assertEquals(null, p.get("ba"));
        assertEquals(null, p.get("awf"));
        assertEquals(null, p.get("zoo"));
        assertEquals(null, p.get("worst"));
    }

    @Test
    public void testSize() {
        p.put("bad");
        p.put("awful");
        p.put("terrible");
        p.put("horrific");
        p.put("worst job");
        assertEquals(5, p.size());
    }

    @Test
    public void testIsEmpty() {
        assertEquals(true, p.isEmpty());
        p.put("good");
        p.put("great");
        p.put("terrific");
        p.put("nice");
        p.put("awesome job");
        assertEquals(false, p.isEmpty());
    }

    @Test
    public void testLongestLength() {
        assertEquals(0, p.longestLength());
        p.put("good");
        assertEquals(1, p.longestLength());
        p.put("great job");
        assertEquals(2, p.longestLength());
        p.put("really well done");
        assertEquals(3, p.longestLength());
    }

}
