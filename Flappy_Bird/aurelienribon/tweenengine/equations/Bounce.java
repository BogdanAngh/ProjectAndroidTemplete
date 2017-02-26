package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class Bounce extends TweenEquation {
    public static final Bounce IN;
    public static final Bounce INOUT;
    public static final Bounce OUT;

    /* renamed from: aurelienribon.tweenengine.equations.Bounce.1 */
    static class C05421 extends Bounce {
        C05421() {
        }

        public final float compute(float t) {
            return TextTrackStyle.DEFAULT_FONT_SCALE - OUT.compute(TextTrackStyle.DEFAULT_FONT_SCALE - t);
        }

        public String toString() {
            return "Bounce.IN";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Bounce.2 */
    static class C05432 extends Bounce {
        C05432() {
        }

        public final float compute(float t) {
            if (((double) t) < 0.36363636363636365d) {
                return (7.5625f * t) * t;
            }
            if (((double) t) < 0.7272727272727273d) {
                t -= 0.54545456f;
                return ((7.5625f * t) * t) + 0.75f;
            } else if (((double) t) < 0.9090909090909091d) {
                t -= 0.8181818f;
                return ((7.5625f * t) * t) + 0.9375f;
            } else {
                t -= 0.95454544f;
                return ((7.5625f * t) * t) + 0.984375f;
            }
        }

        public String toString() {
            return "Bounce.OUT";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Bounce.3 */
    static class C05443 extends Bounce {
        C05443() {
        }

        public final float compute(float t) {
            if (t < 0.5f) {
                return IN.compute(2.0f * t) * 0.5f;
            }
            return (OUT.compute((2.0f * t) - TextTrackStyle.DEFAULT_FONT_SCALE) * 0.5f) + 0.5f;
        }

        public String toString() {
            return "Bounce.INOUT";
        }
    }

    static {
        IN = new C05421();
        OUT = new C05432();
        INOUT = new C05443();
    }
}
