package com.miniplatformer.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Enemy entity class
 * Concrete implementation of Entity for enemies
 */
public class EnemyEntity extends Entity {
    private String type;

    public EnemyEntity(double x, double y, double width, double height, String type) {
        super(x, y, width, height);
        this.type = type;
    }

    @Override
    public void update() {
        position.updatePosition();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.web(com.miniplatformer.config.GameConfig.COLOR_ENEMY));
        gc.fillRect(getX(), getY(), width, height);
    }

    public String getType() {
        return type;
    }
}
