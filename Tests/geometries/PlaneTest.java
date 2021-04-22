package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;


/**
 * Unit tests for geometries.Plane class
 * @author Adina Kallus and Hadassa Israel
 */
class PlaneTest {
    /**
     * Test constructor for {@link Plane#Plane(Point3D, Point3D, Point3D)}.
     */
    @Test
void testConstructor(){
        // ============ Equivalence Partitions Tests ==============
        Point3D p1=new Point3D(0,1,1);
        Point3D p2=new Point3D(-2,1,0);
        Point3D p3=new Point3D(5,0,2);

        Plane pl=new Plane(p1, p2, p3);
        Vector v=pl.getNormal(p1);
        assertEquals(new Vector(-1/Math.sqrt(14),-3/Math.sqrt(14),2/Math.sqrt(14)),v, "Error: constructor does not work correctly");
        // =============== Boundary Values Tests ==================
        //test for p1=p2
        Plane pl1=new Plane(p1, p1, p3);
        //test for p1, p2, p3 on the same line
        Vector v1 =p1.subtract(p2);
        try {
            Plane pl2 = new Plane(p1, p2, v1.getHead());
            fail("cant create plane if 3 of the points are on the same line");
        }
        catch (IllegalArgumentException e){
        }

    }

    /**
     * Test method for {@link Plane#getNormal(Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p1=new Point3D(1,2,3);
        Point3D p2=new Point3D(1,0,1);
        Point3D p3=new Point3D(3,1,2);

        Plane pl=new Plane(p1, p2, p3);
        Vector normal=pl.getNormal(p1);

        //check if the function returns correct normal to this Plane
        assertEquals(new Vector(0,-4,4),pl.getNormal(p1), "ERROR: Plane getNormal doesn't return correct normal");

        //check that normal to plane is a unit vector
        assertEquals(1,normal.length(),"ERROR: normal to plane is not normalized");

        //check that normal is orthogonal to plane
        Vector v1=new Vector(p1.subtract(p2).getHead());
        Vector v2=new Vector(p2.subtract(p3).getHead());

        assertTrue(isZero(v1.dotProduct(normal)),"ERROR: normal not orthogonal to plane");
        assertTrue(isZero(v2.dotProduct(normal)),"ERROR: normal not orthogonal to plane");
    }


    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
   void testFindIntersections() {
        Plane pl = new Plane(new Point3D(0, 0, 1), new Vector(1, 1, 1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects plane
        assertEquals(List.of(new Point3D(1, 0, 0)),
                pl.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0))),
                "Incorrect plane intersection");

        // TC02: Ray doesn't intersect plane
        assertEquals(null, pl.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))),
                "findIntersections didn't return null");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to plane
        // TC11: ray not included in plane
        assertEquals(null, pl.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(0, 1, -1))),
                "findIntersections didn't return null for parallel ray");

        // TC12:ray included in plane:
        assertEquals(null, pl.findIntersections(new Ray(new Point3D(0, 0.5, .5), new Vector(0, 1, -1))),
                "findIntersections didn't return null when ray is included in plane ");


        // **** Group: Ray orthogonal to plane
        // TC13: Ray starts before plane
        assertEquals(List.of(new Point3D(1d / 3, 1d / 3, 1d / 3)),
                pl.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(-1, -1, -1))),
                "Incorrect plane intersection for orthogonal ray that starts before plane ");

        // TC14: Ray starts after plane
        assertEquals(null, pl.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(1, 1, 1))),
                "Incorrect plane intersection for orthogonal ray that starts after plane");

        // TC15: Ray starts in plane
        assertEquals(null, pl.findIntersections(new Ray(new Point3D(0, 0.5, 0.5), new Vector(1, 1, 1))),
                "Incorrect plane intersection for orthogonal ray that starts in plane");


        //// **** Group: Spacial cases
        // TC16: non orthogonal or parallel ray that starts from plane
        assertEquals(null, pl.findIntersections(new Ray(new Point3D(0, 0.5, 0.5), new Vector(1, 1, 0))),
                "Incorrect plane intersection for ray that starts from plane");

        //TC17: ray starts from plane's Q point
        assertEquals(null, pl.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 1, 0))),
                "Incorrect plane intersection when P0=Q");
    }

}