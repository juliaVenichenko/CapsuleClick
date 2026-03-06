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
        bgDefault = new ImageView(50, 500, 140, 160, GameResources.ICON_BG_DEFAULT);
        bgPlanet = new ImageView(240, 500, 140, 160, GameResources.ICON_BG_PLANET);
        bgMountains = new ImageView(430, 500, 140, 160, GameResources.ICON_BG_MOUNTAINS);
        bgRainbow = new ImageView(50, 190, 140, 160, GameResources.ICON_BG_RAINBOW);
        bgEpic = new ImageView(240, 190, 140, 160, GameResources.ICON_BG_EPIC);

        buyBgDefault = new ButtonView(50, 440, 140, 70, myGdxGame.defaultFont, GameResources.BUTTON, "0 монет");
        buyBgPlanet = new ButtonView(240, 440, 140, 70, myGdxGame.defaultFont, GameResources.BUTTON, "10 монет");
        buyBgMountains = new ButtonView(430, 440, 140, 70, myGdxGame.defaultFont, GameResources.BUTTON, "10 монет");
        buyBgRainbow = new ButtonView(50, 130, 140, 70, myGdxGame.defaultFont, GameResources.BUTTON, "10 монет");
        buyBgEpic = new ButtonView(240, 130, 140, 70, myGdxGame.defaultFont, GameResources.BUTTON, "10 монет");

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
            }
            if (buyBgPlanet.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 10) {
                System.out.println("Writing to file: 1");
                fileManager.writeToFile(1, GameResources.BACKGROUNDS_DATA);
                GameSettings.SCORE -= 10;
            }
            if (buyBgMountains.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 10) {
                System.out.println("Writing to file: 2");
                fileManager.writeToFile(2, GameResources.BACKGROUNDS_DATA);
                GameSettings.SCORE -= 10;
            }
            if (buyBgRainbow.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 10) {
                System.out.println("Writing to file: 3");
                fileManager.writeToFile(3, GameResources.BACKGROUNDS_DATA);
                GameSettings.SCORE -= 10;
            }
            if (buyBgEpic.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 10) {
                System.out.println("Writing to file: 4");
                fileManager.writeToFile(4, GameResources.BACKGROUNDS_DATA);
                GameSettings.SCORE -= 10;
            }

            if (icon_back.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.customizationScreen);
            }
        }
    }

    @Override
    public void dispose() {
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

