package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;


/**
 * SpotLight class represent a light source like point light with one difference- it has a direction
 *
 * @author Adina Kallus Hadassa Israel
 */
public class SpotLight extends PointLight {
    private final Vector _dir;

    /**
     * constructor of the SpotLight
     * @param intensity the intensity of the spotLight
     * @param position the position of the spotLight
     * @param dir the direction of the spotLight
     */
    protected SpotLight(Color intensity, Point3D position, Vector dir) {
        super(intensity, position);
        _dir = dir.normalized();
    }

    /**
     * finds the direction of the spotLight at the point that was received
     * @param p point 3D
     * @return the color of the intensity in the point
     */
    @Override
    public Color getIntensity(Point3D p) {

        Vector l=getL(p).normalized();
        double cosTheta=_dir.dotProduct(l);
        return super.getIntensity(p).scale(Math.max(0, cosTheta));
    }
}
