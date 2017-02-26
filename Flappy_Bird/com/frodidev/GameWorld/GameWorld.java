package com.frodidev.GameWorld;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.frodidev.FloppyBird.ActionResolver;
import com.frodidev.GameObjects.Bird;
import com.frodidev.GameObjects.ScrollHandler;
import com.frodidev.ZBHelpers.AssetLoader;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.drive.events.CompletionEvent;

public class GameWorld {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$frodidev$GameWorld$GameWorld$GameState;
    public ActionResolver actionResolver;
    private Bird bird;
    private GameState currentState;
    private Rectangle ground;
    private int midPointY;
    private GameRenderer renderer;
    private float runTime;
    public int score;
    private ScrollHandler scroller;

    public enum GameState {
        MENU,
        READY,
        RUNNING,
        GAMEOVER,
        HIGHSCORE
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$frodidev$GameWorld$GameWorld$GameState() {
        int[] iArr = $SWITCH_TABLE$com$frodidev$GameWorld$GameWorld$GameState;
        if (iArr == null) {
            iArr = new int[GameState.values().length];
            try {
                iArr[GameState.GAMEOVER.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[GameState.HIGHSCORE.ordinal()] = 5;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[GameState.MENU.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[GameState.READY.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[GameState.RUNNING.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            $SWITCH_TABLE$com$frodidev$GameWorld$GameWorld$GameState = iArr;
        }
        return iArr;
    }

    public GameWorld(int midPointY, ActionResolver actionResolver) {
        this.score = 0;
        this.runTime = 0.0f;
        this.actionResolver = actionResolver;
        this.currentState = GameState.MENU;
        this.midPointY = midPointY;
        this.bird = new Bird(33.0f, (float) (midPointY - 5), 17, 12);
        this.scroller = new ScrollHandler(this, (float) (midPointY + 66));
        this.ground = new Rectangle(0.0f, (float) (midPointY + 66), 137.0f, 11.0f);
    }

    public void update(float delta) {
        this.runTime += delta;
        switch ($SWITCH_TABLE$com$frodidev$GameWorld$GameWorld$GameState()[this.currentState.ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                updateReady(delta);
            case CompletionEvent.STATUS_CANCELED /*3*/:
                updateRunning(delta);
            default:
        }
    }

    private void updateReady(float delta) {
        this.bird.updateReady(this.runTime);
        this.scroller.updateReady(delta);
    }

    public void updateRunning(float delta) {
        if (delta > 0.15f) {
            delta = 0.15f;
        }
        this.bird.update(delta);
        this.scroller.update(delta);
        if (this.scroller.collides(this.bird) && this.bird.isAlive()) {
            this.scroller.stop();
            this.bird.die();
            AssetLoader.dead.play();
            this.renderer.prepareTransition(Keys.F12, Keys.F12, Keys.F12, 0.3f);
            AssetLoader.fall.play();
        }
        if (Intersector.overlaps(this.bird.getBoundingCircle(), this.ground)) {
            if (this.bird.isAlive()) {
                AssetLoader.dead.play();
                this.renderer.prepareTransition(Keys.F12, Keys.F12, Keys.F12, 0.3f);
                this.bird.die();
            }
            this.scroller.stop();
            this.bird.decelerate();
            this.currentState = GameState.GAMEOVER;
            if (this.score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(this.score);
                this.currentState = GameState.HIGHSCORE;
            }
        }
    }

    public Bird getBird() {
        return this.bird;
    }

    public int getMidPointY() {
        return this.midPointY;
    }

    public ScrollHandler getScroller() {
        return this.scroller;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int increment) {
        this.score += increment;
    }

    public void start() {
        this.currentState = GameState.RUNNING;
    }

    public void ready() {
        this.currentState = GameState.READY;
        this.renderer.prepareTransition(0, 0, 0, TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public void restart() {
        this.score = 0;
        this.bird.onRestart(this.midPointY - 5);
        this.scroller.onRestart();
        ready();
    }

    public boolean isReady() {
        return this.currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return this.currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return this.currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return this.currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return this.currentState == GameState.RUNNING;
    }

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }
}
