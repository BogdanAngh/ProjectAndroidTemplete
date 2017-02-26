package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Animation {
    public static final int LOOP = 2;
    public static final int LOOP_PINGPONG = 4;
    public static final int LOOP_RANDOM = 5;
    public static final int LOOP_REVERSED = 3;
    public static final int NORMAL = 0;
    public static final int REVERSED = 1;
    public final float animationDuration;
    public final float frameDuration;
    final TextureRegion[] keyFrames;
    private int playMode;

    public Animation(float frameDuration, Array<? extends TextureRegion> keyFrames) {
        this.playMode = NORMAL;
        this.frameDuration = frameDuration;
        this.animationDuration = ((float) keyFrames.size) * frameDuration;
        this.keyFrames = new TextureRegion[keyFrames.size];
        int n = keyFrames.size;
        for (int i = NORMAL; i < n; i += REVERSED) {
            this.keyFrames[i] = (TextureRegion) keyFrames.get(i);
        }
        this.playMode = NORMAL;
    }

    public Animation(float frameDuration, Array<? extends TextureRegion> keyFrames, int playType) {
        this.playMode = NORMAL;
        this.frameDuration = frameDuration;
        this.animationDuration = ((float) keyFrames.size) * frameDuration;
        this.keyFrames = new TextureRegion[keyFrames.size];
        int n = keyFrames.size;
        for (int i = NORMAL; i < n; i += REVERSED) {
            this.keyFrames[i] = (TextureRegion) keyFrames.get(i);
        }
        this.playMode = playType;
    }

    public Animation(float frameDuration, TextureRegion... keyFrames) {
        this.playMode = NORMAL;
        this.frameDuration = frameDuration;
        this.animationDuration = ((float) keyFrames.length) * frameDuration;
        this.keyFrames = keyFrames;
        this.playMode = NORMAL;
    }

    public TextureRegion getKeyFrame(float stateTime, boolean looping) {
        int oldPlayMode = this.playMode;
        if (looping && (this.playMode == 0 || this.playMode == REVERSED)) {
            if (this.playMode == 0) {
                this.playMode = LOOP;
            } else {
                this.playMode = LOOP_REVERSED;
            }
        } else if (!(looping || this.playMode == 0 || this.playMode == REVERSED)) {
            if (this.playMode == LOOP_REVERSED) {
                this.playMode = REVERSED;
            } else {
                this.playMode = LOOP;
            }
        }
        TextureRegion frame = getKeyFrame(stateTime);
        this.playMode = oldPlayMode;
        return frame;
    }

    public TextureRegion getKeyFrame(float stateTime) {
        return this.keyFrames[getKeyFrameIndex(stateTime)];
    }

    public int getKeyFrameIndex(float stateTime) {
        if (this.keyFrames.length == REVERSED) {
            return NORMAL;
        }
        int frameNumber = (int) (stateTime / this.frameDuration);
        switch (this.playMode) {
            case NORMAL /*0*/:
                return Math.min(this.keyFrames.length - 1, frameNumber);
            case REVERSED /*1*/:
                return Math.max((this.keyFrames.length - frameNumber) - 1, NORMAL);
            case LOOP /*2*/:
                return frameNumber % this.keyFrames.length;
            case LOOP_REVERSED /*3*/:
                return (this.keyFrames.length - (frameNumber % this.keyFrames.length)) - 1;
            case LOOP_PINGPONG /*4*/:
                frameNumber %= (this.keyFrames.length * LOOP) - 2;
                if (frameNumber >= this.keyFrames.length) {
                    return (this.keyFrames.length - 2) - (frameNumber - this.keyFrames.length);
                }
                return frameNumber;
            case LOOP_RANDOM /*5*/:
                return MathUtils.random(this.keyFrames.length - 1);
            default:
                return Math.min(this.keyFrames.length - 1, frameNumber);
        }
    }

    public int getPlayMode() {
        return this.playMode;
    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }

    public boolean isAnimationFinished(float stateTime) {
        return this.keyFrames.length + -1 < ((int) (stateTime / this.frameDuration));
    }
}
