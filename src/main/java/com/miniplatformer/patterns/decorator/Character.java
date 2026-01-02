package com.miniplatformer.patterns.decorator;

/**
 * Character interface for the Decorator Pattern
 * Defines the contract for player character with power-ups
 */
public interface Character {
    /**
     * Get the movement speed multiplier
     */
    double getSpeed();

    /**
     * Check if character has a shield
     */
    boolean hasShield();

    /**
     * Remove the shield
     */
    void removeShield();

    /**
     * Check if character has a weapon
     */
    boolean hasWeapon();

    /**
     * Check if character can double jump
     */
    boolean canDoubleJump();

    /**
     * Check if character is invincible
     */
    boolean isInvincible();

    /**
     * Get the name of this character (for logging)
     */
    String getName();
}
