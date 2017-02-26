package com.badlogic.gdx.graphics.g3d.model.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.google.android.gms.cast.TextTrackStyle;

public class ModelMaterial {
    public Color ambient;
    public Color diffuse;
    public Color emissive;
    public String id;
    public float opacity;
    public Color reflection;
    public float shininess;
    public Color specular;
    public Array<ModelTexture> textures;
    public MaterialType type;

    public enum MaterialType {
        Lambert,
        Phong
    }

    public ModelMaterial() {
        this.opacity = TextTrackStyle.DEFAULT_FONT_SCALE;
    }
}
