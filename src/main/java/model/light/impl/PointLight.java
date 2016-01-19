package model.light.impl;

import model.Color;
import model.Vector3D;
import model.light.Light;

public class PointLight implements Light {
    private Color color;
    private Vector3D position;

    public PointLight(Color color, Vector3D position) {
        this.color = color;
        this.position = position;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Vector3D getDirection(double x, double y, double z) {
        return new Vector3D(x, y, z).sub(position);
    }
}
