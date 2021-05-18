package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;


import java.util.List;

/**
        * Ray for RayTracing
        *
        * @author Adina Kallus and Hadassa Israel
        */
public class RayTracerBasic extends RayTracerBase  {
    /**
     * @param scene
     * @return scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * @param ray ray to trace
     * @return color of scene at the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        //find all intersections of ray with the geometries
        List<Point3D>intersections=_scene.geometries.findIntersections(ray);

        //if there are intersections
        if(intersections!=null){
            Point3D closestPoint=ray.findClosestPoint(intersections);
            return calcColor(closestPoint);
        }
        return _scene.background;
    }


    /**
     *get a point and return the color of this point
     * @param point the point
     * @return the color of the point
     */
    private Color calcColor(Point3D point){
        return _scene.ambientLight.getIntensity();
    }
}
