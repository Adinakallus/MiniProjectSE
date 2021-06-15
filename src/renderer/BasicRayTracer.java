package renderer;


import elements.LightSource;
import elements.Material;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

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
    private static final double INITIAL_K=1.0;
    private static final int MAX_CALC_COLOR_LEVEL=10;//base condition for calcGlobalEffect recursive method
    private static final double MIN_CALC_COLOR_K=0.001;//base condition for calcGlobalEffect recursive method

    /**
     * constructor for BasicRayTracer
     * @param scene the current scene
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
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    /**
     * uses phongs reflectance model to calculate the color at geoPoint
     * @param geoPoint  point on the object
     * @param ray  ray that is intersecting the point
     * @param level the level of the recursion
     * @param k מקדם הנחתה
     * @return the color of the point including all the global effects
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level , double k) {
        Color color = geoPoint.geometry.getEmission();
        color =color.add(calcLocalEffects(geoPoint, ray,k));
        if (level == 1) {
            return color;
        }
        return color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k));

    }

    /**
     * adds Reflection and Refraction parts of phong reflectance model
     * @param gp point on the object
     * @param v vector- direction of ray that intersected the surface
     * @param level the level of the recursion
     * @param k מקדם הנחתה
     * @return the color of the point including Reflection and Refraction
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kr;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kr, kkr);
        double kkt = k * material.kt;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kt, kkt));
        return color;
    }

    /**
     * calculates reflection or refraction part of the phong reflectance model
     * @param ray the new ray
     * @param level the level of the recursion
     * @param kx מקדם הנחתה original
     * @param kkx מקדם הנחתה multiplied
     * @return the color of the reflection or the refraction
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? _scene.background : calcColor(gp, ray, level-1, kkx).scale(kx));
    }

    /**
     * calculates refracted ray
     * @param point point 3D
     * @param v direction of ray that intersected the surface
     * @param n the normal vector to point
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v,n);
    }

    /**
     * calculates reflected ray
     * @param point point 3D
     * @param v direction of ray that intersected the surface
     * @param n the normal vector to point
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        //𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏 )∙ 𝒏
        Vector r=v.subtract(n.scale(v.dotProduct(n)*2));
        return new Ray(point, r,n);
    }


    /**
     * calculates intersections and finds the closest one to the rays starting point
     * @param ray the ray to find intersections
     * @return  GeoPoint with the closest intersection to rays starting point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint>pointsList=_scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(pointsList);

    }

    /**
     * get a GeoPoint on a certain object and a ray and find the color in this point by considering all the effects (lights) in the scene
     * @param intersection  intersection point with an object
     * @param ray  ray sent to the object
     * @return the color at the intersection point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        Vector v = ray.getDir(); //the direction of the ray
        Vector n = intersection.geometry.getNormal(intersection.point); //the normal vector of the geometry
        double nv = alignZero(n.dotProduct(v)); //the rate between the normal of the geometry and the direction of the ray
        if (nv == 0)
            return Color.BLACK; //if the ray doesn't hit the geometry, return black
        Material material = intersection.geometry.getMaterial(); //the material of the geometry
        int nShininess = material.getShininess();
        double kd = material.getKd(), ks = material.getKs();
        Color color = Color.BLACK;
        //pass the list of the lights of the scene
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point); //get the direction of the current light
            double nl = alignZero(n.dotProduct(l)); //the rate between the normal of the geometry and the direction of the light
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                double ktr = transparency(lightSource, l, n, intersection.point);// transparency coefficient
                // if transparency is significant enough, add to calculation of the color
                if (ktr * k > MIN_CALC_COLOR_K) {//
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    //calculate the color using the Phong model formula
                        color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                                calcSpecular(ks, l, n, v, nShininess, lightIntensity)); //add the color of this light to the main color
                    }
                }
            }
            return color;
        }




    /**
     * boolean method to determine if light reaches point or if an object is blocking it, creating a shade
     * @param light light source that we would like to determine if it reaches point
     * @param l vector from light source to point
     * @param n normal at the point
     * @param point point that we would like to know if the light reaches it
     * @return true- if the light reaches the object, otherwise- false
     */
    /**
    private boolean unshaded(LightSource light, Vector l, Vector n, Point3D point) {
        Vector dir=l.scale(-1);//vector from point towards light source
        Ray lightRay=new Ray(point,dir,n);
        double lightDistance = light.getDistance(point);//distance between LightSource and point
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay,lightDistance);
        if (intersections == null)
            return true;
        for (GeoPoint gp : intersections) {
            //if an opaque geometry is in between the light source and the point the light won't reach the point and will be shaded
            if (gp.geometry.getMaterial().kt == 0)
                return false;
        }
        return true;

    }
     */

    /**
     * calculates transparency coefficient in order to achieve accurate amount of transparency
     * @param light light source that we would like to determine if it reaches point
     * @param l vector from light source to point
     * @param n normal at the point
     * @param point point that we would like to know if the light reaches it
     * @return transparency coefficient
     */
    private double transparency(LightSource light, Vector l, Vector n, Point3D point) {
        Vector dir=l.scale(-1);//vector from point towards light source
        Ray lightRay=new Ray(point,dir,n);
        double lightDistance = light.getDistance(point);//distance between LightSource and point
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay,lightDistance);
        if (intersections == null)
            return 1.0;
        double ktr=1.0; //multiplication of all recursion coefficient to determine transparency of the point
        for (GeoPoint gp : intersections) {
            ktr *= gp.geometry.getMaterial().getKt();
            if (ktr < MIN_CALC_COLOR_K)
                return 0.0;
        }
        return ktr;

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
