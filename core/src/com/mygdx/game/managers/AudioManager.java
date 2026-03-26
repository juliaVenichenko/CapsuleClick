package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameResources;

public class AudioManager {
    public Music gameMusic;
    public Sound buySound;
    public Sound laughterSound;

    public AudioManager(){
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.GAME_MUSIC));
        buySound = Gdx.audio.newSound(Gdx.files.internal(GameResources.BUY_SOUND));
        laughterSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.LAUGHTER_SOUND));

        gameMusic.setVolume(0.5f);
        gameMusic.setLooping(true);
    }
}
