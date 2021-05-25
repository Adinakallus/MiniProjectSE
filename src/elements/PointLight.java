package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * PointLight class represents an omni directional light source,
 * where the intensity is calculated based on the initial intensity, the position,
 * and factoring for attenuation with distance
 */
public class PointLight extends Light implements LightSource {

    //position of light source
    private final Point3D _position;

    // Parameters to help calculate the intensity of the light at each point
    private double _kc =1.0;
    private double _kl =0;
    private double _kq =0;


    protected PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    public double getKc() {
        return _kc;
    }

    public PointLight setKc(double kc) {
        this._kc = kc;
        return this;
    }

    public double getKl() {
        return _kl;
    }

    public PointLight setKl(double kl) {
        this._kl = kl;
        return this;
    }

    public double getKq() {
        return _kq;
    }

    public PointLight setKq(double kq) {
        this._kq = kq;
        return this;
    }

    /**
     *
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point3D p) {
    double d=p.distance(_position);
    double factor=1d/(_kc+_kl*d+_kq*d*d);
    return getIntensity().scale(factor);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalized();
    }
}
