package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapRenderer;

public interface TiledMapRenderer extends MapRenderer {
    void renderObject(MapObject mapObject);

    void renderTileLayer(TiledMapTileLayer tiledMapTileLayer);
}
