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

public class GlassesScreen implements Screen {
    MyGdxGame myGdxGame;
    private Texture background;
    private ImageView glassesNull;
    private ImageView glassesBase;
    private ButtonView icon_back;
    private ButtonView buyGlassesNull;
    private ButtonView buyGlassesBase;
    private FileManager fileManager;

    public GlassesScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        background = new Texture(GameResources.BACKGROUND_SHOP);
        glassesNull = new ImageView(30, 500, 160, 140, GameResources.ICON_GLASSES_NULL);
        glassesBase = new ImageView(220, 500, 160, 140, GameResources.ICON_GLASSES_BASE);

        buyGlassesNull = new ButtonView(30, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "0 очков");
        buyGlassesBase = new ButtonView(220, 440, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "200 очков");

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
        glassesNull.draw(myGdxGame.batch);
        glassesBase.draw(myGdxGame.batch);
        buyGlassesNull.draw(myGdxGame.batch);
        buyGlassesBase.draw(myGdxGame.batch);
        icon_back.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buyGlassesNull.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                System.out.println("Writing to file: 0");
                fileManager.writeToFile(0, GameResources.GLASSES_DATA);
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyGlassesBase.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 200) {
                System.out.println("Writing to file: 1");
                fileManager.writeToFile(1, GameResources.GLASSES_DATA);
                GameSettings.SCORE -= 200;
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
        glassesNull.dispose();
        glassesBase.dispose();
        buyGlassesNull.dispose();
        buyGlassesBase.dispose();
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


