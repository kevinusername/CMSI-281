package linked_forneymonegerie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedForneymonegerieTest {

    LinkedForneymonegerie fm1;
    LinkedForneymonegerie fm2;

    @BeforeEach
    public void setUp() {
        fm1 = new LinkedForneymonegerie();
        for (int i = 0; i < 10; i++) {
            fm1.collect("BurnyBoi");
            fm1.collect("DampyBoi");
            fm1.collect("LeafyBoi");
        }
        fm2 = new LinkedForneymonegerie();
        for (int i = 0; i < 15; i++) {
            fm2.collect("BurnyBoi");
            fm2.collect("ZappyBoi");
            fm2.collect("LeafyBoi");
            fm2.collect("SpookyBoi");
        }
    }

    @Test
    void diffMon() {
        LinkedForneymonegerie fm3 = LinkedForneymonegerie.diffMon(fm2, fm1);
        assertEquals(5, fm3.countType("BurnyBoi"));
        assertEquals(5, fm3.countType("LeafyBoi"));
        assertEquals(0, fm3.countType("DampyBoi"));
        assertEquals(15, fm3.countType("SpookyBoi"));

        LinkedForneymonegerie fm4 = LinkedForneymonegerie.diffMon(fm1, fm2);
        assertEquals(0, fm4.countType("BurnyBoi"));
        assertEquals(0, fm4.countType("LeafyBoi"));
        assertEquals(10, fm4.countType("DampyBoi"));
    }

    @Test
    void sameCollection() {
        assertFalse(LinkedForneymonegerie.sameCollection(fm1, fm2));
        LinkedForneymonegerie fm3 = fm1.clone();
        assertTrue(LinkedForneymonegerie.sameCollection(fm1, fm3));

        fm3.collect("BurnyBoi");

        assertFalse(LinkedForneymonegerie.sameCollection(fm1, fm3));

        fm3.release("BurnyBoi");
        assertTrue(LinkedForneymonegerie.sameCollection(fm1, fm3));
    }


    @Test
    void collect() {
        fm1 = new LinkedForneymonegerie();
        fm1.collect("BurnyBoi");
        fm1.release("BurnyBoi");
        assertTrue(fm1.empty());

        for (int i = 0; i < 100; i++) {
            fm1.collect("BurnyBoi");
        }
        assertEquals(100, fm1.countType("BurnyBoi"));
    }

    @Test
    void release() {
        fm1.release("BurnyBoi");
        assertEquals(9, fm1.countType("BurnyBoi"));

        for (int i = 0; i < 9; i++) { fm1.release("BurnyBoi"); }

        assertFalse(fm1.contains("BurnyBoi"));
        fm1.release("BurnyBoi");
    }

    @Test
    void releaseType() {
        fm1.releaseType("DampyBoi");
        assertFalse(fm1.contains("DamyBoi"));
        fm1.releaseType("BurnyBoi");
        assertFalse(fm1.contains("BurnyBoi"));
    }


    @Test
    void rarestType() {
        assertEquals("LeafyBoi", fm1.rarestType());
        fm1.release("BurnyBoi");
        assertEquals("BurnyBoi", fm1.rarestType());
        fm1.releaseType("DampyBoi");
        fm1.collect("DampyBoi");
        assertEquals("DampyBoi", fm1.rarestType());
    }

    @Test
    void trade() {
        fm1.trade(fm2);

        assertEquals(15, fm1.countType("LeafyBoi"));
        assertEquals(15, fm1.countType("ZappyBoi"));
        assertEquals(4, fm1.typeSize());

        assertEquals(10, fm2.countType("LeafyBoi"));
        assertEquals(10, fm2.countType("BurnyBoi"));
        assertEquals(0, fm2.countType("ZappyBoi"));
        assertEquals(3, fm2.typeSize());
    }

    @Test
    void replaceAll() {
        LinkedForneymonegerie.Iterator iter = fm1.getIterator();

        // Case: replace with existing type
        iter.replaceAll("DampyBoi");
        assertEquals(20, iter.getCount());
        assertEquals(20, fm1.countType("DampyBoi"));
        assertEquals(0, fm1.countType("BurnyBoi"));
        assertEquals(2, fm1.typeSize());
        assertTrue(iter.isValid());

        // Case: replace with new type
        iter.replaceAll("NewBoi");
        assertEquals(20, iter.getCount());
        assertEquals(20, fm1.countType("NewBoi"));
        assertEquals(0, fm1.countType("DampyBoi"));
        assertEquals(2, fm1.typeSize());
        assertTrue(iter.isValid());

        // Case: replace with same type
        iter.replaceAll("NewBoi");
        assertEquals(20, iter.getCount());
        assertEquals(20, fm1.countType("NewBoi"));
        assertEquals(2, fm1.typeSize());
        assertTrue(iter.isValid());
    }
}