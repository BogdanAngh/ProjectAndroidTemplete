package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.Vector2;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public enum Scaling {
    fit,
    fill,
    fillX,
    fillY,
    stretch,
    stretchX,
    stretchY,
    none;
    
    private static final Vector2 temp;

    /* renamed from: com.badlogic.gdx.utils.Scaling.1 */
    static /* synthetic */ class C00641 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$utils$Scaling;

        static {
            $SwitchMap$com$badlogic$gdx$utils$Scaling = new int[Scaling.values().length];
            try {
                $SwitchMap$com$badlogic$gdx$utils$Scaling[Scaling.fit.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$Scaling[Scaling.fill.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$Scaling[Scaling.fillX.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$Scaling[Scaling.fillY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$Scaling[Scaling.stretch.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$Scaling[Scaling.stretchX.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$Scaling[Scaling.stretchY.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$Scaling[Scaling.none.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    static {
        temp = new Vector2();
    }

    public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
        float scale;
        float targetRatio;
        float f;
        switch (C00641.$SwitchMap$com$badlogic$gdx$utils$Scaling[ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                scale = targetHeight / targetWidth > sourceHeight / sourceWidth ? targetWidth / sourceWidth : targetHeight / sourceHeight;
                temp.f100x = sourceWidth * scale;
                temp.f101y = sourceHeight * scale;
                break;
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                scale = targetHeight / targetWidth < sourceHeight / sourceWidth ? targetWidth / sourceWidth : targetHeight / sourceHeight;
                temp.f100x = sourceWidth * scale;
                temp.f101y = sourceHeight * scale;
                break;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                targetRatio = targetHeight / targetWidth;
                f = sourceHeight / sourceWidth;
                scale = targetWidth / sourceWidth;
                temp.f100x = sourceWidth * scale;
                temp.f101y = sourceHeight * scale;
                break;
            case GameHelper.CLIENT_APPSTATE /*4*/:
                targetRatio = targetHeight / targetWidth;
                f = sourceHeight / sourceWidth;
                scale = targetHeight / sourceHeight;
                temp.f100x = sourceWidth * scale;
                temp.f101y = sourceHeight * scale;
                break;
            case Place.TYPE_ART_GALLERY /*5*/:
                temp.f100x = targetWidth;
                temp.f101y = targetHeight;
                break;
            case Place.TYPE_ATM /*6*/:
                temp.f100x = targetWidth;
                temp.f101y = sourceHeight;
                break;
            case Place.TYPE_BAKERY /*7*/:
                temp.f100x = sourceWidth;
                temp.f101y = targetHeight;
                break;
            case GameHelper.CLIENT_SNAPSHOT /*8*/:
                temp.f100x = sourceWidth;
                temp.f101y = sourceHeight;
                break;
        }
        return temp;
    }
}
