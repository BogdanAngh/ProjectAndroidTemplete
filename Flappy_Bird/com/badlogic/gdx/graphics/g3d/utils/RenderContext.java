package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.location.places.Place;

public class RenderContext {
    private int blendDFactor;
    private int blendSFactor;
    private boolean blending;
    private int cullFace;
    private int depthFunc;
    private boolean depthMask;
    private float depthRangeFar;
    private float depthRangeNear;
    public final TextureBinder textureBinder;

    public RenderContext(TextureBinder textures) {
        this.textureBinder = textures;
    }

    public final void begin() {
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        this.depthFunc = 0;
        Gdx.gl.glDisable(GL20.GL_BLEND);
        this.blending = false;
        Gdx.gl.glDisable(GL20.GL_CULL_FACE);
        int i = this.blendDFactor;
        this.blendSFactor = i;
        this.cullFace = i;
        this.textureBinder.begin();
    }

    public final void end() {
        if (this.depthFunc != 0) {
            Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        }
        if (!this.depthMask) {
            Gdx.gl.glDepthMask(true);
        }
        if (this.blending) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        if (this.cullFace > 0) {
            Gdx.gl.glDisable(GL20.GL_CULL_FACE);
        }
        this.textureBinder.end();
    }

    public final void setDepthMask(boolean depthMask) {
        if (this.depthMask != depthMask) {
            GLCommon gLCommon = Gdx.gl;
            this.depthMask = depthMask;
            gLCommon.glDepthMask(depthMask);
        }
    }

    public final void setDepthTest(int depthFunction) {
        setDepthTest(depthFunction, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public final void setDepthTest(int depthFunction, float depthRangeNear, float depthRangeFar) {
        boolean wasEnabled;
        boolean enabled = true;
        if (this.depthFunc != 0) {
            wasEnabled = true;
        } else {
            wasEnabled = false;
        }
        if (depthFunction == 0) {
            enabled = false;
        }
        if (this.depthFunc != depthFunction) {
            this.depthFunc = depthFunction;
            if (enabled) {
                Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
                Gdx.gl.glDepthFunc(depthFunction);
            } else {
                Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
            }
        }
        if (enabled) {
            GLCommon gLCommon;
            if (!(wasEnabled && this.depthFunc == depthFunction)) {
                gLCommon = Gdx.gl;
                this.depthFunc = depthFunction;
                gLCommon.glDepthFunc(depthFunction);
            }
            if (!wasEnabled || this.depthRangeNear != depthRangeNear || this.depthRangeFar != depthRangeFar) {
                gLCommon = Gdx.gl;
                this.depthRangeNear = depthRangeNear;
                this.depthRangeFar = depthRangeFar;
                gLCommon.glDepthRangef(depthRangeNear, depthRangeFar);
            }
        }
    }

    public final void setBlending(boolean enabled, int sFactor, int dFactor) {
        if (enabled != this.blending) {
            this.blending = enabled;
            if (enabled) {
                Gdx.gl.glEnable(GL20.GL_BLEND);
            } else {
                Gdx.gl.glDisable(GL20.GL_BLEND);
            }
        }
        if (!enabled) {
            return;
        }
        if (this.blendSFactor != sFactor || this.blendDFactor != dFactor) {
            Gdx.gl.glBlendFunc(sFactor, dFactor);
            this.blendSFactor = sFactor;
            this.blendDFactor = dFactor;
        }
    }

    public final void setCullFace(int face) {
        if (face != this.cullFace) {
            this.cullFace = face;
            if (face == Place.TYPE_SUBPREMISE || face == Place.TYPE_SYNTHETIC_GEOCODE || face == GL20.GL_FRONT_AND_BACK) {
                Gdx.gl.glEnable(GL20.GL_CULL_FACE);
                Gdx.gl.glCullFace(face);
                return;
            }
            Gdx.gl.glDisable(GL20.GL_CULL_FACE);
        }
    }
}
