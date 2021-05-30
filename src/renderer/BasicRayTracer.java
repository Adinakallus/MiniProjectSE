package renderer;


import elements.LightSource;
import elements.Material;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import primitives.Color;
import primitives.Ray;
import primitives.Vector;

import _scene.Scene;


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
     * find intersections between ray and geometries in the scene,
     * and return the color of the geometry at the intersection point
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
     * calculate color of object at intersection point with ray
     * @param geoPoint  point on the object
     * @param ray  ray that is intersecting the point
     * @return  color of the point
     */

        private Color calcColor(GeoPoint geoPoint, Ray ray) {
            Color emissionColor = geoPoint.geometry.getEmission();
            Color basicColor = _scene.ambientLight.getIntensity().add(emissionColor);
            return basicColor.add(calcLocalEffects(geoPoint, ray));

    }


    /**
     * get a GeoPoint on a certain object and a ray and find the color in this point by considering all the effects (lights) in the scene
     * @param intersection  intersection point with an object
     * @param ray  ray sent to the object
     * @return the color at the intersection point
     */
     private Color calcLocalEffects(GeoPoint intersection, Ray ray){
        Vector v = ray.getDir(); //the direction of the ray
        Vector n = intersection.geometry.getNormal(intersection.point); //the normal vector of the geometry
        double nv = alignZero(n.dotProduct(v)); //the rate between the normal of the geometry and the direction of the ray
        if (nv == 0) return Color.BLACK; //if the ray doesn't hit the geometry, return black
        Material material = intersection.geometry.getMaterial(); //the material of the geometry
        int nShininess = material.getShininess();
        double kd = material.getKd(), ks = material.getKs();
        Color color = Color.BLACK;
        //pass the list of the lights of the scene
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point); //get the direction of the current light
            double nl = alignZero(n.dotProduct(l)); //the rate between the normal of the geometry and the direction of the light
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point); //calculate the intensity in the intersection point
                //calculate the color using the Phong model formula
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity)); //add the color of this light to the main color
            }
        }
        return color;
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
