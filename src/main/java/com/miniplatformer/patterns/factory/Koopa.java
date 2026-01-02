package com.miniplatformer.patterns.factory;

import com.miniplatformer.config.GameConfig;
import com.miniplatformer.entities.EnemyEntity;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Koopa enemy - turtle-like enemy with shell
 * Part of Factory Pattern
 */
public class Koopa implements Enemy {
    private EnemyEntity entity;
    private double speed;
    private boolean movingRight;
    private boolean inShell;

    public Koopa(double x, double y) {
        this.entity = new EnemyEntity(x, y, GameConfig.ENEMY_WIDTH, GameConfig.ENEMY_HEIGHT, "Koopa");
        this.speed = GameConfig.ENEMY_SPEED * 0.8;
        this.movingRight = true;
        this.inShell = false;
        GameLogger.info("Koopa enemy created at (" + x + ", " + y + ")");
    }

    @Override
    public void update() {
        if (!entity.isActive()) return;

        if (inShell) {
            // When in shell, move faster in one direction
            entity.setVelocityX(movingRight ? speed * 2 : -speed * 2);
        } else {
            // Normal movement
            if (movingRight) {
                entity.setVelocityX(speed);
            } else {
                entity.setVelocityX(-speed);
            }
        }

        entity.getPosition().updatePosition();

        // Reverse direction at boundaries
        if (entity.getX() < 0) {
            movingRight = true;
        } else if (entity.getX() > GameConfig.WINDOW_WIDTH - GameConfig.ENEMY_WIDTH) {
            movingRight = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!entity.isActive()) return;

        double x = entity.getX();
        double y = entity.getY();
        double w = entity.getWidth();
        double h = entity.getHeight();

        if (inShell) {
            // Draw shell mode
            gc.setFill(Color.web("#228B22")); // Green shell
            gc.fillOval(x + 2, y + 4, w - 4, h - 8);
            
            // Shell pattern
            gc.setStroke(Color.web("#006400"));
            gc.setLineWidth(1);
            gc.strokeOval(x + 6, y + 8, w - 12, h - 16);
            
            // Shell spikes
            gc.setFill(Color.web("#32CD32"));
            gc.fillPolygon(new double[]{x + w/2, x + w/2 - 4, x + w/2 + 4}, new double[]{y, y + 6, y + 6}, 3);
        } else {
            // Draw turtle body
            gc.setFill(Color.web("#DEB887")); // Skin color
            gc.fillOval(x + 4, y + 2, w - 8, h - 12);
            
            // Draw shell on back
            gc.setFill(Color.web("#228B22"));
            gc.fillOval(x + 2, y + h - 14, w - 4, 12);
            
            // Shell pattern
            gc.setStroke(Color.web("#006400"));
            gc.setLineWidth(1);
            gc.strokeOval(x + 6, y + h - 12, w - 12, 8);
            
            // Draw head
            gc.setFill(Color.web("#DEB887"));
            gc.fillOval(x + w/2 - 6, y + 4, 12, 10);
            
            // Draw eyes
            gc.setFill(Color.WHITE);
            gc.fillOval(x + w/2 - 4, y + 6, 4, 4);
            gc.fillOval(x + w/2 + 2, y + 6, 4, 4);
            
            // Draw pupils
            gc.setFill(Color.BLACK);
            if (movingRight) {
                gc.fillOval(x + w/2 + 3, y + 7, 2, 2);
            } else {
                gc.fillOval(x + w/2 - 3, y + 7, 2, 2);
            }
            
            // Draw beak
            gc.setFill(Color.web("#FFD700"));
            gc.fillPolygon(new double[]{x + w/2, x + w/2 - 3, x + w/2 + 3}, new double[]{y + 12, y + 15, y + 15}, 3);
        }
        
        // Draw feet
        gc.setFill(Color.web("#DEB887"));
        gc.fillOval(x + 2, y + h - 6, 8, 6);
        gc.fillOval(x + w - 10, y + h - 6, 8, 6);
    }

    @Override
    public String getType() {
        return "Koopa";
    }

    @Override
    public boolean isActive() {
        return entity.isActive();
    }

    @Override
    public void setActive(boolean active) {
        entity.setActive(active);
    }

    @Override
    public double getX() {
        return entity.getX();
    }

    @Override
    public double getY() {
        return entity.getY();
    }

    @Override
    public double getWidth() {
        return entity.getWidth();
    }

    @Override
    public double getHeight() {
        return entity.getHeight();
    }

    @Override
    public void setVelocityX(double velocityX) {
        entity.setVelocityX(velocityX);
    }

    @Override
    public void setVelocityY(double velocityY) {
        entity.setVelocityY(velocityY);
    }

    @Override
    public double getVelocityX() {
        return entity.getVelocityX();
    }

    @Override
    public double getVelocityY() {
        return entity.getVelocityY();
    }

    public void enterShell() {
        this.inShell = true;
        GameLogger.logGameplay("Koopa entered shell mode");
    }

    public boolean isInShell() {
        return inShell;
    }
}
