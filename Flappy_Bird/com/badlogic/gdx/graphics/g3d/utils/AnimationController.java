package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;
import com.google.android.gms.cast.TextTrackStyle;

public class AnimationController extends BaseAnimationController {
    protected final Pool<AnimationDesc> animationPool;
    public AnimationDesc current;
    public boolean inAction;
    private boolean justChangedAnimation;
    public AnimationDesc previous;
    public AnimationDesc queued;
    public float queuedTransitionTime;
    public float transitionCurrentTime;
    public float transitionTargetTime;
    private boolean updating;

    public static class AnimationDesc {
        public Animation animation;
        public AnimationListener listener;
        public int loopCount;
        public float speed;
        public float time;

        protected AnimationDesc() {
        }

        protected float update(float delta) {
            if (this.loopCount == 0 || this.animation == null) {
                return delta;
            }
            float duration = this.animation.duration;
            float diff = this.speed * delta;
            this.time += diff;
            int loops = (int) Math.abs(this.time / duration);
            if (this.time < 0.0f) {
                loops++;
                while (this.time < 0.0f) {
                    this.time += duration;
                }
            }
            this.time = Math.abs(this.time % duration);
            for (int i = 0; i < loops; i++) {
                if (this.loopCount > 0) {
                    this.loopCount--;
                }
                if (!(this.loopCount == 0 || this.listener == null)) {
                    this.listener.onLoop(this);
                }
                if (this.loopCount == 0) {
                    float result = (((float) ((loops - 1) - i)) * duration) + (diff < 0.0f ? duration - this.time : this.time);
                    if (diff >= 0.0f) {
                        duration = 0.0f;
                    }
                    this.time = duration;
                    if (this.listener == null) {
                        return result;
                    }
                    this.listener.onEnd(this);
                    return result;
                }
            }
            return 0.0f;
        }
    }

    public interface AnimationListener {
        void onEnd(AnimationDesc animationDesc);

        void onLoop(AnimationDesc animationDesc);
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.utils.AnimationController.1 */
    class C03621 extends Pool<AnimationDesc> {
        C03621() {
        }

        protected AnimationDesc newObject() {
            return new AnimationDesc();
        }
    }

    public AnimationController(ModelInstance target) {
        super(target);
        this.animationPool = new C03621();
        this.justChangedAnimation = false;
    }

    private AnimationDesc obtain(Animation anim, int loopCount, float speed, AnimationListener listener) {
        float f = 0.0f;
        if (anim == null) {
            return null;
        }
        AnimationDesc result = (AnimationDesc) this.animationPool.obtain();
        result.animation = anim;
        result.listener = listener;
        result.loopCount = loopCount;
        result.speed = speed;
        if (speed < 0.0f) {
            f = anim.duration;
        }
        result.time = f;
        return result;
    }

    private AnimationDesc obtain(String id, int loopCount, float speed, AnimationListener listener) {
        if (id == null) {
            return null;
        }
        Animation anim = this.target.getAnimation(id);
        if (anim != null) {
            return obtain(anim, loopCount, speed, listener);
        }
        throw new GdxRuntimeException("Unknown animation: " + id);
    }

    private AnimationDesc obtain(AnimationDesc anim) {
        return obtain(anim.animation, anim.loopCount, anim.speed, anim.listener);
    }

    public void update(float delta) {
        if (this.previous != null) {
            float f = this.transitionCurrentTime + delta;
            this.transitionCurrentTime = f;
            if (f >= this.transitionTargetTime) {
                removeAnimation(this.previous.animation);
                this.justChangedAnimation = true;
                this.animationPool.free(this.previous);
                this.previous = null;
            }
        }
        if (this.justChangedAnimation) {
            this.target.calculateTransforms();
            this.justChangedAnimation = false;
        }
        if (this.current != null && this.current.loopCount != 0 && this.current.animation != null) {
            this.justChangedAnimation = false;
            this.updating = true;
            float remain = this.current.update(delta);
            if (remain == 0.0f || this.queued == null) {
                if (this.previous != null) {
                    applyAnimations(this.previous.animation, this.previous.time, this.current.animation, this.current.time, this.transitionCurrentTime / this.transitionTargetTime);
                } else {
                    applyAnimation(this.current.animation, this.current.time);
                }
                this.updating = false;
                return;
            }
            this.inAction = false;
            animate(this.queued, this.queuedTransitionTime);
            this.queued = null;
            this.updating = false;
            update(remain);
        }
    }

