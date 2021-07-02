package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;

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
    private boolean _superSampling = false;

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
     *set adaptiveSampling
     * @param superSampling
     * @return current Render
     */
    public Render setSuperSampling(boolean superSampling) {
        _superSampling = superSampling;
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
        if (camera.get_dof()) {//use depth of field
            for (int i = 0; i < ny; i++) {
                for (int j = 0; j < nx; j++) {
                    mainRay = camera.constructRayThroughPixel(nx, ny, j, i);
                    List<Ray> beam = camera.constructRayBeamThroughPixelDOF(nx, ny, j, i);
                    beam.add(mainRay);
                    Color avgColor = calcAverageColor(beam);
                    imageWriter.writePixel(j, i, avgColor);
                }
            }
        }
        //      if(_superSampling){//use superSampling
        //          for (int i = 0; i < ny; i++) {
        //              for (int j = 0; j < nx; j++) {
        //                  mainRay = camera.constructRayThroughPixel(nx, ny, j, i);
        //                  for (int k = 0; k < 100; k++) {
        //                      pixelColor = rayTracer.traceRay(camera.constructRayThroughPixel(nx, ny, j, i));
        //                  }
        //              }
        //          }
        //  }
        else {//render without any improvements
            for (int i = 0; i < ny; i++) {
                for (int j = 0; j < nx; j++) {
                    pixelColor = rayTracer.traceRay(camera.constructRayThroughPixel(nx, ny, j, i));
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }
            //   if() {//
            //       for (int i = 0; i < ny; i++) {
            //           for (int j = 0; j < nx; j++) {
            //               Color avgColor = adaptiveSupersamplingAcceleration(nx,ny,i,j,2);
            //               imageWriter.writePixel(j, i, avgColor);
            //           }

            //   }
            //}
        }
    }
    /**
    public Color adaptiveSupersamplingAcceleration(int nx, int ny, int i, int j, double div) {
        List<Ray> rays = new LinkedList<>();
        List<Color>colors= new LinkedList<>();
        Point3D pij=camera.findPixel(nx, ny,i,j);
        return adaptiveSupersamplingAcceleration(nx,ny,i,j,7,div,pij,rays,colors);
    }


     * ADD RAYS AND COLORS TO LIST
     * @param nx
     * @param ny
     * @param i
     * @param j
     * @param rec_level
     * @param div
     * @param center
     * @param rays
     * @param pixelColors
     * @return

        private Color adaptiveSupersamplingAcceleration(int nx, int ny, int i, int j, int rec_level, double div, Point3D center,
                                                        List<Ray>rays,List<Color>pixelColors){
            List<Ray> beam = camera.createAdaptiveBeamOfRays(nx,ny,i,j,div,center);
        //get list of rays-center and 4 corners

      //center color

      Ray pixCenter=rays.get(0);
      Color centerColor=findRayInList(pixCenter,rays,pixelColors);
      if(centerColor==null) {
          centerColor = rayTracer.traceRay(pixCenter);
          rays.add(pixCenter);
          pixelColors.add(centerColor);
      }
      //corener colors
      Ray corner1=rays.get(1);
      Color corner1color=rayTracer.traceRay(corner1);
      beam.add(corner1);
      pixelColors.add(corner1color);

      Ray corner2=rays.get(2);
      Color corner2color=rayTracer.traceRay(corner2);
      beam.add(corner2);
      pixelColors.add(corner2color);

      Ray corner3=rays.get(3);
      Color corner3color=rayTracer.traceRay(corner3);
      beam.add(corner3);
      pixelColors.add(corner3color);

      Ray corner4=rays.get(4);
      Color corner4color=rayTracer.traceRay(corner4);
      beam.add(corner1);
      pixelColors.add(corner1color);


    if(rec_level==0) {//stoping condition
        centerColor=centerColor.add(corner1color,corner2color,corner3color, corner4color);
        return centerColor.reduce(5);//calculate average of all colors
      }

          Color cornersAvg=Color.BLACK.add(corner1color,corner2color,corner3color, corner4color).reduce(4);
          //average color of corners is the same as center color
          if(cornersAvg.equals(centerColor)){
              return centerColor;
          }

          else {//compares each color with center color, if not equal will call recursive function for sub pixel
              double centerX=pixCenter.getP0().getX();
              double centerY=pixCenter.getP0().getY();
              double centerZ=pixCenter.getP0().getZ();
              Point3D rayHead1=corner1.getP0();
              Point3D rayHead2=corner2.getP0();
              Point3D rayHead3=corner3.getP0();
              Point3D rayHead4=corner4.getP0();


              if(!corner1color.equals(centerColor)){
                  Point3D p1=new Point3D((centerX+rayHead1.getX())/2d, (centerY+rayHead1.getY())/2, (centerZ+rayHead1.getZ())/2);
                  corner1color=adaptiveSupersamplingAcceleration(nx,ny,i,j,rec_level-1,div*2,p1,rays,pixelColors);
              }
              if(!corner2color.equals(centerColor)){
                  Point3D p2=new Point3D((centerX+rayHead2.getX())/2d, (centerY+rayHead2.getY())/2, (centerZ+rayHead2.getZ())/2);
                  corner2color=adaptiveSupersamplingAcceleration(nx,ny,i,j,rec_level-1,div*2,p2,rays,pixelColors);
              }
              if(!corner3color.equals(centerColor)){
                  Point3D p3=new Point3D((centerX+rayHead3.getX())/2d, (centerY+rayHead3.getY())/2, (centerZ+rayHead3.getZ())/2);
                  corner3color=adaptiveSupersamplingAcceleration(nx,ny,i,j,rec_level-1,div*2,p3,rays,pixelColors);
              }
              if(!corner4color.equals(centerColor)){
                  Point3D p4=new Point3D((centerX+rayHead4.getX())/2d, (centerY+rayHead4.getY())/2, (centerZ+rayHead4.getZ())/2);
                  corner4color=adaptiveSupersamplingAcceleration(nx,ny,i,j,rec_level-1,div*2,p4,rays,pixelColors);
              }
          }
            centerColor=centerColor.add(corner1color,corner2color,corner3color, corner4color);
            return centerColor.reduce(5);//calculate average of all colors

        }



        private Color findRayInList(Ray ray,List<Ray>rays,List<Color>colors){

            int index=rays.indexOf(ray);
            if(index==-1){//ray isn't in list
                return null;
            }
            return colors.get(index);

        }
*/
    /**
     * calculates the average color of all the intersection points of the rays in the beam
     * @param beam list of rays
     * @return the color of beam
     */
    private Color calcAverageColor(List<Ray> beam){
        Color avgColor = new Color(0,0,0);
        for(Ray r:beam){
            avgColor = avgColor.add(rayTracer.traceRay(r));
        }
        avgColor = avgColor.reduce(beam.size());
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

