package model.shape.impl;

import model.Color;
import model.Material;
import model.Ray;
import model.Vector3D;
import model.shape.Shape;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Triangle implements Shape {
    private final static double EPSILON = 0.000001;
    private List<Vector3D> vertices;
    private Vector3D normal;
    private Material material;

    public Triangle() {}

    public void setNormal(Vector3D normal) {
        this.normal = normal;
    }

    public void setVertices(List<Vector3D> vertices) {
        this.vertices = vertices;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Optional<Double> intersectionDistance(Ray ray) {
        Vector3D e1, e2;  //Edge1, Edge2
        Vector3D P, Q, T;
        double det, inv_det, u, v;
        double t;
        e1 = vertices.get(1).sub(vertices.get(0));
        e2 = vertices.get(2).sub(vertices.get(0));
        P = ray.getDirection().cross(e2);
        det = e1.dot(P);
        if(det > -EPSILON && det < EPSILON)
            return Optional.empty();
        inv_det = 1 / det;
        T = ray.getOrigin().sub(vertices.get(0));
        u = T.dot(P) * inv_det;
        if(u < 0 || u > 1)
            return Optional.empty();
        Q = T.cross(e1);
        v = ray.getDirection().dot(Q) * inv_det;
        if(v < 0 || u + v  > 1)
            return Optional.empty();
        t = e2.dot(Q) * inv_det;
        if(t > EPSILON)
            return Optional.of(t);
        return Optional.empty();
    }

    @Override
    public Vector3D getNormal(Vector3D intersectionPoint) {
        return normal;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public Color getColor(Vector3D intersectionPoint) {
        if(material.getColor() == null) {
            double edge1 = vertices.get(1).sub(vertices.get(0)).length();
            int x = (int) (intersectionPoint.getValues()[0]/edge1*material.getTexture().getWidth());
            double edge2 = vertices.get(2).sub(vertices.get(1)).length();
            int y = (int) (intersectionPoint.getValues()[1]/edge2*material.getTexture().getHeight());
            x = x < 0 ? -x : x;
            y = y < 0 ? -y : y;
            return material.getTextureColor(x, y);
        }
        else
            return material.getColor();
    }

    @Override
    public String toString() {
        return "\nTriangle\n [vertices: " + Arrays.toString(vertices.toArray())
                + ",\n normal: " + normal
                + ",\n material: " + material
                + "]";
    }
}
