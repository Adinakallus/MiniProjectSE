package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for geometries.Triangle class
 * @author Adina Kallus and Hadassa Israel
 */
public class TriangleTest {
    /**
     * Test method for Triangle.getNormal().
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        //check if the function returns correct normal to this triangle
        Point3D p1=new Point3D(0,1,1);
        Point3D p2=new Point3D(-2,1,0);
        Point3D p3=new Point3D(5,0,2);
        Triangle tr=new Triangle(p1, p2, p3);
        assertEquals(new Vector(-1/Math.sqrt(14),-3/Math.sqrt(14),2/Math.sqrt(14)),tr.getNormal(p1), "ERROR: Triangle getNormal doesn't return correct normal");
    }

}