package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.ImageView;
import com.mygdx.game.managers.FileManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HatsScreen implements Screen {
    MyGdxGame myGdxGame;
    private Texture background;
    private ImageView hatNull;
    private ImageView hatApple;
    private ImageView hatCap;
    private ImageView hatCapybara;
    private ButtonView icon_back;
    private ButtonView buyHatApple;
    private ButtonView buyHatCap;
    private ButtonView buyHatCapybara;
    private ButtonView buyHatNull;

    private FileManager fileManager;

    public HatsScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        background = new Texture(GameResources.BACKGROUND_SHOP);
        hatNull = new ImageView(50, 500, 140, 160, GameResources.ICON_NULL_HAT);
        hatApple = new ImageView(240, 500, 140, 160, GameResources.ICON_APPLE_HAT);
        hatCap = new ImageView(430, 500, 140, 160, GameResources.ICON_NG_HAT);
        hatCapybara = new ImageView(50, 190, 140, 160, GameResources.ICON_CAPYBARA_HAT);

        buyHatApple = new ButtonView(240, 440, 140, 70, myGdxGame.defaultFont, GameResources.BUTTON, "10 монет");
        buyHatCap = new ButtonView(430, 440, 140, 70, myGdxGame.defaultFont, GameResources.BUTTON, "20 монет");
        buyHatCapybara = new ButtonView(50, 130, 140, 70, myGdxGame.defaultFont, GameResources.BUTTON, "30 монет");
        buyHatNull = new ButtonView(50, 440, 140, 70, myGdxGame.defaultFont, GameResources.BUTTON, "0 монет");

        icon_back = new ButtonView(GameSettings.SCR_WIDTH - 90, GameSettings.SCR_HEIGHT - 80,
                85, 75, GameResources.ICON_BACK);

        fileManager = new FileManager();
    }

    @Override
    public void show() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
    }

    @Override
    public void render(float delta) {
        handleInput();

        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background,  0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        hatNull.draw(myGdxGame.batch);
        hatApple.draw(myGdxGame.batch);
        hatCap.draw(myGdxGame.batch);
        hatCapybara.draw(myGdxGame.batch);
        buyHatApple.draw(myGdxGame.batch);
        buyHatCap.draw(myGdxGame.batch);
        buyHatCapybara.draw(myGdxGame.batch);
        buyHatNull.draw(myGdxGame.batch);

        icon_back.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buyHatApple.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 10) {
                System.out.println("Writing to file: 1");
                fileManager.writeToFile(1, GameResources.HATS_DATA);
                GameSettings.SCORE -= 10;
            }
            if (buyHatCap.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 20) {
                System.out.println("Writing to file: 2");
                fileManager.writeToFile(2, GameResources.HATS_DATA);
                GameSettings.SCORE -= 20;
            }
            if (buyHatCapybara.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 30) {
                System.out.println("Writing to file: 3");
                fileManager.writeToFile(3, GameResources.HATS_DATA);
                GameSettings.SCORE -= 30;
            }
            if (buyHatNull.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                System.out.println("Writing to file: 0");
                fileManager.writeToFile(0, GameResources.HATS_DATA);
            }

            if (icon_back.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.customizationScreen);
            }
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        hatNull.dispose();
        hatApple.dispose();
        hatCap.dispose();
        hatCapybara.dispose();
        buyHatApple.dispose();
        buyHatCap.dispose();
        buyHatCapybara.dispose();
        buyHatNull.dispose();
        icon_back.dispose();
    }

    @Override
    public void resize(int width, int height) {

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
}

