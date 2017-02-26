package com.badlogic.gdx.graphics;

import com.badlogic.gdx.graphics.Pixmap.Format;

public interface TextureData {

    public enum TextureDataType {
        Pixmap,
        Compressed,
        Float
    }

    void consumeCompressedData(int i);

    Pixmap consumePixmap();

    boolean disposePixmap();

    Format getFormat();

    int getHeight();

    TextureDataType getType();

    int getWidth();

    boolean isManaged();

    boolean isPrepared();

    void prepare();

    boolean useMipMaps();
}
