package com.palestone.enigma.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.palestone.enigma.EnigmaMain;
import com.palestone.enigma.components.TextureComponent;
import com.palestone.enigma.components.TransformComponent;

public class CameraSystem extends EntitySystem {

    private EnigmaMain game;
    private Engine engine;
    private OrthographicCamera camera;

    public static float xCamera, yCamera;

    public CameraSystem(Engine engine, EnigmaMain game) {
        this.engine = engine;
        this.game = game;
    }

    @Override
    public void update(float delta) {
        Entity player = engine.getEntity(PlayerSystem.id);
        TransformComponent playerPosition = player.getComponent(TransformComponent.class);
        TextureComponent playerTexture = player.getComponent(TextureComponent.class);

        float lerpAmount = 10 * delta;
        float x = playerPosition.position.x + playerTexture.region.getRegionWidth() / 2;
        float y = playerPosition.position.y + playerTexture.region.getRegionHeight() / 2;
        x = MathUtils.round(x);
        y = MathUtils.round(y);

        camera.position.lerp(new Vector3(x, y, 0), lerpAmount);
        xCamera = camera.position.x;
        yCamera = camera.position.y;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }
}
