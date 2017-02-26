package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NinePatchDrawable extends BaseDrawable {
    private NinePatch patch;

    public NinePatchDrawable(NinePatch patch) {
        setPatch(patch);
    }

    public NinePatchDrawable(NinePatchDrawable drawable) {
        super(drawable);
        setPatch(drawable.patch);
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        this.patch.draw(batch, x, y, width, height);
    }

    public void setPatch(NinePatch patch) {
        this.patch = patch;
        setMinWidth(patch.getTotalWidth());
        setMinHeight(patch.getTotalHeight());
        setTopHeight(patch.getPadTop());
        setRightWidth(patch.getPadRight());
        setBottomHeight(patch.getPadBottom());
        setLeftWidth(patch.getPadLeft());
    }

    public NinePatch getPatch() {
        return this.patch;
    }
}
