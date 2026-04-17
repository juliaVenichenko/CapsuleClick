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

public class AccessoriesScreen implements Screen {
    MyGdxGame myGdxGame;
    private Texture background;
    private ImageView accessoriesNull;
    private ImageView iconBag;
    private ImageView iconCircle;
    private ButtonView icon_back;
    private ButtonView buyAccessoriesNull;
    private ButtonView buyBag;
    private ButtonView buyCircle;
    private FileManager fileManager;

    public AccessoriesScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        background = new Texture(GameResources.BACKGROUND_SHOP);
        accessoriesNull = new ImageView(30, 500, 160, 140, GameResources.ICON_ACCESSORIES_NULL);
        iconBag = new ImageView(220, 500, 160, 140, GameResources.ICON_ACCESSORIES_BAG);
        iconCircle = new ImageView(410, 500, 160, 140, GameResources.ICON_ACCESSORIES_CIRCLE);

        buyAccessoriesNull = new ButtonView(30, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "0 очков");
        buyBag = new ButtonView(220, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "250 очков");
        buyCircle = new ButtonView(410, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "400 очков");

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
        accessoriesNull.draw(myGdxGame.batch);
        iconBag.draw(myGdxGame.batch);
        iconCircle.draw(myGdxGame.batch);
        buyAccessoriesNull.draw(myGdxGame.batch);
        buyBag.draw(myGdxGame.batch);
        buyCircle.draw(myGdxGame.batch);
        icon_back.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buyAccessoriesNull.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                System.out.println("Writing to file: 0");
                fileManager.writeToFile(0, GameResources.ACCESSORIES_DATA);
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyBag.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 250) {
                System.out.println("Writing to file: 1");
                fileManager.writeToFile(1, GameResources.ACCESSORIES_DATA);
                GameSettings.SCORE -= 250;
                myGdxGame.audioManager.buySound.play(0.2f);
            }

            if (buyCircle.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 400) {
                System.out.println("Writing to file: 2");
                fileManager.writeToFile(2, GameResources.ACCESSORIES_DATA);
                GameSettings.SCORE -= 400;
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
        accessoriesNull.dispose();
        iconBag.dispose();
        iconCircle.dispose();
        buyAccessoriesNull.dispose();
        buyBag.dispose();
        buyCircle.dispose();
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

