package primitives;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    /**
     * Test method for {@link Ray#findClosestPoint(List)}.
     */
    @Test
    void findClosestPointTest() {
        Ray ray = new Ray(new Point3D(0,0,10), new Vector(1, 10, -100));
        List<Point3D> list = new LinkedList<Point3D>();

        // ============ Equivalence Partitions Tests ==============
        // TC01: Closest point is in the middle of the list
        list.add(new Point3D(1, 1, -100));
        list.add(new Point3D(-1, 1, -99));
        list.add(new Point3D(0, 2, -10));
        list.add(new Point3D(0.5, 0, -100));
        assertEquals(list.get(2),ray.findClosestPoint(list),"incorrect point returned");

        // =============== Boundary Values Tests ==================
        // TC11: list of points is empty
        List<Point3D> emptylist = new LinkedList<Point3D>();
        assertNull(ray.findClosestPoint(emptylist)," null wasn't returned for empty list");

        // TC12: First point in list is the closest
            Point3D point=list.remove(2);
            list.add(0,point);
            assertEquals(list.get(0),ray.findClosestPoint(list),"incorrect point returned");

        // TC13: Last point in list is th closest
         point=list.remove(0);
         list.add(point);
        assertEquals(list.get(3), ray.findClosestPoint(list),"incorrect point returned");

    }
}