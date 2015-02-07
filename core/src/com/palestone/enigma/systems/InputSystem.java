package com.palestone.enigma.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class InputSystem extends EntitySystem{

    private boolean escapePressed;
    private Engine engine;
    public InputSystem(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void update(float delta) {
        PlayerSystem playerSystem = engine.getSystem(PlayerSystem.class);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escapePressed) {
            JsonSaveGameSystem jsonSaveGameSystem = engine.getSystem(JsonSaveGameSystem.class);
            jsonSaveGameSystem.saveWorld();
            escapePressed = true;

        }
        else if(!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            escapePressed = false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerSystem.setWalkingUp(true);
        }
        else {
            playerSystem.setWalkingUp(false);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerSystem.setWalkingDown(true);
        }
        else {
            playerSystem.setWalkingDown(false);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerSystem.setWalkingRight(true);
        }
        else {
            playerSystem.setWalkingRight(false);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerSystem.setWalkingLeft(true);
        }
        else {
            playerSystem.setWalkingLeft(false);
        }
    }
}
