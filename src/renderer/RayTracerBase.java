package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Ray for RayTracing
 *
 * @author Adina Kallus and Hadassa Israel
 */
abstract public class RayTracerBase {
    protected Scene _scene;

    /**
     * constructor for scene
     * @param scene scene
     * @return the closest point to _p0
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * find intersections between ray and geometries in the scene,
     * and return the color of the geometry at the intersection point
     * @param ray ray to trace
     * @return color of scene at the intersection point
     */
    abstract public Color traceRay(Ray ray);

}
