package com.frodidev.GameObjects;

public class Grass extends Scrollable {
    public Grass(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }

    public void onRestart(float x, float scrollSpeed) {
        this.position.f100x = x;
        this.velocity.f100x = scrollSpeed;
    }
}
