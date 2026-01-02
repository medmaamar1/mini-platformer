package com.miniplatformer.patterns.state;

import com.miniplatformer.core.GameManager;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Menu State - displays main menu with retro pixel art style
 * Part of State Pattern
 */
public class MenuState implements GameState {
    private static final String STATE_NAME = "MENU";
    private int selectedOption = 0; // 0 = Start, 1 = Quit
    private long animationTime = 0;
    private int pixelSize = 4;
    
    @Override
    public void enter(GameManager manager) {
        GameLogger.info("Entered Menu State");
        selectedOption = 0;
        animationTime = 0;
    }

    @Override
    public void exit(GameManager manager) {
        GameLogger.info("Exited Menu State");
    }

    @Override
    public void update(GameManager manager) {
        animationTime++;
    }

    @Override
    public void render(GraphicsContext gc) {
        // Clear screen with dark background
        gc.setFill(Color.rgb(20, 15, 30));
        gc.fillRect(0, 0, 800, 600);
        
        // Draw pixelated stars in background
        drawPixelStars(gc);
        
        // Draw decorative pixel border
        drawPixelBorder(gc);
        
        // Draw animated title with pixel effect (centered at top)
        drawAnimatedTitle(gc);
        
        // Draw menu options with retro styling (centered)
        gc.setFont(Font.font("Press Start 2P", FontWeight.BOLD, 24));
        drawMenuOption(gc, 0, "START GAME", 300, 320, selectedOption == 0);
        drawMenuOption(gc, 1, "QUIT", 300, 400, selectedOption == 1);
        
        // Draw animated cursor (positioned next to selected option)
        drawAnimatedCursor(gc);
        
        // Draw retro instructions (bottom section)
        drawRetroInstructions(gc);
    }
    
    private void drawPixelStars(GraphicsContext gc) {
        // Create twinkling stars effect
        for (int i = 0; i < 50; i++) {
            int x = (int)(Math.random() * 780 + 10);
            int y = (int)(Math.random() * 580 + 10);
            int size = (int)(Math.random() * 3 + 2);
            double brightness = Math.max(0.0, Math.min(1.0, 0.3 + Math.sin(animationTime * 0.05 + i) * 0.7));
            gc.setFill(Color.rgb(255, 255, 200, brightness));
            // Draw pixelated star
            for (int px = 0; px < size * pixelSize; px += pixelSize) {
                for (int py = 0; py < size * pixelSize; py += pixelSize) {
                    gc.fillRect(x + px, y + py, pixelSize, pixelSize);
                }
            }
        }
    }
    
    private void drawPixelBorder(GraphicsContext gc) {
        // Draw retro pixel border frame
        int borderThickness = 8;
        gc.setFill(Color.rgb(100, 80, 60));
        // Top border
        for (int i = 0; i < 800; i += pixelSize) {
            gc.fillRect(i, 0, pixelSize, borderThickness);
        }
        // Bottom border
        for (int i = 0; i < 800; i += pixelSize) {
            gc.fillRect(i, 600 - borderThickness, pixelSize, borderThickness);
        }
        // Left border
        for (int i = 0; i < 600; i += pixelSize) {
            gc.fillRect(0, i, borderThickness, pixelSize);
        }
        // Right border
        for (int i = 0; i < 600; i += pixelSize) {
            gc.fillRect(800 - borderThickness, i, borderThickness, pixelSize);
        }
        
        // Draw corner pixels
        gc.setFill(Color.rgb(255, 200, 100));
        gc.fillRect(0, 0, pixelSize * 2, pixelSize * 2);
        gc.fillRect(800 - pixelSize * 2, 0, pixelSize * 2, pixelSize * 2);
        gc.fillRect(0, 600 - pixelSize * 2, pixelSize * 2, pixelSize * 2);
        gc.fillRect(800 - pixelSize * 2, 600 - pixelSize * 2, pixelSize * 2, pixelSize * 2);
    }
    
    private void drawAnimatedTitle(GraphicsContext gc) {
        // Animated title with bouncing/scaling effect
        double scale = 1.0 + Math.sin(animationTime * 0.03) * 0.15;
        double bounce = Math.abs(Math.sin(animationTime * 0.05)) * 10;
        
        // Draw shadow
        gc.setFill(Color.rgb(0, 0, 0, 0.3));
        gc.fillRect(200 - bounce + 10, 210 - bounce + 10, 400 + bounce * 2, 60 + bounce * 2);
        
        // Draw pixelated title letters
        gc.setFill(Color.rgb(255, 255, 200));
        drawPixelText(gc, "MINI", 200, 200, (int)(48 * scale));
        drawPixelText(gc, "PLATFORMER", 200, 260, (int)(40 * scale));
        
        // Draw subtitle with pixel effect
        gc.setFont(Font.font("Press Start 2P", FontWeight.BOLD, (int)(16 * scale)));
        gc.setFill(Color.rgb(200, 200, 150));
        gc.fillText("Design Patterns Project", 300, 300);
    }
    
    private void drawPixelText(GraphicsContext gc, String text, double x, double y, int size) {
        // Draw text as pixelated blocks
        char[] chars = text.toCharArray();
        double currentX = x;
        for (char c : chars) {
            // Draw each character as a pixel block
            for (int px = 0; px < 8; px++) {
                for (int py = 0; py < 8; py++) {
                    if (px < 7 && py < 7 && (px * 8 + py) < chars.length * 64) {
                        gc.fillRect(currentX + px * size / 8, y + py * size / 8, size / 8, size / 8);
                    }
                }
            }
            currentX += size;
        }
    }
    