    public AnimationDesc setAnimation(String id) {
        return setAnimation(id, 1, (float) TextTrackStyle.DEFAULT_FONT_SCALE, null);
    }

    public AnimationDesc setAnimation(String id, int loopCount) {
        return setAnimation(id, loopCount, (float) TextTrackStyle.DEFAULT_FONT_SCALE, null);
    }

    public AnimationDesc setAnimation(String id, AnimationListener listener) {
        return setAnimation(id, 1, (float) TextTrackStyle.DEFAULT_FONT_SCALE, listener);
    }

    public AnimationDesc setAnimation(String id, int loopCount, AnimationListener listener) {
        return setAnimation(id, loopCount, (float) TextTrackStyle.DEFAULT_FONT_SCALE, listener);
    }

    public AnimationDesc setAnimation(String id, int loopCount, float speed, AnimationListener listener) {
        return setAnimation(obtain(id, loopCount, speed, listener));
    }

    protected AnimationDesc setAnimation(Animation anim, int loopCount, float speed, AnimationListener listener) {
        return setAnimation(obtain(anim, loopCount, speed, listener));
    }

    protected AnimationDesc setAnimation(AnimationDesc anim) {
        if (this.updating) {
            throw new GdxRuntimeException("Cannot change animation during update");
        }
        if (this.current == null) {
            this.current = anim;
        } else {
            if (anim == null || this.current.animation != anim.animation) {
                removeAnimation(this.current.animation);
            } else {
                anim.time = this.current.time;
            }
            this.animationPool.free(this.current);
            this.current = anim;
        }
        this.justChangedAnimation = true;
        return anim;
    }

    public AnimationDesc animate(String id, float transitionTime) {
        return animate(id, 1, (float) TextTrackStyle.DEFAULT_FONT_SCALE, null, transitionTime);
    }

    public AnimationDesc animate(String id, AnimationListener listener, float transitionTime) {
        return animate(id, 1, (float) TextTrackStyle.DEFAULT_FONT_SCALE, listener, transitionTime);
    }

    public AnimationDesc animate(String id, int loopCount, AnimationListener listener, float transitionTime) {
        return animate(id, loopCount, (float) TextTrackStyle.DEFAULT_FONT_SCALE, listener, transitionTime);
    }

    public AnimationDesc animate(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return animate(obtain(id, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc animate(Animation anim, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return animate(obtain(anim, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc animate(AnimationDesc anim, float transitionTime) {
        if (this.current == null) {
            this.current = anim;
        } else if (this.inAction) {
            queue(anim, transitionTime);
        } else if (anim == null || this.current.animation != anim.animation) {
            if (this.previous != null) {
                this.animationPool.free(this.previous);
            }
            this.previous = this.current;
            this.current = anim;
            this.transitionCurrentTime = 0.0f;
            this.transitionTargetTime = transitionTime;
        } else {
            anim.time = this.current.time;
            this.animationPool.free(this.current);
            this.current = anim;
        }
        return anim;
    }

    public AnimationDesc queue(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return queue(obtain(id, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc queue(Animation anim, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return queue(obtain(anim, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc queue(AnimationDesc anim, float transitionTime) {
        if (this.current == null || this.current.loopCount == 0) {
            animate(anim, transitionTime);
        } else {
            if (this.queued != null) {
                this.animationPool.free(this.queued);
            }
            this.queued = anim;
            this.queuedTransitionTime = transitionTime;
            if (this.current.loopCount < 0) {
                this.current.loopCount = 1;
            }
        }
        return anim;
    }

    public AnimationDesc action(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return action(obtain(id, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc action(Animation anim, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return action(obtain(anim, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc action(AnimationDesc anim, float transitionTime) {
        if (anim.loopCount < 0) {
            throw new GdxRuntimeException("An action cannot be continuous");
        }
        if (this.current == null || this.current.loopCount == 0) {
            animate(anim, transitionTime);
        } else {
            AnimationDesc toQueue = this.inAction ? null : obtain(this.current);
            this.inAction = false;
            animate(anim, transitionTime);
            this.inAction = true;
            if (toQueue != null) {
                queue(toQueue, transitionTime);
            }
        }
        return anim;
    }
}
