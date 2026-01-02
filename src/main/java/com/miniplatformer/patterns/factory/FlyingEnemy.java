package com.miniplatformer.patterns.factory;

import com.miniplatformer.config.GameConfig;
import com.miniplatformer.entities.EnemyEntity;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Flying Enemy - bird-like enemy that flies
 * Part of Factory Pattern
 */
public class FlyingEnemy implements Enemy {
    private EnemyEntity entity;
    private double speed;
    private boolean movingRight;
    private double baseY;

    public FlyingEnemy(double x, double y) {
        this.entity = new EnemyEntity(x, y, GameConfig.ENEMY_WIDTH, GameConfig.ENEMY_HEIGHT, "FlyingEnemy");
        this.speed = GameConfig.ENEMY_SPEED * 1.2;
        this.movingRight = true;
        this.baseY = y;
        GameLogger.info("FlyingEnemy created at (" + x + ", " + y + ")");
    }

    @Override
    public void update() {
        if (!entity.isActive()) return;

        // Move horizontally
        if (movingRight) {
            entity.setVelocityX(speed);
        } else {
            entity.setVelocityX(-speed);
        }

        // Add slight vertical oscillation
        double oscillation = Math.sin(System.currentTimeMillis() / 500.0) * 20;
        entity.getPosition().setVelocityY(oscillation * 0.1);

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

        // Animate wings based on time
        double wingOffset = Math.sin(System.currentTimeMillis() / 100.0) * 5;

        // Draw left wing
        gc.setFill(Color.web("#87CEEB"));
        gc.fillPolygon(new double[]{x, x - 12, x - 8},
                      new double[]{y + h/2, y + h/2 + wingOffset, y + h/2 + 10}, 3);
        
        // Draw right wing
        gc.fillPolygon(new double[]{x + w, x + w + 12, x + w + 8},
                      new double[]{y + h/2, y + h/2 + wingOffset, y + h/2 + 10}, 3);

        // Draw body (bird-like shape)
        gc.setFill(Color.web("#FF6B6B"));
        gc.fillOval(x + 4, y + 4, w - 8, h - 8);
        
        // Draw belly
        gc.setFill(Color.web("#FFE4E1"));
        gc.fillOval(x + 8, y + h/2, w - 16, h/4);

        // Draw beak
        gc.setFill(Color.web("#FFD700"));
        if (movingRight) {
            gc.fillPolygon(new double[]{x + w - 4, x + w + 6, x + w - 4},
                          new double[]{y + h/2 - 2, y + h/2, y + h/2 + 2}, 3);
        } else {
            gc.fillPolygon(new double[]{x + 4, x - 6, x + 4},
                          new double[]{y + h/2 - 2, y + h/2, y + h/2 + 2}, 3);
        }

        // Draw eyes
        gc.setFill(Color.WHITE);
        gc.fillOval(x + 10, y + 10, 7, 7);
        gc.fillOval(x + w - 17, y + 10, 7, 7);

        // Draw pupils (fierce look)
        gc.setFill(Color.RED);
        if (movingRight) {
            gc.fillOval(x + w - 15, y + 12, 3, 3);
        } else {
            gc.fillOval(x + 12, y + 12, 3, 3);
        }
        
        // Draw tail feathers
        gc.setFill(Color.web("#FF6B6B"));
        gc.fillPolygon(new double[]{x + w/2, x + w/2 - 6, x + w/2 + 6},
                      new double[]{y + h, y + h + 6, y + h + 6}, 3);
    }

    @Override
    public String getType() {
        return "FlyingEnemy";
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
}
