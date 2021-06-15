package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Hadassa Israel and Adina Kallus
 */
class Point3DTest {

    /**
     * Test method for {@link Point3D#distance(Point3D)}.
     */
    @Test
    void distanceTest() {
       Point3D p1=new Point3D(3,4,6);
        Point3D p2=new Point3D(2,2,4);
        assertEquals(3d, p1.distance(p2), "incorrect distance between two points");

    }
}