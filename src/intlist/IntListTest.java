package intlist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntListTest {

    private IntList createTestList() {
        IntList testList = new IntList();
        for (int i = 0; i < 10; i++) {
            testList.append(i);
        }
        return testList;
    }


    @Test
    void prepend() {
        IntList testList = createTestList();

        testList.prepend(20);

        assertEquals(20, testList.getAt(0));
        assertEquals(9, testList.getAt(10));

        testList.prepend(-10);
        assertEquals(-10, testList.getAt(0));
        assertEquals(9, testList.getAt(11));
    }

    @Test
    void insertAt() {
        IntList testList = createTestList();

        testList.insertAt(100, 5);
        assertEquals(100, testList.getAt(5));

        testList.insertAt(999, 0);
        assertEquals(999, testList.getAt(0));

        assertThrows(IndexOutOfBoundsException.class, () -> testList.insertAt(1, 100));
        assertThrows(IndexOutOfBoundsException.class, () -> testList.insertAt(1, -3));
    }

    @Test
    void removeAll() {
        IntList binaryList = new IntList();
        for (int i = 0; i < 5; i++) {
            binaryList.append(1);
            binaryList.append(2);
        }
        binaryList.removeAll(1);

        for (int i = 0; i < 5; i++) {
            assertEquals(2, binaryList.getAt(i));
        }
    }
}