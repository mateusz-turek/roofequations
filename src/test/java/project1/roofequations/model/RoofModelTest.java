package project1.roofequations.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoofModelTest {

    RoofModel roofTest = new RoofModel();

    @Test
    public void roofTest() {
        roofTest.setAngle(30);
        roofTest.setWidth(-4.0);
        assertNull(roofTest.getWidth());
        assertEquals(30, roofTest.getAngle());

        roofTest.setWidth(10000.00);
        assertEquals(10000.00,roofTest.getWidth());
    }
    @Test
    public void getRoofProportion(){
        roofTest.setProportion();
        assertEquals(0.6,roofTest.getProportion());
    }
    @Test
    void setRoofHeightOther() {
        roofTest.setRoofHeight(5000.00,20);
        assertNull(roofTest.getRoofHeight());
    }
    @Test
    void setRoofHeight30degree() {
        roofTest.setAngle(30);
        roofTest.setWidth(10000.00);
        roofTest.setRoofHeight(roofTest.getWidth(),roofTest.getAngle());
        assertEquals(2886.751345948129,roofTest.getRoofHeight());
    }
    @Test
    void setRoofHeight45degree() {
        roofTest.setAngle(45);
        roofTest.setWidth(10000.00);
        roofTest.setRoofHeight(roofTest.getWidth(),roofTest.getAngle());
        assertEquals(5000,roofTest.getRoofHeight());
    }
    @Test
    void setRoofHeight60degree() {
        roofTest.setAngle(60);
        roofTest.setWidth(10000.00);
        roofTest.setRoofHeight(roofTest.getWidth(),roofTest.getAngle());
        assertEquals(8660.254037844386,roofTest.getRoofHeight());
    }

    @Test
    void setLengthOfRafterNull() {
        roofTest.setWidth(10000.00);
        roofTest.setAngle(20);
        roofTest.setLengthOfRafter(roofTest.getWidth(),roofTest.getAngle());
        assertNull(roofTest.getLengthOfRafter());
    }

    @Test
    void setLengthOfRafter30() {
        roofTest.setWidth(10000.00);
        roofTest.setAngle(30);
        roofTest.setLengthOfRafter(roofTest.getWidth(),roofTest.getAngle());
       assertEquals(5773.502691896258,roofTest.getLengthOfRafter());
    }

    @Test
    void setLowerPartOfRafter() {
        roofTest.setWidth(10000.00);
        roofTest.setAngle(30);
        roofTest.setLengthOfRafter(roofTest.getWidth(),roofTest.getAngle());
        roofTest.setProportion();
        roofTest.setLowerPartOfRafter(roofTest.getProportion(),roofTest.getLengthOfRafter());
        assertEquals(3464.1016151377544,roofTest.getLowerPartOfRafter());

    }

    @Test
    void setUpperPartOfRafter() {
        roofTest.setWidth(10000.00);
        roofTest.setAngle(30);
        roofTest.setLengthOfRafter(roofTest.getWidth(),roofTest.getAngle());
        roofTest.setProportion();
        roofTest.setLowerPartOfRafter(roofTest.getProportion(),roofTest.getLengthOfRafter());
        roofTest.setUpperPartOfRafter(roofTest.getLengthOfRafter(),roofTest.getLowerPartOfRafter());
        assertEquals(2309.401076758503,roofTest.getUpperPartOfRafter());
    }
}

   // @Test
   // public void roofHeightTest1() {
    //    assertEquals(null, RoofModel.roofHeight(roofTest.getWidth(), 0));
        //assertEquals(2.89,Roof.roofHeight(roofTest.getWidth(),roofTest.getAngle()));
   // }

    //@Test
    //public void roofHeightTest2() {
     //   assertEquals(5000, RoofModel.roofHeight(roofTest.getWidth(), 45));
    //}

    //@Test
    //public void roofHeightTest3() {
      //  assertEquals(2886.751345948129, RoofModel.roofHeight(10000.00, 30));
    //}

  //  @Test
   // public void lengthOfRafterTest() {
   //     assertEquals(5773.502691896258, RoofModel.lengthOfRafter(10000.00, 30));
   // }

    //@Test
    //void lowerPartOfRafter() {
     //   assertEquals(3464.1016151377544, RoofModel.
      //          lowerPartOfRafter(0.6, RoofModel.
      //                  lengthOfRafter(10000.00, 30)));
    //}

    //@Test
    //void upperPartOfRafter() {
     //   assertEquals(2310, RoofModel.upperPartOfRafter(5770.0, 3460.0));


