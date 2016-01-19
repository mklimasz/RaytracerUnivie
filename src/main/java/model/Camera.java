package model;

public class Camera {
    private Vector3D position;
    private Vector3D lookAt;
    private Vector3D up;
    private int angle;
    private int width;
    private int height;
    private int maxBounces;

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getLookAt() {
        return lookAt;
    }

    public void setLookAt(Vector3D lookAt) {
        this.lookAt = lookAt;
    }

    public Vector3D getUp() {
        return up;
    }

    public void setUp(Vector3D up) {
        this.up = up;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMaxBounces() {
        return maxBounces;
    }

    public void setMaxBounces(int maxBounces) {
        this.maxBounces = maxBounces;
    }

    @Override
    public String toString() {
        return "Camera [position: " + position.toString()
                + ", lookAt: " + lookAt.toString()
                + ", up: " + up.toString()
                + ", angle: " + angle
                + ", width: " + width
                + ", height: " + height
                + ", maxBounces: " + maxBounces
                + "]";
    }
}
