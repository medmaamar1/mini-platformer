package com.miniplatformer.patterns.decorator;

import com.miniplatformer.utils.GameLogger;

/**
 * Abstract decorator for power-ups
 * Part of the Decorator Pattern
 */
public abstract class PowerUpDecorator implements Character {
    protected Character decoratedCharacter;

    public PowerUpDecorator(Character character) {
        this.decoratedCharacter = character;
        GameLogger.logDecorator(getClass().getSimpleName(), character.getName());
    }

    @Override
    public double getSpeed() {
        return decoratedCharacter.getSpeed();
    }

    @Override
    public boolean hasShield() {
        return decoratedCharacter.hasShield();
    }

    @Override
    public void removeShield() {
        decoratedCharacter.removeShield();
    }

    @Override
    public boolean hasWeapon() {
        return decoratedCharacter.hasWeapon();
    }

    @Override
    public boolean canDoubleJump() {
        return decoratedCharacter.canDoubleJump();
    }

    @Override
    public boolean isInvincible() {
        return decoratedCharacter.isInvincible();
    }

    @Override
    public String getName() {
        return decoratedCharacter.getName();
    }
}
