package com.palestone.enigma.screens;

import com.badlogic.ashley.core.Engine;
import com.palestone.enigma.EnigmaMain;
import com.palestone.enigma.World;
import com.palestone.enigma.systems.InputSystem;
import com.palestone.enigma.systems.PlayerSystem;
import com.palestone.enigma.systems.RenderingSystem;

public class GameScreen extends BaseScreen{

    World world;
    Engine engine;

    public GameScreen(EnigmaMain game) {
        super(game);

        engine = new Engine();
        world = new World(engine);

        engine.addSystem(new RenderingSystem(game.batch, camera));
        engine.addSystem(new InputSystem(engine));
        engine.addSystem(new PlayerSystem());

        world.create();
    }

    @Override
    public void show() {
        System.out.println("Showing GameScreen.");
    }

    @Override
    public void update(float delta) {
        engine.update(delta);
    }

    @Override
    public void renderScreen(float delta) {
    }
}
