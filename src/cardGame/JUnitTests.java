package cardGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ForneymonCard and FlippingForneymonCard classes
 * Had issues testing for if InvalidArgumentAcception was thrown, so absent
 */
class JUnitTests {

    /**
     * Tests creation of ForneymonCards with and without args given
     */
    @org.junit.jupiter.api.Test
    public void testForneymonCard() {
        ForneymonCard testBoi = new ForneymonCard();
        assertEquals(testBoi.toString(), "Burnymon: MissingNu");

        ForneymonCard customBoi = new ForneymonCard("customBoi", "Dampymon");
        assertEquals(customBoi.toString(), "Dampymon: customBoi");

    }

    /**
     * Tests creation of FlippingForneymonCards as well as their methods
     */
    @org.junit.jupiter.api.Test
    public void testFlippingForneymonCard() {
        // No args given
        FlippingForneymonCard flippy = new FlippingForneymonCard();
        assertEquals(flippy.faceDown, true);

        // Args given
        FlippingForneymonCard customFlippy = new FlippingForneymonCard("FlippyBoi", "Leafymon", "Up");
        FlippingForneymonCard clonedFlippy = new FlippingForneymonCard("FlippyBoi", "Leafymon", "Up");
        FlippingForneymonCard badClone = new FlippingForneymonCard("BoiFlippy", "Leafymon", "Up");

        assertEquals(customFlippy.faceDown, false);

        // 3 Tests of if match method works
        assertEquals(flippy.match(customFlippy), 2);
        assertEquals(clonedFlippy.match(customFlippy), 1);
        assertEquals(badClone.match(clonedFlippy), 0);

        // Tests of toString() when face-down and face-up + flip() method
        assertEquals(flippy.toString(), "?: ?");
        flippy.flip();
        assertEquals(flippy.toString(), "Burnymon: MissingNu");
        assertEquals(customFlippy.toString(), "Leafymon: FlippyBoi");
    }


}