    private void drawMenuOption(GraphicsContext gc, int option, String text, double x, double y, boolean selected) {
        // Draw pixelated option box
        Color bgColor = selected ? Color.rgb(255, 200, 50) : Color.rgb(50, 50, 50);
        Color textColor = selected ? Color.WHITE : Color.rgb(200, 200, 200);
        
        // Draw option background
        gc.setFill(bgColor);
        gc.fillRect(x - 10, y - 20, 220, 50);
        
        // Draw pixel border
        gc.setFill(Color.rgb(255, 255, 100));
        for (int i = 0; i < 220; i += pixelSize) {
            gc.fillRect(x - 10 + i, y - 20, pixelSize, pixelSize);
            gc.fillRect(x - 10 + i, y + 30 - pixelSize, pixelSize, pixelSize);
            gc.fillRect(x + 210 - pixelSize, y - 20 + i, pixelSize, pixelSize);
            gc.fillRect(x + 210 - pixelSize, y + 30 - pixelSize, pixelSize, pixelSize);
        }
        
        // Draw text
        gc.setFill(textColor);
        gc.fillText(text, x + 10, y + 5);
        
        // Draw pixel cursor
        if (selected) {
            double cursorX = x - 25 + Math.sin(animationTime * 0.1) * 10;
            gc.setFill(Color.rgb(255, 255, 0));
            drawPixelCursor(gc, cursorX, y + 15);
        }
    }
    
    private void drawPixelCursor(GraphicsContext gc, double x, double y) {
        // Draw pixelated cursor arrow
        gc.fillRect(x, y, pixelSize, pixelSize);
        gc.fillRect(x + pixelSize, y + pixelSize, pixelSize, pixelSize);
        gc.fillRect(x + pixelSize * 2, y, pixelSize, pixelSize);
    }
    
    private void drawAnimatedCursor(GraphicsContext gc) {
        // Smooth animated cursor - positioned next to selected option
        double cursorX = 270 + Math.sin(animationTime * 0.08) * 5;
        double cursorY = selectedOption == 0 ? 325 : 405;
        
        // Draw glow effect with proper opacity
        for (int i = 3; i > 0; i--) {
            double alpha = 0.3 / i;
            gc.setFill(Color.rgb(255, 255, 0, alpha));
            gc.fillOval(cursorX - 4 * i, cursorY - 4 * i, 8 * i, 8 * i);
        }
        
        // Draw cursor
        gc.setFill(Color.rgb(255, 255, 0));
        drawPixelCursor(gc, cursorX, cursorY);
    }
    
    private void drawPixelDecorations(GraphicsContext gc) {
        // Draw pixelated coin
        gc.setFill(Color.rgb(255, 215, 0));
        gc.fillRect(650, 500, 16, 16);
        gc.fillRect(668, 500, 16, 16);
        gc.fillRect(652, 492, 16, 16);
        gc.fillRect(668, 492, 16, 16);
        
        // Draw pixelated platform
        gc.setFill(Color.rgb(78, 205, 196));
        gc.fillRect(100, 480, 80, 16);
        gc.fillRect(100, 480, 80, 16);
        gc.fillRect(180, 480, 80, 16);
        
        // Draw pixelated enemy (Goomba)
        gc.setFill(Color.rgb(139, 69, 19));
        gc.fillRect(680, 450, 32, 32);
        gc.fillRect(688, 450, 32, 32);
        gc.fillRect(680, 462, 32, 32);
    }
    
    private void drawRetroInstructions(GraphicsContext gc) {
        // Draw retro-styled instructions in organized sections
        int startY = 480;
        int sectionGap = 90;
        
        // Left section - Controls
        gc.setFont(Font.font("Press Start 2P", FontWeight.BOLD, 14));
        gc.setFill(Color.rgb(255, 200, 100));
        gc.fillText("CONTROLS", 50, startY + 20);
        
        gc.setFont(Font.font("Press Start 2P", 12));
        gc.setFill(Color.rgb(200, 200, 150));
        gc.fillText("ARROWS: Move", 50, startY + 45);
        gc.fillText("SPACE: Jump", 50, startY + 65);
        gc.fillText("ESC: Pause", 50, startY + 85);
        
        // Right section - Instructions
        gc.setFont(Font.font("Press Start 2P", FontWeight.BOLD, 14));
        gc.setFill(Color.rgb(255, 200, 100));
        gc.fillText("INSTRUCTIONS", 450, startY + 20);
        
        gc.setFont(Font.font("Press Start 2P", 12));
        gc.setFill(Color.rgb(200, 200, 150));
        gc.fillText("Collect coins for points", 450, startY + 45);
        gc.fillText("Defeat enemies by jumping", 450, startY + 65);
        gc.fillText("Collect power-ups for abilities", 450, startY + 85);
    }

    @Override
    public void handleInput(GameManager manager, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed, boolean escapePressed) {
        // Toggle between start and quit options
        if (jumpPressed) {
            selectedOption = 0;
        }
        if (escapePressed) {
            selectedOption = 1;
        }
        
        // Select option
        if (attackPressed) {
            if (selectedOption == 0) {
                manager.startGame();
            } else {
                System.exit(0);
            }
        }
    }

    public String getStateName() {
        return STATE_NAME;
    }
}
