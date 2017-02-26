package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;
import com.google.android.gms.cast.TextTrackStyle;

public class Slider extends Widget implements Disableable {
    private float animateDuration;
    private float animateFromValue;
    private Interpolation animateInterpolation;
    private float animateTime;
    boolean disabled;
    int draggingPointer;
    private float max;
    private float min;
    private float sliderPos;
    private float[] snapValues;
    private float stepSize;
    private SliderStyle style;
    private float threshold;
    private float value;
    private final boolean vertical;

    public static class SliderStyle {
        public Drawable background;
        public Drawable disabledBackground;
        public Drawable disabledKnob;
        public Drawable disabledKnobAfter;
        public Drawable disabledKnobBefore;
        public Drawable knob;
        public Drawable knobAfter;
        public Drawable knobBefore;

        public SliderStyle(Drawable background, Drawable knob) {
            this.background = background;
            this.knob = knob;
        }

        public SliderStyle(SliderStyle style) {
            this.background = style.background;
            this.knob = style.knob;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Slider.1 */
    class C05841 extends InputListener {
        C05841() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (Slider.this.disabled || Slider.this.draggingPointer != -1) {
                return false;
            }
            Slider.this.draggingPointer = pointer;
            Slider.this.calculatePositionAndValue(x, y);
            return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (pointer == Slider.this.draggingPointer) {
                Slider.this.draggingPointer = -1;
                if (!Slider.this.calculatePositionAndValue(x, y)) {
                    ChangeEvent changeEvent = (ChangeEvent) Pools.obtain(ChangeEvent.class);
                    Slider.this.fire(changeEvent);
                    Pools.free(changeEvent);
                }
            }
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Slider.this.calculatePositionAndValue(x, y);
        }
    }

    public Slider(float min, float max, float stepSize, boolean vertical, Skin skin) {
        this(min, max, stepSize, vertical, (SliderStyle) skin.get("default-" + (vertical ? "vertical" : "horizontal"), SliderStyle.class));
    }

    public Slider(float min, float max, float stepSize, boolean vertical, Skin skin, String styleName) {
        this(min, max, stepSize, vertical, (SliderStyle) skin.get(styleName, SliderStyle.class));
    }

    public Slider(float min, float max, float stepSize, boolean vertical, SliderStyle style) {
        this.draggingPointer = -1;
        this.animateInterpolation = Interpolation.linear;
        if (min > max) {
            throw new IllegalArgumentException("min must be > max: " + min + " > " + max);
        } else if (stepSize <= 0.0f) {
            throw new IllegalArgumentException("stepSize must be > 0: " + stepSize);
        } else {
            setStyle(style);
            this.min = min;
            this.max = max;
            this.stepSize = stepSize;
            this.vertical = vertical;
            this.value = min;
            setWidth(getPrefWidth());
            setHeight(getPrefHeight());
            addListener(new C05841());
        }
    }

    public void setStyle(SliderStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        invalidateHierarchy();
    }

    public SliderStyle getStyle() {
        return this.style;
    }

