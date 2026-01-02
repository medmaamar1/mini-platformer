package com.miniplatformer.patterns.factory;

import javafx.scene.canvas.GraphicsContext;

/**
 * Enemy interface for Factory Pattern
 * Defines the contract for all enemy types
 */
public interface Enemy {
    /**
     * Update enemy
     */
    void update();

    /**
     * Render enemy
     */
    void render(GraphicsContext gc);

    /**
     * Get enemy type
     */
    String getType();

    /**
     * Get X position
     */
    double getX();

    /**
     * Get Y position
     */
    double getY();

    /**
     * Get width
     */
    double getWidth();

    /**
     * Get height
     */
    double getHeight();

    /**
     * Check if enemy is active
     */
    boolean isActive();

    /**
     * Set enemy active state
     */
    void setActive(boolean active);

    /**
     * Set X velocity
     */
    void setVelocityX(double velocityX);

    /**
     * Set Y velocity
     */
    void setVelocityY(double velocityY);

    /**
     * Get X velocity
     */
    double getVelocityX();

    /**
     * Get Y velocity
     */
    double getVelocityY();
}
