package com.badlogic.gdx.graphics.g3d.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Iterator;

public class GLES10Shader implements Shader {
    public static int defaultCullFace;
    private Camera camera;
    private RenderContext context;
    private Material currentMaterial;
    private Mesh currentMesh;
    private Texture currentTexture0;
    private Matrix4 currentTransform;
    private final float[] lightVal;
    private final float[] oneVal4;
    private Color tmpC;
    private final float[] zeroVal4;

    static {
        defaultCullFace = Place.TYPE_SYNTHETIC_GEOCODE;
    }

    public GLES10Shader() {
        this.lightVal = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.zeroVal4 = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.oneVal4 = new float[]{TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE};
        this.tmpC = new Color();
        if (Gdx.gl10 == null) {
            throw new GdxRuntimeException("This shader requires OpenGL ES 1.x");
        }
    }

    public void init() {
    }

    public boolean canRender(Renderable renderable) {
        return true;
    }

    public int compareTo(Shader other) {
        return 0;
    }

    public boolean equals(Object obj) {
        return obj instanceof GLES10Shader ? equals((GLES10Shader) obj) : false;
    }

    public boolean equals(GLES10Shader obj) {
        return obj == this;
    }

    public void begin(Camera camera, RenderContext context) {
        this.context = context;
        this.camera = camera;
        context.setDepthTest(GL20.GL_LEQUAL, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        context.setDepthMask(true);
        Gdx.gl10.glMatrixMode(GL10.GL_PROJECTION);
        Gdx.gl10.glLoadMatrixf(camera.combined.val, 0);
        Gdx.gl10.glMatrixMode(GL10.GL_MODELVIEW);
    }

    private void bindLights(Environment lights) {
        if (lights == null) {
            Gdx.gl10.glDisable(GL10.GL_LIGHTING);
            return;
        }
        int i;
        Gdx.gl10.glEnable(GL10.GL_LIGHTING);
        if (lights.has(ColorAttribute.AmbientLight)) {
            Gdx.gl10.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, getValues(this.lightVal, ((ColorAttribute) lights.get(ColorAttribute.AmbientLight)).color), 0);
        } else {
            Gdx.gl10.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, this.zeroVal4, 0);
        }
        Gdx.gl10.glLightfv(GL20.GL_COLOR_BUFFER_BIT, GL10.GL_DIFFUSE, this.zeroVal4, 0);
        int idx = 0;
        Gdx.gl10.glPushMatrix();
        Gdx.gl10.glLoadIdentity();
        for (i = 0; i < lights.directionalLights.size && idx < 8; i++) {
            DirectionalLight light = (DirectionalLight) lights.directionalLights.get(i);
            Gdx.gl10.glEnable(idx + GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl10.glLightfv(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_DIFFUSE, getValues(this.lightVal, light.color), 0);
            Gdx.gl10.glLightfv(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_POSITION, getValues(this.lightVal, -light.direction.f105x, -light.direction.f106y, -light.direction.f107z, 0.0f), 0);
            Gdx.gl10.glLightf(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_SPOT_CUTOFF, BitmapDescriptorFactory.HUE_CYAN);
            Gdx.gl10.glLightf(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_CONSTANT_ATTENUATION, TextTrackStyle.DEFAULT_FONT_SCALE);
            Gdx.gl10.glLightf(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_LINEAR_ATTENUATION, 0.0f);
            Gdx.gl10.glLightf(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_QUADRATIC_ATTENUATION, 0.0f);
            idx++;
        }
        for (i = 0; i < lights.pointLights.size && idx < 8; i++) {
            Gdx.gl10.glEnable(idx + GL20.GL_COLOR_BUFFER_BIT);
            PointLight light2 = (PointLight) lights.pointLights.get(i);
            Gdx.gl10.glLightfv(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_DIFFUSE, getValues(this.lightVal, light2.color), 0);
            Gdx.gl10.glLightfv(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_POSITION, getValues(this.lightVal, light2.position.f105x, light2.position.f106y, light2.position.f107z, TextTrackStyle.DEFAULT_FONT_SCALE), 0);
            Gdx.gl10.glLightf(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_SPOT_CUTOFF, BitmapDescriptorFactory.HUE_CYAN);
            Gdx.gl10.glLightf(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_CONSTANT_ATTENUATION, 0.0f);
            Gdx.gl10.glLightf(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_LINEAR_ATTENUATION, 0.0f);
            Gdx.gl10.glLightf(idx + GL20.GL_COLOR_BUFFER_BIT, GL10.GL_QUADRATIC_ATTENUATION, TextTrackStyle.DEFAULT_FONT_SCALE / light2.intensity);
            idx++;
        }
        int idx2 = idx;
        while (idx2 < 8) {
            idx = idx2 + 1;
            Gdx.gl10.glDisable(idx2 + GL20.GL_COLOR_BUFFER_BIT);
            idx2 = idx;
        }
        Gdx.gl10.glPopMatrix();
    }

