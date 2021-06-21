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

    /**
     *
     * pds to combine a point and geometry that its on,
     * so we can know the color of the point while ray tracing
     *
     */
    public static class GeoPoint{
        public Geometry geometry;
        public Point3D point;
        /**
         * constructor for geometry
         * @param geometry  the geometry that point is on
         * @param point the point who's color we want
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * equals method compares two geoPoints
         * @param o
         * @return true if geoPoints are equal to each other, and false otherwise
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
     *  finds all intersections between received ray and all the geometries in the list
     * @param ray-  ray which we want to find the intersections with
     * @return list of all intersection points
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().
                map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    /**
     *  finds all intersections between received ray and all the geometries in the list
     * @param ray-  ray which we want to find the intersections with
     * @return list of all geoPoints that had an intersection with ray
     */
  default   List<GeoPoint> findGeoIntersections(Ray ray){
      return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
  }

    /**
     * within the given distance, finds all intersections   between received ray and all the geometries in the list
     * @param ray-  ray which we want to find the intersections with
     * @param maxDistance given distance
     * @return list of all geoPoints that had an intersection with ray, the are within the given distance
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

}
