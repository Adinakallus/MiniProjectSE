package elements;

import primitives.Color;

/**
 * Light class represents the intensity of the light in the scene
 *
 *  @author Adina Kallus and Hadassa Israel
 */
abstract class Light {
    private final Color _intensity;

    /**
     * get the intensity of the light
     * @return intensity of the light
     */
    public Color getIntensity() {

        return _intensity;
    }

    /**
     * constructor for light
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }
}
