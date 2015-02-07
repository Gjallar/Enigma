package com.palestone.enigma.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.palestone.enigma.TextureAssets;
import com.palestone.enigma.components.*;
import com.palestone.enigma.enums.Layer;

import java.util.ArrayList;

public class JsonSaveGameSystem extends IteratingSystem{
    Engine engine;

    ComponentMapper<TransformComponent> transformMapper;
    ComponentMapper<TextureComponent> textureMapper;

    public JsonSaveGameSystem(Engine engine) {
        super(Family.getFor(JsonSaveGameComponent.class));

        this.engine = engine;

        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public static class JsonWorld {
        public ArrayList<JsonSection> sectionList = new ArrayList<JsonSection>();
    }

    public static class JsonSection {
        public ArrayList<JsonGroundTile> groundTileList = new ArrayList<JsonGroundTile>();
    }

    public static class JsonGroundTile {
        public float x;
        public float y;
        public String textureName;
        public Layer layer;
    }

    public void saveWorld() {
        Family groundTileFamily = Family.all(GroundTileComponent.class).get();
        ImmutableArray<Entity> groundTiles = engine.getEntitiesFor(groundTileFamily);

        JsonWorld jWorld = new JsonWorld();
        JsonSection jSection = new JsonSection();

        for (Entity groundTile : groundTiles) {
            TransformComponent transformComp = transformMapper.get(groundTile);
            TextureComponent textureComp = textureMapper.get(groundTile);
            JsonGroundTile jGroundTile = new JsonGroundTile();
            jGroundTile.x = transformComp.position.x;
            jGroundTile.y = transformComp.position.y;
            jGroundTile.textureName = textureComp.textureName;
            jGroundTile.layer = textureComp.layer;

            jSection.groundTileList.add(jGroundTile);
        }

        jWorld.sectionList.add(jSection);

        Json json = new Json();
        writeFile("game.sav", json.toJson(jWorld));
        System.out.println("Saved world.");
    }

    public void loadWorld() {
        String save = readFile("game.sav");

        loadPlayer();

        if (!save.isEmpty()) {
            Json json = new Json();
            JsonWorld jWorld = json.fromJson(JsonWorld.class, save);

            for(JsonSection jSection : jWorld.sectionList) {
                for(JsonGroundTile jGroundTile : jSection.groundTileList) {
                    Entity entity = new Entity();

                    GroundTileComponent groundTileComponent = new GroundTileComponent();
                    TransformComponent transformComponent = new TransformComponent();
                    TextureComponent textureComponent = new TextureComponent();

                    textureComponent.textureName = jGroundTile.textureName;
                    textureComponent.region.setRegion(TextureAssets.sectionMap.get(jGroundTile.textureName));
                    textureComponent.layer = jGroundTile.layer;

                    transformComponent.position.set(jGroundTile.x, jGroundTile.y);

                    entity.add(groundTileComponent);
                    entity.add(transformComponent);
                    entity.add(textureComponent);

                    engine.addEntity(entity);
                }
            }
        }
        System.out.println("Loaded world.");
    }

    private void loadPlayer() {
        Entity player = new Entity();

        MovementComponent movementComponent = new MovementComponent();
        TransformComponent transformComponent = new TransformComponent();
        TextureComponent textureComponent = new TextureComponent();
        PlayerComponent playerComponent = new PlayerComponent();

        textureComponent.region.setRegion(TextureAssets.unitMap.get("player"));
        textureComponent.layer = Layer.MIDDLE;

        player.add(movementComponent);
        player.add(transformComponent);
        player.add(textureComponent);
        player.add(playerComponent);

        engine.addEntity(player);
        PlayerSystem.id = player.getId();
    }

    public static void writeFile(String fileName, String s) {
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
    }

    public static String readFile(String fileName) {
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String s = file.readString();
            if (!s.isEmpty()) {
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            }
        }
        return "";
    }
}
