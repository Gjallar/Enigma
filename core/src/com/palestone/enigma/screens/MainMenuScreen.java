package com.palestone.enigma.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.palestone.enigma.EnigmaMain;
import com.palestone.enigma.TextureAssets;

public class MainMenuScreen extends BaseScreen {

    Stage stage;
    TextButton continueButton;
    TextButton newGameButton;
    TextButton.TextButtonStyle buttonStyle;
    BitmapFont font;
    Skin skin;

    public MainMenuScreen(EnigmaMain game) {
        super(game);

        camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();

        skin.add("buttonUp", new TextureRegion(TextureAssets.getRegion("guiSheet/buttonUp")));
        skin.add("buttonDown", new TextureRegion(TextureAssets.getRegion("guiSheet/buttonDown")));
        skin.getDrawable("buttonUp");
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = skin.getDrawable("buttonUp");
        buttonStyle.down = skin.getDrawable("buttonDown");

        continueButton = new TextButton("Continue", buttonStyle);
        continueButton.setPosition(width / 2 - continueButton.getWidth() / 2, height / 2 - continueButton.getHeight() / 2);

        newGameButton = new TextButton("New Game", buttonStyle);
        newGameButton.setPosition(continueButton.getX(), continueButton.getY() - continueButton.getHeight() * 1.5f);

        stage.addActor(continueButton);
        stage.addActor(newGameButton);

        initButtonListeners();
    }

    private void initButtonListeners() {
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.gameScreen.world.loadSections();
                continueButton.remove();
                newGameButton.remove();
                game.setScreen(game.gameScreen);
            }
        });
        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.gameScreen.world.create();
                continueButton.remove();
                newGameButton.remove();
                game.setScreen(game.gameScreen);
            }
        });
    }
    @Override
    public void show() {
        System.out.println("Showing MainMenu.");
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void renderScreen(float delta) {
        stage.draw();
    }
}
