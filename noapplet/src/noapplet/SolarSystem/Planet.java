package noapplet.SolarSystem;

import java.awt.*;
import java.util.ArrayList;

public class Planet {
    private int x;
    private int y;
    private final int radius;
    private final int orbitRadius;
    private double angle;
    private final double orbitSpeed;
    private final Sun sun;
    private ArrayList<Moon> moons = new ArrayList<>();
    private final Color color; // Added color instance variable

    public void addMoon(int orbitRadius, int size, double speed) {
        this.moons.add(new Moon(this, orbitRadius, size, speed));
    }

    // Updated constructor to take Color as an argument
    public Planet(Sun sun, int orbitRadius, int radius, double orbitSpeed, Color color) {
        this.sun = sun;
        this.orbitRadius = orbitRadius;
        this.radius = radius;
        this.orbitSpeed = orbitSpeed;
        this.angle = 0;
        this.color = color;

        this.x = sun.getX() + (int)(orbitRadius * Math.cos(angle));
        this.y = sun.getY() + (int)(orbitRadius * Math.sin(angle));
    }

    public void move() {
        angle += orbitSpeed;
        x = sun.getX() + (int)(orbitRadius * Math.cos(angle));
        y = sun.getY() + (int)(orbitRadius * Math.sin(angle));
        for (Moon moon : moons) {
            moon.move();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public int getOrbitRadius() {
        return orbitRadius;
    }

    public double getAngle() {
        return angle;
    }

    public double getOrbitSpeed() {
        return orbitSpeed;
    }

    // Updated paintComponent method to use the color instance variable
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        for (Moon moon : moons) {
            moon.paintComponent(g);
        }
    }
}
