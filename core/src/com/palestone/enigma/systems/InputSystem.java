package com.palestone.enigma.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.palestone.enigma.World;
import com.palestone.enigma.components.TransformComponent;
import com.palestone.enigma.screens.GameScreen;
import sun.awt.windows.WPageDialog;

public class InputSystem extends EntitySystem{

    public static float xMouse, yMouse, xMouseGrid, yMouseGrid;
    public static boolean leftMouse, rightMouse;
    private boolean leftMouseWasPressed, rightMouseWasPressed;
    private boolean escapePressed;
    private Engine engine;

    ComponentMapper<TransformComponent> transformMapper;

    public InputSystem(Engine engine) {
        this.engine = engine;

        transformMapper = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void update(float delta) {
        PlayerSystem playerSystem = engine.getSystem(PlayerSystem.class);

        xMouse = Gdx.input.getX() + CameraSystem.xCamera - GameScreen.width / 2;
        yMouse = CameraSystem.yCamera + GameScreen.height / 2 - Gdx.input.getY();
        xMouseGrid = xMouse - xMouse % World.GRID_SIZE;
        yMouseGrid = yMouse - yMouse % World.GRID_SIZE;

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escapePressed) {
            JsonSaveGameSystem jsonSaveGameSystem = engine.getSystem(JsonSaveGameSystem.class);
            jsonSaveGameSystem.saveWorld();
            escapePressed = true;

        }
        else if(!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            escapePressed = false;
        }

        leftMouse = false;
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !leftMouseWasPressed) {
            leftMouse = true;
            leftMouseWasPressed = true;

        }
        else if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            leftMouseWasPressed = false;
        }

        rightMouse = false;
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && !rightMouseWasPressed) {
            rightMouse = true;
            rightMouseWasPressed = true;

        }
        else if(!Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            rightMouseWasPressed = false;
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
