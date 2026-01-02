package com.miniplatformer.patterns.decorator;

/**
 * Shield Decorator - provides one-hit protection
 * Part of the Decorator Pattern
 */
public class ShieldDecorator extends PowerUpDecorator {
    private boolean shieldActive = true;

    public ShieldDecorator(Character character) {
        super(character);
    }

    @Override
    public boolean hasShield() {
        return shieldActive || decoratedCharacter.hasShield();
    }

    @Override
    public void removeShield() {
        if (shieldActive) {
            shieldActive = false;
            com.miniplatformer.utils.GameLogger.logDecoratorRemoval("Shield", decoratedCharacter.getName());
        } else {
            decoratedCharacter.removeShield();
        }
    }

    @Override
    public String getName() {
        return "Shield(" + decoratedCharacter.getName() + ")";
    }
}
