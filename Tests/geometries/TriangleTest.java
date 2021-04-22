package geometries;


import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersectionsRay() {
        Triangle tr = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));


        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside triangle

        assertEquals(List.of(new Point3D(1d / 3, 1d / 3, 1d / 3)),
                tr.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(-1, -1, -1))),
                "Ray intersects triangle  inside ");

        // TC02: Against edge
        assertEquals(List.of(new Point3D(1, 1, -1)),
                pl.findIntersections(new Ray(new Point3D(0, 0, -1), new Vector(1, 1, 0))),
                " Incorrect plane intersection");

        assertNull(tr.findIntersections(   new Ray(new Point3D(0, 0, -1), new Vector(1, 1, 0))),
                "Ray intersects triangle  against edge");

        // TC03: Against vertex

        assertEquals(List.of(new Point3D(-0.5, -0.5, 2)),
                pl.findIntersections(new Ray(new Point3D(0, 0, 2), new Vector(-1, -1, 0))),
                "Incorrect plane intersection");

        assertNull(tr.findIntersections(new Ray(new Point3D(0, 0, 2), new Vector(-1, -1, 0))),
                "Ray intersects triangle against vertex");

        // =============== Boundary Values Tests ==================
        // TC11: In vertex
        assertEquals(List.of(new Point3D(0, 1, 0)),
                pl.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))),
                "Incorrect plane intersection");

        assertNull(tr.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray intersects triangle through vertex");


        // TC12: On edge
        assertEquals(List.of(new Point3D(0.5, 0.5, 0)), pl.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(1, 1, 0))),
                "Incorrect plane intersection");

        assertNull(tr.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(1, 1, 0))),
                "Ray intersects triangle  through edge");


        // TC13: On edge continuation
        assertEquals(List.of(new Point3D(-0.5, 1.5, 0)), pl.findIntersections( new Ray(new Point3D(-2, 0, 0), new Vector(1, 1, 0))),
                "Incorrect plane intersection");

        assertNull(tr.findIntersections( new Ray(new Point3D(-2, 0, 0), new Vector(1, 1, 0))),
                "Ray intersects triangle on continuation of edge");
    }

}