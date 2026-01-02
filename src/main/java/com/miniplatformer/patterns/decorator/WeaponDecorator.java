package com.miniplatformer.patterns.decorator;

/**
 * Weapon Decorator - enables attack capability
 * Part of the Decorator Pattern
 */
public class WeaponDecorator extends PowerUpDecorator {
    public WeaponDecorator(Character character) {
        super(character);
    }

    @Override
    public boolean hasWeapon() {
        return true;
    }

    @Override
    public String getName() {
        return "Weapon(" + decoratedCharacter.getName() + ")";
    }
}
