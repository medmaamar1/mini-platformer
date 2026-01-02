package com.miniplatformer.entities;

import com.miniplatformer.config.GameConfig;
import com.miniplatformer.patterns.decorator.BaseCharacter;
import com.miniplatformer.patterns.decorator.Character;
import com.miniplatformer.patterns.state.IdleState;
import com.miniplatformer.patterns.state.PlayerState;
import com.miniplatformer.utils.GameLogger;
import com.miniplatformer.systems.SoundSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Player entity class
 * Uses State Pattern for player behavior and Decorator Pattern for power-ups
 */
public class Player extends Entity {
    private Character character;
    private PlayerState currentState;
    private int lives;
    private boolean onGround;
    private boolean facingRight;
    private long jumpStartTime;
    private long lastDamageTime;
    private long animationStartTime;
    private static final long DAMAGE_COOLDOWN = 1000; // 1 second cooldown
    private static final double ANIM_SPEED = 0.01;

    public Player(double x, double y) {
        super(x, y, GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);
        this.character = new BaseCharacter();
        this.currentState = new IdleState();
        this.lives = GameConfig.MAX_LIVES;
        this.onGround = false;
        this.facingRight = true;
        this.lastDamageTime = 0;
        this.animationStartTime = System.currentTimeMillis();
        GameLogger.info("Player created at position (" + x + ", " + y + ")");
    }

    @Override
    public void update() {
        // Apply gravity
        if (!onGround) {
            setVelocityY(getVelocityY() + GameConfig.GRAVITY);
        }

        // Update position
        position.updatePosition();

        // Check horizontal boundaries - force player to fall if past border
        if (getX() < 0 || getX() + width > GameConfig.WINDOW_WIDTH) {
            // Push player back to screen edge and make them fall
            if (getX() < 0) {
                setX(0);
            } else if (getX() + width > GameConfig.WINDOW_WIDTH) {
                setX(GameConfig.WINDOW_WIDTH - width);
            }
            // Force player to fall by setting onGround to false
            if (onGround) {
                onGround = false;
                GameLogger.logGameplay("Player fell off platform at border");
            }
        }

        // Update current state
        currentState.update(this);
    }

    @Override
    public void render(GraphicsContext gc) {
        double x = getX();
        double y = getY();
        long time = System.currentTimeMillis() - animationStartTime;
        
        // Calculate animation offsets
        double bobOffset = 0;
        double legOffset = 0;
        double stretchY = 0;
        double squashX = 0;
        
        if (onGround) {
            if (Math.abs(getVelocityX()) > 0.1) {
                // Walking animation
                legOffset = Math.sin(time * 0.015) * 10;
                bobOffset = Math.abs(Math.cos(time * 0.015)) * 4;
            } else {
                // Idle animation
                bobOffset = Math.sin(time * 0.003) * 2;
            }
        } else {
            // Jumping/Falling animation
            if (getVelocityY() < 0) {
                stretchY = 5; // Stretch when going up
                squashX = -2;
            } else {
                stretchY = -3; // Squash slightly when falling
                squashX = 2;
            }
        }

        // Draw player body
        gc.setFill(Color.web(GameConfig.COLOR_PLAYER));
        gc.fillRect(x + squashX, y + bobOffset - stretchY, width - squashX * 2, height + stretchY - bobOffset);
        
        // Draw head
        double headY = y + 2 + bobOffset * 0.5 - stretchY * 0.2;
        gc.setFill(Color.web("#FFE4C4")); // Skin color
        gc.fillRect(x + 4 + squashX, headY, width - 8 - squashX * 2, 14);
        
        // Draw eyes
        gc.setFill(Color.WHITE);
        double eyeY = headY + 4;
        if (facingRight) {
            gc.fillOval(x + 16, eyeY, 6, 6);
            gc.fillOval(x + 24, eyeY, 6, 6);
            gc.setFill(Color.BLACK);
            gc.fillOval(x + 18, eyeY + 2, 3, 3);
            gc.fillOval(x + 26, eyeY + 2, 3, 3);
        } else {
            gc.fillOval(x + 2, eyeY, 6, 6);
            gc.fillOval(x + 10, eyeY, 6, 6);
            gc.setFill(Color.BLACK);
            gc.fillOval(x + 3, eyeY + 2, 3, 3);
            gc.fillOval(x + 11, eyeY + 2, 3, 3);
        }
        
        // Draw shirt details
        gc.setFill(Color.web("#4169E1"));
        gc.fillRect(x + 4 + squashX, y + 16 + bobOffset * 0.7, width - 8 - squashX * 2, 12);
        
        // Draw pants
        gc.setFill(Color.web("#2F4F4F"));
        gc.fillRect(x + 4 + squashX, y + 28 + bobOffset * 0.9, width - 8 - squashX * 2, 12);
        
        // Draw shoes with walking animation
        gc.setFill(Color.web("#8B4513"));
        if (onGround && Math.abs(getVelocityX()) > 0.1) {
            // Animated legs
            gc.fillRect(x + 2 + legOffset, y + 40 + bobOffset, 10, 8);
            gc.fillRect(x + width - 12 - legOffset, y + 40 + bobOffset, 10, 8);
        } else {
            // Static legs
            gc.fillRect(x + 2, y + 40 + bobOffset, 10, 8);
            gc.fillRect(x + width - 12, y + 40 + bobOffset, 10, 8);
        }
        
        // Draw hands
        gc.setFill(Color.web("#FFE4C4")); // Skin color
        double handX1, handX2, handY1, handY2;
        
        if (!onGround) {
            // Jumping/Falling hands
            handY1 = y + 15 + bobOffset - stretchY;
            handY2 = y + 15 + bobOffset - stretchY;
            handX1 = x - 4 + squashX;
            handX2 = x + width - 4 - squashX;
        } else if (Math.abs(getVelocityX()) > 0.1) {
            // Walking hands (swinging)
            double armSwing = Math.sin(time * 0.015) * 8;
            handY1 = y + 20 + bobOffset + armSwing;
            handY2 = y + 20 + bobOffset - armSwing;
            handX1 = x - 2 + squashX;
            handX2 = x + width - 6 - squashX;
        } else {
            // Idle hands
            handY1 = y + 22 + bobOffset;
            handY2 = y + 22 + bobOffset;
            handX1 = x - 2;
            handX2 = x + width - 6;
        }
        
        gc.fillOval(handX1, handY1, 8, 8);
        gc.fillOval(handX2, handY2, 8, 8);
        
        // Draw power-up indicators
        Character playerChar = getCharacter();
        if (playerChar.hasShield()) {
            gc.setStroke(Color.web(GameConfig.COLOR_POWERUP_SHIELD));
            gc.setLineWidth(2);
            gc.strokeOval(x - 2, y - 2 + bobOffset, width + 4, height + 4);
        }
        if (playerChar.hasWeapon()) {
            gc.setFill(Color.web(GameConfig.COLOR_POWERUP_WEAPON));
            gc.fillRect(x + width - 6 + squashX, y + 20 + bobOffset * 0.8, 6, 12);
        }
        if (playerChar.canDoubleJump()) {
            gc.setFill(Color.web(GameConfig.COLOR_POWERUP_DOUBLEJUMP));
            gc.fillOval(x + width / 2 - 4 + squashX, y - 4 + bobOffset * 0.4, 8, 8);
        }
        if (playerChar.isInvincible()) {
            gc.setFill(Color.web(GameConfig.COLOR_POWERUP_INVINCIBILITY));
            gc.setGlobalAlpha(0.3);
            gc.fillRect(x + squashX, y + bobOffset - stretchY, width - squashX * 2, height + stretchY - bobOffset);
            gc.setGlobalAlpha(1.0);
        }
    }

