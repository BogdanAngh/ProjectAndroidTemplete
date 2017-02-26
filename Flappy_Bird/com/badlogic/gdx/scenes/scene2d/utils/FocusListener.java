package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.google.android.gms.drive.events.CompletionEvent;

public abstract class FocusListener implements EventListener {

    /* renamed from: com.badlogic.gdx.scenes.scene2d.utils.FocusListener.1 */
    static /* synthetic */ class C00611 {
        static final /* synthetic */ int[] f85x585ff15f;

        static {
            f85x585ff15f = new int[Type.values().length];
            try {
                f85x585ff15f[Type.keyboard.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f85x585ff15f[Type.scroll.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static class FocusEvent extends Event {
        private boolean focused;
        private Actor relatedActor;
        private Type type;

        public enum Type {
            keyboard,
            scroll
        }

        public void reset() {
            super.reset();
            this.relatedActor = null;
        }

        public boolean isFocused() {
            return this.focused;
        }

        public void setFocused(boolean focused) {
            this.focused = focused;
        }

        public Type getType() {
            return this.type;
        }

        public void setType(Type focusType) {
            this.type = focusType;
        }

        public Actor getRelatedActor() {
            return this.relatedActor;
        }

        public void setRelatedActor(Actor relatedActor) {
            this.relatedActor = relatedActor;
        }
    }

    public boolean handle(Event event) {
        if (event instanceof FocusEvent) {
            FocusEvent focusEvent = (FocusEvent) event;
            switch (C00611.f85x585ff15f[focusEvent.getType().ordinal()]) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    keyboardFocusChanged(focusEvent, event.getTarget(), focusEvent.isFocused());
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    scrollFocusChanged(focusEvent, event.getTarget(), focusEvent.isFocused());
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
    }

    public void scrollFocusChanged(FocusEvent event, Actor actor, boolean focused) {
    }
}
