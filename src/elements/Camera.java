package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;
import static primitives.Util.random;

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
    private Point3D _p0;
    private double _width;
    private double _height;
    private double _distance;
    private boolean _dof=false;
    private double _aperture;
    private int _amountOfRays;
    private double _focalDistance;

    /**
     * @return vTo
     */
    public Vector getvTo() {
        return _vTo;
    }

    /**
     * @return vRight
     */
    public Vector getvRight() {
        return _vRight;
    }

    /**
     * @return vUp
     */
    public Vector getvUp() {
        return _vUp;
    }

    /**
     * @return po
     */
    public Point3D getPo() {
        return _p0;
    }

    /**
     * @return height
     */
    public double getHeight() {
        return _height;
    }

    /**
     * @return width
     */
    public double getWidth() {
        return _width;
    }

    /**
     * @return distance
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * @return dof
     */
    public boolean get_dof() {
        return _dof;
    }

    /**
     * sets the depth of field
     * @param _dof
     * @return current camera
     */
    public Camera set_dof(boolean _dof) {
        this._dof = _dof;
        return this;
    }

    /**
     * @return aperture
     */
    public double get_aperture() {
        return _aperture;
    }

    /**
     * sets the aperture size
     * @param _aperture
     * @return current camera
     */
    public Camera set_aperture(double _aperture) {
        this._aperture = _aperture;
        return this;
    }

    /**
     * @return amountOfRays
     */
    public int get_amountOfRays() {
        return _amountOfRays;
    }

    /**
     * sets the ammount of rays
     * @param _amountOfRays
     * @return current camera
     */
    public Camera set_amountOfRays(int _amountOfRays) {
        this._amountOfRays = _amountOfRays;
        return this;
    }

    /**
     * @return focalDistance
     */
    public double get_focalDistance() {
        return _focalDistance;
    }

    /**
     * sets the focal distance
     * @param _focalDistance
     * @return the current camera
     */
    public Camera set_focalDistance(double _focalDistance) {
        this._focalDistance = _focalDistance;
        return this;
    }

    /**
     * constructor for Camera
     * @param p0  the location of the camera
     * @param vTO vector to
     * @param vUP vector up
     */
    public Camera(Point3D p0, Vector vTO, Vector vUP) {
        _p0 = p0;
        if (!isZero(vTO.dotProduct(vUP))) {
            throw new IllegalArgumentException("vUP is not orthogonal to vTO");
        }
        _vUp = vUP.normalized();
        _vTo = vTO.normalized();
        _vRight = _vTo.crossProduct(_vUp);


    }

    /**
     * sets the view planes size
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
     * @param j the column of the pixel that we go to
     * @param i the row of the pixel that we go to
     * @return constructed ray
     */

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D pij=findPixel(nX, nY, j, i);
            return new Ray(_p0, pij.subtract(_p0));
    }


    /**
     * constructs a ray from a random point on the aperture through the focal point
     *
     *
     * @return constructed ray
     */
    public Ray constructRayThroughPixelDOF(Point3D pij) {


        Vector dir=pij.subtract(_p0).normalized();//direction from camera towards the object through the view plane

        Point3D focalPoint=pij.add(dir.scale(_focalDistance-_distance));//find focal point based on line formula (x,y,z)+t*(a,b,c)
        //
        //double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();

        //create  from random point in aperture
        Point3D randPoint;
        Random r = new Random();
        double minY=-_p0.getY()-_aperture;
        double maxY=-_p0.getY()+_aperture;
        double minZ=-_p0.getZ()-_aperture;
        double maxZ=-_p0.getZ()+_aperture;
        double randY =minY+(maxY-minY)*r.nextDouble();
        double randZ =minZ+(maxZ-minZ)*r.nextDouble();
        randPoint = new Point3D(_p0.getX(),  randY,  randZ);

        return new Ray(randPoint, focalPoint.subtract(randPoint));


    }




    public   List<Ray> constructRayBeamThroughPixelDOF(int nX, int nY, int j, int i) {

        List<Ray> beam = new LinkedList<>();//list of rays representing the beam
        Point3D pij = findPixel(nX, nY, j, i);//current pixel on view plane
        for (int k = 1; k <_amountOfRays; k++) {
            beam.add(constructRayThroughPixelDOF(pij));
        }
        return beam;
    }



    /**
     * creates a beam of rays that start at the view plane towards the focal
     point
     * @param nX amount of pixels in a row
     * @param nY amount of pixels in a column
     * @param j index j
     * @param i index i
     * @return List of Rays
     */
