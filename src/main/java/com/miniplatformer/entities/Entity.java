package com.miniplatformer.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Base entity class for all game objects
 * Provides common properties and methods for position, size, and collision
 */
public class Entity {
    protected Position position;
    protected double width;
    protected double height;
    protected boolean active;

    public Entity(double x, double y, double width, double height) {
        this.position = new Position(x, y);
        this.width = width;
        this.height = height;
        this.active = true;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getX() {
        return position.getX();
    }

    public void setX(double x) {
        position.setX(x);
    }

    public double getY() {
        return position.getY();
    }

    public void setY(double y) {
        position.setY(y);
    }

    public double getVelocityX() {
        return position.getVelocityX();
    }

    public void setVelocityX(double velocityX) {
        position.setVelocityX(velocityX);
    }

    public double getVelocityY() {
        return position.getVelocityY();
    }

    public void setVelocityY(double velocityY) {
        position.setVelocityY(velocityY);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Get the bounding rectangle for collision detection
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }

    /**
     * Check if this entity collides with another entity
     */
    public boolean collidesWith(Entity other) {
        return this.getBounds().getBoundsInParent().intersects(
            other.getBounds().getBoundsInParent()
        );
    }

    /**
     * Update the entity (called each frame)
     */
    public void update() {
        position.updatePosition();
    }

    /**
     * Render the entity (called each frame)
     */
    public void render(GraphicsContext gc) {
        // Default render - draw a rectangle
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(getX(), getY(), width, height);
    }
}
