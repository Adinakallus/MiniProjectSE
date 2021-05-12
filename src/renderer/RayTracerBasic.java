package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
        * Ray for RayTracing
        *
        * @author Adina Kallus and Hadassa Israel
        */
public class RayTracerBasic extends RayTracerBase  {
    /**
     * @param points list of 3d points
     * @return the closest point to _p0
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * @param points list of 3d points
     * @return the closest point to _p0
     */
    @Override
    public Color traceRay(Ray ray) {
        return null;
    }
}
