package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class Circ extends TweenEquation {
    public static final Circ IN;
    public static final Circ INOUT;
    public static final Circ OUT;

    /* renamed from: aurelienribon.tweenengine.equations.Circ.1 */
    static class C05451 extends Circ {
        C05451() {
        }

        public final float compute(float t) {
            return ((float) (-Math.sqrt((double) (TextTrackStyle.DEFAULT_FONT_SCALE - (t * t))))) - TextTrackStyle.DEFAULT_FONT_SCALE;
        }

        public String toString() {
            return "Circ.IN";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Circ.2 */
    static class C05462 extends Circ {
        C05462() {
        }

        public final float compute(float t) {
            t -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return (float) Math.sqrt((double) (TextTrackStyle.DEFAULT_FONT_SCALE - (t * t)));
        }

        public String toString() {
            return "Circ.OUT";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Circ.3 */
    static class C05473 extends Circ {
        C05473() {
        }

        public final float compute(float t) {
            t *= 2.0f;
            if (t < TextTrackStyle.DEFAULT_FONT_SCALE) {
                return -0.5f * (((float) Math.sqrt((double) (TextTrackStyle.DEFAULT_FONT_SCALE - (t * t)))) - TextTrackStyle.DEFAULT_FONT_SCALE);
            }
            t -= 2.0f;
            return 0.5f * (((float) Math.sqrt((double) (TextTrackStyle.DEFAULT_FONT_SCALE - (t * t)))) + TextTrackStyle.DEFAULT_FONT_SCALE);
        }

        public String toString() {
            return "Circ.INOUT";
        }
    }

    static {
        IN = new C05451();
        OUT = new C05462();
        INOUT = new C05473();
    }
}
