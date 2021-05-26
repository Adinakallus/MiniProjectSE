package primitives;

import geometries.Geometry;
import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import java.awt.*;

import static primitives.Util.isZero;

/**
 * Ray for RayTracing
 *
 * @author Adina Kallus and Hadassa Israel
 */
public class Ray {
    Point3D _p0;
    Vector _dir;


    /**
     * Constructor for Ray class
     *
     * @param p0  starting point of Ray
     * @param dir direction of Ray
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }


    /**
     * @return p0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * @return new vector with same head as dir
     */

    public Vector getDir() {
        return new Vector(_dir.getHead());
    }


    public Point3D getPoint(double delta) {
        if (isZero(delta)) {
            return _p0;
        }
        return _p0.add(_dir.scale(delta));
    }


    /**
     * @param o Object ( another Ray) to compare
     * @return true or false accordingly
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }


    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + _p0 +
                ", dir=" + _dir +
                '}';
    }

    /**
     * @param points list of 3d points
     * @return the closest point to _p0
     */
    public Point3D findClosestPoint(List<Point3D> points) {
        if(points.isEmpty())
            return null;

        double minDistance = _p0.distance(points.get(0));
        Point3D closest = points.get(0);

        for (int i = 1; i < points.size(); i++) {
            double dist = _p0.distance(points.get(i));
            if (dist < minDistance) {
                minDistance = dist;
                closest = points.get(i);
            }
        }
        return closest;

    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        if(intersections.isEmpty())
            return null;

        double minDistance = _p0.distance(intersections.get(0).point);
        GeoPoint closest = intersections.get(0);

        for (GeoPoint gp:intersections) {
            double dist = gp.point.distance(_p0);
            if (dist < minDistance) {
                minDistance = dist;
                closest = gp;
            }
        }
        return closest;

    }
}
