package geometries;

import primitives.*;

/**
 * Tube implements Geometry
 *
 *@author Adina Kallus and Hadassa Israel
 */
public class Tube implements Geometry {
    final Ray _axysRay;
    final double _radius;


    /**
     * Constructor for Tube class
     *
     * @param axisRay ray of the tube
     * @param radius of the tube
     */

    public Tube(Ray axisRay, double radius) {
        _axysRay = axisRay;
        _radius = radius;
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