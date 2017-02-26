package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class Quad extends TweenEquation {
    public static final Quad IN;
    public static final Quad INOUT;
    public static final Quad OUT;

    /* renamed from: aurelienribon.tweenengine.equations.Quad.1 */
    static class C05581 extends Quad {
        C05581() {
        }

        public final float compute(float t) {
            return t * t;
        }

        public String toString() {
            return "Quad.IN";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Quad.2 */
    static class C05592 extends Quad {
        C05592() {
        }

        public final float compute(float t) {
            return (-t) * (t - 2.0f);
        }

        public String toString() {
            return "Quad.OUT";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Quad.3 */
    static class C05603 extends Quad {
        C05603() {
        }

        public final float compute(float t) {
            t *= 2.0f;
            if (t < TextTrackStyle.DEFAULT_FONT_SCALE) {
                return (0.5f * t) * t;
            }
            t -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return -0.5f * (((t - 2.0f) * t) - TextTrackStyle.DEFAULT_FONT_SCALE);
        }

        public String toString() {
            return "Quad.INOUT";
        }
    }

    static {
        IN = new C05581();
        OUT = new C05592();
        INOUT = new C05603();
    }
}
