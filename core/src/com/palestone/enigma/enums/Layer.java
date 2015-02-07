package com.palestone.enigma.enums;

public enum Layer {
    BACKGROUND(0), MIDDLE(1), FOREGROUND(2);

    public static final int size = Layer.values().length;
    private int numVal;

    Layer(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
