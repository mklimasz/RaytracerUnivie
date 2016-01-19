package model;

import java.awt.image.BufferedImage;

public class Material {
    private Color color;
    private Vector3D phongConstants;
    private BufferedImage texture;
    private int exponent;
    private double reflectance;
    private double transmittance;
    private double refraction;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector3D getPhongConstants() {
        return phongConstants;
    }

    public void setPhongConstants(Vector3D phongConstants) {
        this.phongConstants = phongConstants;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public int getExponent() {
        return exponent;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    public double getReflectance() {
        return reflectance;
    }

    public void setReflectance(double reflectance) {
        this.reflectance = reflectance;
    }

    public double getTransmittance() {
        return transmittance;
    }

    public void setTransmittance(double transmittance) {
        this.transmittance = transmittance;
    }

    public double getRefraction() {
        return refraction;
    }

    public void setRefraction(double refraction) {
        this.refraction = refraction;
    }

    @Override
    public String toString() {
        return "Material:\n Color: " + color.toString() +
                "\n PhongConsts: " + phongConstants.toString() +
                "\n Exponent: " + exponent +
                "\n Reflectance: " + reflectance +
                "\n Transmittance: " + transmittance +
                "\n Refraction: " + refraction;
    }
}
