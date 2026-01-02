package com.miniplatformer.patterns.factory;

import com.miniplatformer.utils.GameLogger;

/**
 * Enemy Factory - creates different enemy types
 * Part of Factory Pattern
 */
public class EnemyFactory {
    /**
     * Create an enemy of the specified type
     * @param type The type of enemy to create
     * @param x X position
     * @param y Y position
     * @return Enemy instance
     */
    public static Enemy createEnemy(String type, double x, double y) {
        switch (type.toLowerCase()) {
            case "goomba":
                return new Goomba(x, y);
            case "koopa":
                return new Koopa(x, y);
            case "flying":
                return new FlyingEnemy(x, y);
            case "shooter":
                return new Shooter(x, y);
            default:
                GameLogger.warn("Unknown enemy type: " + type + ", creating Goomba instead");
                return new Goomba(x, y);
        }
    }
}
