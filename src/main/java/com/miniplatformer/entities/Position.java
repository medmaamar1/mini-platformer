package com.miniplatformer.entities;

/**
 * Position and velocity class for game entities
 * Handles 2D position and velocity with double precision
 */
public class Position {
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public Position(double x, double y, double velocityX, double velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void addVelocity(double vx, double vy) {
        this.velocityX += vx;
        this.velocityY += vy;
    }

    public void updatePosition() {
        this.x += velocityX;
        this.y += velocityY;
    }

    public void resetVelocity() {
        this.velocityX = 0;
        this.velocityY = 0;
    }

    @Override
    public Position clone() {
        return new Position(x, y, velocityX, velocityY);
    }

    @Override
    public String toString() {
        return String.format("Position{x=%.2f, y=%.2f, vx=%.2f, vy=%.2f}", x, y, velocityX, velocityY);
    }
}
