package com.badlogic.gdx.graphics.g3d.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.google.android.gms.location.places.Place;

public class DepthShader extends DefaultShader {
    private static String defaultFragmentShader;
    private static String defaultVertexShader;
    public final int numBones;
    private int originalCullFace;
    public final int weights;

    public static class Config extends com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config {
        public boolean depthBufferOnly;

        public Config() {
            this.depthBufferOnly = false;
        }

        public Config(String vertexShader, String fragmentShader) {
            super(vertexShader, fragmentShader);
            this.depthBufferOnly = false;
        }
    }

    static {
        defaultVertexShader = null;
        defaultFragmentShader = null;
    }

    public static final String getDefaultVertexShader() {
        if (defaultVertexShader == null) {
            defaultVertexShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/depth.vertex.glsl").readString();
        }
        return defaultVertexShader;
    }

    public static final String getDefaultFragmentShader() {
        if (defaultFragmentShader == null) {
            defaultFragmentShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/depth.fragment.glsl").readString();
        }
        return defaultFragmentShader;
    }

    public static String createPrefix(Renderable renderable, Config config) {
        String prefix = "";
        long mask = renderable.material.getMask();
        if ((renderable.mesh.getVertexAttributes().getMask() & 64) == 64) {
            int n = renderable.mesh.getVertexAttributes().size();
            for (int i = 0; i < n; i++) {
                VertexAttribute attr = renderable.mesh.getVertexAttributes().get(i);
                if (attr.usage == 64) {
                    prefix = prefix + "#define boneWeight" + attr.unit + "Flag\n";
                }
            }
        }
        if (renderable.bones != null && config.numBones > 0) {
            prefix = prefix + "#define numBones " + config.numBones + "\n";
        }
        if (config.depthBufferOnly) {
            return prefix;
        }
        return prefix + "#define PackedDepthFlag\n";
    }

    public DepthShader(Renderable renderable) {
        this(renderable, new Config());
    }

    public DepthShader(Renderable renderable, Config config) {
        this(renderable, config, createPrefix(renderable, config));
    }

    public DepthShader(Renderable renderable, Config config, String prefix) {
        this(renderable, config, prefix, config.vertexShader != null ? config.vertexShader : getDefaultVertexShader(), config.fragmentShader != null ? config.fragmentShader : getDefaultFragmentShader());
    }

    public DepthShader(Renderable renderable, Config config, String prefix, String vertexShader, String fragmentShader) {
        this(renderable, config, new ShaderProgram(prefix + vertexShader, prefix + fragmentShader));
    }

    public DepthShader(Renderable renderable, Config config, ShaderProgram shaderProgram) {
        super(renderable, (com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config) config, shaderProgram);
        this.numBones = renderable.bones == null ? 0 : config.numBones;
        int w = 0;
        int n = renderable.mesh.getVertexAttributes().size();
        for (int i = 0; i < n; i++) {
            VertexAttribute attr = renderable.mesh.getVertexAttributes().get(i);
            if (attr.usage == 64) {
                w |= 1 << attr.unit;
            }
        }
        this.weights = w;
    }

    public void begin(Camera camera, RenderContext context) {
        this.originalCullFace = DefaultShader.defaultCullFace;
        DefaultShader.defaultCullFace = Place.TYPE_SUBPREMISE;
        super.begin(camera, context);
    }

    public void end() {
        super.end();
        DefaultShader.defaultCullFace = this.originalCullFace;
        Gdx.gl20.glDisable(GL20.GL_POLYGON_OFFSET_FILL);
    }

    public boolean canRender(Renderable renderable) {
        boolean skinned;
        boolean z;
        if ((renderable.mesh.getVertexAttributes().getMask() & 64) == 64) {
            skinned = true;
        } else {
            skinned = false;
        }
        if (this.numBones > 0) {
            z = true;
        } else {
            z = false;
        }
        if (skinned != z) {
            return false;
        }
        if (!skinned) {
            return true;
        }
        int w = 0;
        int n = renderable.mesh.getVertexAttributes().size();
        for (int i = 0; i < n; i++) {
            VertexAttribute attr = renderable.mesh.getVertexAttributes().get(i);
            if (attr.usage == 64) {
                w |= 1 << attr.unit;
            }
        }
        if (w != this.weights) {
            return false;
        }
        return true;
    }
}
