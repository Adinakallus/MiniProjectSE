package geometries;

import primitives.*;

import static primitives.Util.isZero;

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

    public Tube( double radius,Ray axisRay) {
        _axysRay = axisRay;
        _radius = radius;
    }

    /**
     * override func getNormal
     * @param point the point we want to find normal to
     * @return normal to Tube at point
     */
    @Override
    public Vector getNormal(Point3D point) {
    Point3D p0=_axysRay.getP0();
    Vector v=_axysRay.getDir();
     Vector p0_p=point.subtract(p0);

     double t=v.dotProduct(p0_p);

     if(isZero(t)){
         return p0_p;
     }
     Point3D o=p0.add(v.scale(t));
     Vector n=point.subtract(o);
     return n.normalize();
    }


}