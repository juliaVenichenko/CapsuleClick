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

public class ColorsScreen implements Screen {
    MyGdxGame myGdxGame;
    private Texture background;
    private ImageView colorGreen;
    private ImageView colorBlue;
    private ImageView colorRed;
    private ImageView colorPlum;
    private ImageView colorYellow;
    private ImageView colorOrange;
    private ImageView colorWhite;

    private ButtonView icon_back;
    private ButtonView buyColorGreen;
    private ButtonView buyColorBlue;
    private ButtonView buyColorRed;
    private ButtonView buyColorPlum;
    private ButtonView buyColorYellow;
    private ButtonView buyColorOrange;
    private ButtonView buyColorWhite;

    private FileManager fileManager;

    public ColorsScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        background = new Texture(GameResources.BACKGROUND_SHOP);
        colorWhite = new ImageView(30, 570, 160, 120, GameResources.ICON_COLOR_WHITE);
        colorGreen = new ImageView(220, 570, 160, 120, GameResources.ICON_COLOR_GREEN);
        colorBlue = new ImageView(410, 570, 160, 120, GameResources.ICON_COLOR_BLUE);
        colorRed = new ImageView(30, 330, 160, 120, GameResources.ICON_COLOR_RED);
        colorPlum = new ImageView(220, 330, 160, 120, GameResources.ICON_COLOR_LILAC);
        colorYellow = new ImageView(410, 330, 160, 120, GameResources.ICON_COLOR_YELLOW);
        colorOrange = new ImageView(30, 90, 160, 120, GameResources.ICON_COLOR_ORANGE);

        buyColorWhite = new ButtonView(30, 510, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "0 очков");
        buyColorGreen = new ButtonView(220, 510, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "40 очков");
        buyColorBlue = new ButtonView(410, 510, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "80 очков");
        buyColorRed = new ButtonView(30, 270, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "150 очков");
        buyColorPlum = new ButtonView(220, 270, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "200 очков");
        buyColorYellow = new ButtonView(410, 270, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "300 очков");
        buyColorOrange = new ButtonView(30, 30, 160, 70, myGdxGame.defaultFont, GameResources.BUTTON, "450 очков");

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
        colorWhite.draw(myGdxGame.batch);
        colorGreen.draw(myGdxGame.batch);
        colorBlue.draw(myGdxGame.batch);
        colorRed.draw(myGdxGame.batch);
        colorPlum.draw(myGdxGame.batch);
        colorYellow.draw(myGdxGame.batch);
        colorOrange.draw(myGdxGame.batch);

        buyColorWhite.draw(myGdxGame.batch);
        buyColorGreen.draw(myGdxGame.batch);
        buyColorBlue.draw(myGdxGame.batch);
        buyColorRed.draw(myGdxGame.batch);
        buyColorPlum.draw(myGdxGame.batch);
        buyColorYellow.draw(myGdxGame.batch);
        buyColorOrange.draw(myGdxGame.batch);

        icon_back.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buyColorWhite.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                System.out.println("Writing to file: 0");
                fileManager.writeToFile(0, GameResources.COLORS_DATA);
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyColorGreen.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 40) {
                System.out.println("Writing to file: 1");
                fileManager.writeToFile(1, GameResources.COLORS_DATA);
                GameSettings.SCORE -= 40;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyColorBlue.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 80) {
                System.out.println("Writing to file: 2");
                fileManager.writeToFile(2, GameResources.COLORS_DATA);
                GameSettings.SCORE -= 80;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyColorRed.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 150) {
                System.out.println("Writing to file: 3");
                fileManager.writeToFile(3, GameResources.COLORS_DATA);
                GameSettings.SCORE -= 150;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyColorPlum.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 200) {
                System.out.println("Writing to file: 4");
                fileManager.writeToFile(4, GameResources.COLORS_DATA);
                GameSettings.SCORE -= 200;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyColorYellow.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 300) {
                System.out.println("Writing to file: 5");
                fileManager.writeToFile(5, GameResources.COLORS_DATA);
                GameSettings.SCORE -= 300;
                myGdxGame.audioManager.buySound.play(0.2f);
            }
            if (buyColorOrange.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 450) {
                System.out.println("Writing to file: 6");
                fileManager.writeToFile(6, GameResources.COLORS_DATA);
                GameSettings.SCORE -= 450;
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
        colorWhite.dispose();
        colorGreen.dispose();
        colorBlue.dispose();
        colorRed.dispose();
        colorPlum.dispose();
        colorYellow.dispose();
        colorOrange.dispose();

        buyColorWhite.dispose();
        buyColorGreen.dispose();
        buyColorBlue.dispose();
        buyColorRed.dispose();
        buyColorPlum.dispose();
        buyColorYellow.dispose();
        buyColorOrange.dispose();
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

