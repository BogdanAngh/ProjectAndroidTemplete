package com.frodidev.GameObjects;

import com.frodidev.GameWorld.GameWorld;
import com.frodidev.ZBHelpers.AssetLoader;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class ScrollHandler {
    public static final int PIPE_GAP = 49;
    public static final int SCROLL_SPEED = -59;
    private Grass backGrass;
    private Grass frontGrass;
    private GameWorld gameWorld;
    private Pipe pipe1;
    private Pipe pipe2;
    private Pipe pipe3;

    public ScrollHandler(GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        this.frontGrass = new Grass(0.0f, yPos, 143, 11, -59.0f);
        this.backGrass = new Grass(this.frontGrass.getTailX(), yPos, 143, 11, -59.0f);
        this.pipe1 = new Pipe(BitmapDescriptorFactory.HUE_AZURE, 0.0f, 22, 60, -59.0f, yPos);
        this.pipe2 = new Pipe(this.pipe1.getTailX() + 49.0f, 0.0f, 22, 70, -59.0f, yPos);
        this.pipe3 = new Pipe(this.pipe2.getTailX() + 49.0f, 0.0f, 22, 60, -59.0f, yPos);
    }

    public void updateReady(float delta) {
        this.frontGrass.update(delta);
        this.backGrass.update(delta);
        if (this.frontGrass.isScrolledLeft()) {
            this.frontGrass.reset(this.backGrass.getTailX());
        } else if (this.backGrass.isScrolledLeft()) {
            this.backGrass.reset(this.frontGrass.getTailX());
        }
    }

    public void update(float delta) {
        this.frontGrass.update(delta);
        this.backGrass.update(delta);
        this.pipe1.update(delta);
        this.pipe2.update(delta);
        this.pipe3.update(delta);
        if (this.pipe1.isScrolledLeft()) {
            this.pipe1.reset(this.pipe3.getTailX() + 49.0f);
        } else if (this.pipe2.isScrolledLeft()) {
            this.pipe2.reset(this.pipe1.getTailX() + 49.0f);
        } else if (this.pipe3.isScrolledLeft()) {
            this.pipe3.reset(this.pipe2.getTailX() + 49.0f);
        }
        if (this.frontGrass.isScrolledLeft()) {
            this.frontGrass.reset(this.backGrass.getTailX());
        } else if (this.backGrass.isScrolledLeft()) {
            this.backGrass.reset(this.frontGrass.getTailX());
        }
    }

    public void stop() {
        this.frontGrass.stop();
        this.backGrass.stop();
        this.pipe1.stop();
        this.pipe2.stop();
        this.pipe3.stop();
    }

    public boolean collides(Bird bird) {
        if (!this.pipe1.isScored() && this.pipe1.getX() + ((float) (this.pipe1.getWidth() / 2)) < bird.getX() + bird.getWidth()) {
            addScore(1);
            this.pipe1.setScored(true);
            AssetLoader.coin.play();
        } else if (!this.pipe2.isScored() && this.pipe2.getX() + ((float) (this.pipe2.getWidth() / 2)) < bird.getX() + bird.getWidth()) {
            addScore(1);
            this.pipe2.setScored(true);
            AssetLoader.coin.play();
        } else if (!this.pipe3.isScored() && this.pipe3.getX() + ((float) (this.pipe3.getWidth() / 2)) < bird.getX() + bird.getWidth()) {
            addScore(1);
            this.pipe3.setScored(true);
            AssetLoader.coin.play();
        }
        if (this.pipe1.collides(bird) || this.pipe2.collides(bird) || this.pipe3.collides(bird)) {
            return true;
        }
        return false;
    }

    private void addScore(int increment) {
        this.gameWorld.addScore(increment);
    }

    public Grass getFrontGrass() {
        return this.frontGrass;
    }

    public Grass getBackGrass() {
        return this.backGrass;
    }

    public Pipe getPipe1() {
        return this.pipe1;
    }

    public Pipe getPipe2() {
        return this.pipe2;
    }

    public Pipe getPipe3() {
        return this.pipe3;
    }

    public void onRestart() {
        this.frontGrass.onRestart(0.0f, -59.0f);
        this.backGrass.onRestart(this.frontGrass.getTailX(), -59.0f);
        this.pipe1.onRestart(BitmapDescriptorFactory.HUE_AZURE, -59.0f);
        this.pipe2.onRestart(this.pipe1.getTailX() + 49.0f, -59.0f);
        this.pipe3.onRestart(this.pipe2.getTailX() + 49.0f, -59.0f);
    }
}
