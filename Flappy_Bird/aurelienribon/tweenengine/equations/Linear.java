package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

public abstract class Linear extends TweenEquation {
    public static final Linear INOUT;

    /* renamed from: aurelienribon.tweenengine.equations.Linear.1 */
    static class C05571 extends Linear {
        C05571() {
        }

        public float compute(float t) {
            return t;
        }

        public String toString() {
            return "Linear.INOUT";
        }
    }

    static {
        INOUT = new C05571();
    }
}
