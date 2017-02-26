package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class Quart extends TweenEquation {
    public static final Quart IN;
    public static final Quart INOUT;
    public static final Quart OUT;

    /* renamed from: aurelienribon.tweenengine.equations.Quart.1 */
    static class C05611 extends Quart {
        C05611() {
        }

        public final float compute(float t) {
            return ((t * t) * t) * t;
        }

        public String toString() {
            return "Quart.IN";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Quart.2 */
    static class C05622 extends Quart {
        C05622() {
        }

        public final float compute(float t) {
            t -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return -((((t * t) * t) * t) - TextTrackStyle.DEFAULT_FONT_SCALE);
        }

        public String toString() {
            return "Quart.OUT";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Quart.3 */
    static class C05633 extends Quart {
        C05633() {
        }

        public final float compute(float t) {
            t *= 2.0f;
            if (t < TextTrackStyle.DEFAULT_FONT_SCALE) {
                return (((0.5f * t) * t) * t) * t;
            }
            t -= 2.0f;
            return -0.5f * ((((t * t) * t) * t) - 2.0f);
        }

        public String toString() {
            return "Quart.INOUT";
        }
    }

    static {
        IN = new C05611();
        OUT = new C05622();
        INOUT = new C05633();
    }
}
