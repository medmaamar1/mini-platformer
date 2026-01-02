package com.miniplatformer.patterns.composite;

import javafx.scene.canvas.GraphicsContext;

/**
 * Game Component interface for Composite Pattern
 * Defines the contract for all game components (levels, scenes, platforms, etc.)
 */
public interface GameComponent {
    /**
     * Update the component
     */
    void update();

    /**
     * Render the component
     */
    void render(GraphicsContext gc);

    /**
     * Add a child component
     */
    void add(GameComponent component);

    /**
     * Remove a child component
     */
    void remove(GameComponent component);

    /**
     * Get the x position
     */
    double getX();

    /**
     * Get the y position
     */
    double getY();

    /**
     * Set the x position
     */
    void setX(double x);

    /**
     * Set the y position
     */
    void setY(double y);

    /**
     * Get the width
     */
    double getWidth();

    /**
     * Get the height
     */
    double getHeight();

    /**
     * Check if component is active
     */
    boolean isActive();

    /**
     * Set component active state
     */
    void setActive(boolean active);
}
