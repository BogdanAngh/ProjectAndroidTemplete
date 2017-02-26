package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.GLES10Shader;

public class GLES10ShaderProvider extends BaseShaderProvider {
    protected Shader createShader(Renderable renderable) {
        return new GLES10Shader();
    }
}
