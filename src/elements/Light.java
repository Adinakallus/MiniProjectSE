package elements;

import primitives.Color;

abstract class Light {
    private final Color _intensity;

    /**
     * get the intensity of the light
     * @return intensity
     */
    public Color getIntensity() {
        return _intensity;
    }

    protected Light(Color intensity) {
        _intensity = intensity;
    }
}
