package model.light.impl;

import model.Color;
import model.Vector3D;
import model.light.Light;

public class ParallelLight implements Light {
    private Color color;
    private Vector3D direction;

    public ParallelLight(Color color, Vector3D direction) {
        this.color = color;
        this.direction = direction;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Vector3D getDirection(double x, double y, double z) {
        return direction;
    }
}
