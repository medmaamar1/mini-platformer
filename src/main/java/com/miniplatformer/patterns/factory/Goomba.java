package com.miniplatformer.patterns.factory;

import com.miniplatformer.config.GameConfig;
import com.miniplatformer.entities.Position;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Goomba enemy - simple walking enemy
 * Part of Factory Pattern
 */
public class Goomba implements Enemy {
    private Position position;
    private double width;
    private double height;
    private boolean active;
    private double speed;
    private boolean movingRight;

    public Goomba(double x, double y) {
        this.position = new Position(x, y);
        this.width = GameConfig.ENEMY_WIDTH;
        this.height = GameConfig.ENEMY_HEIGHT;
        this.active = true;
        this.speed = GameConfig.ENEMY_SPEED;
        this.movingRight = true;
        GameLogger.info("Goomba enemy created at (" + x + ", " + y + ")");
    }

    @Override
    public void update() {
        if (!active) return;

        // Move back and forth
        if (movingRight) {
            position.setVelocityX(speed);
        } else {
            position.setVelocityX(-speed);
        }

        position.updatePosition();

        // Simple AI: reverse direction at boundaries
        if (position.getX() < 0) {
            movingRight = true;
        } else if (position.getX() > GameConfig.WINDOW_WIDTH - width) {
            movingRight = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;

        double x = position.getX();
        double y = position.getY();
        
        // Draw mushroom cap (brown dome)
        gc.setFill(Color.web("#8B4513"));
        gc.fillOval(x, y, width, height - 8);
        
        // Draw cap spots
        gc.setFill(Color.web("#A0522D"));
        gc.fillOval(x + 6, y + 4, 8, 8);
        gc.fillOval(x + 18, y + 6, 6, 6);
        
        // Draw stem/body
        gc.setFill(Color.web("#DEB887"));
        gc.fillRect(x + 8, y + height - 12, width - 16, 12);
        
        // Draw feet
        gc.setFill(Color.web("#8B4513"));
        gc.fillOval(x + 2, y + height - 8, 10, 8);
        gc.fillOval(x + width - 12, y + height - 8, 10, 8);
        
        // Draw angry eyebrows
        gc.setFill(Color.BLACK);
        gc.fillPolygon(new double[]{x + 6, x + 14, x + 10}, new double[]{y + 10, y + 10, y + 8}, 3);
        gc.fillPolygon(new double[]{x + 18, x + 26, x + 22}, new double[]{y + 10, y + 10, y + 8}, 3);
        
        // Draw eyes
        gc.setFill(Color.WHITE);
        gc.fillOval(x + 8, y + 12, 7, 7);
        gc.fillOval(x + 17, y + 12, 7, 7);
        
        // Draw pupils (angry look)
        gc.setFill(Color.BLACK);
        if (movingRight) {
            gc.fillOval(x + 22, y + 14, 3, 3);
            gc.fillOval(x + 22, y + 14, 3, 3);
        } else {
            gc.fillOval(x + 10, y + 14, 3, 3);
            gc.fillOval(x + 10, y + 14, 3, 3);
        }
        
        // Draw mouth (frown)
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(x + 12, y + 22, x + 20, y + 20);
    }

    @Override
    public String getType() {
        return "Goomba";
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public double getX() {
        return position.getX();
    }

    @Override
    public double getY() {
        return position.getY();
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
    public void setVelocityX(double velocityX) {
        position.setVelocityX(velocityX);
    }

    @Override
    public void setVelocityY(double velocityY) {
        position.setVelocityY(velocityY);
    }

    @Override
    public double getVelocityX() {
        return position.getVelocityX();
    }

    @Override
    public double getVelocityY() {
        return position.getVelocityY();
    }
}
