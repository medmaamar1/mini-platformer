package com.miniplatformer.patterns.decorator;

/**
 * Invincibility Decorator - temporary invincibility (star power)
 * Part of the Decorator Pattern
 */
public class InvincibilityDecorator extends PowerUpDecorator {
    private long startTime;
    private long duration;

    public InvincibilityDecorator(Character character, long duration) {
        super(character);
        this.startTime = System.currentTimeMillis();
        this.duration = duration;
    }

    @Override
    public boolean isInvincible() {
        if (System.currentTimeMillis() - startTime < duration) {
            return true;
        }
        // Duration expired, check nested decorators
        return decoratedCharacter.isInvincible();
    }

    @Override
    public String getName() {
        return "Invincibility(" + decoratedCharacter.getName() + ")";
    }
}
