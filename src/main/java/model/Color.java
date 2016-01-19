package model;

public class Color {
    private double red;
    private double green;
    private double blue;

    public Color() {}

    public Color(double r, double g, double b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public double getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public Color add(Color c) {
        return new Color(red + c.red, green + c.green, blue + c.blue);
    }

    public Vector3D asVector() {
        return new Vector3D(red, green, blue);
    }

    public static Color parse(Vector3D v) {
        return new Color(v.getValues()[0],
                v.getValues()[1],
                v.getValues()[2]
        );
    }

    @Override
    public String toString() {
        return "Color [red: " + red
                + ", green: " + green
                + ", blue: " + blue
                + "]";
    }
}
