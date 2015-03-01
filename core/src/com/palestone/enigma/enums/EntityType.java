package com.palestone.enigma.enums;

public enum EntityType {
    PLAYER(1), GROUND(2), ENVIRONMENT(3), DOOR(4), LIFTABLE(5), CONVEYOR(6);

    public static final int size = Layer.values().length;
    private int numVal;

    EntityType(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
