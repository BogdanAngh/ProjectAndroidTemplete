package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;
import com.google.android.gms.cast.TextTrackStyle;

public class SelectBox extends Widget implements Disableable {
    static final Vector2 tmpCoords;
    private final TextBounds bounds;
    private ClickListener clickListener;
    boolean disabled;
    String[] items;
    SelectList list;
    int maxListCount;
    private float prefHeight;
    private float prefWidth;
    int selectedIndex;
    SelectBoxStyle style;

    public static class SelectBoxStyle {
        public Drawable background;
        public Drawable backgroundDisabled;
        public Drawable backgroundOpen;
        public Drawable backgroundOver;
        public Color disabledFontColor;
        public BitmapFont font;
        public Color fontColor;
        public ListStyle listStyle;
        public ScrollPaneStyle scrollStyle;

        public SelectBoxStyle() {
            this.fontColor = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        }

        public SelectBoxStyle(BitmapFont font, Color fontColor, Drawable background, ScrollPaneStyle scrollStyle, ListStyle listStyle) {
            this.fontColor = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            this.font = font;
            this.fontColor.set(fontColor);
            this.background = background;
            this.scrollStyle = scrollStyle;
            this.listStyle = listStyle;
        }

        public SelectBoxStyle(SelectBoxStyle style) {
            this.fontColor = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            this.font = style.font;
            this.fontColor.set(style.fontColor);
            if (style.disabledFontColor != null) {
                this.disabledFontColor = new Color(style.disabledFontColor);
            }
            this.background = style.background;
            this.backgroundOver = style.backgroundOver;
            this.backgroundOpen = style.backgroundOpen;
            this.backgroundDisabled = style.backgroundDisabled;
            this.scrollStyle = new ScrollPaneStyle(style.scrollStyle);
            this.listStyle = new ListStyle(style.listStyle);
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.SelectBox.1 */
    class C08411 extends ClickListener {
        C08411() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if ((pointer == 0 && button != 0) || SelectBox.this.disabled) {
                return false;
            }
            Stage stage = SelectBox.this.getStage();
            if (SelectBox.this.list == null) {
                SelectBox.this.list = new SelectList();
            }
            SelectBox.this.list.show(stage);
            return true;
        }
    }

    class SelectList extends ScrollPane {
        final List list;
        final Vector2 screenCoords;

        /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectList.1 */
        class C05781 extends InputListener {
            final /* synthetic */ SelectBox val$this$0;

            C05781(SelectBox selectBox) {
                this.val$this$0 = selectBox;
            }

            public boolean mouseMoved(InputEvent event, float x, float y) {
                SelectList.this.list.setSelectedIndex(Math.min(SelectBox.this.items.length - 1, (int) ((SelectList.this.list.getHeight() - y) / SelectList.this.list.getItemHeight())));
                return true;
            }
        }

        /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectList.2 */
        class C05792 extends InputListener {
            final /* synthetic */ SelectBox val$this$0;

            C05792(SelectBox selectBox) {
                this.val$this$0 = selectBox;
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget() == SelectList.this.list) {
                    return true;
                }
                SelectBox.this.hideList();
                return false;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (SelectList.this.hit(x, y, true) == SelectList.this.list) {
                    SelectBox.this.setSelection(SelectList.this.list.getSelectedIndex());
                    ChangeEvent changeEvent = (ChangeEvent) Pools.obtain(ChangeEvent.class);
                    SelectBox.this.fire(changeEvent);
                    Pools.free(changeEvent);
                    SelectBox.this.hideList();
                }
            }
        }

        public SelectList() {
            super(null, SelectBox.this.style.scrollStyle);
            this.screenCoords = new Vector2();
            setOverscroll(false, false);
            setFadeScrollBars(false);
            this.list = new List(new Object[0], SelectBox.this.style.listStyle);
            setWidget(this.list);
            this.list.addListener(new C05781(SelectBox.this));
            addListener(new C05792(SelectBox.this));
        }

