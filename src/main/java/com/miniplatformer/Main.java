package com.miniplatformer;

import com.miniplatformer.config.GameConfig;
import com.miniplatformer.core.GameManager;
import com.miniplatformer.utils.GameLogger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main Application Entry Point
 * JavaFX application for Mini Platformer game
 */
public class Main extends Application {
    private Canvas canvas;
    private GraphicsContext gc;
    private GameManager gameManager;
    private AnimationTimer gameLoop;
    
    // Input state
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean jumpPressed = false;
    private boolean attackPressed = false;
    private boolean escapePressed = false;

    @Override
    public void start(Stage primaryStage) {
        GameLogger.info("Starting Mini Platformer");
        
        // Initialize game manager
        gameManager = GameManager.getInstance();
        
        // Create canvas
        canvas = new Canvas(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        
        // Set up scene
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        scene.setFill(Color.web(GameConfig.COLOR_BACKGROUND));
        
        // Set up input handling
        scene.setOnKeyPressed(this::handleKeyPressed);
        scene.setOnKeyReleased(this::handleKeyReleased);
        
        // Configure stage
        primaryStage.setTitle(GameConfig.WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Start game loop
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            private final long targetFPS = 1000000000 / GameConfig.FPS;
            
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= targetFPS) {
                    // Update game
                    gameManager.update();
                    
                    // Render game
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    gameManager.getCurrentState().render(gc);
                    
                    lastUpdate = now;
                }
            }
        };
        
        gameLoop.start();
        GameLogger.info("Game loop started at " + GameConfig.FPS + " FPS");
    }
    
    /**
     * Handle key press events
     */
    private void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        
        if (code == KeyCode.LEFT) {
            leftPressed = true;
        } else if (code == KeyCode.RIGHT) {
            rightPressed = true;
        } else if (code == KeyCode.SPACE || code == KeyCode.UP) {
            jumpPressed = true;
        } else if (code == KeyCode.ENTER) {
            attackPressed = true;
        } else if (code == KeyCode.ESCAPE) {
            escapePressed = true;
        }
        
        // Pass input to game manager
        gameManager.handleInput(leftPressed, rightPressed, jumpPressed, attackPressed, escapePressed);
    }
    
    /**
     * Handle key release events
     */
    private void handleKeyReleased(KeyEvent event) {
        KeyCode code = event.getCode();
        
        if (code == KeyCode.LEFT) {
            leftPressed = false;
        } else if (code == KeyCode.RIGHT) {
            rightPressed = false;
        } else if (code == KeyCode.SPACE || code == KeyCode.UP) {
            jumpPressed = false;
        } else if (code == KeyCode.ENTER) {
            attackPressed = false;
        } else if (code == KeyCode.ESCAPE) {
            escapePressed = false;
        }
        
        // Pass input to game manager
        gameManager.handleInput(leftPressed, rightPressed, jumpPressed, attackPressed, escapePressed);
    }
    
    @Override
    public void stop() throws Exception {
        GameLogger.info("Stopping Mini Platformer");
        if (gameLoop != null) {
            gameLoop.stop();
        }
        GameManager.reset();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
