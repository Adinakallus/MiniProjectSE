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
     * constructor for spotLight
     * @param intensity intensity of the spotLight
     * @param position position of the spotLight
     * @param dir direction of the light
     */
    protected SpotLight(Color intensity, Point3D position, Vector dir) {
        super(intensity, position);
        _dir = dir.normalized();
    }

    /**
     *  finds the direction of the spotLight at the point that was received
     * @param p point where we want to know the intensity
     * @return intensity at point p
     */
    @Override
    public Color getIntensity(Point3D p) {

        Vector l=getL(p).normalized();
        double cosTheta=_dir.dotProduct(l);
        return super.getIntensity(p).scale(Math.max(0, cosTheta));
    }
}
