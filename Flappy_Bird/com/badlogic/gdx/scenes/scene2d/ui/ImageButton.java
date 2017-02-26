package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.esotericsoftware.tablelayout.Cell;

public class ImageButton extends Button {
    private final Image image;
    private ImageButtonStyle style;

    public static class ImageButtonStyle extends ButtonStyle {
        public Drawable imageChecked;
        public Drawable imageCheckedOver;
        public Drawable imageDisabled;
        public Drawable imageDown;
        public Drawable imageOver;
        public Drawable imageUp;

        public ImageButtonStyle(Drawable up, Drawable down, Drawable checked, Drawable imageUp, Drawable imageDown, Drawable imageChecked) {
            super(up, down, checked);
            this.imageUp = imageUp;
            this.imageDown = imageDown;
            this.imageChecked = imageChecked;
        }

        public ImageButtonStyle(ImageButtonStyle style) {
            super(style);
            this.imageUp = style.imageUp;
            this.imageDown = style.imageDown;
            this.imageOver = style.imageOver;
            this.imageChecked = style.imageChecked;
            this.imageCheckedOver = style.imageCheckedOver;
            this.imageDisabled = style.imageDisabled;
        }

        public ImageButtonStyle(ButtonStyle style) {
            super(style);
        }
    }

    public ImageButton(Skin skin) {
        this((ImageButtonStyle) skin.get(ImageButtonStyle.class));
    }

    public ImageButton(Skin skin, String styleName) {
        this((ImageButtonStyle) skin.get(styleName, ImageButtonStyle.class));
    }

    public ImageButton(ImageButtonStyle style) {
        super((ButtonStyle) style);
        this.image = new Image();
        this.image.setScaling(Scaling.fit);
        add(this.image);
        setStyle(style);
        setWidth(getPrefWidth());
        setHeight(getPrefHeight());
    }

    public ImageButton(Drawable imageUp) {
        this(new ImageButtonStyle(null, null, null, imageUp, null, null));
    }

    public ImageButton(Drawable imageUp, Drawable imageDown) {
        this(new ImageButtonStyle(null, null, null, imageUp, imageDown, null));
    }

    public ImageButton(Drawable imageUp, Drawable imageDown, Drawable imageChecked) {
        this(new ImageButtonStyle(null, null, null, imageUp, imageDown, imageChecked));
    }

    public void setStyle(ButtonStyle style) {
        if (style instanceof ImageButtonStyle) {
            super.setStyle(style);
            this.style = (ImageButtonStyle) style;
            if (this.image != null) {
                updateImage();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("style must be an ImageButtonStyle.");
    }

    public ImageButtonStyle getStyle() {
        return this.style;
    }

    private void updateImage() {
        boolean isPressed = isPressed();
        if (this.isDisabled && this.style.imageDisabled != null) {
            this.image.setDrawable(this.style.imageDisabled);
        } else if (isPressed && this.style.imageDown != null) {
            this.image.setDrawable(this.style.imageDown);
        } else if (this.isChecked && this.style.imageChecked != null) {
            Image image = this.image;
            Drawable drawable = (this.style.imageCheckedOver == null || !isOver()) ? this.style.imageChecked : this.style.imageCheckedOver;
            image.setDrawable(drawable);
        } else if (isOver() && this.style.imageOver != null) {
            this.image.setDrawable(this.style.imageOver);
        } else if (this.style.imageUp != null) {
            this.image.setDrawable(this.style.imageUp);
        }
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        updateImage();
        super.draw(batch, parentAlpha);
    }

    public Image getImage() {
        return this.image;
    }

    public Cell getImageCell() {
        return getCell(this.image);
    }
}
