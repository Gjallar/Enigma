package com.palestone.enigma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.palestone.enigma.EnigmaMain;

public abstract class BaseScreen implements Screen {

    public static int width, height;
    protected EnigmaMain game;
    OrthographicCamera camera;

    public BaseScreen(EnigmaMain game) {
        this.game = game;

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        renderScreen(delta);
    }

    public void update(float delta) {

    }

    public void renderScreen(float felta) {

    }

    @Override
    public void resize(int width, int height) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }
}
