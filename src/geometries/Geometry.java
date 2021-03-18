package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry Interface for any given geometry
 *
 *@author Adina Kallus and Hadassa Israel
 */
public interface Geometry {
    Vector getNormal(Point3D point3D);
}