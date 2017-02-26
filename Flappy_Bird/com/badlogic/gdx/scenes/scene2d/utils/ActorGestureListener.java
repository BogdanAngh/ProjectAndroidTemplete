package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.google.android.gms.drive.events.CompletionEvent;

public class ActorGestureListener implements EventListener {
    static final Vector2 tmpCoords;
    Actor actor;
    private final GestureDetector detector;
    InputEvent event;
    Actor touchDownTarget;

    /* renamed from: com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener.2 */
    static /* synthetic */ class C00602 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type;

        static {
            $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type = new int[Type.values().length];
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.touchDown.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.touchUp.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.touchDragged.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener.1 */
    class C05891 extends GestureAdapter {
        private final Vector2 initialPointer1;
        private final Vector2 initialPointer2;
        private final Vector2 pointer1;
        private final Vector2 pointer2;

        C05891() {
            this.initialPointer1 = new Vector2();
            this.initialPointer2 = new Vector2();
            this.pointer1 = new Vector2();
            this.pointer2 = new Vector2();
        }

        public boolean tap(float stageX, float stageY, int count, int button) {
            ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(stageX, stageY));
            ActorGestureListener.this.tap(ActorGestureListener.this.event, ActorGestureListener.tmpCoords.f100x, ActorGestureListener.tmpCoords.f101y, count, button);
            return true;
        }

        public boolean longPress(float stageX, float stageY) {
            ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(stageX, stageY));
            return ActorGestureListener.this.longPress(ActorGestureListener.this.actor, ActorGestureListener.tmpCoords.f100x, ActorGestureListener.tmpCoords.f101y);
        }

        public boolean fling(float velocityX, float velocityY, int button) {
            ActorGestureListener.this.fling(ActorGestureListener.this.event, velocityX, velocityY, button);
            return true;
        }

        public boolean pan(float stageX, float stageY, float deltaX, float deltaY) {
            ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(stageX, stageY));
            ActorGestureListener.this.pan(ActorGestureListener.this.event, ActorGestureListener.tmpCoords.f100x, ActorGestureListener.tmpCoords.f101y, deltaX, deltaY);
            return true;
        }

        public boolean zoom(float initialDistance, float distance) {
            ActorGestureListener.this.zoom(ActorGestureListener.this.event, initialDistance, distance);
            return true;
        }

        public boolean pinch(Vector2 stageInitialPointer1, Vector2 stageInitialPointer2, Vector2 stagePointer1, Vector2 stagePointer2) {
            ActorGestureListener.this.actor.stageToLocalCoordinates(this.initialPointer1.set(stageInitialPointer1));
            ActorGestureListener.this.actor.stageToLocalCoordinates(this.initialPointer2.set(stageInitialPointer2));
            ActorGestureListener.this.actor.stageToLocalCoordinates(this.pointer1.set(stagePointer1));
            ActorGestureListener.this.actor.stageToLocalCoordinates(this.pointer2.set(stagePointer2));
            ActorGestureListener.this.pinch(ActorGestureListener.this.event, this.initialPointer1, this.initialPointer2, this.pointer1, this.pointer2);
            return true;
        }
    }

    static {
        tmpCoords = new Vector2();
    }

    public ActorGestureListener() {
        this(20.0f, 0.4f, 1.1f, 0.15f);
    }

    public ActorGestureListener(float halfTapSquareSize, float tapCountInterval, float longPressDuration, float maxFlingDelay) {
        this.detector = new GestureDetector(halfTapSquareSize, tapCountInterval, longPressDuration, maxFlingDelay, new C05891());
    }

    public boolean handle(Event e) {
        if (!(e instanceof InputEvent)) {
            return false;
        }
        InputEvent event = (InputEvent) e;
        switch (C00602.$SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[event.getType().ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                this.actor = event.getListenerActor();
                this.touchDownTarget = event.getTarget();
                this.detector.touchDown(event.getStageX(), event.getStageY(), event.getPointer(), event.getButton());
                this.actor.stageToLocalCoordinates(tmpCoords.set(event.getStageX(), event.getStageY()));
                touchDown(event, tmpCoords.f100x, tmpCoords.f101y, event.getPointer(), event.getButton());
                return true;
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                if (event.isTouchFocusCancel()) {
                    return false;
                }
                this.event = event;
                this.actor = event.getListenerActor();
                this.detector.touchUp(event.getStageX(), event.getStageY(), event.getPointer(), event.getButton());
                this.actor.stageToLocalCoordinates(tmpCoords.set(event.getStageX(), event.getStageY()));
                touchUp(event, tmpCoords.f100x, tmpCoords.f101y, event.getPointer(), event.getButton());
                return true;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                this.event = event;
                this.actor = event.getListenerActor();
                this.detector.touchDragged(event.getStageX(), event.getStageY(), event.getPointer());
                return true;
            default:
                return false;
        }
    }

    public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
    }

    public void tap(InputEvent event, float x, float y, int count, int button) {
    }

    public boolean longPress(Actor actor, float x, float y) {
        return false;
    }

    public void fling(InputEvent event, float velocityX, float velocityY, int button) {
    }

    public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
    }

    public void zoom(InputEvent event, float initialDistance, float distance) {
    }

    public void pinch(InputEvent event, Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
    }

    public GestureDetector getGestureDetector() {
        return this.detector;
    }

    public Actor getTouchDownTarget() {
        return this.touchDownTarget;
    }
}
