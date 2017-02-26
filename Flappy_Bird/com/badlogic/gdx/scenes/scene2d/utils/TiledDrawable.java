package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TiledDrawable extends TextureRegionDrawable {
    public TiledDrawable(TextureRegion region) {
        super(region);
    }

    public TiledDrawable(TextureRegionDrawable drawable) {
        super(drawable);
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        float u2;
        float v;
        TextureRegion region = getRegion();
        float regionWidth = (float) region.getRegionWidth();
        float regionHeight = (float) region.getRegionHeight();
        float remainingX = width % regionWidth;
        float remainingY = height % regionHeight;
        float startX = x;
        float startY = y;
        float endX = (x + width) - remainingX;
        float endY = (y + height) - remainingY;
        while (x < endX) {
            y = startY;
            while (y < endY) {
                batch.draw(region, x, y, regionWidth, regionHeight);
                y += regionHeight;
            }
            x += regionWidth;
        }
        Texture texture = region.getTexture();
        float u = region.getU();
        float v2 = region.getV2();
        if (remainingX > 0.0f) {
            u2 = u + (remainingX / ((float) texture.getWidth()));
            v = region.getV();
            y = startY;
            while (y < endY) {
                batch.draw(texture, x, y, remainingX, regionHeight, u, v2, u2, v);
                y += regionHeight;
            }
            if (remainingY > 0.0f) {
                batch.draw(texture, x, y, remainingX, remainingY, u, v2, u2, v2 - (remainingY / ((float) texture.getHeight())));
            }
        }
        if (remainingY > 0.0f) {
            u2 = region.getU2();
            v = v2 - (remainingY / ((float) texture.getHeight()));
            for (x = startX; x < endX; x += regionWidth) {
                batch.draw(texture, x, y, regionWidth, remainingY, u, v2, u2, v);
            }
        }
    }
}
