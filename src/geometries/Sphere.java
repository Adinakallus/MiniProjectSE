package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry {
    final Point3D _center;
    final double _radius;

    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }

    public Point3D getCenter() {
        return _center;
    }

    public double getRadius() {
        return _radius;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}