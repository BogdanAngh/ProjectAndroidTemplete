package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class Cubic extends TweenEquation {
    public static final Cubic IN;
    public static final Cubic INOUT;
    public static final Cubic OUT;

    /* renamed from: aurelienribon.tweenengine.equations.Cubic.1 */
    static class C05481 extends Cubic {
        C05481() {
        }

        public final float compute(float t) {
            return (t * t) * t;
        }

        public String toString() {
            return "Cubic.IN";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Cubic.2 */
    static class C05492 extends Cubic {
        C05492() {
        }

        public final float compute(float t) {
            t -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return ((t * t) * t) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }

        public String toString() {
            return "Cubic.OUT";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Cubic.3 */
    static class C05503 extends Cubic {
        C05503() {
        }

        public final float compute(float t) {
            t *= 2.0f;
            if (t < TextTrackStyle.DEFAULT_FONT_SCALE) {
                return ((0.5f * t) * t) * t;
            }
            t -= 2.0f;
            return (((t * t) * t) + 2.0f) * 0.5f;
        }

        public String toString() {
            return "Cubic.INOUT";
        }
    }

    static {
        IN = new C05481();
        OUT = new C05492();
        INOUT = new C05503();
    }
}
