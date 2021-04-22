package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry Interface for any given geometry that has a normal vector
 *
 *@author Adina Kallus and Hadassa Israel
 */
public interface Geometry extends Intersectable {
    /**
     *
     * @param point3D should be null for flat geometries
     * @return the normal to the geometry
     */
    Vector getNormal(Point3D point3D);
}