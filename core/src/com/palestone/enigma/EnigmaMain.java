package com.palestone.enigma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.palestone.enigma.screens.BaseScreen;
import com.palestone.enigma.screens.GameScreen;
import com.palestone.enigma.screens.MainMenuScreen;

public class EnigmaMain extends Game {

	public SpriteBatch batch;

	public MainMenuScreen mainMenuScreen;
	public GameScreen gameScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();

		TextureAssets.load();

		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);

		setScreen(mainMenuScreen);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	public BaseScreen getActiveScreen() {
		return (BaseScreen) getScreen();
	}
}
