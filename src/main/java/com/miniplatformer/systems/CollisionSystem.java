package com.miniplatformer.systems;

import com.miniplatformer.core.GameManager;
import com.miniplatformer.entities.Entity;
import com.miniplatformer.entities.Player;
import com.miniplatformer.patterns.composite.GameComponent;
import com.miniplatformer.patterns.factory.Enemy;
import com.miniplatformer.utils.GameLogger;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Collision detection system
 * Handles collision detection between player, enemies, platforms, and collectibles
 */
public class CollisionSystem {

    /**
     * Check and resolve collisions between player and platforms
     * Includes bridge crossing fix - player falls when walking past platform edges
     */
    public static void checkPlatformCollisions(Player player, List<GameComponent> platforms) {
        boolean isOverAnyPlatform = false;
        
        for (GameComponent platform : platforms) {
            if (platform instanceof com.miniplatformer.patterns.composite.Platform) {
                com.miniplatformer.patterns.composite.Platform p = (com.miniplatformer.patterns.composite.Platform) platform;
                
                // Create a temporary entity for collision detection
                Entity platformEntity = new Entity(p.getX(), p.getY(), p.getWidth(), p.getHeight());
                
                if (player.collidesWith(platformEntity)) {
                    // Check if player is landing on top of platform
                    double playerBottom = player.getY() + player.getHeight();
                    double platformTop = p.getY();
                    double playerPrevBottom = playerBottom - player.getVelocityY();
                    
                    // If player was above the platform in the previous frame
                    if (playerPrevBottom <= platformTop + 10 && player.getVelocityY() >= 0) {
                        player.setY(platformTop - player.getHeight());
                        player.setVelocityY(0);
                        player.setOnGround(true);
                        isOverAnyPlatform = true;
                        
                        // Log collision
                        GameLogger.logGameplay("Player landed on platform");
                    }
                }
            }
        }
        
        // Bridge crossing fix: if player is on ground but not over any platform, make them fall
        if (player.isOnGround() && !isOverAnyPlatform) {
            player.setOnGround(false);
            GameLogger.logGameplay("Player fell off platform edge");
        }
    }

    /**
     * Check and resolve collisions between player and enemies
     */
    public static void checkEnemyCollisions(Player player, List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (!enemy.isActive()) continue;
            
            // Create a temporary entity for collision detection
            Entity enemyEntity = new Entity(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
            
            if (player.collidesWith(enemyEntity)) {
                // Check if player is jumping on top of enemy
                double playerBottom = player.getY() + player.getHeight();
                double enemyTop = enemy.getY();
                double playerVelocityY = player.getVelocityY();
                
                // Mario-style: jump on enemy to defeat it
                if (playerBottom < enemyTop + enemy.getHeight() / 2 && playerVelocityY > 0) {
                    // Player defeats enemy by jumping on it
                    enemy.setActive(false);
                    player.setVelocityY(-5); // Bounce up
                    SoundSystem.playEnemyDefeat();
                    GameLogger.logGameplay("Player defeated " + enemy.getType() + " enemy by jumping");
                    maybeDropPowerup(enemy.getX(), enemy.getY());
                } else if (player.getCharacter().hasWeapon()) {
                    // Player defeats enemy with weapon
                    enemy.setActive(false);
                    player.setVelocityY(-3); // Slight bounce for feedback
                    SoundSystem.playEnemyDefeat();
                    GameLogger.logGameplay("Player defeated " + enemy.getType() + " enemy with weapon");
                    maybeDropPowerup(enemy.getX(), enemy.getY());
                } else if (!player.getCharacter().isInvincible()) {
                    // Player takes damage
                    player.takeDamage();
                    GameLogger.logGameplay("Player hit by " + enemy.getType());
                }
            }
        }
    }

    /**
     * Check and resolve collisions between player and collectibles
     */
    public static void checkCollectibleCollisions(Player player, List<GameComponent> collectibles) {
        for (GameComponent collectible : collectibles) {
            if (collectible instanceof com.miniplatformer.patterns.composite.Collectible) {
                com.miniplatformer.patterns.composite.Collectible c =
                    (com.miniplatformer.patterns.composite.Collectible) collectible;
                
                if (c.isActive() && player.collidesWith(c.getEntity())) {
                    c.collect();
                    GameLogger.logGameplay("Player collected " + c.getType());
                    
                    // Apply power-up effect to player character
                    if (!c.getType().equals("coin")) {
                        player.setCharacter(c.applyToCharacter(player.getCharacter()));
                        SoundSystem.playPowerup();
                    }
                    
                    // Add score for coins
                    if (c.getType().equals("coin")) {
                        GameManager.getInstance().addScore(c.getValue());
                        SoundSystem.playCoin();
                    }
                }
            }
        }
    }

    /**
     * Check if player is out of bounds
     */
    public static boolean checkOutOfBounds(Player player, double screenWidth, double screenHeight) {
        return player.getX() < -player.getWidth() || 
               player.getX() > screenWidth ||
               player.getY() > screenHeight;
    }

    /**
     * Get collision rectangle for an entity
     */
    public static Rectangle getCollisionBounds(Entity entity) {
        return new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
    }

    /**
     * Check and resolve collisions between player and obstacles (spikes, fire)
     */
    public static void checkObstacleCollisions(Player player, List<com.miniplatformer.patterns.composite.GameComponent> components) {
        for (com.miniplatformer.patterns.composite.GameComponent component : components) {
            if (component instanceof com.miniplatformer.patterns.composite.Obstacle) {
                com.miniplatformer.patterns.composite.Obstacle obstacle = (com.miniplatformer.patterns.composite.Obstacle) component;
                
                if (obstacle.isActive() && player.collidesWith(obstacle.getEntity())) {
                    if (!player.getCharacter().isInvincible()) {
                        player.takeDamage();
                        GameLogger.logGameplay("Player hit by " + obstacle.getType());
                    }
                }
            }
        }
    }

    /**
     * Check and resolve collisions between player and projectiles
     */
    public static void checkProjectileCollisions(Player player, List<com.miniplatformer.entities.Projectile> projectiles) {
        for (com.miniplatformer.entities.Projectile projectile : projectiles) {
            if (projectile.isActive() && player.collidesWith(projectile)) {
                if (!player.getCharacter().isInvincible()) {
                    player.takeDamage();
                    projectile.setActive(false);
                    GameLogger.logGameplay("Player hit by projectile");
                }
            }
        }
    }

    /**
     * Chance to drop a power-up when an enemy is defeated
     */
    private static void maybeDropPowerup(double x, double y) {
        java.util.Random rand = new java.util.Random();
        if (rand.nextDouble() < 0.3) { // 30% chance
            String[] powerTypes = {"weapon", "shield", "doubleJump", "speedBoost", "invincibility", "coin"};
            String type = powerTypes[rand.nextInt(powerTypes.length)];
            GameManager.getInstance().getCurrentLevel().add(
                new com.miniplatformer.patterns.composite.Collectible(x, y, type)
            );
            GameLogger.info("Enemy dropped a " + type + "!");
        }
    }

    /**
     * Check if two rectangles intersect
     */
    public static boolean rectanglesIntersect(Rectangle r1, Rectangle r2) {
        return r1.getBoundsInParent().intersects(r2.getBoundsInParent());
    }
}
