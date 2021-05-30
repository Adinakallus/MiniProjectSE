package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * PointLight class represents an omni directional light source,
 * where the intensity is calculated based on the initial intensity, the position,
 * and factoring for attenuation with distance
 *
 * @author Adina Kallus and Hadassa Israel
 */
public class PointLight extends Light implements LightSource {

    //position of light source
    private final Point3D _position;

    // Parameters to help calculate the intensity of the light at each point
    private double _kc = 1.0;
    private double _kl = 0;
    private double _kq = 0;

    /**
     * constructor of the PointLight
     * @param intensity the intensity of the pointLight
     * @param position the position of the pointLight
     */
    protected PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * @return kc
     */
    public double getKc() {
        return _kc;
    }

    /**
     * sets a new value in kc
     * @param kc value to set kc
     * @return current PointLight
     */
    public PointLight setKc(double kc) {
        _kc = kc;
        return this;
    }

    /**
     * @return kl
     */
    public double getKl() {
        return _kl;
    }

    /**
     * sets a new value in kl
     * @param kl value to set kl
     * @return current PointLight
     */
    public PointLight setKl(double kl) {
        _kl = kl;
        return this;
    }

    /**
     * @return kq
     */
    public double getKq() {
        return _kq;
    }

    /**
     * sets a new value in kq
     * @param kq value to set kq
     * @return current PointLight
     */
    public PointLight setKq(double kq) {
        _kq = kq;
        return this;
    }

    /**
     * finds the direction of the light at the point that was received
     * @param p point 3D
     * @return the intensity of the light in the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(_position);
        double factor = 1d / (_kc + _kl * d + _kq * d * d);
        return getIntensity().scale(factor);
    }

    /**
     * finds the direction of the light at the point that was received
     * @param p 3D point
     * @return a vector of the direction of the pointLight that is in the point
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalize();
    }
}
