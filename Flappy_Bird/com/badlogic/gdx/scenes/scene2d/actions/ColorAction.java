package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.graphics.Color;

public class ColorAction extends TemporalAction {
    private Color color;
    private final Color end;
    private float startA;
    private float startB;
    private float startG;
    private float startR;

    public ColorAction() {
        this.end = new Color();
    }

    protected void begin() {
        if (this.color == null) {
            this.color = this.actor.getColor();
        }
        this.startR = this.color.f40r;
        this.startG = this.color.f39g;
        this.startB = this.color.f38b;
        this.startA = this.color.f37a;
    }

    protected void update(float percent) {
        this.color.set(this.startR + ((this.end.f40r - this.startR) * percent), this.startG + ((this.end.f39g - this.startG) * percent), this.startB + ((this.end.f38b - this.startB) * percent), this.startA + ((this.end.f37a - this.startA) * percent));
    }

    public void reset() {
        super.reset();
        this.color = null;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getEndColor() {
        return this.end;
    }

    public void setEndColor(Color color) {
        this.end.set(color);
    }
}
