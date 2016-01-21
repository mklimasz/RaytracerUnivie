package model.shape;

import model.Color;
import model.Material;
import model.Ray;
import model.Vector3D;

import java.util.Optional;

public interface Shape {
    Optional<Double> intersectionDistance(Ray ray);
    Vector3D getNormal(Vector3D intersectionPoint);
    Material getMaterial();
    Color getColor(Vector3D intersectionPoint);
}
