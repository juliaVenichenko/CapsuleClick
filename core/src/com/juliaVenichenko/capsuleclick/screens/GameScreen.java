package com.juliaVenichenko.capsuleclick.screens;

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
import com.juliaVenichenko.capsuleclick.GameResources;
import com.juliaVenichenko.capsuleclick.GameSettings;
import com.juliaVenichenko.capsuleclick.MyGdxGame;
import com.juliaVenichenko.capsuleclick.components.ImageView;
import com.juliaVenichenko.capsuleclick.components.TextView;
import com.juliaVenichenko.capsuleclick.managers.FileManager;
import com.juliaVenichenko.capsuleclick.util.AnimationUtil;

public class GameScreen implements Screen {
    MyGdxGame myGdxGame;
    private Texture background;
    private ImageView capsule;
    private TextView textScore;
    private ImageView icon_shop;
    private ImageView icon_music;
    private ImageView icon_sound;
    private TextView textPowerClick;
    private ImageView hat;
    private ImageView waferScore;
    String curHat = GameResources.HAT_NULL;
    String curColor = GameResources.CAPSULE_TMP;
    String curBackground = GameResources.BACKGROUND_DEFAULT;
    FileManager fileManager;
    private boolean onPassive = false;
    boolean isPauseMusic = false;
    boolean isPauseSound = false;

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

        activatePassive();

        updateMusic();
        updateSound();

        textScore = new TextView(myGdxGame.scoreFont, GameSettings.SCR_WIDTH / 2 - 40, GameSettings.SCR_HEIGHT - 110);
        textPowerClick = new TextView(myGdxGame.blackFontBasic, 15, 40);
        waferScore = new ImageView(-20, 10, 250, 80, GameResources.BUTTON);
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

        activatePassive();

        if (onPassive) {
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
        updateMusic();
        updateSound();

        textScore.setText(GameSettings.SCORE + "");
        textPowerClick.setText("Сила клика: " + GameSettings.UPGRADE_POWER);

        myGdxGame.batch.begin();

        if (background != null){
            myGdxGame.batch.draw(background,  0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        }

        if (capsule != null) {
            capsule.draw(myGdxGame.batch);
        }

        icon_shop.draw(myGdxGame.batch);

        waferScore.draw(myGdxGame.batch);
        textScore.draw(myGdxGame.batch);
        textPowerClick.draw(myGdxGame.batch);

        TextureRegion region = eyes.getKeyFrame(curTime, true);
        myGdxGame.batch.draw(region, 200, 300, 200f, 80f);

        // Отрисовка шапки
        if (hat != null) {
            hat.draw(myGdxGame.batch);
        }

        if (icon_music != null) {
            icon_music.draw(myGdxGame.batch);
        }

        if (icon_sound != null) {
            icon_sound.draw(myGdxGame.batch);
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
        hat = new ImageView(143, 360, 387, 287, curHat);
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
        capsule = new ImageView(130, 60, 350, 490, curColor);
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

    private void activatePassive(){
        int index = fileManager.readFromFile(GameResources.PASSIVE_DATA);
        switch (index) {
            case 1:
                onPassive = true;
                break;
            case 0:
                onPassive = false;
                break;
        }
    }

    @Override
    public void dispose() {
        myGdxGame.audioManager.gameMusic.dispose();
        myGdxGame.audioManager.laughterSound.dispose();
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
        icon_shop.dispose();

        if (hat != null){
            hat.dispose();
        }

        if (icon_music != null){
            icon_music.dispose();
        }

        if (icon_sound != null){
            icon_sound.dispose();
        }

        waferScore.dispose();

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
                if (isPauseSound == false){
                    myGdxGame.audioManager.laughterSound.play(0.25f);
                }
            }

            if (icon_shop.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.shopScreen);
            }

            if (icon_music.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                if (isPauseMusic == false){
                    isPauseMusic = true;
                    myGdxGame.audioManager.gameMusic.pause();
                }
                else if (isPauseMusic == true){
                    isPauseMusic = false;
                    myGdxGame.audioManager.gameMusic.play();
                }
            }

            if (icon_sound.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                if (isPauseSound == false){
                    isPauseSound = true;
                }
                else if (isPauseSound == true){
                    isPauseSound = false;
                }
            }
        }
    }

    private void updateMusic(){

            if (icon_music != null) {
                icon_music.dispose();
            }

            icon_music = new ImageView(5, GameSettings.SCR_HEIGHT - 80,
                    85, 75, isPauseMusic ? GameResources.ICON_VOLUME_OFF : GameResources.ICON_VOLUME);
    }

    private void updateSound(){

        if (icon_sound != null) {
            icon_sound.dispose();
        }

        icon_sound = new ImageView(5, GameSettings.SCR_HEIGHT - 160,
                85, 75, isPauseSound ? GameResources.ICON_SOUND_OFF : GameResources.ICON_SOUND);
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
