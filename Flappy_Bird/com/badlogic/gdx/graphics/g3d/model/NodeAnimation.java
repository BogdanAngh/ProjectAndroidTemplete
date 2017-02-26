package com.badlogic.gdx.graphics.g3d.model;

import com.badlogic.gdx.utils.Array;

public class NodeAnimation {
    public Array<NodeKeyframe> keyframes;
    public Node node;

    public NodeAnimation() {
        this.keyframes = new Array();
    }
}
