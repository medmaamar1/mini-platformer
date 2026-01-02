package com.miniplatformer.patterns.decorator;

/**
 * Speed Boost Decorator - doubles player speed
 * Part of the Decorator Pattern
 */
public class SpeedBoostDecorator extends PowerUpDecorator {
    private static final double SPEED_MULTIPLIER = 2.0;

    public SpeedBoostDecorator(Character character) {
        super(character);
    }

    @Override
    public double getSpeed() {
        return decoratedCharacter.getSpeed() * SPEED_MULTIPLIER;
    }

    @Override
    public String getName() {
        return "SpeedBoost(" + decoratedCharacter.getName() + ")";
    }
}
