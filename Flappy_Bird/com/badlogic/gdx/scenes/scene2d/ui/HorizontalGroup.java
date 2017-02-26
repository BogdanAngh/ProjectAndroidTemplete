package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.SnapshotArray;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class HorizontalGroup extends WidgetGroup {
    private int alignment;
    private float prefHeight;
    private float prefWidth;
    private boolean reverse;
    private boolean sizeInvalid;
    private float spacing;

    public HorizontalGroup() {
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
        this.prefWidth = this.spacing * ((float) (n - 1));
        this.prefHeight = 0.0f;
        for (int i = 0; i < n; i++) {
            Actor child = (Actor) children.get(i);
            if (child instanceof Layout) {
                Layout layout = (Layout) child;
                this.prefWidth += layout.getPrefWidth();
                this.prefHeight = Math.max(this.prefHeight, layout.getPrefHeight());
            } else {
                this.prefWidth += child.getWidth();
                this.prefHeight = Math.max(this.prefHeight, child.getHeight());
            }
        }
    }

    public void layout() {
        float spacing = this.spacing;
        float groupHeight = getHeight();
        float x = this.reverse ? getWidth() : 0.0f;
        float dir = this.reverse ? GroundOverlayOptions.NO_DIMENSION : TextTrackStyle.DEFAULT_FONT_SCALE;
        SnapshotArray<Actor> children = getChildren();
        int n = children.size;
        for (int i = 0; i < n; i++) {
            float width;
            float height;
            float y;
            Actor child = (Actor) children.get(i);
            if (child instanceof Layout) {
                Layout layout = (Layout) child;
                width = layout.getPrefWidth();
                height = layout.getPrefHeight();
            } else {
                width = child.getWidth();
                height = child.getHeight();
            }
            if ((this.alignment & 4) != 0) {
                y = 0.0f;
            } else if ((this.alignment & 2) != 0) {
                y = groupHeight - height;
            } else {
                y = (groupHeight - height) / 2.0f;
            }
            if (this.reverse) {
                x += (width + spacing) * dir;
            }
            child.setBounds(x, y, width, height);
            if (!this.reverse) {
                x += (width + spacing) * dir;
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
