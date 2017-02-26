package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class Expo extends TweenEquation {
    public static final Expo IN;
    public static final Expo INOUT;
    public static final Expo OUT;

    /* renamed from: aurelienribon.tweenengine.equations.Expo.1 */
    static class C05541 extends Expo {
        C05541() {
        }

        public final float compute(float t) {
            return t == 0.0f ? 0.0f : (float) Math.pow(2.0d, (double) (10.0f * (t - TextTrackStyle.DEFAULT_FONT_SCALE)));
        }

        public String toString() {
            return "Expo.IN";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Expo.2 */
    static class C05552 extends Expo {
        C05552() {
        }

        public final float compute(float t) {
            return t == TextTrackStyle.DEFAULT_FONT_SCALE ? TextTrackStyle.DEFAULT_FONT_SCALE : TextTrackStyle.DEFAULT_FONT_SCALE + (-((float) Math.pow(2.0d, (double) (-10.0f * t))));
        }

        public String toString() {
            return "Expo.OUT";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Expo.3 */
    static class C05563 extends Expo {
        C05563() {
        }

        public final float compute(float t) {
            if (t == 0.0f) {
                return 0.0f;
            }
            if (t == TextTrackStyle.DEFAULT_FONT_SCALE) {
                return TextTrackStyle.DEFAULT_FONT_SCALE;
            }
            t *= 2.0f;
            if (t < TextTrackStyle.DEFAULT_FONT_SCALE) {
                return ((float) Math.pow(2.0d, (double) (10.0f * (t - TextTrackStyle.DEFAULT_FONT_SCALE)))) * 0.5f;
            }
            return ((-((float) Math.pow(2.0d, (double) (-10.0f * (t - TextTrackStyle.DEFAULT_FONT_SCALE))))) + 2.0f) * 0.5f;
        }

        public String toString() {
            return "Expo.INOUT";
        }
    }

    static {
        IN = new C05541();
        OUT = new C05552();
        INOUT = new C05563();
    }
}
