package com.palestone.enigma.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.palestone.enigma.EnigmaMain;

public class MainMenuScreen extends BaseScreen {

    public MainMenuScreen(EnigmaMain game) {
        super(game);

        camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
    }

    @Override
    public void show() {
        System.out.println("Showing MainMenu.");
    }

    @Override
    public void update(float delta) {
        game.setScreen(game.gameScreen);
    }

    @Override
    public void renderScreen(float delta) {

    }
}
