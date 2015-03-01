package com.palestone.enigma;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.palestone.enigma.components.*;
import com.palestone.enigma.enums.EntityType;
import com.palestone.enigma.enums.Layer;
import com.palestone.enigma.systems.JsonSaveGameSystem;
import com.palestone.enigma.systems.PlayerSystem;

public class World {

    private Engine engine;
    public static final int GRID_SIZE = 16;
    public static Vector2 activeSection;
    public static ObjectMap<Vector2, Entity> sections;

    ComponentMapper<SectionComponent> sectionMapper;

    public World(Engine engine) {
        this.engine = engine;
        sections = new ObjectMap<Vector2, Entity>();

        sectionMapper = ComponentMapper.getFor(SectionComponent.class);
    }

    public void create() {
        createPlayer();
        createSections();
    }

    private void createPlayer() {
        Entity player = new Entity();

        SerializableComponent serializableComp = new SerializableComponent();
        MovementComponent movementComponent = new MovementComponent();
        TransformComponent transformComponent = new TransformComponent();
        TextureComponent textureComponent = new TextureComponent();
        PlayerComponent playerComponent = new PlayerComponent();
        BodyComponent bodyComponent = new BodyComponent();
        LiftComponent canLifrComp = new LiftComponent();

        serializableComp.entityType = EntityType.PLAYER;

        textureComponent.textureName = "unitSheet/player0";
        textureComponent.region.setRegion(TextureAssets.getRegion(textureComponent.textureName));
        textureComponent.layer = Layer.MIDDLE;

        transformComponent.position.set(300, 400);

        bodyComponent.collides = true;
        bodyComponent.body = new Rectangle(transformComponent.position.x, transformComponent.position.y,
                32, 32);

        player.add(serializableComp);
        player.add(movementComponent);
        player.add(transformComponent);
        player.add(textureComponent);
        player.add(bodyComponent);
        player.add(playerComponent);
        player.add(canLifrComp);

        engine.addEntity(player);
        PlayerSystem.id = player.getId();
    }

    private void createSections() {
        Entity section = new Entity();

        SectionComponent sectionComp = new SectionComponent();
        sectionComp.tileChance = 2;
        sectionComp.initialSection = true;
        sectionComp.key = new Vector2(0, 0);

        section.add(sectionComp);

        engine.addEntity(section);

        activeSection = sectionComp.key;
        sections.put(sectionComp.key, section);
    }

    public void loadSections() {
        JsonSaveGameSystem jsonSaveGameSystem = engine.getSystem(JsonSaveGameSystem.class);
        jsonSaveGameSystem.loadWorld();
    }

    public void removeActiveSection() {
        SectionComponent sectionComp = sectionMapper.get(sections.get(activeSection));
        for(Entity entity : sectionComp.allEntities)
            engine.removeEntity(entity);

        engine.removeEntity(sections.get(activeSection));
    }

    public static Entity getActiveSection(){
        return sections.get(activeSection);
    }
}
