package elements;

public class Material {
    public double _kd=0;
    public double _ks=0;
    public int _nShininess=0;

    public double getKd() {
        return _kd;
    }

    public Material setKd(double kd) {
        _kd = kd;
        return this;
    }

    public double getKs() {
        return _ks;
    }

    public Material setKs(double ks) {
        _ks = ks;
        return this;
    }

    public int getShininess() {
        return _nShininess;
    }

    public Material setShininess(int nShininess) {
        _nShininess = nShininess;
        return this;
    }
}

