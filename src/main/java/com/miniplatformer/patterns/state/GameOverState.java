package com.miniplatformer.patterns.state;

import com.miniplatformer.core.GameManager;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Game Over State - displays game over screen
 * Part of State Pattern
 */
public class GameOverState implements GameState {
    private static final String STATE_NAME = "GAME_OVER";
    private int finalScore;

    public GameOverState(int score) {
        this.finalScore = score;
    }

    @Override
    public void enter(GameManager manager) {
        GameLogger.logState("Game", "PLAYING", "GAME_OVER");
        GameLogger.info("Game Over! Final score: " + finalScore);
    }

    @Override
    public void exit(GameManager manager) {
        GameLogger.info("Exited Game Over State");
    }

    @Override
    public void update(GameManager manager) {
        // Game over state doesn't need updates
    }

    @Override
    public void render(GraphicsContext gc) {
        // Clear screen
        gc.setFill(Color.rgb(40, 0, 0));
        gc.fillRect(0, 0, 800, 600);

        // Draw title
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.fillText("GAME OVER", 280, 200);

        // Draw final score
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        gc.fillText("Final Score: " + finalScore, 280, 300);

        // Draw instructions
        gc.setFont(Font.font("Arial", 18));
        gc.setFill(Color.LIGHTGRAY);
        gc.fillText("Press ENTER to return to Menu", 260, 400);
        gc.fillText("Press Q to Quit", 300, 450);
    }

    @Override
    public void handleInput(GameManager manager, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed, boolean escapePressed) {
        // Return to menu
        if (attackPressed) {
            manager.setState(new MenuState());
        }
        
        // Quit game
        if (escapePressed) {
            System.exit(0);
        }
    }

    public String getStateName() {
        return STATE_NAME;
    }
}
