package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.SnapshotArray;
import com.google.android.gms.cast.TextTrackStyle;

public class Stage extends InputAdapter implements Disposable {
    private static final Vector2 actorCoords;
    private static final Vector3 cameraCoords;
    private final SpriteBatch batch;
    private Camera camera;
    private float gutterHeight;
    private float gutterWidth;
    private float height;
    private Actor keyboardFocus;
    private Actor mouseOverActor;
    private int mouseScreenX;
    private int mouseScreenY;
    private final boolean ownsBatch;
    private final Actor[] pointerOverActors;
    private final int[] pointerScreenX;
    private final int[] pointerScreenY;
    private final boolean[] pointerTouched;
    private final Group root;
    private Actor scrollFocus;
    private final Vector2 stageCoords;
    private final SnapshotArray<TouchFocus> touchFocuses;
    private float viewportHeight;
    private float viewportWidth;
    private float viewportX;
    private float viewportY;
    private float width;

    public static final class TouchFocus implements Poolable {
        int button;
        EventListener listener;
        Actor listenerActor;
        int pointer;
        Actor target;

        public void reset() {
            this.listenerActor = null;
            this.listener = null;
        }
    }

    static {
        actorCoords = new Vector2();
        cameraCoords = new Vector3();
    }

    public Stage() {
        this((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight(), false, null);
    }

    public Stage(float width, float height) {
        this(width, height, false, null);
    }

    public Stage(float width, float height, boolean keepAspectRatio) {
        this(width, height, keepAspectRatio, null);
    }

    public Stage(float width, float height, boolean keepAspectRatio, SpriteBatch batch) {
        boolean z = true;
        this.stageCoords = new Vector2();
        this.pointerOverActors = new Actor[20];
        this.pointerTouched = new boolean[20];
        this.pointerScreenX = new int[20];
        this.pointerScreenY = new int[20];
        this.touchFocuses = new SnapshotArray(true, 4, TouchFocus.class);
        if (batch != null) {
            z = false;
        }
        this.ownsBatch = z;
        if (this.ownsBatch) {
            batch = new SpriteBatch();
        }
        this.batch = batch;
        this.width = width;
        this.height = height;
        this.root = new Group();
        this.root.setStage(this);
        this.camera = new OrthographicCamera();
        setViewport(width, height, keepAspectRatio);
    }

    public void setViewport(float width, float height) {
        setViewport(width, height, false, 0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
    }

    public void setViewport(float width, float height, boolean keepAspectRatio) {
        setViewport(width, height, keepAspectRatio, 0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
    }

    public void setViewport(float stageWidth, float stageHeight, boolean keepAspectRatio, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
        this.viewportX = viewportX;
        this.viewportY = viewportY;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        if (!keepAspectRatio) {
            this.width = stageWidth;
            this.height = stageHeight;
            this.gutterWidth = 0.0f;
            this.gutterHeight = 0.0f;
        } else if (viewportHeight / viewportWidth < stageHeight / stageWidth) {
            lengthen = (viewportWidth - (stageWidth * (viewportHeight / stageHeight))) * (stageHeight / viewportHeight);
            this.width = stageWidth + lengthen;
            this.height = stageHeight;
            this.gutterWidth = lengthen / 2.0f;
            this.gutterHeight = 0.0f;
        } else {
            lengthen = (viewportHeight - (stageHeight * (viewportWidth / stageWidth))) * (stageWidth / viewportWidth);
            this.height = stageHeight + lengthen;
            this.width = stageWidth;
            this.gutterWidth = 0.0f;
            this.gutterHeight = lengthen / 2.0f;
        }
        this.camera.position.set(this.width / 2.0f, this.height / 2.0f, 0.0f);
        this.camera.viewportWidth = this.width;
        this.camera.viewportHeight = this.height;
    }

    public void draw() {
        this.camera.update();
        if (this.root.isVisible()) {
            this.batch.setProjectionMatrix(this.camera.combined);
            this.batch.begin();
            this.root.draw(this.batch, TextTrackStyle.DEFAULT_FONT_SCALE);
            this.batch.end();
        }
    }

    public void act() {
        act(Math.min(Gdx.graphics.getDeltaTime(), 0.033333335f));
    }

    public void act(float delta) {
        int n = this.pointerOverActors.length;
        for (int pointer = 0; pointer < n; pointer++) {
            Actor overLast = this.pointerOverActors[pointer];
            if (this.pointerTouched[pointer]) {
                this.pointerOverActors[pointer] = fireEnterAndExit(overLast, this.pointerScreenX[pointer], this.pointerScreenY[pointer], pointer);
            } else if (overLast != null) {
                this.pointerOverActors[pointer] = null;
                screenToStageCoordinates(this.stageCoords.set((float) this.pointerScreenX[pointer], (float) this.pointerScreenY[pointer]));
                InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
                event.setType(Type.exit);
                event.setStage(this);
                event.setStageX(this.stageCoords.f100x);
                event.setStageY(this.stageCoords.f101y);
                event.setRelatedActor(overLast);
                event.setPointer(pointer);
                overLast.fire(event);
                Pools.free(event);
            }
        }
        ApplicationType type = Gdx.app.getType();
        if (type == ApplicationType.Desktop || type == ApplicationType.Applet || type == ApplicationType.WebGL) {
            this.mouseOverActor = fireEnterAndExit(this.mouseOverActor, this.mouseScreenX, this.mouseScreenY, -1);
        }
        this.root.act(delta);
    }

    private Actor fireEnterAndExit(Actor overLast, int screenX, int screenY, int pointer) {
        screenToStageCoordinates(this.stageCoords.set((float) screenX, (float) screenY));
        Actor over = hit(this.stageCoords.f100x, this.stageCoords.f101y, true);
        if (over == overLast) {
            return overLast;
        }
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setStageX(this.stageCoords.f100x);
        event.setStageY(this.stageCoords.f101y);
        event.setPointer(pointer);
        if (overLast != null) {
            event.setType(Type.exit);
            event.setRelatedActor(over);
            overLast.fire(event);
        }
        if (over != null) {
            event.setType(Type.enter);
            event.setRelatedActor(overLast);
            over.fire(event);
        }
        Pools.free(event);
        return over;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (((float) screenX) < this.viewportX || ((float) screenX) >= this.viewportX + this.viewportWidth || ((float) screenY) < this.viewportY || ((float) screenY) >= this.viewportY + this.viewportHeight) {
            return false;
        }
        this.pointerTouched[pointer] = true;
        this.pointerScreenX[pointer] = screenX;
        this.pointerScreenY[pointer] = screenY;
        screenToStageCoordinates(this.stageCoords.set((float) screenX, (float) screenY));
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setType(Type.touchDown);
        event.setStage(this);
        event.setStageX(this.stageCoords.f100x);
        event.setStageY(this.stageCoords.f101y);
        event.setPointer(pointer);
        event.setButton(button);
        Actor target = hit(this.stageCoords.f100x, this.stageCoords.f101y, true);
        if (target == null) {
            target = this.root;
        }
        target.fire(event);
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        this.pointerScreenX[pointer] = screenX;
        this.pointerScreenY[pointer] = screenY;
        if (this.touchFocuses.size == 0) {
            return false;
        }
        screenToStageCoordinates(this.stageCoords.set((float) screenX, (float) screenY));
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setType(Type.touchDragged);
        event.setStage(this);
        event.setStageX(this.stageCoords.f100x);
        event.setStageY(this.stageCoords.f101y);
        event.setPointer(pointer);
        SnapshotArray<TouchFocus> touchFocuses = this.touchFocuses;
        TouchFocus[] focuses = (TouchFocus[]) touchFocuses.begin();
        int n = touchFocuses.size;
        for (int i = 0; i < n; i++) {
            TouchFocus focus = focuses[i];
            if (focus.pointer == pointer) {
                event.setTarget(focus.target);
                event.setListenerActor(focus.listenerActor);
                if (focus.listener.handle(event)) {
                    event.handle();
                }
            }
        }
        touchFocuses.end();
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.pointerTouched[pointer] = false;
        this.pointerScreenX[pointer] = screenX;
        this.pointerScreenY[pointer] = screenY;
        if (this.touchFocuses.size == 0) {
            return false;
        }
        screenToStageCoordinates(this.stageCoords.set((float) screenX, (float) screenY));
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setType(Type.touchUp);
        event.setStage(this);
        event.setStageX(this.stageCoords.f100x);
        event.setStageY(this.stageCoords.f101y);
        event.setPointer(pointer);
        event.setButton(button);
        SnapshotArray<TouchFocus> touchFocuses = this.touchFocuses;
        TouchFocus[] focuses = (TouchFocus[]) touchFocuses.begin();
        int n = touchFocuses.size;
        for (int i = 0; i < n; i++) {
            TouchFocus focus = focuses[i];
            if (focus.pointer == pointer && focus.button == button && touchFocuses.removeValue(focus, true)) {
                event.setTarget(focus.target);
                event.setListenerActor(focus.listenerActor);
                if (focus.listener.handle(event)) {
                    event.handle();
                }
                Pools.free(focus);
            }
        }
        touchFocuses.end();
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        if (((float) screenX) < this.viewportX || ((float) screenX) >= this.viewportX + this.viewportWidth || ((float) screenY) < this.viewportY || ((float) screenY) >= this.viewportY + this.viewportHeight) {
            return false;
        }
        this.mouseScreenX = screenX;
        this.mouseScreenY = screenY;
        screenToStageCoordinates(this.stageCoords.set((float) screenX, (float) screenY));
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.mouseMoved);
        event.setStageX(this.stageCoords.f100x);
        event.setStageY(this.stageCoords.f101y);
        Actor target = hit(this.stageCoords.f100x, this.stageCoords.f101y, true);
        if (target == null) {
            target = this.root;
        }
        target.fire(event);
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean scrolled(int amount) {
        Actor target = this.scrollFocus == null ? this.root : this.scrollFocus;
        screenToStageCoordinates(this.stageCoords.set((float) this.mouseScreenX, (float) this.mouseScreenY));
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.scrolled);
        event.setScrollAmount(amount);
        event.setStageX(this.stageCoords.f100x);
        event.setStageY(this.stageCoords.f101y);
        target.fire(event);
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean keyDown(int keyCode) {
        Actor target = this.keyboardFocus == null ? this.root : this.keyboardFocus;
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.keyDown);
        event.setKeyCode(keyCode);
        target.fire(event);
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean keyUp(int keyCode) {
        Actor target = this.keyboardFocus == null ? this.root : this.keyboardFocus;
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.keyUp);
        event.setKeyCode(keyCode);
        target.fire(event);
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean keyTyped(char character) {
        Actor target = this.keyboardFocus == null ? this.root : this.keyboardFocus;
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.keyTyped);
        event.setCharacter(character);
        target.fire(event);
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public void addTouchFocus(EventListener listener, Actor listenerActor, Actor target, int pointer, int button) {
        TouchFocus focus = (TouchFocus) Pools.obtain(TouchFocus.class);
        focus.listenerActor = listenerActor;
        focus.target = target;
        focus.listener = listener;
        focus.pointer = pointer;
        focus.button = button;
        this.touchFocuses.add(focus);
    }

    public void removeTouchFocus(EventListener listener, Actor listenerActor, Actor target, int pointer, int button) {
        SnapshotArray<TouchFocus> touchFocuses = this.touchFocuses;
        for (int i = touchFocuses.size - 1; i >= 0; i--) {
            TouchFocus focus = (TouchFocus) touchFocuses.get(i);
            if (focus.listener == listener && focus.listenerActor == listenerActor && focus.target == target && focus.pointer == pointer && focus.button == button) {
                touchFocuses.removeIndex(i);
                Pools.free(focus);
            }
        }
    }

    public void cancelTouchFocus() {
        cancelTouchFocus(null, null);
    }

    public void cancelTouchFocus(EventListener listener, Actor actor) {
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.touchUp);
        event.setStageX(-2.14748365E9f);
        event.setStageY(-2.14748365E9f);
        SnapshotArray<TouchFocus> touchFocuses = this.touchFocuses;
        TouchFocus[] items = (TouchFocus[]) touchFocuses.begin();
        int n = touchFocuses.size;
        for (int i = 0; i < n; i++) {
            TouchFocus focus = items[i];
            if (!(focus.listener == listener && focus.listenerActor == actor) && touchFocuses.removeValue(focus, true)) {
                event.setTarget(focus.target);
                event.setListenerActor(focus.listenerActor);
                event.setPointer(focus.pointer);
                event.setButton(focus.button);
                focus.listener.handle(event);
            }
        }
        touchFocuses.end();
        Pools.free(event);
    }

    public void addActor(Actor actor) {
        this.root.addActor(actor);
    }

    public void addAction(Action action) {
        this.root.addAction(action);
    }

    public Array<Actor> getActors() {
        return this.root.getChildren();
    }

    public boolean addListener(EventListener listener) {
        return this.root.addListener(listener);
    }

    public boolean removeListener(EventListener listener) {
        return this.root.removeListener(listener);
    }

    public boolean addCaptureListener(EventListener listener) {
        return this.root.addCaptureListener(listener);
    }

    public boolean removeCaptureListener(EventListener listener) {
        return this.root.removeCaptureListener(listener);
    }

    public void clear() {
        unfocusAll();
        this.root.clear();
    }

    public void unfocusAll() {
        this.scrollFocus = null;
        this.keyboardFocus = null;
        cancelTouchFocus();
    }

    public void unfocus(Actor actor) {
        if (this.scrollFocus != null && this.scrollFocus.isDescendantOf(actor)) {
            this.scrollFocus = null;
        }
        if (this.keyboardFocus != null && this.keyboardFocus.isDescendantOf(actor)) {
            this.keyboardFocus = null;
        }
    }

    public void setKeyboardFocus(Actor actor) {
        if (this.keyboardFocus != actor) {
            FocusEvent event = (FocusEvent) Pools.obtain(FocusEvent.class);
            event.setStage(this);
            event.setType(FocusEvent.Type.keyboard);
            Actor oldKeyboardFocus = this.keyboardFocus;
            if (oldKeyboardFocus != null) {
                event.setFocused(false);
                event.setRelatedActor(actor);
                oldKeyboardFocus.fire(event);
            }
            if (!event.isCancelled()) {
                this.keyboardFocus = actor;
                if (actor != null) {
                    event.setFocused(true);
                    event.setRelatedActor(oldKeyboardFocus);
                    actor.fire(event);
                    if (event.isCancelled()) {
                        setKeyboardFocus(oldKeyboardFocus);
                    }
                }
            }
            Pools.free(event);
        }
    }

    public Actor getKeyboardFocus() {
        return this.keyboardFocus;
    }

    public void setScrollFocus(Actor actor) {
        if (this.scrollFocus != actor) {
            FocusEvent event = (FocusEvent) Pools.obtain(FocusEvent.class);
            event.setStage(this);
            event.setType(FocusEvent.Type.scroll);
            Actor oldScrollFocus = this.keyboardFocus;
            if (oldScrollFocus != null) {
                event.setFocused(false);
                event.setRelatedActor(actor);
                oldScrollFocus.fire(event);
            }
            if (!event.isCancelled()) {
                this.scrollFocus = actor;
                if (actor != null) {
                    event.setFocused(true);
                    event.setRelatedActor(oldScrollFocus);
                    actor.fire(event);
                    if (event.isCancelled()) {
                        setScrollFocus(oldScrollFocus);
                    }
                }
            }
            Pools.free(event);
        }
    }

    public Actor getScrollFocus() {
        return this.scrollFocus;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getGutterWidth() {
        return this.gutterWidth;
    }

    public float getGutterHeight() {
        return this.gutterHeight;
    }

    public SpriteBatch getSpriteBatch() {
        return this.batch;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Group getRoot() {
        return this.root;
    }

    public Actor hit(float stageX, float stageY, boolean touchable) {
        this.root.parentToLocalCoordinates(actorCoords.set(stageX, stageY));
        return this.root.hit(actorCoords.f100x, actorCoords.f101y, touchable);
    }

    public Vector2 screenToStageCoordinates(Vector2 screenCoords) {
        this.camera.unproject(cameraCoords.set(screenCoords.f100x, screenCoords.f101y, 0.0f), this.viewportX, this.viewportY, this.viewportWidth, this.viewportHeight);
        screenCoords.f100x = cameraCoords.f105x;
        screenCoords.f101y = cameraCoords.f106y;
        return screenCoords;
    }

    public Vector2 stageToScreenCoordinates(Vector2 stageCoords) {
        this.camera.project(cameraCoords.set(stageCoords.f100x, stageCoords.f101y, 0.0f), this.viewportX, this.viewportY, this.viewportWidth, this.viewportHeight);
        stageCoords.f100x = cameraCoords.f105x;
        stageCoords.f101y = this.viewportHeight - cameraCoords.f106y;
        return stageCoords;
    }

    public Vector2 toScreenCoordinates(Vector2 coords, Matrix4 transformMatrix) {
        ScissorStack.toWindowCoordinates(this.camera, transformMatrix, coords);
        return coords;
    }

    public void calculateScissors(Rectangle area, Rectangle scissor) {
        ScissorStack.calculateScissors(this.camera, this.viewportX, this.viewportY, this.viewportWidth, this.viewportHeight, this.batch.getTransformMatrix(), area, scissor);
    }

    public void dispose() {
        clear();
        if (this.ownsBatch) {
            this.batch.dispose();
        }
    }
}
