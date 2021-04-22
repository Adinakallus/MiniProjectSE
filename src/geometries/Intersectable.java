package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface for ray- geometry intersections
 *
 *@author Adina Kallus and Hadassa Israel
 */
public interface Intersectable {
    List<Point3D> findIntersections(Ray ray);
}
