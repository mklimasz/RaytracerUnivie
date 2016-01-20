package model;

import java.util.Arrays;

public class Vector3D {
    private double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Vector3D v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public double dot(Vector3D v) {
        return x*v.x + y*v.y + z*v.z;
    }

    public double dot(double x, double y, double z) {
        return this.x *x + this.y *y + this.z *z;
    }

    public  Vector3D cross(Vector3D v) {
        return new Vector3D(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
    }

    public  Vector3D cross(double x, double y, double z) {
        return new Vector3D(this.y*z - this.z*y, this.z*x - this.x*z, this.x*y - this.y*x);
    }


    public double length() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3D normalize() {
        double t = x*x + y*y + z*z;
        if (t != 0 && t != 1) t = (1 / Math.sqrt(t));
        return new Vector3D(x*t ,y*t, z*t);
    }

    public Vector3D sub(Vector3D v) {
        return new Vector3D(this.x-v.x, this.y-v.y, this.z-v.z);
    }

    public Vector3D add(Vector3D v) {
        return new Vector3D(this.x+v.x, this.y+v.y, this.z+v.z);
    }

    public Vector3D mul(double scalar) {
        return new Vector3D(this.x*scalar, this.y*scalar, this.z*scalar);
    }

    public Vector3D mul(Vector3D v) {
        return new Vector3D(x*v.x, y*v.y, z*v.z);
    }

    public Vector3D neg() {
        return new Vector3D(-x, -y, -z);
    }

    public double[] getValues() {
        return new double[] {x, y, z};
    }

    @Override
    public String toString() {
        return Arrays.asList(x, y, z).toString();
    }
}
