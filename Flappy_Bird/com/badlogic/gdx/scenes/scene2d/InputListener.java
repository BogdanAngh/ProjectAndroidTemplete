package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public class InputListener implements EventListener {
    private static final Vector2 tmpCoords;

    /* renamed from: com.badlogic.gdx.scenes.scene2d.InputListener.1 */
    static /* synthetic */ class C00591 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type;

        static {
            $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type = new int[Type.values().length];
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.keyDown.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.keyUp.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.keyTyped.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.touchDown.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.touchUp.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.touchDragged.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.mouseMoved.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.scrolled.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.enter.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[Type.exit.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    static {
        tmpCoords = new Vector2();
    }

    public boolean handle(Event e) {
        if (!(e instanceof InputEvent)) {
            return false;
        }
        InputEvent event = (InputEvent) e;
        switch (C00591.$SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[event.getType().ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return keyDown(event, event.getKeyCode());
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return keyUp(event, event.getKeyCode());
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return keyTyped(event, event.getCharacter());
            default:
                event.toCoordinates(event.getListenerActor(), tmpCoords);
                switch (C00591.$SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[event.getType().ordinal()]) {
                    case GameHelper.CLIENT_APPSTATE /*4*/:
                        return touchDown(event, tmpCoords.f100x, tmpCoords.f101y, event.getPointer(), event.getButton());
                    case Place.TYPE_ART_GALLERY /*5*/:
                        touchUp(event, tmpCoords.f100x, tmpCoords.f101y, event.getPointer(), event.getButton());
                        return true;
                    case Place.TYPE_ATM /*6*/:
                        touchDragged(event, tmpCoords.f100x, tmpCoords.f101y, event.getPointer());
                        return true;
                    case Place.TYPE_BAKERY /*7*/:
                        return mouseMoved(event, tmpCoords.f100x, tmpCoords.f101y);
                    case GameHelper.CLIENT_SNAPSHOT /*8*/:
                        return scrolled(event, tmpCoords.f100x, tmpCoords.f101y, event.getScrollAmount());
                    case Place.TYPE_BAR /*9*/:
                        enter(event, tmpCoords.f100x, tmpCoords.f101y, event.getPointer(), event.getRelatedActor());
                        return false;
                    case Place.TYPE_BEAUTY_SALON /*10*/:
                        exit(event, tmpCoords.f100x, tmpCoords.f101y, event.getPointer(), event.getRelatedActor());
                        return false;
                    default:
                        return false;
                }
        }
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return false;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
    }

    public void touchDragged(InputEvent event, float x, float y, int pointer) {
    }

    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }

    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
    }

    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
    }

    public boolean scrolled(InputEvent event, float x, float y, int amount) {
        return false;
    }

    public boolean keyDown(InputEvent event, int keycode) {
        return false;
    }

    public boolean keyUp(InputEvent event, int keycode) {
        return false;
    }

    public boolean keyTyped(InputEvent event, char character) {
        return false;
    }
}
