package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.google.android.gms.cast.TextTrackStyle;

public class DragScrollListener extends DragListener {
    Interpolation interpolation;
    float maxSpeed;
    float minSpeed;
    long rampTime;
    private ScrollPane scroll;
    private Task scrollDown;
    private Task scrollUp;
    long startTime;
    float tickSecs;

    /* renamed from: com.badlogic.gdx.scenes.scene2d.utils.DragScrollListener.1 */
    class C03791 extends Task {
        final /* synthetic */ ScrollPane val$scroll;

        C03791(ScrollPane scrollPane) {
            this.val$scroll = scrollPane;
        }

        public void run() {
            this.val$scroll.setScrollY(this.val$scroll.getScrollY() - DragScrollListener.this.getScrollPixels());
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.utils.DragScrollListener.2 */
    class C03802 extends Task {
        final /* synthetic */ ScrollPane val$scroll;

        C03802(ScrollPane scrollPane) {
            this.val$scroll = scrollPane;
        }

        public void run() {
            this.val$scroll.setScrollY(this.val$scroll.getScrollY() + DragScrollListener.this.getScrollPixels());
        }
    }

    public DragScrollListener(ScrollPane scroll) {
        this.interpolation = Interpolation.exp5In;
        this.minSpeed = 15.0f;
        this.maxSpeed = 75.0f;
        this.tickSecs = 0.05f;
        this.rampTime = 1750;
        this.scroll = scroll;
        this.scrollUp = new C03791(scroll);
        this.scrollDown = new C03802(scroll);
    }

    public void setup(float minSpeedPixels, float maxSpeedPixels, float tickSecs, float rampSecs) {
        this.minSpeed = minSpeedPixels;
        this.maxSpeed = maxSpeedPixels;
        this.tickSecs = tickSecs;
        this.rampTime = (long) (1000.0f * rampSecs);
    }

    float getScrollPixels() {
        return this.interpolation.apply(this.minSpeed, this.maxSpeed, Math.min(TextTrackStyle.DEFAULT_FONT_SCALE, ((float) (System.currentTimeMillis() - this.startTime)) / ((float) this.rampTime)));
    }

    public void drag(InputEvent event, float x, float y, int pointer) {
        if (x >= 0.0f && x < this.scroll.getWidth()) {
            if (y >= this.scroll.getHeight()) {
                this.scrollDown.cancel();
                if (!this.scrollUp.isScheduled()) {
                    this.startTime = System.currentTimeMillis();
                    Timer.schedule(this.scrollUp, this.tickSecs, this.tickSecs);
                    return;
                }
                return;
            } else if (y < 0.0f) {
                this.scrollUp.cancel();
                if (!this.scrollDown.isScheduled()) {
                    this.startTime = System.currentTimeMillis();
                    Timer.schedule(this.scrollDown, this.tickSecs, this.tickSecs);
                    return;
                }
                return;
            }
        }
        this.scrollUp.cancel();
        this.scrollDown.cancel();
    }

    public void dragStop(InputEvent event, float x, float y, int pointer) {
        this.scrollUp.cancel();
        this.scrollDown.cancel();
    }
}
