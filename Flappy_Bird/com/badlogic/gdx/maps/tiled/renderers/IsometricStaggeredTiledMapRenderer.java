package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.google.android.gms.drive.events.CompletionEvent;

public class IsometricStaggeredTiledMapRenderer extends BatchTiledMapRenderer {
    private float[] vertices;

    public IsometricStaggeredTiledMapRenderer(TiledMap map) {
        super(map);
        this.vertices = new float[20];
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap map, SpriteBatch spriteBatch) {
        super(map, spriteBatch);
        this.vertices = new float[20];
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        this.vertices = new float[20];
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap map, float unitScale, SpriteBatch spriteBatch) {
        super(map, unitScale, spriteBatch);
        this.vertices = new float[20];
    }

    public void renderObject(MapObject object) {
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        Color batchColor = this.spriteBatch.getColor();
        float color = Color.toFloatBits(batchColor.f40r, batchColor.f39g, batchColor.f38b, batchColor.f37a * layer.getOpacity());
        float tileWidth = layer.getTileWidth() * this.unitScale;
        float halfTileWidth = tileWidth * 0.5f;
        float halfTileHeight = (layer.getTileHeight() * this.unitScale) * 0.5f;
        for (int y = layer.getHeight() - 1; y >= 0; y--) {
            for (int x = layer.getWidth() - 1; x >= 0; x--) {
                Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    TiledMapTile tile = cell.getTile();
                    if (tile != null) {
                        float temp;
                        boolean flipX = cell.getFlipHorizontally();
                        boolean flipY = cell.getFlipVertically();
                        int rotations = cell.getRotation();
                        TextureRegion region = tile.getTextureRegion();
                        float x1 = (((float) x) * tileWidth) - (y % 2 == 1 ? halfTileWidth : 0.0f);
                        float y1 = ((float) y) * halfTileHeight;
                        float x2 = x1 + (((float) region.getRegionWidth()) * this.unitScale);
                        float y2 = y1 + (((float) region.getRegionHeight()) * this.unitScale);
                        float u1 = region.getU();
                        float v1 = region.getV2();
                        float u2 = region.getU2();
                        float v2 = region.getV();
                        this.vertices[0] = x1;
                        this.vertices[1] = y1;
                        this.vertices[2] = color;
                        this.vertices[3] = u1;
                        this.vertices[4] = v1;
                        this.vertices[5] = x1;
                        this.vertices[6] = y2;
                        this.vertices[7] = color;
                        this.vertices[8] = u1;
                        this.vertices[9] = v2;
                        this.vertices[10] = x2;
                        this.vertices[11] = y2;
                        this.vertices[12] = color;
                        this.vertices[13] = u2;
                        this.vertices[14] = v2;
                        this.vertices[15] = x2;
                        this.vertices[16] = y1;
                        this.vertices[17] = color;
                        this.vertices[18] = u2;
                        this.vertices[19] = v1;
                        if (flipX) {
                            temp = this.vertices[3];
                            this.vertices[3] = this.vertices[13];
                            this.vertices[13] = temp;
                            temp = this.vertices[8];
                            this.vertices[8] = this.vertices[18];
                            this.vertices[18] = temp;
                        }
                        if (flipY) {
                            temp = this.vertices[4];
                            this.vertices[4] = this.vertices[14];
                            this.vertices[14] = temp;
                            temp = this.vertices[9];
                            this.vertices[9] = this.vertices[19];
                            this.vertices[19] = temp;
                        }
                        if (rotations != 0) {
                            float tempV;
                            float tempU;
                            switch (rotations) {
                                case CompletionEvent.STATUS_FAILURE /*1*/:
                                    tempV = this.vertices[4];
                                    this.vertices[4] = this.vertices[9];
                                    this.vertices[9] = this.vertices[14];
                                    this.vertices[14] = this.vertices[19];
                                    this.vertices[19] = tempV;
                                    tempU = this.vertices[3];
                                    this.vertices[3] = this.vertices[8];
                                    this.vertices[8] = this.vertices[13];
                                    this.vertices[13] = this.vertices[18];
                                    this.vertices[18] = tempU;
                                    break;
                                case CompletionEvent.STATUS_CONFLICT /*2*/:
                                    tempU = this.vertices[3];
                                    this.vertices[3] = this.vertices[13];
                                    this.vertices[13] = tempU;
                                    tempU = this.vertices[8];
                                    this.vertices[8] = this.vertices[18];
                                    this.vertices[18] = tempU;
                                    tempV = this.vertices[4];
                                    this.vertices[4] = this.vertices[14];
                                    this.vertices[14] = tempV;
                                    tempV = this.vertices[9];
                                    this.vertices[9] = this.vertices[19];
                                    this.vertices[19] = tempV;
                                    break;
                                case CompletionEvent.STATUS_CANCELED /*3*/:
                                    tempV = this.vertices[4];
                                    this.vertices[4] = this.vertices[19];
                                    this.vertices[19] = this.vertices[14];
                                    this.vertices[14] = this.vertices[9];
                                    this.vertices[9] = tempV;
                                    tempU = this.vertices[3];
                                    this.vertices[3] = this.vertices[18];
                                    this.vertices[18] = this.vertices[13];
                                    this.vertices[13] = this.vertices[8];
                                    this.vertices[8] = tempU;
                                    break;
                            }
                        }
                        this.spriteBatch.draw(region.getTexture(), this.vertices, 0, 20);
                    }
                }
            }
        }
    }
}
