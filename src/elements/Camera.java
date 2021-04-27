package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;


public class Camera {
    private Vector _vUp;
    private Vector _vRight;
    private Vector _vTo;
    private Point3D _po;
    private double _width;
    private double _height;
    private double _distance;


    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Point3D getPo() {
        return _po;
    }

    public double getHeight() {
        return _height;
    }

    public double getWidth() {
        return _width;
    }

    public double getDistance() {
        return _distance;
    }


    public Camera(Point3D p0, Vector vTO, Vector vUP ) {
        _po = p0;
        if (!isZero(vTO.dotProduct(vUP))) {
            throw new IllegalArgumentException("vUP is not orthogonal to vTO");
        }
        _vUp = vUP.normalized();
        _vTo = vTO.normalized();
        _vRight = _vTo.crossProduct(_vUp);

    }

    public Camera setViewPlaneSize(double width, double height){
        _height=height;
        _width=width;
        return this;
    }

    public Camera setDistance(double distance){
      _distance=distance;
      return this;
    }


        // constructing a ray passing through pixel(i,j) of the view plane
        public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
           //Image center
            Point3D Pc = _po.add(_vTo.scale(_distance));

            //Ratio(pixel width and height)
            double Rx = _width / nX;
            double Ry = _height / nY;

            Point3D Pij = Pc;


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
