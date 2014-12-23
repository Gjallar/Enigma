package com.palestone.enigma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureAssets {

    private static Texture sheet;

    public static TextureRegion player;
    public static TextureRegion ground;
    public static TextureRegion wall;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        sheet = loadTexture("sheet.png");
        player = new TextureRegion(sheet, 2, 2, 64, 64);
        ground = new TextureRegion(sheet, 69, 1, 32, 32);
        wall = new TextureRegion(sheet, 103, 1, 32, 32);
    }
}
