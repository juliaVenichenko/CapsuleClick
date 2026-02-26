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
import com.mygdx.game.components.ImageView;

public class ShopScreen implements Screen {
    MyGdxGame myGdxGame;
    private Texture background;
    private ImageView icon_game;
    private ImageView icon_upgrade;
    private ImageView icon_customization;

    public ShopScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        background = new Texture(GameResources.BACKGROUND_SHOP);
        icon_game = new ImageView(GameSettings.SCR_WIDTH - 90, GameSettings.SCR_HEIGHT - 80,
                85, 75, GameResources.ICON_GAME);
        icon_upgrade = new ImageView(90, 400, 160, 160, GameResources.BUTTON);
        icon_customization = new ImageView(350, 400, 160, 160, GameResources.BUTTON);
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
        icon_game.draw(myGdxGame.batch);
        icon_upgrade.draw(myGdxGame.batch);
        icon_customization.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        icon_game.dispose();
        icon_upgrade.dispose();
        icon_customization.dispose();
    }
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (icon_game.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
            if (icon_customization.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.customizationScreen);
            }
//            if (icon_upgrade.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
//                myGdxGame.setScreen(myGdxGame.upgradeScreen);
//            }
        }
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
