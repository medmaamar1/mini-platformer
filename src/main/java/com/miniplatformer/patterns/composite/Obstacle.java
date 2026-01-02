package com.miniplatformer.patterns.composite;

import com.miniplatformer.config.GameConfig;
import com.miniplatformer.entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Obstacle class - leaf component in Composite Pattern
 * Represents dangerous elements like spikes or fire
 */
public class Obstacle implements GameComponent {
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean active;
    private String type; // "spike" or "fire"
    private Entity entity;
    private long animationTimer;

    public Obstacle(double x, double y, String type) {
        this.x = x;
        this.y = y;
        this.width = GameConfig.OBSTACLE_WIDTH;
        this.height = GameConfig.OBSTACLE_HEIGHT;
        this.type = type;
        this.active = true;
        this.entity = new Entity(x, y, width, height);
        this.animationTimer = System.currentTimeMillis();
    }

    @Override
    public void update() {
        // Animation logic for fire
        if (type.equals("fire")) {
            animationTimer = System.currentTimeMillis();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;

        if (type.equals("spike")) {
            renderSpike(gc);
        } else if (type.equals("fire")) {
            renderFire(gc);
        }
    }

    private void renderSpike(GraphicsContext gc) {
        gc.setFill(Color.web(GameConfig.COLOR_SPIKE));
        double[] xPoints = {x, x + width / 2, x + width};
        double[] yPoints = {y + height, y, y + height};
        gc.fillPolygon(xPoints, yPoints, 3);
        
        // Add metallic sheen
        gc.setStroke(Color.web("#FFFFFF"));
        gc.setGlobalAlpha(0.3);
        gc.setLineWidth(1);
        gc.strokePolygon(xPoints, yPoints, 3);
        gc.setGlobalAlpha(1.0);
    }

    private void renderFire(GraphicsContext gc) {
        double flick = Math.sin(animationTimer / 100.0) * 5;
        
        // Outer flame
        gc.setFill(Color.web(GameConfig.COLOR_FIRE));
        gc.fillOval(x + 2, y + flick, width - 4, height - flick);
        
        // Inner flame
        gc.setFill(Color.web("#FFFF00")); // Yellow
        gc.fillOval(x + 8, y + 10 + flick, width - 16, height - 15 - flick);
        
        // Base
        gc.setFill(Color.web("#8B0000")); // DarkRed
        gc.fillRect(x + 4, y + height - 8, width - 8, 8);
    }

    @Override
    public void add(GameComponent component) {}

    @Override
    public void remove(GameComponent component) {}

    @Override
    public double getX() { return x; }

    @Override
    public void setX(double x) { this.x = x; this.entity.setX(x); }

    @Override
    public double getY() { return y; }

    @Override
    public void setY(double y) { this.y = y; this.entity.setY(y); }

    @Override
    public double getWidth() { return width; }

    @Override
    public double getHeight() { return height; }

    @Override
    public boolean isActive() { return active; }

    @Override
    public void setActive(boolean active) { this.active = active; }

    public String getType() { return type; }
    
    public Entity getEntity() { return entity; }
}
