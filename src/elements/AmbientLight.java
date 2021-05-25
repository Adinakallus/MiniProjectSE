package elements;

import primitives.Color;

public class AmbientLight extends Light {


    /**
     * default constructor- restart intensity to black
     */
    protected AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * constructor for ambient light
     * @param iA color of ambient light
     * @param kA intensity of the ambient light
     */
    public AmbientLight(Color iA,double kA) {
      super(iA.scale(kA));
    }


}
