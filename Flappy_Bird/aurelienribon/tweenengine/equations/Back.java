package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class Back extends TweenEquation {
    public static final Back IN;
    public static final Back INOUT;
    public static final Back OUT;
    protected float param_s;

    /* renamed from: aurelienribon.tweenengine.equations.Back.1 */
    static class C05391 extends Back {
        C05391() {
        }

        public final float compute(float t) {
            float s = this.param_s;
            return (t * t) * (((TextTrackStyle.DEFAULT_FONT_SCALE + s) * t) - s);
        }

        public String toString() {
            return "Back.IN";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Back.2 */
    static class C05402 extends Back {
        C05402() {
        }

        public final float compute(float t) {
            float s = this.param_s;
            t -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return ((t * t) * (((s + TextTrackStyle.DEFAULT_FONT_SCALE) * t) + s)) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }

        public String toString() {
            return "Back.OUT";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Back.3 */
    static class C05413 extends Back {
        C05413() {
        }

        public final float compute(float t) {
            float s = this.param_s;
            t *= 2.0f;
            if (t < TextTrackStyle.DEFAULT_FONT_SCALE) {
                s *= 1.525f;
                return ((t * t) * (((TextTrackStyle.DEFAULT_FONT_SCALE + s) * t) - s)) * 0.5f;
            }
            t -= 2.0f;
            s *= 1.525f;
            return (((t * t) * (((TextTrackStyle.DEFAULT_FONT_SCALE + s) * t) + s)) + 2.0f) * 0.5f;
        }

        public String toString() {
            return "Back.INOUT";
        }
    }

    public Back() {
        this.param_s = 1.70158f;
    }

    static {
        IN = new C05391();
        OUT = new C05402();
        INOUT = new C05413();
    }

    public Back m5s(float s) {
        this.param_s = s;
        return this;
    }
}