    public void handleInput(boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean attackPressed) {
        PlayerState previousState = currentState;
        currentState.handleInput(this, leftPressed, rightPressed, jumpPressed, attackPressed);

        // Log state transition if changed
        if (previousState != currentState) {
            GameLogger.logState("Player", previousState.getStateName(), currentState.getStateName());
        }
    }

    public void setState(PlayerState newState) {
        this.currentState = newState;
    }

    public PlayerState getCurrentState() {
        return currentState;
    }

    public void takeDamage() {
        long currentTime = System.currentTimeMillis();
        
        // Check if cooldown has passed to prevent multiple damage in quick succession
        if (currentTime - lastDamageTime < DAMAGE_COOLDOWN) {
            return; // Skip damage if still in cooldown
        }
        
        lastDamageTime = currentTime;
        
        if (character.hasShield()) {
            character.removeShield();
            SoundSystem.playDamage(); // Reuse damage sound for shield break
            GameLogger.logDecoratorRemoval("Shield", "Player");
        } else {
            lives--;
            SoundSystem.playDamage();
            GameLogger.logGameplay("Player took damage. Lives remaining: " + lives);
            if (lives <= 0) {
                GameLogger.logState("Player", currentState.getStateName(), "DEAD");
            }
        }
    }

    public void respawn() {
        setX(GameConfig.PLAYER_START_X);
        setY(GameConfig.PLAYER_START_Y);
        setVelocityX(0);
        setVelocityY(0);
        onGround = false;
        lastDamageTime = 0; // Reset damage cooldown on respawn
        GameLogger.logGameplay("Player respawned");
    }

    public void jump() {
        if (onGround || character.canDoubleJump()) {
            setVelocityY(GameConfig.JUMP_FORCE);
            onGround = false;
            jumpStartTime = System.currentTimeMillis();
            SoundSystem.playJump();
            GameLogger.logGameplay("Player jumped");
        }
    }

    public void moveLeft() {
        double speed = character.getSpeed() * GameConfig.MOVE_SPEED;
        setVelocityX(-speed);
        facingRight = false;
    }

    public void moveRight() {
        double speed = character.getSpeed() * GameConfig.MOVE_SPEED;
        setVelocityX(speed);
        facingRight = true;
    }

    public void stopMoving() {
        setVelocityX(0);
    }

    public void attack() {
        if (character.hasWeapon()) {
            GameLogger.logGameplay("Player attacked with weapon");
            // Attack logic would go here
        }
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public long getJumpStartTime() {
        return jumpStartTime;
    }
}
