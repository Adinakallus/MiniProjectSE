package geometries;

import primitives.Point3D;
import primitives.Vector;
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

    public Sphere(Point3D center, double radius) {
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
     * @param point the point we want to find normal to
     * @return normal to sphere at point
     */
    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}