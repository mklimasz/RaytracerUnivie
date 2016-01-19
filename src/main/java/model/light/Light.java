package model.light;

import model.Color;
import model.Vector3D;

public interface Light {
    Color getColor();
    Vector3D getDirection(double x, double y, double z);
}
