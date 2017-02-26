package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Matrix4;

public class Renderable {
    public Matrix4[] bones;
    public Environment environment;
    public Material material;
    public Mesh mesh;
    public int meshPartOffset;
    public int meshPartSize;
    public int primitiveType;
    public Shader shader;
    public Object userData;
    public final Matrix4 worldTransform;

    public Renderable() {
        this.worldTransform = new Matrix4();
    }
}
