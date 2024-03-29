package elements;

import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Hadassa Israel and Adina Kallus
 */
class CameraTest {
    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
     */
    @Test
    public void testConstructRayThroughPixel() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        camera.setDistance(10);
        camera.setViewPlaneSize(6,6);



        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
        camera.setViewPlaneSize(6, 6);
        assertEquals(new Ray(Point3D.ZERO, new Vector(-2, -2, 10)),
                camera.constructRayThroughPixel(3, 3, 0, 0),
                "Bad ray");

        // TC02: 4X4 Corner (0,0)
        camera.setViewPlaneSize(8, 8);
        assertEquals(new Ray(Point3D.ZERO, new Vector(-3, -3, 10)),
                camera.constructRayThroughPixel(4, 4, 0, 0),
                "Bad ray");

        // TC03: 4X4 Side (0,1)
        camera.setViewPlaneSize(8, 8);
        assertEquals(new Ray(Point3D.ZERO, new Vector(-1, -3, 10)),
                camera.constructRayThroughPixel(4, 4, 1, 0),
                "Bad ray");

        // TC04: 4X4 Inside (1,1)
        camera.setViewPlaneSize(8, 8);
        assertEquals(new Ray(Point3D.ZERO, new Vector(-1, -1, 10)),
                camera.constructRayThroughPixel(4, 4, 1, 1),
                "Bad ray");

        // =============== Boundary Values Tests ==================
        // TC11: 3X3 Center (1,1)
        camera.setViewPlaneSize(6, 6);
        assertEquals(new Ray(Point3D.ZERO, new Vector(0, 0, 10)),
                camera.constructRayThroughPixel(3, 3, 1, 1),
                "Bad ray");

        // TC12: 3X3 Center of Upper Side (0,1)
        camera.setViewPlaneSize(6, 6);
        assertEquals(new Ray(Point3D.ZERO, new Vector(0, -2, 10)),
                camera.constructRayThroughPixel(3, 3, 1, 0),
                "Bad ray");

        // TC13: 3X3 Center of Left Side (1,0)
        camera.setViewPlaneSize(6, 6);
        assertEquals(new Ray(Point3D.ZERO, new Vector(-2, 0, 10)),
                camera.constructRayThroughPixel(3, 3, 0, 1),
                "Bad ray");

    }


}