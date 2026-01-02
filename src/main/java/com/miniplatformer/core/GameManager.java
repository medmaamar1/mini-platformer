package com.miniplatformer.core;

import com.miniplatformer.config.GameConfig;
import com.miniplatformer.entities.Player;
import com.miniplatformer.patterns.composite.Level;
import com.miniplatformer.patterns.factory.Enemy;
import com.miniplatformer.patterns.factory.EnemyFactory;
import com.miniplatformer.patterns.state.*;
import com.miniplatformer.entities.Projectile;
import com.miniplatformer.utils.GameLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Game Manager - Singleton Pattern
 * Manages the overall game state and game data
 */
public class GameManager {
    private static GameManager instance;
    
    private GameState currentState;
    private Player player;
    private Level currentLevel;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;
    private int score;
    private int lives;
    private long startTime;
    private long elapsedTime;
    
    private GameManager() {
        this.score = 0;
        this.lives = GameConfig.MAX_LIVES;
        this.enemies = new ArrayList<>();
        this.projectiles = new ArrayList<>();
        this.currentState = new MenuState();
        GameLogger.info("GameManager initialized");
    }
    
    /**
     * Get the singleton instance
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    
    /**
     * Start a new game
     */
    public void startGame() {
        GameLogger.info("Game started");
        this.score = 0;
        this.lives = GameConfig.MAX_LIVES;
        this.startTime = System.currentTimeMillis();
        this.elapsedTime = 0;
        this.projectiles = new ArrayList<>();
        
        // Create player
        this.player = new Player(GameConfig.PLAYER_START_X, GameConfig.PLAYER_START_Y);
        
        // Create level
        this.currentLevel = createLevel1();
        
        // Create enemies - Distributed evenly across the level
        this.enemies = new ArrayList<>();
        enemies.add(EnemyFactory.createEnemy("goomba", 350, 518));
        enemies.add(EnemyFactory.createEnemy("koopa", 550, 518));
        enemies.add(EnemyFactory.createEnemy("shooter", 650, 418)); // Moved from 100
        enemies.add(EnemyFactory.createEnemy("flying", 400, 250));
        enemies.add(EnemyFactory.createEnemy("shooter", 750, 418));
        enemies.add(EnemyFactory.createEnemy("goomba", 150, 518)); // Safe Goomba near start but on ground
        
        // Set initial state
        setState(new PlayingState());
    }
    /**
     * Create level 1 - Enhanced with more complexity
     */
    private Level createLevel1() {
        Level level = new Level("Level 1", GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        
        // Add ground platforms with gaps for challenge
        level.add(new com.miniplatformer.patterns.composite.Platform(0, 550, 200, 50, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(250, 550, 150, 50, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(450, 550, 350, 50, GameConfig.COLOR_PLATFORM));
        
        // Add multiple layers of floating platforms
        level.add(new com.miniplatformer.patterns.composite.Platform(50, 450, 120, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(200, 450, 100, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(350, 450, 120, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(550, 450, 100, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(700, 450, 100, 20, GameConfig.COLOR_PLATFORM));
        
        level.add(new com.miniplatformer.patterns.composite.Platform(100, 380, 150, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(300, 380, 100, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(450, 380, 150, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(650, 380, 100, 20, GameConfig.COLOR_PLATFORM));
        
        level.add(new com.miniplatformer.patterns.composite.Platform(50, 300, 100, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(200, 300, 120, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(400, 300, 100, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(550, 300, 120, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(700, 300, 100, 20, GameConfig.COLOR_PLATFORM));
        
        level.add(new com.miniplatformer.patterns.composite.Platform(150, 220, 100, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(300, 220, 120, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(500, 220, 100, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(650, 220, 120, 20, GameConfig.COLOR_PLATFORM));
        
        level.add(new com.miniplatformer.patterns.composite.Platform(100, 150, 80, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(250, 150, 80, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(400, 150, 80, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(550, 150, 80, 20, GameConfig.COLOR_PLATFORM));
        level.add(new com.miniplatformer.patterns.composite.Platform(700, 150, 80, 20, GameConfig.COLOR_PLATFORM));
        
        // Add coins scattered throughout the level
        addCoinsToLevel(level);
        
        // Add random power-ups
        addRandomPowerups(level);
        
        // Add obstacles (Spikes and Fire)
        level.add(new com.miniplatformer.patterns.composite.Obstacle(210, 568, "spike"));
        level.add(new com.miniplatformer.patterns.composite.Obstacle(410, 568, "spike"));
        level.add(new com.miniplatformer.patterns.composite.Obstacle(250, 420, "fire"));
        level.add(new com.miniplatformer.patterns.composite.Obstacle(600, 350, "fire"));
        level.add(new com.miniplatformer.patterns.composite.Obstacle(450, 120, "fire"));
        
        GameLogger.info("Level 1 created with random power-ups and enhanced design");
        return level;
    }

    private void addCoinsToLevel(Level level) {
        // Simple helper to add coins in clusters or rows
        double[][] coinCoords = {
            {100, 520}, {150, 520}, {300, 520}, {500, 520}, {600, 520}, {700, 520},
            {100, 420}, {200, 420}, {350, 420}, {550, 420}, {700, 420},
            {150, 350}, {300, 350}, {450, 350}, {650, 350},
            {100, 270}, {250, 270}, {400, 270}, {550, 270}, {700, 270},
            {150, 190}, {300, 190}, {500, 190}, {650, 190},
            {100, 120}, {250, 120}, {400, 120}, {550, 120}, {700, 120}
        };
        for (double[] coord : coinCoords) {
            level.add(new com.miniplatformer.patterns.composite.Collectible(coord[0], coord[1], "coin"));
        }
    }

    private void addRandomPowerups(Level level) {
        String[] powerTypes = {"weapon", "weapon", "shield", "doubleJump", "speedBoost", "invincibility"};
        java.util.Random rand = new java.util.Random();
        
        // Extract platforms to pick from
        List<com.miniplatformer.patterns.composite.Platform> platforms = new ArrayList<>();
        for (com.miniplatformer.patterns.composite.GameComponent comp : level.getComponents()) {
            if (comp instanceof com.miniplatformer.patterns.composite.Platform) {
                platforms.add((com.miniplatformer.patterns.composite.Platform) comp);
            }
        }

        // Spawn 4-6 random power-ups
        int numPowerups = 4 + rand.nextInt(3);
        java.util.Collections.shuffle(platforms);

        for (int i = 0; i < Math.min(numPowerups, platforms.size()); i++) {
            com.miniplatformer.patterns.composite.Platform p = platforms.get(i);
            String type = powerTypes[rand.nextInt(powerTypes.length)];
            
            // Random position on platform
            double itemX = p.getX() + rand.nextDouble() * (p.getWidth() - 20);
            double itemY = p.getY() - 30;
            
            level.add(new com.miniplatformer.patterns.composite.Collectible(itemX, itemY, type));
            GameLogger.info("Spawned random power-up: " + type + " at (" + itemX + ", " + itemY + ")");
        }
    }
    
    /**
     * Pause the game
     */
    public void pauseGame() {
        if (currentState instanceof PlayingState) {
            setState(new PauseState());
            GameLogger.logState("Game", "PLAYING", "PAUSE");
        }
    }
    
    /**
     * Resume the game
     */
    public void resumeGame() {
        if (currentState instanceof PauseState) {
            setState(new PlayingState());
            GameLogger.logState("Game", "PAUSE", "PLAYING");
        }
    }
    
    /**
     * Game over
     */
    public void gameOver() {
        setState(new GameOverState(score));
        GameLogger.logState("Game", "PLAYING", "GAME_OVER");
        GameLogger.info("Final score: " + score);
    }
    
    /**
     * Victory
     */
    public void victory() {
        setState(new VictoryState(score, elapsedTime));
        GameLogger.logState("Game", "PLAYING", "VICTORY");
        GameLogger.info("Victory! Final score: " + score + ", Time: " + (elapsedTime / 1000) + "s");
    }
    
    /**
     * Update the game
     */
    public void update() {
        elapsedTime = System.currentTimeMillis() - startTime;
        
        if (currentState != null) {
            currentState.update(this);
        }
    }
    
    /**
     * Set the current game state
     */
    public void setState(GameState newState) {
        if (currentState != null) {
            currentState.exit(this);
        }
        this.currentState = newState;
        currentState.enter(this);
    }
    
    /**
     * Handle input
     */
    public void handleInput(boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed, boolean escapePressed) {
        if (currentState != null) {
            currentState.handleInput(this, leftPressed, rightPressed, jumpPressed, attackPressed, escapePressed);
        }
    }
    
    // Getters and setters
    public GameState getCurrentState() {
        return currentState;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Level getCurrentLevel() {
        return currentLevel;
    }
    
    public List<Enemy> getEnemies() {
        return enemies;
    }
    
    public List<Projectile> getProjectiles() {
        return projectiles;
    }
    
    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void addScore(int points) {
        this.score += points;
        GameLogger.logGameplay("Score increased by " + points + ". Total: " + score);
    }
    
    public int getLives() {
        if (player != null) {
            return player.getLives();
        }
        return lives;
    }
    
    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public long getElapsedTime() {
        return elapsedTime;
    }
    
    /**
     * Reset the singleton instance
     */
    public static void reset() {
        instance = null;
    }
}
