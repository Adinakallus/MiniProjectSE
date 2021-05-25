package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Intersectable interface for ray- geometry intersections
 *
 *@author Adina Kallus and Hadassa Israel
 */
public interface Intersectable {
    public static class GeoPoint{
        public Geometry geometry;
        public Point3D point;

        /**
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         *
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

    }

    /**
     *
     * @param ray- check the intersections between it and the Geometry shape
     * @return a list of all the intersections points
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().
                map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    List<GeoPoint> findGeoIntersections(Ray ray);

}
