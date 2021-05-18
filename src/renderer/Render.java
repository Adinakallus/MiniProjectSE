package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

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
    public RayTracerBasic rayTracer;


    /**
     *
     * set imageWriter
     * @param imageWriter holds image related parameters of View Plane - pixel matrix
      * size and resolution
     * @return current render
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }


    /**
     *
     * set camera
     * @param camera point that rays start from and continue towards the scene
     * @return current render
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     *
     * set rayTracer
     * @param rayTracerBasic ray tracer for scene
     * @return current render
     */
    public Render setRayTracer(RayTracerBasic rayTracerBasic) {
        this.rayTracer = rayTracerBasic;
        return this;
    }

    /**
     * gets correct color for each pixel in the image,
     * using ray tracing, and writes it to the pixel
     */
    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter is missing", "Render", "ImageWriter");
        if (camera == null)
            throw new MissingResourceException("camera is missing", "Render", "camera");
        if (rayTracer == null)
            throw new MissingResourceException("rayTracer is missing", "Render", "rayTracerBase");
        Color pixelColor= new Color(0,0,0);
        int nx= imageWriter.getNx();
        int ny= imageWriter.getNy();
        for (int i = 0; i < ny; i++) {
            for (int j = 0; j < nx; j++) {
             pixelColor=rayTracer.traceRay(camera.constructRayThroughPixel(nx,ny,j,i));
                imageWriter.writePixel(j, i,pixelColor);
                }
            }
        }


    /**
     *
     * prints a grid onto viewPlane
     * @param interval size of each box in the grid
     * @param color color of the grid
     */
    public void printGrid(int interval, Color color){
        if(imageWriter==null)
            throw new MissingResourceException("image writer is null", "Render","ImageWriter");

        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i,color);
                }
            }
        }
        imageWriter.writeToImage();
    }

    /**
     * Function writeToImage produces unoptimized png file of the image according to
     * pixel color matrix in the directory of the project
     */
    public void writeToImage(){
        if(imageWriter==null)
            throw new MissingResourceException("image writer is null", "Render","ImageWriter");
        imageWriter.writeToImage();
    }

}

