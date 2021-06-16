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
   private static final double DELTA = 0.1;
   private final Point3D _p0;
   private final Vector _dir;


    /**
     * Constructor for Ray class
     * @param p0  starting point of Ray
     * @param dir direction of Ray
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }

    /**
     * Constructor for ray that moves it's starting point
     * @param point starting point of Ray
     * @param dir direction of Ray
     * @param n the normal line to which we want to move the head of the ray
     *
     */
    public Ray(Point3D point, Vector dir, Vector n) {
        Vector delta = n.scale(n.dotProduct(dir) > 0 ? DELTA : - DELTA);
        Point3D p=point.add(delta);
        _p0=p;
        _dir=dir;
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

    /**
     *
     * @param delta
     * @return
     */
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
     * finds the point in the list that is closest to _p0
     * @param points list of 3d points
     * @return the closest point to _p0
     */
    public Point3D findClosestPoint(List<Point3D> points) {
        if(points.isEmpty())
            return null;
        // the minimum distance
        double minDistance = _p0.distance(points.get(0));
        Point3D closest = points.get(0);

        // go over the list to check the closest point
        for (int i = 1; i < points.size(); i++) {
            double dist = _p0.distance(points.get(i));
            if (dist < minDistance) {
                minDistance = dist;
                closest = points.get(i);
            }
        }
        return closest;

    }

    /**
     * finds GeoPoint with closest intersection to rays starting point
     * @param intersections list of intersections with the ray and the geometries
     * @return closets intersection point to _p0
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        if(intersections!=null) {
            double minDistance = _p0.distance(intersections.get(0).point);
            GeoPoint closest = intersections.get(0);

            //looks for closest intersection
            for (GeoPoint gp : intersections) {
                double dist = gp.point.distance(_p0);
                if (dist < minDistance) {
                    minDistance = dist;
                    closest = gp;
                }
            }
            return closest;
        }
        return null;
    }
}
