package project1.roofequations.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoofModelTest {

    RoofModel roofTest = new RoofModel(10000.00, 30);

    @Test
    public void roofTest() {

        assertEquals(10000.00, roofTest.getWidth());
        assertEquals(30, roofTest.getAngle());
    }

    @Test
    public void roofHeightTest1() {
        assertEquals(null, RoofModel.roofHeight(roofTest.getWidth(), 0));
        //assertEquals(2.89,Roof.roofHeight(roofTest.getWidth(),roofTest.getAngle()));
    }

    @Test
    public void roofHeightTest2() {
        assertEquals(5000, RoofModel.roofHeight(roofTest.getWidth(), 45));
    }

    @Test
    public void roofHeightTest3() {
        assertEquals(2886.751345948129, RoofModel.roofHeight(10000.00, 30));
    }

    @Test
    public void lengthOfRafterTest() {
        assertEquals(5773.502691896258, RoofModel.lengthOfRafter(10000.00, 30));
    }

    @Test
    void lowerPartOfRafter() {
        assertEquals(3464.1016151377544, RoofModel.
                lowerPartOfRafter(0.6, RoofModel.
                        lengthOfRafter(10000.00, 30)));
    }

    @Test
    void upperPartOfRafter() {
        assertEquals(2310, RoofModel.upperPartOfRafter(5770.0, 3460.0));
    }
}
