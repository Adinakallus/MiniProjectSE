package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;


import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
/**
 * Plane in geometry
 *
 *@author Adina Kallus and Hadassa Israel
 */
public class Plane extends Geometry {

    final Point3D _q0;
    final Vector _normal;

    /**
     * Constructor of Plane from point and normal
     * @param p0 point on the plane
     * @param normal the normal vector to the plane
     */
    public Plane(Point3D p0, Vector normal) {
        _q0 = p0;
        _normal = normal.normalized();
    }

    /**
     * Constructor of Plane from 3 points on its surface
     * the points are ordered from right to left
     * forming an arc in right direction
     * @param p1 point on the plane
     * @param p2 point on the plane
     * @param p3 point on the plane
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;


        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);
        try {
            // perpendicular vector to both vectors which becomes the normal vector to the plane
            Vector N = U.crossProduct(V);
            N.normalize();
            _normal = N;
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("cant create plane if 3 of the points are on the same line");
        }


    }

    /**
     * getter for normal to plane
     * @param point should be null for flat geometries
     * @return normal to plane
     */
    @Override
    public Vector getNormal(Point3D point) {

        return _normal;
    }



    /**
     * finds all intersections between received ray within maxDistance from light
     * @param ray that intersects the point in geometry
     * @param maxDistance the maximum distance
     * @return list of all geoPoints that intersected with ray
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();

        Vector n = _normal;

        // the head of the ray is
        if(_q0.equals(P0)){
            return  null;
        }

        Vector P0_Q0 = _q0.subtract(P0);

        //numerator
        double nP0Q0  = alignZero(n.dotProduct(P0_Q0));

        //
        if (isZero(nP0Q0 )){
            return null;
        }

        //denominator
        double nv = alignZero(n.dotProduct(v));

        // ray is lying in the plane axis
        if(isZero(nv)){
            return null;
        }

        double  t = alignZero(nP0Q0  / nv);

        if (t <=0||alignZero(t-maxDistance)>0){
            return  null;
        }

        Point3D point = ray.getPoint(t);

        return List.of(new GeoPoint(this, point));
    }



    /**
     * override toString
     * @return string with details of the plane
     */
    @Override
    public String toString() {
        return "Plane{" +
                "_q0=" + _q0 +
                ", _normal=" + _normal +
                '}';
    }
}