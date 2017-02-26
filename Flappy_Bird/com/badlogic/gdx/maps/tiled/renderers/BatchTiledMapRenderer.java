package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Iterator;

public abstract class BatchTiledMapRenderer implements TiledMapRenderer, Disposable {
    protected TiledMap map;
    protected boolean ownsSpriteBatch;
    protected SpriteBatch spriteBatch;
    protected float unitScale;
    protected Rectangle viewBounds;

    public TiledMap getMap() {
        return this.map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public float getUnitScale() {
        return this.unitScale;
    }

    public SpriteBatch getSpriteBatch() {
        return this.spriteBatch;
    }

    public Rectangle getViewBounds() {
        return this.viewBounds;
    }

    public BatchTiledMapRenderer(TiledMap map) {
        this(map, (float) TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public BatchTiledMapRenderer(TiledMap map, float unitScale) {
        this.map = map;
        this.unitScale = unitScale;
        this.viewBounds = new Rectangle();
        this.spriteBatch = new SpriteBatch();
        this.ownsSpriteBatch = true;
    }

    public BatchTiledMapRenderer(TiledMap map, SpriteBatch spriteBatch) {
        this(map, TextTrackStyle.DEFAULT_FONT_SCALE, spriteBatch);
    }

    public BatchTiledMapRenderer(TiledMap map, float unitScale, SpriteBatch spriteBatch) {
        this.map = map;
        this.unitScale = unitScale;
        this.viewBounds = new Rectangle();
        this.spriteBatch = spriteBatch;
        this.ownsSpriteBatch = false;
    }

    public void setView(OrthographicCamera camera) {
        this.spriteBatch.setProjectionMatrix(camera.combined);
        float width = camera.viewportWidth * camera.zoom;
        float height = camera.viewportHeight * camera.zoom;
        this.viewBounds.set(camera.position.f105x - (width / 2.0f), camera.position.f106y - (height / 2.0f), width, height);
    }

    public void setView(Matrix4 projection, float x, float y, float width, float height) {
        this.spriteBatch.setProjectionMatrix(projection);
        this.viewBounds.set(x, y, width, height);
    }

    public void render() {
        beginRender();
        Iterator it = this.map.getLayers().iterator();
        while (it.hasNext()) {
            MapLayer layer = (MapLayer) it.next();
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                } else {
                    Iterator i$ = layer.getObjects().iterator();
                    while (i$.hasNext()) {
                        renderObject((MapObject) i$.next());
                    }
                }
            }
        }
        endRender();
    }

    public void render(int[] layers) {
        beginRender();
        for (int layerIdx : layers) {
            MapLayer layer = this.map.getLayers().get(layerIdx);
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                } else {
                    Iterator i$ = layer.getObjects().iterator();
                    while (i$.hasNext()) {
                        renderObject((MapObject) i$.next());
                    }
                }
            }
        }
        endRender();
    }

    protected void beginRender() {
        AnimatedTiledMapTile.updateAnimationBaseTime();
        this.spriteBatch.begin();
    }

    protected void endRender() {
        this.spriteBatch.end();
    }

    public void dispose() {
        if (this.ownsSpriteBatch) {
            this.spriteBatch.dispose();
        }
    }
}
