package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ImageView;

public class CustomizationScreen implements Screen {
    MyGdxGame myGdxGame;
    private Texture background;
    private ImageView icon_color;
    private ImageView icon_hat;
//    private ImageView icon_bg;

    public CustomizationScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        background = new Texture(GameResources.BACKGROUND_SHOP);
        icon_color = new ImageView(50, 320, 140, 160, GameResources.ICON_COLOR);
        icon_hat = new ImageView(240, 320, 140, 160, GameResources.ICON_HAT);
    }

    @Override
    public void show() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
    }

    @Override
    public void render(float delta) {
//        handleInput();

        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background,  0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        icon_color.draw(myGdxGame.batch);
        icon_hat.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        icon_color.dispose();
        icon_hat.dispose();
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
