package com.palestone.enigma.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class InputSystem extends EntitySystem{

    private Engine engine;
    public InputSystem(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void update(float delta) {
        PlayerSystem playerSystem = engine.getSystem(PlayerSystem.class);

        Vector2 acceleration = new Vector2();
        float accelerationAmount = 0.2f;

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            acceleration.y += accelerationAmount;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            acceleration.y -= accelerationAmount;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            acceleration.x -= accelerationAmount;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            acceleration.x += accelerationAmount;
        }

        playerSystem.addAcceleration(acceleration);
    }
}
