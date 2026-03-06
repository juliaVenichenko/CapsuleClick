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
import com.mygdx.game.managers.FileManager;
import com.mygdx.game.util.AnimationUtil;

public class GameScreen implements Screen {
    MyGdxGame myGdxGame;
    private Texture background;
    private ImageView capsule;
    private TextView textScore;
    private ImageView icon_shop;
    private TextView textPowerClick;
    private TextView textUpgradePassive;
    private ImageView hat;
    String curHat = GameResources.HAT_NULL;
    String curColor = GameResources.CAPSULE_TMP;
    String curBackground = GameResources.BACKGROUND_DEFAULT;
    FileManager fileManager;
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

        fileManager = new FileManager();

        // Создаём тело капсулы
        updateColor();
        // Изначально создаем шапку
        updateHat();

        updateBackground();

        updatePowerScore();

        textScore = new TextView(myGdxGame.scoreFont, GameSettings.SCR_WIDTH / 2 - 20, GameSettings.SCR_HEIGHT - 110);
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
        eyes = AnimationUtil.getAnimationFromAtlas(atlas, 5f);
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

        // Проверяем и обновляем
        updateHat();
        updateColor();
        updateBackground();

        textScore.setText(formatScore(GameSettings.SCORE));
        textPowerClick.setText("Сила клика: " + GameSettings.UPGRADE_POWER);
        textUpgradePassive.setText("Пассивный доход:" + GameSettings.UPGRADE_PASSIVE);

        myGdxGame.batch.begin();

        if (background != null){
            myGdxGame.batch.draw(background,  0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        }

        if (capsule != null) {
            capsule.draw(myGdxGame.batch);
        }

        icon_shop.draw(myGdxGame.batch);

        textScore.draw(myGdxGame.batch);
        textPowerClick.draw(myGdxGame.batch);
        textUpgradePassive.draw(myGdxGame.batch);

        TextureRegion region = eyes.getKeyFrame(curTime, true);
        myGdxGame.batch.draw(region, 190, 300, 200f, 80f);

        // Отрисовка шапки
        if (hat != null) {
            hat.draw(myGdxGame.batch);
        }

        myGdxGame.batch.end();
    }

    private void updatePowerScore(){
        int index = fileManager.readFromFile(GameResources.LEVELS_DATA);
        switch (index) {
            case 2:
                GameSettings.UPGRADE_POWER = 2;
                break;
            case 3:
                GameSettings.UPGRADE_POWER = 3;
                break;
            case 4:
                GameSettings.UPGRADE_POWER = 4;
                break;
        }
    }

    private void updateHat() {
        int index = fileManager.readFromFile(GameResources.HATS_DATA); // Предполагается, что это возвращает индекс шапки
        switch (index) {
            case 1:
                curHat = GameResources.HAT_APPLE;
                break;
            case 2:
                curHat = GameResources.HAT_CAP;
                break;
            case 3:
                curHat = GameResources.HAT_CAPYBARA;
                break;
            case 0:
                curHat = GameResources.HAT_NULL;
                break;
        }
        // Освобождаем ресурсы старого объекта hat
        if (hat != null) {
            hat.dispose();
        }
        // Обновляем объект hat
        hat = new ImageView(133, 360, 387, 287, curHat);
    }

    private void updateColor() {
        int index = fileManager.readFromFile(GameResources.COLORS_DATA);
        switch (index) {
            case 1:
                curColor = GameResources.CAPSULE_GREEN;
                break;
            case 2:
                curColor = GameResources.CAPSULE_BLUE;
                break;
            case 3:
                curColor = GameResources.CAPSULE_RED;
                break;
            case 4:
                curColor = GameResources.CAPSULE_PLUM;
                break;
            case 5:
                curColor = GameResources.CAPSULE_YELLOW;
                break;
            case 6:
                curColor = GameResources.CAPSULE_ORANGE;
                break;
            case 0:
                curColor = GameResources.CAPSULE_TMP;
                break;
        }
        // Освобождаем ресурсы старого объекта capsule
        if (capsule != null) {
            capsule.dispose();
        }
        // Обновляем capsule
        capsule = new ImageView(120, 60, 350, 490, curColor);
    }

    private void updateBackground() {
        int index = fileManager.readFromFile(GameResources.BACKGROUNDS_DATA);
        switch (index) {
            case 1:
                curBackground = GameResources.BACKGROUND_PLANET;
                break;
            case 2:
                curBackground = GameResources.BACKGROUND_MOUNTAINS;
                break;
            case 3:
                curBackground = GameResources.BACKGROUND_RAINBOW;
                break;
            case 4:
                curBackground = GameResources.BACKGROUND_EPIC;
                break;
            case 0:
                curBackground = GameResources.BACKGROUND_DEFAULT;
                break;
        }
        // Освобождаем ресурсы старого объекта background
        if (background != null) {
            background.dispose();
        }
        // Обновляем background
        background = new Texture(curBackground);
    }

    @Override
    public void dispose() {
        if (background != null){
            background.dispose();
        }

        if(capsule != null){
            capsule.dispose();
        }
        if (textScore != null) {
            textScore.dispose();
//            textScore = null; // Обнуляем ссылку для предотвращения дальнейшего использования
        }
        if (textPowerClick != null) {
            textPowerClick.dispose();
//            textPowerClick = null;
        }
        if (textUpgradePassive != null) {
            textUpgradePassive.dispose();
//            textUpgradePassive = null;
        }
        icon_shop.dispose();

        if(hat != null){
            hat.dispose();
        }

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
                updatePowerScore();
                GameSettings.SCORE += GameSettings.UPGRADE_POWER;
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
