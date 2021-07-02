package project;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

import javax.swing.plaf.synth.Region;
import java.util.LinkedList;
import java.util.List;


public class DepthOfField {

    private Scene scene = new Scene("Test scene");

    //DELETE POLYGON GETVERTECES!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Test
    public void dof() {
        Camera cameraTo = new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000).set_aperture(4d).set_amountOfRays(100).set_dof(true)
                .set_focalDistance(new Point3D(-1000, 0, 0).distance(new Point3D(-500,-31,0)));

        scene.geometries.add(
                new Plane(new Point3D(8, -40, 3), new Point3D(3, -40, 2), new Point3D(5, -40, 5))
                        .setEmission(new Color(3, 85, 87))
                        .setMaterial(new Material().setKd(1).setShininess(19)),
                new Plane(new Point3D(200, 0, 0), new Point3D(200, 1, 5), new Point3D(200, 2, 3))
                        .setEmission(new Color(3, 85, 87)).setMaterial(new Material().setKd(1).setShininess(19)),
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
                        .setMaterial(new Material().setKd(0.9).setKt(0.1).setShininess(19)),
                new Sphere(9, new Point3D(-491, -31, 5))
                        .setEmission(new Color(80, 30, 18))
                        .setMaterial(new Material().setKd(0.9).setKt(0.1).setShininess(19))
        );
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.15));

        scene.lights.add(new DirectionalLight(new Color(185, 200, 0), new Vector(-100, -50, 90)));
        scene.lights.add(new SpotLight(new Color(229, 180, 225), new Point3D(100, 30, -90), new Vector(65, -50, 90)));
        scene.lights.add(new PointLight(new Color(185, 200, 0), new Point3D(-400, 600, 200)));
        ImageWriter imageWriter = new ImageWriter("dof", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(cameraTo) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    private void addTree(Point3D center, double height, double width,Color treeShade) {
        double stemHeight=height/10d;
        Point3D stemBack=new Point3D(center.getX()+width/12d,center.getY(),center.getZ());
        Point3D stemRight=new Point3D(center.getX(),center.getY(),center.getZ()+width/12d);
        Point3D stemFront=new Point3D(center.getX()-width/12d,center.getY(),center.getZ());
        Point3D stemLeft=new Point3D(center.getX(),center.getY(),center.getZ()-width/12d);

        //build stem
        buildPrism( new Polygon(stemBack,stemRight,stemFront,stemLeft),height/2d,new Color(143,72,31),new Material().setKd(1).setShininess(19),new Color(143,72,31),new Material().setKd(1).setShininess(19));

        Point3D baseBack=new Point3D(center.getX()+width/2d,center.getY()+stemHeight,center.getZ());
        Point3D baseRight=new Point3D(center.getX(),center.getY()+stemHeight,center.getZ()+width/2d);
        Point3D baseFront=new Point3D(center.getX()-width/2d,center.getY()+stemHeight,center.getZ());
        Point3D baseLeft=new Point3D(center.getX(),center.getY()+stemHeight,center.getZ()-width/2d);
        Point3D treeTop=new Point3D(center.getX(),center.getY()+height,center.getZ());
        scene.geometries.add(new Triangle(treeTop,baseBack,baseRight).setEmission(treeShade).setMaterial(new Material().setKd(1).setShininess(19)));
        scene.geometries.add(new Triangle(treeTop,baseRight,baseFront).setEmission(treeShade).setMaterial(new Material().setKd(1).setShininess(19)));
        scene.geometries.add(new Triangle(treeTop,baseFront,baseLeft).setEmission(treeShade).setMaterial(new Material().setKd(1).setShininess(19)));
        scene.geometries.add(new Triangle(treeTop,baseLeft,baseBack).setEmission(treeShade).setMaterial(new Material().setKd(1).setShininess(19)));

    }


    //DELETE POLYGON GETVERTECES!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void buildPrism(Polygon base, double height,Color topC,Material topm, Color sidesC, Material sidesM) {

        List<Point3D> topPoly = new LinkedList<>();//list of vertecies the top polygon
        scene.geometries.add(base);//collection of all geometries that create this prism
        Point3D topPoint1;
        Point3D topPoint2;
        Point3D temp;
        List<Point3D> vertices = base.get_vertices();
        for (int i = 0; i < vertices.size() - 1; i++) {
            temp = vertices.get(i);
            topPoint1 = new Point3D(temp.getX(), temp.getY() + height, temp.getZ());
            temp = vertices.get(i + 1);
            topPoint2 = new Point3D(temp.getX(), temp.getY() + height, temp.getZ());
            topPoly.add(topPoint1);
            // topPoly.add(topPoint2);
            scene.geometries.add(new Polygon(topPoint1, vertices.get(i), vertices.get(i + 1), topPoint2).setEmission(sidesC).setMaterial(sidesM));
        }
        temp = vertices.get(0);
        topPoint1 = new Point3D(temp.getX(), temp.getY() + height, temp.getZ());
        temp = vertices.get(vertices.size() - 1);
        topPoint2 = new Point3D(temp.getX(), temp.getY() + height, temp.getZ());
        topPoly.add(topPoint2);
        // topPoly.add(topPoint2);
        scene.geometries.add(new Polygon(topPoint1, vertices.get(0), temp, topPoint2).setEmission(sidesC).setMaterial(sidesM));
        if (topPoly.size() == 3) {
            scene.geometries.add(new Polygon(topPoly.get(0), topPoly.get(1), topPoly.get(2)).setEmission(sidesC).setMaterial(sidesM));
        }
        if (topPoly.size() == 4) {
            scene.geometries.add(new Polygon(topPoly.get(0), topPoly.get(1), topPoly.get(2), topPoly.get(3)).setEmission(topC).setMaterial(topm));
        }

        if (topPoly.size() == 5) {
            scene.geometries.add(new Polygon(topPoly.get(0), topPoly.get(1), topPoly.get(2), topPoly.get(4), topPoly.get(5)).setEmission(topC).setMaterial(topm));
        }
            if (topPoly.size() == 6) {
                scene.geometries.add(new Polygon(topPoly.get(0), topPoly.get(1), topPoly.get(2), topPoly.get(3), topPoly.get(4), topPoly.get(5)).setEmission(topC).setMaterial(topm));
            }
        }

        //  private List<Geometry>buildTree(Point3D treeCenter)

        @Test
        public void adaptiveSuperSample () {
            //<editor-fold desc="  ">
            //</editor-fold>

            //<editor-fold desc=" Base values ">
            double ground=-60;
            double streetSideZ=35;
            double streetSideX=9000;
            String imageName="tree_rowsAnd_st_lines2";
            //</editor-fold>
            //<editor-fold desc="Set camera, ImageWriter, Lights, background">
            Camera cameraTo = new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 1, 0)) //
                    .setViewPlaneSize(200, 200).setDistance(1000).set_aperture(4d).set_amountOfRays(300).set_dof(false).set_focalDistance(50);
            scene.background = new Color(134, 255, 255);
            scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.15));

            scene.lights.add(new DirectionalLight(new Color(230, 100, 0), new Vector(50, -70, -70)));
            //scene.lights.add(new SpotLight(new Color(229, 180, 225), new Point3D(100, 30, -90), new Vector(65, -50, 90)));
            //scene.lights.add(new PointLight(new Color(185, 200, 0), new Point3D(0, 20, -10)));
            ImageWriter imageWriter = new ImageWriter(imageName, 600, 600);
            //</editor-fold>
            //<editor-fold desc="Values for the left of street">

            Point3D leftStBack = new Point3D(streetSideX, ground, -1*streetSideZ);
            Point3D leftSWback = new Point3D(streetSideX, ground, -90000);
            Point3D leftSWfront = new Point3D(-1*streetSideX, ground, -90000);
            Point3D leftStFront = new Point3D(-1*streetSideX, ground, -1*streetSideZ);
            Color sideWalkColor =new Color(126,126,126);
            Material sidewalkMat=new Material().setKd(1).setShininess(19);
            //</editor-fold>
            //<editor-fold desc="Left side of the street">
            buildPrism(new Polygon(leftStBack, leftStFront, leftSWfront, leftSWback),
                     5,sideWalkColor,sidewalkMat ,sideWalkColor.reduce(1.5d), sidewalkMat);
            //</editor-fold>
            //<editor-fold desc="Values for the right of street">

            Point3D rightStBack = new Point3D(streetSideX, ground, streetSideZ);
            Point3D rightStfront = new Point3D(-1*streetSideX, ground, streetSideZ);
            Point3D rightSWfront = new Point3D(-1*streetSideX, ground, 90000);
            Point3D rightSWback = new Point3D(streetSideX, ground, 90000);
            //</editor-fold>
            //<editor-fold desc="Right  side of the street">
            buildPrism(new Polygon(rightStBack, rightStfront, rightSWfront, rightSWback),
                     5,sideWalkColor,sidewalkMat ,sideWalkColor.reduce(1.5d), sidewalkMat);//r
            //</editor-fold>
            //<editor-fold desc="Street">
            Geometry street= new Polygon(leftStBack, rightStBack, rightStfront, leftStFront)
                    .setEmission(new Color(39, 39, 39))
                    .setMaterial(new Material().setKd(1).setShininess(19));
            //</editor-fold>
           // <editor-fold desc="Create tree rows">
            double treeXL=-500;
            double treeXR=-500;
            double treeWidth=15;
            for(int i=0;i<30;i++) {
               addTree(new Point3D(treeXL, ground + 5, -1*streetSideZ - 10),
                       60, treeWidth, new Color(53, 98, 16));
               addTree(new Point3D(treeXR, ground + 5, streetSideZ+10),
                     60, treeWidth, new Color(53, 98, 16));
                treeXL=treeXL+200;
                treeXR=treeXR+200;
            }
            //</editor-fold>

             double frontX=-400;
             double backX=-300;
             double leftZ=-1*streetSideZ+33;
             double rightZ=streetSideZ-33;
           for(int i=0;i<70;i++) {
               Geometry streetLine = new Polygon(new Point3D(backX, ground, leftZ), new Point3D(backX, ground, rightZ),
                                     new Point3D(frontX, ground, rightZ), new Point3D(frontX, ground, leftZ))
                                          .setEmission(new Color(255, 255, 255))
                                            .setMaterial(sidewalkMat);
               scene.geometries.add( streetLine);
               frontX=frontX+150;
               backX=backX+150;
           }

            scene.geometries.add( street);

            //<editor-fold desc="Render and print">
            Render render = new Render() //
                    .setImageWriter(imageWriter) //
                    .setCamera(cameraTo) //
                    .setRayTracer(new BasicRayTracer(scene));

            render.renderImage();
            render.writeToImage();
            //</editor-fold>


        }
    }








