package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Cylinder inherits from Tube
 *
 *@author Adina Kallus and Hadassa Israel
 */

public class Cylinder extends Tube {   // implements Geometry
    final double _height;

    /**
     * Constructor for Cylinder class
     *
     * @param axisRay ray of the Cylinder
     * @param radius of the Cylinder
     * @param height of the Cylinder
     */
    public Cylinder(Ray axisRay, double radius,double height) {
        super(radius,axisRay);
        _height = height;
    }

    /**
     * getter for normal
     * @param point3D point on cylinder which we want the normal to
     * @return
     */
    @Override
    public Vector getNormal(Point3D point3D){
        return null;
    }

    /**
     * override findIntersections
     * @param ray
     * @return list of all intersection points with Cylinder
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}