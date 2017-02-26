package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Pools;
import com.google.android.gms.cast.TextTrackStyle;

public class Actor {
    private final Array<Action> actions;
    private final DelayedRemovalArray<EventListener> captureListeners;
    final Color color;
    float height;
    private final DelayedRemovalArray<EventListener> listeners;
    private String name;
    float originX;
    float originY;
    private Group parent;
    float rotation;
    float scaleX;
    float scaleY;
    private Stage stage;
    private Touchable touchable;
    private boolean visible;
    float width;
    float f83x;
    float f84y;

    public Actor() {
        this.listeners = new DelayedRemovalArray(0);
        this.captureListeners = new DelayedRemovalArray(0);
        this.actions = new Array(0);
        this.touchable = Touchable.enabled;
        this.visible = true;
        this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.color = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
    }

    public void act(float delta) {
        int i = 0;
        while (i < this.actions.size) {
            Action action = (Action) this.actions.get(i);
            if (action.act(delta) && i < this.actions.size) {
                this.actions.removeIndex(i);
                action.setActor(null);
                i--;
            }
            i++;
        }
    }

    public boolean fire(Event event) {
        if (event.getStage() == null) {
            event.setStage(getStage());
        }
        event.setTarget(this);
        Array<Group> ancestors = (Array) Pools.obtain(Array.class);
        for (Group parent = getParent(); parent != null; parent = parent.getParent()) {
            ancestors.add(parent);
        }
        try {
            int i;
            boolean isCancelled;
            for (i = ancestors.size - 1; i >= 0; i--) {
                ((Group) ancestors.get(i)).notify(event, true);
                if (event.isStopped()) {
                    isCancelled = event.isCancelled();
                    break;
                }
            }
            notify(event, true);
            if (event.isStopped()) {
                isCancelled = event.isCancelled();
                ancestors.clear();
                Pools.free(ancestors);
            } else {
                notify(event, false);
                if (!event.getBubbles()) {
                    isCancelled = event.isCancelled();
                    ancestors.clear();
                    Pools.free(ancestors);
                } else if (event.isStopped()) {
                    isCancelled = event.isCancelled();
                    ancestors.clear();
                    Pools.free(ancestors);
                } else {
                    int n = ancestors.size;
                    for (i = 0; i < n; i++) {
                        ((Group) ancestors.get(i)).notify(event, false);
                        if (event.isStopped()) {
                            isCancelled = event.isCancelled();
                            ancestors.clear();
                            Pools.free(ancestors);
                            break;
                        }
                    }
                    isCancelled = event.isCancelled();
                    ancestors.clear();
                    Pools.free(ancestors);
                }
            }
            return isCancelled;
        } finally {
            ancestors.clear();
            Pools.free(ancestors);
        }
    }

