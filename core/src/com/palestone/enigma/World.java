package com.palestone.enigma;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.palestone.enigma.components.MovementComponent;
import com.palestone.enigma.components.PlayerComponent;
import com.palestone.enigma.components.TextureComponent;
import com.palestone.enigma.components.TransformComponent;

public class World {

    private Engine engine;

    public World(Engine engine) {
        this.engine = engine;
    }

    public void create() {
        createPlayer();
    }

    private void createPlayer() {
        Entity entity = new Entity();

        MovementComponent movementComponent = new MovementComponent();
        TransformComponent transformComponent = new TransformComponent();
        TextureComponent textureComponent = new TextureComponent();
        PlayerComponent playerComponent = new PlayerComponent();

        textureComponent.region.setRegion(TextureAssets.player);

        entity.add(movementComponent);
        entity.add(transformComponent);
        entity.add(textureComponent);
        entity.add(playerComponent);

        engine.addEntity(entity);
    }
}
