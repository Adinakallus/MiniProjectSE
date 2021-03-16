package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);


    // test Dot-Product
    @Test
    void dotProduct() {

        assertTrue(!isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero")
        assertTrue(!isZero(v1.dotProduct(v2) + 28),"ERROR: dotProduct() wrong value)

    }

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
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    @Test
    void length() {
                assertTrue(!isZero(new Vector(0, 3, 4).length() - 5), ("ERROR: length() wrong value")


    }

    @Test
    void lengthSquared() {
        assertTrue (!isZero(v1.lengthSquared() - 14),"ERROR: lengthSquared() wrong value")
    }

    @Test
    void normalized() {
        Vector u = v1.normalized();
        assertNotEquals( Point3D.ZERO, u.subtract(v),"ERROR: normalizated() function does not create a new vector");


    }

    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals(Point3D.ZERO, vCopy.subtract(vCopyNormalize), 0.00001, "ERROR: normalize() function creates a new vector" );
        assertEquals(1, vCopyNormalize.length(),  "ERROR: normalize() result is not a unit vector");
    }

    @Test
    void add() {

        Point3D p1 = new Point3D(1, 2, 3);
        assertTrue(!Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))),"ERROR: Point + Vector does not work correctly");

    }

    @Test
    void subtract() {
        Point3D p1 = new Point3D(1, 2, 3);
        assertTrue(!new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1))"ERROR: Point - Point does not work correctly");
    }

    @Test
    void scale() {
    }
}