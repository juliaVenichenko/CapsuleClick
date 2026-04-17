package com.juliaVenichenko.capsuleclick.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextView extends View{
    BitmapFont font;
    String text;
    public TextView(BitmapFont font, float x, float y) {
        super(x, y);
        this.font = font;
    }
    public TextView(BitmapFont font, float x, float y, String text) {
        this(font, x, y);
        this.text = text;

        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }
    public void setText(String text) {
        this.text = text;
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }

    public void centerAt(float screenWidth, float y) {
        if (text == null || text.isEmpty()) {
            setPosition(screenWidth / 2f, y);
            return;
        }

        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        float textWidth = glyphLayout.width;
        float x = (screenWidth - textWidth) / 2f;
        setPosition(x, y);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch, text, x, y + height);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
