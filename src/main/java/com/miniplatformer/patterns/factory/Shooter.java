package com.miniplatformer.patterns.factory;

import com.miniplatformer.config.GameConfig;
import com.miniplatformer.core.GameManager;
import com.miniplatformer.entities.Position;
import com.miniplatformer.entities.Projectile;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Shooter enemy - stands still or moves slowly and fires projectiles
 */
public class Shooter implements Enemy {
    private Position position;
    private double width;
    private double height;
    private boolean active;
    private long lastShootTime;
    private boolean movingRight;
    private double speed;

    public Shooter(double x, double y) {
        this.position = new Position(x, y);
        this.width = GameConfig.ENEMY_WIDTH;
        this.height = GameConfig.ENEMY_HEIGHT;
        this.active = true;
        this.lastShootTime = System.currentTimeMillis();
        this.movingRight = false;
        this.speed = 1.0;
        GameLogger.info("Shooter enemy created at (" + x + ", " + y + ")");
    }

    @Override
    public void update() {
        if (!active) return;

        // Simple movement
        if (movingRight) {
            position.setVelocityX(speed);
        } else {
            position.setVelocityX(-speed);
        }
        position.updatePosition();

        if (position.getX() < 50 || position.getX() > GameConfig.WINDOW_WIDTH - 100) {
            movingRight = !movingRight;
        }

        // Shooting logic
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime > GameConfig.SHOOT_COOLDOWN) {
            shoot();
            lastShootTime = currentTime;
        }
    }

    private void shoot() {
        GameManager manager = GameManager.getInstance();
        if (manager.getPlayer() == null) return;

        double playerX = manager.getPlayer().getX();
        double bulletVelocity = (playerX < getX()) ? -GameConfig.PROJECTILE_SPEED : GameConfig.PROJECTILE_SPEED;
        
        Projectile projectile = new Projectile(getX() + width / 2, getY() + height / 2, bulletVelocity);
        manager.addProjectile(projectile);
        com.miniplatformer.systems.SoundSystem.playShoot();
        GameLogger.logGameplay("Shooter fired projectile towards player");
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;

        double x = position.getX();
        double y = position.getY();
        
        // Body (Purple armor)
        gc.setFill(Color.PURPLE);
        gc.fillRect(x, y, width, height);
        
        // Helmet (Dark Gray)
        gc.setFill(Color.DARKSLATEGRAY);
        gc.fillRect(x - 2, y - 5, width + 4, 15);
        
        // Visor (Glowing Red)
        gc.setFill(Color.RED);
        if (GameManager.getInstance().getPlayer() != null && GameManager.getInstance().getPlayer().getX() > x) {
            gc.fillRect(x + width - 10, y + 2, 8, 4);
        } else {
            gc.fillRect(x + 2, y + 2, 8, 4);
        }
        
        // Cannon (Dark Gray)
        gc.setFill(Color.BLACK);
        if (GameManager.getInstance().getPlayer() != null && GameManager.getInstance().getPlayer().getX() > x) {
            gc.fillRect(x + width, y + height / 2 - 5, 10, 10);
        } else {
            gc.fillRect(x - 10, y + height / 2 - 5, 10, 10);
        }
    }

    @Override
    public String getType() { return "Shooter"; }

    @Override
    public boolean isActive() { return active; }

    @Override
    public void setActive(boolean active) { this.active = active; }

    @Override
    public double getX() { return position.getX(); }

    @Override
    public double getY() { return position.getY(); }

    @Override
    public double getWidth() { return width; }

    @Override
    public double getHeight() { return height; }

    @Override
    public void setVelocityX(double velocityX) { position.setVelocityX(velocityX); }

    @Override
    public void setVelocityY(double velocityY) { position.setVelocityY(velocityY); }

    @Override
    public double getVelocityX() { return position.getVelocityX(); }

    @Override
    public double getVelocityY() { return position.getVelocityY(); }
}
