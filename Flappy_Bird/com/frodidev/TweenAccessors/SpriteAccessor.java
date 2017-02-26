package com.frodidev.TweenAccessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.google.android.gms.cast.TextTrackStyle;

public class SpriteAccessor implements TweenAccessor<Sprite> {
    public static final int ALPHA = 1;

    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA /*1*/:
                returnValues[0] = target.getColor().f37a;
                return ALPHA;
            default:
                return 0;
        }
    }

    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA /*1*/:
                target.setColor(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, newValues[0]);
            default:
        }
    }
}
