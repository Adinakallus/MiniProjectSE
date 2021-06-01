package elements;

import primitives.Color;

/**
 * AmbientLight class represents the AmbientLight of the scene
 *
 *  @author Adina Kallus and Hadassa Israel
 */

public class AmbientLight extends Light {


    /**
     * default constructor- restart intensity to black-a neutral color
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * creating an ambient light
     * @param iA color of ambient light
     * @param kA intensity of the ambient light
     */
    public AmbientLight(Color iA,double kA) {
      super(iA.scale(kA));
    }


}