        public void show(Stage stage) {
            int length;
            stage.addActor(this);
            SelectBox.this.localToStageCoordinates(SelectBox.tmpCoords.set(0.0f, 0.0f));
            this.screenCoords.set(SelectBox.tmpCoords);
            this.list.setItems(SelectBox.this.items);
            this.list.setSelectedIndex(SelectBox.this.selectedIndex);
            float itemHeight = this.list.getItemHeight();
            if (SelectBox.this.maxListCount <= 0) {
                length = SelectBox.this.items.length;
            } else {
                length = Math.min(SelectBox.this.maxListCount, SelectBox.this.items.length);
            }
            float height = itemHeight * ((float) length);
            Drawable background = getStyle().background;
            if (background != null) {
                height += background.getTopHeight() + background.getBottomHeight();
            }
            float heightBelow = SelectBox.tmpCoords.f101y;
            float heightAbove = (stage.getCamera().viewportHeight - SelectBox.tmpCoords.f101y) - SelectBox.this.getHeight();
            boolean below = true;
            if (height > heightBelow) {
                if (heightAbove > heightBelow) {
                    below = false;
                    height = Math.min(height, heightAbove);
                } else {
                    height = heightBelow;
                }
            }
            if (below) {
                setY(SelectBox.tmpCoords.f101y - height);
            } else {
                setY(SelectBox.tmpCoords.f101y + SelectBox.this.getHeight());
            }
            setX(SelectBox.tmpCoords.f100x);
            setWidth(SelectBox.this.getWidth());
            setHeight(height);
            scrollToCenter(0.0f, (this.list.getHeight() - (((float) SelectBox.this.selectedIndex) * itemHeight)) - (itemHeight / 2.0f), 0.0f, 0.0f);
            updateVisualScroll();
            clearActions();
            getColor().f37a = 0.0f;
            addAction(Actions.fadeIn(0.3f, Interpolation.fade));
            stage.setScrollFocus(this);
        }

        public Actor hit(float x, float y, boolean touchable) {
            Actor actor = super.hit(x, y, touchable);
            return actor != null ? actor : this;
        }

        public void act(float delta) {
            super.act(delta);
            SelectBox.this.localToStageCoordinates(SelectBox.tmpCoords.set(0.0f, 0.0f));
            if (SelectBox.tmpCoords.f100x != this.screenCoords.f100x || SelectBox.tmpCoords.f101y != this.screenCoords.f101y) {
                SelectBox.this.hideList();
            }
        }
    }

    static {
        tmpCoords = new Vector2();
    }

    public SelectBox(Object[] items, Skin skin) {
        this(items, (SelectBoxStyle) skin.get(SelectBoxStyle.class));
    }

    public SelectBox(Object[] items, Skin skin, String styleName) {
        this(items, (SelectBoxStyle) skin.get(styleName, SelectBoxStyle.class));
    }

    public SelectBox(Object[] items, SelectBoxStyle style) {
        this.selectedIndex = 0;
        this.bounds = new TextBounds();
        setStyle(style);
        setItems(items);
        setWidth(getPrefWidth());
        setHeight(getPrefHeight());
        EventListener c08411 = new C08411();
        this.clickListener = c08411;
        addListener(c08411);
    }

    public void setMaxListCount(int maxListCount) {
        this.maxListCount = maxListCount;
    }

    public int getMaxListCount() {
        return this.maxListCount;
    }

