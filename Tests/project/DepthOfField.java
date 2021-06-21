package project;

import elements.*;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;


public class DepthOfField {

    private Scene scene = new Scene("Test scene");

    @Test
    public void dof() {
        Camera cameraTo = new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000).set_aperture(4d).set_amountOfRays(300).set_dof(false).set_focalDistance(50);

        scene.geometries.add(
                new Plane(new Point3D(8, -40, 3), new Point3D(3, -40, 2), new Point3D(5, -40, 5))
                        .setEmission(new Color(3, 85, 87))
                        .setMaterial(new Material().setKd(1).setShininess(19)),
                new Plane(new Point3D(200, 0, 0), new Point3D(200, 1, 5), new Point3D(200, 2, 3))
                        .setEmission(new Color(3, 85, 87))                        .setMaterial(new Material().setKd(1).setShininess(19)),
                new Sphere(30, new Point3D(165, -10, 0))
                        .setEmission(new Color(3, 85, 87))
                        .setMaterial(new Material().setKd(0.9).setKt(0.1).setKs(0.2).setShininess(19)),
                new Sphere(20, new Point3D(110, -20, 45))
                        .setEmission(new Color(80, 8, 87))
                        .setMaterial(new Material().setKd(0.9).setKt(0.1).setShininess(19)),
                new Sphere(20, new Point3D(60, -20, -5))
                        .setEmission(new Color(30, 8, 87))
                        .setMaterial(new Material().setKd(0.9).setKt(0.1).setShininess(19)),
                new Sphere(11, new Point3D(-10, -29, 40))
                        .setEmission(new Color(80, 79, 8))
                        .setMaterial(new Material().setKd(0.9).setKt(0.1).setShininess(19)),
                new Sphere(15, new Point3D(20, -25, 30))
                        .setEmission(new Color(3, 85, 40))
                        .setMaterial(new Material().setKd(0.9).setKt(0.1).setShininess(19)),
                new Sphere(17, new Point3D(110, -23, -45))
                        .setEmission(new Color(80, 8, 20))
                        .setMaterial(new Material().setKd(0.9).setKt(0.1).setShininess(19)),
                new Sphere(12, new Point3D(-10, -28, -70))
                        .setEmission(new Color(90, 60, 10))
                        .setMaterial(new Material().setKd(0.9).setKt(0.1).setShininess(19)),
                new Sphere(9, new Point3D(-35, -31, -25))
                        .setEmission(new Color(80, 30, 18))
                        .setMaterial(new Material().setKd(0.9).setKt(0.1).setShininess(19))
        );
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.15));

        scene.lights.add(new DirectionalLight(new Color(185, 200, 0), new Vector(-100, -50, 90)));
        scene.lights.add(new SpotLight(new Color(229, 180, 225), new Point3D(100, 30, -90), new Vector(65, -50, 90)));
        scene.lights.add(new PointLight(new Color(185, 200, 0), new Point3D(-400, 600, 200)));
        ImageWriter imageWriter = new ImageWriter("no_dof", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(cameraTo) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();

    }

}