    public void act(float delta) {
        super.act(delta);
        this.animateTime -= delta;
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        SliderStyle style = this.style;
        boolean disabled = this.disabled;
        Drawable knob = (!disabled || style.disabledKnob == null) ? style.knob : style.disabledKnob;
        Drawable bg = (!disabled || style.disabledBackground == null) ? style.background : style.disabledBackground;
        Drawable knobBefore = (!disabled || style.disabledKnobBefore == null) ? style.knobBefore : style.disabledKnobBefore;
        Drawable knobAfter = (!disabled || style.disabledKnobAfter == null) ? style.knobAfter : style.disabledKnobAfter;
        Color color = getColor();
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        float knobHeight = knob == null ? 0.0f : knob.getMinHeight();
        float knobWidth = knob == null ? 0.0f : knob.getMinWidth();
        float value = getVisualValue();
        batch.setColor(color.f40r, color.f39g, color.f38b, color.f37a * parentAlpha);
        float knobHeightHalf;
        if (this.vertical) {
            bg.draw(batch, x + ((float) ((int) ((width - bg.getMinWidth()) * 0.5f))), y, bg.getMinWidth(), height);
            float sliderPosHeight = height - (bg.getTopHeight() + bg.getBottomHeight());
            if (this.min != this.max) {
                this.sliderPos = ((value - this.min) / (this.max - this.min)) * (sliderPosHeight - knobHeight);
                this.sliderPos = Math.max(0.0f, this.sliderPos);
                this.sliderPos = Math.min(sliderPosHeight - knobHeight, this.sliderPos) + bg.getBottomHeight();
            }
            knobHeightHalf = knobHeight * 0.5f;
            if (knobBefore != null) {
                knobBefore.draw(batch, x + ((float) ((int) ((width - knobBefore.getMinWidth()) * 0.5f))), y, knobBefore.getMinWidth(), (float) ((int) (this.sliderPos + knobHeightHalf)));
            }
            if (knobAfter != null) {
                knobAfter.draw(batch, x + ((float) ((int) ((width - knobAfter.getMinWidth()) * 0.5f))), y + ((float) ((int) (this.sliderPos + knobHeightHalf))), knobAfter.getMinWidth(), height - ((float) ((int) (this.sliderPos + knobHeightHalf))));
            }
            if (knob != null) {
                knob.draw(batch, x + ((float) ((int) ((width - knobWidth) * 0.5f))), (float) ((int) (this.sliderPos + y)), knobWidth, knobHeight);
                return;
            }
            return;
        }
        bg.draw(batch, x, y + ((float) ((int) ((height - bg.getMinHeight()) * 0.5f))), width, bg.getMinHeight());
        float sliderPosWidth = width - (bg.getLeftWidth() + bg.getRightWidth());
        if (this.min != this.max) {
            this.sliderPos = ((value - this.min) / (this.max - this.min)) * (sliderPosWidth - knobWidth);
            this.sliderPos = Math.max(0.0f, this.sliderPos);
            this.sliderPos = Math.min(sliderPosWidth - knobWidth, this.sliderPos) + bg.getLeftWidth();
        }
        knobHeightHalf = knobHeight * 0.5f;
        if (knobBefore != null) {
            knobBefore.draw(batch, x, y + ((float) ((int) ((height - knobBefore.getMinHeight()) * 0.5f))), (float) ((int) (this.sliderPos + knobHeightHalf)), knobBefore.getMinHeight());
        }
        if (knobAfter != null) {
            knobAfter.draw(batch, x + ((float) ((int) (this.sliderPos + knobHeightHalf))), y + ((float) ((int) ((height - knobAfter.getMinHeight()) * 0.5f))), width - ((float) ((int) (this.sliderPos + knobHeightHalf))), knobAfter.getMinHeight());
        }
        if (knob != null) {
            knob.draw(batch, (float) ((int) (this.sliderPos + x)), (float) ((int) (((height - knobHeight) * 0.5f) + y)), knobWidth, knobHeight);
        }
    }

    boolean calculatePositionAndValue(float x, float y) {
        float value;
        Drawable knob = (!this.disabled || this.style.disabledKnob == null) ? this.style.knob : this.style.disabledKnob;
        Drawable bg = (!this.disabled || this.style.disabledBackground == null) ? this.style.background : this.style.disabledBackground;
        float oldPosition = this.sliderPos;
        if (this.vertical) {
            float height = (getHeight() - bg.getTopHeight()) - bg.getBottomHeight();
            float knobHeight = knob == null ? 0.0f : knob.getMinHeight();
            this.sliderPos = (y - bg.getBottomHeight()) - (0.5f * knobHeight);
            value = this.min + ((this.max - this.min) * (this.sliderPos / (height - knobHeight)));
            this.sliderPos = Math.max(0.0f, this.sliderPos);
            this.sliderPos = Math.min(height - knobHeight, this.sliderPos);
        } else {
            float width = (getWidth() - bg.getLeftWidth()) - bg.getRightWidth();
            float knobWidth = knob == null ? 0.0f : knob.getMinWidth();
            this.sliderPos = (x - bg.getLeftWidth()) - (0.5f * knobWidth);
            value = this.min + ((this.max - this.min) * (this.sliderPos / (width - knobWidth)));
            this.sliderPos = Math.max(0.0f, this.sliderPos);
            this.sliderPos = Math.min(width - knobWidth, this.sliderPos);
        }
        float oldValue = value;
        boolean valueSet = setValue(value);
        if (value == oldValue) {
            this.sliderPos = oldPosition;
        }
        return valueSet;
    }

