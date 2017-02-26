package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.SnapshotArray;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class VerticalGroup extends WidgetGroup {
    private int alignment;
    private float prefHeight;
    private float prefWidth;
    private boolean reverse;
    private boolean sizeInvalid;
    private float spacing;

    public VerticalGroup() {
        this.sizeInvalid = true;
        setTouchable(Touchable.childrenOnly);
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public void invalidate() {
        super.invalidate();
        this.sizeInvalid = true;
    }

    private void computeSize() {
        this.sizeInvalid = false;
        SnapshotArray<Actor> children = getChildren();
        int n = children.size;
        this.prefWidth = 0.0f;
        this.prefHeight = this.spacing * ((float) (n - 1));
        for (int i = 0; i < n; i++) {
            Actor child = (Actor) children.get(i);
            if (child instanceof Layout) {
                Layout layout = (Layout) child;
                this.prefWidth = Math.max(this.prefWidth, layout.getPrefWidth());
                this.prefHeight += layout.getPrefHeight();
            } else {
                this.prefWidth = Math.max(this.prefWidth, child.getWidth());
                this.prefHeight += child.getHeight();
            }
        }
    }

    public void layout() {
        float spacing = this.spacing;
        float groupWidth = getWidth();
        float y = this.reverse ? 0.0f : getHeight();
        float dir = this.reverse ? TextTrackStyle.DEFAULT_FONT_SCALE : GroundOverlayOptions.NO_DIMENSION;
        SnapshotArray<Actor> children = getChildren();
        int n = children.size;
        for (int i = 0; i < n; i++) {
            float width;
            float height;
            float x;
            Actor child = (Actor) children.get(i);
            if (child instanceof Layout) {
                Layout layout = (Layout) child;
                width = layout.getPrefWidth();
                height = layout.getPrefHeight();
            } else {
                width = child.getWidth();
                height = child.getHeight();
            }
            if ((this.alignment & 8) != 0) {
                x = 0.0f;
            } else if ((this.alignment & 16) != 0) {
                x = groupWidth - width;
            } else {
                x = (groupWidth - width) / 2.0f;
            }
            if (!this.reverse) {
                y += (height + spacing) * dir;
            }
            child.setBounds(x, y, width, height);
            if (this.reverse) {
                y += (height + spacing) * dir;
            }
        }
    }

    public float getPrefWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefWidth;
    }

    public float getPrefHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefHeight;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
    }
}
