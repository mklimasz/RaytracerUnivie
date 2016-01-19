package model.shape.impl;

import model.Material;
import model.Ray;
import model.Vector3D;
import model.shape.Shape;

import java.util.Optional;

public class Sphere implements Shape {
    private double radius;
    private Vector3D position;
    private Material material;

    public Sphere(double radius, Vector3D position, Material material) {
        this.radius = radius;
        this.position = position;
        this.material = material;
    }

    @Override
    public Optional<Double> intersectionDistance(Ray ray) {
        double A, B, C, dirc, dircSqrt, t0 = Double.MAX_VALUE, t1;
        B = 2*ray.getDirection().dot(ray.getOrigin().sub(position));
        A = ray.getDirection().dot(ray.getDirection());
        C = ray.getOrigin().sub(position).dot(ray.getOrigin().sub(position)) - Math.pow(radius, 2);
        dirc = Math.pow(B, 2) - 4 * A * C;
        if(dirc >= 0) {
            dircSqrt = Math.sqrt(dirc);
            t0 = (-B + dircSqrt) / 2 * A;
            t1 = (-B - dircSqrt) / 2 * A;
            t0 = t0 < t1 ? t0 : t1;
        }
        if(t0 == Double.MAX_VALUE)
            return Optional.empty();
        else
            return Optional.of(t0);
    }

    @Override
    public Vector3D getNormal(Vector3D intersectionPoint) {
        return intersectionPoint.sub(position).normalize();
    }

    @Override
    public Material getMaterial() {
        return material;
    }

}
