package com.palestone.enigma;

import com.badlogic.ashley.core.Entity;

public class Tile {

    public static final int size = 16;
    private int x, y;
    private Entity entity;
    private boolean occupied;

    public Tile() {

    }

    public Entity getEntity() {
        return entity;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
