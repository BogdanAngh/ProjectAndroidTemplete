package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.io.IOException;

public class PolygonRegionLoader {
    private EarClippingTriangulator triangulator;

    public PolygonRegionLoader() {
        this.triangulator = new EarClippingTriangulator();
    }

    public PolygonRegion load(TextureRegion textureRegion, FileHandle file) {
        String line;
        BufferedReader reader = file.reader((int) GL20.GL_DEPTH_BUFFER_BIT);
        do {
            try {
                line = reader.readLine();
                if (line == null) {
                    StreamUtils.closeQuietly(reader);
                    throw new GdxRuntimeException("Polygon shape not found: " + file);
                }
            } catch (IOException ex) {
                throw new GdxRuntimeException("Error reading polygon shape file: " + file, ex);
            } catch (Throwable th) {
                StreamUtils.closeQuietly(reader);
            }
        } while (!line.startsWith("s"));
        String[] polygonStrings = line.substring(1).trim().split(",");
        float[] vertices = new float[polygonStrings.length];
        int n = vertices.length;
        for (int i = 0; i < n; i++) {
            vertices[i] = Float.parseFloat(polygonStrings[i]);
        }
        PolygonRegion polygonRegion = new PolygonRegion(textureRegion, vertices, this.triangulator.computeTriangles(vertices).toArray());
        StreamUtils.closeQuietly(reader);
        return polygonRegion;
    }
}
