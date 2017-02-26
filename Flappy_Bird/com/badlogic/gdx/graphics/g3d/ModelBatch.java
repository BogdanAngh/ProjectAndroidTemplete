package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.DefaultRenderableSorter;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.GLES10ShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;

public class ModelBatch implements Disposable {
    protected Camera camera;
    protected final RenderContext context;
    private final boolean ownContext;
    protected final Array<Renderable> renderables;
    protected final Pool<Renderable> renderablesPool;
    protected final Array<Renderable> reuseableRenderables;
    protected final ShaderProvider shaderProvider;
    protected final RenderableSorter sorter;

    /* renamed from: com.badlogic.gdx.graphics.g3d.ModelBatch.1 */
    class C03501 extends Pool<Renderable> {
        C03501() {
        }

        protected Renderable newObject() {
            return new Renderable();
        }

        public Renderable obtain() {
            Renderable renderable = (Renderable) super.obtain();
            renderable.environment = null;
            renderable.material = null;
            renderable.mesh = null;
            renderable.shader = null;
            return renderable;
        }
    }

    private ModelBatch(RenderContext context, boolean ownContext, ShaderProvider shaderProvider, RenderableSorter sorter) {
        this.renderablesPool = new C03501();
        this.renderables = new Array();
        this.reuseableRenderables = new Array();
        this.ownContext = ownContext;
        this.context = context;
        this.shaderProvider = shaderProvider;
        this.sorter = sorter;
    }

    public ModelBatch(RenderContext context, ShaderProvider shaderProvider, RenderableSorter sorter) {
        this(context, false, shaderProvider, sorter);
    }

    public ModelBatch(ShaderProvider shaderProvider) {
        this(new RenderContext(new DefaultTextureBinder(1, 1)), true, shaderProvider, new DefaultRenderableSorter());
    }

    public ModelBatch(FileHandle vertexShader, FileHandle fragmentShader) {
        this(new DefaultShaderProvider(vertexShader, fragmentShader));
    }

    public ModelBatch(String vertexShader, String fragmentShader) {
        this(new DefaultShaderProvider(vertexShader, fragmentShader));
    }

    public ModelBatch() {
        this(new RenderContext(new DefaultTextureBinder(0, 1)), true, Gdx.graphics.isGL20Available() ? new DefaultShaderProvider() : new GLES10ShaderProvider(), new DefaultRenderableSorter());
    }

    public void begin(Camera cam) {
        if (this.camera != null) {
            throw new GdxRuntimeException("Call end() first.");
        }
        this.camera = cam;
        if (this.ownContext) {
            this.context.begin();
        }
    }

    public void setCamera(Camera cam) {
        if (this.camera == null) {
            throw new GdxRuntimeException("Call begin() first.");
        }
        if (this.renderables.size > 0) {
            flush();
        }
        this.camera = cam;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public void flush() {
        this.sorter.sort(this.camera, this.renderables);
        Shader currentShader = null;
        for (int i = 0; i < this.renderables.size; i++) {
            Renderable renderable = (Renderable) this.renderables.get(i);
            if (currentShader != renderable.shader) {
                if (currentShader != null) {
                    currentShader.end();
                }
                currentShader = renderable.shader;
                currentShader.begin(this.camera, this.context);
            }
            currentShader.render(renderable);
        }
        if (currentShader != null) {
            currentShader.end();
        }
        this.renderablesPool.freeAll(this.reuseableRenderables);
        this.reuseableRenderables.clear();
        this.renderables.clear();
    }

    public void end() {
        flush();
        if (this.ownContext) {
            this.context.end();
        }
        this.camera = null;
    }

    public void render(Renderable renderable) {
        renderable.shader = this.shaderProvider.getShader(renderable);
        renderable.mesh.setAutoBind(false);
        this.renderables.add(renderable);
    }

    public void render(RenderableProvider renderableProvider) {
        render(renderableProvider, null, null);
    }

    public <T extends RenderableProvider> void render(Iterable<T> renderableProviders) {
        render((Iterable) renderableProviders, null, null);
    }

    public void render(RenderableProvider renderableProvider, Environment lights) {
        render(renderableProvider, lights, null);
    }

    public <T extends RenderableProvider> void render(Iterable<T> renderableProviders, Environment lights) {
        render((Iterable) renderableProviders, lights, null);
    }

    public void render(RenderableProvider renderableProvider, Shader shader) {
        render(renderableProvider, null, shader);
    }

    public <T extends RenderableProvider> void render(Iterable<T> renderableProviders, Shader shader) {
        render((Iterable) renderableProviders, null, shader);
    }

    public void render(RenderableProvider renderableProvider, Environment lights, Shader shader) {
        int offset = this.renderables.size;
        renderableProvider.getRenderables(this.renderables, this.renderablesPool);
        for (int i = offset; i < this.renderables.size; i++) {
            Renderable renderable = (Renderable) this.renderables.get(i);
            renderable.environment = lights;
            renderable.shader = shader;
            renderable.shader = this.shaderProvider.getShader(renderable);
            this.reuseableRenderables.add(renderable);
        }
    }

    public <T extends RenderableProvider> void render(Iterable<T> renderableProviders, Environment lights, Shader shader) {
        for (T renderableProvider : renderableProviders) {
            render((RenderableProvider) renderableProvider, lights, shader);
        }
    }

    public void dispose() {
        this.shaderProvider.dispose();
    }
}
