package geometries;

import primitives.Ray;

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
        super(axisRay,radius);
        _height = height;
    }
}