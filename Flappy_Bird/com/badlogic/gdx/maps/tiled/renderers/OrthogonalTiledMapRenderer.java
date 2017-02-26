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

public class OrthogonalTiledMapRenderer extends BatchTiledMapRenderer {
    private float[] vertices;

    public OrthogonalTiledMapRenderer(TiledMap map) {
        super(map);
        this.vertices = new float[20];
    }

    public OrthogonalTiledMapRenderer(TiledMap map, SpriteBatch spriteBatch) {
        super(map, spriteBatch);
        this.vertices = new float[20];
    }

    public OrthogonalTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        this.vertices = new float[20];
    }

    public OrthogonalTiledMapRenderer(TiledMap map, float unitScale, SpriteBatch spriteBatch) {
        super(map, unitScale, spriteBatch);
        this.vertices = new float[20];
    }

    public void renderObject(MapObject object) {
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        Color batchColor = this.spriteBatch.getColor();
        float color = Color.toFloatBits(batchColor.f40r, batchColor.f39g, batchColor.f38b, batchColor.f37a * layer.getOpacity());
        int layerWidth = layer.getWidth();
        int layerHeight = layer.getHeight();
        float layerTileWidth = layer.getTileWidth() * this.unitScale;
        float layerTileHeight = layer.getTileHeight() * this.unitScale;
        int col1 = Math.max(0, (int) (this.viewBounds.f75x / layerTileWidth));
        int col2 = Math.min(layerWidth, (int) (((this.viewBounds.f75x + this.viewBounds.width) + layerTileWidth) / layerTileWidth));
        int row1 = Math.max(0, (int) (this.viewBounds.f76y / layerTileHeight));
        int row2 = Math.min(layerHeight, (int) (((this.viewBounds.f76y + this.viewBounds.height) + layerTileHeight) / layerTileHeight));
        float y = ((float) row1) * layerTileHeight;
        float xStart = ((float) col1) * layerTileWidth;
        float[] vertices = this.vertices;
        for (int row = row1; row < row2; row++) {
            float x = xStart;
            for (int col = col1; col < col2; col++) {
                Cell cell = layer.getCell(col, row);
                if (cell == null) {
                    x += layerTileWidth;
                } else {
                    TiledMapTile tile = cell.getTile();
                    if (tile != null) {
                        float temp;
                        boolean flipX = cell.getFlipHorizontally();
                        boolean flipY = cell.getFlipVertically();
                        int rotations = cell.getRotation();
                        TextureRegion region = tile.getTextureRegion();
                        float x1 = x;
                        float y1 = y;
                        float x2 = x1 + (((float) region.getRegionWidth()) * this.unitScale);
                        float y2 = y1 + (((float) region.getRegionHeight()) * this.unitScale);
                        float u1 = region.getU();
                        float v1 = region.getV2();
                        float u2 = region.getU2();
                        float v2 = region.getV();
                        vertices[0] = x1;
                        vertices[1] = y1;
                        vertices[2] = color;
                        vertices[3] = u1;
                        vertices[4] = v1;
                        vertices[5] = x1;
                        vertices[6] = y2;
                        vertices[7] = color;
                        vertices[8] = u1;
                        vertices[9] = v2;
                        vertices[10] = x2;
                        vertices[11] = y2;
                        vertices[12] = color;
                        vertices[13] = u2;
                        vertices[14] = v2;
                        vertices[15] = x2;
                        vertices[16] = y1;
                        vertices[17] = color;
                        vertices[18] = u2;
                        vertices[19] = v1;
                        if (flipX) {
                            temp = vertices[3];
                            vertices[3] = vertices[13];
                            vertices[13] = temp;
                            temp = vertices[8];
                            vertices[8] = vertices[18];
                            vertices[18] = temp;
                        }
                        if (flipY) {
                            temp = vertices[4];
                            vertices[4] = vertices[14];
                            vertices[14] = temp;
                            temp = vertices[9];
                            vertices[9] = vertices[19];
                            vertices[19] = temp;
                        }
                        if (rotations != 0) {
                            float tempV;
                            float tempU;
                            switch (rotations) {
                                case CompletionEvent.STATUS_FAILURE /*1*/:
                                    tempV = vertices[4];
                                    vertices[4] = vertices[9];
                                    vertices[9] = vertices[14];
                                    vertices[14] = vertices[19];
                                    vertices[19] = tempV;
                                    tempU = vertices[3];
                                    vertices[3] = vertices[8];
                                    vertices[8] = vertices[13];
                                    vertices[13] = vertices[18];
                                    vertices[18] = tempU;
                                    break;
                                case CompletionEvent.STATUS_CONFLICT /*2*/:
                                    tempU = vertices[3];
                                    vertices[3] = vertices[13];
                                    vertices[13] = tempU;
                                    tempU = vertices[8];
                                    vertices[8] = vertices[18];
                                    vertices[18] = tempU;
                                    tempV = vertices[4];
                                    vertices[4] = vertices[14];
                                    vertices[14] = tempV;
                                    tempV = vertices[9];
                                    vertices[9] = vertices[19];
                                    vertices[19] = tempV;
                                    break;
                                case CompletionEvent.STATUS_CANCELED /*3*/:
                                    tempV = vertices[4];
                                    vertices[4] = vertices[19];
                                    vertices[19] = vertices[14];
                                    vertices[14] = vertices[9];
                                    vertices[9] = tempV;
                                    tempU = vertices[3];
                                    vertices[3] = vertices[18];
                                    vertices[18] = vertices[13];
                                    vertices[13] = vertices[8];
                                    vertices[8] = tempU;
                                    break;
                            }
                        }
                        this.spriteBatch.draw(region.getTexture(), vertices, 0, 20);
                        x += layerTileWidth;
                    }
                }
            }
            y += layerTileHeight;
        }
    }
}
