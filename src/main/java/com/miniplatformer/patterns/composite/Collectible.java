package com.miniplatformer.patterns.composite;

import com.miniplatformer.config.GameConfig;
import com.miniplatformer.entities.Entity;
import com.miniplatformer.patterns.decorator.Character;
import com.miniplatformer.patterns.decorator.*;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Collectible class - leaf component in Composite Pattern
 * Represents collectible items (coins, power-ups, etc.)
 */
public class Collectible implements GameComponent {
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean active;
    private String type;
    private String color;
    private int value;

    public Collectible(double x, double y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.width = GameConfig.COLLECTIBLE_SIZE;
        this.height = GameConfig.COLLECTIBLE_SIZE;
        this.active = true;
        
        // Set properties based on type
        switch (type) {
            case "coin":
                this.color = GameConfig.COLOR_COIN;
                this.value = 10;
                break;
            case "speedBoost":
                this.color = GameConfig.COLOR_POWERUP_SPEED;
                this.value = 0;
                break;
            case "shield":
                this.color = GameConfig.COLOR_POWERUP_SHIELD;
                this.value = 0;
                break;
            case "weapon":
                this.color = GameConfig.COLOR_POWERUP_WEAPON;
                this.value = 0;
                break;
            case "doubleJump":
                this.color = GameConfig.COLOR_POWERUP_DOUBLEJUMP;
                this.value = 0;
                break;
            case "invincibility":
                this.color = GameConfig.COLOR_POWERUP_INVINCIBILITY;
                this.value = 0;
                break;
            default:
                this.color = GameConfig.COLOR_COIN;
                this.value = 10;
        }
    }

    @Override
    public void update() {
        // Collectibles are static, no update needed
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;
        
        // Add floating animation
        double floatOffset = Math.sin(System.currentTimeMillis() / 300.0) * 3;
        double renderY = y + floatOffset;
        
        // Draw different shapes based on type
        switch (type) {
            case "coin":
                // Draw coin with shine effect
                gc.setFill(Color.web("#FFD700"));
                gc.fillOval(x, renderY, width, height);
                
                // Inner shine
                gc.setFill(Color.web("#FFFACD"));
                gc.fillOval(x + 3, renderY + 3, width - 6, height - 6);
                
                // Dollar sign
                gc.setFill(Color.web("#B8860B"));
                gc.fillText("$", x + width/3, renderY + height*0.7);
                
                // Border
                gc.setStroke(Color.web("#DAA520"));
                gc.setLineWidth(2);
                gc.strokeOval(x, renderY, width, height);
                break;
                
            case "speedBoost":
                // Draw lightning bolt shape
                gc.setFill(Color.web("#00FF00"));
                gc.fillPolygon(new double[]{x + width/2, x + width - 4, x + width/2 + 4, x + width/2, x + 4, x + width/2 - 4},
                              new double[]{renderY, renderY + 4, renderY + height/2, renderY + height/2, renderY + height - 4, renderY + height/2}, 6);
                // Glow effect
                gc.setStroke(Color.web("#ADFF2F"));
                gc.setLineWidth(2);
                gc.strokePolygon(new double[]{x + width/2, x + width - 4, x + width/2 + 4, x + width/2, x + 4, x + width/2 - 4},
                              new double[]{renderY, renderY + 4, renderY + height/2, renderY + height/2, renderY + height - 4, renderY + height/2}, 6);
                break;
                
            case "shield":
                // Draw shield shape
                gc.setFill(Color.web("#00BFFF"));
                gc.fillOval(x + 2, renderY + 2, width - 4, height - 4);
                // Shield pattern
                gc.setStroke(Color.web("#1E90FF"));
                gc.setLineWidth(2);
                gc.strokeOval(x + 2, renderY + 2, width - 4, height - 4);
                // Cross pattern
                gc.strokeLine(x + width/2, renderY + 4, x + width/2, renderY + height - 4);
                gc.strokeLine(x + 4, renderY + height/2, x + width - 4, renderY + height/2);
                break;
                
            case "weapon":
                // Draw sword shape
                gc.setFill(Color.web("#FF0000"));
                // Handle
                gc.fillRect(x + width/2 - 2, renderY + height - 8, 4, 8);
                // Blade
                gc.fillPolygon(new double[]{x + width/2 - 2, x + width/2 + 2, x + width/2},
                              new double[]{renderY + height - 8, renderY + height - 8, renderY}, 3);
                // Glow
                gc.setStroke(Color.web("#FF6347"));
                gc.setLineWidth(2);
                gc.strokePolygon(new double[]{x + width/2 - 2, x + width/2 + 2, x + width/2},
                              new double[]{renderY + height - 8, renderY + height - 8, renderY}, 3);
                break;
                
            case "doubleJump":
                // Draw double jump icon (two arrows)
                gc.setFill(Color.web("#FF00FF"));
                // First arrow
                gc.fillPolygon(new double[]{x + 4, x + width/2, x + width - 4},
                              new double[]{renderY + height - 4, renderY + 4, renderY + height - 4}, 3);
                // Second arrow
                gc.fillPolygon(new double[]{x + 6, x + width/2 + 2, x + width - 2},
                              new double[]{renderY + height - 2, renderY + 8, renderY + height - 2}, 3);
                // Glow
                gc.setStroke(Color.web("#FF69B4"));
                gc.setLineWidth(1);
                gc.strokePolygon(new double[]{x + 4, x + width/2, x + width - 4},
                              new double[]{renderY + height - 4, renderY + 4, renderY + height - 4}, 3);
                break;
                
            case "invincibility":
                // Draw star shape
                gc.setFill(Color.web("#FFD700"));
                double[] starX = new double[]{x + width/2, x + width - 4, x + width/2 + 2, x + width - 2, x + width/2, x + 2, x + width/2 - 2, x + 4, x + width/2, x + width/2};
                double[] starY = new double[]{renderY, renderY + height/3, renderY + height/2, renderY + height - 4, renderY + height - 2, renderY + height - 4, renderY + height/2, renderY + height/3, renderY, renderY + height/2};
                gc.fillPolygon(starX, starY, 10);
                // Glow effect
                gc.setStroke(Color.web("#FFA500"));
                gc.setLineWidth(2);
                gc.strokePolygon(starX, starY, 10);
                break;
                
            default:
                gc.fillOval(x, renderY, width, height);
        }
    }

    /**
     * Collect this item and apply its effect to player
     */
    public void collect() {
        this.active = false;
        GameLogger.logGameplay("Collected " + type);
    }

    /**
     * Apply power-up effect to player character
     */
    public Character applyToCharacter(Character character) {
        switch (type) {
            case "speedBoost":
                GameLogger.logDecorator("SpeedBoost", character.getName());
                return new SpeedBoostDecorator(character);
            case "shield":
                GameLogger.logDecorator("Shield", character.getName());
                return new ShieldDecorator(character);
            case "weapon":
                GameLogger.logDecorator("Weapon", character.getName());
                return new WeaponDecorator(character);
            case "doubleJump":
                GameLogger.logDecorator("DoubleJump", character.getName());
                return new DoubleJumpDecorator(character);
            case "invincibility":
                GameLogger.logDecorator("Invincibility", character.getName());
                return new InvincibilityDecorator(character, GameConfig.POWERUP_DURATION);
            default:
                return character;
        }
    }

    @Override
    public void add(GameComponent component) {
        // Leaf nodes cannot have children
    }

    @Override
    public void remove(GameComponent component) {
        // Leaf nodes cannot have children
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public Entity getEntity() {
        return new Entity(x, y, width, height);
    }
}
