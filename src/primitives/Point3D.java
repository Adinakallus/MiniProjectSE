package primitives;

/**
 * Class Point3D is the basic class representing a 3D point.
 *
 * @author Adina Kallus and Hadassa Israel
 */
public class Point3D {
    //static Point3D for origin point (0,0,0)*
    public final static Point3D ZERO = new Point3D(0, 0, 0);

    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;


    /**
     * constructor for Point3D
     *
     * @param x coordinate for X axis
     * @param y coordinate for Y axis
     * @param z coordinate for Z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = x;
        _y = y;
        _z = z;
    }

    /**
     * primary constructor for Point3D
     * @param x coordinate value for X axis
     * @param y coordinate value for Y axis
     * @param z coordinate value for Z axis
     */
    public Point3D(double x, double y, double z) {
        this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    //getters
    public double getX() {
        return _x._coord;
    }
    public double getY() {
        return _y._coord;
    }
    public double getZ() {
        return _z._coord;
    }
/**
 * @param vec Vector  to add to this point
 * @return new Point3D the result of the addition

 */

    public Point3D add(Vector vec) {
        double x = _x._coord + vec._head._x._coord;
        double y = _y._coord + vec._head._y._coord;
        double z = _z._coord + vec._head._z._coord;

        Point3D answer = new Point3D(x, y, z);

        return answer;
    }

    /**
     * @param point Point3D  from whom we create the Vector
     *                pointing towards this Point3d instance
     * @return new vector pointing towards this Point3d instance
     */
    public Vector subtract(Point3D point) {
        double x = _x._coord - point._x._coord;
        double y = _y._coord - point._y._coord;
        double z = _z._coord - point._z._coord;

        Vector answer = new Vector(new Point3D(x, y, z));
        return answer;

    }

    /**
     * @param point point3D from whom we want to get the distance squared
     * @return ( xx+yy+zz)
     */
    public double distanceSquared(Point3D point) {
        double xx = (_x._coord - point._x._coord)*(_x._coord - point._x._coord);
        double yy = (_y._coord - point._y._coord)*(_y._coord - point._y._coord);
        double zz = (_z._coord - point._z._coord)*(_z._coord - point._z._coord);

        return( xx+yy+zz);
    }

    /**
     * @param point point3D from whom we want to get the distance
     * @return  distance between 2  3D points
     */
    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }


    /**
     *
     * @param o Object ( another Point3d) to compare
     * @return true or false accordingly
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }


    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + _x +
                ", y=" + _y +
                ", z=" + _z +
                '}';
    }
}