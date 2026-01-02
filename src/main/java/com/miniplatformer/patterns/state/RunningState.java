package com.miniplatformer.patterns.state;

import com.miniplatformer.entities.Player;

/**
 * Running State - player is moving horizontally
 * Part of the State Pattern for player behavior
 */
public class RunningState implements PlayerState {
    private static final String STATE_NAME = "RUNNING";

    @Override
    public void handleInput(Player player, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed) {
        if (jumpPressed) {
            player.jump();
            player.setState(new JumpingState());
        } else if (!leftPressed && !rightPressed) {
            player.stopMoving();
            player.setState(new IdleState());
        } else if (leftPressed) {
            player.moveLeft();
        } else if (rightPressed) {
            player.moveRight();
        } else if (attackPressed) {
            player.attack();
            player.setState(new AttackingState());
        }
    }

    @Override
    public void update(Player player) {
        // Continue moving based on current velocity
    }

    @Override
    public String getStateName() {
        return STATE_NAME;
    }
}
