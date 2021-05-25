package renderer;

import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;


import java.util.List;

/**
        * Ray for RayTracing
        *
        * @author Adina Kallus and Hadassa Israel
        */
public class BasicRayTracer extends RayTracerBase {
    /**
     * @param scene
     * @return scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * @param ray ray to trace
     * @return color of scene at the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        //find all intersections of ray with the geometries
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);

        //if there are intersections
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint, ray);
        }
        return _scene.background;
    }



    /**
     * get a point on an object and return the color of this point
     * @param geoPoint the point on the object
     * @param ray the ray intersecting with the point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Color emissionColor = geoPoint.geometry.getEmission();
        Color basicColor = _scene.ambientLight.getIntensity().add(emissionColor);
        Vector
        return basicColor.add(calcLocalEffects(geoPoint, ray));
    }


    /**
     * Calculates the specular part of the Phong Reflective model
     * @return the color created in this part
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity){
        Vector r=l.subtract(n.scale(l.dotProduct(n)*2));
        return lightIntensity.scale(ks*Math.pow(Math.max(0,v.scale(-1).dotProduct(r)),nShininess));
    }
    /**
     * Calculates the diffuse part of the Phong Reflective model
     *
     * @return the diffuse
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }
}
