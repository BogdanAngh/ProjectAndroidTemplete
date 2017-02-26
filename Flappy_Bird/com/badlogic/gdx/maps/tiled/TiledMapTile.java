package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;

public interface TiledMapTile {

    public enum BlendMode {
        NONE,
        ALPHA
    }

    BlendMode getBlendMode();

    int getId();

    MapProperties getProperties();

    TextureRegion getTextureRegion();

    void setBlendMode(BlendMode blendMode);

    void setId(int i);
}
