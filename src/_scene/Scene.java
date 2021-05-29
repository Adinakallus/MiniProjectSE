package _scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class that represents the scene
 *
 *  @author Adina Kallus and Hadassa Israel
 */

public class Scene {
    private final String _name;
    public Color background=Color.BLACK;
    public AmbientLight ambientLight=new AmbientLight(Color.BLACK, 1d);
    public Geometries geometries;
    public List<LightSource> lights=new LinkedList<>();

    /**
     *changes the list of lights that are in the scene
     * @param lights the new list of lights
     * @return the scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * constructor of scene
     * @param name the name of the scene
     */
    public Scene(String name) {
        _name = name;
        geometries=new Geometries();
    }

    /**
     * set the scenes background color
     * @param background the new background
     * @return the scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * set the scenes ambientLight
     * @param ambientLight the new ambientLight
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * set the geometries list of the scene
     * @param geometries the new geometries of the scene
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
