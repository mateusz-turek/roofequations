package project1.roofequations.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoofModelTest {

    RoofModel roofTest = new RoofModel();

    @Test
    void roofTest() {
        roofTest.setAngle(30.0);
        roofTest.setWidth(-4.0);
        assertNull(roofTest.getWidth());
        assertEquals(30, roofTest.getAngle());

        roofTest.setWidth(10000.00);
        assertEquals(10000.00, roofTest.getWidth());
    }

    @Test
    void getRoofProportion() {
        roofTest.setProportion();
        assertEquals(0.6, roofTest.getProportion());
    }

    @Test
    void setRoofHeightOther() {
        roofTest.setRoofHeight(5000.00, 20.0);
        assertNull(roofTest.getRoofHeight());
    }

    @Test
    void setRoofHeight30degree() {
        roofTest.setAngle(30.0);
        roofTest.setWidth(10000.00);
        roofTest.setRoofHeight(roofTest.getWidth(), roofTest.getAngle());
        assertEquals(2886.751345948129, roofTest.getRoofHeight());
    }

    @Test
    void setRoofHeight45degree() {
        roofTest.setAngle(45.0);
        roofTest.setWidth(10000.00);
        roofTest.setRoofHeight(roofTest.getWidth(), roofTest.getAngle());
        assertEquals(5000, roofTest.getRoofHeight());
    }

    @Test
    void setRoofHeight60degree() {
        roofTest.setAngle(60.0);
        roofTest.setWidth(10000.00);
        roofTest.setRoofHeight(roofTest.getWidth(), roofTest.getAngle());
        assertEquals(8660.254037844386, roofTest.getRoofHeight());
    }

    @Test
    void setLengthOfRafterNull() {
        roofTest.setWidth(10000.00);
        roofTest.setAngle(20.0);
        roofTest.setLengthOfRafter(roofTest.getWidth(), roofTest.getAngle());
        assertNull(roofTest.getLengthOfRafter());
    }

    @Test
    void setLengthOfRafter30() {
        roofTest.setWidth(10000.00);
        roofTest.setAngle(30.0);
        roofTest.setLengthOfRafter(roofTest.getWidth(), roofTest.getAngle());
        assertEquals(5773.502691896258, roofTest.getLengthOfRafter());
    }

    @Test
    void setLowerPartOfRafter() {
        roofTest.setWidth(10000.00);
        roofTest.setAngle(30.0);
        roofTest.setLengthOfRafter(roofTest.getWidth(), roofTest.getAngle());
        roofTest.setProportion();
        roofTest.setLowerPartOfRafter(roofTest.getProportion(), roofTest.getLengthOfRafter());
        assertEquals(3464.1016151377544, roofTest.getLowerPartOfRafter());

    }

    @Test
    void setUpperPartOfRafter() {
        roofTest.setWidth(10000.00);
        roofTest.setAngle(30.0);
        roofTest.setLengthOfRafter(roofTest.getWidth(), roofTest.getAngle());
        roofTest.setProportion();
        roofTest.setLowerPartOfRafter(roofTest.getProportion(), roofTest.getLengthOfRafter());
        roofTest.setUpperPartOfRafter(roofTest.getLengthOfRafter(), roofTest.getLowerPartOfRafter());
        assertEquals(2309.401076758503, roofTest.getUpperPartOfRafter());
    }
}