    public boolean notify(Event event, boolean capture) {
        if (event.getTarget() == null) {
            throw new IllegalArgumentException("The event target cannot be null.");
        }
        DelayedRemovalArray<EventListener> listeners = capture ? this.captureListeners : this.listeners;
        if (listeners.size == 0) {
            return event.isCancelled();
        }
        event.setListenerActor(this);
        event.setCapture(capture);
        if (event.getStage() == null) {
            event.setStage(this.stage);
        }
        listeners.begin();
        int n = listeners.size;
        for (int i = 0; i < n; i++) {
            EventListener listener = (EventListener) listeners.get(i);
            if (listener.handle(event)) {
                event.handle();
                if (event instanceof InputEvent) {
                    InputEvent inputEvent = (InputEvent) event;
                    if (inputEvent.getType() == Type.touchDown) {
                        event.getStage().addTouchFocus(listener, this, inputEvent.getTarget(), inputEvent.getPointer(), inputEvent.getButton());
                    }
                }
            }
        }
        listeners.end();
        return event.isCancelled();
    }

    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && this.touchable != Touchable.enabled) {
            return null;
        }
        if (x < 0.0f || x >= this.width || y < 0.0f || y >= this.height) {
            this = null;
        }
        return this;
    }

    public boolean remove() {
        if (this.parent != null) {
            return this.parent.removeActor(this);
        }
        return false;
    }

    public boolean addListener(EventListener listener) {
        if (this.listeners.contains(listener, true)) {
            return false;
        }
        this.listeners.add(listener);
        return true;
    }

    public boolean removeListener(EventListener listener) {
        return this.listeners.removeValue(listener, true);
    }

    public Array<EventListener> getListeners() {
        return this.listeners;
    }

    public boolean addCaptureListener(EventListener listener) {
        if (!this.captureListeners.contains(listener, true)) {
            this.captureListeners.add(listener);
        }
        return true;
    }

    public boolean removeCaptureListener(EventListener listener) {
        return this.captureListeners.removeValue(listener, true);
    }

    public Array<EventListener> getCaptureListeners() {
        return this.captureListeners;
    }

    public void addAction(Action action) {
        action.setActor(this);
        this.actions.add(action);
    }

    public void removeAction(Action action) {
        if (this.actions.removeValue(action, true)) {
            action.setActor(null);
        }
    }

    public Array<Action> getActions() {
        return this.actions;
    }

    public void clearActions() {
        for (int i = this.actions.size - 1; i >= 0; i--) {
            ((Action) this.actions.get(i)).setActor(null);
        }
        this.actions.clear();
    }

    public void clearListeners() {
        this.listeners.clear();
        this.captureListeners.clear();
    }

    public void clear() {
        clearActions();
        clearListeners();
    }

    public Stage getStage() {
        return this.stage;
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isDescendantOf(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        for (Actor parent = this; parent != null; parent = parent.parent) {
            if (parent == actor) {
                return true;
            }
        }
        return false;
    }

    public boolean isAscendantOf(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        while (actor != null) {
            if (actor == this) {
                return true;
            }
            actor = actor.parent;
        }
        return false;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public Group getParent() {
        return this.parent;
    }

    protected void setParent(Group parent) {
        this.parent = parent;
    }

    public Touchable getTouchable() {
        return this.touchable;
    }

    public void setTouchable(Touchable touchable) {
        this.touchable = touchable;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getX() {
        return this.f83x;
    }

    public void setX(float x) {
        this.f83x = x;
    }

    public float getY() {
        return this.f84y;
    }

    public void setY(float y) {
        this.f84y = y;
    }

    public void setPosition(float x, float y) {
        this.f83x = x;
        this.f84y = y;
    }

    public void translate(float x, float y) {
        this.f83x += x;
        this.f84y += y;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        float oldWidth = this.width;
        this.width = width;
        if (width != oldWidth) {
            sizeChanged();
        }
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        float oldHeight = this.height;
        this.height = height;
        if (height != oldHeight) {
            sizeChanged();
        }
    }

    public float getTop() {
        return this.f84y + this.height;
    }

    public float getRight() {
        return this.f83x + this.width;
    }

    protected void sizeChanged() {
    }

    public void setSize(float width, float height) {
        float oldWidth = this.width;
        float oldHeight = this.height;
        this.width = width;
        this.height = height;
        if (width != oldWidth || height != oldHeight) {
            sizeChanged();
        }
    }

    public void size(float size) {
        this.width += size;
        this.height += size;
        sizeChanged();
    }

    public void size(float width, float height) {
        this.width += width;
        this.height += height;
        sizeChanged();
    }

    public void setBounds(float x, float y, float width, float height) {
        float oldWidth = this.width;
        float oldHeight = this.height;
        this.f83x = x;
        this.f84y = y;
        this.width = width;
        this.height = height;
        if (width != oldWidth || height != oldHeight) {
            sizeChanged();
        }
    }

    public float getOriginX() {
        return this.originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return this.originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void setScale(float scale) {
        this.scaleX = scale;
        this.scaleY = scale;
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public void scale(float scale) {
        this.scaleX += scale;
        this.scaleY += scale;
    }

    public void scale(float scaleX, float scaleY) {
        this.scaleX += scaleX;
        this.scaleY += scaleY;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setRotation(float degrees) {
        this.rotation = degrees;
    }

    public void rotate(float amountInDegrees) {
        this.rotation += amountInDegrees;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public Color getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void toFront() {
        setZIndex(Integer.MAX_VALUE);
    }

    public void toBack() {
        setZIndex(0);
    }

    public void setZIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("ZIndex cannot be < 0.");
        }
        Group parent = this.parent;
        if (parent != null) {
            Array<Actor> children = parent.getChildren();
            if (children.size != 1 && children.removeValue(this, true)) {
                if (index >= children.size) {
                    children.add(this);
                } else {
                    children.insert(index, this);
                }
            }
        }
    }

    public int getZIndex() {
        Group parent = this.parent;
        if (parent == null) {
            return -1;
        }
        return parent.getChildren().indexOf(this, true);
    }

    public boolean clipBegin() {
        return clipBegin(this.f83x, this.f84y, this.width, this.height);
    }

    public boolean clipBegin(float x, float y, float width, float height) {
        Rectangle tableBounds = Rectangle.tmp;
        tableBounds.f75x = x;
        tableBounds.f76y = y;
        tableBounds.width = width;
        tableBounds.height = height;
        Rectangle scissorBounds = (Rectangle) Pools.obtain(Rectangle.class);
        this.stage.calculateScissors(tableBounds, scissorBounds);
        if (ScissorStack.pushScissors(scissorBounds)) {
            return true;
        }
        Pools.free(scissorBounds);
        return false;
    }

    public void clipEnd() {
        Pools.free(ScissorStack.popScissors());
    }

    public Vector2 screenToLocalCoordinates(Vector2 screenCoords) {
        Stage stage = this.stage;
        return stage == null ? screenCoords : stageToLocalCoordinates(stage.screenToStageCoordinates(screenCoords));
    }

    public Vector2 stageToLocalCoordinates(Vector2 stageCoords) {
        if (this.parent != null) {
            this.parent.stageToLocalCoordinates(stageCoords);
            parentToLocalCoordinates(stageCoords);
        }
        return stageCoords;
    }

    public Vector2 localToStageCoordinates(Vector2 localCoords) {
        return localToAscendantCoordinates(null, localCoords);
    }

    public Vector2 localToParentCoordinates(Vector2 localCoords) {
        float rotation = -this.rotation;
        float scaleX = this.scaleX;
        float scaleY = this.scaleY;
        float x = this.f83x;
        float y = this.f84y;
        float originX;
        float originY;
        if (rotation != 0.0f) {
            float cos = (float) Math.cos((double) (MathUtils.degreesToRadians * rotation));
            float sin = (float) Math.sin((double) (MathUtils.degreesToRadians * rotation));
            originX = this.originX;
            originY = this.originY;
            float tox = localCoords.f100x - originX;
            float toy = localCoords.f101y - originY;
            localCoords.f100x = ((((tox * cos) + (toy * sin)) * scaleX) + originX) + x;
            localCoords.f101y = (((((-sin) * tox) + (toy * cos)) * scaleY) + originY) + y;
        } else if (scaleX == TextTrackStyle.DEFAULT_FONT_SCALE && scaleY == TextTrackStyle.DEFAULT_FONT_SCALE) {
            localCoords.f100x += x;
            localCoords.f101y += y;
        } else {
            originX = this.originX;
            originY = this.originY;
            localCoords.f100x = (((localCoords.f100x - originX) * scaleX) + originX) + x;
            localCoords.f101y = (((localCoords.f101y - originY) * scaleY) + originY) + y;
        }
        return localCoords;
    }

    public Vector2 localToAscendantCoordinates(Actor ascendant, Vector2 localCoords) {
        Actor actor = this;
        while (actor.parent != null) {
            actor.localToParentCoordinates(localCoords);
            actor = actor.parent;
            if (actor == ascendant) {
                break;
            }
        }
        return localCoords;
    }

    public Vector2 parentToLocalCoordinates(Vector2 parentCoords) {
        float rotation = this.rotation;
        float scaleX = this.scaleX;
        float scaleY = this.scaleY;
        float childX = this.f83x;
        float childY = this.f84y;
        float originX;
        float originY;
        if (rotation != 0.0f) {
            float cos = (float) Math.cos((double) (MathUtils.degreesToRadians * rotation));
            float sin = (float) Math.sin((double) (MathUtils.degreesToRadians * rotation));
            originX = this.originX;
            originY = this.originY;
            float tox = (parentCoords.f100x - childX) - originX;
            float toy = (parentCoords.f101y - childY) - originY;
            parentCoords.f100x = (((tox * cos) + (toy * sin)) / scaleX) + originX;
            parentCoords.f101y = ((((-sin) * tox) + (toy * cos)) / scaleY) + originY;
        } else if (scaleX == TextTrackStyle.DEFAULT_FONT_SCALE && scaleY == TextTrackStyle.DEFAULT_FONT_SCALE) {
            parentCoords.f100x -= childX;
            parentCoords.f101y -= childY;
        } else {
            originX = this.originX;
            originY = this.originY;
            parentCoords.f100x = (((parentCoords.f100x - childX) - originX) / scaleX) + originX;
            parentCoords.f101y = (((parentCoords.f101y - childY) - originY) / scaleY) + originY;
        }
        return parentCoords;
    }

    public String toString() {
        String name = this.name;
        if (name != null) {
            return name;
        }
        name = getClass().getName();
        int dotIndex = name.lastIndexOf(46);
        if (dotIndex != -1) {
            return name.substring(dotIndex + 1);
        }
        return name;
    }
}
