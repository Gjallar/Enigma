package com.palestone.enigma.screens;

import com.badlogic.ashley.core.Engine;
import com.palestone.enigma.EnigmaMain;
import com.palestone.enigma.Utility;
import com.palestone.enigma.World;
import com.palestone.enigma.systems.*;

public class GameScreen extends BaseScreen{

    World world;
    Engine engine;

    public GameScreen(EnigmaMain game) {
        super(game);

        engine = new Engine();
        world = new World(engine);
        Utility.initUtility(engine);

        engine.addSystem(new JsonSaveGameSystem(engine));
        engine.addSystem(new CameraSystem(engine, game));
        engine.addSystem(new InputSystem(engine));
        engine.addSystem(new CollisionSystem(engine));
        engine.addSystem(new BuildSystem(engine));
        engine.addSystem(new DoorSystem(engine, world));
        engine.addSystem(new SectionSystem(engine));
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new LiftSystem(engine));
        engine.addSystem(new RenderingSystem(game));

        CameraSystem cameraSystem = engine.getSystem(CameraSystem.class);
        cameraSystem.setCamera(camera);
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

    @Override
    public void dispose() {

    }
}
