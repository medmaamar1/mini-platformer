package com.miniplatformer.patterns.decorator;

/**
 * Double Jump Decorator - allows jumping while in air
 * Part of the Decorator Pattern
 */
public class DoubleJumpDecorator extends PowerUpDecorator {
    public DoubleJumpDecorator(Character character) {
        super(character);
    }

    @Override
    public boolean canDoubleJump() {
        return true;
    }

    @Override
    public String getName() {
        return "DoubleJump(" + decoratedCharacter.getName() + ")";
    }
}
