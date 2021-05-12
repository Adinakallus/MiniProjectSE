package elements;

import primitives.Color;

public class AmbientLight {
    private final Color _intensity;

    public AmbientLight(Color iA,double kA) {
        _intensity = new Color(iA.scale(kA));
    }

    public Color getIntensity() {
        return _intensity;
    }
}
