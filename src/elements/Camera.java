package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * camera class represents the point
 * from which all the rays start,
 * and continue through the viewPlane towards the scene
 *
 * @author Adina Kallus and Hadassa Israel
 */
public class Camera {

    private Vector _vUp;
    private Vector _vRight;
    private Vector _vTo;
    private Point3D _po;
    private double _width;
    private double _height;
    private double _distance;

    /**
     *
     * @return vTo
     */
    public Vector getvTo() {
        return _vTo;
    }

    /**
     *
     * @return vRight
     */
    public Vector getvRight() {
        return _vRight;
    }

    /**
     *
     * @return vUp
     */
    public Vector getvUp() {
        return _vUp;
    }

    /**
     *
     * @return po
     */
    public Point3D getPo() {
        return _po;
    }

    /**
     *
     * @return height
     */
    public double getHeight() {
        return _height;
    }

    /**
     *
     * @return width
     */
    public double getWidth() {
        return _width;
    }

    /**
     *
     * @return distance
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * constructor for Camera
     *
     * @param p0  the location of the camera
     * @param vTO vector to
     * @param vUP vector up
     */
    public Camera(Point3D p0, Vector vTO, Vector vUP) {
        _po = p0;
        if (!isZero(vTO.dotProduct(vUP))) {
            throw new IllegalArgumentException("vUP is not orthogonal to vTO");
        }
        _vUp = vUP.normalized();
        _vTo = vTO.normalized();
        _vRight = _vTo.crossProduct(_vUp);

    }

    /**
     * sets the view planes size
     *
     * @param width  width of the view plane
     * @param height height of the view plane
     * @return current camera
     */
    public Camera setViewPlaneSize(double width, double height) {
        _height = height;
        _width = width;
        return this;
    }

    /**
     * sets the distance between view plane and camera
     *
     * @param distance the distance of the view plane
     * @return the current camera
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }


    /**
     * constructing a ray passing through pixel(i,j) of the view plane
     * @param nX number of columns in the view plane
     * @param nY number of rows in the view plane
     * @param j the column of the pixel that we go to?
     * @param i the row of the pixel that we go to?
     * @return constructed ray
     */

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        //Image center
        Point3D Pc = _po.add(_vTo.scale(_distance));

        //Ratio(pixel width and height)
        double Rx = _width / nX;
        double Ry = _height / nY;

        Point3D Pij = Pc;

        //calculate the distance of the pixel from the center of the view plane
        double Yi = -(i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;

        if (isZero(Xj) && isZero(Yi)) {
            return new Ray(_po, Pij.subtract(_po));
        }
        if (isZero(Xj)) {
            Pij = Pij.add(_vUp.scale(Yi));
            return new Ray(_po, Pij.subtract(_po));
        }
        if (isZero(Yi)) {
            Pij = Pij.add(_vRight.scale(Xj));
            return new Ray(_po, Pij.subtract(_po));
        }

        Pij = Pij.add(_vRight.scale(Xj).add(_vUp.scale(Yi)));
        return new Ray(_po, Pij.subtract(_po));

    }

}
