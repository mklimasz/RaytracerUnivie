package util;

import model.*;
import model.light.Light;
import model.light.impl.ParallelLight;
import model.light.impl.PointLight;
import model.shape.Shape;
import model.shape.impl.Sphere;
import model.shape.impl.Triangle;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLInputFileParser {

    private XMLInputFileParser() {
        throw new AssertionError();
    }

    private static String filePath;

    public static RayTracer parse(String path) throws ParserConfigurationException, IOException, SAXException {
        filePath = path;
        File inputFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        List<Shape> shapes = getShapes(doc);
        Color color = getColor(doc);
        List<Light> lights = getLights(doc);
        Camera camera = getCamera(doc);
        Color ambientLight = getAmbientLight(doc);
        return new RayTracer(path, shapes, lights, camera, ambientLight, color);
    }

    private static Color getAmbientLight(Document doc) {
        NodeList list = doc.getElementsByTagName("ambient_light");
        list = list.item(0).getChildNodes();
        double r = Double.parseDouble(list.item(1).getAttributes().getNamedItem("r").getTextContent());
        double g = Double.parseDouble(list.item(1).getAttributes().getNamedItem("g").getTextContent());
        double b = Double.parseDouble(list.item(1).getAttributes().getNamedItem("b").getTextContent());
        return new Color(r,g,b);
    }

    private static Camera getCamera(Document doc) {
        NodeList list = doc.getElementsByTagName("camera");
        list = list.item(0).getChildNodes();
        Camera camera = new Camera();
        setCameraPositionVector(list, camera);
        setCameraLookUpVector(list, camera);
        setCameraUpVector(list, camera);
        setCameraAngle(list, camera);
        setCameraResolution(list, camera);
        setCameraMaxBounces(list, camera);
        return camera;
    }

    private static void setCameraPositionVector(NodeList list, Camera camera) {
        double x = Double.parseDouble(list.item(1).getAttributes().getNamedItem("x").getTextContent());
        double y = Double.parseDouble(list.item(1).getAttributes().getNamedItem("y").getTextContent());
        double z = Double.parseDouble(list.item(1).getAttributes().getNamedItem("z").getTextContent());
        camera.setPosition(new Vector3D(x,y,z));
    }

    private static void setCameraLookUpVector(NodeList list, Camera camera) {
        double x = Double.parseDouble(list.item(3).getAttributes().getNamedItem("x").getTextContent());
        double y = Double.parseDouble(list.item(3).getAttributes().getNamedItem("y").getTextContent());
        double z = Double.parseDouble(list.item(3).getAttributes().getNamedItem("z").getTextContent());
        camera.setLookAt(new Vector3D(x,y,z));
    }

    private static void setCameraUpVector(NodeList list, Camera camera) {
        double x = Double.parseDouble(list.item(5).getAttributes().getNamedItem("x").getTextContent());
        double y = Double.parseDouble(list.item(5).getAttributes().getNamedItem("y").getTextContent());
        double z = Double.parseDouble(list.item(5).getAttributes().getNamedItem("z").getTextContent());
        camera.setUp(new Vector3D(x,y,z));
    }

    private static void setCameraAngle(NodeList list, Camera camera) {
        int angle = Integer.parseInt(list.item(7).getAttributes().getNamedItem("angle").getTextContent());
        camera.setAngle(angle);
    }

    private static void setCameraResolution(NodeList list, Camera camera) {
        int width = Integer.parseInt(list.item(9).getAttributes().getNamedItem("horizontal").getTextContent());
        int height = Integer.parseInt(list.item(9).getAttributes().getNamedItem("vertical").getTextContent());
        camera.setWidth(width);
        camera.setHeight(height);
    }

    private static void setCameraMaxBounces(NodeList list, Camera camera) {
        int max = Integer.parseInt(list.item(11).getAttributes().getNamedItem("n").getTextContent());
        camera.setMaxBounces(max);
    }

    private static List<Light> getLights(Document doc) {
        List<Light> lights = new ArrayList<>();
        NodeList list = doc.getElementsByTagName("parallel_light");
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            lights.add(getParallelLight(element));
        }
        list = doc.getElementsByTagName("point_light");
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            lights.add(getPointLight(element));
        }
        return lights;
    }

    private static Light getPointLight(Element element) {
        NodeList list = element.getChildNodes();
        double r = Double.parseDouble(list.item(1).getAttributes().getNamedItem("r").getTextContent());
        double g = Double.parseDouble(list.item(1).getAttributes().getNamedItem("g").getTextContent());
        double b = Double.parseDouble(list.item(1).getAttributes().getNamedItem("b").getTextContent());
        double x = Double.parseDouble(list.item(3).getAttributes().getNamedItem("x").getTextContent());
        double y = Double.parseDouble(list.item(3).getAttributes().getNamedItem("y").getTextContent());
        double z = Double.parseDouble(list.item(3).getAttributes().getNamedItem("z").getTextContent());
        return new PointLight(new Color(r,g,b), new Vector3D(x,y,z));
    }

    private static Light getParallelLight(Element element) {
        NodeList list = element.getChildNodes();
        double r = Double.parseDouble(list.item(1).getAttributes().getNamedItem("r").getTextContent());
        double g = Double.parseDouble(list.item(1).getAttributes().getNamedItem("g").getTextContent());
        double b = Double.parseDouble(list.item(1).getAttributes().getNamedItem("b").getTextContent());
        double x = Double.parseDouble(list.item(3).getAttributes().getNamedItem("x").getTextContent());
        double y = Double.parseDouble(list.item(3).getAttributes().getNamedItem("y").getTextContent());
        double z = Double.parseDouble(list.item(3).getAttributes().getNamedItem("z").getTextContent());
        return new ParallelLight(new Color(r,g,b), new Vector3D(x,y,z));
    }


    private static Color getColor(Document doc) {
        NodeList list = doc.getElementsByTagName("background_color");
        NamedNodeMap map = list.item(0).getAttributes();
        double red = Double.parseDouble(map.getNamedItem("r").getTextContent());
        double green = Double.parseDouble(map.getNamedItem("g").getTextContent());
        double blue = Double.parseDouble(map.getNamedItem("b").getTextContent());
        return new Color(red, green, blue);
    }

    private static List<Shape> getShapes(Document doc) throws IOException {
        NodeList list = doc.getElementsByTagName("sphere");
        List<Shape> shapes = new ArrayList<>();
        for (int i = 0; i < list.getLength() ; i++) {
            Element element = (Element) list.item(i);
            shapes.add(getSphere(element));
        }
        list = doc.getElementsByTagName("mesh");
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            shapes.addAll(getTriangle(element));
        }
        return shapes;
    }

    private static List<Triangle> getTriangle(Element element) throws IOException {
        String fileName = element.getAttribute("name");
        List<Triangle> triangles = OBJInputFileParser.parseOBJ(new File(filePath).getParentFile() + "/" + fileName);
        Material material = getMaterial(element);
        triangles.forEach(t -> t.setMaterial(material));
        return triangles;
    }

    private static Shape getSphere(Element element) throws IOException {
        double radius;
        radius = Double.parseDouble(element.getAttribute("radius"));
        double x = Double.parseDouble(element.getElementsByTagName("position").item(0).getAttributes().getNamedItem("x").getTextContent());
        double y = Double.parseDouble(element.getElementsByTagName("position").item(0).getAttributes().getNamedItem("y").getTextContent());
        double z = Double.parseDouble(element.getElementsByTagName("position").item(0).getAttributes().getNamedItem("z").getTextContent());
        Vector3D position = new Vector3D(x, y, z);
        Material material = getMaterial(element);
        return new Sphere(radius, position, material);
    }

    private static Material getMaterial(Element element) throws IOException {
        Material material = new Material();
        Node node =  element.getElementsByTagName("material_solid").item(0);
        NodeList list;
        if(node == null)
            list = element.getElementsByTagName("material_textured").item(0).getChildNodes();
        else
            list = node.getChildNodes();
        if(list.item(1).getAttributes().getNamedItem("r") != null) {
            double r = Double.parseDouble(list.item(1).getAttributes().getNamedItem("r").getTextContent());
            double g = Double.parseDouble(list.item(1).getAttributes().getNamedItem("g").getTextContent());
            double b = Double.parseDouble(list.item(1).getAttributes().getNamedItem("b").getTextContent());
            material.setColor(new Color(r, g, b));
        }
        else {
            String path = list.item(1).getAttributes().getNamedItem("name").getTextContent();
            material.setTexture(ImageIO.read(new File(new File(filePath).getParent() + "/" + path)));
        }
        double ka = Double.parseDouble(list.item(3).getAttributes().getNamedItem("ka").getTextContent());
        double kd = Double.parseDouble(list.item(3).getAttributes().getNamedItem("kd").getTextContent());
        double ks = Double.parseDouble(list.item(3).getAttributes().getNamedItem("ks").getTextContent());
        material.setPhongConstants(new Vector3D(ka, kd, ks));
        int exponent = Integer.parseInt(list.item(3).getAttributes().getNamedItem("exponent").getTextContent());
        material.setExponent(exponent);
        double reflectance = Double.parseDouble(list.item(5).getAttributes().getNamedItem("r").getTextContent());
        material.setReflectance(reflectance);
        double transmittance = Double.parseDouble(list.item(7).getAttributes().getNamedItem("t").getTextContent());
        material.setTransmittance(transmittance);
        double refraction = Double.parseDouble(list.item(9).getAttributes().getNamedItem("iof").getTextContent());
        material.setRefraction(refraction);
        return material;
    }
}