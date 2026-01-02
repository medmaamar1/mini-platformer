package com.miniplatformer.patterns.composite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Platform class - leaf component in Composite Pattern
 * Represents a solid platform the player can stand on
 */
public class Platform implements GameComponent {
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean active;
    private String color;

    public Platform(double x, double y, double width, double height, String color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.active = true;
    }

    @Override
    public void update() {
        // Platforms are static, no update needed
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;
        
        // Draw main platform body with gradient effect
        gc.setFill(Color.web(color));
        gc.fillRect(x, y, width, height);
        
        // Add top highlight for 3D effect
        gc.setFill(Color.web("#FFFFFF"));
        gc.setGlobalAlpha(0.3);
        gc.fillRect(x, y, width, 4);
        gc.setGlobalAlpha(1.0);
        
        // Add bottom shadow for depth
        gc.setFill(Color.web("#000000"));
        gc.setGlobalAlpha(0.2);
        gc.fillRect(x, y + height - 4, width, 4);
        gc.setGlobalAlpha(1.0);
        
        // Add border with rounded corners effect
        gc.setStroke(Color.web("#FFFFFF"));
        gc.setLineWidth(2);
        gc.strokeRect(x, y, width, height);
        
        // Add decorative pattern on platform
        gc.setStroke(Color.web("#FFFFFF"));
        gc.setGlobalAlpha(0.2);
        gc.setLineWidth(1);
        // Draw horizontal lines for texture
        for (int i = 8; i < height - 4; i += 8) {
            gc.strokeLine(x + 4, y + i, x + width - 4, y + i);
        }
        // Draw vertical lines for texture
        for (int i = 12; i < width - 4; i += 12) {
            gc.strokeLine(x + i, y + 4, x + i, y + height - 4);
        }
        gc.setGlobalAlpha(1.0);
        
        // Add corner accents
        gc.setFill(Color.web("#FFFFFF"));
        gc.setGlobalAlpha(0.4);
        gc.fillOval(x + 2, y + 2, 4, 4);
        gc.fillOval(x + width - 6, y + 2, 4, 4);
        gc.setGlobalAlpha(1.0);
    }

    @Override
    public void add(GameComponent component) {
        // Leaf nodes cannot have children
    }

    @Override
    public void remove(GameComponent component) {
        // Leaf nodes cannot have children
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
