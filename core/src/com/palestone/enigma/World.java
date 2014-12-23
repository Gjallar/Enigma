package com.palestone.enigma;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.palestone.enigma.components.MovementComponent;
import com.palestone.enigma.components.PlayerComponent;
import com.palestone.enigma.components.TextureComponent;
import com.palestone.enigma.components.TransformComponent;
import com.palestone.enigma.enums.Layer;

public class World {

    private Engine engine;

    public World(Engine engine) {
        this.engine = engine;
    }

    public void create() {
        createPlayer();
        createLevel();
    }

    private void createPlayer() {
        Entity entity = new Entity();

        MovementComponent movementComponent = new MovementComponent();
        TransformComponent transformComponent = new TransformComponent();
        TextureComponent textureComponent = new TextureComponent();
        PlayerComponent playerComponent = new PlayerComponent();

        textureComponent.region.setRegion(TextureAssets.player);
        textureComponent.layer = Layer.MIDDLE;

        entity.add(movementComponent);
        entity.add(transformComponent);
        entity.add(textureComponent);
        entity.add(playerComponent);

        engine.addEntity(entity);
    }

    private void createLevel() {

        for(int y = 0; y < 16; y++) {
            for(int x = 0; x < 16; x++) {
                Entity entity = new Entity();

                TransformComponent transformComponent = new TransformComponent();
                TextureComponent textureComponent = new TextureComponent();

                textureComponent.region.setRegion(TextureAssets.ground);
                textureComponent.layer = Layer.BACKGROUND;

                transformComponent.position.set(x * 32, y * 32);

                entity.add(transformComponent);
                entity.add(textureComponent);

                engine.addEntity(entity);

                int chance = MathUtils.random(2);
                if (chance == 0) {
                    Entity entity2 = new Entity();

                    TransformComponent transformComponent2 = new TransformComponent();
                    TextureComponent textureComponent2 = new TextureComponent();

                    textureComponent2.region.setRegion(TextureAssets.wall);
                    textureComponent2.layer = Layer.MIDDLE;

                    transformComponent2.position.set(x * 32, y * 32);

                    entity.add(transformComponent2);
                    entity.add(textureComponent2);

                    engine.addEntity(entity);
                }
            }
        }
    }
}
