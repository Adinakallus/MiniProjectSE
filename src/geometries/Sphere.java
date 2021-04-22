package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
/**
 * Sphere inherits from polygon
 *
 *@author Adina Kallus and Hadassa Israel
 */
public class Sphere implements Geometry {
    final Point3D _center;
    final double _radius;

    /**
     * Constructor for Sphere class
     *
     * @param center point of the sphere
     * @param radius of the sphere
     */

    public Sphere(double radius,Point3D center) {
        _center = center;
        _radius = radius;
    }


    /**
     * @return a center of sphere
     */
    public Point3D getCenter() {

        return _center;
    }


    /**
     * @return a radius of sphere
     */
    public double getRadius() {

        return _radius;
    }

    /**
     * override func get normal
     * @param point the point we want to find normal to
     * @return normal to sphere at point
     */
    @Override
    public Vector getNormal(Point3D point) {
      if(point.equals(_center)) {
          throw new IllegalArgumentException("Error: point equals center");
      }
      Vector vector= point.subtract(_center);
      return vector.normalize();
    }

    /**
     * override findIntersections
     *  @param ray
     * @return list of all intersection points with sphere
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();

        if (P0.equals(_center)) {
            return List.of(_center.add(v.scale(_radius)));
        }

        Vector U = _center.subtract(P0);

        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));

        // no intersections : the ray direction is above the sphere
        if (d >= _radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(_radius * _radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
//            Point3D P1 = P0.add(v.scale(t1));
//            Point3D P2 = P0.add(v.scale(t2));
            Point3D P1 =ray.getPoint(t1);
            Point3D P2 =ray.getPoint(t2);
            return List.of(P1, P2);
        }
        if (t1 > 0) {
//            Point3D P1 = P0.add(v.scale(t1));
            Point3D P1 =ray.getPoint(t1);
            return List.of(P1);
        }
        if (t2 > 0) {
//            Point3D P2 = P0.add(v.scale(t2));
            Point3D P2 =ray.getPoint(t2);
            return List.of(P2);
        }
        return null;
    }

}