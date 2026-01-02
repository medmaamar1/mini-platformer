package com.miniplatformer.patterns.state;

import com.miniplatformer.core.GameManager;
import com.miniplatformer.entities.Player;
import com.miniplatformer.entities.Projectile;
import com.miniplatformer.systems.CollisionSystem;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Playing State - active gameplay
 * Part of State Pattern
 */
public class PlayingState implements GameState {
    private static final String STATE_NAME = "PLAYING";

    @Override
    public void enter(GameManager manager) {
        GameLogger.logState("Game", "MENU", "PLAYING");
        GameLogger.info("Game started");
    }

    @Override
    public void exit(GameManager manager) {
        GameLogger.info("Exited Playing State");
    }

    @Override
    public void update(GameManager manager) {
        // Update player
        if (manager.getPlayer() != null) {
            manager.getPlayer().update();
        }

        // Update enemies
        for (var enemy : manager.getEnemies()) {
            enemy.update();
        }

        // Update level
        if (manager.getCurrentLevel() != null) {
            manager.getCurrentLevel().update();
        }

        // Update projectiles
        for (Projectile projectile : manager.getProjectiles()) {
            projectile.update();
        }

        // Check collisions
        if (manager.getPlayer() != null && manager.getCurrentLevel() != null) {
            Player player = manager.getPlayer();
            int livesBefore = player.getLives();
            
            CollisionSystem.checkPlatformCollisions(player, manager.getCurrentLevel().getComponents());
            CollisionSystem.checkEnemyCollisions(player, manager.getEnemies());
            CollisionSystem.checkCollectibleCollisions(player, manager.getCurrentLevel().getComponents());
            CollisionSystem.checkObstacleCollisions(player, manager.getCurrentLevel().getComponents());
            CollisionSystem.checkProjectileCollisions(player, manager.getProjectiles());

            // If player took damage from enemy or obstacle (lives decreased), respawn if alive
            if (player.getLives() < livesBefore) {
                if (player.getLives() <= 0) {
                    manager.gameOver();
                } else {
                    player.respawn();
                }
            }
        }

        // Check if player is out of bounds
        if (manager.getPlayer() != null && CollisionSystem.checkOutOfBounds(manager.getPlayer(), 800, 600)) {
            manager.getPlayer().takeDamage();
            if (manager.getPlayer().getLives() <= 0) {
                manager.gameOver();
            } else {
                manager.getPlayer().respawn();
            }
        }

        // Check for victory condition (all enemies defeated)
        boolean allEnemiesDefeated = true;
        for (var enemy : manager.getEnemies()) {
            if (enemy.isActive()) {
                allEnemiesDefeated = false;
                break;
            }
        }
        if (allEnemiesDefeated) {
            manager.victory();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        // Render level
        if (GameManager.getInstance().getCurrentLevel() != null) {
            GameManager.getInstance().getCurrentLevel().render(gc);
        }

        // Render enemies
        for (var enemy : GameManager.getInstance().getEnemies()) {
            enemy.render(gc);
        }

        // Render player
        if (GameManager.getInstance().getPlayer() != null) {
            GameManager.getInstance().getPlayer().render(gc);
        }

        // Render projectiles
        for (Projectile projectile : GameManager.getInstance().getProjectiles()) {
            projectile.render(gc);
        }

        // Render HUD
        renderHUD(gc);
    }

    private void renderHUD(GraphicsContext gc) {
        GameManager manager = GameManager.getInstance();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // Score
        gc.fillText("Score: " + manager.getScore(), 10, 25);
        
        // Lives
        gc.fillText("Lives: " + manager.getLives(), 10, 50);
        
        // Time
        long seconds = manager.getElapsedTime() / 1000;
        gc.fillText("Time: " + seconds + "s", 10, 75);
        
        // Power-ups
        if (manager.getPlayer() != null) {
            StringBuilder powerups = new StringBuilder("Power-ups: ");
            if (manager.getPlayer().getCharacter().getSpeed() > 1.0) {
                powerups.append("Speed ");
            }
            if (manager.getPlayer().getCharacter().hasShield()) {
                powerups.append("Shield ");
            }
            if (manager.getPlayer().getCharacter().hasWeapon()) {
                powerups.append("Weapon ");
            }
            if (manager.getPlayer().getCharacter().canDoubleJump()) {
                powerups.append("DoubleJump ");
            }
            if (manager.getPlayer().getCharacter().isInvincible()) {
                powerups.append("Invincible ");
            }
            gc.setFont(Font.font("Arial", 12));
            gc.fillText(powerups.toString(), 10, 100);
        }
    }

    @Override
    public void handleInput(GameManager manager, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed, boolean escapePressed) {
        // Handle pause
        if (escapePressed) {
            manager.pauseGame();
            return;
        }

        // Handle player input
        if (manager.getPlayer() != null) {
            manager.getPlayer().handleInput(leftPressed, rightPressed, jumpPressed, attackPressed);
        }
    }

    public String getStateName() {
        return STATE_NAME;
    }
}
