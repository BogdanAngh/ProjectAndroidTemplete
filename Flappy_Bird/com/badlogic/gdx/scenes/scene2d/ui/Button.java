package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

public class Button extends Table implements Disableable {
    ButtonGroup buttonGroup;
    private ClickListener clickListener;
    boolean isChecked;
    boolean isDisabled;
    private ButtonStyle style;

    public static class ButtonStyle {
        public Drawable checked;
        public Drawable checkedOver;
        public Drawable disabled;
        public Drawable down;
        public Drawable over;
        public float pressedOffsetX;
        public float pressedOffsetY;
        public float unpressedOffsetX;
        public float unpressedOffsetY;
        public Drawable up;

        public ButtonStyle(Drawable up, Drawable down, Drawable checked) {
            this.up = up;
            this.down = down;
            this.checked = checked;
        }

        public ButtonStyle(ButtonStyle style) {
            this.up = style.up;
            this.down = style.down;
            this.over = style.over;
            this.checked = style.checked;
            this.checkedOver = style.checkedOver;
            this.disabled = style.disabled;
            this.pressedOffsetX = style.pressedOffsetX;
            this.pressedOffsetY = style.pressedOffsetY;
            this.unpressedOffsetX = style.unpressedOffsetX;
            this.unpressedOffsetY = style.unpressedOffsetY;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Button.1 */
    class C08401 extends ClickListener {
        C08401() {
        }

        public void clicked(InputEvent event, float x, float y) {
            if (!Button.this.isDisabled) {
                Button.this.setChecked(!Button.this.isChecked);
            }
        }
    }

    public Button(Skin skin) {
        super(skin);
        initialize();
        setStyle((ButtonStyle) skin.get(ButtonStyle.class));
        setWidth(getPrefWidth());
        setHeight(getPrefHeight());
    }

    public Button(Skin skin, String styleName) {
        super(skin);
        initialize();
        setStyle((ButtonStyle) skin.get(styleName, ButtonStyle.class));
        setWidth(getPrefWidth());
        setHeight(getPrefHeight());
    }

    public Button(Actor child, Skin skin, String styleName) {
        this(child, (ButtonStyle) skin.get(styleName, ButtonStyle.class));
    }

    public Button(Actor child, ButtonStyle style) {
        initialize();
        add(child);
        setStyle(style);
        setWidth(getPrefWidth());
        setHeight(getPrefHeight());
    }

    public Button(ButtonStyle style) {
        initialize();
        setStyle(style);
        setWidth(getPrefWidth());
        setHeight(getPrefHeight());
    }

    public Button() {
        initialize();
    }

    private void initialize() {
        setTouchable(Touchable.enabled);
        EventListener c08401 = new C08401();
        this.clickListener = c08401;
        addListener(c08401);
    }

    public Button(Drawable up) {
        this(new ButtonStyle(up, null, null));
    }

    public Button(Drawable up, Drawable down) {
        this(new ButtonStyle(up, down, null));
    }

    public Button(Drawable up, Drawable down, Drawable checked) {
        this(new ButtonStyle(up, down, checked));
    }

    public Button(Actor child, Skin skin) {
        this(child, (ButtonStyle) skin.get(ButtonStyle.class));
    }

    public void setChecked(boolean isChecked) {
        if (this.isChecked != isChecked) {
            if (this.buttonGroup == null || this.buttonGroup.canCheck(this, isChecked)) {
                this.isChecked = isChecked;
                if (!this.isDisabled) {
                    ChangeEvent changeEvent = (ChangeEvent) Pools.obtain(ChangeEvent.class);
                    if (fire(changeEvent)) {
                        this.isChecked = !isChecked;
                    }
                    Pools.free(changeEvent);
                }
            }
        }
    }

    public void toggle() {
        setChecked(!this.isChecked);
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public boolean isPressed() {
        return this.clickListener.isPressed();
    }

    public boolean isOver() {
        return this.clickListener.isOver();
    }

    public ClickListener getClickListener() {
        return this.clickListener;
    }

    public boolean isDisabled() {
        return this.isDisabled;
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public void setStyle(ButtonStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        Drawable background = style.up;
        if (background == null) {
            background = style.down;
            if (background == null) {
                background = style.checked;
            }
        }
        if (background != null) {
            padBottom(background.getBottomHeight());
            padTop(background.getTopHeight());
            padLeft(background.getLeftWidth());
            padRight(background.getRightWidth());
        }
        invalidateHierarchy();
    }

    public ButtonStyle getStyle() {
        return this.style;
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        Drawable background;
        float offsetX;
        float offsetY;
        int i;
        validate();
        if (!isPressed() || this.isDisabled) {
            if (this.isDisabled && this.style.disabled != null) {
                background = this.style.disabled;
            } else if (this.isChecked && this.style.checked != null) {
                background = (!isOver() || this.style.checkedOver == null) ? this.style.checked : this.style.checkedOver;
            } else if (!isOver() || this.style.over == null) {
                background = this.style.up;
            } else {
                background = this.style.over;
            }
            offsetX = this.style.unpressedOffsetX;
            offsetY = this.style.unpressedOffsetY;
        } else {
            background = this.style.down == null ? this.style.up : this.style.down;
            offsetX = this.style.pressedOffsetX;
            offsetY = this.style.pressedOffsetY;
        }
        if (background != null) {
            Color color = getColor();
            batch.setColor(color.f40r, color.f39g, color.f38b, color.f37a * parentAlpha);
            background.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
        Array<Actor> children = getChildren();
        for (i = 0; i < children.size; i++) {
            ((Actor) children.get(i)).translate(offsetX, offsetY);
        }
        super.draw(batch, parentAlpha);
        for (i = 0; i < children.size; i++) {
            ((Actor) children.get(i)).translate(-offsetX, -offsetY);
        }
    }

    protected void drawBackground(SpriteBatch batch, float parentAlpha) {
    }

    public float getPrefWidth() {
        float width = super.getPrefWidth();
        if (this.style.up != null) {
            width = Math.max(width, this.style.up.getMinWidth());
        }
        if (this.style.down != null) {
            width = Math.max(width, this.style.down.getMinWidth());
        }
        if (this.style.checked != null) {
            return Math.max(width, this.style.checked.getMinWidth());
        }
        return width;
    }

    public float getPrefHeight() {
        float height = super.getPrefHeight();
        if (this.style.up != null) {
            height = Math.max(height, this.style.up.getMinHeight());
        }
        if (this.style.down != null) {
            height = Math.max(height, this.style.down.getMinHeight());
        }
        if (this.style.checked != null) {
            return Math.max(height, this.style.checked.getMinHeight());
        }
        return height;
    }

    public float getMinWidth() {
        return getPrefWidth();
    }

    public float getMinHeight() {
        return getPrefHeight();
    }
}
