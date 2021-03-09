package geometries;

import primitives.Ray;

public class Cylinder extends Tube
{   // implements Geometry
    final double _height;

    public Cylinder(Ray axisRay, double radius,double height) {
        super(axisRay,radius);
        _height = height;
    }
}