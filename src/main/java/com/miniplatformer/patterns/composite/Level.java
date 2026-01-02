package com.miniplatformer.patterns.composite;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

/**
 * Level class - composite in Composite Pattern
 * Contains scenes, platforms, enemies, and collectibles
 */
public class Level implements GameComponent {
    private List<GameComponent> components;
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean active;
    private String name;

    public Level(String name, double width, double height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
        this.components = new ArrayList<>();
        this.active = true;
    }

    @Override
    public void update() {
        if (!active) return;
        for (GameComponent component : components) {
            if (component.isActive()) {
                component.update();
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;
        for (GameComponent component : components) {
            if (component.isActive()) {
                component.render(gc);
            }
        }
    }

    @Override
    public void add(GameComponent component) {
        components.add(component);
    }

    @Override
    public void remove(GameComponent component) {
        components.remove(component);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    public List<GameComponent> getComponents() {
        return new ArrayList<>(components);
    }

    public String getName() {
        return name;
    }
}
