package primitives;

/**
 * Ray for RayTracing
 *
 *@author Adina Kallus and Hadassa Israel
 */
public class Ray {
    Point3D _p0;
    Vector _dir;


    /**
     * Constructor for Ray class
     *
     * @param p0 starting point of Ray
     * @param dir direction of Ray
     */
    public Ray(Point3D p0, Vector dir) {
      _p0 = p0;
      _dir = dir.normalized();
    }


/**
 * @return p0
 */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * @return new vector with same head as dir
     */
    public Vector getDir() {
        return new Vector(_dir._head);
    }

    /**
     * @param o Object ( another Ray) to compare
     * @return true or false accordingly
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }


    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + _p0 +
                ", dir=" + _dir +
                '}';
    }
}