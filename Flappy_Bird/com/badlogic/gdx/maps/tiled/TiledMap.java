package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import java.util.Iterator;

public class TiledMap extends Map {
    private Array<? extends Disposable> ownedResources;
    private TiledMapTileSets tilesets;

    public TiledMapTileSets getTileSets() {
        return this.tilesets;
    }

    public TiledMap() {
        this.tilesets = new TiledMapTileSets();
    }

    public void setOwnedResources(Array<? extends Disposable> resources) {
        this.ownedResources = resources;
    }

    public void dispose() {
        if (this.ownedResources != null) {
            Iterator i$ = this.ownedResources.iterator();
            while (i$.hasNext()) {
                ((Disposable) i$.next()).dispose();
            }
        }
    }
}
