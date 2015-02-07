package com.palestone.enigma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class TextureAssets {

    private static Texture sheet;
    private static Texture guiSheet;

    public static ObjectMap<String, TextureRegion> guiMap;
    public static ObjectMap<String, TextureRegion> sectionMap;
    public static ObjectMap<String, TextureRegion> unitMap;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        guiSheet = loadTexture("guiSheet.png");
        guiMap = new ObjectMap<String, TextureRegion>();
        guiMap.put("buttonUp", useRegion(guiSheet, 0, 0, 32 * 4, 32));
        guiMap.put("buttonDown", useRegion(guiSheet, 0, 32, 32 * 4, 32));

        sheet = loadTexture("sheet.png");
        sectionMap = new ObjectMap<String, TextureRegion>();
        sectionMap.put("ground", useRegion(sheet, 32, 0, 32, 32));
        sectionMap.put("wall", useRegion(sheet, 64, 0, 32, 32));

        unitMap = new ObjectMap<String, TextureRegion>();
        unitMap.put("player", useRegion(sheet, 0, 0, 32, 64));
    }

    private static TextureRegion useRegion(Texture sheet, int x, int y, int width, int height) {
        TextureRegion region = new TextureRegion(sheet, x, y, width, height);
        region.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        fixBleeding(region);
        return region;
    }

    public static void fixBleeding(TextureRegion region) {
        float x = region.getRegionX();
        float y = region.getRegionY();
        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        float invTexWidth = 1f / region.getTexture().getWidth();
        float invTexHeight = 1f / region.getTexture().getHeight();
        region.setRegion((x + .01f) * invTexWidth, (y+.01f) * invTexHeight, (x + width - .01f) * invTexWidth, (y + height - .01f) * invTexHeight);
    }
}
