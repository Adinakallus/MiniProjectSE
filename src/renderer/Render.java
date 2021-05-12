package renderer;

import elements.Camera;
import primitives.Color;

import java.util.MissingResourceException;

/**
        * Renderer to create matrix with the colors
 *          of the picture in the scene
        *
        * @author Adina Kallus and Hadassa Israel
        */
public class Render {
    public ImageWriter imageWriter;
    public Camera camera;
    public RayTracerBase rayTracerBase;

    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    public void renderImage(){
        if(imageWriter==null)
            throw new MissingResourceException("imageWriter is missing","Render","ImageWriter");
        if(camera==null)
            throw new MissingResourceException("camera is missing","Render","camera");
        if(rayTracerBase==null)
            throw new MissingResourceException("rayTracer is missing","Render","rayTracerBase");

        throw new UnsupportedOperationException();
    }

    public void printGrid(int interval, Color color){
        ImageWriter viewPlane = new ImageWriter("grid", 800, 500);
        if(viewPlane==null)
            throw new MissingResourceException("image writer is null", "Render","ImageWriter");

        for (int i = 0; i < viewPlane.getNy(); i++) {
            for (int j = 0; j < viewPlane.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    viewPlane.writePixel(j, i,color);
                }
            }
        }
        viewPlane.writeToImage();
    }
    public void writeToImage(){
        if(imageWriter==null)
            throw new MissingResourceException("image writer is null", "Render","ImageWriter");
        imageWriter.writeToImage();
    }
}

