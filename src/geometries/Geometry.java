package geometries;

import elements.Material;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry Interface for any given geometry that has a normal vector
 *
 *@author Adina Kallus and Hadassa Israel
 */
public abstract class Geometry implements Intersectable {
    /**
     * defult emission color is black
     */
    protected Color _emission=Color.BLACK;
    protected Material _material=new Material();

    public Material getMaterial() {
        return _material;
    }

    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * chaining setter method for the emission
     * @param emission: emission color inherent to the current geometry
     * @return this:the current geometry object
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * getter method for the emission
     * @return emission
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     *
     * @param point3D should be null for flat geometries
     * @return the normal to the geometry
     */
  public abstract   Vector getNormal(Point3D point3D);



}