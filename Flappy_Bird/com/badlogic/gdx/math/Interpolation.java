package com.badlogic.gdx.math;

import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public abstract class Interpolation {
    public static final Interpolation bounce;
    public static final Interpolation bounceIn;
    public static final Interpolation bounceOut;
    public static final Interpolation circle;
    public static final Interpolation circleIn;
    public static final Interpolation circleOut;
    public static final Elastic elastic;
    public static final Elastic elasticIn;
    public static final Elastic elasticOut;
    public static final Interpolation exp10;
    public static final Interpolation exp10In;
    public static final Interpolation exp10Out;
    public static final Interpolation exp5;
    public static final Interpolation exp5In;
    public static final Interpolation exp5Out;
    public static final Interpolation fade;
    public static final Interpolation linear;
    public static final Pow pow2;
    public static final PowIn pow2In;
    public static final PowOut pow2Out;
    public static final Pow pow3;
    public static final PowIn pow3In;
    public static final PowOut pow3Out;
    public static final Pow pow4;
    public static final PowIn pow4In;
    public static final PowOut pow4Out;
    public static final Pow pow5;
    public static final PowIn pow5In;
    public static final PowOut pow5Out;
    public static final Interpolation sine;
    public static final Interpolation sineIn;
    public static final Interpolation sineOut;
    public static final Interpolation swing;
    public static final Interpolation swingIn;
    public static final Interpolation swingOut;

    /* renamed from: com.badlogic.gdx.math.Interpolation.1 */
    static class C03671 extends Interpolation {
        C03671() {
        }

        public float apply(float a) {
            return a;
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation.2 */
    static class C03682 extends Interpolation {
        C03682() {
        }

        public float apply(float a) {
            return MathUtils.clamp(((a * a) * a) * ((((6.0f * a) - 15.0f) * a) + 10.0f), 0.0f, (float) TextTrackStyle.DEFAULT_FONT_SCALE);
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation.3 */
    static class C03693 extends Interpolation {
        C03693() {
        }

        public float apply(float a) {
            return (TextTrackStyle.DEFAULT_FONT_SCALE - MathUtils.cos(MathUtils.PI * a)) / 2.0f;
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation.4 */
    static class C03704 extends Interpolation {
        C03704() {
        }

        public float apply(float a) {
            return TextTrackStyle.DEFAULT_FONT_SCALE - MathUtils.cos((MathUtils.PI * a) / 2.0f);
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation.5 */
    static class C03715 extends Interpolation {
        C03715() {
        }

        public float apply(float a) {
            return MathUtils.sin((MathUtils.PI * a) / 2.0f);
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation.6 */
    static class C03726 extends Interpolation {
        C03726() {
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                a *= 2.0f;
                return (TextTrackStyle.DEFAULT_FONT_SCALE - ((float) Math.sqrt((double) (TextTrackStyle.DEFAULT_FONT_SCALE - (a * a))))) / 2.0f;
            }
            a = (a - TextTrackStyle.DEFAULT_FONT_SCALE) * 2.0f;
            return (((float) Math.sqrt((double) (TextTrackStyle.DEFAULT_FONT_SCALE - (a * a)))) + TextTrackStyle.DEFAULT_FONT_SCALE) / 2.0f;
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation.7 */
    static class C03737 extends Interpolation {
        C03737() {
        }

        public float apply(float a) {
            return TextTrackStyle.DEFAULT_FONT_SCALE - ((float) Math.sqrt((double) (TextTrackStyle.DEFAULT_FONT_SCALE - (a * a))));
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation.8 */
    static class C03748 extends Interpolation {
        C03748() {
        }

        public float apply(float a) {
            a -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return (float) Math.sqrt((double) (TextTrackStyle.DEFAULT_FONT_SCALE - (a * a)));
        }
    }

    public static class BounceOut extends Interpolation {
        final float[] heights;
        final float[] widths;

        public BounceOut(float[] widths, float[] heights) {
            if (widths.length != heights.length) {
                throw new IllegalArgumentException("Must be the same number of widths and heights.");
            }
            this.widths = widths;
            this.heights = heights;
        }

        public BounceOut(int bounces) {
            if (bounces < 2 || bounces > 5) {
                throw new IllegalArgumentException("bounces cannot be < 2 or > 5: " + bounces);
            }
            this.widths = new float[bounces];
            this.heights = new float[bounces];
            this.heights[0] = TextTrackStyle.DEFAULT_FONT_SCALE;
            switch (bounces) {
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    this.widths[0] = 0.6f;
                    this.widths[1] = 0.4f;
                    this.heights[1] = 0.33f;
                    break;
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    this.widths[0] = 0.4f;
                    this.widths[1] = 0.4f;
                    this.widths[2] = 0.2f;
                    this.heights[1] = 0.33f;
                    this.heights[2] = 0.1f;
                    break;
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    this.widths[0] = 0.34f;
                    this.widths[1] = 0.34f;
                    this.widths[2] = 0.2f;
                    this.widths[3] = 0.15f;
                    this.heights[1] = 0.26f;
                    this.heights[2] = 0.11f;
                    this.heights[3] = 0.03f;
                    break;
                case Place.TYPE_ART_GALLERY /*5*/:
                    this.widths[0] = 0.3f;
                    this.widths[1] = 0.3f;
                    this.widths[2] = 0.2f;
                    this.widths[3] = 0.1f;
                    this.widths[4] = 0.1f;
                    this.heights[1] = 0.45f;
                    this.heights[2] = 0.3f;
                    this.heights[3] = 0.15f;
                    this.heights[4] = 0.06f;
                    break;
            }
            float[] fArr = this.widths;
            fArr[0] = fArr[0] * 2.0f;
        }

        public float apply(float a) {
            a += this.widths[0] / 2.0f;
            float width = 0.0f;
            float height = 0.0f;
            int n = this.widths.length;
            for (int i = 0; i < n; i++) {
                width = this.widths[i];
                if (a <= width) {
                    height = this.heights[i];
                    break;
                }
                a -= width;
            }
            a /= width;
            float z = ((4.0f / width) * height) * a;
            return TextTrackStyle.DEFAULT_FONT_SCALE - ((z - (z * a)) * width);
        }
    }

    public static class Elastic extends Interpolation {
        final float power;
        final float value;

        public Elastic(float value, float power) {
            this.value = value;
            this.power = power;
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                a *= 2.0f;
                return ((((float) Math.pow((double) this.value, (double) (this.power * (a - TextTrackStyle.DEFAULT_FONT_SCALE)))) * MathUtils.sin(a * 20.0f)) * 1.0955f) / 2.0f;
            }
            a = (TextTrackStyle.DEFAULT_FONT_SCALE - a) * 2.0f;
            return TextTrackStyle.DEFAULT_FONT_SCALE - (((((float) Math.pow((double) this.value, (double) (this.power * (a - TextTrackStyle.DEFAULT_FONT_SCALE)))) * MathUtils.sin(a * 20.0f)) * 1.0955f) / 2.0f);
        }
    }

    public static class Exp extends Interpolation {
        final float min;
        final float power;
        final float scale;
        final float value;

        public Exp(float value, float power) {
            this.value = value;
            this.power = power;
            this.min = (float) Math.pow((double) value, (double) (-power));
            this.scale = TextTrackStyle.DEFAULT_FONT_SCALE / (TextTrackStyle.DEFAULT_FONT_SCALE - this.min);
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                return ((((float) Math.pow((double) this.value, (double) (this.power * ((a * 2.0f) - TextTrackStyle.DEFAULT_FONT_SCALE)))) - this.min) * this.scale) / 2.0f;
            }
            return (2.0f - ((((float) Math.pow((double) this.value, (double) ((-this.power) * ((a * 2.0f) - TextTrackStyle.DEFAULT_FONT_SCALE)))) - this.min) * this.scale)) / 2.0f;
        }
    }

    public static class Pow extends Interpolation {
        final int power;

        public Pow(int power) {
            this.power = power;
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                return ((float) Math.pow((double) (a * 2.0f), (double) this.power)) / 2.0f;
            }
            return (((float) Math.pow((double) ((a - TextTrackStyle.DEFAULT_FONT_SCALE) * 2.0f), (double) this.power)) / ((float) (this.power % 2 == 0 ? -2 : 2))) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }
    }

    public static class Swing extends Interpolation {
        private final float scale;

        public Swing(float scale) {
            this.scale = 2.0f * scale;
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                a *= 2.0f;
                return ((a * a) * (((this.scale + TextTrackStyle.DEFAULT_FONT_SCALE) * a) - this.scale)) / 2.0f;
            }
            a = (a - TextTrackStyle.DEFAULT_FONT_SCALE) * 2.0f;
            return (((a * a) * (((this.scale + TextTrackStyle.DEFAULT_FONT_SCALE) * a) + this.scale)) / 2.0f) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }
    }

    public static class SwingIn extends Interpolation {
        private final float scale;

        public SwingIn(float scale) {
            this.scale = scale;
        }

        public float apply(float a) {
            return (a * a) * (((this.scale + TextTrackStyle.DEFAULT_FONT_SCALE) * a) - this.scale);
        }
    }

    public static class SwingOut extends Interpolation {
        private final float scale;

        public SwingOut(float scale) {
            this.scale = scale;
        }

        public float apply(float a) {
            a -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return ((a * a) * (((this.scale + TextTrackStyle.DEFAULT_FONT_SCALE) * a) + this.scale)) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }
    }

    public static class Bounce extends BounceOut {
        public Bounce(float[] widths, float[] heights) {
            super(widths, heights);
        }

        public Bounce(int bounces) {
            super(bounces);
        }

        private float out(float a) {
            float test = a + (this.widths[0] / 2.0f);
            if (test < this.widths[0]) {
                return (test / (this.widths[0] / 2.0f)) - TextTrackStyle.DEFAULT_FONT_SCALE;
            }
            return super.apply(a);
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                return (TextTrackStyle.DEFAULT_FONT_SCALE - out(TextTrackStyle.DEFAULT_FONT_SCALE - (a * 2.0f))) / 2.0f;
            }
            return (out((a * 2.0f) - TextTrackStyle.DEFAULT_FONT_SCALE) / 2.0f) + 0.5f;
        }
    }

    public static class BounceIn extends BounceOut {
        public BounceIn(float[] widths, float[] heights) {
            super(widths, heights);
        }

        public BounceIn(int bounces) {
            super(bounces);
        }

        public float apply(float a) {
            return TextTrackStyle.DEFAULT_FONT_SCALE - super.apply(TextTrackStyle.DEFAULT_FONT_SCALE - a);
        }
    }

    public static class ElasticIn extends Elastic {
        public ElasticIn(float value, float power) {
            super(value, power);
        }

        public float apply(float a) {
            return (((float) Math.pow((double) this.value, (double) (this.power * (a - TextTrackStyle.DEFAULT_FONT_SCALE)))) * MathUtils.sin(20.0f * a)) * 1.0955f;
        }
    }

    public static class ElasticOut extends Elastic {
        public ElasticOut(float value, float power) {
            super(value, power);
        }

        public float apply(float a) {
            a = TextTrackStyle.DEFAULT_FONT_SCALE - a;
            return TextTrackStyle.DEFAULT_FONT_SCALE - ((((float) Math.pow((double) this.value, (double) (this.power * (a - TextTrackStyle.DEFAULT_FONT_SCALE)))) * MathUtils.sin(20.0f * a)) * 1.0955f);
        }
    }

    public static class ExpIn extends Exp {
        public ExpIn(float value, float power) {
            super(value, power);
        }

        public float apply(float a) {
            return (((float) Math.pow((double) this.value, (double) (this.power * (a - TextTrackStyle.DEFAULT_FONT_SCALE)))) - this.min) * this.scale;
        }
    }

    public static class ExpOut extends Exp {
        public ExpOut(float value, float power) {
            super(value, power);
        }

        public float apply(float a) {
            return TextTrackStyle.DEFAULT_FONT_SCALE - ((((float) Math.pow((double) this.value, (double) ((-this.power) * a))) - this.min) * this.scale);
        }
    }

    public static class PowIn extends Pow {
        public PowIn(int power) {
            super(power);
        }

        public float apply(float a) {
            return (float) Math.pow((double) a, (double) this.power);
        }
    }

    public static class PowOut extends Pow {
        public PowOut(int power) {
            super(power);
        }

        public float apply(float a) {
            return (((float) (this.power % 2 == 0 ? -1 : 1)) * ((float) Math.pow((double) (a - TextTrackStyle.DEFAULT_FONT_SCALE), (double) this.power))) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }
    }

    public abstract float apply(float f);

    public float apply(float start, float end, float a) {
        return ((end - start) * apply(a)) + start;
    }

    static {
        linear = new C03671();
        fade = new C03682();
        pow2 = new Pow(2);
        pow2In = new PowIn(2);
        pow2Out = new PowOut(2);
        pow3 = new Pow(3);
        pow3In = new PowIn(3);
        pow3Out = new PowOut(3);
        pow4 = new Pow(4);
        pow4In = new PowIn(4);
        pow4Out = new PowOut(4);
        pow5 = new Pow(5);
        pow5In = new PowIn(5);
        pow5Out = new PowOut(5);
        sine = new C03693();
        sineIn = new C03704();
        sineOut = new C03715();
        exp10 = new Exp(2.0f, 10.0f);
        exp10In = new ExpIn(2.0f, 10.0f);
        exp10Out = new ExpOut(2.0f, 10.0f);
        exp5 = new Exp(2.0f, 5.0f);
        exp5In = new ExpIn(2.0f, 5.0f);
        exp5Out = new ExpOut(2.0f, 5.0f);
        circle = new C03726();
        circleIn = new C03737();
        circleOut = new C03748();
        elastic = new Elastic(2.0f, 10.0f);
        elasticIn = new ElasticIn(2.0f, 10.0f);
        elasticOut = new ElasticOut(2.0f, 10.0f);
        swing = new Swing(1.5f);
        swingIn = new SwingIn(2.0f);
        swingOut = new SwingOut(2.0f);
        bounce = new Bounce(4);
        bounceIn = new BounceIn(4);
        bounceOut = new BounceOut(4);
    }
}
