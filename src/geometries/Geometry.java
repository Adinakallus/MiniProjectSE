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

    protected Color _emission=Color.BLACK; //default emission color is black
    protected Material _material=new Material();//the material of the geometry

    /**
     * @return material
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     *
     * @param material
     * @return
     */
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
     * @return the Vector that is normal to the geometry
     */
  public abstract   Vector getNormal(Point3D point3D);



}