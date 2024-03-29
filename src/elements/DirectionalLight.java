package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * DirectionalLight class represent a light source where the intensity doesn't depend
 * on the distance between the light source and the object
 *
 *  @author Adina Kallus and Hadassa Israel
 */

public class DirectionalLight extends Light implements LightSource{
    private final Vector _direction;

    /**
     * constructor for directional light
     * @param intensity   the intensity of the light
     * @param direction the vector with the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     *get a point on an object and find the direction of the directional light in this point
     * @param p  3D point
     * @return the intensity of the directional light in the point
     */
    @Override
    public Color getIntensity(Point3D p) {

        return super.getIntensity();
    }

    /**
     * get a point on an object and find the direction of the directional light in this point
     * @param p  3D point
     * @return a vector of the direction of the directional light in the point
     */
    @Override
    public Vector getL(Point3D p) {

        return _direction;
    }

    /**
     * gets distance from light source
     * @param point 3D point
     * @return distance
     */
    @Override
    public double getDistance(Point3D point) {

        return Double.POSITIVE_INFINITY;
    }


}
