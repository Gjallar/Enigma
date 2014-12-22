package com.palestone.enigma.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.palestone.enigma.EnigmaMain;
import com.palestone.enigma.components.PlayerComponent;
import com.palestone.enigma.components.TextureComponent;
import com.palestone.enigma.components.TransformComponent;
import com.palestone.enigma.screens.GameScreen;

public class CameraSystem extends EntitySystem {

    private EnigmaMain game;
    private Engine engine;
    private OrthographicCamera camera;

    public CameraSystem(Engine engine, EnigmaMain game) {
        this.engine = engine;
        this.game = game;
    }

    @Override
    public void update(float delta) {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.getFor(PlayerComponent.class));
        Entity entity = entities.get(0);
        TransformComponent playerPosition = entity.getComponent(TransformComponent.class);
        TextureComponent playerTexture = entity.getComponent(TextureComponent.class);

        float lerpAmount = 20f * delta;

        camera.position.lerp(new Vector3(playerPosition.position.x + playerTexture.region.getRegionWidth() / 2,
                playerPosition.position.y + playerTexture.region.getRegionHeight() / 2, 0), lerpAmount);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }
}
