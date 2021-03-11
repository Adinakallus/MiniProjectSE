package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane in geometry
 *
 *@author Adina Kallus and Hadassa Israel
 */
public class Plane implements Geometry {

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
        //  TODO check direction of vectors
//        Vector U = p1.subtract(p2);
//        Vector V = p3.subtract(p2);

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);
        Vector N = U.crossProduct(V);

        N.normalize();
          _normal = N;
    }

    /**
     *
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_q0=" + _q0 +
                ", _normal=" + _normal +
                '}';
    }
}