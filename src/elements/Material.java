package elements;

/**
 * Material class that represents the material of the object
 *
 *  @author Adina Kallus and Hadassa Israel
 */
public class Material {
    public double _kd=0;
    public double _ks=0;
    public int _nShininess=0;

    /**
     * @return kd
     */
    public double getKd() {
        return _kd;
    }

    /**
     * sets a new value in kd
     * @param kd a new variable that will be inserted with the new value
     * @return the current material
     */
    public Material setKd(double kd) {
        _kd = kd;
        return this;
    }

    /**
     * @return ks
     */
    public double getKs() {
        return _ks;
    }

    /**
     * sets a new value in kd
     * @param ks value to set ks to
     * @return the current material
     */
    public Material setKs(double ks) {
        _ks = ks;
        return this;
    }

    /**
     * @return nShininess
     */
    public int getShininess() {
        return _nShininess;
    }

    /**
     * sets a new value in shininess
     * @param nShininess  value to set nShininess to
     * @return the current material
     */
    public Material setShininess(int nShininess) {
        _nShininess = nShininess;
        return this;
    }
}

