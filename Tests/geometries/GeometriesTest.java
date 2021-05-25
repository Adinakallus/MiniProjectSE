package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 *  Unit tests for geometries.Geometries class
*
*@author Adina Kallus and Hadassa Israel
*/
class GeometriesTest {
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point3D(0, 0, 1), new Vector(1, 1, 1));
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));
        Triangle tr = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Geometries intersetables = new Geometries(plane, sphere, tr);


        // ============ Equivalence Partitions Tests ==============
       // TC01: Ray intersects some of the geometries but not all
        assertEquals(List.of(new Point3D(0.5d, -0.1d, 0.6d), new Point3D(0.17d, -0.3d, 0.47d), new Point3D(1.25d, 0.35d, 0.9d)),
        intersetables.findIntersections( new Ray(new Point3D(-1, -1, 0), new Vector(2.5, 1.5, 1))),
                "Ray intersection with some geometries but not all");


        // =============== Boundary Values Tests ==================

        // TC11: the list of the intersectables is empty
        Geometries emptyGeo = new Geometries();
        assertNull(emptyGeo.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(1,1,1))),
                "Empty list of intersectables ");

        // TC12: the ray has no intersections with the geometries
        assertNull(emptyGeo.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(3,-4,0))), "Empty list of geometries");

        // TC13: the ray has intersection with only one geometry
        assertEquals(List.of(new Point3D(3,-2,0 )),
                intersetables.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(3,-2,0))),
                "Ray intersection with only one geometry");


        // TC14: the ray has intersections with all the geometries
        assertEquals(List.of(new Point3D(0.5d, 0.5d, 0), new Point3D(0.5d, 0.5d, 0.71),
                new Point3D(0.5d, 0.5d, -0.71), new Point3D(0.5d, 0.5d, 0)),
                intersetables.findIntersections(new Ray(new Point3D(0.5d, 0.5d, -1.5), new Vector(0,0,2.5))),
                " Ray intersections with all geometries");
    }



    }