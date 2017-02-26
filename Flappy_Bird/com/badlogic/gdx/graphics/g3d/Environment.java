package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Iterator;

public class Environment extends Attributes {
    public final Array<DirectionalLight> directionalLights;
    public final Array<PointLight> pointLights;
    public ShadowMap shadowMap;

    public Environment() {
        this.directionalLights = new Array();
        this.pointLights = new Array();
    }

    public Environment add(BaseLight... lights) {
        for (BaseLight light : lights) {
            add(light);
        }
        return this;
    }

    public Environment add(Array<BaseLight> lights) {
        Iterator i$ = lights.iterator();
        while (i$.hasNext()) {
            add((BaseLight) i$.next());
        }
        return this;
    }

    public Environment add(BaseLight light) {
        if (light instanceof DirectionalLight) {
            this.directionalLights.add((DirectionalLight) light);
        } else if (light instanceof PointLight) {
            this.pointLights.add((PointLight) light);
        } else {
            throw new GdxRuntimeException("Unknown light type");
        }
        return this;
    }
}
