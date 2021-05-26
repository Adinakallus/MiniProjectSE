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

    protected SpotLight(Color intensity, Point3D position, Vector dir) {
        super(intensity, position);
        _dir = dir.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {

        Vector l=getL(p).normalized();
        double cosTheta=_dir.dotProduct(l);
        return super.getIntensity(p).scale(Math.max(0, cosTheta));
    }
}
