package com.miniplatformer.patterns.state;

import com.miniplatformer.core.GameManager;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Victory State - displays victory screen
 * Part of State Pattern
 */
public class VictoryState implements GameState {
    private static final String STATE_NAME = "VICTORY";
    private int finalScore;
    private long finalTime;

    public VictoryState(int score, long time) {
        this.finalScore = score;
        this.finalTime = time;
    }

    @Override
    public void enter(GameManager manager) {
        GameLogger.logState("Game", "PLAYING", "VICTORY");
        GameLogger.info("Victory! Final score: " + finalScore + ", Time: " + (finalTime / 1000) + "s");
    }

    @Override
    public void exit(GameManager manager) {
        GameLogger.info("Exited Victory State");
    }

    @Override
    public void update(GameManager manager) {
        // Victory state doesn't need updates
    }

    @Override
    public void render(GraphicsContext gc) {
        // Clear screen
        gc.setFill(Color.rgb(0, 100, 0));
        gc.fillRect(0, 0, 800, 600);

        // Draw title
        gc.setFill(Color.GOLD);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.fillText("VICTORY!", 300, 200);

        // Draw final score
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        gc.fillText("Final Score: " + finalScore, 260, 300);

        // Draw final time
        long seconds = finalTime / 1000;
        gc.setFont(Font.font("Arial", 24));
        gc.fillText("Time: " + seconds + "s", 310, 350);

        // Draw instructions
        gc.setFont(Font.font("Arial", 18));
        gc.setFill(Color.LIGHTGRAY);
        gc.fillText("Press ENTER to return to Menu", 260, 450);
        gc.fillText("Press Q to Quit", 300, 500);
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
