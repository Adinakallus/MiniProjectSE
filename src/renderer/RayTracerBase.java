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
     * @param scene scene
     * @return the closest point to _p0
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * @param points list of 3d points
     * @return the closest point to _p0
     */
    abstract public Color traceRay(Ray ray);

}
