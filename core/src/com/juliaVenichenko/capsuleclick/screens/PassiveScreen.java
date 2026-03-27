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
import com.juliaVenichenko.capsuleclick.components.TextView;
import com.juliaVenichenko.capsuleclick.managers.FileManager;

public class PassiveScreen implements Screen {
    MyGdxGame myGdxGame;
    Texture background;
    private ButtonView passiveBtn;
    private TextView passiveText;
    private ButtonView icon_back;
    private FileManager fileManager;
    String curPassive = GameResources.ICON_OFF;
    String curPassiveText = "     Купить за 700 очков";

    public PassiveScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        background = new Texture(GameResources.BACKGROUND_SHOP);
        fileManager = new FileManager();

        passiveText = new TextView(myGdxGame.blackFontMulti, 80, 150, curPassiveText);

        icon_back = new ButtonView(GameSettings.SCR_WIDTH - 90, GameSettings.SCR_HEIGHT - 80,
                85, 75, GameResources.ICON_BACK);

        updatePassive();
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

        updatePassive();

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background,  0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);

        if (passiveBtn != null) {
            passiveBtn.draw(myGdxGame.batch);
        }

        passiveText.draw(myGdxGame.batch);

        icon_back.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (passiveBtn.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 700 && curPassive.equals(GameResources.ICON_OFF)) {
                System.out.println("Writing to file: 1");
                fileManager.writeToFile(1, GameResources.PASSIVE_DATA);
                GameSettings.SCORE -= 700;
                myGdxGame.audioManager.buySound.play(0.2f);
            }

            if (passiveBtn.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && curPassive.equals(GameResources.ICON_ON)) {
                System.out.println("Writing to file: 0");
                fileManager.writeToFile(0, GameResources.PASSIVE_DATA);
                myGdxGame.audioManager.buySound.play(0.2f);
            }

            if (icon_back.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
        }
    }

    private void updatePassive() {
        int index = fileManager.readFromFile(GameResources.PASSIVE_DATA);
        switch (index) {
            case 1:
                curPassive = GameResources.ICON_ON;
                curPassiveText = "Пассивный доход включён";
                break;
            case 0:
                curPassive = GameResources.ICON_OFF;
                curPassiveText = "    Купить за 700 очков";
        }
        // Освобождаем ресурсы старого объекта passiveBtn
        if (passiveBtn != null) {
            passiveBtn.dispose();
        }
        // Обновляем passiveBtn
        passiveBtn = new ButtonView(45, 300, 500, 200, curPassive);

        passiveText.setText(curPassiveText);
    }

    @Override
    public void dispose() {
        myGdxGame.audioManager.buySound.dispose();
        background.dispose();
        if (passiveBtn != null){
            passiveBtn.dispose();
        }
        passiveText.dispose();
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