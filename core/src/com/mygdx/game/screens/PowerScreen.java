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
import com.mygdx.game.components.TextView;
import com.mygdx.game.managers.FileManager;

public class PowerScreen implements Screen {
    MyGdxGame myGdxGame;
    Texture background;
    private ButtonView levels;
    private TextView levelsText;
    private ButtonView icon_back;
    private FileManager fileManager;
    String curLevel = GameResources.ICON_LEVEL1;
    String curLevelText = "Купить за 10 очков";

    public PowerScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        background = new Texture(GameResources.BACKGROUND_SHOP);
        fileManager = new FileManager();

        levelsText = new TextView(myGdxGame.blackFontMulti, 140, 150, curLevelText);

        icon_back = new ButtonView(GameSettings.SCR_WIDTH - 90, GameSettings.SCR_HEIGHT - 80,
                85, 75, GameResources.ICON_BACK);

        updateLevel();
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

        updateLevel();

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background,  0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);

        if (levels != null) {
            levels.draw(myGdxGame.batch);
        }

        levelsText.draw(myGdxGame.batch);

        icon_back.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (levels.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 10 && curLevel.equals(GameResources.ICON_LEVEL1)) {
                System.out.println("Writing to file: 2");
                fileManager.writeToFile(2, GameResources.LEVELS_DATA);
                GameSettings.UPGRADE_POWER = 2;
                GameSettings.SCORE -= 10;
                myGdxGame.audioManager.buySound.play(0.2f);
            }

            if (levels.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 20 && curLevel.equals(GameResources.ICON_LEVEL2)) {
                System.out.println("Writing to file: 3");
                fileManager.writeToFile(3, GameResources.LEVELS_DATA);
                GameSettings.UPGRADE_POWER = 3;
                GameSettings.SCORE -= 20;
                myGdxGame.audioManager.buySound.play(0.2f);
            }

            if (levels.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && GameSettings.SCORE >= 30 && curLevel.equals(GameResources.ICON_LEVEL3)) {
                System.out.println("Writing to file: 4");
                fileManager.writeToFile(4, GameResources.LEVELS_DATA);
                GameSettings.UPGRADE_POWER = 4;
                GameSettings.SCORE -= 30;
                myGdxGame.audioManager.buySound.play(0.2f);
            }

            if (icon_back.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.shopScreen);
            }
        }
    }

    private void updateLevel() {
        int index = fileManager.readFromFile(GameResources.LEVELS_DATA);
        switch (index) {
            case 1:
                curLevel = GameResources.ICON_LEVEL1;
                break;
            case 2:
                curLevel = GameResources.ICON_LEVEL2;
                curLevelText = "Купить за 20 очков";
                break;
            case 3:
                curLevel = GameResources.ICON_LEVEL3;
                curLevelText = "Купить за 30 очков";
                break;
            case 4:
                curLevel = GameResources.ICON_LEVEL4;
                curLevelText = "Купить за 0 очков";
                break;
        }
        // Освобождаем ресурсы старого объекта levels
        if (levels != null) {
            levels.dispose();
        }
        // Обновляем levels
        levels = new ButtonView(45, 300, 500, 200, curLevel);

        levelsText.setText(curLevelText);
    }

    @Override
    public void dispose() {
        myGdxGame.audioManager.buySound.dispose();
        background.dispose();
        if (levels != null){
            levels.dispose();
        }
        levelsText.dispose();
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