package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Geometries class represents a group of multiple Geometries together
 *
 *@author Adina Kallus and Hadassa Israel
 */

public class Geometries implements Intersectable {
    private List<Intersectable> _intersectables = new ArrayList<>();

    /**
     * constructs a group of geometries
     * @param geom list of geometries we want to group together
     */
    public Geometries(Intersectable... geom) {
        add(geom);
    }

    /**
     *adds geometries to list
     * @param geom the geometry that we add to the list
     */
    public void add(Intersectable... geom) {
        if (_intersectables == null) {
            _intersectables = new ArrayList<>();
        }
        Collections.addAll(_intersectables, geom);
    }

    /**
     *  finds all intersections between received ray and all the geometries in the list
     * @param ray-  ray which we want to find the intersections with
     * @return list of all intersection points
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;
        for (Intersectable geometry : _intersectables) {
            //get intersection points of a particular geometry from _intersectables
            List<Point3D> geomPoints = geometry.findIntersections(ray);
            if (geomPoints != null) {
                //first time initialize result to new LinkList
                if (result == null) {
                    result = new LinkedList<>();
                }
                //add all item points to the resulting list
                result.addAll(geomPoints);
            }
        }
        return result;
    }

    /**
     *  finds all intersections between received ray and all the geometries in the list
     * @param ray-  ray which we want to find the intersections with
     * @return list of all geoPoints that had an intersection with ray
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable geometry : _intersectables) {
            //get intersection points of a particular geometry from _intersectables
            List<GeoPoint> geomPoints = geometry.findGeoIntersections(ray);
            if (geomPoints != null) {
                //first time initialize result to new LinkList
                if (result == null) {
                    result = new LinkedList<>();
                }
                //add all item points to the resulting list
                result.addAll(geomPoints);
            }
        }
        return result;
    }
}
