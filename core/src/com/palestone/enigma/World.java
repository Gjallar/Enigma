package com.palestone.enigma;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.palestone.enigma.components.*;
import com.palestone.enigma.enums.Layer;
import com.palestone.enigma.systems.JsonSaveGameSystem;
import com.palestone.enigma.systems.PlayerSystem;

public class World {

    private Engine engine;
    private Entity level;

    public World(Engine engine) {
        this.engine = engine;
    }

    public void create() {
        createPlayer();
        createSections();
    }

    private void createPlayer() {
        Entity player = new Entity();

        MovementComponent movementComponent = new MovementComponent();
        TransformComponent transformComponent = new TransformComponent();
        TextureComponent textureComponent = new TextureComponent();
        PlayerComponent playerComponent = new PlayerComponent();
        CollisionComponent collisionComponent = new CollisionComponent();

        textureComponent.region.setRegion(TextureAssets.unitMap.get("player"));
        textureComponent.layer = Layer.MIDDLE;

        collisionComponent.body = new Rectangle(transformComponent.position.x, transformComponent.position.y,
                32, 32);

        player.add(movementComponent);
        player.add(transformComponent);
        player.add(textureComponent);
        player.add(collisionComponent);
        player.add(playerComponent);

        engine.addEntity(player);
        PlayerSystem.id = player.getId();

    }

    private void createSections() {
        Entity section = new Entity();

        SectionComponent sectionComp = new SectionComponent();
        sectionComp.tileChance = 2;

        section.add(sectionComp);

        engine.addEntity(section);
    }

    public void loadSections() {
        JsonSaveGameSystem jsonSaveGameSystem = engine.getSystem(JsonSaveGameSystem.class);
        jsonSaveGameSystem.loadWorld();
    }
}
