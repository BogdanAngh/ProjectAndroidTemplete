package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pools;
import com.google.android.gms.cast.TextTrackStyle;

public class List extends Widget implements Cullable {
    private Rectangle cullingArea;
    private float itemHeight;
    private String[] items;
    private float prefHeight;
    private float prefWidth;
    private boolean selectable;
    private int selectedIndex;
    private ListStyle style;
    private float textOffsetX;
    private float textOffsetY;

    public static class ListStyle {
        public BitmapFont font;
        public Color fontColorSelected;
        public Color fontColorUnselected;
        public Drawable selection;

        public ListStyle() {
            this.fontColorSelected = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            this.fontColorUnselected = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        }

        public ListStyle(BitmapFont font, Color fontColorSelected, Color fontColorUnselected, Drawable selection) {
            this.fontColorSelected = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            this.fontColorUnselected = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            this.font = font;
            this.fontColorSelected.set(fontColorSelected);
            this.fontColorUnselected.set(fontColorUnselected);
            this.selection = selection;
        }

        public ListStyle(ListStyle style) {
            this.fontColorSelected = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            this.fontColorUnselected = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            this.font = style.font;
            this.fontColorSelected.set(style.fontColorSelected);
            this.fontColorUnselected.set(style.fontColorUnselected);
            this.selection = style.selection;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.List.1 */
    class C05741 extends InputListener {
        C05741() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if ((pointer == 0 && button != 0) || !List.this.isSelectable()) {
                return false;
            }
            List.this.touchDown(y);
            return true;
        }
    }

    public List(Object[] items, Skin skin) {
        this(items, (ListStyle) skin.get(ListStyle.class));
    }

    public List(Object[] items, Skin skin, String styleName) {
        this(items, (ListStyle) skin.get(styleName, ListStyle.class));
    }

    public List(Object[] items, ListStyle style) {
        this.selectable = true;
        setStyle(style);
        setItems(items);
        setWidth(getPrefWidth());
        setHeight(getPrefHeight());
        addListener(new C05741());
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    void touchDown(float y) {
        int oldIndex = this.selectedIndex;
        this.selectedIndex = (int) ((getHeight() - y) / this.itemHeight);
        this.selectedIndex = Math.max(0, this.selectedIndex);
        this.selectedIndex = Math.min(this.items.length - 1, this.selectedIndex);
        if (oldIndex != this.selectedIndex) {
            ChangeEvent changeEvent = (ChangeEvent) Pools.obtain(ChangeEvent.class);
            if (fire(changeEvent)) {
                this.selectedIndex = oldIndex;
            }
            Pools.free(changeEvent);
        }
    }

    public void setStyle(ListStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        if (this.items != null) {
            setItems(this.items);
        } else {
            invalidateHierarchy();
        }
    }

    public ListStyle getStyle() {
        return this.style;
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        BitmapFont font = this.style.font;
        Drawable selectedDrawable = this.style.selection;
        Color fontColorSelected = this.style.fontColorSelected;
        Color fontColorUnselected = this.style.fontColorUnselected;
        Color color = getColor();
        batch.setColor(color.f40r, color.f39g, color.f38b, color.f37a * parentAlpha);
        float x = getX();
        float y = getY();
        font.setColor(fontColorUnselected.f40r, fontColorUnselected.f39g, fontColorUnselected.f38b, fontColorUnselected.f37a * parentAlpha);
        float itemY = getHeight();
        for (int i = 0; i < this.items.length; i++) {
            if (this.cullingArea == null || (itemY - this.itemHeight <= this.cullingArea.f76y + this.cullingArea.height && itemY >= this.cullingArea.f76y)) {
                if (this.selectedIndex == i) {
                    selectedDrawable.draw(batch, x, (y + itemY) - this.itemHeight, getWidth(), this.itemHeight);
                    font.setColor(fontColorSelected.f40r, fontColorSelected.f39g, fontColorSelected.f38b, fontColorSelected.f37a * parentAlpha);
                }
                font.draw(batch, this.items[i], this.textOffsetX + x, (y + itemY) - this.textOffsetY);
                if (this.selectedIndex == i) {
                    font.setColor(fontColorUnselected.f40r, fontColorUnselected.f39g, fontColorUnselected.f38b, fontColorUnselected.f37a * parentAlpha);
                }
            } else if (itemY < this.cullingArea.f76y) {
                return;
            }
            itemY -= this.itemHeight;
        }
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public void setSelectedIndex(int index) {
        if (index < -1 || index >= this.items.length) {
            throw new GdxRuntimeException("index must be >= -1 and < " + this.items.length + ": " + index);
        }
        this.selectedIndex = index;
    }

    public String getSelection() {
        if (this.items.length == 0 || this.selectedIndex == -1) {
            return null;
        }
        return this.items[this.selectedIndex];
    }

    public int setSelection(String item) {
        this.selectedIndex = -1;
        int n = this.items.length;
        for (int i = 0; i < n; i++) {
            if (this.items[i].equals(item)) {
                this.selectedIndex = i;
                break;
            }
        }
        return this.selectedIndex;
    }

    public void setItems(Object[] objects) {
        if (objects == null) {
            throw new IllegalArgumentException("items cannot be null.");
        }
        if (objects instanceof String[]) {
            this.items = (String[]) objects;
        } else {
            String[] strings = new String[objects.length];
            int n = objects.length;
            int i;
            for (i = 0; i < n; i++) {
                strings[i] = String.valueOf(objects[i]);
            }
            this.items = strings;
        }
        this.selectedIndex = 0;
        BitmapFont font = this.style.font;
        Drawable selectedDrawable = this.style.selection;
        this.itemHeight = font.getCapHeight() - (font.getDescent() * 2.0f);
        this.itemHeight += selectedDrawable.getTopHeight() + selectedDrawable.getBottomHeight();
        this.textOffsetX = selectedDrawable.getLeftWidth();
        this.textOffsetY = selectedDrawable.getTopHeight() - font.getDescent();
        this.prefWidth = 0.0f;
        for (CharSequence bounds : this.items) {
            this.prefWidth = Math.max(font.getBounds(bounds).width, this.prefWidth);
        }
        this.prefWidth += selectedDrawable.getLeftWidth() + selectedDrawable.getRightWidth();
        this.prefHeight = ((float) this.items.length) * this.itemHeight;
        invalidateHierarchy();
    }

    public String[] getItems() {
        return this.items;
    }

    public float getItemHeight() {
        return this.itemHeight;
    }

    public float getPrefWidth() {
        return this.prefWidth;
    }

    public float getPrefHeight() {
        return this.prefHeight;
    }

    public void setCullingArea(Rectangle cullingArea) {
        this.cullingArea = cullingArea;
    }
}
