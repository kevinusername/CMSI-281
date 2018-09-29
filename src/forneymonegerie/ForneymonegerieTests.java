package forneymonegerie;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ForneymonegerieTests {

    // =================================================
    // Test Configuration
    // =================================================

    // Used as the basic empty menagerie to test; the @Before
    // method is run before every @Test
    Forneymonegerie fm;

    @Before
    public void init() {
        fm = new Forneymonegerie();
    }


    // =================================================
    // Unit Tests
    // =================================================

    @Test
    public void testSize() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
    }

    @Test
    public void testTypeSize() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(1, fm.typeSize());
        fm.collect("Burnymon");
        assertEquals(2, fm.typeSize());
    }

    @Test
    public void testCollect() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
    }

    @Test
    public void testRelease() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        assertEquals(1, fm.typeSize());
        fm.release("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
    }

    @Test
    public void testReleaseType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
        assertEquals(2, fm.typeSize());
        fm.releaseType("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
    }

    @Test
    public void testCountType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(2, fm.countType("Dampymon"));
        assertEquals(1, fm.countType("Burnymon"));
        assertEquals(0, fm.countType("forneymon"));
    }

    @Test
    public void testContains() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        assertFalse(fm.contains("forneymon"));
    }

    @Test
    public void testNth() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        assertEquals("Dampymon", fm.nth(0));
        assertEquals("Dampymon", fm.nth(1));
        assertEquals("Burnymon", fm.nth(2));
        assertEquals("Zappymon", fm.nth(3));
    }

    @Test
    public void testRarestType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
    }

    @Test
    public void testClone() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        Forneymonegerie dolly = fm.clone();
        assertEquals(dolly.countType("Dampymon"), 2);
        assertEquals(dolly.countType("Burnymon"), 1);
        dolly.collect("Zappymon");
        assertFalse(fm.contains("Zappymon"));
    }

    @Test
    public void testTrade() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Zappymon");
        fm2.collect("Leafymon");
        fm1.trade(fm2);
        assertTrue(fm1.contains("Zappymon"));
        assertTrue(fm1.contains("Leafymon"));
        assertTrue(fm2.contains("Dampymon"));
        assertTrue(fm2.contains("Burnymon"));
        assertFalse(fm1.contains("Dampymon"));
    }

    @Test
    public void testDiffMon() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Dampymon");
        fm2.collect("Zappymon");
        Forneymonegerie fm3 = Forneymonegerie.diffMon(fm1, fm2);
        assertEquals(fm3.countType("Dampymon"), 1);
        assertEquals(fm3.countType("Burnymon"), 1);
        assertFalse(fm3.contains("Zappymon"));
        fm3.collect("Leafymon");
        assertFalse(fm1.contains("Leafymon"));
        assertFalse(fm2.contains("Leafymon"));
    }

    @Test
    public void testSameForneymonegerie() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Burnymon");
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        Forneymonegerie fm3 = Forneymonegerie.diffMon(fm1, fm2);
        assertTrue(Forneymonegerie.sameCollection(fm1, fm2));
        assertTrue(Forneymonegerie.sameCollection(fm2, fm1));

        // Custom Tests
        fm2.collect("Dampymon");
        assertFalse(Forneymonegerie.sameCollection(fm2, fm1));
        fm2.release("Dampymon");
        assertTrue(Forneymonegerie.sameCollection(fm2, fm1));
        // End custom tests

        fm2.collect("Leafymon");
        assertFalse(Forneymonegerie.sameCollection(fm1, fm2));
    }

    @Test
    public void testGiantForneymonegerie() { // See if it can grow to accept any amount of Forneymon
        for (int i = 0; i < 100; i++) {
            fm.collect("" + i);
            int j = i;
            while (j > 0) {
                fm.collect("" + i);
                j--;
            }
        }
        System.out.println(fm.toString()); // See if they're all in there

        Forneymonegerie cloneyBoi = fm.clone();
        cloneyBoi.release("50");

        System.out.println(cloneyBoi.toString()); // See if they're all in there


        assertFalse(Forneymonegerie.sameCollection(fm, cloneyBoi));
        System.out.println(Forneymonegerie.diffMon(fm, cloneyBoi).toString());
        assertEquals("[ \"50\": 1 ]", Forneymonegerie.diffMon(fm, cloneyBoi).toString());
    }

    @Test
    public void testPrintEmpty() {
        assertEquals("[  ]", fm.toString());
    }

}
