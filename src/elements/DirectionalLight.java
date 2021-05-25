package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private final Vector _direction;
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     *
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point3D p) {
         return super.getIntensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return null;
    }
}
