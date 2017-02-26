package com.badlogic.gdx.maps.tiled.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTile.BlendMode;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class AnimatedTiledMapTile implements TiledMapTile {
    private static final long initialTimeOffset;
    private static long lastTiledMapRenderTime;
    private float animationInterval;
    private BlendMode blendMode;
    private long frameCount;
    private Array<StaticTiledMapTile> frameTiles;
    private int id;
    private MapProperties properties;

    static {
        lastTiledMapRenderTime = 0;
        initialTimeOffset = TimeUtils.millis();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BlendMode getBlendMode() {
        return this.blendMode;
    }

    public void setBlendMode(BlendMode blendMode) {
        this.blendMode = blendMode;
    }

    public TextureRegion getTextureRegion() {
        return ((StaticTiledMapTile) this.frameTiles.get((int) ((lastTiledMapRenderTime / ((long) (this.animationInterval * 1000.0f))) % this.frameCount))).getTextureRegion();
    }

    public MapProperties getProperties() {
        if (this.properties == null) {
            this.properties = new MapProperties();
        }
        return this.properties;
    }

    public static void updateAnimationBaseTime() {
        lastTiledMapRenderTime = TimeUtils.millis() - initialTimeOffset;
    }

    public AnimatedTiledMapTile(float interval, Array<StaticTiledMapTile> frameTiles) {
        this.blendMode = BlendMode.ALPHA;
        this.frameCount = 0;
        this.frameTiles = frameTiles;
        this.animationInterval = interval;
        this.frameCount = (long) frameTiles.size;
    }
}
