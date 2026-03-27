package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class BackgroundsScreen implements Screen {
    MyGdxGame myGdxGame;
    private Texture background;
    private ImageView bgDefault;
    private ImageView bgPlanet;
    private ImageView bgMountains;
    private ImageView bgRainbow;
    private ImageView bgEpic;
    private ButtonView icon_back;
    private ButtonView buyBgDefault;
    private ButtonView buyBgPlanet;
    private ButtonView buyBgMountains;
    private ButtonView buyBgRainbow;
    private ButtonView buyBgEpic;

    private FileManager fileManager;

    public BackgroundsScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        background = new Texture(GameResources.BACKGROUND_SHOP);
        bgDefault = new ImageView(30, 500, 160, 140, GameResources.ICON_BG_DEFAULT);
        bgPlanet = new ImageView(220, 500, 160, 140, GameResources.ICON_BG_PLANET);
        bgMountains = new ImageView(410, 500, 160, 140, GameResources.ICON_BG_MOUNTAINS);
        bgRainbow = new ImageView(30, 190, 160, 140, GameResources.ICON_BG_RAINBOW);
        bgEpic = new ImageView(220, 190, 160, 140, GameResources.ICON_BG_EPIC);

        buyBgDefault = new ButtonView(30, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "0 очков");
        buyBgPlanet = new ButtonView(220, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "60 очков");
        buyBgMountains = new ButtonView(410, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "250 очков");
        buyBgRainbow = new ButtonView(30, 130, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "380 очков");
        buyBgEpic = new ButtonView(220, 130, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "650 очков");

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
        bgDefault.draw(myGdxGame.batch);
        bgPlanet.draw(myGdxGame.batch);
        bgMountains.draw(myGdxGame.batch);
        bgRainbow.draw(myGdxGame.batch);
        bgEpic.draw(myGdxGame.batch);

        buyBgDefault.draw(myGdxGame.batch);
        buyBgPlanet.draw(myGdxGame.batch);
        buyBgMountains.draw(myGdxGame.batch);
        buyBgRainbow.draw(myGdxGame.batch);
        buyBgEpic.draw(myGdxGame.batch);

        icon_back.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buyBgDefault.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                System.out.println("Writing to file: 0");
                fileManager.writeToFile(0, GameResources.BACKGROUNDS_DATA);
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyBgPlanet.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 60) {
                System.out.println("Writing to file: 1");
                fileManager.writeToFile(1, GameResources.BACKGROUNDS_DATA);
                GameSettings.SCORE -= 60;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyBgMountains.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 250) {
                System.out.println("Writing to file: 2");
                fileManager.writeToFile(2, GameResources.BACKGROUNDS_DATA);
                GameSettings.SCORE -= 250;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyBgRainbow.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 380) {
                System.out.println("Writing to file: 3");
                fileManager.writeToFile(3, GameResources.BACKGROUNDS_DATA);
                GameSettings.SCORE -= 380;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyBgEpic.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 650) {
                System.out.println("Writing to file: 4");
                fileManager.writeToFile(4, GameResources.BACKGROUNDS_DATA);
                GameSettings.SCORE -= 650;
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
        bgDefault.dispose();
        bgPlanet.dispose();
        bgMountains.dispose();
        bgRainbow.dispose();
        bgEpic.dispose();

        buyBgDefault.dispose();
        buyBgPlanet.dispose();
        buyBgMountains.dispose();
        buyBgRainbow.dispose();
        buyBgEpic.dispose();
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

