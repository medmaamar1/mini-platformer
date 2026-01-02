package com.miniplatformer.patterns.state;

import com.miniplatformer.core.GameManager;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Pause State - game paused
 * Part of State Pattern
 */
public class PauseState implements GameState {
    private static final String STATE_NAME = "PAUSE";

    @Override
    public void enter(GameManager manager) {
        GameLogger.logState("Game", "PLAYING", "PAUSE");
    }

    @Override
    public void exit(GameManager manager) {
        GameLogger.logState("Game", "PAUSE", "PLAYING");
    }

    @Override
    public void update(GameManager manager) {
        // Pause state doesn't need updates
    }

    @Override
    public void render(GraphicsContext gc) {
        // Draw semi-transparent overlay
        gc.setFill(new Color(0, 0, 0, 0.5));
        gc.fillRect(0, 0, 800, 600);

        // Draw pause text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.fillText("PAUSED", 320, 280);

        // Draw instructions
        gc.setFont(Font.font("Arial", 18));
        gc.fillText("Press ESC to Resume", 300, 350);
        gc.fillText("Press Q to Quit", 320, 400);
    }

    @Override
    public void handleInput(GameManager manager, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed, boolean escapePressed) {
        // Resume game
        if (escapePressed) {
            manager.resumeGame();
        }
    }

    public String getStateName() {
        return STATE_NAME;
    }
}
