package com.miniplatformer.patterns.state;

import com.miniplatformer.core.GameManager;
import javafx.scene.canvas.GraphicsContext;

/**
 * Interface for game states
 * Part of State Pattern - defines the contract for all game states
 */
public interface GameState {
    /**
     * Called when entering this state
     */
    void enter(GameManager manager);

    /**
     * Called when exiting this state
     */
    void exit(GameManager manager);

    /**
     * Update game state (called each frame)
     */
    void update(GameManager manager);

    /**
     * Render game state (called each frame)
     */
    void render(GraphicsContext gc);

    /**
     * Handle input in this state
     */
    void handleInput(GameManager manager, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed, boolean escapePressed);
}