/**    public List<Ray> createBeamOfRays(int nX, int nY, int j, int i){

        List<Ray> beam = new LinkedList<>();//list of rays representing the beam
        //find the current pixel
        Point3D pij=findPixel(nX, nY, j, i);

        //vector towards the focal point
        Vector mainRayDir = pij.subtract(_p0).normalize();

        //if we are only sending one ray, or the the aperture is almost closed, we only send original ray
        if (_amountOfRays == 1 || isZero(_aperture)) {
            beam.add(new Ray(_p0, mainRayDir));
            return beam;
        }

        //create ray from pixel on view plane towards focal point, and add it to the beam
        Ray mainRay=new Ray(pij,mainRayDir);
        beam.add(mainRay);

        //calculates the focal point
        Point3D focalPoint = pij.add(mainRayDir.scale(_focalDistance/ _vTo
                .dotProduct(mainRayDir)));

        //calculates random rays in aperture
        for (int k = 1; k <_amountOfRays; k++) {
            Point3D randomRayPoint = new Point3D(pij.getX(),pij.getY(), pij.getZ());//starting point of random ray
            //calculate random numbers used to move the point
            double x = _rand.nextDouble() * 2 - 1;
            double y = Math.sqrt(1 - x * x);
            double mult = (_rand.nextDouble()-0.5)*_aperture;
            if (!isZero(x))
                randomRayPoint = randomRayPoint.add(_vRight.scale(x * mult));
            if (!isZero(y))
                randomRayPoint = randomRayPoint.add(_vUp.scale(y * mult));
            beam.add(new Ray(randomRayPoint,
                    focalPoint.subtract(randomRayPoint).normalized()));
        }
        return beam;

    }*/

    /**


public List<Ray> createAdaptiveBeamOfRays(int nX, int nY, int j, int i, double div, Point3D sub_center, List<Ray>calculatedRays){
    List<Ray> beam=new LinkedList<>();

    //find center of current pixel
    Point3D pij=findPixel(nX, nY, j, i);

    //vector towards the focal point
    Vector mainRayDir = pij.subtract(_p0).normalize();

    Point3D center=pij;

    //if point recieved is differnt then calculated point, then this is a sub pixel
    if(!sub_center.equals(pij)){
        center=sub_center;
    }
    //calculates the focal point
    Point3D focalPoint = pij.add(mainRayDir.scale(_focalDistance/ _vTo.dotProduct(mainRayDir)));

    beam.add(new Ray(center,mainRayDir));//add center ray

    //if we are only sending one ray, or the the aperture is almost closed, we only send original ray
    if (_amountOfRays == 1 || isZero(_aperture)) {
        return beam;
    }

    double distToCorners=_aperture/div;

    //move from center of pixel to the corners,and add them to the beam:


    //upper left
    Point3D  corner =new Point3D(center.getX()-distToCorners, center.getY()+distToCorners, center.getZ());
    if()
    beam.add(new Ray(corner,focalPoint.subtract(corner).normalized()));

    //upper right
    corner =new Point3D(center.getX()+distToCorners, center.getY()+distToCorners, center.getZ());
    beam.add(new Ray(corner,focalPoint.subtract(corner).normalized()));

    //lower right
    corner =new Point3D(center.getX()+distToCorners, center.getY()-distToCorners, center.getZ());
    beam.add(new Ray(corner,focalPoint.subtract(corner).normalized()));

    //Lower left
     corner =new Point3D(center.getX()-distToCorners, center.getY()-distToCorners, center.getZ());
    beam.add(new Ray(corner,focalPoint.subtract(corner).normalized()));

    return beam;

}
*/




    /**
     * helper function to find the center point of pixel ij
     * @param nX number of columns in the view plane
     * @param nY number of rows in the view plane
     * @param j the column of the pixel that we go to
     * @param i the row of the pixel that we go to
     * @return center point of pixel ij
     */
    public Point3D findPixel(int nX, int nY, int j, int i){
        //Image center
        Point3D Pc = _p0.add(_vTo.scale(_distance));

        //Ratio(pixel width and height)
        double Rx = _width / nX;
        double Ry = _height / nY;

        Point3D pij = Pc;

        //calculate the distance of the pixel from the center of the view plane
        double yi = -(i - (nY - 1) / 2d) * Ry;
        double xj = (j - (nX - 1) / 2d) * Rx;

        if (!isZero(xj)) {
            pij = pij.add(_vRight.scale(xj));
        }
        if (!isZero(yi)) {
            pij = pij.add(_vUp.scale(yi));
        }
        return pij;



    }

}
