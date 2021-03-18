package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Adina Kallus and Hadassa Israel
 */
class SphereTest {


    /**
     * Test method for {@link Sphere#getNormal(Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp=new Sphere(3, new Point3D(1,2,3));
        Vector v=sp.getNormal(new Point3D(1,2,3));
        assertEquals(new Vector(0,-1,0), v, "ERROR function does not work correctly");
    }
}