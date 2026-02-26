package com.mygdx.game.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationUtil {
    private AnimationUtil(){}
    public static Animation<TextureRegion> getAnimationFromAtlas(TextureAtlas atlas, float timeAnimation){
        Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();
        Animation<TextureRegion> textureRegionAnimation = new Animation<>(timeAnimation / regions.size, regions);
        return textureRegionAnimation;
    }
}
