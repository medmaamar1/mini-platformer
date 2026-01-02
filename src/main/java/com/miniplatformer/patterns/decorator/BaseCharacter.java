package com.miniplatformer.patterns.decorator;

/**
 * Base character implementation
 * Default player without any power-ups
 */
public class BaseCharacter implements Character {
    private static final String NAME = "BasePlayer";

    @Override
    public double getSpeed() {
        return 1.0; // Normal speed
    }

    @Override
    public boolean hasShield() {
        return false;
    }

    @Override
    public void removeShield() {
        // No shield to remove
    }

    @Override
    public boolean hasWeapon() {
        return false;
    }

    @Override
    public boolean canDoubleJump() {
        return false;
    }

    @Override
    public boolean isInvincible() {
        return false;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
