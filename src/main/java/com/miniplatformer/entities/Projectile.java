package com.miniplatformer.entities;

import com.miniplatformer.config.GameConfig;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Projectile entity fired by enemies
 */
public class Projectile extends Entity {
    private boolean active;
    private double velocityX;

    public Projectile(double x, double y, double velocityX) {
        super(x, y, GameConfig.PROJECTILE_WIDTH, GameConfig.PROJECTILE_HEIGHT);
        this.velocityX = velocityX;
        this.active = true;
    }

    @Override
    public void update() {
        if (!active) return;
        setX(getX() + velocityX);
        
        // Deactivate if out of bounds
        if (getX() < 0 || getX() > GameConfig.WINDOW_WIDTH) {
            active = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;
        gc.setFill(Color.web(GameConfig.COLOR_PROJECTILE));
        gc.fillOval(getX(), getY(), width, height);
        
        // Add glow effect
        gc.setGlobalAlpha(0.5);
        gc.fillOval(getX() - 2, getY() - 2, width + 4, height + 4);
        gc.setGlobalAlpha(1.0);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
