package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class Sine extends TweenEquation {
    public static final Sine IN;
    public static final Sine INOUT;
    public static final Sine OUT;
    private static final float PI = 3.1415927f;

    /* renamed from: aurelienribon.tweenengine.equations.Sine.1 */
    static class C05671 extends Sine {
        C05671() {
        }

        public final float compute(float t) {
            return ((float) (-Math.cos((double) (1.5707964f * t)))) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }

        public String toString() {
            return "Sine.IN";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Sine.2 */
    static class C05682 extends Sine {
        C05682() {
        }

        public final float compute(float t) {
            return (float) Math.sin((double) (1.5707964f * t));
        }

        public String toString() {
            return "Sine.OUT";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Sine.3 */
    static class C05693 extends Sine {
        C05693() {
        }

        public final float compute(float t) {
            return -0.5f * (((float) Math.cos((double) (Sine.PI * t))) - TextTrackStyle.DEFAULT_FONT_SCALE);
        }

        public String toString() {
            return "Sine.INOUT";
        }
    }

    static {
        IN = new C05671();
        OUT = new C05682();
        INOUT = new C05693();
    }
}
