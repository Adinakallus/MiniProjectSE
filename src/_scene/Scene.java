package _scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    private final String _name;
    public Color background=Color.BLACK;
    public AmbientLight ambientLight=new AmbientLight(Color.BLACK, 1d);
    public Geometries geometries;
    public List<LightSource> lights=new LinkedList<>();

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    public Scene(String name) {
        _name = name;
        geometries=new Geometries();
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
