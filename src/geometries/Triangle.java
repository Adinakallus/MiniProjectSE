package geometries;

import primitives.Point3D;

/**
 * Triangle inherits from polygon
 *
 *@author Adina Kallus and Hadassa Israel
 */
public class Triangle extends Polygon{

    /**
     * Constructor for Triangle class
     *
     * @param p1 point of triangle
     * @param p2 point of triangle
     * @param p3 point of triangle
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {

        super(p1, p2, p3);
    }
}
