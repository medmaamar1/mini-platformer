package com.miniplatformer.patterns.state;

import com.miniplatformer.entities.Player;

/**
 * Attacking State - player is attacking
 * Part of the State Pattern for player behavior
 */
public class AttackingState implements PlayerState {
    private static final String STATE_NAME = "ATTACKING";
    private long attackStartTime;
    private static final long ATTACK_DURATION = 500; // 500ms attack duration

    public AttackingState() {
        this.attackStartTime = System.currentTimeMillis();
    }

    @Override
    public void handleInput(Player player, boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed) {
        // During attack, movement is limited
        if (leftPressed) {
            player.moveLeft();
        } else if (rightPressed) {
            player.moveRight();
        } else {
            player.stopMoving();
        }

        if (jumpPressed) {
            player.jump();
            player.setState(new JumpingState());
        }
    }

    @Override
    public void update(Player player) {
        // Check if attack animation is complete
        if (System.currentTimeMillis() - attackStartTime > ATTACK_DURATION) {
            if (player.isOnGround()) {
                if (Math.abs(player.getVelocityX()) > 0) {
                    player.setState(new RunningState());
                } else {
                    player.setState(new IdleState());
                }
            } else {
                player.setState(new JumpingState());
            }
        }
    }

    @Override
    public String getStateName() {
        return STATE_NAME;
    }
}