    public void setStyle(SelectBoxStyle style) {
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

    public SelectBoxStyle getStyle() {
        return this.style;
    }

    public void setItems(Object[] objects) {
        float f = 0.0f;
        if (objects == null) {
            throw new IllegalArgumentException("items cannot be null.");
        }
        if (!(objects instanceof String[])) {
            String[] strings = new String[objects.length];
            int n = objects.length;
            int i;
            for (i = 0; i < n; i++) {
                strings[i] = String.valueOf(objects[i]);
            }
            objects = strings;
        }
        this.items = (String[]) objects;
        this.selectedIndex = 0;
        Drawable bg = this.style.background;
        BitmapFont font = this.style.font;
        this.prefHeight = Math.max(((bg.getTopHeight() + bg.getBottomHeight()) + font.getCapHeight()) - (font.getDescent() * 2.0f), bg.getMinHeight());
        float maxItemWidth = 0.0f;
        for (CharSequence bounds : this.items) {
            maxItemWidth = Math.max(font.getBounds(bounds).width, maxItemWidth);
        }
        this.prefWidth = (bg.getLeftWidth() + bg.getRightWidth()) + maxItemWidth;
        ListStyle listStyle = this.style.listStyle;
        ScrollPaneStyle scrollStyle = this.style.scrollStyle;
        float f2 = this.prefWidth;
        float rightWidth = listStyle.selection.getRightWidth() + (((scrollStyle.background.getLeftWidth() + maxItemWidth) + scrollStyle.background.getRightWidth()) + listStyle.selection.getLeftWidth());
        float minWidth = this.style.scrollStyle.vScroll != null ? this.style.scrollStyle.vScroll.getMinWidth() : 0.0f;
        if (this.style.scrollStyle.vScrollKnob != null) {
            f = this.style.scrollStyle.vScrollKnob.getMinWidth();
        }
        this.prefWidth = Math.max(f2, Math.max(minWidth, f) + rightWidth);
        if (this.items.length > 0) {
            ChangeEvent changeEvent = (ChangeEvent) Pools.obtain(ChangeEvent.class);
            fire(changeEvent);
            Pools.free(changeEvent);
        }
        invalidateHierarchy();
    }

    public String[] getItems() {
        return this.items;
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        Drawable background;
        if (this.disabled) {
            background = this.style.backgroundDisabled;
        } else if (this.list != null && this.list.getParent() != null && this.style.backgroundOpen != null) {
            background = this.style.backgroundOpen;
        } else if (!this.clickListener.isOver() || this.style.backgroundOver == null) {
            background = this.style.background;
        } else {
            background = this.style.backgroundOver;
        }
        BitmapFont font = this.style.font;
        Color fontColor = (!this.disabled || this.style.disabledFontColor == null) ? this.style.fontColor : this.style.disabledFontColor;
        Color color = getColor();
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        batch.setColor(color.f40r, color.f39g, color.f38b, color.f37a * parentAlpha);
        background.draw(batch, x, y, width, height);
        if (this.items.length > 0) {
            int numGlyphs = font.computeVisibleGlyphs(this.items[this.selectedIndex], 0, this.items[this.selectedIndex].length(), (width - background.getLeftWidth()) - background.getRightWidth());
            this.bounds.set(font.getBounds(this.items[this.selectedIndex]));
            float textY = (float) ((int) ((((height - (background.getBottomHeight() + background.getTopHeight())) / 2.0f) + background.getBottomHeight()) + (this.bounds.height / 2.0f)));
            font.setColor(fontColor.f40r, fontColor.f39g, fontColor.f38b, fontColor.f37a * parentAlpha);
            font.draw(batch, this.items[this.selectedIndex], x + background.getLeftWidth(), y + textY, 0, numGlyphs);
        }
    }

    public void setSelection(int selection) {
        this.selectedIndex = selection;
    }

    public void setSelection(String item) {
        for (int i = 0; i < this.items.length; i++) {
            if (this.items[i].equals(item)) {
                this.selectedIndex = i;
            }
        }
    }

    public int getSelectionIndex() {
        return this.selectedIndex;
    }

    public String getSelection() {
        return this.items[this.selectedIndex];
    }

    public void setDisabled(boolean disabled) {
        if (disabled && !this.disabled) {
            hideList();
        }
        this.disabled = disabled;
    }

    public float getPrefWidth() {
        return this.prefWidth;
    }

    public float getPrefHeight() {
        return this.prefHeight;
    }

    public void hideList() {
        if (this.list != null && this.list.getParent() != null) {
            this.list.addAction(Actions.sequence(Actions.fadeOut(0.15f, Interpolation.fade), Actions.removeActor()));
        }
    }
}
