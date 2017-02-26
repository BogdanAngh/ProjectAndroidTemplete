package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class Quint extends TweenEquation {
    public static final Quint IN;
    public static final Quint INOUT;
    public static final Quint OUT;

    /* renamed from: aurelienribon.tweenengine.equations.Quint.1 */
    static class C05641 extends Quint {
        C05641() {
        }

        public final float compute(float t) {
            return (((t * t) * t) * t) * t;
        }

        public String toString() {
            return "Quint.IN";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Quint.2 */
    static class C05652 extends Quint {
        C05652() {
        }

        public final float compute(float t) {
            t -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return ((((t * t) * t) * t) * t) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }

        public String toString() {
            return "Quint.OUT";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Quint.3 */
    static class C05663 extends Quint {
        C05663() {
        }

        public final float compute(float t) {
            t *= 2.0f;
            if (t < TextTrackStyle.DEFAULT_FONT_SCALE) {
                return ((((0.5f * t) * t) * t) * t) * t;
            }
            t -= 2.0f;
            return (((((t * t) * t) * t) * t) + 2.0f) * 0.5f;
        }

        public String toString() {
            return "Quint.INOUT";
        }
    }

    static {
        IN = new C05641();
        OUT = new C05652();
        INOUT = new C05663();
    }
}
