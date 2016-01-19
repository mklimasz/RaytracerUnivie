package model.shape.impl;

import model.Material;
import model.Ray;
import model.Vector3D;
import model.shape.Shape;

import java.util.List;
import java.util.Optional;

public class Triangle implements Shape {
    private List<Vector3D> vertices;
    private Vector3D normal;
    private Material material;

    public Triangle(List<Vector3D> vertices, Vector3D normal, Material material) {
        this.vertices = vertices;
        this.normal = normal;
        this.material = material;
    }

    @Override
    public Optional<Double> intersectionDistance(Ray ray) {
        Vector3D A = vertices.get(1).sub(vertices.get(0));
        Vector3D B = vertices.get(2).sub(vertices.get(0));
        Vector3D normal = A.cross(B).normalize();
        double D = normal.dot(vertices.get(0));
        double t = (normal.dot(ray.getOrigin()) + D) / normal.dot(ray.getDirection());
        if(t<0)
            return Optional.empty();
        Vector3D phit = ray.getOrigin().add(ray.getDirection().mul(t));
        Vector3D c;
        Vector3D edge0 = vertices.get(1).sub(vertices.get(0));
        Vector3D vp0 = phit.sub(vertices.get(0));
        c = edge0.cross(vp0);
        if(normal.dot(c) < 0)
            return Optional.empty();
        Vector3D edge1 = vertices.get(2).sub(vertices.get(1));
        Vector3D vp1 = phit.sub(vertices.get(1));
        c = edge1.cross(vp1);
        if(normal.dot(c) < 0)
            return Optional.empty();
        Vector3D edge2 = vertices.get(0).sub(vertices.get(2));
        Vector3D vp2     = phit.sub(vertices.get(2));
        c = edge2.cross(vp2);
        if(normal.dot(c) < 0)
            return Optional.empty();
        else
            return Optional.of(phit.sub(ray.getOrigin()).length());
    }

    @Override
    public Vector3D getNormal(Vector3D intersectionPoint) {
        return null;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

}
