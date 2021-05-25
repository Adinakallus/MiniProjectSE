package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    private List<Intersectable> _intersectables = new ArrayList<>();


    public Geometries(Intersectable... geom) {
        add(geom);
    }

    public void add(Intersectable... geom) {
        if (_intersectables == null) {
            _intersectables = new ArrayList<>();
        }
        Collections.addAll(_intersectables, geom);
    }

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
