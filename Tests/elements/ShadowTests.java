package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests {
    private Scene scene = new Scene("Test scene");
    private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200).setDistance(1000);

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleInitial() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleClose() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-60,-30,0), new Point3D(-30, -60, 0), new Point3D(-58, -58, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("sphereTriangleClose", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleClosest() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-50,-20,0), new Point3D(-20, -50, 0), new Point3D(-48, -48, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("sphereTriangleClosest", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleCloserLight() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-70,-40,0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-90, -90, 130), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("sphereTriangleCloserLight", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleClosestLight() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-70,-40,0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-75, -75, 65), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("sphereTriangleClosestLight", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                new Sphere(30, new Point3D(0, 0, -115)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void dofTest3() {
         Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(247, 247, 235), 0.09));

        Point3D i=new Point3D(-169,-4,-122);
        Point3D j=new Point3D(-131,-5,-74);
        Point3D k=new Point3D(-70,-5,-83);
        Point3D l=new Point3D(-48,-4,-140);
        Point3D m=new Point3D(-86,-4,-188);
        Point3D n=new Point3D(-147,-4,-179);
        Point3D u=new Point3D(-169,16,-122);
        Point3D v=new Point3D(-131,15,-74);
        Point3D w=new Point3D(-74,15,-83);
        Point3D z=new Point3D(-48,16,-140);
        Point3D a1=new Point3D(-86,16,-188);
        Point3D b1=new Point3D(-147,16,-179);
        Material topMaterial=new Material().setKd(0.5).setKs(0.5).setShininess(30);
        Color topColor=new Color(252, 227, 212);
        Material sideMaterial=new Material().setKd(0.5).setKs(0.5).setShininess(60);
        Color sideColor=new Color(237, 244, 245);
        scene.geometries.add( //
                /**new Plane(new Point3D(-200, 200, -1000), new Point3D(-200, -200, -1000), new Point3D(200, -200, -1000))//,new Point3D(200, 200, -1000)) //
                        .setEmission(new Color(55, 188, 222))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) , //
                new Sphere(2, new Point3D(-160, -55, -1000)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
              new Polygon(u,v,w,z,a1,b1)
                      .setEmission(topColor)
                       .setMaterial(topMaterial),
            */  new Polygon(n,i,u,b1)
                        .setEmission(sideColor)
                        .setMaterial(sideMaterial) ,
               new Polygon(m,n,b1,a1)
                          .setEmission(sideColor)
                          .setMaterial(sideMaterial) ,
                new Polygon(l,m,a1,z)
                          .setEmission(sideColor)
                          .setMaterial(sideMaterial) ,
            //    new Polygon(k,l,z,w)
              //          .setEmission(sideColor)
                //        .setMaterial(sideMaterial)  ,
              //  new Polygon(j,k,l,m,n,i)//(j,v,w,k)
                  //   .setEmission(sideColor)
                //     .setMaterial(sideMaterial),
                new Polygon(i,j,v,u)
                        .setEmission(sideColor)
                       .setMaterial(sideMaterial),
                new Plane(i,j,l)
                        .setEmission(new Color(55, 188, 222))
                       .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(90))
        );
        scene.lights.add( new SpotLight(new Color(245, 245, 255), new Point3D(300,100,-500), new Vector(-423, -104, -114).normalized()) //
                .setKl(0.0001).setKq(0.000005));
        scene.lights.add(new DirectionalLight(new Color(250, 500, 0), new Vector(-20,-263,340).normalized()));
        scene.lights.add(new PointLight(new Color(250, 400, 0), new Point3D(150, 150, 50))
                        .setKl(0.0000003).setKq(0.00000003));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("DOFtest6", 600, 600)) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

}
