package com.miniplatformer.patterns.state;

import com.miniplatformer.entities.Player;

/**
 * Dead State - player has died
 * Part of the State Pattern for player behavior
 */
public class DeadState implements PlayerState {
    private static final String STATE_NAME = "DEAD";

    @Override
    public void handleInput(Player player, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed) {
        // Player is dead, no input handling
    }

    @Override
    public void update(Player player) {
        // Player is dead, no updates
    }

    @Override
    public String getStateName() {
        return STATE_NAME;
    }
}
