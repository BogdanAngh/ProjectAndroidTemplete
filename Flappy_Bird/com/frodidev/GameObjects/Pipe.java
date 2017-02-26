package com.frodidev.GameObjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

public class Pipe extends Scrollable {
    public static final int SKULL_HEIGHT = 11;
    public static final int SKULL_WIDTH = 24;
    public static final int VERTICAL_GAP = 45;
    private Rectangle barDown;
    private Rectangle barUp;
    private float groundY;
    private boolean isScored;
    private Random f108r;
    private Rectangle skullDown;
    private Rectangle skullUp;

    public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        this.isScored = false;
        this.f108r = new Random();
        this.skullUp = new Rectangle();
        this.skullDown = new Rectangle();
        this.barUp = new Rectangle();
        this.barDown = new Rectangle();
        this.groundY = groundY;
    }

    public void update(float delta) {
        super.update(delta);
        this.barUp.set(this.position.f100x, this.position.f101y, (float) this.width, (float) this.height);
        this.barDown.set(this.position.f100x, (this.position.f101y + ((float) this.height)) + 45.0f, (float) this.width, this.groundY - ((this.position.f101y + ((float) this.height)) + 45.0f));
        this.skullUp.set(this.position.f100x - ((float) ((24 - this.width) / 2)), (this.position.f101y + ((float) this.height)) - 11.0f, 24.0f, 11.0f);
        this.skullDown.set(this.position.f100x - ((float) ((24 - this.width) / 2)), this.barDown.f76y, 24.0f, 11.0f);
    }

    public void reset(float newX) {
        super.reset(newX);
        this.height = this.f108r.nextInt(90) + 15;
        this.isScored = false;
    }

    public void onRestart(float x, float scrollSpeed) {
        this.velocity.f100x = scrollSpeed;
        reset(x);
    }

    public Rectangle getSkullUp() {
        return this.skullUp;
    }

    public Rectangle getSkullDown() {
        return this.skullDown;
    }

    public Rectangle getBarUp() {
        return this.barUp;
    }

    public Rectangle getBarDown() {
        return this.barDown;
    }

    public boolean collides(Bird bird) {
        if (this.position.f100x >= bird.getX() + bird.getWidth()) {
            return false;
        }
        if (Intersector.overlaps(bird.getBoundingCircle(), this.barUp) || Intersector.overlaps(bird.getBoundingCircle(), this.barDown) || Intersector.overlaps(bird.getBoundingCircle(), this.skullUp) || Intersector.overlaps(bird.getBoundingCircle(), this.skullDown)) {
            return true;
        }
        return false;
    }

    public boolean isScored() {
        return this.isScored;
    }

    public void setScored(boolean b) {
        this.isScored = b;
    }
}