    private static final float[] getValues(float[] out, float v0, float v1, float v2, float v3) {
        out[0] = v0;
        out[1] = v1;
        out[2] = v2;
        out[3] = v3;
        return out;
    }

    private static final float[] getValues(float[] out, Color color) {
        return getValues(out, color.f40r, color.f39g, color.f38b, color.f37a);
    }

    public void render(Renderable renderable) {
        this.tmpC.set(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        boolean hasColor = false;
        if (this.currentMaterial != renderable.material) {
            this.currentMaterial = renderable.material;
            if (!this.currentMaterial.has(BlendingAttribute.Type)) {
                this.context.setBlending(false, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }
            if (!this.currentMaterial.has(ColorAttribute.Diffuse)) {
                Gdx.gl10.glColor4f(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
                if (renderable.environment != null) {
                    Gdx.gl10.glDisable(GL10.GL_COLOR_MATERIAL);
                }
            }
            if (!this.currentMaterial.has(TextureAttribute.Diffuse)) {
                Gdx.gl10.glDisable(GL20.GL_TEXTURE_2D);
            }
            int cullFace = defaultCullFace;
            Iterator i$ = this.currentMaterial.iterator();
            while (i$.hasNext()) {
                Attribute attribute = (Attribute) i$.next();
                if (attribute.type == BlendingAttribute.Type) {
                    this.context.setBlending(true, ((BlendingAttribute) attribute).sourceFunction, ((BlendingAttribute) attribute).destFunction);
                    hasColor = true;
                    this.tmpC.f37a = ((BlendingAttribute) attribute).opacity;
                } else if (attribute.type == ColorAttribute.Diffuse) {
                    float a = this.tmpC.f37a;
                    this.tmpC.set(((ColorAttribute) attribute).color);
                    this.tmpC.f37a = a;
                    hasColor = true;
                } else if (attribute.type == TextureAttribute.Diffuse) {
                    TextureDescriptor textureDesc = ((TextureAttribute) attribute).textureDescription;
                    if (this.currentTexture0 != textureDesc.texture) {
                        Texture texture = (Texture) textureDesc.texture;
                        this.currentTexture0 = texture;
                        texture.bind(0);
                    }
                    this.currentTexture0.unsafeSetFilter(textureDesc.minFilter, textureDesc.magFilter);
                    this.currentTexture0.unsafeSetWrap(textureDesc.uWrap, textureDesc.vWrap);
                    Gdx.gl10.glEnable(GL20.GL_TEXTURE_2D);
                } else if ((attribute.type & IntAttribute.CullFace) == IntAttribute.CullFace) {
                    cullFace = ((IntAttribute) attribute).value;
                }
            }
            this.context.setCullFace(cullFace);
        }
        if (hasColor) {
            Gdx.gl10.glColor4f(this.tmpC.f40r, this.tmpC.f39g, this.tmpC.f38b, this.tmpC.f37a);
            if (renderable.environment != null) {
                Gdx.gl10.glEnable(GL10.GL_COLOR_MATERIAL);
                Gdx.gl10.glMaterialfv(GL20.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, getValues(this.lightVal, this.tmpC), 0);
                Gdx.gl10.glMaterialfv(GL20.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, getValues(this.lightVal, this.tmpC), 0);
            }
        }
        if (this.currentTransform != renderable.worldTransform) {
            if (this.currentTransform != null) {
                Gdx.gl10.glPopMatrix();
            }
            this.currentTransform = renderable.worldTransform;
            Gdx.gl10.glPushMatrix();
            Gdx.gl10.glLoadMatrixf(this.currentTransform.val, 0);
        }
        bindLights(renderable.environment);
        if (this.currentMesh != renderable.mesh) {
            if (this.currentMesh != null) {
                this.currentMesh.unbind();
            }
            Mesh mesh = renderable.mesh;
            this.currentMesh = mesh;
            mesh.bind();
        }
        renderable.mesh.render(renderable.primitiveType, renderable.meshPartOffset, renderable.meshPartSize);
    }

    public void end() {
        if (this.currentMesh != null) {
            this.currentMesh.unbind();
        }
        this.currentMesh = null;
        if (this.currentTransform != null) {
            Gdx.gl10.glPopMatrix();
        }
        this.currentTransform = null;
        this.currentTexture0 = null;
        this.currentMaterial = null;
        Gdx.gl10.glDisable(GL10.GL_LIGHTING);
    }

    public void dispose() {
    }
}
