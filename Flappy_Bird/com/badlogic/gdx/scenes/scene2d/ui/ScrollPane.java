package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class ScrollPane extends WidgetGroup {
    float amountX;
    float amountY;
    float areaHeight;
    float areaWidth;
    boolean cancelTouchFocus;
    private boolean clamp;
    private boolean disableX;
    private boolean disableY;
    int draggingPointer;
    float fadeAlpha;
    float fadeAlphaSeconds;
    float fadeDelay;
    float fadeDelaySeconds;
    private boolean fadeScrollBars;
    boolean flickScroll;
    private ActorGestureListener flickScrollListener;
    float flingTime;
    float flingTimer;
    private boolean forceScrollX;
    private boolean forceScrollY;
    final Rectangle hKnobBounds;
    final Rectangle hScrollBounds;
    boolean hScrollOnBottom;
    final Vector2 lastPoint;
    float maxX;
    float maxY;
    private float overscrollDistance;
    private float overscrollSpeedMax;
    private float overscrollSpeedMin;
    private boolean overscrollX;
    private boolean overscrollY;
    private final Rectangle scissorBounds;
    boolean scrollX;
    boolean scrollY;
    private boolean scrollbarsOnTop;
    private boolean smoothScrolling;
    private ScrollPaneStyle style;
    boolean touchScrollH;
    boolean touchScrollV;
    final Rectangle vKnobBounds;
    final Rectangle vScrollBounds;
    boolean vScrollOnRight;
    float velocityX;
    float velocityY;
    float visualAmountX;
    float visualAmountY;
    private Actor widget;
    private final Rectangle widgetAreaBounds;
    private final Rectangle widgetCullingArea;

    public static class ScrollPaneStyle {
        public Drawable background;
        public Drawable corner;
        public Drawable hScroll;
        public Drawable hScrollKnob;
        public Drawable vScroll;
        public Drawable vScrollKnob;

        public ScrollPaneStyle(Drawable background, Drawable hScroll, Drawable hScrollKnob, Drawable vScroll, Drawable vScrollKnob) {
            this.background = background;
            this.hScroll = hScroll;
            this.hScrollKnob = hScrollKnob;
            this.vScroll = vScroll;
            this.vScrollKnob = vScrollKnob;
        }

        public ScrollPaneStyle(ScrollPaneStyle style) {
            this.background = style.background;
            this.hScroll = style.hScroll;
            this.hScrollKnob = style.hScrollKnob;
            this.vScroll = style.vScroll;
            this.vScrollKnob = style.vScrollKnob;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.1 */
    class C05751 extends InputListener {
        private float handlePosition;

        C05751() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            int i = -1;
            if (ScrollPane.this.draggingPointer != -1) {
                return false;
            }
            if (pointer == 0 && button != 0) {
                return false;
            }
            ScrollPane.this.getStage().setScrollFocus(ScrollPane.this);
            if (!ScrollPane.this.flickScroll) {
                ScrollPane.this.resetFade();
            }
            if (ScrollPane.this.fadeAlpha == 0.0f) {
                return false;
            }
            ScrollPane scrollPane;
            float f;
            float f2;
            if (ScrollPane.this.scrollX && ScrollPane.this.hScrollBounds.contains(x, y)) {
                event.stop();
                ScrollPane.this.resetFade();
                if (ScrollPane.this.hKnobBounds.contains(x, y)) {
                    ScrollPane.this.lastPoint.set(x, y);
                    this.handlePosition = ScrollPane.this.hKnobBounds.f75x;
                    ScrollPane.this.touchScrollH = true;
                    ScrollPane.this.draggingPointer = pointer;
                    return true;
                }
                scrollPane = ScrollPane.this;
                f = ScrollPane.this.amountX;
                f2 = ScrollPane.this.areaWidth;
                if (x >= ScrollPane.this.hKnobBounds.f75x) {
                    i = 1;
                }
                scrollPane.setScrollX((((float) i) * f2) + f);
                return true;
            } else if (!ScrollPane.this.scrollY || !ScrollPane.this.vScrollBounds.contains(x, y)) {
                return false;
            } else {
                event.stop();
                ScrollPane.this.resetFade();
                if (ScrollPane.this.vKnobBounds.contains(x, y)) {
                    ScrollPane.this.lastPoint.set(x, y);
                    this.handlePosition = ScrollPane.this.vKnobBounds.f76y;
                    ScrollPane.this.touchScrollV = true;
                    ScrollPane.this.draggingPointer = pointer;
                    return true;
                }
                scrollPane = ScrollPane.this;
                f = ScrollPane.this.amountY;
                f2 = ScrollPane.this.areaHeight;
                if (y < ScrollPane.this.vKnobBounds.f76y) {
                    i = 1;
                }
                scrollPane.setScrollY((((float) i) * f2) + f);
                return true;
            }
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (pointer == ScrollPane.this.draggingPointer) {
                ScrollPane.this.cancel();
            }
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (pointer == ScrollPane.this.draggingPointer) {
                float total;
                if (ScrollPane.this.touchScrollH) {
                    float scrollH = this.handlePosition + (x - ScrollPane.this.lastPoint.f100x);
                    this.handlePosition = scrollH;
                    scrollH = Math.min((ScrollPane.this.hScrollBounds.f75x + ScrollPane.this.hScrollBounds.width) - ScrollPane.this.hKnobBounds.width, Math.max(ScrollPane.this.hScrollBounds.f75x, scrollH));
                    total = ScrollPane.this.hScrollBounds.width - ScrollPane.this.hKnobBounds.width;
                    if (total != 0.0f) {
                        ScrollPane.this.setScrollPercentX((scrollH - ScrollPane.this.hScrollBounds.f75x) / total);
                    }
                    ScrollPane.this.lastPoint.set(x, y);
                } else if (ScrollPane.this.touchScrollV) {
                    float scrollV = this.handlePosition + (y - ScrollPane.this.lastPoint.f101y);
                    this.handlePosition = scrollV;
                    scrollV = Math.min((ScrollPane.this.vScrollBounds.f76y + ScrollPane.this.vScrollBounds.height) - ScrollPane.this.vKnobBounds.height, Math.max(ScrollPane.this.vScrollBounds.f76y, scrollV));
                    total = ScrollPane.this.vScrollBounds.height - ScrollPane.this.vKnobBounds.height;
                    if (total != 0.0f) {
                        ScrollPane.this.setScrollPercentY(TextTrackStyle.DEFAULT_FONT_SCALE - ((scrollV - ScrollPane.this.vScrollBounds.f76y) / total));
                    }
                    ScrollPane.this.lastPoint.set(x, y);
                }
            }
        }

        public boolean mouseMoved(InputEvent event, float x, float y) {
            if (!ScrollPane.this.flickScroll) {
                ScrollPane.this.resetFade();
            }
            return false;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.2 */
    class C05762 extends ActorGestureListener {
        C05762() {
        }

        public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
            ScrollPane.this.resetFade();
            ScrollPane scrollPane = ScrollPane.this;
            scrollPane.amountX -= deltaX;
            scrollPane = ScrollPane.this;
            scrollPane.amountY += deltaY;
            ScrollPane.this.clamp();
            ScrollPane.this.cancelTouchFocusedChild(event);
        }

        public void fling(InputEvent event, float x, float y, int button) {
            if (Math.abs(x) > 150.0f) {
                ScrollPane.this.flingTimer = ScrollPane.this.flingTime;
                ScrollPane.this.velocityX = x;
                ScrollPane.this.cancelTouchFocusedChild(event);
            }
            if (Math.abs(y) > 150.0f) {
                ScrollPane.this.flingTimer = ScrollPane.this.flingTime;
                ScrollPane.this.velocityY = -y;
                ScrollPane.this.cancelTouchFocusedChild(event);
            }
        }

        public boolean handle(Event event) {
            if (!super.handle(event)) {
                return false;
            }
            if (((InputEvent) event).getType() == Type.touchDown) {
                ScrollPane.this.flingTimer = 0.0f;
            }
            return true;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.3 */
    class C05773 extends InputListener {
        C05773() {
        }

        public boolean scrolled(InputEvent event, float x, float y, int amount) {
            ScrollPane.this.resetFade();
            if (ScrollPane.this.scrollY) {
                ScrollPane.this.setScrollY(ScrollPane.this.amountY + (ScrollPane.this.getMouseWheelY() * ((float) amount)));
            } else if (ScrollPane.this.scrollX) {
                ScrollPane.this.setScrollX(ScrollPane.this.amountX + (ScrollPane.this.getMouseWheelX() * ((float) amount)));
            }
            return true;
        }
    }

    public ScrollPane(Actor widget) {
        this(widget, new ScrollPaneStyle());
    }

    public ScrollPane(Actor widget, Skin skin) {
        this(widget, (ScrollPaneStyle) skin.get(ScrollPaneStyle.class));
    }

    public ScrollPane(Actor widget, Skin skin, String styleName) {
        this(widget, (ScrollPaneStyle) skin.get(styleName, ScrollPaneStyle.class));
    }

    public ScrollPane(Actor widget, ScrollPaneStyle style) {
        this.hScrollBounds = new Rectangle();
        this.vScrollBounds = new Rectangle();
        this.hKnobBounds = new Rectangle();
        this.vKnobBounds = new Rectangle();
        this.widgetAreaBounds = new Rectangle();
        this.widgetCullingArea = new Rectangle();
        this.scissorBounds = new Rectangle();
        this.vScrollOnRight = true;
        this.hScrollOnBottom = true;
        this.lastPoint = new Vector2();
        this.fadeScrollBars = true;
        this.smoothScrolling = true;
        this.fadeAlphaSeconds = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.fadeDelaySeconds = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.cancelTouchFocus = true;
        this.flickScroll = true;
        this.overscrollX = true;
        this.overscrollY = true;
        this.flingTime = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.overscrollDistance = 50.0f;
        this.overscrollSpeedMin = BitmapDescriptorFactory.HUE_ORANGE;
        this.overscrollSpeedMax = 200.0f;
        this.clamp = true;
        this.draggingPointer = -1;
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        setWidget(widget);
        setWidth(150.0f);
        setHeight(150.0f);
        addCaptureListener(new C05751());
        this.flickScrollListener = new C05762();
        addListener(this.flickScrollListener);
        addListener(new C05773());
    }

    void resetFade() {
        this.fadeAlpha = this.fadeAlphaSeconds;
        this.fadeDelay = this.fadeDelaySeconds;
    }

    void cancelTouchFocusedChild(InputEvent event) {
        if (this.cancelTouchFocus) {
            Stage stage = getStage();
            if (stage != null) {
                stage.cancelTouchFocus(this.flickScrollListener, this);
            }
        }
    }

    public void cancel() {
        this.draggingPointer = -1;
        this.touchScrollH = false;
        this.touchScrollV = false;
        this.flickScrollListener.getGestureDetector().cancel();
    }

    void clamp() {
        if (this.clamp) {
            scrollX(this.overscrollX ? MathUtils.clamp(this.amountX, -this.overscrollDistance, this.maxX + this.overscrollDistance) : MathUtils.clamp(this.amountX, 0.0f, this.maxX));
            scrollY(this.overscrollY ? MathUtils.clamp(this.amountY, -this.overscrollDistance, this.maxY + this.overscrollDistance) : MathUtils.clamp(this.amountY, 0.0f, this.maxY));
        }
    }

    public void setStyle(ScrollPaneStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        invalidateHierarchy();
    }

    public ScrollPaneStyle getStyle() {
        return this.style;
    }

    public void act(float delta) {
        super.act(delta);
        boolean panning = this.flickScrollListener.getGestureDetector().isPanning();
        if (!(this.fadeAlpha <= 0.0f || !this.fadeScrollBars || panning || this.touchScrollH || this.touchScrollV)) {
            this.fadeDelay -= delta;
            if (this.fadeDelay <= 0.0f) {
                this.fadeAlpha = Math.max(0.0f, this.fadeAlpha - delta);
            }
        }
        if (this.flingTimer > 0.0f) {
            resetFade();
            float alpha = this.flingTimer / this.flingTime;
            this.amountX -= (this.velocityX * alpha) * delta;
            this.amountY -= (this.velocityY * alpha) * delta;
            clamp();
            if (this.amountX == (-this.overscrollDistance)) {
                this.velocityX = 0.0f;
            }
            if (this.amountX >= this.maxX + this.overscrollDistance) {
                this.velocityX = 0.0f;
            }
            if (this.amountY == (-this.overscrollDistance)) {
                this.velocityY = 0.0f;
            }
            if (this.amountY >= this.maxY + this.overscrollDistance) {
                this.velocityY = 0.0f;
            }
            this.flingTimer -= delta;
            if (this.flingTimer <= 0.0f) {
                this.velocityX = 0.0f;
                this.velocityY = 0.0f;
            }
        }
        if (!this.smoothScrolling || this.flingTimer > 0.0f || this.touchScrollH || this.touchScrollV || panning) {
            if (this.visualAmountX != this.amountX) {
                visualScrollX(this.amountX);
            }
            if (this.visualAmountY != this.amountY) {
                visualScrollY(this.amountY);
            }
        } else {
            if (this.visualAmountX != this.amountX) {
                if (this.visualAmountX < this.amountX) {
                    visualScrollX(Math.min(this.amountX, this.visualAmountX + Math.max(150.0f * delta, ((this.amountX - this.visualAmountX) * 5.0f) * delta)));
                } else {
                    visualScrollX(Math.max(this.amountX, this.visualAmountX - Math.max(150.0f * delta, ((this.visualAmountX - this.amountX) * 5.0f) * delta)));
                }
            }
            if (this.visualAmountY != this.amountY) {
                if (this.visualAmountY < this.amountY) {
                    visualScrollY(Math.min(this.amountY, this.visualAmountY + Math.max(150.0f * delta, ((this.amountY - this.visualAmountY) * 5.0f) * delta)));
                } else {
                    visualScrollY(Math.max(this.amountY, this.visualAmountY - Math.max(150.0f * delta, ((this.visualAmountY - this.amountY) * 5.0f) * delta)));
                }
            }
        }
        if (!panning) {
            if (this.overscrollX && this.scrollX) {
                if (this.amountX < 0.0f) {
                    resetFade();
                    this.amountX += (this.overscrollSpeedMin + (((this.overscrollSpeedMax - this.overscrollSpeedMin) * (-this.amountX)) / this.overscrollDistance)) * delta;
                    if (this.amountX > 0.0f) {
                        scrollX(0.0f);
                    }
                } else if (this.amountX > this.maxX) {
                    resetFade();
                    this.amountX -= (this.overscrollSpeedMin + (((this.overscrollSpeedMax - this.overscrollSpeedMin) * (-(this.maxX - this.amountX))) / this.overscrollDistance)) * delta;
                    if (this.amountX < this.maxX) {
                        scrollX(this.maxX);
                    }
                }
            }
            if (!this.overscrollY || !this.scrollY) {
                return;
            }
            if (this.amountY < 0.0f) {
                resetFade();
                this.amountY += (this.overscrollSpeedMin + (((this.overscrollSpeedMax - this.overscrollSpeedMin) * (-this.amountY)) / this.overscrollDistance)) * delta;
                if (this.amountY > 0.0f) {
                    scrollY(0.0f);
                }
            } else if (this.amountY > this.maxY) {
                resetFade();
                this.amountY -= (this.overscrollSpeedMin + (((this.overscrollSpeedMax - this.overscrollSpeedMin) * (-(this.maxY - this.amountY))) / this.overscrollDistance)) * delta;
                if (this.amountY < this.maxY) {
                    scrollY(this.maxY);
                }
            }
        }
    }

    public void layout() {
        Drawable bg = this.style.background;
        Drawable hScrollKnob = this.style.hScrollKnob;
        Drawable vScrollKnob = this.style.vScrollKnob;
        float bgLeftWidth = 0.0f;
        float bgRightWidth = 0.0f;
        float bgTopHeight = 0.0f;
        float bgBottomHeight = 0.0f;
        if (bg != null) {
            bgLeftWidth = bg.getLeftWidth();
            bgRightWidth = bg.getRightWidth();
            bgTopHeight = bg.getTopHeight();
            bgBottomHeight = bg.getBottomHeight();
        }
        float width = getWidth();
        float height = getHeight();
        float scrollbarHeight = 0.0f;
        if (hScrollKnob != null) {
            scrollbarHeight = hScrollKnob.getMinHeight();
        }
        if (this.style.hScroll != null) {
            scrollbarHeight = Math.max(scrollbarHeight, this.style.hScroll.getMinHeight());
        }
        float scrollbarWidth = 0.0f;
        if (vScrollKnob != null) {
            scrollbarWidth = vScrollKnob.getMinWidth();
        }
        if (this.style.vScroll != null) {
            scrollbarWidth = Math.max(scrollbarWidth, this.style.vScroll.getMinWidth());
        }
        this.areaWidth = (width - bgLeftWidth) - bgRightWidth;
        this.areaHeight = (height - bgTopHeight) - bgBottomHeight;
        if (this.widget != null) {
            float widgetWidth;
            float widgetHeight;
            boolean z;
            boolean fade;
            Rectangle rectangle;
            float hScrollHeight;
            float boundsX;
            float boundsY;
            float vScrollWidth;
            float minWidth;
            if (this.widget instanceof Layout) {
                Layout layout = this.widget;
                widgetWidth = layout.getPrefWidth();
                widgetHeight = layout.getPrefHeight();
            } else {
                widgetWidth = this.widget.getWidth();
                widgetHeight = this.widget.getHeight();
            }
            if (!this.forceScrollX) {
                if (widgetWidth <= this.areaWidth || this.disableX) {
                    z = false;
                    this.scrollX = z;
                    if (!this.forceScrollY) {
                        if (widgetHeight <= this.areaHeight || this.disableY) {
                            z = false;
                            this.scrollY = z;
                            fade = this.fadeScrollBars;
                            if (!fade) {
                                if (this.scrollY) {
                                    this.areaWidth -= scrollbarWidth;
                                    if (!this.scrollX) {
                                        if (widgetWidth > this.areaWidth && !this.disableX) {
                                            this.scrollX = true;
                                        }
                                    }
                                }
                                if (this.scrollX) {
                                    this.areaHeight -= scrollbarHeight;
                                    if (!this.scrollY) {
                                        if (widgetHeight > this.areaHeight && !this.disableY) {
                                            this.scrollY = true;
                                            this.areaWidth -= scrollbarWidth;
                                        }
                                    }
                                }
                            }
                            this.widgetAreaBounds.set(bgLeftWidth, bgBottomHeight, this.areaWidth, this.areaHeight);
                            if (!fade) {
                                if (this.scrollX) {
                                    this.areaHeight -= scrollbarHeight;
                                }
                                if (this.scrollY) {
                                    this.areaWidth -= scrollbarWidth;
                                }
                            } else if (this.scrollbarsOnTop) {
                                if (this.scrollX) {
                                    if (this.hScrollOnBottom) {
                                        this.widgetAreaBounds.f76y = 0.0f;
                                    } else {
                                        rectangle = this.widgetAreaBounds;
                                        rectangle.f76y += scrollbarHeight;
                                    }
                                }
                                if (this.scrollY) {
                                    if (this.vScrollOnRight) {
                                        rectangle = this.widgetAreaBounds;
                                        rectangle.f75x += scrollbarWidth;
                                    } else {
                                        this.widgetAreaBounds.f75x = 0.0f;
                                    }
                                }
                            } else {
                                if (this.scrollX) {
                                    rectangle = this.widgetAreaBounds;
                                    rectangle.height += scrollbarHeight;
                                }
                                if (this.scrollY) {
                                    rectangle = this.widgetAreaBounds;
                                    rectangle.width += scrollbarWidth;
                                }
                            }
                            if (this.disableX) {
                                widgetWidth = Math.max(this.areaWidth, widgetWidth);
                            } else {
                                widgetWidth = width;
                            }
                            if (this.disableY) {
                                widgetHeight = Math.max(this.areaHeight, widgetHeight);
                            } else {
                                widgetHeight = height;
                            }
                            this.maxX = widgetWidth - this.areaWidth;
                            this.maxY = widgetHeight - this.areaHeight;
                            if (fade) {
                                if (this.scrollX) {
                                    this.maxY -= scrollbarHeight;
                                }
                                if (this.scrollY) {
                                    this.maxX -= scrollbarWidth;
                                }
                            }
                            scrollX(MathUtils.clamp(this.amountX, 0.0f, this.maxX));
                            scrollY(MathUtils.clamp(this.amountY, 0.0f, this.maxY));
                            if (this.scrollX) {
                                if (hScrollKnob == null) {
                                    if (this.style.hScroll == null) {
                                        hScrollHeight = this.style.hScroll.getMinHeight();
                                    } else {
                                        hScrollHeight = hScrollKnob.getMinHeight();
                                    }
                                    if (this.vScrollOnRight) {
                                        boundsX = bgLeftWidth + scrollbarWidth;
                                    } else {
                                        boundsX = bgLeftWidth;
                                    }
                                    if (this.hScrollOnBottom) {
                                        boundsY = (height - bgTopHeight) - hScrollHeight;
                                    } else {
                                        boundsY = bgBottomHeight;
                                    }
                                    this.hScrollBounds.set(boundsX, boundsY, this.areaWidth, hScrollHeight);
                                    rectangle = this.hKnobBounds;
                                    rectangle.width = Math.max(hScrollKnob.getMinWidth(), (float) ((int) ((this.hScrollBounds.width * this.areaWidth) / widgetWidth)));
                                    rectangle = this.hKnobBounds;
                                    rectangle.height = hScrollKnob.getMinHeight();
                                    rectangle = this.hKnobBounds;
                                    rectangle.f75x = this.hScrollBounds.f75x + ((float) ((int) ((this.hScrollBounds.width - this.hKnobBounds.width) * getScrollPercentX())));
                                    rectangle = this.hKnobBounds;
                                    rectangle.f76y = this.hScrollBounds.f76y;
                                } else {
                                    this.hScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                                    this.hKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                                }
                            }
                            if (this.scrollY) {
                                if (vScrollKnob == null) {
                                    if (this.style.vScroll == null) {
                                        vScrollWidth = this.style.vScroll.getMinWidth();
                                    } else {
                                        vScrollWidth = vScrollKnob.getMinWidth();
                                    }
                                    if (this.hScrollOnBottom) {
                                        boundsY = bgBottomHeight;
                                    } else {
                                        boundsY = (height - bgTopHeight) - this.areaHeight;
                                    }
                                    if (this.vScrollOnRight) {
                                        boundsX = bgLeftWidth;
                                    } else {
                                        boundsX = (width - bgRightWidth) - vScrollWidth;
                                    }
                                    this.vScrollBounds.set(boundsX, boundsY, vScrollWidth, this.areaHeight);
                                    rectangle = this.vKnobBounds;
                                    rectangle.width = vScrollKnob.getMinWidth();
                                    rectangle = this.vKnobBounds;
                                    rectangle.height = Math.max(vScrollKnob.getMinHeight(), (float) ((int) ((this.vScrollBounds.height * this.areaHeight) / widgetHeight)));
                                    if (this.vScrollOnRight) {
                                        this.vKnobBounds.f75x = bgLeftWidth;
                                    } else {
                                        minWidth = (width - bgRightWidth) - vScrollKnob.getMinWidth();
                                        this.vKnobBounds.f75x = r23;
                                    }
                                    rectangle = this.vKnobBounds;
                                    rectangle.f76y = this.vScrollBounds.f76y + ((float) ((int) ((this.vScrollBounds.height - this.vKnobBounds.height) * (TextTrackStyle.DEFAULT_FONT_SCALE - getScrollPercentY()))));
                                } else {
                                    this.vScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                                    this.vKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                                }
                            }
                            this.widget.setSize(widgetWidth, widgetHeight);
                            if (!(this.widget instanceof Layout)) {
                                ((Layout) this.widget).validate();
                            }
                        }
                    }
                    z = true;
                    this.scrollY = z;
                    fade = this.fadeScrollBars;
                    if (fade) {
                        if (this.scrollY) {
                            this.areaWidth -= scrollbarWidth;
                            if (this.scrollX) {
                                this.scrollX = true;
                            }
                        }
                        if (this.scrollX) {
                            this.areaHeight -= scrollbarHeight;
                            if (this.scrollY) {
                                this.scrollY = true;
                                this.areaWidth -= scrollbarWidth;
                            }
                        }
                    }
                    this.widgetAreaBounds.set(bgLeftWidth, bgBottomHeight, this.areaWidth, this.areaHeight);
                    if (!fade) {
                        if (this.scrollX) {
                            this.areaHeight -= scrollbarHeight;
                        }
                        if (this.scrollY) {
                            this.areaWidth -= scrollbarWidth;
                        }
                    } else if (this.scrollbarsOnTop) {
                        if (this.scrollX) {
                            if (this.hScrollOnBottom) {
                                this.widgetAreaBounds.f76y = 0.0f;
                            } else {
                                rectangle = this.widgetAreaBounds;
                                rectangle.f76y += scrollbarHeight;
                            }
                        }
                        if (this.scrollY) {
                            if (this.vScrollOnRight) {
                                rectangle = this.widgetAreaBounds;
                                rectangle.f75x += scrollbarWidth;
                            } else {
                                this.widgetAreaBounds.f75x = 0.0f;
                            }
                        }
                    } else {
                        if (this.scrollX) {
                            rectangle = this.widgetAreaBounds;
                            rectangle.height += scrollbarHeight;
                        }
                        if (this.scrollY) {
                            rectangle = this.widgetAreaBounds;
                            rectangle.width += scrollbarWidth;
                        }
                    }
                    if (this.disableX) {
                        widgetWidth = Math.max(this.areaWidth, widgetWidth);
                    } else {
                        widgetWidth = width;
                    }
                    if (this.disableY) {
                        widgetHeight = Math.max(this.areaHeight, widgetHeight);
                    } else {
                        widgetHeight = height;
                    }
                    this.maxX = widgetWidth - this.areaWidth;
                    this.maxY = widgetHeight - this.areaHeight;
                    if (fade) {
                        if (this.scrollX) {
                            this.maxY -= scrollbarHeight;
                        }
                        if (this.scrollY) {
                            this.maxX -= scrollbarWidth;
                        }
                    }
                    scrollX(MathUtils.clamp(this.amountX, 0.0f, this.maxX));
                    scrollY(MathUtils.clamp(this.amountY, 0.0f, this.maxY));
                    if (this.scrollX) {
                        if (hScrollKnob == null) {
                            this.hScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                            this.hKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                        } else {
                            if (this.style.hScroll == null) {
                                hScrollHeight = hScrollKnob.getMinHeight();
                            } else {
                                hScrollHeight = this.style.hScroll.getMinHeight();
                            }
                            if (this.vScrollOnRight) {
                                boundsX = bgLeftWidth + scrollbarWidth;
                            } else {
                                boundsX = bgLeftWidth;
                            }
                            if (this.hScrollOnBottom) {
                                boundsY = (height - bgTopHeight) - hScrollHeight;
                            } else {
                                boundsY = bgBottomHeight;
                            }
                            this.hScrollBounds.set(boundsX, boundsY, this.areaWidth, hScrollHeight);
                            rectangle = this.hKnobBounds;
                            rectangle.width = Math.max(hScrollKnob.getMinWidth(), (float) ((int) ((this.hScrollBounds.width * this.areaWidth) / widgetWidth)));
                            rectangle = this.hKnobBounds;
                            rectangle.height = hScrollKnob.getMinHeight();
                            rectangle = this.hKnobBounds;
                            rectangle.f75x = this.hScrollBounds.f75x + ((float) ((int) ((this.hScrollBounds.width - this.hKnobBounds.width) * getScrollPercentX())));
                            rectangle = this.hKnobBounds;
                            rectangle.f76y = this.hScrollBounds.f76y;
                        }
                    }
                    if (this.scrollY) {
                        if (vScrollKnob == null) {
                            this.vScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                            this.vKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                        } else {
                            if (this.style.vScroll == null) {
                                vScrollWidth = vScrollKnob.getMinWidth();
                            } else {
                                vScrollWidth = this.style.vScroll.getMinWidth();
                            }
                            if (this.hScrollOnBottom) {
                                boundsY = bgBottomHeight;
                            } else {
                                boundsY = (height - bgTopHeight) - this.areaHeight;
                            }
                            if (this.vScrollOnRight) {
                                boundsX = bgLeftWidth;
                            } else {
                                boundsX = (width - bgRightWidth) - vScrollWidth;
                            }
                            this.vScrollBounds.set(boundsX, boundsY, vScrollWidth, this.areaHeight);
                            rectangle = this.vKnobBounds;
                            rectangle.width = vScrollKnob.getMinWidth();
                            rectangle = this.vKnobBounds;
                            rectangle.height = Math.max(vScrollKnob.getMinHeight(), (float) ((int) ((this.vScrollBounds.height * this.areaHeight) / widgetHeight)));
                            if (this.vScrollOnRight) {
                                this.vKnobBounds.f75x = bgLeftWidth;
                            } else {
                                minWidth = (width - bgRightWidth) - vScrollKnob.getMinWidth();
                                this.vKnobBounds.f75x = r23;
                            }
                            rectangle = this.vKnobBounds;
                            rectangle.f76y = this.vScrollBounds.f76y + ((float) ((int) ((this.vScrollBounds.height - this.vKnobBounds.height) * (TextTrackStyle.DEFAULT_FONT_SCALE - getScrollPercentY()))));
                        }
                    }
                    this.widget.setSize(widgetWidth, widgetHeight);
                    if (!(this.widget instanceof Layout)) {
                        ((Layout) this.widget).validate();
                    }
                }
            }
            z = true;
            this.scrollX = z;
            if (this.forceScrollY) {
                z = false;
                this.scrollY = z;
                fade = this.fadeScrollBars;
                if (fade) {
                    if (this.scrollY) {
                        this.areaWidth -= scrollbarWidth;
                        if (this.scrollX) {
                            this.scrollX = true;
                        }
                    }
                    if (this.scrollX) {
                        this.areaHeight -= scrollbarHeight;
                        if (this.scrollY) {
                            this.scrollY = true;
                            this.areaWidth -= scrollbarWidth;
                        }
                    }
                }
                this.widgetAreaBounds.set(bgLeftWidth, bgBottomHeight, this.areaWidth, this.areaHeight);
                if (!fade) {
                    if (this.scrollX) {
                        this.areaHeight -= scrollbarHeight;
                    }
                    if (this.scrollY) {
                        this.areaWidth -= scrollbarWidth;
                    }
                } else if (this.scrollbarsOnTop) {
                    if (this.scrollX) {
                        rectangle = this.widgetAreaBounds;
                        rectangle.height += scrollbarHeight;
                    }
                    if (this.scrollY) {
                        rectangle = this.widgetAreaBounds;
                        rectangle.width += scrollbarWidth;
                    }
                } else {
                    if (this.scrollX) {
                        if (this.hScrollOnBottom) {
                            rectangle = this.widgetAreaBounds;
                            rectangle.f76y += scrollbarHeight;
                        } else {
                            this.widgetAreaBounds.f76y = 0.0f;
                        }
                    }
                    if (this.scrollY) {
                        if (this.vScrollOnRight) {
                            this.widgetAreaBounds.f75x = 0.0f;
                        } else {
                            rectangle = this.widgetAreaBounds;
                            rectangle.f75x += scrollbarWidth;
                        }
                    }
                }
                if (this.disableX) {
                    widgetWidth = width;
                } else {
                    widgetWidth = Math.max(this.areaWidth, widgetWidth);
                }
                if (this.disableY) {
                    widgetHeight = height;
                } else {
                    widgetHeight = Math.max(this.areaHeight, widgetHeight);
                }
                this.maxX = widgetWidth - this.areaWidth;
                this.maxY = widgetHeight - this.areaHeight;
                if (fade) {
                    if (this.scrollX) {
                        this.maxY -= scrollbarHeight;
                    }
                    if (this.scrollY) {
                        this.maxX -= scrollbarWidth;
                    }
                }
                scrollX(MathUtils.clamp(this.amountX, 0.0f, this.maxX));
                scrollY(MathUtils.clamp(this.amountY, 0.0f, this.maxY));
                if (this.scrollX) {
                    if (hScrollKnob == null) {
                        if (this.style.hScroll == null) {
                            hScrollHeight = this.style.hScroll.getMinHeight();
                        } else {
                            hScrollHeight = hScrollKnob.getMinHeight();
                        }
                        if (this.vScrollOnRight) {
                            boundsX = bgLeftWidth;
                        } else {
                            boundsX = bgLeftWidth + scrollbarWidth;
                        }
                        if (this.hScrollOnBottom) {
                            boundsY = bgBottomHeight;
                        } else {
                            boundsY = (height - bgTopHeight) - hScrollHeight;
                        }
                        this.hScrollBounds.set(boundsX, boundsY, this.areaWidth, hScrollHeight);
                        rectangle = this.hKnobBounds;
                        rectangle.width = Math.max(hScrollKnob.getMinWidth(), (float) ((int) ((this.hScrollBounds.width * this.areaWidth) / widgetWidth)));
                        rectangle = this.hKnobBounds;
                        rectangle.height = hScrollKnob.getMinHeight();
                        rectangle = this.hKnobBounds;
                        rectangle.f75x = this.hScrollBounds.f75x + ((float) ((int) ((this.hScrollBounds.width - this.hKnobBounds.width) * getScrollPercentX())));
                        rectangle = this.hKnobBounds;
                        rectangle.f76y = this.hScrollBounds.f76y;
                    } else {
                        this.hScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                        this.hKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                    }
                }
                if (this.scrollY) {
                    if (vScrollKnob == null) {
                        if (this.style.vScroll == null) {
                            vScrollWidth = this.style.vScroll.getMinWidth();
                        } else {
                            vScrollWidth = vScrollKnob.getMinWidth();
                        }
                        if (this.hScrollOnBottom) {
                            boundsY = (height - bgTopHeight) - this.areaHeight;
                        } else {
                            boundsY = bgBottomHeight;
                        }
                        if (this.vScrollOnRight) {
                            boundsX = (width - bgRightWidth) - vScrollWidth;
                        } else {
                            boundsX = bgLeftWidth;
                        }
                        this.vScrollBounds.set(boundsX, boundsY, vScrollWidth, this.areaHeight);
                        rectangle = this.vKnobBounds;
                        rectangle.width = vScrollKnob.getMinWidth();
                        rectangle = this.vKnobBounds;
                        rectangle.height = Math.max(vScrollKnob.getMinHeight(), (float) ((int) ((this.vScrollBounds.height * this.areaHeight) / widgetHeight)));
                        if (this.vScrollOnRight) {
                            minWidth = (width - bgRightWidth) - vScrollKnob.getMinWidth();
                            this.vKnobBounds.f75x = r23;
                        } else {
                            this.vKnobBounds.f75x = bgLeftWidth;
                        }
                        rectangle = this.vKnobBounds;
                        rectangle.f76y = this.vScrollBounds.f76y + ((float) ((int) ((this.vScrollBounds.height - this.vKnobBounds.height) * (TextTrackStyle.DEFAULT_FONT_SCALE - getScrollPercentY()))));
                    } else {
                        this.vScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                        this.vKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                    }
                }
                this.widget.setSize(widgetWidth, widgetHeight);
                if (!(this.widget instanceof Layout)) {
                    ((Layout) this.widget).validate();
                }
            }
            z = true;
            this.scrollY = z;
            fade = this.fadeScrollBars;
            if (fade) {
                if (this.scrollY) {
                    this.areaWidth -= scrollbarWidth;
                    if (this.scrollX) {
                        this.scrollX = true;
                    }
                }
                if (this.scrollX) {
                    this.areaHeight -= scrollbarHeight;
                    if (this.scrollY) {
                        this.scrollY = true;
                        this.areaWidth -= scrollbarWidth;
                    }
                }
            }
            this.widgetAreaBounds.set(bgLeftWidth, bgBottomHeight, this.areaWidth, this.areaHeight);
            if (!fade) {
                if (this.scrollX) {
                    this.areaHeight -= scrollbarHeight;
                }
                if (this.scrollY) {
                    this.areaWidth -= scrollbarWidth;
                }
            } else if (this.scrollbarsOnTop) {
                if (this.scrollX) {
                    if (this.hScrollOnBottom) {
                        this.widgetAreaBounds.f76y = 0.0f;
                    } else {
                        rectangle = this.widgetAreaBounds;
                        rectangle.f76y += scrollbarHeight;
                    }
                }
                if (this.scrollY) {
                    if (this.vScrollOnRight) {
                        rectangle = this.widgetAreaBounds;
                        rectangle.f75x += scrollbarWidth;
                    } else {
                        this.widgetAreaBounds.f75x = 0.0f;
                    }
                }
            } else {
                if (this.scrollX) {
                    rectangle = this.widgetAreaBounds;
                    rectangle.height += scrollbarHeight;
                }
                if (this.scrollY) {
                    rectangle = this.widgetAreaBounds;
                    rectangle.width += scrollbarWidth;
                }
            }
            if (this.disableX) {
                widgetWidth = Math.max(this.areaWidth, widgetWidth);
            } else {
                widgetWidth = width;
            }
            if (this.disableY) {
                widgetHeight = Math.max(this.areaHeight, widgetHeight);
            } else {
                widgetHeight = height;
            }
            this.maxX = widgetWidth - this.areaWidth;
            this.maxY = widgetHeight - this.areaHeight;
            if (fade) {
                if (this.scrollX) {
                    this.maxY -= scrollbarHeight;
                }
                if (this.scrollY) {
                    this.maxX -= scrollbarWidth;
                }
            }
            scrollX(MathUtils.clamp(this.amountX, 0.0f, this.maxX));
            scrollY(MathUtils.clamp(this.amountY, 0.0f, this.maxY));
            if (this.scrollX) {
                if (hScrollKnob == null) {
                    this.hScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                    this.hKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                } else {
                    if (this.style.hScroll == null) {
                        hScrollHeight = hScrollKnob.getMinHeight();
                    } else {
                        hScrollHeight = this.style.hScroll.getMinHeight();
                    }
                    if (this.vScrollOnRight) {
                        boundsX = bgLeftWidth + scrollbarWidth;
                    } else {
                        boundsX = bgLeftWidth;
                    }
                    if (this.hScrollOnBottom) {
                        boundsY = (height - bgTopHeight) - hScrollHeight;
                    } else {
                        boundsY = bgBottomHeight;
                    }
                    this.hScrollBounds.set(boundsX, boundsY, this.areaWidth, hScrollHeight);
                    rectangle = this.hKnobBounds;
                    rectangle.width = Math.max(hScrollKnob.getMinWidth(), (float) ((int) ((this.hScrollBounds.width * this.areaWidth) / widgetWidth)));
                    rectangle = this.hKnobBounds;
                    rectangle.height = hScrollKnob.getMinHeight();
                    rectangle = this.hKnobBounds;
                    rectangle.f75x = this.hScrollBounds.f75x + ((float) ((int) ((this.hScrollBounds.width - this.hKnobBounds.width) * getScrollPercentX())));
                    rectangle = this.hKnobBounds;
                    rectangle.f76y = this.hScrollBounds.f76y;
                }
            }
            if (this.scrollY) {
                if (vScrollKnob == null) {
                    this.vScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                    this.vKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                } else {
                    if (this.style.vScroll == null) {
                        vScrollWidth = vScrollKnob.getMinWidth();
                    } else {
                        vScrollWidth = this.style.vScroll.getMinWidth();
                    }
                    if (this.hScrollOnBottom) {
                        boundsY = bgBottomHeight;
                    } else {
                        boundsY = (height - bgTopHeight) - this.areaHeight;
                    }
                    if (this.vScrollOnRight) {
                        boundsX = bgLeftWidth;
                    } else {
                        boundsX = (width - bgRightWidth) - vScrollWidth;
                    }
                    this.vScrollBounds.set(boundsX, boundsY, vScrollWidth, this.areaHeight);
                    rectangle = this.vKnobBounds;
                    rectangle.width = vScrollKnob.getMinWidth();
                    rectangle = this.vKnobBounds;
                    rectangle.height = Math.max(vScrollKnob.getMinHeight(), (float) ((int) ((this.vScrollBounds.height * this.areaHeight) / widgetHeight)));
                    if (this.vScrollOnRight) {
                        this.vKnobBounds.f75x = bgLeftWidth;
                    } else {
                        minWidth = (width - bgRightWidth) - vScrollKnob.getMinWidth();
                        this.vKnobBounds.f75x = r23;
                    }
                    rectangle = this.vKnobBounds;
                    rectangle.f76y = this.vScrollBounds.f76y + ((float) ((int) ((this.vScrollBounds.height - this.vKnobBounds.height) * (TextTrackStyle.DEFAULT_FONT_SCALE - getScrollPercentY()))));
                }
            }
            this.widget.setSize(widgetWidth, widgetHeight);
            if (!(this.widget instanceof Layout)) {
                ((Layout) this.widget).validate();
            }
        }
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        if (this.widget != null) {
            validate();
            applyTransform(batch, computeTransform());
            if (this.scrollX) {
                this.hKnobBounds.f75x = this.hScrollBounds.f75x + ((float) ((int) ((this.hScrollBounds.width - this.hKnobBounds.width) * getScrollPercentX())));
            }
            if (this.scrollY) {
                this.vKnobBounds.f76y = this.vScrollBounds.f76y + ((float) ((int) ((this.vScrollBounds.height - this.vKnobBounds.height) * (TextTrackStyle.DEFAULT_FONT_SCALE - getScrollPercentY()))));
            }
            float y = this.widgetAreaBounds.f76y;
            if (this.scrollY) {
                y -= (float) ((int) (this.maxY - this.visualAmountY));
            } else {
                y -= (float) ((int) this.maxY);
            }
            if (!this.fadeScrollBars && this.scrollbarsOnTop && this.scrollX) {
                float scrollbarHeight = 0.0f;
                if (this.style.hScrollKnob != null) {
                    scrollbarHeight = this.style.hScrollKnob.getMinHeight();
                }
                if (this.style.hScroll != null) {
                    scrollbarHeight = Math.max(scrollbarHeight, this.style.hScroll.getMinHeight());
                }
                y += scrollbarHeight;
            }
            float x = this.widgetAreaBounds.f75x;
            if (this.scrollX) {
                x -= (float) ((int) this.visualAmountX);
            }
            this.widget.setPosition(x, y);
            if (this.widget instanceof Cullable) {
                this.widgetCullingArea.f75x = (-this.widget.getX()) + this.widgetAreaBounds.f75x;
                this.widgetCullingArea.f76y = (-this.widget.getY()) + this.widgetAreaBounds.f76y;
                this.widgetCullingArea.width = this.widgetAreaBounds.width;
                this.widgetCullingArea.height = this.widgetAreaBounds.height;
                ((Cullable) this.widget).setCullingArea(this.widgetCullingArea);
            }
            getStage().calculateScissors(this.widgetAreaBounds, this.scissorBounds);
            Color color = getColor();
            batch.setColor(color.f40r, color.f39g, color.f38b, color.f37a * parentAlpha);
            if (this.style.background != null) {
                this.style.background.draw(batch, 0.0f, 0.0f, getWidth(), getHeight());
            }
            batch.flush();
            if (ScissorStack.pushScissors(this.scissorBounds)) {
                drawChildren(batch, parentAlpha);
                ScissorStack.popScissors();
            }
            batch.setColor(color.f40r, color.f39g, color.f38b, (color.f37a * parentAlpha) * Interpolation.fade.apply(this.fadeAlpha / this.fadeAlphaSeconds));
            if (this.scrollX && this.scrollY && this.style.corner != null) {
                this.style.corner.draw(batch, this.hScrollBounds.width + this.hScrollBounds.f75x, this.hScrollBounds.f76y, this.vScrollBounds.width, this.vScrollBounds.f76y);
            }
            if (this.scrollX) {
                if (this.style.hScroll != null) {
                    this.style.hScroll.draw(batch, this.hScrollBounds.f75x, this.hScrollBounds.f76y, this.hScrollBounds.width, this.hScrollBounds.height);
                }
                if (this.style.hScrollKnob != null) {
                    this.style.hScrollKnob.draw(batch, this.hKnobBounds.f75x, this.hKnobBounds.f76y, this.hKnobBounds.width, this.hKnobBounds.height);
                }
            }
            if (this.scrollY) {
                if (this.style.vScroll != null) {
                    this.style.vScroll.draw(batch, this.vScrollBounds.f75x, this.vScrollBounds.f76y, this.vScrollBounds.width, this.vScrollBounds.height);
                }
                if (this.style.vScrollKnob != null) {
                    this.style.vScrollKnob.draw(batch, this.vKnobBounds.f75x, this.vKnobBounds.f76y, this.vKnobBounds.width, this.vKnobBounds.height);
                }
            }
            resetTransform(batch);
        }
    }

    public float getPrefWidth() {
        if (!(this.widget instanceof Layout)) {
            return 150.0f;
        }
        float width = ((Layout) this.widget).getPrefWidth();
        if (this.style.background != null) {
            return width + (this.style.background.getLeftWidth() + this.style.background.getRightWidth());
        }
        return width;
    }

    public float getPrefHeight() {
        if (!(this.widget instanceof Layout)) {
            return 150.0f;
        }
        float height = ((Layout) this.widget).getPrefHeight();
        if (this.style.background != null) {
            return height + (this.style.background.getTopHeight() + this.style.background.getBottomHeight());
        }
        return height;
    }

    public float getMinWidth() {
        return 0.0f;
    }

    public float getMinHeight() {
        return 0.0f;
    }

    public void setWidget(Actor widget) {
        if (widget == this) {
            throw new IllegalArgumentException("widget cannot be same object");
        }
        if (this.widget != null) {
            super.removeActor(this.widget);
        }
        this.widget = widget;
        if (widget != null) {
            super.addActor(widget);
        }
    }

    public Actor getWidget() {
        return this.widget;
    }

    public void addActor(Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorAt(int index, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorBefore(Actor actorBefore, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorAfter(Actor actorAfter, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public boolean removeActor(Actor actor) {
        if (actor != this.widget) {
            return false;
        }
        setWidget(null);
        return true;
    }

    public Actor hit(float x, float y, boolean touchable) {
        if (x < 0.0f || x >= getWidth() || y < 0.0f || y >= getHeight()) {
            return null;
        }
        if (this.scrollX && this.hScrollBounds.contains(x, y)) {
            return this;
        }
        return (this.scrollY && this.vScrollBounds.contains(x, y)) ? this : super.hit(x, y, touchable);
    }

    protected void scrollX(float pixelsX) {
        this.amountX = pixelsX;
    }

    protected void scrollY(float pixelsY) {
        this.amountY = pixelsY;
    }

    protected void visualScrollX(float pixelsX) {
        this.visualAmountX = pixelsX;
    }

    protected void visualScrollY(float pixelsY) {
        this.visualAmountY = pixelsY;
    }

    protected float getMouseWheelX() {
        return Math.max(this.areaWidth * 0.9f, this.maxX * 0.1f) / 4.0f;
    }

    protected float getMouseWheelY() {
        return Math.max(this.areaHeight * 0.9f, this.maxY * 0.1f) / 4.0f;
    }

    public void setScrollX(float pixels) {
        scrollX(MathUtils.clamp(pixels, 0.0f, this.maxX));
    }

    public float getScrollX() {
        return this.amountX;
    }

    public void setScrollY(float pixels) {
        scrollY(MathUtils.clamp(pixels, 0.0f, this.maxY));
    }

    public float getScrollY() {
        return this.amountY;
    }

    public void updateVisualScroll() {
        this.visualAmountX = this.amountX;
        this.visualAmountY = this.amountY;
    }

    public float getVisualScrollX() {
        return !this.scrollX ? 0.0f : this.visualAmountX;
    }

    public float getVisualScrollY() {
        return !this.scrollY ? 0.0f : this.visualAmountY;
    }

    public float getScrollPercentX() {
        return MathUtils.clamp(this.amountX / this.maxX, 0.0f, (float) TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public void setScrollPercentX(float percentX) {
        scrollX(this.maxX * MathUtils.clamp(percentX, 0.0f, (float) TextTrackStyle.DEFAULT_FONT_SCALE));
    }

    public float getScrollPercentY() {
        return MathUtils.clamp(this.amountY / this.maxY, 0.0f, (float) TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public void setScrollPercentY(float percentY) {
        scrollY(this.maxY * MathUtils.clamp(percentY, 0.0f, (float) TextTrackStyle.DEFAULT_FONT_SCALE));
    }

    public void setFlickScroll(boolean flickScroll) {
        if (this.flickScroll != flickScroll) {
            this.flickScroll = flickScroll;
            if (flickScroll) {
                addListener(this.flickScrollListener);
            } else {
                removeListener(this.flickScrollListener);
            }
            invalidate();
        }
    }

    public void scrollTo(float x, float y, float width, float height) {
        float amountX = this.amountX;
        if (x + width > this.areaWidth + amountX) {
            amountX = (x + width) - this.areaWidth;
        }
        if (x < amountX) {
            amountX = x;
        }
        scrollX(MathUtils.clamp(amountX, 0.0f, this.maxX));
        float amountY = this.amountY;
        if (amountY > ((this.maxY - y) - height) + this.areaHeight) {
            amountY = ((this.maxY - y) - height) + this.areaHeight;
        }
        if (amountY < this.maxY - y) {
            amountY = this.maxY - y;
        }
        scrollY(MathUtils.clamp(amountY, 0.0f, this.maxY));
    }

    public void scrollToCenter(float x, float y, float width, float height) {
        float amountX = this.amountX;
        if (x + width > this.areaWidth + amountX) {
            amountX = (x + width) - this.areaWidth;
        }
        if (x < amountX) {
            amountX = x;
        }
        scrollX(MathUtils.clamp(amountX, 0.0f, this.maxX));
        float amountY = this.amountY;
        float centerY = ((this.maxY - y) + (this.areaHeight / 2.0f)) - (height / 2.0f);
        if (amountY < centerY - (this.areaHeight / 4.0f) || amountY > (this.areaHeight / 4.0f) + centerY) {
            amountY = centerY;
        }
        scrollY(MathUtils.clamp(amountY, 0.0f, this.maxY));
    }

    public float getMaxX() {
        return this.maxX;
    }

    public float getMaxY() {
        return this.maxY;
    }

    public float getScrollBarHeight() {
        return (this.style.hScrollKnob == null || !this.scrollX) ? 0.0f : this.style.hScrollKnob.getMinHeight();
    }

    public float getScrollBarWidth() {
        return (this.style.vScrollKnob == null || !this.scrollY) ? 0.0f : this.style.vScrollKnob.getMinWidth();
    }

    public boolean isScrollX() {
        return this.scrollX;
    }

    public boolean isScrollY() {
        return this.scrollY;
    }

    public void setScrollingDisabled(boolean x, boolean y) {
        this.disableX = x;
        this.disableY = y;
    }

    public boolean isDragging() {
        return this.draggingPointer != -1;
    }

    public boolean isPanning() {
        return this.flickScrollListener.getGestureDetector().isPanning();
    }

    public boolean isFlinging() {
        return this.flingTimer > 0.0f;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityX() {
        if (this.flingTimer <= 0.0f) {
            return 0.0f;
        }
        float alpha = this.flingTimer / this.flingTime;
        alpha *= alpha * alpha;
        return ((this.velocityX * alpha) * alpha) * alpha;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getVelocityY() {
        return this.velocityY;
    }

    public void setOverscroll(boolean overscrollX, boolean overscrollY) {
        this.overscrollX = overscrollX;
        this.overscrollY = overscrollY;
    }

    public void setupOverscroll(float distance, float speedMin, float speedMax) {
        this.overscrollDistance = distance;
        this.overscrollSpeedMin = speedMin;
        this.overscrollSpeedMax = speedMax;
    }

    public void setForceScroll(boolean x, boolean y) {
        this.forceScrollX = x;
        this.forceScrollY = y;
    }

    public boolean isForceScrollX() {
        return this.forceScrollX;
    }

    public boolean isForceScrollY() {
        return this.forceScrollY;
    }

    public void setFlingTime(float flingTime) {
        this.flingTime = flingTime;
    }

    public void setClamp(boolean clamp) {
        this.clamp = clamp;
    }

    public void setScrollBarPositions(boolean bottom, boolean right) {
        this.hScrollOnBottom = bottom;
        this.vScrollOnRight = right;
    }

    public void setFadeScrollBars(boolean fadeScrollBars) {
        if (this.fadeScrollBars != fadeScrollBars) {
            this.fadeScrollBars = fadeScrollBars;
            if (!fadeScrollBars) {
                this.fadeAlpha = this.fadeAlphaSeconds;
            }
            invalidate();
        }
    }

    public void setupFadeScrollBars(float fadeAlphaSeconds, float fadeDelaySeconds) {
        this.fadeAlphaSeconds = fadeAlphaSeconds;
        this.fadeDelaySeconds = fadeDelaySeconds;
    }

    public void setSmoothScrolling(boolean smoothScrolling) {
        this.smoothScrolling = smoothScrolling;
    }

    public void setScrollbarsOnTop(boolean scrollbarsOnTop) {
        this.scrollbarsOnTop = scrollbarsOnTop;
        invalidate();
    }

    public void setCancelTouchFocus(boolean cancelTouchFocus) {
        this.cancelTouchFocus = cancelTouchFocus;
    }
}
