package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * LightSource class represents the source of 3 types of light
 *
 *  @author Adina Kallus and Hadassa Israel
 */
public interface LightSource {

    /**
     * gets a point on an object and finds the intensity of the light at that point
     * @param p  3D point
     * @return color- the intensity of the light in the point
     */
    public Color getIntensity(Point3D p);

    /**
     * gets a point on an object and find the direction of the light in this point
     * @param p  3D point
     * @return a vector of the direction of the light in the point
     */
    public Vector getL(Point3D p);

    double getDistance(Point3D point);
}
