package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.google.android.gms.cast.TextTrackStyle;

public class Window extends Table {
    private static final Vector2 tmpPosition;
    private static final Vector2 tmpSize;
    Table buttonTable;
    final Vector2 dragOffset;
    boolean dragging;
    boolean isModal;
    boolean isMovable;
    boolean keepWithinStage;
    private WindowStyle style;
    private String title;
    private int titleAlignment;
    private BitmapFontCache titleCache;

    public static class WindowStyle {
        public Drawable background;
        public Drawable stageBackground;
        public BitmapFont titleFont;
        public Color titleFontColor;

        public WindowStyle() {
            this.titleFontColor = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        }

        public WindowStyle(BitmapFont titleFont, Color titleFontColor, Drawable background) {
            this.titleFontColor = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            this.background = background;
            this.titleFont = titleFont;
            this.titleFontColor.set(titleFontColor);
        }

        public WindowStyle(WindowStyle style) {
            this.titleFontColor = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            this.background = style.background;
            this.titleFont = style.titleFont;
            this.titleFontColor = new Color(style.titleFontColor);
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Window.1 */
    class C05871 extends InputListener {
        C05871() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Window.this.toFront();
            return false;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Window.2 */
    class C05882 extends InputListener {
        C05882() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (button == 0) {
                boolean z;
                Window window = Window.this;
                if (!Window.this.isMovable || Window.this.getHeight() - y > Window.this.getPadTop() || y >= Window.this.getHeight() || x <= 0.0f || x >= Window.this.getWidth()) {
                    z = false;
                } else {
                    z = true;
                }
                window.dragging = z;
                Window.this.dragOffset.set(x, y);
            }
            if (Window.this.dragging || Window.this.isModal) {
                return true;
            }
            return false;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (Window.this.dragging) {
                Window.this.dragging = false;
            }
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (Window.this.dragging) {
                Window.this.translate(x - Window.this.dragOffset.f100x, y - Window.this.dragOffset.f101y);
            }
        }

        public boolean mouseMoved(InputEvent event, float x, float y) {
            return Window.this.isModal;
        }

        public boolean scrolled(InputEvent event, float x, float y, int amount) {
            return Window.this.isModal;
        }

        public boolean keyDown(InputEvent event, int keycode) {
            return Window.this.isModal;
        }

        public boolean keyUp(InputEvent event, int keycode) {
            return Window.this.isModal;
        }

        public boolean keyTyped(InputEvent event, char character) {
            return Window.this.isModal;
        }
    }

    static {
        tmpPosition = new Vector2();
        tmpSize = new Vector2();
    }

    public Window(String title, Skin skin) {
        this(title, (WindowStyle) skin.get(WindowStyle.class));
        setSkin(skin);
    }

    public Window(String title, Skin skin, String styleName) {
        this(title, (WindowStyle) skin.get(styleName, WindowStyle.class));
        setSkin(skin);
    }

    public Window(String title, WindowStyle style) {
        this.isMovable = true;
        this.dragOffset = new Vector2();
        this.titleAlignment = 1;
        this.keepWithinStage = true;
        if (title == null) {
            throw new IllegalArgumentException("title cannot be null.");
        }
        this.title = title;
        setTouchable(Touchable.enabled);
        setClip(true);
        setStyle(style);
        setWidth(150.0f);
        setHeight(150.0f);
        setTitle(title);
        this.buttonTable = new Table();
        addActor(this.buttonTable);
        addCaptureListener(new C05871());
        addListener(new C05882());
    }

    public void setStyle(WindowStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        setBackground(style.background);
        this.titleCache = new BitmapFontCache(style.titleFont);
        this.titleCache.setColor(style.titleFontColor);
        if (this.title != null) {
            setTitle(this.title);
        }
        invalidateHierarchy();
    }

    public WindowStyle getStyle() {
        return this.style;
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        Stage stage = getStage();
        if (this.keepWithinStage && getParent() == stage.getRoot()) {
            float parentWidth = stage.getWidth();
            float parentHeight = stage.getHeight();
            if (getX() < 0.0f) {
                setX(0.0f);
            }
            if (getRight() > parentWidth) {
                setX(parentWidth - getWidth());
            }
            if (getY() < 0.0f) {
                setY(0.0f);
            }
            if (getTop() > parentHeight) {
                setY(parentHeight - getHeight());
            }
        }
        super.draw(batch, parentAlpha);
    }

    protected void drawBackground(SpriteBatch batch, float parentAlpha) {
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        float padTop = getPadTop();
        if (this.style.stageBackground != null) {
            Color color = getColor();
            batch.setColor(color.f40r, color.f39g, color.f38b, color.f37a * parentAlpha);
            Stage stage = getStage();
            stageToLocalCoordinates(tmpPosition.set(0.0f, 0.0f));
            stageToLocalCoordinates(tmpSize.set(stage.getWidth(), stage.getHeight()));
            this.style.stageBackground.draw(batch, x + tmpPosition.f100x, y + tmpPosition.f101y, x + tmpSize.f100x, y + tmpSize.f101y);
        }
        super.drawBackground(batch, parentAlpha);
        this.buttonTable.getColor().f37a = getColor().f37a;
        this.buttonTable.pack();
        this.buttonTable.setPosition(width - this.buttonTable.getWidth(), Math.min(height - padTop, height - this.buttonTable.getHeight()));
        this.buttonTable.translate(x, y);
        this.buttonTable.draw(batch, parentAlpha);
        this.buttonTable.translate(-x, -y);
        y += height;
        TextBounds bounds = this.titleCache.getBounds();
        if ((this.titleAlignment & 8) != 0) {
            x += getPadLeft();
        } else if ((this.titleAlignment & 16) != 0) {
            x += (width - bounds.width) - getPadRight();
        } else {
            x += (width - bounds.width) / 2.0f;
        }
        if ((this.titleAlignment & 2) == 0) {
            if ((this.titleAlignment & 4) != 0) {
                y -= padTop - bounds.height;
            } else {
                y -= (padTop - bounds.height) / 2.0f;
            }
        }
        this.titleCache.setColor(Color.tmp.set(getColor()).mul(this.style.titleFontColor));
        this.titleCache.setPosition((float) ((int) x), (float) ((int) y));
        this.titleCache.draw(batch, parentAlpha);
    }

    public Actor hit(float x, float y, boolean touchable) {
        Actor hit = super.hit(x, y, touchable);
        return (hit == null && this.isModal && (!touchable || getTouchable() == Touchable.enabled)) ? this : hit;
    }

    public void setTitle(String title) {
        this.title = title;
        this.titleCache.setMultiLineText(title, 0.0f, 0.0f);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitleAlignment(int titleAlignment) {
        this.titleAlignment = titleAlignment;
    }

    public void setMovable(boolean isMovable) {
        this.isMovable = isMovable;
    }

    public void setModal(boolean isModal) {
        this.isModal = isModal;
    }

    public void setKeepWithinStage(boolean keepWithinStage) {
        this.keepWithinStage = keepWithinStage;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public float getPrefWidth() {
        return Math.max(super.getPrefWidth(), (this.titleCache.getBounds().width + getPadLeft()) + getPadRight());
    }

    public Table getButtonTable() {
        return this.buttonTable;
    }
}
