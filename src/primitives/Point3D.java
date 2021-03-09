package primitives;

import java.util.Objects;

public class Point3D {
    public final static Point3D ZERO = new Point3D(0, 0, 0);

    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    public Point3D(double x, double y, double z) {
        this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    /**
     * primary constructor for Point3D
     *
     * @param x
     * @param y
     * @param z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = x;
        _y = y;
        _z = z;
    }


    public Point3D add(Vector vec) {
        double x = _x.coord + vec._head._x.coord;
        double y = _y.coord + vec._head._y.coord;
        double z = _z.coord + vec._head._z.coord;

        Point3D answer = new Point3D(x, y, z);

        return answer;
    }

    public Vector subtract(Point3D point) {
        double x = _x.coord - point._x.coord;
        double y = _y.coord - point._y.coord;
        double z = _z.coord - point._z.coord;

        Vector answer = new Vector(new Point3D(x, y, z));
        return answer;

    }

    public double distanceSquared(Point3D point) {
        double xx = (_x.coord - point._x.coord)*(_x.coord - point._x.coord);
        double yy = (_y.coord - point._y.coord)*(_y.coord - point._y.coord);
        double zz = (_z.coord - point._z.coord)*(_z.coord - point._z.coord);

        return( xx+yy+zz);
    }

    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }

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