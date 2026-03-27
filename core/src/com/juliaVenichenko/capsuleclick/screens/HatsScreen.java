package com.juliaVenichenko.capsuleclick.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.juliaVenichenko.capsuleclick.GameResources;
import com.juliaVenichenko.capsuleclick.GameSettings;
import com.juliaVenichenko.capsuleclick.MyGdxGame;
import com.juliaVenichenko.capsuleclick.components.ButtonView;
import com.juliaVenichenko.capsuleclick.components.ImageView;
import com.juliaVenichenko.capsuleclick.managers.FileManager;

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
        hatNull = new ImageView(30, 500, 160, 140, GameResources.ICON_NULL_HAT);
        hatApple = new ImageView(220, 500, 160, 140, GameResources.ICON_APPLE_HAT);
        hatCap = new ImageView(410, 500, 160, 140, GameResources.ICON_NG_HAT);
        hatCapybara = new ImageView(30, 230, 160, 140, GameResources.ICON_CAPYBARA_HAT);

        buyHatApple = new ButtonView(220, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "50 очков");
        buyHatCap = new ButtonView(410, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "200 очков");
        buyHatCapybara = new ButtonView(30, 170, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "400 очков");
        buyHatNull = new ButtonView(30, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "0 очков");

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

            if (buyHatApple.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 50) {
                System.out.println("Writing to file: 1");
                fileManager.writeToFile(1, GameResources.HATS_DATA);
                GameSettings.SCORE -= 50;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyHatCap.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 200) {
                System.out.println("Writing to file: 2");
                fileManager.writeToFile(2, GameResources.HATS_DATA);
                GameSettings.SCORE -= 200;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyHatCapybara.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 400) {
                System.out.println("Writing to file: 3");
                fileManager.writeToFile(3, GameResources.HATS_DATA);
                GameSettings.SCORE -= 400;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyHatNull.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                System.out.println("Writing to file: 0");
                fileManager.writeToFile(0, GameResources.HATS_DATA);
                myGdxGame.audioManager.buySound.play(0.2f);
            }

            if (icon_back.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
        }
    }

    @Override
    public void dispose() {
        myGdxGame.audioManager.buySound.dispose();
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

