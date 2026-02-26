package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.ImageView;
import com.mygdx.game.components.TextView;
import com.mygdx.game.util.AnimationUtil;

public class GameScreen implements Screen {
    MyGdxGame myGdxGame;
    private Texture background;
    private ImageView capsule;
    private TextView textScore;
    private ImageView icon_shop;
    private TextView textPowerClick;
    private TextView textUpgradePassive;
    private boolean isUpgradePassive = false;

    private float passiveIncomeTimer = 0;
    private static final float PASSIVE_INCOME_INTERVAL = 1.0f;

    private Animation<TextureRegion> eyes;
    protected Array<TextureAtlas> textureAtlasArray;
    private float curTime;
    private TextureAtlas atlas;


    public GameScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        curTime = 0;

        background = new Texture(GameResources.BACKGROUND_DEFAULT);
        capsule = new ImageView(140, 100, 350, 490, GameResources.CAPSULE_TMP);

        textScore = new TextView(myGdxGame.scoreFont, GameSettings.SCR_WIDTH / 2, GameSettings.SCR_HEIGHT - 110);
        textPowerClick = new TextView(myGdxGame.defaultFont, GameSettings.SCR_WIDTH / 25, GameSettings.SCR_HEIGHT / 25);
        textUpgradePassive = new TextView(myGdxGame.defaultFont, GameSettings.SCR_WIDTH / 1.4f, GameSettings.SCR_HEIGHT / 25);
        icon_shop = new ImageView(GameSettings.SCR_WIDTH - 90, GameSettings.SCR_HEIGHT - 80,
                85, 75, GameResources.ICON_SHOP);
        
    }
    @Override
    public void show() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        initAnimation();
    }

    private void initAnimation(){
        if (atlas != null) {
            atlas.dispose();
        }
        atlas = new TextureAtlas("eyes.txt");
        eyes = AnimationUtil.getAnimationFromAtlas(atlas, 6f);

    }

    @Override
    public void render(float delta) {
        handleInput();

        ScreenUtils.clear(Color.CLEAR);

        float dTime = Gdx.graphics.getDeltaTime();
        curTime += dTime;

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        if (isUpgradePassive) {
            passiveIncomeTimer += delta;
            if (passiveIncomeTimer >= PASSIVE_INCOME_INTERVAL) {
                GameSettings.SCORE += GameSettings.UPGRADE_PASSIVE;
                passiveIncomeTimer = 0;
            }
        }

        textScore.setText(formatScore(GameSettings.SCORE));
        textPowerClick.setText("Сила клика: " + GameSettings.UPGRADE_SCORE);
        textUpgradePassive.setText("Пассивный доход:" + GameSettings.UPGRADE_PASSIVE);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background,  0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        capsule.draw(myGdxGame.batch);
        icon_shop.draw(myGdxGame.batch);

        textScore.draw(myGdxGame.batch);
        textPowerClick.draw(myGdxGame.batch);
        textUpgradePassive.draw(myGdxGame.batch);

        TextureRegion region = eyes.getKeyFrame(curTime, true);
        myGdxGame.batch.draw(region, 210, 340, 200f, 80f);

        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        capsule.dispose();
        if (textScore != null) {
            textScore.dispose();
//            textScore = null; // Обнуляем ссылку для предотвращения дальнейшего использования
        }
        if (textPowerClick != null) {
            textPowerClick.dispose();
//            textPowerClick = null; // Обнуляем ссылку
        }
        if (textUpgradePassive != null) {
            textUpgradePassive.dispose();
//            textUpgradePassive = null; // Обнуляем ссылку
        }
        icon_shop.dispose();

        if (textureAtlasArray != null){
            for (TextureAtlas atlas : textureAtlasArray) {
                atlas.dispose();
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (capsule.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameSettings.SCORE += GameSettings.UPGRADE_SCORE;
            }
//            // Реализовать запись текущей мощности клика в файл!
//            if (upgradeClick.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
//                GameSettings.SCORE -= GameSettings.UPGRADE_SCORE;
//                GameSettings.UPGRADE_SCORE *= 2;
//            }
//            // Реализовать запись текущего пассивного дохода в файл!
//            if (upgradePassive.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
//                isUpgradePassive = true;
//                GameSettings.UPGRADE_PASSIVE += 1;
//                GameSettings.SCORE -= GameSettings.UPGRADE_PASSIVE;
//            }
            if (icon_shop.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.shopScreen);
            }
        }
    }

    private String formatScore(int score) {
        if (score < 1000) {
            return String.valueOf(score);
        } else {
            double formattedScore = score / 1000.0;
            return String.format("%.1fk", formattedScore).replace(",", ".");
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
