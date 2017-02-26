package org.apache.commons.math4.dfp;

import com.example.duy.calculator.math_eval.Constants;
import com.google.android.gms.common.ConnectionResult;
import edu.jas.poly.ExpVectorInteger;
import io.github.kexanie.library.R;
import java.util.Arrays;
import org.apache.commons.math4.RealFieldElement;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.analysis.interpolation.MicrosphereInterpolator;
import org.apache.commons.math4.dfp.DfpField.RoundingMode;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.random.EmpiricalDistribution;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.FastMath;
import org.matheclipse.core.interfaces.IExpr;

public class Dfp implements RealFieldElement<Dfp> {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode = null;
    private static final String ADD_TRAP = "add";
    private static final String ALIGN_TRAP = "align";
    private static final String DIVIDE_TRAP = "divide";
    public static final int ERR_SCALE = 32760;
    public static final byte FINITE = (byte) 0;
    private static final String GREATER_THAN_TRAP = "greaterThan";
    public static final byte INFINITE = (byte) 1;
    private static final String LESS_THAN_TRAP = "lessThan";
    public static final int MAX_EXP = 32768;
    public static final int MIN_EXP = -32767;
    private static final String MULTIPLY_TRAP = "multiply";
    private static final String NAN_STRING = "NaN";
    private static final String NEG_INFINITY_STRING = "-Infinity";
    private static final String NEW_INSTANCE_TRAP = "newInstance";
    private static final String NEXT_AFTER_TRAP = "nextAfter";
    private static final String POS_INFINITY_STRING = "Infinity";
    public static final byte QNAN = (byte) 3;
    public static final int RADIX = 10000;
    public static final byte SNAN = (byte) 2;
    private static final String SQRT_TRAP = "sqrt";
    private static final String TRUNC_TRAP = "trunc";
    protected int exp;
    private final DfpField field;
    protected int[] mant;
    protected byte nans;
    protected byte sign;

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode;
        if (iArr == null) {
            iArr = new int[RoundingMode.values().length];
            try {
                iArr[RoundingMode.ROUND_CEIL.ordinal()] = 7;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[RoundingMode.ROUND_DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[RoundingMode.ROUND_FLOOR.ordinal()] = 8;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[RoundingMode.ROUND_HALF_DOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[RoundingMode.ROUND_HALF_EVEN.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[RoundingMode.ROUND_HALF_ODD.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[RoundingMode.ROUND_HALF_UP.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[RoundingMode.ROUND_UP.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode = iArr;
        }
        return iArr;
    }

    protected Dfp(DfpField field) {
        this.mant = new int[field.getRadixDigits()];
        this.sign = INFINITE;
        this.exp = 0;
        this.nans = FINITE;
        this.field = field;
    }

    protected Dfp(DfpField field, byte x) {
        this(field, (long) x);
    }

    protected Dfp(DfpField field, int x) {
        this(field, (long) x);
    }

    protected Dfp(DfpField field, long x) {
        this.mant = new int[field.getRadixDigits()];
        this.nans = FINITE;
        this.field = field;
        boolean isLongMin = false;
        if (x == Long.MIN_VALUE) {
            isLongMin = true;
            x++;
        }
        if (x < 0) {
            this.sign = (byte) -1;
            x = -x;
        } else {
            this.sign = INFINITE;
        }
        this.exp = 0;
        while (x != 0) {
            System.arraycopy(this.mant, this.mant.length - this.exp, this.mant, (this.mant.length - 1) - this.exp, this.exp);
            this.mant[this.mant.length - 1] = (int) (x % 10000);
            x /= 10000;
            this.exp++;
        }
        if (isLongMin) {
            for (int i = 0; i < this.mant.length - 1; i++) {
                if (this.mant[i] != 0) {
                    int[] iArr = this.mant;
                    iArr[i] = iArr[i] + 1;
                    return;
                }
            }
        }
    }

    protected Dfp(DfpField field, double x) {
        this.mant = new int[field.getRadixDigits()];
        this.sign = INFINITE;
        this.exp = 0;
        this.nans = FINITE;
        this.field = field;
        long bits = Double.doubleToLongBits(x);
        long mantissa = bits & 4503599627370495L;
        int exponent = ((int) ((9218868437227405312L & bits) >> 52)) - 1023;
        if (exponent == -1023) {
            if (x != 0.0d) {
                exponent++;
                while ((4503599627370496L & mantissa) == 0) {
                    exponent--;
                    mantissa <<= 1;
                }
                mantissa &= 4503599627370495L;
            } else if ((Long.MIN_VALUE & bits) != 0) {
                this.sign = (byte) -1;
                return;
            } else {
                return;
            }
        }
        if (exponent != IExpr.ASTID) {
            Dfp xdfp = new Dfp(field, mantissa).divide(new Dfp(field, 4503599627370496L)).add(field.getOne()).multiply(DfpMath.pow(field.getTwo(), exponent));
            if ((Long.MIN_VALUE & bits) != 0) {
                xdfp = xdfp.negate();
            }
            System.arraycopy(xdfp.mant, 0, this.mant, 0, this.mant.length);
            this.sign = xdfp.sign;
            this.exp = xdfp.exp;
            this.nans = xdfp.nans;
        } else if (x != x) {
            this.sign = INFINITE;
            this.nans = QNAN;
        } else if (x < 0.0d) {
            this.sign = (byte) -1;
            this.nans = INFINITE;
        } else {
            this.sign = INFINITE;
            this.nans = INFINITE;
        }
    }

    public Dfp(Dfp d) {
        this.mant = (int[]) d.mant.clone();
        this.sign = d.sign;
        this.exp = d.exp;
        this.nans = d.nans;
        this.field = d.field;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected Dfp(org.apache.commons.math4.dfp.DfpField r19, java.lang.String r20) {
        /*
        r18 = this;
        r18.<init>();
        r15 = r19.getRadixDigits();
        r15 = new int[r15];
        r0 = r18;
        r0.mant = r15;
        r15 = 1;
        r0 = r18;
        r0.sign = r15;
        r15 = 0;
        r0 = r18;
        r0.exp = r15;
        r15 = 0;
        r0 = r18;
        r0.nans = r15;
        r0 = r19;
        r1 = r18;
        r1.field = r0;
        r2 = 0;
        r11 = 4;
        r8 = 4;
        r15 = r18.getRadixDigits();
        r15 = r15 * 4;
        r15 = r15 + 8;
        r14 = new char[r15];
        r15 = "Infinity";
        r0 = r20;
        r15 = r0.equals(r15);
        if (r15 == 0) goto L_0x0044;
    L_0x0039:
        r15 = 1;
        r0 = r18;
        r0.sign = r15;
        r15 = 1;
        r0 = r18;
        r0.nans = r15;
    L_0x0043:
        return;
    L_0x0044:
        r15 = "-Infinity";
        r0 = r20;
        r15 = r0.equals(r15);
        if (r15 == 0) goto L_0x0059;
    L_0x004e:
        r15 = -1;
        r0 = r18;
        r0.sign = r15;
        r15 = 1;
        r0 = r18;
        r0.nans = r15;
        goto L_0x0043;
    L_0x0059:
        r15 = "NaN";
        r0 = r20;
        r15 = r0.equals(r15);
        if (r15 == 0) goto L_0x006e;
    L_0x0063:
        r15 = 1;
        r0 = r18;
        r0.sign = r15;
        r15 = 3;
        r0 = r18;
        r0.nans = r15;
        goto L_0x0043;
    L_0x006e:
        r15 = "e";
        r0 = r20;
        r9 = r0.indexOf(r15);
        r15 = -1;
        if (r9 != r15) goto L_0x0081;
    L_0x0079:
        r15 = "E";
        r0 = r20;
        r9 = r0.indexOf(r15);
    L_0x0081:
        r12 = 0;
        r15 = -1;
        if (r9 == r15) goto L_0x0168;
    L_0x0085:
        r15 = 0;
        r0 = r20;
        r4 = r0.substring(r15, r9);
        r15 = r9 + 1;
        r0 = r20;
        r5 = r0.substring(r15);
        r7 = 0;
        r6 = 0;
    L_0x0096:
        r15 = r5.length();
        if (r6 < r15) goto L_0x013a;
    L_0x009c:
        if (r7 == 0) goto L_0x009f;
    L_0x009e:
        r12 = -r12;
    L_0x009f:
        r15 = "-";
        r15 = r4.indexOf(r15);
        r16 = -1;
        r0 = r16;
        if (r15 == r0) goto L_0x00b0;
    L_0x00ab:
        r15 = -1;
        r0 = r18;
        r0.sign = r15;
    L_0x00b0:
        r9 = 0;
        r3 = 0;
    L_0x00b2:
        r15 = r4.charAt(r9);
        r16 = 49;
        r0 = r16;
        if (r15 < r0) goto L_0x016c;
    L_0x00bc:
        r15 = r4.charAt(r9);
        r16 = 57;
        r0 = r16;
        if (r15 > r0) goto L_0x016c;
    L_0x00c6:
        r10 = 4;
        r15 = 0;
        r16 = 48;
        r14[r15] = r16;
        r15 = 1;
        r16 = 48;
        r14[r15] = r16;
        r15 = 2;
        r16 = 48;
        r14[r15] = r16;
        r15 = 3;
        r16 = 48;
        r14[r15] = r16;
        r13 = 0;
    L_0x00dc:
        r15 = r4.length();
        if (r9 != r15) goto L_0x018f;
    L_0x00e2:
        if (r2 == 0) goto L_0x00ec;
    L_0x00e4:
        r15 = 4;
        if (r10 == r15) goto L_0x00ec;
    L_0x00e7:
        r10 = r10 + -1;
        r15 = 4;
        if (r10 != r15) goto L_0x01d2;
    L_0x00ec:
        if (r2 == 0) goto L_0x00f1;
    L_0x00ee:
        if (r13 != 0) goto L_0x00f1;
    L_0x00f0:
        r3 = 0;
    L_0x00f1:
        if (r2 != 0) goto L_0x00f5;
    L_0x00f3:
        r3 = r10 + -4;
    L_0x00f5:
        r10 = 4;
        r15 = r13 + -1;
        r9 = r15 + 4;
    L_0x00fa:
        if (r9 > r10) goto L_0x01de;
    L_0x00fc:
        r15 = 400 - r3;
        r16 = r12 % 4;
        r15 = r15 - r16;
        r6 = r15 % 4;
        r10 = r10 - r6;
        r3 = r3 + r6;
    L_0x0106:
        r15 = r9 - r10;
        r0 = r18;
        r0 = r0.mant;
        r16 = r0;
        r0 = r16;
        r0 = r0.length;
        r16 = r0;
        r16 = r16 * 4;
        r0 = r16;
        if (r15 < r0) goto L_0x01ea;
    L_0x0119:
        r0 = r18;
        r15 = r0.mant;
        r15 = r15.length;
        r6 = r15 + -1;
    L_0x0120:
        if (r6 >= 0) goto L_0x01f7;
    L_0x0122:
        r15 = r3 + r12;
        r15 = r15 / 4;
        r0 = r18;
        r0.exp = r15;
        r15 = r14.length;
        if (r10 >= r15) goto L_0x0043;
    L_0x012d:
        r15 = r14[r10];
        r15 = r15 + -48;
        r15 = r15 * 1000;
        r0 = r18;
        r0.round(r15);
        goto L_0x0043;
    L_0x013a:
        r15 = r5.charAt(r6);
        r16 = 45;
        r0 = r16;
        if (r15 != r0) goto L_0x0149;
    L_0x0144:
        r7 = 1;
    L_0x0145:
        r6 = r6 + 1;
        goto L_0x0096;
    L_0x0149:
        r15 = r5.charAt(r6);
        r16 = 48;
        r0 = r16;
        if (r15 < r0) goto L_0x0145;
    L_0x0153:
        r15 = r5.charAt(r6);
        r16 = 57;
        r0 = r16;
        if (r15 > r0) goto L_0x0145;
    L_0x015d:
        r15 = r12 * 10;
        r16 = r5.charAt(r6);
        r15 = r15 + r16;
        r12 = r15 + -48;
        goto L_0x0145;
    L_0x0168:
        r4 = r20;
        goto L_0x009f;
    L_0x016c:
        if (r2 == 0) goto L_0x017a;
    L_0x016e:
        r15 = r4.charAt(r9);
        r16 = 48;
        r0 = r16;
        if (r15 != r0) goto L_0x017a;
    L_0x0178:
        r3 = r3 + -1;
    L_0x017a:
        r15 = r4.charAt(r9);
        r16 = 46;
        r0 = r16;
        if (r15 != r0) goto L_0x0185;
    L_0x0184:
        r2 = 1;
    L_0x0185:
        r9 = r9 + 1;
        r15 = r4.length();
        if (r9 != r15) goto L_0x00b2;
    L_0x018d:
        goto L_0x00c6;
    L_0x018f:
        r0 = r18;
        r15 = r0.mant;
        r15 = r15.length;
        r15 = r15 * 4;
        r15 = r15 + 4;
        r15 = r15 + 1;
        if (r10 == r15) goto L_0x00e2;
    L_0x019c:
        r15 = r4.charAt(r9);
        r16 = 46;
        r0 = r16;
        if (r15 != r0) goto L_0x01ac;
    L_0x01a6:
        r2 = 1;
        r3 = r13;
        r9 = r9 + 1;
        goto L_0x00dc;
    L_0x01ac:
        r15 = r4.charAt(r9);
        r16 = 48;
        r0 = r16;
        if (r15 < r0) goto L_0x01c0;
    L_0x01b6:
        r15 = r4.charAt(r9);
        r16 = 57;
        r0 = r16;
        if (r15 <= r0) goto L_0x01c4;
    L_0x01c0:
        r9 = r9 + 1;
        goto L_0x00dc;
    L_0x01c4:
        r15 = r4.charAt(r9);
        r14[r10] = r15;
        r10 = r10 + 1;
        r9 = r9 + 1;
        r13 = r13 + 1;
        goto L_0x00dc;
    L_0x01d2:
        r15 = r14[r10];
        r16 = 48;
        r0 = r16;
        if (r15 != r0) goto L_0x00ec;
    L_0x01da:
        r13 = r13 + -1;
        goto L_0x00e7;
    L_0x01de:
        r15 = r14[r9];
        r16 = 48;
        r0 = r16;
        if (r15 != r0) goto L_0x00fc;
    L_0x01e6:
        r9 = r9 + -1;
        goto L_0x00fa;
    L_0x01ea:
        r6 = 0;
    L_0x01eb:
        r15 = 4;
        if (r6 >= r15) goto L_0x0106;
    L_0x01ee:
        r9 = r9 + 1;
        r15 = 48;
        r14[r9] = r15;
        r6 = r6 + 1;
        goto L_0x01eb;
    L_0x01f7:
        r0 = r18;
        r15 = r0.mant;
        r16 = r14[r10];
        r16 = r16 + -48;
        r0 = r16;
        r0 = r0 * 1000;
        r16 = r0;
        r17 = r10 + 1;
        r17 = r14[r17];
        r17 = r17 + -48;
        r17 = r17 * 100;
        r16 = r16 + r17;
        r17 = r10 + 2;
        r17 = r14[r17];
        r17 = r17 + -48;
        r17 = r17 * 10;
        r16 = r16 + r17;
        r17 = r10 + 3;
        r17 = r14[r17];
        r17 = r17 + -48;
        r16 = r16 + r17;
        r15[r6] = r16;
        r10 = r10 + 4;
        r6 = r6 + -1;
        goto L_0x0120;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.dfp.Dfp.<init>(org.apache.commons.math4.dfp.DfpField, java.lang.String):void");
    }

    protected Dfp(DfpField field, byte sign, byte nans) {
        this.field = field;
        this.mant = new int[field.getRadixDigits()];
        this.sign = sign;
        this.exp = 0;
        this.nans = nans;
    }

    public Dfp newInstance() {
        return new Dfp(getField());
    }

    public Dfp newInstance(byte x) {
        return new Dfp(getField(), x);
    }

    public Dfp newInstance(int x) {
        return new Dfp(getField(), x);
    }

    public Dfp newInstance(long x) {
        return new Dfp(getField(), x);
    }

    public Dfp newInstance(double x) {
        return new Dfp(getField(), x);
    }

    public Dfp newInstance(Dfp d) {
        if (this.field.getRadixDigits() == d.field.getRadixDigits()) {
            return new Dfp(d);
        }
        this.field.setIEEEFlagsBits(1);
        Dfp result = newInstance(getZero());
        result.nans = QNAN;
        return dotrap(1, NEW_INSTANCE_TRAP, d, result);
    }

    public Dfp newInstance(String s) {
        return new Dfp(this.field, s);
    }

    public Dfp newInstance(byte sig, byte code) {
        return this.field.newDfp(sig, code);
    }

    public DfpField getField() {
        return this.field;
    }

    public int getRadixDigits() {
        return this.field.getRadixDigits();
    }

    public Dfp getZero() {
        return this.field.getZero();
    }

    public Dfp getOne() {
        return this.field.getOne();
    }

    public Dfp getTwo() {
        return this.field.getTwo();
    }

    protected void shiftLeft() {
        for (int i = this.mant.length - 1; i > 0; i--) {
            this.mant[i] = this.mant[i - 1];
        }
        this.mant[0] = 0;
        this.exp--;
    }

    protected void shiftRight() {
        for (int i = 0; i < this.mant.length - 1; i++) {
            this.mant[i] = this.mant[i + 1];
        }
        this.mant[this.mant.length - 1] = 0;
        this.exp++;
    }

    protected int align(int e) {
        int lostdigit = 0;
        boolean inexact = false;
        int diff = this.exp - e;
        int adiff = diff;
        if (adiff < 0) {
            adiff = -adiff;
        }
        if (diff == 0) {
            return 0;
        }
        if (adiff > this.mant.length + 1) {
            Arrays.fill(this.mant, 0);
            this.exp = e;
            this.field.setIEEEFlagsBits(16);
            dotrap(16, ALIGN_TRAP, this, this);
            return 0;
        }
        for (int i = 0; i < adiff; i++) {
            if (diff < 0) {
                if (lostdigit != 0) {
                    inexact = true;
                }
                lostdigit = this.mant[0];
                shiftRight();
            } else {
                shiftLeft();
            }
        }
        if (inexact) {
            this.field.setIEEEFlagsBits(16);
            dotrap(16, ALIGN_TRAP, this, this);
        }
        return lostdigit;
    }

    public boolean lessThan(Dfp x) {
        if (this.field.getRadixDigits() != x.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            Dfp result = newInstance(getZero());
            result.nans = QNAN;
            dotrap(1, LESS_THAN_TRAP, x, result);
            return false;
        } else if (isNaN() || x.isNaN()) {
            this.field.setIEEEFlagsBits(1);
            dotrap(1, LESS_THAN_TRAP, x, newInstance(getZero()));
            return false;
        } else if (compare(this, x) < 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean greaterThan(Dfp x) {
        if (this.field.getRadixDigits() != x.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            Dfp result = newInstance(getZero());
            result.nans = QNAN;
            dotrap(1, GREATER_THAN_TRAP, x, result);
            return false;
        } else if (isNaN() || x.isNaN()) {
            this.field.setIEEEFlagsBits(1);
            dotrap(1, GREATER_THAN_TRAP, x, newInstance(getZero()));
            return false;
        } else if (compare(this, x) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean negativeOrNull() {
        if (isNaN()) {
            this.field.setIEEEFlagsBits(1);
            dotrap(1, LESS_THAN_TRAP, this, newInstance(getZero()));
            return false;
        } else if (this.sign < null || (this.mant[this.mant.length - 1] == 0 && !isInfinite())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean strictlyNegative() {
        if (isNaN()) {
            this.field.setIEEEFlagsBits(1);
            dotrap(1, LESS_THAN_TRAP, this, newInstance(getZero()));
            return false;
        } else if (this.sign >= null) {
            return false;
        } else {
            if (this.mant[this.mant.length - 1] != 0 || isInfinite()) {
                return true;
            }
            return false;
        }
    }

    public boolean positiveOrNull() {
        if (isNaN()) {
            this.field.setIEEEFlagsBits(1);
            dotrap(1, LESS_THAN_TRAP, this, newInstance(getZero()));
            return false;
        } else if (this.sign > null || (this.mant[this.mant.length - 1] == 0 && !isInfinite())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean strictlyPositive() {
        if (isNaN()) {
            this.field.setIEEEFlagsBits(1);
            dotrap(1, LESS_THAN_TRAP, this, newInstance(getZero()));
            return false;
        } else if (this.sign <= null) {
            return false;
        } else {
            if (this.mant[this.mant.length - 1] != 0 || isInfinite()) {
                return true;
            }
            return false;
        }
    }

    public Dfp abs() {
        Dfp result = newInstance(this);
        result.sign = INFINITE;
        return result;
    }

    public boolean isInfinite() {
        return this.nans == INFINITE;
    }

    public boolean isNaN() {
        return this.nans == 3 || this.nans == 2;
    }

    public boolean isZero() {
        if (isNaN()) {
            this.field.setIEEEFlagsBits(1);
            dotrap(1, LESS_THAN_TRAP, this, newInstance(getZero()));
            return false;
        } else if (this.mant[this.mant.length - 1] != 0 || isInfinite()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean equals(Object other) {
        if (!(other instanceof Dfp)) {
            return false;
        }
        Dfp x = (Dfp) other;
        if (isNaN() || x.isNaN() || this.field.getRadixDigits() != x.field.getRadixDigits() || compare(this, x) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((((this.sign << 8) + 17) + (this.nans << 16)) + this.exp) + Arrays.hashCode(this.mant);
    }

    public boolean unequal(Dfp x) {
        if (isNaN() || x.isNaN() || this.field.getRadixDigits() != x.field.getRadixDigits()) {
            return false;
        }
        if (greaterThan(x) || lessThan(x)) {
            return true;
        }
        return false;
    }

    private static int compare(Dfp a, Dfp b) {
        if (a.mant[a.mant.length - 1] == 0 && b.mant[b.mant.length - 1] == 0 && a.nans == null && b.nans == null) {
            return 0;
        }
        if (a.sign != b.sign) {
            if (a.sign == (byte) -1) {
                return -1;
            }
            return 1;
        } else if (a.nans == INFINITE && b.nans == null) {
            return a.sign;
        } else {
            if (a.nans == null && b.nans == INFINITE) {
                return -b.sign;
            }
            if (a.nans == INFINITE && b.nans == INFINITE) {
                return 0;
            }
            if (!(b.mant[b.mant.length - 1] == 0 || a.mant[b.mant.length - 1] == 0)) {
                if (a.exp < b.exp) {
                    return -a.sign;
                }
                if (a.exp > b.exp) {
                    return a.sign;
                }
            }
            for (int i = a.mant.length - 1; i >= 0; i--) {
                if (a.mant[i] > b.mant[i]) {
                    return a.sign;
                }
                if (a.mant[i] < b.mant[i]) {
                    return -a.sign;
                }
            }
            return 0;
        }
    }

    public Dfp rint() {
        return trunc(RoundingMode.ROUND_HALF_EVEN);
    }

    public Dfp floor() {
        return trunc(RoundingMode.ROUND_FLOOR);
    }

    public Dfp ceil() {
        return trunc(RoundingMode.ROUND_CEIL);
    }

    public Dfp remainder(Dfp d) {
        Dfp result = subtract(divide(d).rint().multiply(d));
        if (result.mant[this.mant.length - 1] == 0) {
            result.sign = this.sign;
        }
        return result;
    }

    protected Dfp trunc(RoundingMode rmode) {
        boolean changed = false;
        if (isNaN()) {
            return newInstance(this);
        }
        if (this.nans == INFINITE) {
            return newInstance(this);
        }
        if (this.mant[this.mant.length - 1] == 0) {
            return newInstance(this);
        }
        if (this.exp < 0) {
            this.field.setIEEEFlagsBits(16);
            return dotrap(16, TRUNC_TRAP, this, newInstance(getZero()));
        } else if (this.exp >= this.mant.length) {
            return newInstance(this);
        } else {
            Dfp result = newInstance(this);
            for (int i = 0; i < this.mant.length - result.exp; i++) {
                int i2;
                if (result.mant[i] != 0) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                changed |= i2;
                result.mant[i] = 0;
            }
            if (!changed) {
                return result;
            }
            switch ($SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode()[rmode.ordinal()]) {
                case R.styleable.Toolbar_contentInsetLeft /*7*/:
                    if (result.sign == INFINITE) {
                        result = result.add(getOne());
                        break;
                    }
                    break;
                case IExpr.INTEGERID /*8*/:
                    if (result.sign == (byte) -1) {
                        result = result.add(newInstance(-1));
                        break;
                    }
                    break;
                default:
                    Dfp half = newInstance("0.5");
                    Dfp a = subtract(result);
                    a.sign = INFINITE;
                    if (a.greaterThan(half)) {
                        a = newInstance(getOne());
                        a.sign = this.sign;
                        result = result.add(a);
                    }
                    if (a.equals(half) && result.exp > 0 && (result.mant[this.mant.length - result.exp] & 1) != 0) {
                        a = newInstance(getOne());
                        a.sign = this.sign;
                        result = result.add(a);
                        break;
                    }
            }
            this.field.setIEEEFlagsBits(16);
            return dotrap(16, TRUNC_TRAP, this, result);
        }
    }

    public int intValue() {
        int result = 0;
        Dfp rounded = rint();
        if (rounded.greaterThan(newInstance((int) BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT))) {
            return BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT;
        }
        if (rounded.lessThan(newInstance((int) RtlSpacingHelper.UNDEFINED))) {
            return RtlSpacingHelper.UNDEFINED;
        }
        for (int i = this.mant.length - 1; i >= this.mant.length - rounded.exp; i--) {
            result = (result * RADIX) + rounded.mant[i];
        }
        if (rounded.sign == -1) {
            result = -result;
        }
        return result;
    }

    public int log10K() {
        return this.exp - 1;
    }

    public Dfp power10K(int e) {
        Dfp d = newInstance(getOne());
        d.exp = e + 1;
        return d;
    }

    public int intLog10() {
        if (this.mant[this.mant.length - 1] > 1000) {
            return (this.exp * 4) - 1;
        }
        if (this.mant[this.mant.length - 1] > 100) {
            return (this.exp * 4) - 2;
        }
        if (this.mant[this.mant.length - 1] > 10) {
            return (this.exp * 4) - 3;
        }
        return (this.exp * 4) - 4;
    }

    public Dfp power10(int e) {
        Dfp d = newInstance(getOne());
        if (e >= 0) {
            d.exp = (e / 4) + 1;
        } else {
            d.exp = (e + 1) / 4;
        }
        switch (((e % 4) + 4) % 4) {
            case ValueServer.DIGEST_MODE /*0*/:
                return d;
            case ValueServer.REPLAY_MODE /*1*/:
                return d.multiply(10);
            case IExpr.DOUBLEID /*2*/:
                return d.multiply(100);
            default:
                return d.multiply((int) EmpiricalDistribution.DEFAULT_BIN_COUNT);
        }
    }

    protected int complement(int extra) {
        int i;
        extra = 10000 - extra;
        for (i = 0; i < this.mant.length; i++) {
            this.mant[i] = (10000 - this.mant[i]) - 1;
        }
        int rh = extra / RADIX;
        extra -= rh * RADIX;
        for (i = 0; i < this.mant.length; i++) {
            int r = this.mant[i] + rh;
            rh = r / RADIX;
            this.mant[i] = r - (rh * RADIX);
        }
        return extra;
    }

    public Dfp add(Dfp x) {
        if (this.field.getRadixDigits() != x.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            Dfp result = newInstance(getZero());
            result.nans = QNAN;
            return dotrap(1, ADD_TRAP, x, result);
        }
        int excp;
        if (!(this.nans == null && x.nans == null)) {
            if (isNaN()) {
                return this;
            }
            if (x.isNaN()) {
                return x;
            }
            byte b = this.nans;
            if (r0 == 1 && x.nans == null) {
                return this;
            }
            b = x.nans;
            if (r0 == 1 && this.nans == null) {
                return x;
            }
            b = x.nans;
            if (r0 == 1) {
                b = this.nans;
                if (r0 == 1 && this.sign == x.sign) {
                    return x;
                }
            }
            b = x.nans;
            if (r0 == 1) {
                b = this.nans;
                if (r0 == 1 && this.sign != x.sign) {
                    this.field.setIEEEFlagsBits(1);
                    result = newInstance(getZero());
                    result.nans = QNAN;
                    return dotrap(1, ADD_TRAP, x, result);
                }
            }
        }
        Dfp a = newInstance(this);
        Dfp b2 = newInstance(x);
        result = newInstance(getZero());
        byte asign = a.sign;
        byte bsign = b2.sign;
        a.sign = INFINITE;
        b2.sign = INFINITE;
        byte rsign = bsign;
        if (compare(a, b2) > 0) {
            rsign = asign;
        }
        if (b2.mant[this.mant.length - 1] == 0) {
            b2.exp = a.exp;
        }
        if (a.mant[this.mant.length - 1] == 0) {
            a.exp = b2.exp;
        }
        int aextradigit = 0;
        int bextradigit = 0;
        if (a.exp < b2.exp) {
            aextradigit = a.align(b2.exp);
        } else {
            bextradigit = b2.align(a.exp);
        }
        if (asign != bsign) {
            if (asign == rsign) {
                bextradigit = b2.complement(bextradigit);
            } else {
                aextradigit = a.complement(aextradigit);
            }
        }
        int rh = 0;
        int i = 0;
        while (true) {
            int length = this.mant.length;
            if (i >= r0) {
                break;
            }
            int r = (a.mant[i] + b2.mant[i]) + rh;
            rh = r / RADIX;
            result.mant[i] = r - (rh * RADIX);
            i++;
        }
        result.exp = a.exp;
        result.sign = rsign;
        if (rh != 0 && asign == bsign) {
            int lostdigit = result.mant[0];
            result.shiftRight();
            result.mant[this.mant.length - 1] = rh;
            excp = result.round(lostdigit);
            if (excp != 0) {
                result = dotrap(excp, ADD_TRAP, x, result);
            }
        }
        i = 0;
        while (true) {
            length = this.mant.length;
            if (i < r0) {
                if (result.mant[this.mant.length - 1] != 0) {
                    break;
                }
                result.shiftLeft();
                if (i == 0) {
                    result.mant[0] = aextradigit + bextradigit;
                    aextradigit = 0;
                    bextradigit = 0;
                }
                i++;
            } else {
                break;
            }
        }
        if (result.mant[this.mant.length - 1] == 0) {
            result.exp = 0;
            if (asign != bsign) {
                result.sign = INFINITE;
            }
        }
        excp = result.round(aextradigit + bextradigit);
        if (excp != 0) {
            result = dotrap(excp, ADD_TRAP, x, result);
        }
        return result;
    }

    public Dfp negate() {
        Dfp result = newInstance(this);
        result.sign = (byte) (-result.sign);
        return result;
    }

    public Dfp subtract(Dfp x) {
        return add(x.negate());
    }

    protected int round(int n) {
        boolean inc;
        switch ($SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode()[this.field.getRoundingMode().ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                inc = false;
                break;
            case IExpr.DOUBLEID /*2*/:
                if (n != 0) {
                    inc = true;
                } else {
                    inc = false;
                }
                break;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                if (n >= 5000) {
                    inc = true;
                } else {
                    inc = false;
                }
                break;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                if (n > 5000) {
                    inc = true;
                } else {
                    inc = false;
                }
                break;
            case ValueServer.CONSTANT_MODE /*5*/:
                if (n > 5000 || (n == 5000 && (this.mant[0] & 1) == 1)) {
                    inc = true;
                } else {
                    inc = false;
                }
                break;
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
                if (n > 5000 || (n == 5000 && (this.mant[0] & 1) == 0)) {
                    inc = true;
                } else {
                    inc = false;
                }
                break;
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
                if (this.sign != INFINITE || n == 0) {
                    inc = false;
                } else {
                    inc = true;
                }
                break;
            default:
                if (this.sign == -1 && n != 0) {
                    inc = true;
                    break;
                }
                inc = false;
                break;
        }
        if (inc) {
            int rh = 1;
            for (int i = 0; i < this.mant.length; i++) {
                int r = this.mant[i] + rh;
                rh = r / RADIX;
                this.mant[i] = r - (rh * RADIX);
            }
            if (rh != 0) {
                shiftRight();
                this.mant[this.mant.length - 1] = rh;
            }
        }
        if (this.exp < MIN_EXP) {
            this.field.setIEEEFlagsBits(8);
            return 8;
        } else if (this.exp > MAX_EXP) {
            this.field.setIEEEFlagsBits(4);
            return 4;
        } else if (n == 0) {
            return 0;
        } else {
            this.field.setIEEEFlagsBits(16);
            return 16;
        }
    }

    public Dfp multiply(Dfp x) {
        int i = 1;
        if (this.field.getRadixDigits() != x.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            Dfp result = newInstance(getZero());
            result.nans = QNAN;
            return dotrap(1, MULTIPLY_TRAP, x, result);
        }
        int i2;
        int excp;
        result = newInstance(getZero());
        if (!(this.nans == null && x.nans == null)) {
            if (isNaN()) {
                return this;
            }
            if (x.isNaN()) {
                return x;
            }
            if (this.nans == INFINITE && x.nans == null && x.mant[this.mant.length - 1] != 0) {
                result = newInstance(this);
                result.sign = (byte) (this.sign * x.sign);
                return result;
            } else if (x.nans == INFINITE && this.nans == null && this.mant[this.mant.length - 1] != 0) {
                result = newInstance(x);
                result.sign = (byte) (this.sign * x.sign);
                return result;
            } else if (x.nans == INFINITE && this.nans == INFINITE) {
                result = newInstance(this);
                result.sign = (byte) (this.sign * x.sign);
                return result;
            } else if ((x.nans == INFINITE && this.nans == null && this.mant[this.mant.length - 1] == 0) || (this.nans == INFINITE && x.nans == null && x.mant[this.mant.length - 1] == 0)) {
                this.field.setIEEEFlagsBits(1);
                result = newInstance(getZero());
                result.nans = QNAN;
                return dotrap(1, MULTIPLY_TRAP, x, result);
            }
        }
        int[] product = new int[(this.mant.length * 2)];
        for (i2 = 0; i2 < this.mant.length; i2++) {
            int rh = 0;
            for (int j = 0; j < this.mant.length; j++) {
                int r = (this.mant[i2] * x.mant[j]) + (product[i2 + j] + rh);
                rh = r / RADIX;
                product[i2 + j] = r - (rh * RADIX);
            }
            product[this.mant.length + i2] = rh;
        }
        int md = (this.mant.length * 2) - 1;
        for (i2 = (this.mant.length * 2) - 1; i2 >= 0; i2--) {
            if (product[i2] != 0) {
                md = i2;
                break;
            }
        }
        for (i2 = 0; i2 < this.mant.length; i2++) {
            result.mant[(this.mant.length - i2) - 1] = product[md - i2];
        }
        result.exp = (((this.exp + x.exp) + md) - (this.mant.length * 2)) + 1;
        if (this.sign != x.sign) {
            i = -1;
        }
        result.sign = (byte) i;
        if (result.mant[this.mant.length - 1] == 0) {
            result.exp = 0;
        }
        if (md > this.mant.length - 1) {
            excp = result.round(product[md - this.mant.length]);
        } else {
            excp = result.round(0);
        }
        if (excp != 0) {
            result = dotrap(excp, MULTIPLY_TRAP, x, result);
        }
        return result;
    }

    public Dfp multiply(int x) {
        if (x < 0 || x >= RADIX) {
            return multiply(newInstance(x));
        }
        return multiplyFast(x);
    }

    private Dfp multiplyFast(int x) {
        Dfp result = newInstance(this);
        if (this.nans != null) {
            if (isNaN()) {
                return this;
            }
            if (this.nans == INFINITE && x != 0) {
                return newInstance(this);
            }
            if (this.nans == INFINITE && x == 0) {
                this.field.setIEEEFlagsBits(1);
                result = newInstance(getZero());
                result.nans = QNAN;
                return dotrap(1, MULTIPLY_TRAP, newInstance(getZero()), result);
            }
        }
        if (x < 0 || x >= RADIX) {
            this.field.setIEEEFlagsBits(1);
            result = newInstance(getZero());
            result.nans = QNAN;
            return dotrap(1, MULTIPLY_TRAP, result, result);
        }
        int rh = 0;
        for (int i = 0; i < this.mant.length; i++) {
            int r = (this.mant[i] * x) + rh;
            rh = r / RADIX;
            result.mant[i] = r - (rh * RADIX);
        }
        int lostdigit = 0;
        if (rh != 0) {
            lostdigit = result.mant[0];
            result.shiftRight();
            result.mant[this.mant.length - 1] = rh;
        }
        if (result.mant[this.mant.length - 1] == 0) {
            result.exp = 0;
        }
        int excp = result.round(lostdigit);
        if (excp != 0) {
            result = dotrap(excp, MULTIPLY_TRAP, result, result);
        }
        return result;
    }

    public Dfp divide(Dfp divisor) {
        int trial = 0;
        if (this.field.getRadixDigits() != divisor.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            Dfp result = newInstance(getZero());
            result.nans = QNAN;
            return dotrap(1, DIVIDE_TRAP, divisor, result);
        }
        result = newInstance(getZero());
        if (!(this.nans == null && divisor.nans == null)) {
            if (isNaN()) {
                return this;
            }
            if (divisor.isNaN()) {
                return divisor;
            }
            byte b = this.nans;
            if (r0 == 1 && divisor.nans == null) {
                result = newInstance(this);
                result.sign = (byte) (this.sign * divisor.sign);
                return result;
            }
            b = divisor.nans;
            if (r0 == 1 && this.nans == null) {
                result = newInstance(getZero());
                result.sign = (byte) (this.sign * divisor.sign);
                return result;
            }
            b = divisor.nans;
            if (r0 == 1) {
                b = this.nans;
                if (r0 == 1) {
                    this.field.setIEEEFlagsBits(1);
                    result = newInstance(getZero());
                    result.nans = QNAN;
                    return dotrap(1, DIVIDE_TRAP, divisor, result);
                }
            }
        }
        if (divisor.mant[this.mant.length - 1] == 0) {
            this.field.setIEEEFlagsBits(2);
            result = newInstance(getZero());
            result.sign = (byte) (this.sign * divisor.sign);
            result.nans = INFINITE;
            return dotrap(2, DIVIDE_TRAP, divisor, result);
        }
        int excp;
        int[] dividend = new int[(this.mant.length + 1)];
        int[] quotient = new int[(this.mant.length + 2)];
        int[] remainder = new int[(this.mant.length + 1)];
        dividend[this.mant.length] = 0;
        quotient[this.mant.length] = 0;
        quotient[this.mant.length + 1] = 0;
        remainder[this.mant.length] = 0;
        int i = 0;
        while (true) {
            int length = this.mant.length;
            if (i >= r0) {
                break;
            }
            dividend[i] = this.mant[i];
            quotient[i] = 0;
            remainder[i] = 0;
            i++;
        }
        int nsqd = 0;
        for (int qd = this.mant.length + 1; qd >= 0; qd--) {
            int divMsb = (dividend[this.mant.length] * RADIX) + dividend[this.mant.length - 1];
            int min = divMsb / (divisor.mant[this.mant.length - 1] + 1);
            int max = (divMsb + 1) / divisor.mant[this.mant.length - 1];
            boolean trialgood = false;
            while (!trialgood) {
                trial = (min + max) / 2;
                int rh = 0;
                i = 0;
                while (true) {
                    if (i >= this.mant.length + 1) {
                        break;
                    }
                    length = this.mant.length;
                    int r = ((i < r0 ? divisor.mant[i] : 0) * trial) + rh;
                    rh = r / RADIX;
                    remainder[i] = r - (rh * RADIX);
                    i++;
                }
                rh = 1;
                i = 0;
                while (true) {
                    if (i >= this.mant.length + 1) {
                        break;
                    }
                    r = ((9999 - remainder[i]) + dividend[i]) + rh;
                    rh = r / RADIX;
                    remainder[i] = r - (rh * RADIX);
                    i++;
                }
                if (rh == 0) {
                    max = trial - 1;
                } else {
                    int minadj = ((remainder[this.mant.length] * RADIX) + remainder[this.mant.length - 1]) / (divisor.mant[this.mant.length - 1] + 1);
                    if (minadj >= 2) {
                        min = trial + minadj;
                    } else {
                        trialgood = false;
                        for (i = this.mant.length - 1; i >= 0; i--) {
                            if (divisor.mant[i] > remainder[i]) {
                                trialgood = true;
                            }
                            if (divisor.mant[i] < remainder[i]) {
                                break;
                            }
                        }
                        if (remainder[this.mant.length] != 0) {
                            trialgood = false;
                        }
                        if (!trialgood) {
                            min = trial + 1;
                        }
                    }
                }
            }
            quotient[qd] = trial;
            if (!(trial == 0 && nsqd == 0)) {
                nsqd++;
            }
            if (this.field.getRoundingMode() == RoundingMode.ROUND_DOWN) {
                length = this.mant.length;
                if (nsqd == r0) {
                    break;
                }
            }
            length = this.mant.length;
            if (nsqd > r0) {
                break;
            }
            dividend[0] = 0;
            i = 0;
            while (true) {
                length = this.mant.length;
                if (i >= r0) {
                    break;
                }
                dividend[i + 1] = remainder[i];
                i++;
            }
        }
        int md = this.mant.length;
        for (i = this.mant.length + 1; i >= 0; i--) {
            if (quotient[i] != 0) {
                md = i;
                break;
            }
        }
        i = 0;
        while (true) {
            length = this.mant.length;
            if (i >= r0) {
                break;
            }
            result.mant[(this.mant.length - i) - 1] = quotient[md - i];
            i++;
        }
        result.exp = ((this.exp - divisor.exp) + md) - this.mant.length;
        result.sign = (byte) (this.sign == divisor.sign ? 1 : -1);
        if (result.mant[this.mant.length - 1] == 0) {
            result.exp = 0;
        }
        if (md > this.mant.length - 1) {
            excp = result.round(quotient[md - this.mant.length]);
        } else {
            excp = result.round(0);
        }
        if (excp != 0) {
            result = dotrap(excp, DIVIDE_TRAP, divisor, result);
        }
        return result;
    }

    public Dfp divide(int divisor) {
        if (this.nans != null) {
            if (isNaN()) {
                return this;
            }
            if (this.nans == INFINITE) {
                return newInstance(this);
            }
        }
        Dfp result;
        if (divisor == 0) {
            this.field.setIEEEFlagsBits(2);
            result = newInstance(getZero());
            result.sign = this.sign;
            result.nans = INFINITE;
            return dotrap(2, DIVIDE_TRAP, getZero(), result);
        } else if (divisor < 0 || divisor >= RADIX) {
            this.field.setIEEEFlagsBits(1);
            result = newInstance(getZero());
            result.nans = QNAN;
            return dotrap(1, DIVIDE_TRAP, result, result);
        } else {
            int r;
            int rh;
            result = newInstance(this);
            int rl = 0;
            for (int i = this.mant.length - 1; i >= 0; i--) {
                r = (rl * RADIX) + result.mant[i];
                rh = r / divisor;
                rl = r - (rh * divisor);
                result.mant[i] = rh;
            }
            if (result.mant[this.mant.length - 1] == 0) {
                result.shiftLeft();
                r = rl * RADIX;
                rh = r / divisor;
                rl = r - (rh * divisor);
                result.mant[0] = rh;
            }
            int excp = result.round((rl * RADIX) / divisor);
            if (excp != 0) {
                return dotrap(excp, DIVIDE_TRAP, result, result);
            }
            return result;
        }
    }

    public Dfp reciprocal() {
        return this.field.getOne().divide(this);
    }

    public Dfp sqrt() {
        if (this.nans == null && this.mant[this.mant.length - 1] == 0) {
            return newInstance(this);
        }
        if (this.nans != null) {
            if (this.nans == INFINITE && this.sign == INFINITE) {
                return newInstance(this);
            }
            if (this.nans == QNAN) {
                return newInstance(this);
            }
            if (this.nans == SNAN) {
                this.field.setIEEEFlagsBits(1);
                return dotrap(1, SQRT_TRAP, null, newInstance(this));
            }
        }
        if (this.sign == (byte) -1) {
            this.field.setIEEEFlagsBits(1);
            Dfp result = newInstance(this);
            result.nans = QNAN;
            return dotrap(1, SQRT_TRAP, null, result);
        }
        Dfp x = newInstance(this);
        if (x.exp < -1 || x.exp > 1) {
            x.exp = this.exp / 2;
        }
        switch (x.mant[this.mant.length - 1] / MicrosphereInterpolator.DEFAULT_MICROSPHERE_ELEMENTS) {
            case ValueServer.DIGEST_MODE /*0*/:
                x.mant[this.mant.length - 1] = (x.mant[this.mant.length - 1] / 2) + 1;
                break;
            case IExpr.DOUBLEID /*2*/:
                x.mant[this.mant.length - 1] = ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED;
                break;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                x.mant[this.mant.length - 1] = 2200;
                break;
            default:
                x.mant[this.mant.length - 1] = 3000;
                break;
        }
        Dfp dx = newInstance(x);
        Dfp px = getZero();
        Dfp ppx = getZero();
        while (x.unequal(px)) {
            dx = newInstance(x);
            dx.sign = (byte) -1;
            dx = dx.add(divide(x)).divide(2);
            ppx = px;
            px = x;
            x = x.add(dx);
            if (!x.equals(ppx)) {
                if (dx.mant[this.mant.length - 1] == 0) {
                }
            }
            return x;
        }
        return x;
    }

    public String toString() {
        if (this.nans != null) {
            if (this.nans == 1) {
                return this.sign < null ? NEG_INFINITY_STRING : POS_INFINITY_STRING;
            } else {
                return NAN_STRING;
            }
        } else if (this.exp > this.mant.length || this.exp < -1) {
            return dfp2sci();
        } else {
            return dfp2string();
        }
    }

    protected String dfp2sci() {
        int i;
        char[] rawdigits = new char[(this.mant.length * 4)];
        char[] outputbuffer = new char[((this.mant.length * 4) + 20)];
        int p = 0;
        for (int i2 = this.mant.length - 1; i2 >= 0; i2--) {
            i = p + 1;
            rawdigits[p] = (char) ((this.mant[i2] / EmpiricalDistribution.DEFAULT_BIN_COUNT) + 48);
            p = i + 1;
            rawdigits[i] = (char) (((this.mant[i2] / 100) % 10) + 48);
            i = p + 1;
            rawdigits[p] = (char) (((this.mant[i2] / 10) % 10) + 48);
            p = i + 1;
            rawdigits[i] = (char) ((this.mant[i2] % 10) + 48);
        }
        i = 0;
        while (i < rawdigits.length && rawdigits[i] == '0') {
            i++;
        }
        int shf = i;
        int i3 = 0;
        if (this.sign == -1) {
            int q = 0 + 1;
            outputbuffer[0] = Constants.MINUS_UNICODE;
            i3 = q;
        }
        if (i != rawdigits.length) {
            q = i3 + 1;
            p = i + 1;
            outputbuffer[i3] = rawdigits[i];
            i3 = q + 1;
            outputbuffer[q] = '.';
            i = p;
            while (i < rawdigits.length) {
                q = i3 + 1;
                p = i + 1;
                outputbuffer[i3] = rawdigits[i];
                i3 = q;
                i = p;
            }
            q = i3 + 1;
            outputbuffer[i3] = 'e';
            int e = ((this.exp * 4) - shf) - 1;
            int ae = e;
            if (e < 0) {
                ae = -e;
            }
            i = 1000000000;
            while (i > ae) {
                i /= 10;
            }
            if (e < 0) {
                i3 = q + 1;
                outputbuffer[q] = Constants.MINUS_UNICODE;
                q = i3;
            }
            while (i > 0) {
                i3 = q + 1;
                outputbuffer[q] = (char) ((ae / i) + 48);
                ae %= i;
                i /= 10;
                q = i3;
            }
            i3 = q;
            return new String(outputbuffer, 0, q);
        }
        q = i3 + 1;
        outputbuffer[i3] = '0';
        i3 = q + 1;
        outputbuffer[q] = '.';
        q = i3 + 1;
        outputbuffer[i3] = '0';
        i3 = q + 1;
        outputbuffer[q] = 'e';
        q = i3 + 1;
        outputbuffer[i3] = '0';
        i3 = q;
        return new String(outputbuffer, 0, 5);
    }

    protected String dfp2string() {
        int p;
        int p2;
        char[] buffer = new char[((this.mant.length * 4) + 20)];
        int e = this.exp;
        boolean pointInserted = false;
        buffer[0] = Letters.SPACE;
        if (e <= 0) {
            p = 1 + 1;
            buffer[1] = '0';
            p2 = p + 1;
            buffer[p] = '.';
            pointInserted = true;
            p = p2;
        } else {
            p = 1;
        }
        while (e < 0) {
            p2 = p + 1;
            buffer[p] = '0';
            p = p2 + 1;
            buffer[p2] = '0';
            p2 = p + 1;
            buffer[p] = '0';
            p = p2 + 1;
            buffer[p2] = '0';
            e++;
        }
        int i = this.mant.length - 1;
        while (i >= 0) {
            p2 = p + 1;
            buffer[p] = (char) ((this.mant[i] / EmpiricalDistribution.DEFAULT_BIN_COUNT) + 48);
            p = p2 + 1;
            buffer[p2] = (char) (((this.mant[i] / 100) % 10) + 48);
            p2 = p + 1;
            buffer[p] = (char) (((this.mant[i] / 10) % 10) + 48);
            p = p2 + 1;
            buffer[p2] = (char) ((this.mant[i] % 10) + 48);
            e--;
            if (e == 0) {
                p2 = p + 1;
                buffer[p] = '.';
                pointInserted = true;
            } else {
                p2 = p;
            }
            i--;
            p = p2;
        }
        while (e > 0) {
            p2 = p + 1;
            buffer[p] = '0';
            p = p2 + 1;
            buffer[p2] = '0';
            p2 = p + 1;
            buffer[p] = '0';
            p = p2 + 1;
            buffer[p2] = '0';
            e--;
        }
        if (pointInserted) {
            p2 = p;
        } else {
            p2 = p + 1;
            buffer[p] = '.';
        }
        int q = 1;
        while (buffer[q] == '0') {
            q++;
        }
        if (buffer[q] == '.') {
            q--;
        }
        while (buffer[p2 - 1] == '0') {
            p2--;
        }
        if (this.sign < null) {
            q--;
            buffer[q] = Constants.MINUS_UNICODE;
        }
        return new String(buffer, q, p2 - q);
    }

    public Dfp dotrap(int type, String what, Dfp oper, Dfp result) {
        Dfp def = result;
        switch (type) {
            case ValueServer.REPLAY_MODE /*1*/:
                def = newInstance(getZero());
                def.sign = result.sign;
                def.nans = QNAN;
                break;
            case IExpr.DOUBLEID /*2*/:
                if (this.nans == null && this.mant[this.mant.length - 1] != 0) {
                    def = newInstance(getZero());
                    def.sign = (byte) (this.sign * oper.sign);
                    def.nans = INFINITE;
                }
                if (this.nans == null && this.mant[this.mant.length - 1] == 0) {
                    def = newInstance(getZero());
                    def.nans = QNAN;
                }
                if (this.nans == INFINITE || this.nans == QNAN) {
                    def = newInstance(getZero());
                    def.nans = QNAN;
                }
                if (this.nans == INFINITE || this.nans == 2) {
                    def = newInstance(getZero());
                    def.nans = QNAN;
                    break;
                }
            case IExpr.DOUBLECOMPLEXID /*4*/:
                result.exp -= 32760;
                def = newInstance(getZero());
                def.sign = result.sign;
                def.nans = INFINITE;
                break;
            case IExpr.INTEGERID /*8*/:
                if (result.exp + this.mant.length < MIN_EXP) {
                    def = newInstance(getZero());
                    def.sign = result.sign;
                } else {
                    def = newInstance(result);
                }
                result.exp += ERR_SCALE;
                break;
            default:
                def = result;
                break;
        }
        return trap(type, what, oper, def, result);
    }

    protected Dfp trap(int type, String what, Dfp oper, Dfp def, Dfp result) {
        return def;
    }

    public int classify() {
        return this.nans;
    }

    public static Dfp copysign(Dfp x, Dfp y) {
        Dfp result = x.newInstance(x);
        result.sign = y.sign;
        return result;
    }

    public Dfp nextAfter(Dfp x) {
        if (this.field.getRadixDigits() != x.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            Dfp result = newInstance(getZero());
            result.nans = QNAN;
            return dotrap(1, NEXT_AFTER_TRAP, x, result);
        }
        boolean up = false;
        if (lessThan(x)) {
            up = true;
        }
        if (compare(this, x) == 0) {
            return newInstance(x);
        }
        if (lessThan(getZero())) {
            if (up) {
                up = false;
            } else {
                up = true;
            }
        }
        Dfp inc;
        if (up) {
            inc = newInstance(getOne());
            inc.exp = (this.exp - this.mant.length) + 1;
            inc.sign = this.sign;
            if (equals(getZero())) {
                inc.exp = -32767 - this.mant.length;
            }
            result = add(inc);
        } else {
            inc = newInstance(getOne());
            inc.exp = this.exp;
            inc.sign = this.sign;
            if (equals(inc)) {
                inc.exp = this.exp - this.mant.length;
            } else {
                inc.exp = (this.exp - this.mant.length) + 1;
            }
            if (equals(getZero())) {
                inc.exp = -32767 - this.mant.length;
            }
            result = subtract(inc);
        }
        if (result.classify() == 1 && classify() != 1) {
            this.field.setIEEEFlagsBits(16);
            result = dotrap(16, NEXT_AFTER_TRAP, x, result);
        }
        if (!result.equals(getZero()) || equals(getZero())) {
            return result;
        }
        this.field.setIEEEFlagsBits(16);
        return dotrap(16, NEXT_AFTER_TRAP, x, result);
    }

    public double toDouble() {
        if (isInfinite()) {
            if (lessThan(getZero())) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        } else if (isNaN()) {
            return Double.NaN;
        } else {
            Dfp y = this;
            boolean negate = false;
            int cmp0 = compare(this, getZero());
            if (cmp0 == 0) {
                return this.sign < null ? -0.0d : 0.0d;
            } else {
                if (cmp0 < 0) {
                    y = negate();
                    negate = true;
                }
                int exponent = (int) (((double) y.intLog10()) * 3.32d);
                if (exponent < 0) {
                    exponent--;
                }
                Dfp tempDfp = DfpMath.pow(getTwo(), exponent);
                while (true) {
                    if (!tempDfp.lessThan(y) && !tempDfp.equals(y)) {
                        break;
                    }
                    tempDfp = tempDfp.multiply(2);
                    exponent++;
                }
                exponent--;
                y = y.divide(DfpMath.pow(getTwo(), exponent));
                if (exponent > -1023) {
                    y = y.subtract(getOne());
                }
                if (exponent < -1074) {
                    return 0.0d;
                }
                if (exponent > 1023) {
                    return negate ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
                } else {
                    String str = y.multiply(newInstance(4503599627370496L)).rint().toString();
                    long mantissa = Long.parseLong(str.substring(0, str.length() - 1));
                    if (mantissa == 4503599627370496L) {
                        mantissa = 0;
                        exponent++;
                    }
                    if (exponent <= -1023) {
                        exponent--;
                    }
                    while (exponent < -1023) {
                        exponent++;
                        mantissa >>>= 1;
                    }
                    double x = Double.longBitsToDouble(mantissa | ((((long) exponent) + 1023) << 52));
                    if (negate) {
                        x = -x;
                    }
                    return x;
                }
            }
        }
    }

    public double[] toSplitDouble() {
        return new double[]{Double.longBitsToDouble(Double.doubleToLongBits(toDouble()) & ExpVectorInteger.minInt), subtract(newInstance(split[0])).toDouble()};
    }

    public double getReal() {
        return toDouble();
    }

    public Dfp add(double a) {
        return add(newInstance(a));
    }

    public Dfp subtract(double a) {
        return subtract(newInstance(a));
    }

    public Dfp multiply(double a) {
        return multiply(newInstance(a));
    }

    public Dfp divide(double a) {
        return divide(newInstance(a));
    }

    public Dfp remainder(double a) {
        return remainder(newInstance(a));
    }

    public long round() {
        return FastMath.round(toDouble());
    }

    public Dfp signum() {
        if (isNaN() || isZero()) {
            return this;
        }
        return newInstance(this.sign > null ? 1 : -1);
    }

    public Dfp copySign(Dfp s) {
        if (this.sign < null || s.sign < null) {
            return (this.sign >= null || s.sign >= null) ? negate() : this;
        } else {
            return this;
        }
    }

    public Dfp copySign(double s) {
        long sb = Double.doubleToLongBits(s);
        if (this.sign < null || sb < 0) {
            return (this.sign >= null || sb >= 0) ? negate() : this;
        } else {
            return this;
        }
    }

    public Dfp scalb(int n) {
        return multiply(DfpMath.pow(getTwo(), n));
    }

    public Dfp hypot(Dfp y) {
        return multiply(this).add(y.multiply(y)).sqrt();
    }

    public Dfp cbrt() {
        return rootN(3);
    }

    public Dfp rootN(int n) {
        if (this.sign >= null) {
            return DfpMath.pow(this, getOne().divide(n));
        }
        return DfpMath.pow(negate(), getOne().divide(n)).negate();
    }

    public Dfp pow(double p) {
        return DfpMath.pow(this, newInstance(p));
    }

    public Dfp pow(int n) {
        return DfpMath.pow(this, n);
    }

    public Dfp pow(Dfp e) {
        return DfpMath.pow(this, e);
    }

    public Dfp exp() {
        return DfpMath.exp(this);
    }

    public Dfp expm1() {
        return DfpMath.exp(this).subtract(getOne());
    }

    public Dfp log() {
        return DfpMath.log(this);
    }

    public Dfp log1p() {
        return DfpMath.log(add(getOne()));
    }

    public Dfp log10() {
        return DfpMath.log(this).divide(DfpMath.log(newInstance(10)));
    }

    public Dfp cos() {
        return DfpMath.cos(this);
    }

    public Dfp sin() {
        return DfpMath.sin(this);
    }

    public Dfp tan() {
        return DfpMath.tan(this);
    }

    public Dfp acos() {
        return DfpMath.acos(this);
    }

    public Dfp asin() {
        return DfpMath.asin(this);
    }

    public Dfp atan() {
        return DfpMath.atan(this);
    }

    public Dfp atan2(Dfp x) throws DimensionMismatchException {
        Dfp r = x.multiply(x).add(multiply(this)).sqrt();
        if (x.sign >= null) {
            return getTwo().multiply(divide(r.add(x)).atan());
        }
        Dfp tmp = getTwo().multiply(divide(r.subtract(x)).atan());
        return newInstance(tmp.sign <= null ? -3.141592653589793d : FastMath.PI).subtract(tmp);
    }

    public Dfp cosh() {
        return DfpMath.exp(this).add(DfpMath.exp(negate())).divide(2);
    }

    public Dfp sinh() {
        return DfpMath.exp(this).subtract(DfpMath.exp(negate())).divide(2);
    }

    public Dfp tanh() {
        Dfp ePlus = DfpMath.exp(this);
        Dfp eMinus = DfpMath.exp(negate());
        return ePlus.subtract(eMinus).divide(ePlus.add(eMinus));
    }

    public Dfp acosh() {
        return multiply(this).subtract(getOne()).sqrt().add(this).log();
    }

    public Dfp asinh() {
        return multiply(this).add(getOne()).sqrt().add(this).log();
    }

    public Dfp atanh() {
        return getOne().add(this).divide(getOne().subtract(this)).log().divide(2);
    }

    public Dfp linearCombination(Dfp[] a, Dfp[] b) throws DimensionMismatchException {
        if (a.length != b.length) {
            throw new DimensionMismatchException(a.length, b.length);
        }
        Dfp r = getZero();
        for (int i = 0; i < a.length; i++) {
            r = r.add(a[i].multiply(b[i]));
        }
        return r;
    }

    public Dfp linearCombination(double[] a, Dfp[] b) throws DimensionMismatchException {
        if (a.length != b.length) {
            throw new DimensionMismatchException(a.length, b.length);
        }
        Dfp r = getZero();
        for (int i = 0; i < a.length; i++) {
            r = r.add(b[i].multiply(a[i]));
        }
        return r;
    }

    public Dfp linearCombination(Dfp a1, Dfp b1, Dfp a2, Dfp b2) {
        return a1.multiply(b1).add(a2.multiply(b2));
    }

    public Dfp linearCombination(double a1, Dfp b1, double a2, Dfp b2) {
        return b1.multiply(a1).add(b2.multiply(a2));
    }

    public Dfp linearCombination(Dfp a1, Dfp b1, Dfp a2, Dfp b2, Dfp a3, Dfp b3) {
        return a1.multiply(b1).add(a2.multiply(b2)).add(a3.multiply(b3));
    }

    public Dfp linearCombination(double a1, Dfp b1, double a2, Dfp b2, double a3, Dfp b3) {
        return b1.multiply(a1).add(b2.multiply(a2)).add(b3.multiply(a3));
    }

    public Dfp linearCombination(Dfp a1, Dfp b1, Dfp a2, Dfp b2, Dfp a3, Dfp b3, Dfp a4, Dfp b4) {
        return a1.multiply(b1).add(a2.multiply(b2)).add(a3.multiply(b3)).add(a4.multiply(b4));
    }

    public Dfp linearCombination(double a1, Dfp b1, double a2, Dfp b2, double a3, Dfp b3, double a4, Dfp b4) {
        return b1.multiply(a1).add(b2.multiply(a2)).add(b3.multiply(a3)).add(b4.multiply(a4));
    }
}