//this was the green sides of the road
// new Plane(new Point3D(8, -70, 3), new Point3D(3, -70, 2), new Point3D(5, -70, 5))
//         .setEmission(new Color(39, 173, 11))
//         .setMaterial(new Material().setKd(1).setShininess(19)),
//
//  new Plane(new Point3D(200, 0, 0), new Point3D(200, 1, 5), new Point3D(200, 2, 3))
//          .setEmission(new Color(3, 85, 87))
//          .setMaterial(new Material().setKd(1).setShininess(19)));

// List<Geometry> globeBase1 = buildPrism(new Polygon(new Point3D(20, 0, -100),
//         new Point3D(60, 0, -80), new Point3D(60, 0, -40),
//         new Point3D(20, 0, -20), new Point3D(-20, 0, -40), new Point3D(-20, 0, -80)), 20);
// Color c = (new Color(223, 220, 218));
// Material m = new Material().setKd(1).setShininess(19);
// for (int i = 1; i < 7; i++) {
//     globeBase1.set(i, globeBase1.get(i).setEmission(c).setMaterial(m));
// }
// c = new Color(220, 197, 188);
// globeBase1.set(7, globeBase1.get(7).setEmission(c).setMaterial(m));
// for (Geometry geo : globeBase1) {
//     scene.geometries.add(geo);
//}

