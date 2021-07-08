package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    public BasicRayTracer rayTracer;

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    private boolean _adaptiveSampling = false;

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
     * @param basicRayTracer ray tracer for scene
     * @return current render
     */
    public Render setRayTracer(BasicRayTracer basicRayTracer) {
        this.rayTracer = basicRayTracer;
        return this;
    }

    /**
     *set adaptiveSampling
     * @param adaptiveSampling
     * @return current Render
     */
    public Render setAdaptiveSampling(boolean adaptiveSampling) {
        _adaptiveSampling = adaptiveSampling;
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

        Color pixelColor = new Color(0, 0, 0);
        Color background = rayTracer._scene.background;//background color

        //size of view plane
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        Ray mainRay;
        if(camera.get_dof()) {//
            if (_adaptiveSampling) {   //use adaptive super sampling acceleration
                for (int i = 0; i < ny; i++) {
                    for (int j = 0; j < nx; j++) {
                        Color avgColor = adaptiveSupersamplingAcceleration(nx, ny, j, i);
                        imageWriter.writePixel(j, i, avgColor);
                    }
                }
            }
            else {                  //use dof without acceleration
                for (int i = 0; i < ny; i++) {
                    for (int j = 0; j < nx; j++) {
                        mainRay = camera.constructRayThroughPixel(nx, ny, j, i);
                        List<Ray> beam = camera.constructRayBeamThroughPixelDOF(nx, ny, j, i);
                        beam.add(mainRay);
                        Color avgColor = calcAverageColor(rayTracer.traceBeam(beam));
                        imageWriter.writePixel(j, i, avgColor);
                    }
                }
            }
        }

        else {//render without any improvements
            for (int i = 0; i < ny; i++) {
                for (int j = 0; j < nx; j++) {
                    pixelColor = rayTracer.traceRay(camera.constructRayThroughPixel(nx, ny, j, i));
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }

        }
    }


    


    public Color adaptiveSupersamplingAcceleration(int nx, int ny, int j, int i) {
        //construct 5 rays through center,and 4 corners of the pixel,clockwise starting from upper left
         List<Ray> beam=camera.createAdaptiveBeamOfRays(nx,ny,j,i,2,null,0);
        return adaptiveSupersamplingAcceleration(nx,ny,j,i,1,2,camera.findPixel(nx,ny,j,i),beam,rayTracer.traceBeam(beam));
    }

    /**
     * 
     * @param nx
     * @param ny
     * @param j
     * @param i
     * @param rec_level
     * @param div
     * @param rays
     * @param colors
     * @return
     */
        private Color adaptiveSupersamplingAcceleration(int nx, int ny, int j, int i, int rec_level,int div,   Point3D prevCenter,
                                                        List<Ray>rays,List<Color>colors) {
            //calculate average color and compare to center ray
            Color average = calcAverageColor(colors);
            //if average is equal to center
            if (average.equals(colors.get(0)) &&rec_level <5) {

                //paramaters to help find the centers of new sub-pixels
                double moveZ = camera.getWidth() / nx/(div*2d);
                double moveY = camera.getHeight() / ny/(div*2d);

                //call function for first quarter
                Point3D upperRightCenter =prevCenter.add(camera.getvUp().scale(moveY)).add(camera.getvRight().scale(moveZ));
                //get list of rays that we havent found in order:[0(center),2,4]
                List<Ray>getBeam= camera.createAdaptiveBeamOfRays(nx, ny, j, i, div * 2, upperRightCenter, 1);
                List<Color> traced1=  rayTracer.traceBeam(getBeam);
                List<Color> tracedAll=List.of(traced1.get(0),colors.get(1),traced1.get(1),colors.get(0),traced1.get(2));
                List<Ray> upperRight =List.of(getBeam.get(0),rays.get(1),getBeam.get(1),rays.get(0),getBeam.get(2));
                average= adaptiveSupersamplingAcceleration(ny, nx, j, i, rec_level + 1, div * 2,prevCenter, upperRight, tracedAll );

                //call function second quarter
                Point3D upperLeftCenter =prevCenter.add(camera.getvUp().scale(moveY)).add(camera.getvRight().scale(-1*moveZ));
                //get list of rays that we havent found in order:[0(center),3]
                getBeam= camera.createAdaptiveBeamOfRays(nx, ny, j, i, div * 2, upperLeftCenter, 2);
                List<Color> traced2=  rayTracer.traceBeam(getBeam);
                List<Ray> upperLeft = List.of(getBeam.get(0),upperRight.get(2),rays.get(2),getBeam.get(1),rays.get(0));
                tracedAll=List.of(traced2.get(0),traced1.get(1),colors.get(2),traced2.get(1),colors.get(0));
               average.add( adaptiveSupersamplingAcceleration(ny, nx, j, i, rec_level + 1, div * 2,prevCenter, upperLeft, tracedAll) );


                //call function for 3rd quarter
                Point3D lowerLeftCenter =prevCenter.add(camera.getvUp().scale(-1*moveY)).add(camera.getvRight().scale(-1*moveZ));
                //get list of rays that we havent found in order:[0(center),4]
                getBeam= camera.createAdaptiveBeamOfRays(nx, ny, j, i, div * 2, lowerLeftCenter, 3);
                List<Color> traced3=  rayTracer.traceBeam(getBeam);
                List<Ray> lowerLeft = List.of(getBeam.get(0),rays.get(0),upperLeft.get(3),rays.get(3),getBeam.get(1));
                            tracedAll=List.of(traced3.get(0),colors.get(0),traced2.get(1),colors.get(3),traced3.get(1));
                average.add(adaptiveSupersamplingAcceleration(ny, nx, j, i, rec_level + 1, div * 2,prevCenter, lowerLeft, tracedAll ));

                //call function for 4th quarter
                Point3D lowerRightCenter =prevCenter.add(camera.getvUp().scale(-1*moveY)).add(camera.getvRight().scale(moveZ));
                getBeam= camera.createAdaptiveBeamOfRays(nx, ny, j, i, div * 2, lowerLeftCenter, 4);
                Color traced4=rayTracer.traceRay(upperRight.get(0));
                List<Ray>lowerRight= new LinkedList<>(List.of(getBeam.get(0),upperRight.get(4),rays.get(0),lowerLeft.get(4),rays.get(4)));
                           tracedAll=new LinkedList<>(List.of(traced4,       traced1.get(2),colors.get(0),traced3.get(1),colors.get(4)));
               average.add(adaptiveSupersamplingAcceleration(ny, nx, j, i, rec_level + 1, div * 2,prevCenter, lowerRight,
                        rayTracer.traceBeam(lowerRight)));
            }
            return average; //leave reccursion and return the average
        }

       //private Color findRayInList(Ray ray,List<Ray>rays,List<Color>colors){

       //    int index=rays.indexOf(ray);
       //    if(index==-1){//ray isn't in list
       //        return null;
       //    }
       //    return colors.get(index);

       //}

    /**
     * calculates the average color of all the colors in the list
     * @param tracedBeam list of colors of the traced rays
     * @return the avg color of beam
     */
    private Color calcAverageColor(List<Color> tracedBeam){
        Color avgColor = new Color(0,0,0);
        for(Color c:tracedBeam){
            avgColor = avgColor.add(c);
        }
        avgColor = avgColor.reduce(tracedBeam.size());
        return avgColor;
    }

    /**
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

