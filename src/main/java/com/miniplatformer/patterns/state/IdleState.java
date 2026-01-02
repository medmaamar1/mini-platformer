package com.miniplatformer.patterns.state;

import com.miniplatformer.entities.Player;
import com.miniplatformer.patterns.state.*;

/**
 * Idle State - player is standing still
 * Part of the State Pattern for player behavior
 */
public class IdleState implements PlayerState {
    private static final String STATE_NAME = "IDLE";

    @Override
    public void handleInput(Player player, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed) {
        if (jumpPressed) {
            player.jump();
            player.setState(new JumpingState());
        } else if (leftPressed) {
            player.moveLeft();
            player.setState(new RunningState());
        } else if (rightPressed) {
            player.moveRight();
            player.setState(new RunningState());
        } else if (attackPressed) {
            player.attack();
            player.setState(new AttackingState());
        }
    }

    @Override
    public void update(Player player) {
        // Player is idle, no movement
        player.stopMoving();
    }

    @Override
    public String getStateName() {
        return STATE_NAME;
    }
}
