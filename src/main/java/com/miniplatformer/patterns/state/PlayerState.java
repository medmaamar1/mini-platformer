package com.miniplatformer.patterns.state;

import com.miniplatformer.entities.Player;

/**
 * Interface for player states
 * Part of the State Pattern - defines the contract for all player states
 */
public interface PlayerState {
    /**
     * Handle input in this state
     */
    void handleInput(Player player, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed);

    /**
     * Update the player state (called each frame)
     */
    void update(Player player);

    /**
     * Get the name of this state
     */
    String getStateName();
}
