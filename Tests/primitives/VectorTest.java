package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;


/**
 * Unit tests for primitives.Vector class
 * @author Adina Kallus and Hadassa Israel
 */

public class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);


    /**
     * Test method for {@link Vector#dotProduct(Vector)}.
     */

    @Test
    void dotProduct() {

        assertTrue(!isZero(v1.dotProduct(v2)), "ERROR: dotProduct() for orthogonal vectors is not zero");
        assertTrue(!isZero(v1.dotProduct(v2) + 28),"ERROR: dotProduct() wrong value");

    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}.
     */

    @Test
    void crossProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    void length() {
                assertTrue(!isZero(new Vector(0, 3, 4).length() - 5), ("ERROR: length() wrong value"));
    }

    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    void lengthSquared() {
        assertTrue (!isZero(v1.lengthSquared() - 14),"ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#normalized()}.
     */
    @Test
    void normalized() {
        Vector u = v1.normalized();
        assertNotEquals( Point3D.ZERO, u.subtract(v1).getHead(),"ERROR: normalized() function does not create a new vector");
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals(Point3D.ZERO, vCopy.subtract(vCopyNormalize).getHead(), "ERROR: normalize() function creates a new vector" );
        assertEquals(1, vCopyNormalize.length(),  "ERROR: normalize() result is not a unit vector");

        try {
            v.normalize();
            fail("Didn't throw divide by zero exception!");
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for {@link Vector#add(Vector)}.
     */
    @Test
    void add() {

        Point3D p1 = new Point3D(1, 2, 3);
        assertTrue(!Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))),"ERROR: Point + Vector does not work correctly");

    }

    /**
     * Test method for {@link Vector#subtract(Vector)}
     */
    @Test
    void subtract() {
        Point3D p1 = new Point3D(1, 2, 3);
        assertTrue(!new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)),"ERROR: Point - Point does not work correctly");
    }

    /**
     * Test method for {@link Vector#scale(double)}
     */
    @Test
    void scale() {
        Vector v3 = new Vector(1.0, 1.0, 1.0);
        Vector v4= v3.scale(2.0);
        assertTrue(v4.equals(new Vector(2.0,2.0,2.0)), "ERROR: Scalar*Vector does not work correctly");

        try{
            Vector v5=v3.scale(0.0);
            fail("scale() doesn't throw an exception when scaling factor is 0");
        } catch (IllegalArgumentException e) {}

    }
}