package com.badlogic.gdx.graphics.g3d.model.data;

import com.badlogic.gdx.utils.Array;

public class ModelNodeAnimation {
    public final Array<ModelNodeKeyframe> keyframes;
    public String nodeId;

    public ModelNodeAnimation() {
        this.keyframes = new Array();
    }
}
