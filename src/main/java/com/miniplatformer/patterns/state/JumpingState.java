package com.miniplatformer.patterns.state;

import com.miniplatformer.entities.Player;

/**
 * Jumping State - player is in the air
 * Part of the State Pattern for player behavior
 */
public class JumpingState implements PlayerState {
    private static final String STATE_NAME = "JUMPING";

    @Override
    public void handleInput(Player player, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed) {
        if (leftPressed) {
            player.moveLeft();
        } else if (rightPressed) {
            player.moveRight();
        } else {
            player.stopMoving();
        }

        if (jumpPressed && player.getCharacter().canDoubleJump()) {
            player.jump();
        }

        if (attackPressed) {
            player.attack();
            player.setState(new AttackingState());
        }
    }

    @Override
    public void update(Player player) {
        // Check if player has landed
        if (player.isOnGround()) {
            if (Math.abs(player.getVelocityX()) > 0) {
                player.setState(new RunningState());
            } else {
                player.setState(new IdleState());
            }
        }
    }

    @Override
    public String getStateName() {
        return STATE_NAME;
    }
}
