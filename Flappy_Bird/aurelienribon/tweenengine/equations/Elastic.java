package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;
import com.badlogic.gdx.math.MathUtils;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class Elastic extends TweenEquation {
    public static final Elastic IN;
    public static final Elastic INOUT;
    public static final Elastic OUT;
    private static final float PI = 3.1415927f;
    protected float param_a;
    protected float param_p;
    protected boolean setA;
    protected boolean setP;

    /* renamed from: aurelienribon.tweenengine.equations.Elastic.1 */
    static class C05511 extends Elastic {
        C05511() {
        }

        public final float compute(float t) {
            float a = this.param_a;
            float p = this.param_p;
            if (t == 0.0f) {
                return 0.0f;
            }
            if (t == TextTrackStyle.DEFAULT_FONT_SCALE) {
                return TextTrackStyle.DEFAULT_FONT_SCALE;
            }
            float s;
            if (!this.setP) {
                p = 0.3f;
            }
            if (!this.setA || a < TextTrackStyle.DEFAULT_FONT_SCALE) {
                a = TextTrackStyle.DEFAULT_FONT_SCALE;
                s = p / 4.0f;
            } else {
                s = (p / MathUtils.PI2) * ((float) Math.asin((double) (TextTrackStyle.DEFAULT_FONT_SCALE / a)));
            }
            t -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return -((((float) Math.pow(2.0d, (double) (10.0f * t))) * a) * ((float) Math.sin((double) (((t - s) * MathUtils.PI2) / p))));
        }

        public String toString() {
            return "Elastic.IN";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Elastic.2 */
    static class C05522 extends Elastic {
        C05522() {
        }

        public final float compute(float t) {
            float a = this.param_a;
            float p = this.param_p;
            if (t == 0.0f) {
                return 0.0f;
            }
            if (t == TextTrackStyle.DEFAULT_FONT_SCALE) {
                return TextTrackStyle.DEFAULT_FONT_SCALE;
            }
            float s;
            if (!this.setP) {
                p = 0.3f;
            }
            if (!this.setA || a < TextTrackStyle.DEFAULT_FONT_SCALE) {
                a = TextTrackStyle.DEFAULT_FONT_SCALE;
                s = p / 4.0f;
            } else {
                s = (p / MathUtils.PI2) * ((float) Math.asin((double) (TextTrackStyle.DEFAULT_FONT_SCALE / a)));
            }
            return ((((float) Math.pow(2.0d, (double) (-10.0f * t))) * a) * ((float) Math.sin((double) (((t - s) * MathUtils.PI2) / p)))) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }

        public String toString() {
            return "Elastic.OUT";
        }
    }

    /* renamed from: aurelienribon.tweenengine.equations.Elastic.3 */
    static class C05533 extends Elastic {
        C05533() {
        }

        public final float compute(float t) {
            float a = this.param_a;
            float p = this.param_p;
            if (t == 0.0f) {
                return 0.0f;
            }
            t *= 2.0f;
            if (t == 2.0f) {
                return TextTrackStyle.DEFAULT_FONT_SCALE;
            }
            float s;
            if (!this.setP) {
                p = 0.45000002f;
            }
            if (!this.setA || a < TextTrackStyle.DEFAULT_FONT_SCALE) {
                a = TextTrackStyle.DEFAULT_FONT_SCALE;
                s = p / 4.0f;
            } else {
                s = (p / MathUtils.PI2) * ((float) Math.asin((double) (TextTrackStyle.DEFAULT_FONT_SCALE / a)));
            }
            if (t < TextTrackStyle.DEFAULT_FONT_SCALE) {
                t -= TextTrackStyle.DEFAULT_FONT_SCALE;
                return -0.5f * ((((float) Math.pow(2.0d, (double) (10.0f * t))) * a) * ((float) Math.sin((double) (((t - s) * MathUtils.PI2) / p))));
            }
            t -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return (((((float) Math.pow(2.0d, (double) (-10.0f * t))) * a) * ((float) Math.sin((double) (((t - s) * MathUtils.PI2) / p)))) * 0.5f) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }

        public String toString() {
            return "Elastic.INOUT";
        }
    }

    public Elastic() {
        this.setA = false;
        this.setP = false;
    }

    static {
        IN = new C05511();
        OUT = new C05522();
        INOUT = new C05533();
    }

    public Elastic m6a(float a) {
        this.param_a = a;
        this.setA = true;
        return this;
    }

    public Elastic m7p(float p) {
        this.param_p = p;
        this.setP = true;
        return this;
    }
}
