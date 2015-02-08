package com.palestone.enigma.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.palestone.enigma.TextureAssets;
import com.palestone.enigma.World;
import com.palestone.enigma.components.*;
import com.palestone.enigma.enums.Layer;

import java.util.ArrayList;

public class JsonSaveGameSystem extends IteratingSystem{
    Engine engine;

    ComponentMapper<TransformComponent> transformMapper;
    ComponentMapper<TextureComponent> textureMapper;
    ComponentMapper<SectionComponent> sectionMapper;
    ComponentMapper<DoorComponent> doorMapper;

    public JsonSaveGameSystem(Engine engine) {
        super(Family.all(JsonSaveGameComponent.class).get());

        this.engine = engine;

        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        sectionMapper = ComponentMapper.getFor(SectionComponent.class);
        doorMapper = ComponentMapper.getFor(DoorComponent.class);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public static class JsonWorld {
        public JsonPlayer player;
        public Vector2 activeSection;
        public ArrayList<JsonSection> sections = new ArrayList<JsonSection>();
    }

    public static class JsonSection {
        public Vector2 key;
        public ArrayList<JsonGroundTile> groundTiles = new ArrayList<JsonGroundTile>();
        public ArrayList<JsonEnvironmentTile> environmentTiles = new ArrayList<JsonEnvironmentTile>();
        public ArrayList<JsonDoor> doors = new ArrayList<JsonDoor>();
    }

    public static class JsonPlayer {
        public float x;
        public float y;
        public String textureName;
        public Layer layer;
    }

    public static class JsonGroundTile {
        public float x;
        public float y;
        public String textureName;
        public Layer layer;
        public Vector2 parentKey;
    }

    public static class JsonEnvironmentTile {
        public float x;
        public float y;
        public String textureName;
        public Layer layer;
        public Vector2 parentKey;
    }

    public static class JsonDoor {
        public float x;
        public float y;
        public String textureName;
        public Layer layer;
        public Vector2 parentKey;
        public Vector2 linkedSectionKey;
    }

    public void saveWorld() {
        JsonWorld jsonWorld = new JsonWorld();
        jsonWorld.activeSection = World.activeSection;

        Entity player = engine.getEntity(PlayerSystem.id);
        TransformComponent transformCompP = transformMapper.get(player);
        TextureComponent textureCompP = textureMapper.get(player);
        JsonPlayer jsonPlayer = new JsonPlayer();
        jsonPlayer.x = transformCompP.position.x;
        jsonPlayer.y = transformCompP.position.y;
        jsonPlayer.textureName = textureCompP.textureName;
        jsonPlayer.layer = textureCompP.layer;

        jsonWorld.player = jsonPlayer;

        for(ObjectMap.Entry<Vector2, Entity> entry : World.sections.entries()) {
            SectionComponent sectionComp = sectionMapper.get(entry.value);
            JsonSection jsonSection = new JsonSection();
            jsonSection.key = entry.key;

            for (Entity groundTile : sectionComp.groundTiles) {
                TransformComponent transformComp = transformMapper.get(groundTile);
                TextureComponent textureComp = textureMapper.get(groundTile);
                JsonGroundTile jsonGroundTile = new JsonGroundTile();
                jsonGroundTile.x = transformComp.position.x;
                jsonGroundTile.y = transformComp.position.y;
                jsonGroundTile.textureName = textureComp.textureName;
                jsonGroundTile.layer = textureComp.layer;

                jsonSection.groundTiles.add(jsonGroundTile);
            }

            for (Entity environmentTile : sectionComp.environmentTiles) {
                TransformComponent transformComp = transformMapper.get(environmentTile);
                TextureComponent textureComp = textureMapper.get(environmentTile);
                JsonEnvironmentTile jsonEnvironmentTile = new JsonEnvironmentTile();
                jsonEnvironmentTile.x = transformComp.position.x;
                jsonEnvironmentTile.y = transformComp.position.y;
                jsonEnvironmentTile.textureName = textureComp.textureName;
                jsonEnvironmentTile.layer = textureComp.layer;

                jsonSection.environmentTiles.add(jsonEnvironmentTile);
            }

            for (Entity door : sectionComp.doors) {
                DoorComponent doorComp= doorMapper.get(door);
                TransformComponent transformComp = transformMapper.get(door);
                TextureComponent textureComp = textureMapper.get(door);
                JsonDoor jsonDoor = new JsonDoor();
                jsonDoor.x = transformComp.position.x;
                jsonDoor.y = transformComp.position.y;
                jsonDoor.textureName = textureComp.textureName;
                jsonDoor.layer = textureComp.layer;
                jsonDoor.linkedSectionKey = doorComp.linkedSectionKey;

                jsonSection.doors.add(jsonDoor);
            }

            jsonWorld.sections.add(jsonSection);
        }

        Json json = new Json();
        writeFile("game.sav", json.toJson(jsonWorld));
        System.out.println("Saved world.");
    }

    public void loadWorld() {
        String save = readFile("game.sav");
        if (!save.isEmpty()) {
            Json json = new Json();
            JsonWorld jsonWorld = json.fromJson(JsonWorld.class, save);
            World.activeSection = jsonWorld.activeSection;

            loadPlayer(jsonWorld.player);

            for(JsonSection jsonSection : jsonWorld.sections) {
                Entity section = new Entity();

                SectionComponent sectionComp = new SectionComponent();
                sectionComp.key = jsonSection.key;
                sectionComp.generated = true;
                if(jsonSection.key == new Vector2(0, 0)) {
                    sectionComp.initialSection = true;
                }

                section.add(sectionComp);

                World.sections.put(sectionComp.key, section);

                for(JsonGroundTile jsonGroundTile : jsonSection.groundTiles) {
                    Entity groundTile = new Entity();

                    GroundTileComponent groundTileComp = new GroundTileComponent();
                    TransformComponent transformComp = new TransformComponent();
                    TextureComponent textureComp = new TextureComponent();

                    textureComp.textureName = jsonGroundTile.textureName;
                    textureComp.region.setRegion(TextureAssets.sectionMap.get(jsonGroundTile.textureName));
                    textureComp.layer = jsonGroundTile.layer;

                    transformComp.position.set(jsonGroundTile.x, jsonGroundTile.y);

                    groundTile.add(groundTileComp);
                    groundTile.add(transformComp);
                    groundTile.add(textureComp);

                    sectionComp.groundTiles.add(groundTile);
                    sectionComp.allEntities.add(groundTile);
                }

                for(JsonEnvironmentTile jsonEnvironmentTile : jsonSection.environmentTiles) {
                    Entity environmentTile = new Entity();

                    EnvironmentTileComponent environmentTileComp = new EnvironmentTileComponent();
                    TransformComponent transformComp = new TransformComponent();
                    TextureComponent textureComp = new TextureComponent();
                    CollisionComponent collisionComp = new CollisionComponent();

                    textureComp.textureName = jsonEnvironmentTile.textureName;
                    textureComp.region.setRegion(TextureAssets.sectionMap.get(textureComp.textureName));
                    textureComp.layer = jsonEnvironmentTile.layer;

                    transformComp.position.set(jsonEnvironmentTile.x, jsonEnvironmentTile.y);

                    collisionComp.body = new Rectangle(transformComp.position.x, transformComp.position.y,
                            textureComp.region.getRegionWidth(), textureComp.region.getRegionHeight());

                    environmentTile.add(transformComp);
                    environmentTile.add(textureComp);
                    environmentTile.add(collisionComp);
                    environmentTile.add(environmentTileComp);

                    sectionComp.environmentTiles.add(environmentTile);
                    sectionComp.allEntities.add(environmentTile);
                }

                for(JsonDoor jsonDoor : jsonSection.doors) {
                    Entity door = new Entity();

                    DoorComponent doorComp = new DoorComponent();
                    TransformComponent transformComp = new TransformComponent();
                    TextureComponent textureComp = new TextureComponent();

                    doorComp.linkedSectionKey = jsonDoor.linkedSectionKey;

                    textureComp.textureName = jsonDoor.textureName;
                    textureComp.region.setRegion(TextureAssets.sectionMap.get(textureComp.textureName));
                    textureComp.layer = jsonDoor.layer;

                    transformComp.position.set(jsonDoor.x, jsonDoor.y);

                    doorComp.body = new Rectangle(transformComp.position.x, transformComp.position.y,
                            textureComp.region.getRegionWidth(), textureComp.region.getRegionHeight());

                    door.add(transformComp);
                    door.add(textureComp);
                    door.add(doorComp);

                    sectionComp.doors.add(door);
                    sectionComp.allEntities.add(door);
                }
            }
        }
        addActiveSectionToEngine();
        System.out.println("Loaded world.");
    }

    private void loadPlayer(JsonPlayer jsonPlayer) {
        Entity player = new Entity();

        CollisionComponent collisionComp = new CollisionComponent();
        MovementComponent movementComp = new MovementComponent();
        TransformComponent transformComp = new TransformComponent();
        TextureComponent textureComp = new TextureComponent();
        PlayerComponent playerComp = new PlayerComponent();

        textureComp.textureName = jsonPlayer.textureName;
        textureComp.region.setRegion(TextureAssets.unitMap.get(textureComp.textureName));
        textureComp.layer = jsonPlayer.layer;

        transformComp.position.set(jsonPlayer.x, jsonPlayer.y);

        collisionComp.body = new Rectangle(transformComp.position.x, transformComp.position.y,
                32, 32);

        player.add(movementComp);
        player.add(transformComp);
        player.add(textureComp);
        player.add(playerComp);
        player.add(collisionComp);

        engine.addEntity(player);
        PlayerSystem.id = player.getId();
    }

    private void addActiveSectionToEngine() {
        Entity section = World.sections.get(World.activeSection);
        SectionComponent sectionComp = sectionMapper.get(section);

        for(Entity entity : sectionComp.allEntities)
            engine.addEntity(entity);

        engine.addEntity(section);
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