    public boolean isDragging() {
        return this.draggingPointer != -1;
    }

    public float getValue() {
        return this.value;
    }

    public float getVisualValue() {
        if (this.animateTime > 0.0f) {
            return this.animateInterpolation.apply(this.animateFromValue, this.value, TextTrackStyle.DEFAULT_FONT_SCALE - (this.animateTime / this.animateDuration));
        }
        return this.value;
    }

    public boolean setValue(float value) {
        value = snap(clamp(((float) Math.round(value / this.stepSize)) * this.stepSize));
        float oldValue = this.value;
        if (value == oldValue) {
            return false;
        }
        float oldVisualValue = getVisualValue();
        this.value = value;
        ChangeEvent changeEvent = (ChangeEvent) Pools.obtain(ChangeEvent.class);
        boolean cancelled = fire(changeEvent);
        if (cancelled) {
            this.value = oldValue;
        } else if (this.animateDuration > 0.0f) {
            this.animateFromValue = oldVisualValue;
            this.animateTime = this.animateDuration;
        }
        Pools.free(changeEvent);
        if (cancelled) {
            return false;
        }
        return true;
    }

    protected float clamp(float value) {
        return MathUtils.clamp(value, this.min, this.max);
    }

    public void setRange(float min, float max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        this.min = min;
        this.max = max;
        if (this.value < min) {
            setValue(min);
        } else if (this.value > max) {
            setValue(max);
        }
    }

    public void setStepSize(float stepSize) {
        if (stepSize <= 0.0f) {
            throw new IllegalArgumentException("steps must be > 0: " + stepSize);
        }
        this.stepSize = stepSize;
    }

    public float getPrefWidth() {
        if (!this.vertical) {
            return 140.0f;
        }
        Drawable knob = (!this.disabled || this.style.disabledKnob == null) ? this.style.knob : this.style.disabledKnob;
        Drawable bg = (!this.disabled || this.style.disabledBackground == null) ? this.style.background : this.style.disabledBackground;
        return Math.max(knob == null ? 0.0f : knob.getMinWidth(), bg.getMinWidth());
    }

    public float getPrefHeight() {
        if (this.vertical) {
            return 140.0f;
        }
        Drawable knob = (!this.disabled || this.style.disabledKnob == null) ? this.style.knob : this.style.disabledKnob;
        Drawable bg = (!this.disabled || this.style.disabledBackground == null) ? this.style.background : this.style.disabledBackground;
        return Math.max(knob == null ? 0.0f : knob.getMinHeight(), bg.getMinHeight());
    }

    public float getMinValue() {
        return this.min;
    }

    public float getMaxValue() {
        return this.max;
    }

    public float getStepSize() {
        return this.stepSize;
    }

    public void setAnimateDuration(float duration) {
        this.animateDuration = duration;
    }

    public void setAnimateInterpolation(Interpolation animateInterpolation) {
        if (animateInterpolation == null) {
            throw new IllegalArgumentException("animateInterpolation cannot be null.");
        }
        this.animateInterpolation = animateInterpolation;
    }

    public void setSnapToValues(float[] values, float threshold) {
        this.snapValues = values;
        this.threshold = threshold;
    }

    private float snap(float value) {
        if (this.snapValues == null) {
            return value;
        }
        for (int i = 0; i < this.snapValues.length; i++) {
            if (Math.abs(value - this.snapValues[i]) <= this.threshold) {
                return this.snapValues[i];
            }
        }
        return value;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return this.disabled;
    }
}
