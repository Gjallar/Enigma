package com.palestone.enigma.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.palestone.enigma.LoadWorld;
import com.palestone.enigma.SaveWorld;
import com.palestone.enigma.World;
import com.palestone.enigma.components.*;
import com.palestone.enigma.JsonClasses.*;

public class JsonSaveGameSystem extends IteratingSystem{
    Engine engine;
    SaveWorld save;
    LoadWorld load;

    ComponentMapper<SerializableComponent> serializableMapper;
    ComponentMapper<SectionComponent> sectionMapper;
    ComponentMapper<LiftComponent> canLiftMapper;

    public JsonSaveGameSystem(Engine engine) {
        super(Family.all(JsonSaveGameComponent.class).get());

        this.engine = engine;
        save = new SaveWorld();
        load = new LoadWorld();

        serializableMapper = ComponentMapper.getFor(SerializableComponent.class);
        sectionMapper = ComponentMapper.getFor(SectionComponent.class);
        canLiftMapper = ComponentMapper.getFor(LiftComponent.class);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public void saveWorld() {
        JsonWorld jsonWorld = new JsonWorld();
        jsonWorld.activeSection = World.activeSection;

        Entity player = engine.getEntity(PlayerSystem.id);
        jsonWorld.player = save.savePlayer(player);

        LiftComponent canLiftCompPlayer = canLiftMapper.get(player);
        if(canLiftCompPlayer.lifting) {
            jsonWorld.player.liftable = save.generateJsonEntity(canLiftCompPlayer.entity);
        }


        for(ObjectMap.Entry<Vector2, Entity> entry : World.sections.entries()) {
            SectionComponent sectionComp = sectionMapper.get(entry.value);
            JsonSection jsonSection = new JsonSection();
            jsonSection.key = entry.key;

            for (Entity serializableEntity : sectionComp.allEntities) {
                JsonEntity jsonEntity = save.generateJsonEntity(serializableEntity);
                if(jsonEntity == null)
                    System.out.println("[SAVE] Entity has no valid Type!");

                jsonSection.entities.add(jsonEntity);
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

            Entity player = load.loadPlayer(jsonWorld.player);
            engine.addEntity(player);
            PlayerSystem.id = player.getId();

            LiftComponent canLiftCompPlayer = canLiftMapper.get(player);
            if(jsonWorld.player.lifting) {
                engine.addEntity(canLiftCompPlayer.entity);
            }

            for(JsonSection jsonSection : jsonWorld.sections) {
                Entity section = new Entity();

                SectionComponent sectionComp = new SectionComponent();
                sectionComp.key = jsonSection.key;
                sectionComp.generated = true;
                if(jsonSection.key == new Vector2(0, 0)) {
                    sectionComp.initialSection = true;
                }

                for(JsonEntity jsonEntity : jsonSection.entities) {
                    Entity entity = load.generateEntity(jsonEntity);
                    if(entity == null)
                        System.out.println("[LOAD] JsonEntity has no valid Type!");

                    sectionComp.allEntities.add(entity);
                }

                section.add(sectionComp);
                World.sections.put(sectionComp.key, section);
            }
        }
        addActiveSectionToEngine();
        System.out.println("Loaded world.");
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
