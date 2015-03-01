package com.palestone.enigma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class TextureAssets {

    private static AssetManager assetManager;
    private static TextureAtlas textureAtlas;

    public static void load() {
        assetManager = new AssetManager();
        assetManager.load("packedImages/imagePack.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        textureAtlas = assetManager.get("packedImages/imagePack.atlas");
    }

    public static TextureRegion getRegion(String name) {
        return textureAtlas.findRegion(name);
    }
}
