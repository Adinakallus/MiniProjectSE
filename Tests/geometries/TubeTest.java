package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 * @author Adina Kallus and Hadassa Israel
 */
class TubeTest {


    /**
     * Test method for {@link Tube#getNormal(Point3D)} .
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        //check if the function returns correct normal to this tube
        Tube tb=new Tube(3, new Ray(new Point3D(1,2,3), new Vector(2,3,4)));
        Vector v = tb.getNormal(new Point3D(1,1,3));
        assertEquals(new Vector(0,-1,0), v, "ERROR function does not work correctly");

    }
}