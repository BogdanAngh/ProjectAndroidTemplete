package com.frodidev.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.frodidev.ZBHelpers.AssetLoader;

public class SimpleButton {
    private Rectangle bounds;
    private TextureRegion buttonDown;
    private TextureRegion buttonUp;
    private float height;
    private boolean isPressed;
    private float width;
    private float f91x;
    private float f92y;

    public SimpleButton(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown) {
        this.isPressed = false;
        this.f91x = x;
        this.f92y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public boolean isClicked(int screenX, int screenY) {
        return this.bounds.contains((float) screenX, (float) screenY);
    }

    public void draw(SpriteBatch batcher) {
        if (this.isPressed) {
            batcher.draw(this.buttonDown, this.f91x, this.f92y, this.width, this.height);
            return;
        }
        batcher.draw(this.buttonUp, this.f91x, this.f92y, this.width, this.height);
    }

    public boolean isTouchDown(int screenX, int screenY) {
        if (!this.bounds.contains((float) screenX, (float) screenY)) {
            return false;
        }
        this.isPressed = true;
        return true;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        if (this.bounds.contains((float) screenX, (float) screenY) && this.isPressed) {
            this.isPressed = false;
            AssetLoader.flap.play();
            return true;
        }
        this.isPressed = false;
        return false;
    }
}
