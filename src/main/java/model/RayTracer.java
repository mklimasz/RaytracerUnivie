package model;

import model.light.Light;
import model.shape.Shape;
import org.xml.sax.SAXException;
import util.XMLInputFileParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RayTracer {
    private String path;
    private List<Shape> shapes;
    private List<Light> lights;
    private Camera camera;
    private Color ambientLight;
    private Color backgroundColor;
    private Vector3D camDir;
    private Vector3D camRight;
    private Vector3D camDown;
    double tg;

    public RayTracer(String path, List<Shape> shapes, List<Light> lights, Camera camera, Color ambientLight, Color backgroundColor) {
        this.path = path;
        this.shapes = shapes;
        this.lights = lights;
        this.camera = camera;
        this.ambientLight = ambientLight;
        this.backgroundColor = backgroundColor;
    }

    public void startRayTracing() throws IOException {
        tg = Math.tan(Math.toRadians(camera.getAngle()));
        camDir = camera.getLookAt().sub(camera.getPosition()).normalize();
        camRight = camera.getUp().cross(camDir).normalize();
        camDown = camRight.cross(camDir);
        producePPMImage();
    }

    private void producePPMImage() throws IOException {
        String path = this.path.replace("xml", "ppm");
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        String format = "P3";
        out.write(format, 0, format.length());
        out.newLine();
        format = camera.getWidth() + " " + camera.getHeight() + " 255";
        out.write(format, 0, format.length());
        out.newLine();
        for (int i = 0; i < camera.getHeight(); i++) {
            for (int j = 0; j < camera.getWidth(); j++) {
                Vector3D rayDirection = camera.getPosition()
                        .add(camDir.mul(camera.getWidth() / 2 * tg))
                        .add(camDown.mul(i /*+ 0.5*/ - (camera.getWidth() / 2)))
                        .add(camRight.mul((camera.getHeight() / 2) - j + 0.5)).normalize();
                Color color = trace(new Ray(camera.getPosition(), rayDirection));
                String pixelColors = (int) (color.getRed() * 255) + " "
                        + (int) (color.getGreen() * 255) + " "
                        + (int) (color.getBlue() * 255) + " ";
                out.write(pixelColors, 0, pixelColors.length());
            }
            out.newLine();
        }
        out.close();
    }

    private Color trace(Ray ray) {
        Optional<Double> distance = Optional.of(Double.MAX_VALUE);
        Shape intersected = null;
        for(Shape shape : shapes) {
            Optional<Double> currentDistance = shape.intersectionDistance(ray);
            if(currentDistance.isPresent() && currentDistance.get() < distance.get()) {
                distance = currentDistance;
                intersected = shape;
            }
        }
        if(intersected != null) {
            Vector3D intersectionPoint = ray.getOrigin().add(ray.getDirection().mul(distance.get()));
            Vector3D normal = intersected.getNormal(intersectionPoint);
            Color color = new Color(0,0,0);
            for(Light light : lights) {
                color = color.add(shadow(ray, intersected, intersectionPoint, normal, light));
            }
            return color;
        }
        else
            return backgroundColor;
    }

    private Color shadow(Ray ray, Shape intersected, Vector3D intersectionPoint, Vector3D normal, Light light) {
        Vector3D toLight = light.getDirection(intersectionPoint.getValues()[0], intersectionPoint.getValues()[1], intersectionPoint.getValues()[2])
                .neg().normalize();
        double testDiffuse = normal.dot(toLight);
        testDiffuse = testDiffuse >= 0 ? testDiffuse : 0;
        double testSpecular = normal.mul(normal.dot(toLight)*2)
                .sub(toLight)
                .dot(ray.getDirection().neg());
        testSpecular = testSpecular >= 0 ? testSpecular : 0;
        Vector3D ambientComponent = light.getColor().asVector()
                .mul(intersected.getMaterial().getColor().asVector())
                .mul(intersected.getMaterial().getPhongConstants().getValues()[0]
                        *intersected.getMaterial().getPhongConstants().getValues()[1]);
        Vector3D specularComponent = light.getColor().asVector()
                .mul(intersected.getMaterial().getPhongConstants().getValues()[2])
                .mul(Math.pow(testSpecular, intersected.getMaterial().getExponent()));
        Vector3D diffuseComponent = intersected.getMaterial().getColor().asVector()
                .mul(light.getColor().asVector())
                .mul(testDiffuse)
                .mul(intersected.getMaterial().getPhongConstants().getValues()[1]);
        return Color.parse(ambientComponent.add(specularComponent).add(diffuseComponent));
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        RayTracer rayTracer = XMLInputFileParser.parse(args[0]);
        System.out.println(rayTracer);
        rayTracer.startRayTracing();
    }

    @Override
    public String toString() {
        return path + "\n" +
                Arrays.toString(shapes.toArray()) + "\n" +
                lights.toString() + "\n" +
                ambientLight.toString() + "\n" +
                camera.toString() + "\n" +
                backgroundColor.toString();
    }
}
