package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry
{
    final Ray _axysRay;
    final double _radius;

    public Tube(Ray axysRay, double radius) {
        _axysRay = axysRay;
        _radius = radius;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}