package com.miniplatformer.config;

/**
 * Game configuration constants
 * Contains all configurable parameters for the game
 */
public class GameConfig {
    // Window settings
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final String WINDOW_TITLE = "Mini Platformer - Design Patterns Project";

    // Game settings
    public static final int FPS = 60;
    public static final double GRAVITY = 0.5;
    public static final double JUMP_FORCE = -12;
    public static final double MOVE_SPEED = 5;
    public static final int MAX_LIVES = 3;

    // Player settings
    public static final int PLAYER_WIDTH = 32;
    public static final int PLAYER_HEIGHT = 48;
    public static final int PLAYER_START_X = 100;
    public static final int PLAYER_START_Y = 400;

    // Enemy settings
    public static final int ENEMY_WIDTH = 32;
    public static final int ENEMY_HEIGHT = 32;
    public static final double ENEMY_SPEED = 2;

    // Power-up settings
    public static final int POWERUP_WIDTH = 24;
    public static final int POWERUP_HEIGHT = 24;
    public static final int POWERUP_DURATION = 10000; // 10 seconds in milliseconds

    // Collectible settings
    public static final int COIN_WIDTH = 16;
    public static final int COIN_HEIGHT = 16;
    public static final int COIN_VALUE = 10;
    public static final int COLLECTIBLE_SIZE = 20;

    // Obstacle settings
    public static final int OBSTACLE_WIDTH = 32;
    public static final int OBSTACLE_HEIGHT = 32;

    // Colors
    public static final String COLOR_PLAYER = "#FF6B6B";
    public static final String COLOR_ENEMY = "#8B4513";
    public static final String COLOR_PLATFORM = "#4ECDC4";
    public static final String COLOR_COIN = "#FFE66D";
    public static final String COLOR_MUSHROOM = "#FF8C00";
    public static final String COLOR_STAR = "#FFD700";
    public static final String COLOR_FLOWER = "#FF69B4";
    public static final String COLOR_SHIELD = "#00BFFF";
    public static final String COLOR_SPIKE = "#708090"; // SlateGray
    public static final String COLOR_FIRE = "#FF4500";  // OrangeRed
    public static final String COLOR_PROJECTILE = "#FFD700"; // Gold
    public static final String COLOR_BACKGROUND = "#1A1A2E";
    
    // Projectile settings
    public static final int PROJECTILE_WIDTH = 8;
    public static final int PROJECTILE_HEIGHT = 8;
    public static final double PROJECTILE_SPEED = 4;
    public static final long SHOOT_COOLDOWN = 2000; // 2 seconds
    public static final String COLOR_POWERUP_SPEED = "#00FF00";
    public static final String COLOR_POWERUP_SHIELD = "#00BFFF";
    public static final String COLOR_POWERUP_WEAPON = "#FF0000";
    public static final String COLOR_POWERUP_DOUBLEJUMP = "#FF00FF";
    public static final String COLOR_POWERUP_INVINCIBILITY = "#FFD700";
}
